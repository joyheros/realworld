package top.ruilink.realworld.article.application.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import top.ruilink.realworld.article.application.data.ArticleData;
import top.ruilink.realworld.article.application.data.ArticleFacets;
import top.ruilink.realworld.article.application.data.ArticlePageSet;
import top.ruilink.realworld.article.application.data.ArticleParam;
import top.ruilink.realworld.article.application.data.Pagination;
import top.ruilink.realworld.article.application.data.UpdateArticleParam;
import top.ruilink.realworld.article.domain.Article;
import top.ruilink.realworld.article.domain.ArticleFavorite;
import top.ruilink.realworld.article.domain.support.ArticleFavoriteProvider;
import top.ruilink.realworld.article.domain.support.ArticleProvider;
import top.ruilink.realworld.auth.application.data.ProfileData;
import top.ruilink.realworld.auth.application.service.ProfileService;
import top.ruilink.realworld.auth.domain.User;

@ExtendWith(MockitoExtension.class)
class ArticleServiceImplTest {
    private ArticleServiceImpl articleService;
    @Mock
	private ArticleProvider articleProvider;
    @Mock
	private ArticleFavoriteProvider favoriteProvider;
    @Mock
	private ProfileService profileService;

	@BeforeEach
	void setUp() throws Exception {
		this.articleService = new ArticleServiceImpl(articleProvider, favoriteProvider, profileService);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testFindById() {
		when(articleProvider.findById(anyLong())).thenReturn(Optional.of(article()));
		when(favoriteProvider.find(any(), any())).thenReturn(Optional.of(favorive()));
		when(favoriteProvider.favoriteCount(any())).thenReturn(3L);
		when(profileService.getProfile(any(), anyLong())).thenReturn(profile());

		ArticleData actual = articleService.findById(userEntity(), anyLong());
        assertEquals(article().getBody(), actual.getBody());
        assertEquals(article().getSlug(), actual.getSlug());
	}

	@Test
	void testFindBySlug() {
		when(articleProvider.findBySlug(anyString())).thenReturn(Optional.of(article()));
		when(favoriteProvider.find(any(), any())).thenReturn(Optional.of(favorive()));
		when(favoriteProvider.favoriteCount(any())).thenReturn(3L);
		when(profileService.getProfile(any(), anyLong())).thenReturn(profile());

		ArticleData actual = articleService.findBySlug(userEntity(), anyString());
        assertEquals(article().getBody(), actual.getBody());
        assertEquals(article().getSlug(), actual.getSlug());
	}

	@Test
	void testCreateArticle() {
		when(articleProvider.save(any())).thenReturn(Optional.of(article()));
		when(favoriteProvider.find(any(), any())).thenReturn(Optional.of(favorive()));
		when(favoriteProvider.favoriteCount(any())).thenReturn(3L);
		when(profileService.getProfile(any(), anyLong())).thenReturn(profile());

		ArticleParam param = new ArticleParam("Article title1", "Article body1", "Article description1", tags());
		ArticleData actual = articleService.createArticle(userEntity(), param);
        assertEquals(article().getBody(), actual.getBody());
        assertEquals(article().getSlug(), actual.getSlug());
	}

	@Test
	void testUpdateArticle() {
		when(articleProvider.findBySlug(anyString())).thenReturn(Optional.of(article()));
		when(articleProvider.save(any())).thenReturn(Optional.of(newArticle()));
		when(favoriteProvider.find(any(), any())).thenReturn(Optional.of(favorive()));
		when(favoriteProvider.favoriteCount(any())).thenReturn(3L);
		when(profileService.getProfile(any(), anyLong())).thenReturn(profile());
		String slug = article().getSlug();
		UpdateArticleParam updateParam = new UpdateArticleParam("New title", "New body", "New description");
		ArticleData actual = articleService.updateArticle(userEntity(), slug, updateParam);
        assertEquals(newArticle().getBody(), actual.getBody());
        assertEquals(newArticle().getSlug(), actual.getSlug());
	}

	@Test
	void testDeleteArticle() {
		when(articleProvider.findBySlug(anyString())).thenReturn(Optional.of(article()));

		String slug = article().getSlug();
		articleService.deleteArticle(userEntity(), slug);
		verify(articleProvider, times(1)).remove(any(Article.class));
	}

	@Test
	void testFindByFacets() {
		when(articleProvider.findByFacets(anyString(), anyString(), anyString(), anyLong(), anyInt())).thenReturn(List.of(article()));
		when(articleProvider.countByFacets(anyString(), anyString(), anyString())).thenReturn(3L);
		when(favoriteProvider.find(any(), any())).thenReturn(Optional.of(favorive()));
		when(favoriteProvider.favoriteCount(any())).thenReturn(3L);
		when(profileService.getProfile(any(), anyLong())).thenReturn(profile());

		Pagination page = new Pagination(0, 20);
		ArticleFacets facets = new ArticleFacets("Java", "tester", "viewer", page);
		ArticlePageSet actual = articleService.findByFacets(userEntity(), facets);

        assertEquals(article().getBody(), actual.getArticles().get(0).getBody());
        assertEquals(article().getSlug(), actual.getArticles().get(0).getSlug());
        assertEquals(actual.getCount(), 3L);
	}

	@Test
	void testFindFeedArticles() {
		when(articleProvider.findFeedArticles(anyLong(), anyLong(), anyInt())).thenReturn(List.of(article()));
		when(articleProvider.countFeedArticles(anyLong())).thenReturn(2L);
		when(favoriteProvider.find(any(), any())).thenReturn(Optional.of(favorive()));
		when(favoriteProvider.favoriteCount(any())).thenReturn(3L);
		when(profileService.getProfile(any(), anyLong())).thenReturn(profile());

		Pagination page = new Pagination(0, 20);
		ArticlePageSet actual = articleService.findFeedArticles(userEntity(), page);
        assertEquals(article().getBody(), actual.getArticles().get(0).getBody());
        assertEquals(article().getSlug(), actual.getArticles().get(0).getSlug());
        assertEquals(actual.getCount(), 2L);
	}

	@Test
	void testFavoriteArticle() {
		when(articleProvider.findBySlug(anyString())).thenReturn(Optional.of(article()));
		when(favoriteProvider.find(any(), any())).thenReturn(Optional.of(favorive()));
		when(favoriteProvider.favoriteCount(any())).thenReturn(1L);
		when(profileService.getProfile(any(), anyLong())).thenReturn(profile());

		String slug = article().getSlug();
		ArticleData actual = articleService.favoriteArticle(userEntity(), slug);
        assertEquals(article().getSlug(), actual.getSlug());
		assertTrue(actual.isFavorited());   
	}

	@Test
	void testUnfavoriteArticle() {
		when(articleProvider.findBySlug(anyString())).thenReturn(Optional.of(article()));
		when(favoriteProvider.find(any(), any())).thenReturn(Optional.empty());
		when(favoriteProvider.favoriteCount(any())).thenReturn(0L);
		when(profileService.getProfile(any(), anyLong())).thenReturn(profile());

		String slug = article().getSlug();
		ArticleData actual = articleService.favoriteArticle(userEntity(), slug);
        assertEquals(article().getSlug(), actual.getSlug());
		assertFalse(actual.isFavorited());
	}

	private Article article() {
		Article article = new Article("Article title", "Article description", "Article body", 1L, tags());
		article.setId(1L);
		return article;
	}
	private Article newArticle() {
		Article article = new Article("New title", "New description", "New body", 1L, tags());
		article.setId(1L);
		return article;
	}
	private Set<String> tags(){
		Set<String> tags = new HashSet<String>(2);
		tags.add("Java");
		tags.add("Junit");
		return tags;
	}
	private User userEntity() {
		User user = new User("tester@email.com", "tester", "Password1.", "user bio", "user image");
		user.setId(1L);
		return user;
	}

	private ProfileData profile() {
		return new ProfileData("tester", "user bio", "user image", true);
	}
	
	private ArticleFavorite favorive() {
		return new ArticleFavorite(1L, 1L);
	}
}
