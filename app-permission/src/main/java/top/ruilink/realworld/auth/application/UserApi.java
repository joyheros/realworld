package top.ruilink.realworld.auth.application;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import top.ruilink.realworld.auth.application.data.AuthUserData;
import top.ruilink.realworld.auth.application.data.LoginParam;
import top.ruilink.realworld.auth.application.data.RegisterParam;
import top.ruilink.realworld.auth.application.data.UpdateUserParam;
import top.ruilink.realworld.auth.application.data.UserData;
import top.ruilink.realworld.auth.application.service.UserService;
import top.ruilink.realworld.auth.domain.User;
import jakarta.validation.Valid;

@RestController
public class UserApi {
	private final UserService userService;

	@Autowired
	public UserApi(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/api/users")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterParam registerParam) {
		AuthUserData user = userService.register(registerParam);
		return ResponseEntity.status(HttpStatus.CREATED).body(userResponse(user));
	}

	@PostMapping("/api/users/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginParam loginParam) {
		AuthUserData user = userService.login(loginParam);
		return ResponseEntity.status(HttpStatus.OK).body(userResponse(user));
	}

	@PutMapping("/api/user")
	public ResponseEntity<?> updateCurrentUser(@Valid @RequestBody UpdateUserParam updateUserParam,
			@AuthenticationPrincipal User currentUser, @RequestHeader("Authorization") String authorization) {
		UserData userData = userService.updateUser(currentUser, updateUserParam);
		return ResponseEntity.status(HttpStatus.OK)
			.body(userResponse(new AuthUserData(userData, authorization.split(" ")[1])));
	}

	@GetMapping("/api/user")
	public ResponseEntity<?> currentUser(@AuthenticationPrincipal User currentUser,
			@RequestHeader(value = "Authorization") String authorization) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(userResponse(new AuthUserData(currentUser, authorization.split(" ")[1])));
	}

	private Map<String, Object> userResponse(AuthUserData authUserData) {
		return new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("user", authUserData);
			}
		};
	}
}
