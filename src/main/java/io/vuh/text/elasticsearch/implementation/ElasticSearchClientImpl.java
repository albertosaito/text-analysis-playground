package io.vuh.text.elasticsearch.implementation;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;

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
    public void postArticle(final Article article) {
	try {
	    logger.info("postArticle article " + article.getId());
	    final byte[] json = objectMapper.writeValueAsBytes(article);
	    client.prepareIndex("articles", "article", article.getId()).setSource(json).execute().actionGet();
	} catch (final JsonProcessingException e) {
	    logger.error(e);
	}

    }

    @Override
    public SearchHits search(final String queryString) {

	logger.info("PARAM: " + queryString);

	final SearchRequestBuilder builder = client.prepareSearch("articles").setTypes("article")
		.setQuery(QueryBuilders.boolQuery().must(QueryBuilders.simpleQueryStringQuery(queryString))); // Query
	logger.info("QUERY: " + builder.toString());
	final SearchResponse response = builder.execute().actionGet();

	logger.info("HITS: " + response.getHits().hits().length);
	return response.getHits();
    }
}
