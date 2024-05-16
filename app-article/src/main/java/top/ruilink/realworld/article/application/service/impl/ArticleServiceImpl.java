package top.ruilink.realworld.article.application.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import top.ruilink.realworld.auth.application.data.ProfileData;
import top.ruilink.realworld.auth.application.service.ProfileService;
import top.ruilink.realworld.auth.domain.User;
import top.ruilink.realworld.exception.ResourceNotFoundException;

import top.ruilink.realworld.article.application.data.ArticleData;
import top.ruilink.realworld.article.application.data.ArticleFacets;
import top.ruilink.realworld.article.application.data.ArticlePageSet;
import top.ruilink.realworld.article.application.data.ArticleParam;
import top.ruilink.realworld.article.application.data.Pagination;
import top.ruilink.realworld.article.application.data.UpdateArticleParam;
import top.ruilink.realworld.article.application.service.ArticleService;
import top.ruilink.realworld.article.domain.Article;
import top.ruilink.realworld.article.domain.ArticleFavorite;
import top.ruilink.realworld.article.domain.support.ArticleFavoriteProvider;
import top.ruilink.realworld.article.domain.support.ArticleProvider;

@Service
public class ArticleServiceImpl implements ArticleService {
	private final ArticleProvider articleProvider;
	private final ArticleFavoriteProvider favoriteProvider;
	private final ProfileService profileService;

	@Autowired
	public ArticleServiceImpl(ArticleProvider articleProvider, ArticleFavoriteProvider favoriteProvider,
			ProfileService profileService) {
		this.articleProvider = articleProvider;
		this.favoriteProvider = favoriteProvider;
		this.profileService = profileService;
	}

	@Override
	@Transactional(readOnly = true)
	public ArticleData findById(User me, Long id) {
		Optional<Article> entity = articleProvider.findById(id);
		if (entity.isEmpty()) {
			throw new ResourceNotFoundException("Article with id [" + id + "] not found !");
		} else {
			Article article = entity.get();
			ArticleData result = new ArticleData(article);
			fillExtraInfo(article.getId(), article.getUserId(), me, result);
			return result;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ArticleData findBySlug(User me, String slug) {
		Optional<Article> entity = articleProvider.findBySlug(slug);
		if (entity.isEmpty()) {
			throw new ResourceNotFoundException("Article with slug [" + slug + "] not found !");
		} else {
			Article article = entity.get();
			ArticleData result = new ArticleData(article);
			fillExtraInfo(article.getId(), article.getUserId(), me, result);
			return result;
		}
	}

	@Override
	@Transactional
	public ArticleData createArticle(User me, ArticleParam param) {
		Article articleObj = new Article(param.getTitle(), param.getDescription(), param.getBody(), me.getId(),
				param.getTagList());
		Optional<Article> entity = articleProvider.save(articleObj);
		if (entity.isPresent()) {
			Article article = entity.get();
			ArticleData result = new ArticleData(article);
			fillExtraInfo(article.getId(), article.getUserId(), me, result);
			return result;
		} else {
			throw new ResourceNotFoundException("Fail to create article!");
		}
	}

	@Override
	@Transactional
	public ArticleData updateArticle(User me, String slug, UpdateArticleParam param) {
		Optional<Article> entity = articleProvider.findBySlug(slug);
		if (entity.isPresent()) {
			Article article = entity.get();
			article.update(param.getTitle(), param.getDescription(), param.getBody());
			Optional<Article> newObj = articleProvider.save(article);
			Article newArticle = newObj.get();
			ArticleData result = new ArticleData(newArticle);
			fillExtraInfo(newArticle.getId(), newArticle.getUserId(), me, result);
			return result;
		} else {
			throw new ResourceNotFoundException("Article with slug [" + slug + "] not found !");
		}
	}

	@Override
	@Transactional
	public void deleteArticle(User me, String slug) {
		Optional<Article> entity = articleProvider.findBySlug(slug);
		if (entity.isPresent()) {
			Article article = entity.get();
			if (article.getUserId() != me.getId()) {
				throw new IllegalArgumentException("You can't delete articles written by others.");
			}
			articleProvider.remove(article);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ArticlePageSet findByFacets(User me, ArticleFacets facets) {
		List<Article> articles = articleProvider.findByFacets(facets.getTag(), facets.getAuthor(),
				facets.getFavorited(), facets.getPagination().getOffset(), facets.getPagination().getLimit());
		long total = articleProvider.countByFacets(facets.getTag(), facets.getAuthor(), facets.getFavorited());
		if (articles.size() == 0) {
			return new ArticlePageSet(new ArrayList<>(), total);
		}
		List<ArticleData> records = articles.stream().map(article -> {
			ArticleData data = new ArticleData(article);
			fillExtraInfo(article.getId(), article.getUserId(), me, data);
			return data;
		}).toList();
		return new ArticlePageSet(records, total);
	}

	@Override
	@Transactional(readOnly = true)
	public ArticlePageSet findFeedArticles(User me, Pagination pagination) {
		if (me == null) {
			return new ArticlePageSet(new ArrayList<>(), 0);
		}
		List<Article> articles = articleProvider.findFeedArticles(me.getId(), pagination.getOffset(),
				pagination.getLimit());
		long total = articleProvider.countFeedArticles(me.getId());
		List<ArticleData> records = articles.stream().map(article -> {
			ArticleData data = new ArticleData(article);
			fillExtraInfo(article.getId(), article.getUserId(), me, data);
			return data;
		}).toList();
		return new ArticlePageSet(records, total);
	}

	@Override
	@Transactional
	public ArticleData favoriteArticle(User me, String slug) {
		Optional<Article> entity = articleProvider.findBySlug(slug);
		if (entity.isPresent()) {
			Article article = entity.get();
			if (me != null && favoriteProvider.find(article.getId(), me.getId()).isEmpty()) {
				favoriteProvider.save(new ArticleFavorite(article.getId(), me.getId()));
			}
			ArticleData result = new ArticleData(article);
			fillExtraInfo(article.getId(), article.getUserId(), me, result);
			return result;
		} else {
			throw new ResourceNotFoundException("Article with slug [" + slug + "] not found !");
		}
	}

	@Override
	@Transactional
	public ArticleData unfavoriteArticle(User me, String slug) {
		Optional<Article> entity = articleProvider.findBySlug(slug);
		if (entity.isPresent()) {
			Article article = entity.get();
			if (me != null && favoriteProvider.find(article.getId(), me.getId()).isPresent()) {
				favoriteProvider.remove(new ArticleFavorite(article.getId(), me.getId()));
			}
			ArticleData result = new ArticleData(article);
			fillExtraInfo(article.getId(), article.getUserId(), me, result);
			return result;
		} else {
			throw new ResourceNotFoundException("Article with slug [" + slug + "] not found !");
		}
	}

	private void fillExtraInfo(Long id, Long userId, User user, ArticleData articleData) {
		if (user == null) {
			articleData.setFavorited(false);
		} else {
			articleData.setFavorited(this.isFavorite(id, user.getId()));
		}
		articleData.setFavoritesCount(this.favoriteCount(id));
		ProfileData profile = profileService.getProfile(user, userId);
		articleData.setProfileData(profile);
	}

	private boolean isFavorite(Long articleId, Long userId) {
		Optional<ArticleFavorite> entity = favoriteProvider.find(articleId, userId);
		if (entity.isPresent()) {
			return true;
		}
		return false;
	}

	private long favoriteCount(Long articleId) {
		return favoriteProvider.favoriteCount(articleId);
	}
}
