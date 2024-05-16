package top.ruilink.realworld.article.application;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import top.ruilink.realworld.article.application.data.ArticleData;
import top.ruilink.realworld.article.application.data.ArticleFacets;
import top.ruilink.realworld.article.application.data.ArticlePageSet;
import top.ruilink.realworld.article.application.data.ArticleParam;
import top.ruilink.realworld.article.application.data.Pagination;
import top.ruilink.realworld.article.application.data.UpdateArticleParam;
import top.ruilink.realworld.article.application.service.ArticleService;
import top.ruilink.realworld.auth.domain.User;
import jakarta.validation.Valid;

@RestController
public class ArticleApi {
	private final ArticleService articleService;

	@Autowired
	public ArticleApi(ArticleService articleService) {
		this.articleService = articleService;
	}

	@PostMapping("/api/articles")
	public ResponseEntity<?> createArticle(@AuthenticationPrincipal User me, @Valid @RequestBody ArticleParam param) {
		ArticleData article = articleService.createArticle(me, param);
		return ResponseEntity.status(HttpStatus.OK).body(articleResponse(article));
	}

	@PutMapping("/api/articles/{slug}")
	public ResponseEntity<?> updateArticle(@AuthenticationPrincipal User me, @PathVariable String slug,
			@Valid @RequestBody UpdateArticleParam param) {
		ArticleData article = articleService.updateArticle(me, slug, param);
		return ResponseEntity.status(HttpStatus.OK).body(articleResponse(article));
	}

	@DeleteMapping("/api/articles/{slug}")
	public ResponseEntity<?> deleteArticle(@AuthenticationPrincipal User me, @PathVariable String slug) {
		articleService.deleteArticle(me, slug);
		return ResponseEntity.status(HttpStatus.OK).body("Success to delete article.");
	}

	@GetMapping("/api/articles")
	public ResponseEntity<?> getArticles(@AuthenticationPrincipal User me,
			@RequestParam(value = "tag", required = false) String tag,
			@RequestParam(value = "author", required = false) String author,
			@RequestParam(value = "favorited", required = false) String favorited,
			@RequestParam(value = "offset", required = false, defaultValue = "0") long offset,
			@RequestParam(value = "limit", required = false, defaultValue = "20") int limit) {
		ArticleFacets facets = new ArticleFacets(tag, author, favorited, new Pagination(offset, limit));
		ArticlePageSet articles = articleService.findByFacets(me, facets);
		return ResponseEntity.status(HttpStatus.OK).body(articles);
	}

	@GetMapping("/api/articles/{slug}")
	public ResponseEntity<?> getArticle(@AuthenticationPrincipal User me, @PathVariable String slug) {
		ArticleData article = articleService.findBySlug(me, slug);
		return ResponseEntity.status(HttpStatus.OK).body(articleResponse(article));
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/api/articles/feed")
	public ResponseEntity<?> getFeedArticles(@AuthenticationPrincipal User me,
			@RequestParam(value = "offset", required = false, defaultValue = "0") long offset,
			@RequestParam(value = "limit", required = false, defaultValue = "20") int limit) {
		Pagination paging = new Pagination(offset, limit);
		ArticlePageSet articles = articleService.findFeedArticles(me, paging);
		return ResponseEntity.status(HttpStatus.OK).body(articles);
	}

	@PostMapping("/api/articles/{slug}/favorite")
	public ResponseEntity<?> favoriteArticle(@AuthenticationPrincipal User me, @PathVariable String slug) {
		ArticleData article = articleService.favoriteArticle(me, slug);
		return ResponseEntity.status(HttpStatus.OK).body(articleResponse(article));
	}

	@DeleteMapping("/api/articles/{slug}/favorite")
	public ResponseEntity<?> unfavoriteArticle(@AuthenticationPrincipal User me, @PathVariable String slug) {
		ArticleData article = articleService.unfavoriteArticle(me, slug);
		return ResponseEntity.status(HttpStatus.OK).body(articleResponse(article));
	}

	private Map<String, Object> articleResponse(ArticleData articleData) {
		return new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("article", articleData);
			}
		};
	}
}
