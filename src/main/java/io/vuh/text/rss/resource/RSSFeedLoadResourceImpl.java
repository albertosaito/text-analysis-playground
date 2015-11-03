package io.vuh.text.rss.resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import io.vuh.text.rss.manager.RSSArticleManager;
import io.vuh.text.rss.resource.transport.LoadRSSResponse;
import rx.Observable;

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
	public List<LoadRSSResponse> loadRSSFeed(final String[] url) {
		if(url == null)throw new IllegalArgumentException("The url list is null");
		logger.info("Called loadRSSFeed with "+url.length+" elements");
		final List<LoadRSSResponse> responses = new ArrayList<>(url.length);
		//We can call this async to speed up the response
		Observable.from(url).toBlocking().forEach(u -> {responses.add(rssArticleManager.loadRSSFeed(u));});
		return responses;
	}

}
