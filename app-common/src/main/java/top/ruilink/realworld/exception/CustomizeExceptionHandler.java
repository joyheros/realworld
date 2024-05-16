package top.ruilink.realworld.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomizeExceptionHandler extends ResponseEntityExceptionHandler {
	private static HttpHeaders headers = new HttpHeaders();
	static {
		headers.setContentType(MediaType.APPLICATION_JSON);
	}
	private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");

	@Override
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
		StringBuffer sb = new StringBuffer();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			sb.append(error.getObjectName()).append(": ");
			sb.append(error.getField()).append(": ");
			sb.append(error.getDefaultMessage());
		}
		ErrorMessages body = new ErrorMessages(HttpStatus.UNPROCESSABLE_ENTITY.value(), LocalDateTime.now().format(dtf),
				sb.toString(), request.getDescription(false));
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(body);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleExceptions(Exception ex, WebRequest request) {
		ErrorMessages body = new ErrorMessages(HttpStatus.UNPROCESSABLE_ENTITY.value(), LocalDateTime.now().format(dtf),
				ex.getMessage(), request.getDescription(false));
		return handleExceptionInternal(ex, body, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler(InvalidRequestException.class)
	public ResponseEntity<Object> handleInvalidRequestException(InvalidRequestException ex, WebRequest request) {
		ErrorMessages body = new ErrorMessages(HttpStatus.UNPROCESSABLE_ENTITY.value(), LocalDateTime.now().format(dtf),
				ex.getMessage(), request.getDescription(false));
		return handleExceptionInternal(ex, body, headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		ErrorMessages body = new ErrorMessages(HttpStatus.NOT_FOUND.value(), LocalDateTime.now().format(dtf),
				ex.getMessage(), request.getDescription(false));
		return handleExceptionInternal(ex, body, headers, HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(InvalidAuthenticationException.class)
	public ResponseEntity<Object> handleAuthenticateFailureException(InvalidAuthenticationException ex,
			WebRequest request) {
		ErrorMessages body = new ErrorMessages(HttpStatus.UNPROCESSABLE_ENTITY.value(), LocalDateTime.now().format(dtf),
				ex.getMessage(), request.getDescription(false));
		return handleExceptionInternal(ex, body, headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
	}

	@ExceptionHandler(NoAuthorizationException.class)
	public ResponseEntity<Object> handleNotAuthorizedException(NoAuthorizationException ex, WebRequest request) {
		ErrorMessages body = new ErrorMessages(HttpStatus.FORBIDDEN.value(), LocalDateTime.now().format(dtf),
				ex.getMessage(), request.getDescription(false));
		return handleExceptionInternal(ex, body, headers, HttpStatus.FORBIDDEN, request);
	}
}
