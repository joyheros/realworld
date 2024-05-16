package top.ruilink.realworld.auth.application;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import top.ruilink.realworld.auth.application.data.ProfileData;
import top.ruilink.realworld.auth.application.service.ProfileService;
import top.ruilink.realworld.auth.domain.User;

@RestController
public class ProfileApi {
	private final ProfileService profileService;

	@Autowired
	public ProfileApi(ProfileService profileService) {
		this.profileService = profileService;
	}

	@PostMapping("/api/profiles/{username}/follow")
	public ResponseEntity<?> follow(@AuthenticationPrincipal User user, @PathVariable("username") String username) {
		ProfileData profile = profileService.follow(user, username);
		return ResponseEntity.status(HttpStatus.OK).body(profileResponse(profile));
	}

	@DeleteMapping("/api/profiles/{username}/follow")
	public ResponseEntity<?> unfollow(@AuthenticationPrincipal User user, @PathVariable("username") String username) {
		ProfileData profile = profileService.unfollow(user, username);
		return ResponseEntity.status(HttpStatus.OK).body(profileResponse(profile));
	}

	@GetMapping("/api/profiles/{username}")
	public ResponseEntity<?> getProfile(@AuthenticationPrincipal User user, @PathVariable("username") String username) {
		ProfileData profile = profileService.getProfile(user, username);
		return ResponseEntity.status(HttpStatus.OK).body(profileResponse(profile));
	}

	private Map<String, Object> profileResponse(ProfileData profile) {
		return new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("profile", profile);
			}
		};
	}
}
