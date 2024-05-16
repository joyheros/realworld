package top.ruilink.realworld.auth.application.data;

import com.fasterxml.jackson.annotation.JsonRootName;
import top.ruilink.realworld.auth.domain.User;

@JsonRootName("profile")
public record ProfileData(String username, String bio, String image, boolean following) {
	public ProfileData(User user, boolean following) {
		this(user.getUsername(), user.getBio(), user.getImage(), following);
	}

	public static ProfileData following(User target) {
		return new ProfileData(target.getUsername(), target.getBio(), target.getImage(), true);
	}

	public static ProfileData unfollowing(User target) {
		return new ProfileData(target.getUsername(), target.getBio(), target.getImage(), false);
	}
}
