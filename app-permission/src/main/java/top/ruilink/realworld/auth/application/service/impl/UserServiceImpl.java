package top.ruilink.realworld.auth.application.service.impl;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import top.ruilink.realworld.exception.ResourceNotFoundException;

import top.ruilink.realworld.auth.application.data.AuthUserData;
import top.ruilink.realworld.auth.application.data.LoginParam;
import top.ruilink.realworld.auth.application.data.RegisterParam;
import top.ruilink.realworld.auth.application.data.UpdateUserParam;
import top.ruilink.realworld.auth.application.data.UserData;
import top.ruilink.realworld.auth.application.service.UserService;
import top.ruilink.realworld.auth.domain.FollowRelation;
import top.ruilink.realworld.auth.domain.User;
import top.ruilink.realworld.auth.domain.support.UserProvider;
import top.ruilink.realworld.auth.security.JwtTokenService;

@Service
public class UserServiceImpl implements UserService {
	private final UserProvider userProvider;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenService jwtTokenService;
	private final AuthenticationManager authenticationManager;

	public UserServiceImpl(UserProvider userProvider, PasswordEncoder passwordEncoder, JwtTokenService jwtTokenService,
			AuthenticationManager authenticationManager) {
		this.userProvider = userProvider;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenService = jwtTokenService;
		this.authenticationManager = authenticationManager;
	}

	@Override
	@Transactional
	public AuthUserData register(RegisterParam param) {
		if (userProvider.findByEmail(param.getEmail()).isPresent()) {
			throw new IllegalArgumentException("Email `%s` is already exists.".formatted(param.getEmail()));
		}
		if (userProvider.findByUsername(param.getUsername()).isPresent()) {
			throw new IllegalArgumentException("Username `%s` is already exists.".formatted(param.getUsername()));
		}
		User userParam = new User(param.getEmail(), param.getUsername(), passwordEncoder.encode(param.getPassword()));
		Optional<User> entity = userProvider.save(userParam);
		if (entity.isPresent()) {
			User user = entity.get();
			String token = jwtTokenService.generateToken(user.getUsername());
			return new AuthUserData(user, token);
		} else {
			throw new IllegalArgumentException("Fail to save user!");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public AuthUserData login(LoginParam param) {
		final String username = param.getEmail();
		Authentication auth = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, param.getPassword()));
		if (auth.isAuthenticated()) {
			Optional<User> entity = userProvider.findByEmail(username);
			if (entity.isPresent()) {
				User user = entity.get();
				String token = jwtTokenService.generateToken(username);
				return new AuthUserData(user, token);
			} else {
				throw new UsernameNotFoundException("User with email [" + username + "] not found !");
			}
		} else {
			throw new IllegalArgumentException("Invalid email or password.");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public UserData findById(Long id) {
		Optional<User> entity = userProvider.findById(id);
		if (entity.isEmpty()) {
			throw new ResourceNotFoundException("User with id [" + id + "] not found !");
		} else {
			User user = entity.get();
			return new UserData(user);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public UserData findByEmail(String email) {
		Optional<User> entity = userProvider.findByEmail(email);
		if (entity.isEmpty()) {
			throw new ResourceNotFoundException("User with email [" + email + "] not found !");
		} else {
			User user = entity.get();
			return new UserData(user);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public UserData findByUsername(String username) {
		Optional<User> entity = userProvider.findByUsername(username);
		if (entity.isEmpty()) {
			throw new ResourceNotFoundException("User with username [" + username + "] not found !");
		} else {
			User user = entity.get();
			return new UserData(user);
		}
	}

	@Override
	@Transactional
	public UserData updateUser(User user, UpdateUserParam param) {
		if (!user.getEmail().equalsIgnoreCase(param.getEmail())
				&& userProvider.findByEmail(param.getEmail()).isPresent()) {
			throw new IllegalArgumentException("Email `%s` is already exists.".formatted(param.getEmail()));
		}
		if (!user.getUsername().equalsIgnoreCase(param.getUsername())
				&& userProvider.findByUsername(param.getUsername()).isPresent()) {
			throw new IllegalArgumentException("Username `%s` is already exists.".formatted(param.getUsername()));
		}
		user.update(param.getEmail(), param.getUsername(), passwordEncoder.encode(param.getPassword()), param.getBio(),
				param.getImage());
		Optional<User> entity = userProvider.save(user);
		if (entity.isEmpty()) {
			throw new IllegalArgumentException("Fail to save user!");
		} else {
			User newUser = entity.get();
			return new UserData(newUser);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isFollowing(Long userId, Long targetId) {
		Optional<FollowRelation> following = userProvider.findRelation(userId, targetId);
		if (following.isEmpty()) {
			return false;
		}
		return true;
	}
}
