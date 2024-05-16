package top.ruilink.realworld.article.application;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import top.ruilink.realworld.article.application.data.CommentData;
import top.ruilink.realworld.article.application.data.CommentPageSet;
import top.ruilink.realworld.article.application.data.CommentParam;
import top.ruilink.realworld.article.application.service.CommentService;
import top.ruilink.realworld.auth.domain.User;
import jakarta.validation.Valid;

@RestController
public class CommentApi {
	private final CommentService commentService;

	@Autowired
	public CommentApi(CommentService commentService) {
		this.commentService = commentService;
	}

	@PostMapping("/api/articles/{slug}/comments")
	public ResponseEntity<?> createComment(@AuthenticationPrincipal User me, @PathVariable String slug,
			@Valid @RequestBody CommentParam param) {
		CommentData comment = commentService.createComment(me, slug, param);
		return ResponseEntity.status(HttpStatus.OK).body(commentResponse(comment));
	}

	@DeleteMapping("/api/articles/{slug}/comments/{id}")
	public ResponseEntity<?> deleteComment(@AuthenticationPrincipal User me, @PathVariable String slug,
			@PathVariable long id) {
		commentService.deleteComment(me, id);
		return ResponseEntity.status(HttpStatus.OK).body("Success to delete comment.");
	}

	@GetMapping("/api/articles/{slug}/comments")
	public ResponseEntity<?> getComments(@AuthenticationPrincipal User me, @PathVariable String slug) {
		CommentPageSet comments = commentService.getArticleComments(me, slug);
		return ResponseEntity.status(HttpStatus.OK).body(comments);
	}

	private Map<String, Object> commentResponse(CommentData commentData) {
		return new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("comment", commentData);
			}
		};
	}
}
