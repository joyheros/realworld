package top.ruilink.realworld.article.application.data;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import top.ruilink.realworld.auth.application.data.ProfileData;

import top.ruilink.realworld.article.domain.Comment;

@JsonRootName("comment")
public class CommentData {
	private Long id;
	private String body;
	@JsonIgnore
	private Long articleId;
	private LocalDateTime createdAt;

	@JsonProperty("author")
	private ProfileData profileData;

	public CommentData() {
	}

	public CommentData(Comment comment) {
		this.id = comment.getId();
		this.articleId = comment.getArticleId();
		this.body = comment.getBody();
		this.createdAt = comment.getCreatedAt();
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
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return the articleId
	 */
	public Long getArticleId() {
		return articleId;
	}

	/**
	 * @param articleId the articleId to set
	 */
	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	/**
	 * @return the createdAt
	 */
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the profileData
	 */
	public ProfileData getProfileData() {
		return profileData;
	}

	/**
	 * @param profileData the profileData to set
	 */
	public void setProfileData(ProfileData profileData) {
		this.profileData = profileData;
	}
}
