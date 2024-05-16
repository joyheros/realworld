package top.ruilink.realworld.article.infra;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import top.ruilink.realworld.article.domain.Comment;

@MybatisTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(CommentRepository.class)
class CommentRepositoryTest {
	@Autowired
	CommentRepository commentRepos;
	private Comment comment;
	
	@BeforeEach
	void setUp() throws Exception {
		Comment newComment = new Comment("Comment body", 1L, 1L);
		Optional<Comment> savedData = commentRepos.save(newComment);
		this.comment = savedData.get();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@Order(1)
	void testSave() {
		Comment newComment = new Comment("Comment body", 1L, 1L);
		Optional<Comment> savedData = commentRepos.save(newComment);
		Assertions.assertTrue(savedData.isPresent());
	}

	@Test
	@Order(2)
	void testFindById() {
		Optional<Comment> result = commentRepos.findById(comment.getId());
		Assertions.assertNotNull(result);
	}

	@Test
	@Order(3)
	void testFindByArticle() {
		List<Comment> comments = commentRepos.findByArticle(1L);
		Assertions.assertNotNull(comments);
	}

	@Test
	@Order(4)
	void testRemove() {
		commentRepos.remove(comment);
		Optional<Comment> result = commentRepos.findById(comment.getId());
		Assertions.assertTrue(result.isEmpty());
	}
}
