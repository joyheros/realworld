package top.ruilink.realworld.exception;

public class InvalidAuthenticationException extends RuntimeException {
	private static final long serialVersionUID = 5104053111110957493L;

	public InvalidAuthenticationException() {
		super("Invalid email or password");
	}
}
