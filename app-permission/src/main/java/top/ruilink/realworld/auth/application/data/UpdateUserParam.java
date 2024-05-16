package top.ruilink.realworld.auth.application.data;

import com.fasterxml.jackson.annotation.JsonRootName;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@JsonRootName("user")
public class UpdateUserParam {

	@Email(message = "should be an email")
	private String email;

	@Size(min = 5, max = 30, message = "The username must be from 5 to 30 characters.")
	private String username;

	@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z](?=.*[@#$%^&!*-_+=])).{8,30}$", message = "Passwrod must be 8 ~ 30 characters long and combination of uppercase, lowercase, number and specail characters.")
	private String password;
	private String bio;
	private String image;
	
	public UpdateUserParam(String email, String username, String password, String bio, String image) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.bio = bio;
		this.image = image;
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

	/**
	 * @return the bio
	 */
	public String getBio() {
		return bio;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}
}
