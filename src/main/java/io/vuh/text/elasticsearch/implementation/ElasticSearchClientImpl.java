package io.vuh.text.elasticsearch.implementation;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.elasticsearch.client.Client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.vuh.text.elasticsearch.ElasticSearchClient;
import io.vuh.text.persistence.model.Article;

/**
 * Implements {@link ElasticSearchClient}
 * 
 * Uses {@link ObjectMapper} to map the {@link Article} to a JSON object and
 * {@link Client} to communicate with the ElasticSearch instance.
 * 
 * @author Rene Loperena <rene@vuh.io>
 *
 */
public class ElasticSearchClientImpl implements ElasticSearchClient {

	@Inject
	private Logger logger;

	@Inject
	private ObjectMapper objectMapper;

	@Inject
	private Client client;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.vuh.text.elasticsearch.ElasticSearchClient#postArticle(io.vuh.text.
	 * model.Article)
	 */

	@Override
	public void postArticle(Article article) {
		try {
			logger.info("postArticle article "+article.getId());
			byte[] json = objectMapper.writeValueAsBytes(article);
			client.prepareIndex("articles", "article", article.getId()).setSource(json).execute().actionGet();
		} catch (JsonProcessingException e) {
			logger.error(e);
		}

	}
}
