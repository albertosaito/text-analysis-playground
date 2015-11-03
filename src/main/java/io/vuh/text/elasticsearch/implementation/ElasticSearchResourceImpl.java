package io.vuh.text.elasticsearch.implementation;

import java.util.List;

import javax.inject.Inject;

import io.vuh.text.elasticsearch.ElasticSearchManager;
import io.vuh.text.elasticsearch.ElasticSearchResource;
import io.vuh.text.persistence.model.Article;

/**
 * Implements {@link ElasticSearchResource}
 *
 * @author Rene Loperena <rene@vuh.io>
 *
 */
public class ElasticSearchResourceImpl implements ElasticSearchResource {

	@Inject
	private ElasticSearchManager elasticSearchManager;


	@Override
	public List<Article> search(final String queryString) {
		return elasticSearchManager.search(queryString);
	}
}
