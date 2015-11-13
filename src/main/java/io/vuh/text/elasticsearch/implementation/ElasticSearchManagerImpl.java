package io.vuh.text.elasticsearch.implementation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import io.vuh.text.elasticsearch.ElasticSearchClient;
import io.vuh.text.elasticsearch.ElasticSearchManager;
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
	private ElasticSearchClient client;

	public void eventHandler(@Observes Article article) {
		logger.info("Invoking article event with id " + article.getId());
		client.postArticle(article);
	}


	@Override
	public List<Article> search(final String queryString) {
		final SearchHits results = client.search(queryString);
		final List<Article> list = new ArrayList<>();
		results.forEach(hit -> list.add(createArticleFromHit(hit)));
		return list;
	}

	private Article createArticleFromHit(final SearchHit hit) {
		final Article article = new Article();
		article.setId(hit.getSource().get("id").toString());
		article.setTitle(hit.getSource().get("title").toString());
		article.setUrl(hit.getSource().get("url").toString());
		article.setSource(hit.getSource().get("source").toString());
		// validate value has more than 250 chars		
		article.setText(hit.getSource().get("text").toString().trim().substring(0, 250));
		// validate value exists and is long value
		article.setDate(new Date(Long.valueOf(hit.getSource().get("date").toString())));
		return article;
	}
}
