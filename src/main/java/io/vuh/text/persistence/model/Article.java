package io.vuh.text.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Represents an Article
 *
 * @author Rene Loperena <rene@vuh.io>
 *
 */
@Entity
@Table(name = "Article")
@NamedQueries({ @NamedQuery(name = "Article.getAllArticles", query = "SELECT a FROM Article as a") })
public class Article implements Serializable {

	private static final long serialVersionUID = 1308923780846174471L;

	@Column(name = "id")
	@Id
	private String id;

	@Column(name = "title", columnDefinition = "TEXT")
	private String title;

	@Column(name = "source", columnDefinition = "TEXT")
	private String source;

	@Column(name = "text", columnDefinition = "TEXT")
	private String text;

	@Column(name = "date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@Column(name = "url", columnDefinition = "TEXT")
	private String url;

	public Date getDate() {
		return date;
	}

	public String getId() {
		return id;
	}

	public String getSource() {
		return source;
	}

	public String getText() {
		return text;
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}

	public void setDate(final Date value) {
		date = value;
	}

	public void setId(final String value) {
		id = value;
	}

	public void setSource(final String value) {
		source = value;
	}

	public void setText(final String value) {
		text = value;
	}

	public void setTitle(final String value) {
		title = value;
	}

	public void setUrl(final String value) {
		url = value;
	}

}
