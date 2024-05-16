package top.ruilink.realworld.article.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import top.ruilink.realworld.article.application.data.CommentData;
import top.ruilink.realworld.article.application.data.CommentPageSet;
import top.ruilink.realworld.article.application.data.CommentParam;
import top.ruilink.realworld.article.application.service.CommentService;
import top.ruilink.realworld.article.domain.Article;
import top.ruilink.realworld.auth.application.data.ProfileData;
import top.ruilink.realworld.auth.domain.User;
import top.ruilink.realworld.auth.security.JwtTokenFilter;
import top.ruilink.realworld.auth.security.JwtTokenService;
import top.ruilink.realworld.auth.security.MockAuthPrincipal;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(CommentApi.class)
class CommentApiTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private JwtTokenService jwtTokenService;
	@MockBean
	JwtTokenFilter jwtTokenFilter;
	@MockBean
	private CommentService commentService;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@MockAuthPrincipal
	void testCreateComment() throws Exception {
		String slug = Article.toSlug("Article title");
		CommentParam param = new CommentParam();
		param.setBody("Comment body");
		when(commentService.createComment(any(User.class), eq(slug), any(CommentParam.class))).thenReturn(comment());

		ResultActions result = mockMvc.perform(post("/api/articles/{slug}/comments", slug)
				.contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer MOCKED_TOKEN")
				.content(objectMapper.writeValueAsString(Map.of("comment", param))));

		result.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.comment.body").value(param.getBody())).andDo(print());
	}

	@Test
	@MockAuthPrincipal
	void testDeleteComment() throws Exception {
		String slug = Article.toSlug("Article title");
		ResultActions result = mockMvc.perform(delete("/api/articles/{slug}/comments/{id}", slug, 1L)
				.contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer MOCKED_TOKEN"));

		result.andExpect(status().isOk()).andDo(print());
	}

	@Test
	@MockAuthPrincipal
	void testGetComments() throws Exception {
		String slug = Article.toSlug("Article title");
		when(commentService.getArticleComments(any(User.class), eq(slug))).thenReturn(comments());

		ResultActions result = mockMvc
				.perform(get("/api/articles/{slug}/comments", slug).header("Authorization", "Bearer MOCKED_TOKEN"));

		result.andExpect(status().isOk()).andExpect(jsonPath("$.comments[0].body").value("Comment body"))
				.andDo(print());
	}

	private CommentData comment() {
		ProfileData profile = new ProfileData("tester", "bio", "image", false);
		CommentData data = new CommentData();
		data.setBody("Comment body");
		data.setCreatedAt(LocalDateTime.now());
		data.setArticleId(1L);
		data.setProfileData(profile);
		return data;
	}

	private CommentPageSet comments() {
		List<CommentData> data = Lists.newArrayList(comment());
		return new CommentPageSet(data);
	}
}
