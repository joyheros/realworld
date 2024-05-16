package top.ruilink.realworld.article.infra;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import top.ruilink.realworld.article.domain.ArticleFavorite;
import top.ruilink.realworld.article.domain.support.ArticleFavoriteProvider;
import top.ruilink.realworld.article.infra.mapper.ArticleFavoriteMapper;

@Repository
public class ArticleFavoriteRepository implements ArticleFavoriteProvider {
	private ArticleFavoriteMapper mapper;

	@Autowired
	public ArticleFavoriteRepository(ArticleFavoriteMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public Optional<ArticleFavorite> save(ArticleFavorite articleFavorite) {
		if (mapper.find(articleFavorite.getArticleId(), articleFavorite.getUserId()) == null) {
			mapper.insert(articleFavorite);
		}
		return Optional.ofNullable(articleFavorite);
	}

	@Override
	public Optional<ArticleFavorite> find(Long articleId, Long userId) {
		return Optional.ofNullable(mapper.find(articleId, userId));
	}

	@Override
	public void remove(ArticleFavorite favorite) {
		mapper.delete(favorite);
	}

	@Override
	public long favoriteCount(Long articleId) {
		return mapper.favoriteCount(articleId);
	}
}
