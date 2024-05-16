package top.ruilink.realworld.auth.application.service;

import top.ruilink.realworld.auth.application.data.AuthUserData;
import top.ruilink.realworld.auth.application.data.LoginParam;
import top.ruilink.realworld.auth.application.data.RegisterParam;
import top.ruilink.realworld.auth.application.data.UpdateUserParam;
import top.ruilink.realworld.auth.application.data.UserData;
import top.ruilink.realworld.auth.domain.User;

public interface UserService {
	AuthUserData register(RegisterParam param);

	AuthUserData login(LoginParam param);

	UserData findById(Long id);

	UserData findByEmail(String email);

	UserData findByUsername(String username);

	UserData updateUser(User user, UpdateUserParam param);

	boolean isFollowing(Long userId, Long targetId);
}
