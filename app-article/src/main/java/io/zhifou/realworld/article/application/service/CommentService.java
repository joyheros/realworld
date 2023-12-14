package io.zhifou.realworld.article.application.service;

import io.zhifou.realworld.auth.domain.User;

import io.zhifou.realworld.article.application.data.CommentData;
import io.zhifou.realworld.article.application.data.CommentPageSet;
import io.zhifou.realworld.article.application.data.CommentParam;

public interface CommentService {

	CommentData createComment(User me, String slug, CommentParam param);

	CommentPageSet getArticleComments(User me, String slug);

	void deleteComment(User me, Long commentId);
}
