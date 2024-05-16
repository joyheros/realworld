package top.ruilink.realworld.article.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Comment {
	private Long id;
	private String body;
	private Long userId;
	private Long articleId;
	private LocalDateTime createdAt;

	public Comment() {
	}

	public Comment(String body, Long userId, Long articleId) {
		this.body = body;
		this.userId = userId;
		this.articleId = articleId;
		this.createdAt = LocalDateTime.now();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @return the articleId
	 */
	public Long getArticleId() {
		return articleId;
	}

	/**
	 * @return the createdAt
	 */
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Comment other && Objects.equals(this.id, other.id)
				&& Objects.equals(this.body, other.body) && Objects.equals(this.createdAt, other.createdAt);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.body, this.createdAt);
	}

	@Override
	public String toString() {
		return "Tag {" + "id=" + id + ", body='" + body + ", createdAt='" + createdAt + "'}";
	}
}
