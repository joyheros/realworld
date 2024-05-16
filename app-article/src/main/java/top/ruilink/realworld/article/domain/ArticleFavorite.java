package top.ruilink.realworld.article.domain;

import java.util.Objects;

public class ArticleFavorite {
	private Long articleId;
	private Long userId;

	public ArticleFavorite() {
	}

	public ArticleFavorite(Long articleId, Long userId) {
		this.articleId = articleId;
		this.userId = userId;
	}

	/**
	 * @return the articleId
	 */
	public Long getArticleId() {
		return articleId;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof ArticleFavorite other && Objects.equals(this.articleId, other.articleId)
				&& Objects.equals(this.userId, other.userId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.articleId, this.userId);
	}

	@Override
	public String toString() {
		return "Tag {" + "articleId=" + articleId + ", userId=" + userId + "}";
	}
}
