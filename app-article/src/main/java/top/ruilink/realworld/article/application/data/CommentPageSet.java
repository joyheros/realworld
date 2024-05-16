package top.ruilink.realworld.article.application.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommentPageSet {
	@JsonProperty("comments")
	private final List<CommentData> comments;

	public CommentPageSet(List<CommentData> comments) {
		this.comments = comments;
	}

	/**
	 * @return the comments
	 */
	public List<CommentData> getComments() {
		return comments;
	}
}
