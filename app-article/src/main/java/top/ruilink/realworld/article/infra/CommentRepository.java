package top.ruilink.realworld.article.infra;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import top.ruilink.realworld.article.domain.Comment;
import top.ruilink.realworld.article.domain.support.CommentProvider;
import top.ruilink.realworld.article.infra.mapper.CommentMapper;

@Repository
public class CommentRepository implements CommentProvider {
	private CommentMapper commentMapper;

	@Autowired
	public CommentRepository(CommentMapper commentMapper) {
		this.commentMapper = commentMapper;
	}

	@Override
	public Optional<Comment> save(Comment comment) {
		Long id = commentMapper.insert(comment);
		comment.setId(id);
		return Optional.ofNullable(comment);
	}

	@Override
	public Optional<Comment> findById(Long id) {
		return Optional.ofNullable(commentMapper.findById(id));
	}

	@Override
	public void remove(Comment comment) {
		commentMapper.delete(comment.getId());
	}

	@Override
	public List<Comment> findByArticle(Long articleId) {
		return commentMapper.findByArticle(articleId);
	}
}
