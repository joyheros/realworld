package top.ruilink.realworld.article.application.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import top.ruilink.realworld.article.domain.support.TagProvider;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {
	private TagServiceImpl tagService;
	@Mock
	private TagProvider tagProvider;
	
	@BeforeEach
	void setUp() throws Exception {
		this.tagService = new TagServiceImpl(tagProvider);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testAllTags() {
		when(tagProvider.allTags()).thenReturn(Lists.newArrayList("Java", "Junit"));
		
		List<String> actual = tagService.allTags();
		assertEquals(actual.size(), 2);
	}

}
