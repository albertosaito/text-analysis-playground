package io.vuh.text.rss;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import rx.Observable;

/**
 * @author nobuji.saito
 *
 */
public class NewsContentScrapperDefaultImpl implements NewsContentScrapper {

	/*
	 * (non-Javadoc)
	 *
	 * @see io.vuh.text.rss.NewsContentScrapper#getNewsContent(java.lang.String)
	 */
	@Override
	public Observable<String> getNewsContent(final String url) throws IOException {
		final Document doc = Jsoup.connect(url).get();
		return Observable.just(Jsoup.parse(doc.text()).text());
	}

}
