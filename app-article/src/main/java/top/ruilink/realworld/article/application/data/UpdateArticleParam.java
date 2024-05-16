package top.ruilink.realworld.article.application.data;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("article")
public class UpdateArticleParam {
	private String title;
	private String body;
	private String description;
	
	public UpdateArticleParam(String title, String body, String description) {
		this.title = title;
		this.body = body;
		this.description = description;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

}
