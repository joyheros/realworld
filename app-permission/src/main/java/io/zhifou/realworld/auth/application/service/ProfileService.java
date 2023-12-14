package io.zhifou.realworld.auth.application.service;

import io.zhifou.realworld.auth.application.data.ProfileData;
import io.zhifou.realworld.auth.domain.User;

public interface ProfileService {
	ProfileData getProfile(User me, Long userId);

	ProfileData getProfile(User me, String username);

	ProfileData follow(User me, String username);

	ProfileData unfollow(User me, String username);
}
