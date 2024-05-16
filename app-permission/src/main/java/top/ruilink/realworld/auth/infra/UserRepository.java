package top.ruilink.realworld.auth.infra;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import top.ruilink.realworld.auth.domain.FollowRelation;
import top.ruilink.realworld.auth.domain.User;
import top.ruilink.realworld.auth.domain.support.UserProvider;
import top.ruilink.realworld.auth.infra.mapper.UserMapper;

@Repository
public class UserRepository implements UserProvider {
	private final UserMapper userMapper;

	@Autowired
	public UserRepository(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override
	public Optional<User> save(User user) {
		if (userMapper.findById(user.getId()) == null) {
			Long id = userMapper.insert(user);
			user.setId(id);
		} else {
			userMapper.update(user);
		}
		return Optional.ofNullable(user);
	}

	@Override
	public Optional<User> findById(Long id) {
		return Optional.ofNullable(userMapper.findById(id));
	}

	@Override
	public Optional<User> findByUsername(String username) {
		return Optional.ofNullable(userMapper.findByUsername(username));
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return Optional.ofNullable(userMapper.findByEmail(email));
	}

	public void removeUser(Long id) {
		userMapper.delete(id);
	}
	
	@Override
	public void saveRelation(FollowRelation followRelation) {
		if (!findRelation(followRelation.getUserId(), followRelation.getTargetId()).isPresent()) {
			userMapper.saveRelation(followRelation);
		}
	}

	@Override
	public Optional<FollowRelation> findRelation(Long userId, Long targetId) {
		return Optional.ofNullable(userMapper.findRelation(userId, targetId));
	}

	@Override
	public void removeRelation(FollowRelation followRelation) {
		userMapper.deleteRelation(followRelation);
	}
}
