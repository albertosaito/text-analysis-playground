package io.vuh.text.elasticsearch.implementation;

import javax.inject.Inject;

import io.vuh.text.elasticsearch.ElasticSearchManager;
import io.vuh.text.elasticsearch.ElasticSearchResource;

/**
 * Implements {@link ElasticSearchResource}
 * 
 * @author Rene Loperena <rene@vuh.io>
 *
 */
public class ElasticSearchResourceImpl implements ElasticSearchResource {

	@Inject
	private ElasticSearchManager elasticSearchManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.vuh.text.elasticsearch.ElasticSearchResource#pushArticleById(java.lang
	 * .String)
	 */
	@Override
	public void pushArticleById(String id) {
		elasticSearchManager.pushArticleById(id);
	}
	
	public void pushAllArticles() {
		elasticSearchManager.pushAllArticles();
	}
}
