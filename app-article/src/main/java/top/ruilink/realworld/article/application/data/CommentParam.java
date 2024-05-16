package top.ruilink.realworld.article.application.data;

import com.fasterxml.jackson.annotation.JsonRootName;

import jakarta.validation.constraints.NotBlank;

@JsonRootName("comment")
public class CommentParam {
	@NotBlank(message = "can't be empty")
	private String body;

	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}
}
