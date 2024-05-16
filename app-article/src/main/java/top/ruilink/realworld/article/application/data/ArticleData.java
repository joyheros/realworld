package top.ruilink.realworld.article.application.data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import top.ruilink.realworld.auth.application.data.ProfileData;

import top.ruilink.realworld.article.domain.Article;

@JsonRootName("article")
public class ArticleData {
	private Long id;
	private String slug;
	private String title;
	private String description;
	private String body;
	private boolean favorited;
	private long favoritesCount;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private List<String> tagList;
	@JsonProperty("author")
	private ProfileData profileData;

	public ArticleData() {
	}

	public ArticleData(Article article) {
		this.id = article.getId();
		this.slug = article.getSlug();
		this.title = article.getTitle();
		this.description = article.getDescription();
		this.body = article.getBody();
		this.tagList = article.getTags().stream().map(tag -> tag.getName()).collect(Collectors.toList());
		this.createdAt = article.getCreatedAt();
		this.updatedAt = article.getUpdatedAt();
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
	 * @return the slug
	 */
	public String getSlug() {
		return slug;
	}

	/**
	 * @param slug the slug to set
	 */
	public void setSlug(String slug) {
		this.slug = slug;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return the favorited
	 */
	public boolean isFavorited() {
		return favorited;
	}

	/**
	 * @param favorited the favorited to set
	 */
	public void setFavorited(boolean favorited) {
		this.favorited = favorited;
	}

	/**
	 * @return the favoritesCount
	 */
	public long getFavoritesCount() {
		return favoritesCount;
	}

	/**
	 * @param favoritesCount the favoritesCount to set
	 */
	public void setFavoritesCount(long favoritesCount) {
		this.favoritesCount = favoritesCount;
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
	 * @return the updatedAt
	 */
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @param updatedAt the updatedAt to set
	 */
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * @return the tagList
	 */
	public List<String> getTagList() {
		return tagList;
	}

	/**
	 * @param tagList the tagList to set
	 */
	public void setTagList(List<String> tagList) {
		this.tagList = tagList;
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
