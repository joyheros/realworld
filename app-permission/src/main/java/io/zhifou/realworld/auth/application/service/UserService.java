package io.zhifou.realworld.auth.application.service;

import io.zhifou.realworld.auth.application.data.AuthUserData;
import io.zhifou.realworld.auth.application.data.LoginParam;
import io.zhifou.realworld.auth.application.data.RegisterParam;
import io.zhifou.realworld.auth.application.data.UpdateUserParam;
import io.zhifou.realworld.auth.application.data.UserData;
import io.zhifou.realworld.auth.domain.User;

public interface UserService {
	AuthUserData register(RegisterParam param);

	AuthUserData login(LoginParam param);

	UserData findById(Long id);

	UserData findByEmail(String email);

	UserData findByUsername(String username);

	UserData updateUser(User user, UpdateUserParam param);

	boolean isFollowing(Long userId, Long targetId);
}
