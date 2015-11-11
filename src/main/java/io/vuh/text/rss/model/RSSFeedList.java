package io.vuh.text.rss.model;

import java.util.ArrayList;
import java.util.List;

public class RSSFeedList {
	
	private List<String> urls;

	/**
	 * @return the urls
	 */
	public List<String> getUrls() {
		return urls;
	}

	/**
	 * @param urls the param to set
	 */
	public void setUrls(List<String> param) {
		this.urls = param;
	}
	
	public RSSFeedList() {
		super();
		this.urls = new ArrayList<>();
		urls.add("http://www.chicagotribune.com/rss2.0.xml");
		
	}
	

}
