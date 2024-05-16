package top.ruilink.realworld.auth.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {
	private User user;

	@BeforeEach
	void setUp() throws Exception {
		user = new User("tester@mail.com", "tester", "password", "bio", "");
		user.setId(1L);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testDefImage() {
		assertNotEquals(user.getImage(), "");
	}

	@Test
	void testUpdate() {
		user.update("new@mail.com", "test", "pass", "update", "avatar");
		assertEquals(user.getUsername(), "test");
	}

	@Test
	void testEqualsObject() {
		User otherUser = new User("tester@mail.com", "tester", "password1", "user bio", "image path");
		otherUser.setId(1L);
		assertTrue(user.equals(otherUser));
	}

	@Test
	void testToString() {
		String str = user.toString();
		assertEquals(str, "User {id=1, username='tester', email='tester@mail.com'}");
	}

}
