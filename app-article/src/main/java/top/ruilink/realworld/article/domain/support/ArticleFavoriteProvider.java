package top.ruilink.realworld.article.domain.support;

import java.util.Optional;

import top.ruilink.realworld.article.domain.ArticleFavorite;

public interface ArticleFavoriteProvider {
	Optional<ArticleFavorite> save(ArticleFavorite articleFavorite);

	Optional<ArticleFavorite> find(Long articleId, Long userId);

	void remove(ArticleFavorite favorite);

	long favoriteCount(Long articleId);
}
