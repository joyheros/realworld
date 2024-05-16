package top.ruilink.realworld.article.application.data;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonRootName;

import jakarta.validation.constraints.NotBlank;

@JsonRootName("article")
public class ArticleParam {
	@NotBlank(message = "can't be empty")
	private String title;

	@NotBlank(message = "can't be empty")
	private String description;

	@NotBlank(message = "can't be empty")
	private String body;

	private Set<String> tagList;

	public ArticleParam(String title, String body, String description, Set<String> tags) {
		this.title = title;
		this.body = body;
		this.description = description;
		this.tagList = tags;
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
	 * @return the tagList
	 */
	public Set<String> getTagList() {
		return tagList;
	}
}
