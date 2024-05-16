/**
 * 
 */
package top.ruilink.realworld.auth.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import top.ruilink.realworld.auth.application.data.AuthUserData;
import top.ruilink.realworld.auth.application.data.LoginParam;
import top.ruilink.realworld.auth.application.data.RegisterParam;
import top.ruilink.realworld.auth.application.data.UpdateUserParam;
import top.ruilink.realworld.auth.application.data.UserData;
import top.ruilink.realworld.auth.application.service.UserService;
import top.ruilink.realworld.auth.domain.User;
import top.ruilink.realworld.auth.security.JwtTokenFilter;
import top.ruilink.realworld.auth.security.JwtTokenService;
import top.ruilink.realworld.auth.security.MockAuthPrincipal;

/**
 * 
 */
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UserApi.class)
class UserApiTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private UserService userService;
	@MockBean
	private JwtTokenService jwtTokenService;
	@MockBean
	JwtTokenFilter jwtTokenFilter;
	@MockBean
	AuthenticationManager authenticationManager;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link top.ruilink.realworld.auth.application.UserApi#register(top.ruilink.realworld.auth.application.data.RegisterParam)}.
	 */
	@Test
	void testRegister() throws Exception {
		// given
		RegisterParam param = new RegisterParam("tester@email.com", "tester", "Password1.");

		// when
		when(userService.register(any(RegisterParam.class))).thenReturn(authUser());
		ResultActions result = mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(Map.of("user", param))));

		// then
		result.andExpect(status().isCreated()).andExpect(jsonPath("$.user.email").value("tester@email.com"))
				.andExpect(jsonPath("$.user.username").value("tester")).andExpect(jsonPath("$.user.token").isNotEmpty())
				.andDo(print());
	}

	/**
	 * Test method for
	 * {@link top.ruilink.realworld.auth.application.UserApi#login(top.ruilink.realworld.auth.application.data.LoginParam)}.
	 */
	@Test
	void testLogin() throws Exception {
		LoginParam param = new LoginParam("tester@email.com", "Password1.");
		when(userService.login(any(LoginParam.class))).thenReturn(authUser());

		ResultActions result = mockMvc.perform(post("/api/users/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(Map.of("user", param))));

		result.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.user.email").value("tester@email.com"))
				.andExpect(jsonPath("$.user.username").value("tester")).andExpect(jsonPath("$.user.token").isNotEmpty())
				.andDo(print());
	}

	/**
	 * Test method for
	 * {@link top.ruilink.realworld.auth.application.UserApi#updateCurrentUser(top.ruilink.realworld.auth.application.data.UpdateUserParam, top.ruilink.realworld.auth.domain.User, java.lang.String)}.
	 */
	@Test
	@MockAuthPrincipal
	void testUpdateCurrentUser() throws Exception {
		UpdateUserParam param = new UpdateUserParam("newtest@email.com", "newtest", "Password2.", "new bio", "new image");
		when(userService.updateUser(any(User.class), any(UpdateUserParam.class))).thenReturn(userData());

		ResultActions result = mockMvc.perform(put("/api/user").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer MOCKED_TOKEN")
				.content(objectMapper.writeValueAsString(Map.of("user", param))));

		result.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.user.email").value(param.getEmail()))
				.andExpect(jsonPath("$.user.username").value(param.getUsername()))
				.andExpect(jsonPath("$.user.bio").value(param.getBio())).andDo(print());
	}

	/**
	 * Test method for
	 * {@link top.ruilink.realworld.auth.application.UserApi#currentUser(top.ruilink.realworld.auth.domain.User, java.lang.String)}.
	 */
	@Test
	@MockAuthPrincipal
	void testCurrentUser() throws Exception {
		ResultActions result = mockMvc.perform(get("/api/user").header("Authorization", "Bearer MOCKED_TOKEN"));

		result.andExpect(status().isOk()).andExpect(jsonPath("$.user.email").value("tester@email.com"))
			.andExpect(jsonPath("$.user.username").value("tester")).andDo(print());
	}

	private AuthUserData authUser() {
		return new AuthUserData("tester@email.com", "tester", "", "", "MOCKED_TOKEN");
	}

	private UserData userData() {
		return new UserData("newtest@email.com", "newtest", "new bio", "new image");
	}
}
