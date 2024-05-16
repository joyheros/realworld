package top.ruilink.realworld.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NoAuthorizationException extends RuntimeException {
	private static final long serialVersionUID = -5984046904185572217L;
}
