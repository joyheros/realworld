package top.ruilink.realworld.article.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import top.ruilink.realworld.article.application.data.ArticleData;
import top.ruilink.realworld.article.application.data.ArticleFacets;
import top.ruilink.realworld.article.application.data.ArticlePageSet;
import top.ruilink.realworld.article.application.data.ArticleParam;
import top.ruilink.realworld.article.application.data.UpdateArticleParam;
import top.ruilink.realworld.article.application.service.ArticleService;
import top.ruilink.realworld.article.domain.Article;
import top.ruilink.realworld.auth.application.data.ProfileData;
import top.ruilink.realworld.auth.domain.User;
import top.ruilink.realworld.auth.security.JwtTokenFilter;
import top.ruilink.realworld.auth.security.JwtTokenService;
import top.ruilink.realworld.auth.security.MockAuthPrincipal;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ArticleApi.class)
class ArticleApiTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private JwtTokenService jwtTokenService;
	@MockBean
	JwtTokenFilter jwtTokenFilter;
	@MockBean
	private ArticleService articleService;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@MockAuthPrincipal
	void testCreateArticle() throws Exception {
		ArticleParam param = new ArticleParam("Article title", "Article body", "Article description", null);
		when(articleService.createArticle(any(User.class), any(ArticleParam.class))).thenReturn(articleData());

		ResultActions result = mockMvc.perform(post("/api/articles").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer MOCKED_TOKEN")
				.content(objectMapper.writeValueAsString(Map.of("article", param))));

		result.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.article.title").value(param.getTitle()))
				.andExpect(jsonPath("$.article.body").value(param.getBody())).andDo(print());

	}

	@Test
	@MockAuthPrincipal
	void testUpdateArticle() throws Exception {
		String slug = Article.toSlug("Article title");
		UpdateArticleParam updateParam = new UpdateArticleParam("New title", "New body", "New description");
		when(articleService.updateArticle(any(User.class), eq(slug), any(UpdateArticleParam.class)))
				.thenReturn(newArticleData());

		ResultActions result = mockMvc.perform(put("/api/articles/" + slug).contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer MOCKED_TOKEN")
				.content(objectMapper.writeValueAsString(Map.of("article", updateParam))));

		result.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.article.title").value(updateParam.getTitle()))
				.andExpect(jsonPath("$.article.body").value(updateParam.getBody())).andDo(print());
	}

	@Test
	@MockAuthPrincipal
	void testDeleteArticle() throws Exception {
		String slug = Article.toSlug("Article title");
		ResultActions result = mockMvc.perform(delete("/api/articles/{slug}", slug)
				.contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer MOCKED_TOKEN"));

		result.andExpect(status().isOk()).andDo(print());
	}

	@Test
	@MockAuthPrincipal
	void testGetArticles() throws Exception {
		when(articleService.findByFacets(any(User.class), any(ArticleFacets.class))).thenReturn(articles());

		ResultActions result = mockMvc.perform(get("/api/articles").param("author", "tester")
				.header("Authorization", "Bearer MOCKED_TOKEN"));

		result.andExpect(status().isOk()).andExpect(jsonPath("$.articlesCount").value(1))
				.andExpect(jsonPath("$.articles[0].author.username").value("tester"))
				.andExpect(jsonPath("$.articles[0].favoritesCount").value(0))
				.andExpect(jsonPath("$.articles[0].tagList[0]").value("Java")).andDo(print());
	}

	@Test
	@MockAuthPrincipal
	void testGetArticle() throws Exception {
		String slug = Article.toSlug("Article title");
		ArticleData article = articleData();
		when(articleService.findBySlug(any(User.class), eq(slug))).thenReturn(article);

		ResultActions result = mockMvc
				.perform(get("/api/articles/" + slug).header("Authorization", "Bearer MOCKED_TOKEN"));

		result.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.article.title").value(article.getTitle()))
				.andExpect(jsonPath("$.article.body").value(article.getBody())).andDo(print());
	}

	@Test
	@MockAuthPrincipal
	void testGetFeedArticles() throws Exception {
		when(articleService.findFeedArticles(any(User.class), any())).thenReturn(articles());

		ResultActions result = mockMvc.perform(get("/api/articles/feed")
			.header("Authorization", "Bearer MOCKED_TOKEN"));

		result.andExpect(status().isOk()).andExpect(jsonPath("$.articlesCount").value(1))
			.andExpect(jsonPath("$.articles[0].author.username").value("tester"))
			.andExpect(jsonPath("$.articles[0].favoritesCount").value(0))
			.andExpect(jsonPath("$.articles[0].tagList[0]").value("Java")).andDo(print());
	}

	@Test
	@MockAuthPrincipal
	void testFavoriteArticle() throws Exception {
		String slug = Article.toSlug("Article title");
		ArticleData article = articleData();
		article.setFavorited(true);
		when(articleService.favoriteArticle(any(User.class), eq(slug))).thenReturn(article);

		ResultActions result = mockMvc
			.perform(post("/api/articles/{slug}/favorite", slug).header("Authorization", "Bearer MOCKED_TOKEN"));

		result.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.article.title").value(article.getTitle()))
			.andExpect(jsonPath("$.article.favorited").value(true))
			.andExpect(jsonPath("$.article.body").value(article.getBody())).andDo(print());
	}

	@Test
	@MockAuthPrincipal
	void testUnfavoriteArticle() throws Exception {
		String slug = Article.toSlug("Article title");
		ArticleData article = articleData();
		article.setFavorited(false);
		when(articleService.unfavoriteArticle(any(User.class), eq(slug))).thenReturn(article);

		ResultActions result = mockMvc
			.perform(delete("/api/articles/{slug}/favorite", slug).header("Authorization", "Bearer MOCKED_TOKEN"));

		result.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.article.title").value(article.getTitle()))
			.andExpect(jsonPath("$.article.favorited").value(false))
			.andExpect(jsonPath("$.article.body").value(article.getBody())).andDo(print());
	}

	private ArticleData articleData() {
		ProfileData profile = new ProfileData("tester", "bio", "image", false);
		List<String> tags = Lists.newArrayList("Java");
		ArticleData data = new ArticleData();
		data.setBody("Article body");
		data.setCreatedAt(LocalDateTime.now());
		data.setDescription("Article description");
		data.setFavorited(false);
		data.setFavoritesCount(0);
		data.setTitle("Article title");
		data.setSlug(Article.toSlug("Article title"));
		data.setUpdatedAt(LocalDateTime.now());
		data.setTagList(tags);
		data.setProfileData(profile);
		return data;
	}

	private ArticleData newArticleData() {
		ArticleData data = articleData();
		data.setBody("New body");
		data.setDescription("New description");
		data.setTitle("New title");
		data.setUpdatedAt(LocalDateTime.now());
		return data;
	}

	private ArticlePageSet articles() {
		List<ArticleData> data = Lists.newArrayList(articleData());
		return new ArticlePageSet(data, 1);
	}
}
