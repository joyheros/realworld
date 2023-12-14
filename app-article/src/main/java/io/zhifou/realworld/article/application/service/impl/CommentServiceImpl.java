package io.zhifou.realworld.article.application.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.zhifou.realworld.auth.application.data.ProfileData;
import io.zhifou.realworld.auth.application.service.ProfileService;
import io.zhifou.realworld.auth.domain.User;
import io.zhifou.realworld.exception.ResourceNotFoundException;

import io.zhifou.realworld.article.application.data.CommentData;
import io.zhifou.realworld.article.application.data.CommentPageSet;
import io.zhifou.realworld.article.application.data.CommentParam;
import io.zhifou.realworld.article.application.service.CommentService;
import io.zhifou.realworld.article.domain.Article;
import io.zhifou.realworld.article.domain.Comment;
import io.zhifou.realworld.article.domain.support.ArticleProvider;
import io.zhifou.realworld.article.domain.support.CommentProvider;

@Service
public class CommentServiceImpl implements CommentService {
	private final ArticleProvider articleProvider;
	private final CommentProvider commentProvider;
	private final ProfileService profileService;

	public CommentServiceImpl(CommentProvider commentProvider, ArticleProvider articleProvider,
			ProfileService profileService) {
		this.commentProvider = commentProvider;
		this.articleProvider = articleProvider;
		this.profileService = profileService;
	}

	@Override
	@Transactional
	public CommentData createComment(User me, String slug, CommentParam param) {
		if (me == null) {
			throw new IllegalArgumentException("Login first when create comment!");
		}
		Optional<Article> entity = articleProvider.findBySlug(slug);
		if (entity.isEmpty()) {
			throw new ResourceNotFoundException("Article with slug [" + slug + "] not found !");
		} else {
			Article article = entity.get();
			Comment newComment = new Comment(param.getBody(), me.getId(), article.getId());
			Optional<Comment> optional = commentProvider.save(newComment);
			if (optional.isPresent()) {
				CommentData result = new CommentData(optional.get());
				ProfileData profile = profileService.getProfile(me, article.getUserId());
				result.setProfileData(profile);
				return result;
			} else {
				throw new IllegalArgumentException("Fail to save comment!");
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public CommentPageSet getArticleComments(User me, String slug) {
		Optional<Article> entity = articleProvider.findBySlug(slug);
		if (entity.isEmpty()) {
			throw new ResourceNotFoundException("Article with slug [" + slug + "] not found !");
		} else {
			Article article = entity.get();
			List<Comment> comments = commentProvider.findByArticle(article.getId());
			List<CommentData> result = comments.stream().map(comment -> {
				CommentData data = new CommentData(comment);
				ProfileData profile = profileService.getProfile(me, article.getUserId());
				data.setProfileData(profile);
				return data;
			}).toList();
			return new CommentPageSet(result);
		}
	}

	@Override
	@Transactional
	public void deleteComment(User me, Long commentId) {
		Optional<Comment> entity = commentProvider.findById(commentId);
		if (entity.isPresent()) {
			Comment comment = entity.get();
			if (me == null || comment.getUserId() != me.getId()) {
				throw new IllegalArgumentException("You can't delete comments written by others.");
			}
			commentProvider.remove(comment);
		}
	}
}
