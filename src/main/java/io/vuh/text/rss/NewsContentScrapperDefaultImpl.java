package io.vuh.text.rss;

import java.io.IOException;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import de.l3s.boilerpipe.extractors.ArticleExtractor;
import rx.Observable;;

/**
 * @author nobuji.saito
 *
 */
public class NewsContentScrapperDefaultImpl implements NewsContentScrapper {

	@Inject
	private Logger logger;
	
	/*
	 * (non-Javadoc)
	 *
	 * @see io.vuh.text.rss.NewsContentScrapper#getNewsContent(java.lang.String)
	 */
	@Override
	public Observable<String> getNewsContent(final String url) throws IOException {
		try {
			final Document doc = Jsoup.connect(url).get();
			return  Observable.just(ArticleExtractor.INSTANCE.getText(doc.html()));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
}
