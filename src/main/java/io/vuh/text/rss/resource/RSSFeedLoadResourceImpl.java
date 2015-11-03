package io.vuh.text.rss.resource;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import io.vuh.text.rss.manager.RSSArticleManager;
import io.vuh.text.rss.resource.transport.LoadRSSResponse;

/**
 * @author nobuji.saito
 *
 */
public class RSSFeedLoadResourceImpl implements RSSFeedLoadResource {

	@Inject
	private RSSArticleManager rssArticleManager;

	@Inject
	private Logger logger;
	
	/*
	 * (non-Javadoc)
	 *
	 * @see io.vuh.text.rss.resource.RSSFeedLoadResource#loadRSSFeed(java.lang.
	 * String)
	 */
	@Override
	public LoadRSSResponse loadRSSFeed(final String[] url) {
		logger.info("called loadRSSFeed with " + url[0]);
		return rssArticleManager.loadRSSFeed(url[0]);
	}

}
