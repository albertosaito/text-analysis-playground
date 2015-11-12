package io.vuh.text.producer;

import java.net.InetSocketAddress;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.apache.log4j.Logger;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Rene Loperena <rene@vuh.io>
 *
 */
public class Context {
	private Client client;

	/**
	 * @param injectionPoint
	 * @return
	 */
	@Produces
	public Client createClient(final InjectionPoint injectionPoint) {
	    String elasticSearchHost = null;
	    // check if Docker links env is present...
	    if (System.getenv("ELASTICSEARCH_1_PORT_9300_TCP_ADDR") != null) 
		elasticSearchHost = System.getenv("ELASTICSEARCH_1_PORT_9300_TCP_ADDR");
	    else if (System.getProperty("ELASTICSEARCH_IP") != null)
		// read from system properties
		elasticSearchHost = System.getenv("ELASTICSEARCH_IP");
	    else
		//default it to 127.0.0.1
		elasticSearchHost = "127.0.0.1";
		
	    client = new TransportClient.Builder().build().addTransportAddress(
			new InetSocketTransportAddress(new InetSocketAddress(elasticSearchHost, 9300)));
	    
	    
		return client;
	}

	/**
	 * @param injectionPoint
	 * @return
	 */
	@Produces
	public Logger createLogger(final InjectionPoint injectionPoint) {
		return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
	}

	/**
	 * @param injectionPoint
	 * @return
	 */
	@Produces
	public ObjectMapper createObjectMapper(final InjectionPoint injectionPoint) {
		return new ObjectMapper();
	}

}
