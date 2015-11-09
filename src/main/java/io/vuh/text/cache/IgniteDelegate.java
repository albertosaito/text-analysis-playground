package io.vuh.text.cache;

public interface IgniteDelegate {

	String getArticle(String key);
	void saveArticle(String key, String articleJson);
}
