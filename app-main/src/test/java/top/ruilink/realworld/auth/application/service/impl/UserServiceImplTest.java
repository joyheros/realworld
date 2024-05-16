package top.ruilink.realworld.auth.application.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import top.ruilink.realworld.auth.application.data.AuthUserData;
import top.ruilink.realworld.auth.application.data.LoginParam;
import top.ruilink.realworld.auth.application.data.RegisterParam;
import top.ruilink.realworld.auth.application.data.UpdateUserParam;
import top.ruilink.realworld.auth.application.data.UserData;
import top.ruilink.realworld.auth.domain.FollowRelation;
import top.ruilink.realworld.auth.domain.User;
import top.ruilink.realworld.auth.domain.support.UserProvider;
import top.ruilink.realworld.auth.security.impl.JwtTokenServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private UserServiceImpl userService;
    @Mock
	private UserProvider userProvider;
    @Mock
	private PasswordEncoder passwordEncoder;
    @Mock
	private JwtTokenServiceImpl jwtTokenService;
    @Mock
	private AuthenticationManager authenticationManager;

	@BeforeEach
	void setUp() throws Exception {
		this.userService = new UserServiceImpl(userProvider, passwordEncoder, jwtTokenService, authenticationManager);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testRegister() {
		RegisterParam param = new RegisterParam("tester@email.com", "tester", "Password1.");
        when(jwtTokenService.generateToken(anyString())).thenReturn("token.test.needed");
        when(passwordEncoder.encode(anyString())).thenReturn("a{testpasswordencodedstring}");
        when(userProvider.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userProvider.findByUsername(anyString())).thenReturn(Optional.empty());
        when(userProvider.save(any())).thenReturn(Optional.of(userEntity()));
        AuthUserData actual = userService.register(param);

        assertEquals(param.getEmail(), actual.email());
        assertEquals(param.getUsername(), actual.username());
        assertEquals("", actual.bio());
        assertNotNull(actual.image());
        assertNotNull(actual.token());
	}

	@Test
	void testRegister_duplicationUser() {
		RegisterParam param = new RegisterParam("tester@email.com", "tester", "Password1.");
        when(userProvider.findByEmail(anyString())).thenReturn(Optional.of(userEntity()));

        try {
            userService.register(param);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Email `%s` is already exists.".formatted(param.getEmail()), e.getMessage());
        } catch (Exception e) {
            fail();
        }
	}

	@Test
	void testLogin() {
		LoginParam param = new LoginParam("tester@email.com", "Password1.");
		Authentication auth = mock(Authentication.class);
		auth.setAuthenticated(true);
		when(auth.isAuthenticated()).thenReturn(true);
		when(authenticationManager.authenticate(any())).thenReturn(auth);
		when(jwtTokenService.generateToken(anyString())).thenReturn("token.test.needed");
		when(userProvider.findByEmail(anyString())).thenReturn(Optional.of(userEntity()));

		AuthUserData actual = userService.login(param);

        assertNotNull(actual.token());
        assertEquals(param.getEmail(), actual.email());
	}

    @Test
    void testLogin_errorEmail() {
    	LoginParam param = new LoginParam("test@email.com", "Password1.");
    	Authentication auth = mock(Authentication.class);
		auth.setAuthenticated(true);
		when(auth.isAuthenticated()).thenReturn(true);
		when(authenticationManager.authenticate(any())).thenReturn(auth);
    	when(userProvider.findByEmail(anyString())).thenReturn(Optional.empty());
        try {
            userService.login(param);
            fail();
        } catch (UsernameNotFoundException e) {
            assertEquals("User with email [" + param.getEmail() + "] not found !", e.getMessage());
        } catch (Exception e) {
            fail();
        }
    }
    
    @Test
    void testLogin_invalidUser() {
    	LoginParam param = new LoginParam("test@email.com", "ErrorPassword1.");
    	Authentication auth = mock(Authentication.class);
		auth.setAuthenticated(false);
		when(auth.isAuthenticated()).thenReturn(false);
		when(authenticationManager.authenticate(any())).thenReturn(auth);
        try {
            userService.login(param);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid email or password.", e.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

	@Test
	void testFindById() {
		when(userProvider.findById(anyLong())).thenReturn(Optional.of(userEntity()));
		UserData actual = userService.findById(anyLong());

        assertEquals("tester@email.com", actual.email());
        assertEquals("tester", actual.username());
        assertEquals("", actual.bio());
	}

	@Test
	void testFindByEmail() {
		when(userProvider.findByEmail(anyString())).thenReturn(Optional.of(userEntity()));
		UserData actual = userService.findByEmail(anyString());

        assertEquals("tester@email.com", actual.email());
        assertEquals("tester", actual.username());
        assertEquals("", actual.bio());
	}

	@Test
	void testFindByUsername() {
		when(userProvider.findByUsername(anyString())).thenReturn(Optional.of(userEntity()));
		UserData actual = userService.findByUsername(anyString());

        assertEquals("tester@email.com", actual.email());
        assertEquals("tester", actual.username());
        assertEquals("", actual.bio());
	}

	@Test
	void testUpdateUser() {
		UpdateUserParam param = new UpdateUserParam("newtest@email.com", "newtest", "Password2.", "new bio", "new image");
        User newUser = new User("newtest@email.com", "newtest", "Password2.", "new bio", "new image");
		when(passwordEncoder.encode(anyString())).thenReturn("a{testpasswordencodedstring}");
        when(userProvider.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userProvider.findByUsername(anyString())).thenReturn(Optional.empty());
        when(userProvider.save(any())).thenReturn(Optional.of(newUser));

        UserData actual = userService.updateUser(userEntity(), param);

        assertEquals(param.getUsername(), actual.username());
        assertEquals(param.getBio(), actual.bio());
	}

	@Test
	void testIsFollowing() {
		FollowRelation follow = new FollowRelation();
		when(userProvider.findRelation(anyLong(), anyLong())).thenReturn(Optional.of(follow));
		boolean actual = userService.isFollowing(anyLong(), anyLong());
        assertTrue(actual);
	}

	@Test
	void testIsFollowing_false() {
		when(userProvider.findRelation(anyLong(), anyLong())).thenReturn(Optional.empty());
		boolean actual = userService.isFollowing(anyLong(), anyLong());
        assertFalse(actual);
	}

	private User userEntity() {
		return new User("tester@email.com", "tester", "", "", "");
	}
}
