package top.ruilink.realworld.article.application.service;

import top.ruilink.realworld.auth.domain.User;

import top.ruilink.realworld.article.application.data.CommentData;
import top.ruilink.realworld.article.application.data.CommentPageSet;
import top.ruilink.realworld.article.application.data.CommentParam;

public interface CommentService {

	CommentData createComment(User me, String slug, CommentParam param);

	CommentPageSet getArticleComments(User me, String slug);

	void deleteComment(User me, Long commentId);
}
