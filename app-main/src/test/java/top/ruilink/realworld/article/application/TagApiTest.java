package top.ruilink.realworld.article.application;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import top.ruilink.realworld.article.application.service.TagService;
import top.ruilink.realworld.auth.security.JwtTokenFilter;
import top.ruilink.realworld.auth.security.JwtTokenService;
import top.ruilink.realworld.auth.security.MockAuthPrincipal;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(TagApi.class)
class TagApiTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private JwtTokenService jwtTokenService;
	@MockBean
	JwtTokenFilter jwtTokenFilter;
	@MockBean
	private TagService tagService;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@MockAuthPrincipal
	void testGetTags() throws Exception {
		List<String> tags = Lists.newArrayList("Java", "Junit");
		when(tagService.allTags()).thenReturn(tags);

		ResultActions result = mockMvc.perform(get("/api/tags").header("Authorization", "Bearer MOCKED_TOKEN"));

		result.andExpect(status().isOk()).andDo(print());
	}

}
