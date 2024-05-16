package top.ruilink.realworld.article.application.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import top.ruilink.realworld.article.application.data.CommentData;
import top.ruilink.realworld.article.application.data.CommentPageSet;
import top.ruilink.realworld.article.application.data.CommentParam;
import top.ruilink.realworld.article.domain.Article;
import top.ruilink.realworld.article.domain.Comment;
import top.ruilink.realworld.article.domain.support.ArticleProvider;
import top.ruilink.realworld.article.domain.support.CommentProvider;
import top.ruilink.realworld.auth.application.data.ProfileData;
import top.ruilink.realworld.auth.application.service.ProfileService;
import top.ruilink.realworld.auth.domain.User;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {
    private CommentServiceImpl commentService;
    @Mock
	private ArticleProvider articleProvider;
    @Mock
	private CommentProvider commentProvider;
    @Mock
	private ProfileService profileService;

	@BeforeEach
	void setUp() throws Exception {
		this.commentService = new CommentServiceImpl(commentProvider, articleProvider, profileService);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testCreateComment() {
		when(articleProvider.findBySlug(anyString())).thenReturn(Optional.of(article()));
		when(commentProvider.save(any())).thenReturn(Optional.of(comment()));
		when(profileService.getProfile(any(), anyLong())).thenReturn(profile());
		
		String slug = article().getSlug();
		CommentParam param = new CommentParam();
		param.setBody("Comment body");
		CommentData actual = commentService.createComment(userEntity(), slug, param);
        assertEquals(article().getId(), actual.getArticleId());
	}

	@Test
	void testGetArticleComments() {
		when(articleProvider.findBySlug(anyString())).thenReturn(Optional.of(article()));
		when(commentProvider.findByArticle(anyLong())).thenReturn(Lists.newArrayList(comment()));
		when(profileService.getProfile(any(), anyLong())).thenReturn(profile());
		
		String slug = article().getSlug();
		CommentPageSet actual = commentService.getArticleComments(userEntity(), slug);
        assertEquals(actual.getComments().size(), 1);
	}

	@Test
	void testDeleteComment() {
		when(commentProvider.findById(anyLong())).thenReturn(Optional.of(comment()));

		commentService.deleteComment(userEntity(), anyLong());
		verify(commentProvider, times(1)).remove(any(Comment.class));
	}

	private Article article() {
		Article article = new Article("Article title", "Article description", "Article body", 1L, tags());
		article.setId(1L);
		return article;
	}
	private Set<String> tags(){
		Set<String> tags = new HashSet<String>(2);
		tags.add("Java");
		tags.add("Junit");
		return tags;
	}
	private Comment comment() {
		Comment comment = new Comment("Comment body", 1L, 1L);
		comment.setId(1L);
		return comment;
	}

	private ProfileData profile() {
		return new ProfileData("tester", "user bio", "user image", true);
	}
	private User userEntity() {
		User user = new User("tester@email.com", "tester", "Password1.", "user bio", "user image");
		user.setId(1L);
		return user;
	}
}
