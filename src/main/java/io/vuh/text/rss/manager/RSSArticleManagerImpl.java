package io.vuh.text.rss.manager;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import io.vuh.text.persistence.model.Article;
import io.vuh.text.rss.RSSArticleReader;
import io.vuh.text.rss.resource.transport.LoadRSSResponse;
import rx.Observable;

public class RSSArticleManagerImpl implements RSSArticleManager {

	@Inject
	private RSSArticleReader rssArticleReader;

	@Inject
	private Logger log;

	@Inject
	private Event<Article> articleEvent;
	
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
			//we need the time? or can be async to improve velocity?
			final long startTime = System.nanoTime();
			log.info("In loadRSSFeed");
			final Observable<Article> results = rssArticleReader.loadArticles(url);
			
			results.toBlocking().forEach(article -> {
				articleEvent.fire(article);
				response.setLoadedArticles(response.getLoadedArticles() + 1);
			});

			final long endTime = System.nanoTime() - startTime;
			final double timeElapsed = (double) endTime / 1000000000;
			log.info("Load finished in " + timeElapsed + " seconds");
			response.setTimeElapsed(timeElapsed);
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		}

		return response;
	}

}
