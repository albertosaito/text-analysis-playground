package io.vuh.text.rss.manager;

import java.net.MalformedURLException;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import io.vuh.text.persistence.ArticleManager;
import io.vuh.text.persistence.model.Article;
import io.vuh.text.rss.RSSArticleReader;
import io.vuh.text.rss.resource.transport.LoadRSSResponse;
import rx.Observable;

public class RSSArticleManagerImpl implements RSSArticleManager {

    @Inject
    private RSSArticleReader rssArticleReader;

    @Inject
    private ArticleManager articleManager;

    @Inject
    private Logger logger;

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
	    final long startTime = System.nanoTime();
	    logger.info("In loadRSSFeed");
	    final Observable<Article> results = rssArticleReader.loadArticles(url);

	    results.toBlocking().forEach(article -> {
		articleManager.createArticle(article);
		System.out.println(article.getText());
		response.setLoadedArticles(response.getLoadedArticles() + 1);
	    });

	    final long endTime = System.nanoTime() - startTime;
	    final double timeElapsed = (double) endTime / 1000000000;
	    System.out.println("Load finished in " + timeElapsed + " seconds");
	    response.setTimeElapsed(timeElapsed);
	} catch (final MalformedURLException e) {
	    e.printStackTrace();
	}

	return response;

    }

}
