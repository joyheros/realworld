package top.ruilink.realworld.article.domain.support;

import java.util.List;
import java.util.Optional;

import top.ruilink.realworld.article.domain.Article;

public interface ArticleProvider {

	Optional<Article> save(Article article);

	Optional<Article> findById(Long id);

	Optional<Article> findBySlug(String slug);

	void remove(Article article);

	List<Article> findByFacets(String tag, String author, String favorited, long offset, int limit);

	long countByFacets(String tag, String author, String favorited);

	List<Article> findFeedArticles(Long authorId, long offset, int limit);

	long countFeedArticles(Long authorId);
}
