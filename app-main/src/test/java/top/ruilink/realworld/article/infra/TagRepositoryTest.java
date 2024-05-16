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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import top.ruilink.realworld.article.domain.Tag;

@MybatisTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(TagRepository.class)
class TagRepositoryTest {
	@Autowired
	TagRepository tagRepos;

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
		Optional<Tag> existData = tagRepos.findTag("Java");
		if(existData.isEmpty()) {
			Tag tag = new Tag("Java");
			Optional<Tag> savedData = tagRepos.save(tag);
			Assertions.assertNotNull(savedData.get());
		}
	}

	@Test
	void testAllTags() {
		List<String> tags = tagRepos.allTags();
		Assertions.assertNotNull(tags);
	}

	@Test
	void testFindTag() {
		Optional<Tag> existData = tagRepos.findTag("Java");
		Assertions.assertNotNull(existData.get());
	}
}
