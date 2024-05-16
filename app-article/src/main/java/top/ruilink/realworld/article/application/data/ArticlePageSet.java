package top.ruilink.realworld.article.application.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ArticlePageSet {
	@JsonProperty("articles")
	private final List<ArticleData> articles;

	@JsonProperty("articlesCount")
	private final long count;

	public ArticlePageSet(List<ArticleData> articles, long count) {

		this.articles = articles;
		this.count = count;
	}

	/**
	 * @return the articles
	 */
	public List<ArticleData> getArticles() {
		return articles;
	}

	/**
	 * @return the count
	 */
	public long getCount() {
		return count;
	}
}
