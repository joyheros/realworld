package io.zhifou.realworld.auth.application.data;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.zhifou.realworld.auth.domain.User;

@JsonRootName("user")
public record UserData(String email, String username, String bio, String image) {
	public UserData(User user) {
		this(user.getEmail(), user.getUsername(), user.getBio(), user.getImage());
	}
}
