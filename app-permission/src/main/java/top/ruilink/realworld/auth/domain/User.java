package top.ruilink.realworld.auth.domain;

import java.util.Objects;

public class User {
	private Long id;
	private String email;
	private String username;
	private String password;
	private String bio;
	private String image;

	public User() {
	}

	public User(String email, String username, String password) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.bio = "";
		this.image = "";
	}

	public User(String email, String username, String password, String bio, String image) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.bio = bio;
		this.image = image;
	}

	public void update(String email, String username, String password, String bio, String image) {
		if (!email.isBlank()) {
			this.email = email;
		}
		if (!username.isBlank()) {
			this.username = username;
		}
		if (!password.isBlank()) {
			this.password = password;
		}
		if (!bio.isBlank()) {
			this.bio = bio;
		}
		if (!image.isBlank()) {
			this.image = image;
		}
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the bio
	 */
	public String getBio() {
		return bio;
	}

	/**
	 * @param bio the bio to set
	 */
	public void setBio(String bio) {
		this.bio = bio;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		if (image == null || "".equals(image)) {
			image = "https://static.productionready.io/images/smiley-cyrus.jpg";
		}
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof User other && Objects.equals(this.id, other.id)
				&& Objects.equals(this.username, other.username) && Objects.equals(this.email, other.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.username, this.email);
	}

	@Override
	public String toString() {
		return "User {" + "id=" + id + ", username='" + username + "', email='" + email + "'}";
	}
}
