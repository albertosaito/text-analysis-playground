package io.vuh.text.cache;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.DeploymentMode;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.marshaller.Marshaller;
import org.apache.ignite.marshaller.optimized.OptimizedMarshaller;
import org.apache.ignite.spi.discovery.DiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;
import org.apache.log4j.Logger;
import org.rage.syring.annotation.ApplicationProperty;

@Singleton
@Startup
public class IgniteDelegateImpl implements IgniteDelegate {

	private Ignite igniteInstance = null;

	@Inject
	private Logger log;

	@Inject
	@ApplicationProperty(name = "ignite.deployment.mode", type = ApplicationProperty.Types.SYSTEM)
	private String deploymentMode;
	@Inject
	@ApplicationProperty(name = "ignite.grid.name", type = ApplicationProperty.Types.SYSTEM)
	private String clientName;
	@Inject
	@ApplicationProperty(name = "ignite.log.metrics.frequency", type = ApplicationProperty.Types.SYSTEM)
	private String metricsFrequency;
	@Inject
	@ApplicationProperty(name = "ignite.multicast.address", type = ApplicationProperty.Types.SYSTEM)
	private String multicastGroup;
	@Inject
	@ApplicationProperty(name = "ignite.multicast.port", type = ApplicationProperty.Types.SYSTEM)
	private String multicastPort;
	@Inject
	@ApplicationProperty(name = "ignite.peerclassloading.enabled", type = ApplicationProperty.Types.SYSTEM)
	private String peerclassloadingEnabled;
	@Inject
	@ApplicationProperty(name = "ignite.serializable.required", type = ApplicationProperty.Types.SYSTEM)
	private String requiredSerializable;

	@PostConstruct
	public void connect() {
		log.info("<<<Starting ignite....>>> ");
		igniteInstance = Ignition.start(createIgniteConfiguration());
		log.info("<<<Finish>>>");
	}

	@PreDestroy
	public void shutdown() {
		try {
			igniteInstance.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * @return ic Ignite Configuration
	 */
	private IgniteConfiguration createIgniteConfiguration() {
		final IgniteConfiguration ic = new IgniteConfiguration();

		ic.setClientMode(Boolean.TRUE);
		ic.setDeploymentMode(DeploymentMode.valueOf(deploymentMode));
		ic.setPeerClassLoadingEnabled(Boolean.parseBoolean(peerclassloadingEnabled));
		ic.setGridName(clientName);
		ic.setMetricsLogFrequency(Long.parseLong(metricsFrequency));

		ic.setDiscoverySpi(getIgniteDiscoverySpi());
		ic.setMarshaller(getIgniteMarshaller());
		return ic;
	}

	/**
	 * @return Marshaller
	 */
	private Marshaller getIgniteMarshaller() {
		final OptimizedMarshaller marsh = new OptimizedMarshaller();
		marsh.setRequireSerializable(Boolean.parseBoolean(requiredSerializable));
		return marsh;
	}

	/**
	 * @return DiscoverySpi
	 */
	private DiscoverySpi getIgniteDiscoverySpi() {
		final TcpDiscoverySpi spi = new TcpDiscoverySpi();
		final TcpDiscoveryMulticastIpFinder tcpDiscoMulticast = new TcpDiscoveryMulticastIpFinder();
		final List<String> addresses = new ArrayList<>(1);

		spi.setSocketTimeout(90000l);

		tcpDiscoMulticast.setMulticastGroup(multicastGroup);
		tcpDiscoMulticast.setMulticastPort(Integer.parseInt(multicastPort));

		addresses.add("localhost");
		tcpDiscoMulticast.setAddresses(addresses);

		spi.setIpFinder(tcpDiscoMulticast);
		return spi;
	}

	@Override
	public String getArticle(String key) {
		return getOrCreateArticleCache().get(key);
	}

	@Override
	public void saveArticle(String key, String articleJson) {
		log.info("Inserting with key "+key);
		getArticleDataStreamer().addData(key, articleJson);
		getArticleDataStreamer().flush();
	}

	private IgniteCache<String, String> getOrCreateArticleCache() {
		log.debug("getOrCreateCache cache...");
		final CacheConfiguration<String, String> cacheCfg = new CacheConfiguration<String, String>();
		cacheCfg.setBackups(1);
		cacheCfg.setCacheMode(CacheMode.PARTITIONED);
		cacheCfg.setName("articles");
		final IgniteCache<String, String> cache = igniteInstance.getOrCreateCache(cacheCfg);
		return cache;
	}

	public IgniteDataStreamer<String, String> getArticleDataStreamer() {
		getOrCreateArticleCache();
		return igniteInstance.dataStreamer("articles");
	}

}
