package io.zhifou.realworld.article.application.service;

import io.zhifou.realworld.article.application.data.ArticleData;
import io.zhifou.realworld.article.application.data.ArticleFacets;
import io.zhifou.realworld.article.application.data.ArticlePageSet;
import io.zhifou.realworld.article.application.data.ArticleParam;
import io.zhifou.realworld.article.application.data.Pagination;
import io.zhifou.realworld.article.application.data.UpdateArticleParam;
import io.zhifou.realworld.auth.domain.User;

public interface ArticleService {

	ArticleData createArticle(User me, ArticleParam param);

	ArticleData updateArticle(User me, String slug, UpdateArticleParam param);

	void deleteArticle(User me, String slug);

	ArticleData findById(User me, Long id);

	ArticleData findBySlug(User me, String slug);

	ArticlePageSet findByFacets(User me, ArticleFacets facets);

	ArticlePageSet findFeedArticles(User me, Pagination pagination);

	ArticleData favoriteArticle(User me, String slug);

	ArticleData unfavoriteArticle(User me, String slug);
}
