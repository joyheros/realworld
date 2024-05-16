package top.ruilink.realworld.auth.application.data;

import com.fasterxml.jackson.annotation.JsonRootName;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@JsonRootName("user")
public class LoginParam {
	@NotBlank(message = "can't be empty")
	@Email(message = "should be an email")
	private String email;

	@NotBlank(message = "can't be empty")
	private String password;

	public LoginParam(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
}
