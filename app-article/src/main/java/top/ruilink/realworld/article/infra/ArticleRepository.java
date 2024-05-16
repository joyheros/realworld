package top.ruilink.realworld.article.infra;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import top.ruilink.realworld.article.domain.Article;
import top.ruilink.realworld.article.domain.Tag;
import top.ruilink.realworld.article.domain.support.ArticleProvider;
import top.ruilink.realworld.article.infra.mapper.ArticleMapper;
import top.ruilink.realworld.article.infra.mapper.TagMapper;

@Repository
public class ArticleRepository implements ArticleProvider {
	private ArticleMapper articleMapper;
	private final TagMapper tagMapper;

	public ArticleRepository(ArticleMapper articleMapper, TagMapper tagMapper) {
		this.articleMapper = articleMapper;
		this.tagMapper = tagMapper;
	}

	@Override
	@Transactional
	public Optional<Article> save(Article article) {
		if (article.getId() == null) {
			Long id = createNew(article);
			article.setId(id);
		} else {
			articleMapper.update(article);
		}
		return Optional.ofNullable(article);
	}

	private Long createNew(Article article) {
		Long id = articleMapper.insert(article);
		for (Tag tag : article.getTags()) {
			Tag targetTag = Optional.ofNullable(tagMapper.findTag(tag.getName())).orElseGet(() -> {
				Long tagId = tagMapper.insertTag(tag);
				tag.setId(tagId);
				return tag;
			});
			articleMapper.insertArticleTag(id, targetTag.getId());
		}
		return id;
	}

	@Override
	public Optional<Article> findById(Long id) {
		return Optional.ofNullable(articleMapper.findById(id));
	}

	@Override
	public Optional<Article> findBySlug(String slug) {
		return Optional.ofNullable(articleMapper.findBySlug(slug));
	}

	@Override
	public void remove(Article article) {
		articleMapper.delete(article.getId());
	}

	@Override
	public List<Article> findByFacets(String tag, String author, String favorited, long offset, int limit) {
		return articleMapper.findByFacets(tag, author, favorited, offset, limit);
	}

	@Override
	public long countByFacets(String tag, String author, String favorited) {
		return articleMapper.countByFacets(tag, author, favorited);
	}

	@Override
	public List<Article> findFeedArticles(Long authorId, long offset, int limit) {
		return articleMapper.findByAuthor(authorId, offset, limit);
	}

	@Override
	public long countFeedArticles(Long authorId) {
		return articleMapper.countByAuthor(authorId);
	}
}
