package top.ruilink.realworld.article.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArticleTest {
	private Article article;
	
	@BeforeEach
	void setUp() throws Exception {
		Set<String> tags = new HashSet<String>(2);
		tags.add("Java");
		tags.add("Junit");
		article = new Article("This is title", "description", "body", 1L, tags);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testUpdate() {
		article.update("new title", "new desc", "new body");
		assertEquals(article.getBody(), "new body");
	}

	@Test
	void testToSlug() {
		String slug = Article.toSlug("This is title");
		assertEquals(article.getSlug(), slug);
	}
}
