package io.zhifou.realworld.article.domain.support;

import java.util.List;
import java.util.Optional;

import io.zhifou.realworld.article.domain.Comment;

public interface CommentProvider {
	Optional<Comment> save(Comment comment);

	Optional<Comment> findById(Long id);

	void remove(Comment comment);

	List<Comment> findByArticle(Long articleId);
}
