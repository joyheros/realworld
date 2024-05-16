package top.ruilink.realworld.article.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Article {
	private Long id;
	private String slug;
	private String title;
	private String description;
	private String body;
	private Long userId;
	private Set<Tag> tags;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public Article() {
	}

	public Article(String title, String description, String body, Long userId, Set<String> tagList) {
		this(title, description, body, userId, LocalDateTime.now(), tagList);
	}

	public Article(String title, String description, String body, Long userId, LocalDateTime createdAt,
			Set<String> tagList) {
		this.slug = toSlug(title);
		this.title = title;
		this.description = description;
		this.body = body;
		this.tags = tagList.stream().map(Tag::new).collect(Collectors.toSet());
		this.userId = userId;
		this.createdAt = createdAt;
		this.updatedAt = createdAt;
	}

	public Article(String title, String description, String body, Long userId, LocalDateTime createdAt,
			LocalDateTime updatedAt, Set<Tag> tagList) {
		this.slug = toSlug(title);
		this.title = title;
		this.description = description;
		this.body = body;
		this.tags = tagList;
		this.userId = userId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
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
	 * @return the tags
	 */
	public Set<Tag> getTags() {
		return tags;
	}

	/**
	 * @return the createdAt
	 */
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	/**
	 * @return the updatedAt
	 */
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void update(String title, String description, String body) {
		if (!title.isBlank()) {
			this.title = title;
			this.slug = toSlug(title);
			this.updatedAt = LocalDateTime.now();
		}
		if (!description.isBlank()) {
			this.description = description;
			this.updatedAt = LocalDateTime.now();
		}
		if (!body.isBlank()) {
			this.body = body;
			this.updatedAt = LocalDateTime.now();
		}
	}

	public static String toSlug(String title) {
		return title.toLowerCase().replaceAll("[\\&|[\\uFE30-\\uFFA0]|\\’|\\”|\\s\\?\\,\\.]+", "-");
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Article other && Objects.equals(this.id, other.id)
				&& Objects.equals(this.slug, other.slug);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.slug);
	}

	@Override
	public String toString() {
		return "Tag {" + "id=" + id + ", title=" + title + ", slug='" + slug + "'}";
	}
}
