package top.ruilink.realworld.auth.security;

public interface JwtTokenService {

	public String generateToken(String username);

	public String getUsernameFromToken(String token);

	public boolean verifyJwtToken(String authToken);
}
