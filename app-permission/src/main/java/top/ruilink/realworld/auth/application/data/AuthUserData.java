package top.ruilink.realworld.auth.application.data;

import com.fasterxml.jackson.annotation.JsonRootName;
import top.ruilink.realworld.auth.domain.User;

@JsonRootName("user")
public record AuthUserData(String email, String username, String bio, String image, String token) {
	public AuthUserData(User user, String token) {
		this(user.getEmail(), user.getUsername(), user.getBio(), user.getImage(), token);
	}

	public AuthUserData(UserData user, String token) {
		this(user.email(), user.username(), user.bio(), user.image(), token);
	}
}
