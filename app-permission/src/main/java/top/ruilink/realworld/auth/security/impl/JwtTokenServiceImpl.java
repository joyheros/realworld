package top.ruilink.realworld.auth.security.impl;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import top.ruilink.realworld.auth.security.JwtTokenService;

/**
 * JWT Token service implements by JJWT
 */
@Component
public class JwtTokenServiceImpl implements JwtTokenService {
	private final String secretKey;
	private final int expireTime;

	public JwtTokenServiceImpl(@Value("${jwt.secretKey}") String secretKey,
			@Value("${jwt.expireTime}") int expireTime) {
		this.secretKey = secretKey;
		this.expireTime = expireTime;
	}

	public String generateToken(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expireTime * 1000))
				.signWith(parsekey(), SignatureAlgorithm.HS256).compact();
	}

	public String getUsernameFromToken(String token) {
		try {
			return Jwts.parserBuilder().setSigningKey(parsekey()).build().parseClaimsJws(token).getBody().getSubject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean verifyJwtToken(String authToken) {
		try {
			Jwts.parserBuilder().setSigningKey(parsekey()).build().parse(authToken);
			return true;
		} catch (MalformedJwtException e) {
			e.printStackTrace();
			// logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			e.printStackTrace();
			// logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			e.printStackTrace();
			// logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			// logger.error("JWT claims string is empty: {}", e.getMessage());
		}
		return false;
	}

	private Key parsekey() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
	}
}
