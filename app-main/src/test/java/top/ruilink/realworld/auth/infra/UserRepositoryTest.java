package top.ruilink.realworld.auth.infra;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import top.ruilink.realworld.auth.domain.FollowRelation;
import top.ruilink.realworld.auth.domain.User;

@MybatisTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(UserRepository.class)
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepos;
	private User user;
	private FollowRelation follow;

	@BeforeEach
	void setUp() throws Exception {
		user = new User("tester@email.com", "tester", "Password1.", "", "");
		follow = new FollowRelation(1L, 2L);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@Rollback(value = false)
	void testSave() {
		Optional<User> existUser = userRepos.findByUsername("tester");
		if(existUser.isPresent()) {
			Long id = existUser.get().getId();
			userRepos.removeUser(id);
		}
		Optional<User> savedUser = userRepos.save(user);
		Assertions.assertNotNull(savedUser.get().getId());
	}

	@Test
	void testFindById() {
		Optional<User> existUser = userRepos.findByUsername("tester");
		Long id = existUser.get().getId();
		Optional<User> result = userRepos.findById(id);
		Assertions.assertEquals(result.get().getUsername(), user.getUsername());
	}

	@Test
	void testFindByUsername() {
		Optional<User> result = userRepos.findByUsername("tester");
		Assertions.assertEquals(result.get().getUsername(), user.getUsername());
	}

	@Test
	void testFindByEmail() {
		Optional<User> result = userRepos.findByEmail("tester@email.com");
		Assertions.assertEquals(result.get().getEmail(), user.getEmail());
	}

	@Test
	@Rollback(value = false)
	void testSaveRelation() {
		Optional<FollowRelation> existFollow = userRepos.findRelation(follow.getUserId(), follow.getTargetId());
		if(existFollow.isPresent()) {
			userRepos.removeRelation(follow);
		}
		userRepos.saveRelation(follow);
		Optional<FollowRelation> result = userRepos.findRelation(follow.getUserId(), follow.getTargetId());
		Assertions.assertTrue(result.isPresent());
	}

	@Test
	void testFindRelation() {
		Optional<FollowRelation> result = userRepos.findRelation(follow.getUserId(), follow.getTargetId());
		Assertions.assertTrue(result.isPresent());
	}

	@Test
	void testRemoveRelation() {
		userRepos.removeRelation(follow);
		Optional<FollowRelation> result = userRepos.findRelation(follow.getUserId(), follow.getTargetId());
		Assertions.assertTrue(result.isEmpty());
	}
}
