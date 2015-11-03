package io.vuh.text.elasticsearch;

import java.util.List;

import javax.enterprise.event.Observes;

import io.vuh.text.persistence.model.Article;

/**
 * Manages all the Article-ElasticSearch operations.
 *
 * @author Rene Loperena <rene@vuh.io>
 *
 */
public interface ElasticSearchManager {

    List<Article> search(String queryString);
    
    void eventHandler(@Observes Article article);
}
