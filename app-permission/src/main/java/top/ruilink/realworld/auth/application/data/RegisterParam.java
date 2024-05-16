package top.ruilink.realworld.auth.application.data;

import com.fasterxml.jackson.annotation.JsonRootName;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@JsonRootName("user")
public class RegisterParam {
	@NotBlank(message = "can't be empty")
	@Email(message = "should be an email")
	private String email;

	@NotBlank(message = "can't be empty")
	@Size(min = 5, max = 30, message = "The username must be from 5 to 30 characters.")
	private String username;

	@NotBlank(message = "can't be empty")
	@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z](?=.*[@#$%^&!*-_+=])).{8,30}$", message = "Passwrod must be 8 ~ 30 characters long and combination of uppercase, lowercase, number and specail characters.")
	private String password;

	public RegisterParam(String email, String username, String password) {
		this.email = email;
		this.username = username;
		this.password = password;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
}
