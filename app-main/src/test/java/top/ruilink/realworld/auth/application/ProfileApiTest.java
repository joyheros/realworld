package top.ruilink.realworld.auth.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import top.ruilink.realworld.auth.application.data.ProfileData;
import top.ruilink.realworld.auth.application.service.ProfileService;
import top.ruilink.realworld.auth.domain.User;
import top.ruilink.realworld.auth.security.JwtTokenFilter;
import top.ruilink.realworld.auth.security.JwtTokenService;
import top.ruilink.realworld.auth.security.MockAuthPrincipal;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ProfileApi.class)
class ProfileApiTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private JwtTokenService jwtTokenService;
	@MockBean
	JwtTokenFilter jwtTokenFilter;
	@MockBean
	private ProfileService profileService;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@MockAuthPrincipal
	void testFollow() throws Exception {
		String username = "tester";
		when(profileService.follow(any(User.class), eq(username))).thenReturn(profile());

		ResultActions result = mockMvc.perform(post("/api/profiles/{username}/follow", username));
		result.andExpect(status().isOk())
			.andExpect(jsonPath("$.profile", Matchers.notNullValue(ProfileData.class)))
			.andExpect(jsonPath("$.profile.following").value(true));
	}

	@Test
	@MockAuthPrincipal
	void testUnfollow() throws Exception {
		String username = "tester";
		ProfileData profile = new ProfileData("tester", "user bio", "user image", false);
		when(profileService.unfollow(any(User.class), eq(username))).thenReturn(profile);

		ResultActions result = mockMvc.perform(delete("/api/profiles/{username}/follow", username));
		result.andExpect(status().isOk())
			.andExpect(jsonPath("$.profile", Matchers.notNullValue(ProfileData.class)))
			.andExpect(jsonPath("$.profile.following").value(false));
	}

	@Test
	@MockAuthPrincipal
	void testGetProfile() throws Exception {
		String username = "tester";
		when(profileService.getProfile(any(User.class), eq(username))).thenReturn(profile());

		ResultActions result = mockMvc.perform(get("/api/profiles/{username}", username));
		result.andExpect(status().isOk())
			.andExpect(jsonPath("$.profile", Matchers.notNullValue(ProfileData.class)));
	}

	private ProfileData profile() {
		return new ProfileData("tester", "user bio", "user image", true);
	}
}
