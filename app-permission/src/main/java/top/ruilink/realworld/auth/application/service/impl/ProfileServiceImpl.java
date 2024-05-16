package top.ruilink.realworld.auth.application.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import top.ruilink.realworld.exception.ResourceNotFoundException;

import top.ruilink.realworld.auth.application.data.ProfileData;
import top.ruilink.realworld.auth.application.service.ProfileService;
import top.ruilink.realworld.auth.domain.FollowRelation;
import top.ruilink.realworld.auth.domain.User;
import top.ruilink.realworld.auth.domain.support.UserProvider;

@Service
public class ProfileServiceImpl implements ProfileService {
	private final UserProvider userProvider;

	public ProfileServiceImpl(UserProvider userProvider) {
		this.userProvider = userProvider;
	}

	@Override
	@Transactional(readOnly = true)
	public ProfileData getProfile(User me, Long userId) {
		Optional<User> entity = userProvider.findById(userId);
		if (entity.isEmpty()) {
			throw new ResourceNotFoundException("User with id [" + userId + "] not found !");
		} else {
			User user = entity.get();
			if (me == null) {
				return new ProfileData(user, false);
			}
			Optional<FollowRelation> following = userProvider.findRelation(me.getId(), user.getId());
			if (following.isPresent()) {
				return new ProfileData(user, true);
			} else {
				return new ProfileData(user, false);
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ProfileData getProfile(User me, String username) {
		Optional<User> entity = userProvider.findByUsername(username);
		if (entity.isEmpty()) {
			throw new ResourceNotFoundException("User with username [" + username + "] not found !");
		} else {
			User user = entity.get();
			if (me == null) {
				return new ProfileData(user, false);
			}
			Optional<FollowRelation> following = userProvider.findRelation(me.getId(), user.getId());
			if (following.isPresent()) {
				return new ProfileData(user, true);
			} else {
				return new ProfileData(user, false);
			}
		}
	}

	@Override
	@Transactional
	public ProfileData follow(User me, String username) {
		Optional<User> entity = userProvider.findByUsername(username);
		if (entity.isEmpty()) {
			throw new ResourceNotFoundException("User with username [" + username + "] not found !");
		} else {
			User user = entity.get();
			Optional<FollowRelation> following = userProvider.findRelation(me.getId(), user.getId());
			if (following.isEmpty()) {
				userProvider.saveRelation(new FollowRelation(me.getId(), user.getId()));
			}
			return ProfileData.following(user);
		}
	}

	@Override
	@Transactional
	public ProfileData unfollow(User me, String username) {
		Optional<User> entity = userProvider.findByUsername(username);
		if (entity.isEmpty()) {
			throw new ResourceNotFoundException("User with username [" + username + "] not found !");
		} else {
			User user = entity.get();
			Optional<FollowRelation> following = userProvider.findRelation(me.getId(), user.getId());
			if (following.isPresent()) {
				userProvider.removeRelation(new FollowRelation(me.getId(), user.getId()));
			}
			return ProfileData.unfollowing(user);
		}
	}
}
