package top.ruilink.realworld.auth.security.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JwtTokenServiceImplTest {
	JwtTokenServiceImpl jwtTokenService;

	@BeforeEach
	void setUp() throws Exception {
		jwtTokenService = new JwtTokenServiceImpl("SignKey0123456789012345678901234567890123456789", 3000);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGenerateToken() {
		String token = jwtTokenService.generateToken("tester@mail.com");
		assertNotNull(token);
	}

	@Test
	void testGetUsernameFromToken() {
		String token = jwtTokenService.generateToken("tester@mail.com");
		assertNotNull(token);
		String actual = jwtTokenService.getUsernameFromToken(token);
		assertNotNull(actual);
		assertEquals(actual, "tester@mail.com");
	}

	@Test
	void testVerifyJwtToken() {
		String token = jwtTokenService.generateToken("tester@mail.com");
		boolean valid = jwtTokenService.verifyJwtToken(token);
		assertTrue(valid);
	}
}
