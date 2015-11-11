package io.vuh.text.rss.manager;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import io.vuh.text.cache.IgniteDelegate;
import io.vuh.text.persistence.model.Article;
import io.vuh.text.rss.RSSArticleReader;
import io.vuh.text.rss.model.RSSFeedList;
import io.vuh.text.rss.resource.transport.LoadRSSResponse;
import rx.Observable;

/**
 * @author asaito
 *
 */
public class RSSArticleManagerImpl implements RSSArticleManager {

	@Inject
	private RSSArticleReader rssArticleReader;

	@Inject
	private Logger log;

	@Inject
	private Event<Article> articleEvent;
	
	@Inject 
	private IgniteDelegate igniteDelegate;
	
	private final Gson gson = new Gson();
	
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * io.vuh.text.rss.manager.RSSArticleManager#loadRSSFeed(java.lang.String)
	 */
	@Override
	public LoadRSSResponse loadRSSFeed(final String url) {
		final LoadRSSResponse response = new LoadRSSResponse();

		try {
			log.info("In loadRSSFeed");
			final Observable<Article> results = rssArticleReader.loadArticles(url);
			
			results.toBlocking().forEach(article -> {
				igniteDelegate.saveArticle(article.getId(), gson.toJson(article));
				articleEvent.fire(article);
			});

			log.info("Load finished");
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		}

		return response;
	}
	
	/* (non-Javadoc)
	 * @see io.vuh.text.rss.manager.RSSArticleManager#loadScheduledRSSFeeds(io.vuh.text.rss.model.RSSFeedList)
	 */
	@Override
	public void loadScheduledRSSFeeds(@Observes RSSFeedList list) {
		System.out.println("Loading lists");
		list.getUrls().parallelStream().forEach(url -> loadRSSFeed(url));
	}

}
