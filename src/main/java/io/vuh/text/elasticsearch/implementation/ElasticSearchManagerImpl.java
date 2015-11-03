package io.vuh.text.elasticsearch.implementation;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import io.vuh.text.elasticsearch.ElasticSearchClient;
import io.vuh.text.elasticsearch.ElasticSearchManager;
import io.vuh.text.persistence.ArticleManager;
import io.vuh.text.persistence.model.Article;

/**
 * Implements {@link ElasticSearchManager}
 * 
 * Uses {@link ArticleManager} to communicate with the persistence layer and
 * {@link ElasticSearchClient} to make the requests to ElasticSearch.
 * 
 * @author Rene Loperena <rene@vuh.io>
 *
 */
@Stateless
public class ElasticSearchManagerImpl implements ElasticSearchManager {

	@Inject
	private Logger logger;

	@Inject
	private ArticleManager articleManager;

	@Inject
	private ElasticSearchClient client;

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.vuh.text.elasticsearch.ElasticSearchController#pushAllArticles()
	 */
	@Override
	public void pushAllArticles() {
		logger.info("Calling pushAllArticles");
		final List<Article> articles = articleManager.getAllArticles();
		logger.info("Size: "+articles.size());
		articles.parallelStream().forEach(article -> client.postArticle(article));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.vuh.text.elasticsearch.ElasticSearchController#pushArticleById(java.
	 * lang.String)
	 */
	@Override
	public void pushArticleById(String id) {
		articleManager.getArticleById(id).subscribe(client::postArticle);
	}

}
