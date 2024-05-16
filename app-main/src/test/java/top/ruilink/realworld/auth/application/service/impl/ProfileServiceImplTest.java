package top.ruilink.realworld.auth.application.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import top.ruilink.realworld.auth.application.data.ProfileData;
import top.ruilink.realworld.auth.domain.FollowRelation;
import top.ruilink.realworld.auth.domain.User;
import top.ruilink.realworld.auth.domain.support.UserProvider;

@ExtendWith(MockitoExtension.class)
class ProfileServiceImplTest {
    private ProfileServiceImpl profileService;
    @Mock
	private UserProvider userProvider;

	@BeforeEach
	void setUp() throws Exception {
		this.profileService = new ProfileServiceImpl(userProvider);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetProfileUserLong() {
		when(userProvider.findById(anyLong())).thenReturn(Optional.of(userEntity()));
		when(userProvider.findRelation(any(), any())).thenReturn(Optional.of(follow()));

		ProfileData actual = profileService.getProfile(userEntity(), anyLong());
        assertEquals(userEntity().getUsername(), actual.username());
        assertEquals(userEntity().getBio(), actual.bio());
        assertTrue(actual.following());
	}

	@Test
	void testGetProfileUserString() {
		when(userProvider.findByUsername(anyString())).thenReturn(Optional.of(userEntity()));
		when(userProvider.findRelation(any(), any())).thenReturn(Optional.of(follow()));
		
		ProfileData actual = profileService.getProfile(userEntity(), anyString());
        assertEquals(userEntity().getUsername(), actual.username());
        assertEquals(userEntity().getBio(), actual.bio());
        assertTrue(actual.following());
	}

	@Test
	void testFollow() {
		when(userProvider.findByUsername(anyString())).thenReturn(Optional.of(userEntity()));
		when(userProvider.findRelation(any(), any())).thenReturn(Optional.empty());

		ProfileData actual = profileService.follow(userEntity(), anyString());
		assertTrue(actual.following());
	}

	@Test
	void testUnfollow() {
		when(userProvider.findByUsername(anyString())).thenReturn(Optional.of(userEntity()));
		when(userProvider.findRelation(any(), any())).thenReturn(Optional.of(follow()));

		ProfileData actual = profileService.unfollow(userEntity(), anyString());
		assertFalse(actual.following());
	}

	private User userEntity() {
		return new User("tester@email.com", "tester", "", "", "");
	}
	
	private FollowRelation follow() {
		return new FollowRelation(1L, 2L);
	}
}
