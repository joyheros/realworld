package top.ruilink.realworld.auth.domain.support;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import top.ruilink.realworld.auth.domain.FollowRelation;
import top.ruilink.realworld.auth.domain.User;

@Repository
public interface UserProvider {
	Optional<User> save(User user);

	Optional<User> findById(Long id);

	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);

	void saveRelation(FollowRelation followRelation);

	Optional<FollowRelation> findRelation(Long userId, Long targetId);

	void removeRelation(FollowRelation followRelation);
}
