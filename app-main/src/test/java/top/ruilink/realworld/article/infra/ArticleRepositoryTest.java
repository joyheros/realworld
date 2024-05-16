package top.ruilink.realworld.article.infra;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import top.ruilink.realworld.article.domain.Article;

@MybatisTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(ArticleRepository.class)
class ArticleRepositoryTest {
	@Autowired
	ArticleRepository articleRepos;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@Rollback(value = false)
	@Order(1)
	void testSave() {
		Optional<Article> existData = articleRepos.findBySlug(article().getSlug());
		if(existData.isPresent()) {
			articleRepos.remove(existData.get());
		}
		Optional<Article> savedData = articleRepos.save(article());
		Assertions.assertNotNull(savedData.get().getId());
	}

	@Test
	void testFindById() {
		Optional<Article> existData = articleRepos.findBySlug(article().getSlug());
		Long id = existData.get().getId();
		Optional<Article> result = articleRepos.findById(id);
		Assertions.assertEquals(result.get().getTitle(), article().getTitle());
	}

	@Test
	void testFindBySlug() {
		Optional<Article> result = articleRepos.findBySlug(article().getSlug());
		Assertions.assertEquals(result.get().getTitle(), article().getTitle());
	}

	@Test
	void testFindByFacets() {
		List<Article> result = articleRepos.findByFacets("Java", "tester", "tester", 0, 20);
		Assertions.assertNotNull(result);
	}

	@Test
	void testCountByFacets() {
		Long result = articleRepos.countByFacets("Java", "tester", "tester");
		Assertions.assertNotNull(result);
	}

	@Test
	void testFindFeedArticles() {
		List<Article> result = articleRepos.findFeedArticles(21L, 0 ,20);
		Assertions.assertNotNull(result);
	}

	@Test
	void testCountFeedArticles() {
		Long result = articleRepos.countFeedArticles(21L);
		Assertions.assertNotNull(result);
	}

	@Test
	void testRemove() {
		Optional<Article> existData = articleRepos.findBySlug(article().getSlug());
		articleRepos.remove(existData.get());
		existData = articleRepos.findBySlug(article().getSlug());
		Assertions.assertTrue(existData.isEmpty());
	}

	private Article article() {
		Article article = new Article("Article title", "Article description", "Article body", 1L, tags());
		return article;
	}

	private Set<String> tags(){
		Set<String> tags = new HashSet<String>(2);
		tags.add("Java");
		tags.add("Junit");
		return tags;
	}
}
