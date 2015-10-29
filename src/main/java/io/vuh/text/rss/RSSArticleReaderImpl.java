package io.vuh.text.rss;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import io.vuh.text.persistence.model.Article;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author nobuji.saito
 *
 */
public class RSSArticleReaderImpl implements RSSArticleReader {

	// @Inject
	private final NewsContentScrapper newsContentScrapper = new NewsContentScrapperDefaultImpl();

	@Inject
	private Logger logger;

	private Observable<Article> createArticle(final SyndEntry s, final String source) {
		final Article result = new Article();
		result.setDate(s.getPublishedDate());
		result.setId(Integer.toString(s.getUri().hashCode()));
		result.setSource(source);
		try {
			newsContentScrapper.getNewsContent(s.getLink()).subscribe(text -> result.setText(text));
		} catch (final IOException ioe) {
			ioe.printStackTrace();
			throw new RuntimeException();
		}
		result.setTitle(s.getTitle());
		result.setUrl(s.getLink());
		return Observable.just(result);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see io.vuh.text.rss.RSSArticleReader#loadArticles()
	 */
	@Override
	public Observable<Article> loadArticles(final String rssFeedUrl) throws MalformedURLException {
		// logger.debug("Called loadArticles with " + rssFeedUrl);

		final URL url = new URL(rssFeedUrl);

		final SyndFeedInput input = new SyndFeedInput();
		try {
			final SyndFeed feed = input.build(new XmlReader(url));
			return Observable.from(feed.getEntries()).flatMap(entry -> {
				return Observable.defer(() -> {

					return createArticle(entry, feed.getDescription());
				}).subscribeOn(Schedulers.newThread());
			});
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
