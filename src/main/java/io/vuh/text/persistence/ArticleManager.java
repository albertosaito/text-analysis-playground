package io.vuh.text.persistence;

import java.util.List;

import io.vuh.text.persistence.model.Article;
import rx.Observable;

/**
 * Interface to interact with {@link Article} persistence.
 * 
 * @author Rene Loperena <rene@vuh.io>
 */
public interface ArticleManager {
	/**
	 * Creates a new {@link Article} on the persistence layer.
	 * 
	 * @param article
	 *            to store in the persistence layer.
	 */
	void createArticle(Article article);

	/**
	 * Gets an specific {@link Article} based on its id from the persistence
	 * layer.
	 * 
	 * @param id
	 *            of the article we want to retrieve
	 * @return RxJava's {@link Article} Observable
	 */
	Observable<Article> getArticleById(String id);

	/**
	 * Gets all {@link Article}s stored in the persistence layer.
	 * 
	 * @return RxJava's {@link Article} Observable
	 */
	List<Article> getAllArticles() ;
}
