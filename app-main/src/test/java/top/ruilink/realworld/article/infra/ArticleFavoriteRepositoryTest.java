package top.ruilink.realworld.article.infra;

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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import top.ruilink.realworld.article.domain.ArticleFavorite;

@MybatisTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(ArticleFavoriteRepository.class)
class ArticleFavoriteRepositoryTest {
	@Autowired
	ArticleFavoriteRepository favoriteRepos;

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
		Optional<ArticleFavorite> existData = favoriteRepos.find(1L, 1L);
		if(existData.isPresent()) {
			favoriteRepos.remove(existData.get());
		}
		ArticleFavorite favorite = new ArticleFavorite(1L, 1L);
		Optional<ArticleFavorite> savedData = favoriteRepos.save(favorite);
		Assertions.assertTrue(savedData.isPresent());
	}

	@Test
	@Order(2)
	void testFind() {
		Optional<ArticleFavorite> existData = favoriteRepos.find(1L, 1L);
		Assertions.assertNotNull(existData);
	}

	@Test
	@Order(3)
	void testFavoriteCount() {
		Long count = favoriteRepos.favoriteCount(1L);
		Assertions.assertNotEquals(count, 0);
	}

	@Test
	@Order(4)
	void testRemove() {
		ArticleFavorite favorite = new ArticleFavorite(1L, 1L);
		favoriteRepos.remove(favorite);
		Optional<ArticleFavorite> existData = favoriteRepos.find(1L, 1L);
		Assertions.assertTrue(existData.isEmpty());
	}
}
