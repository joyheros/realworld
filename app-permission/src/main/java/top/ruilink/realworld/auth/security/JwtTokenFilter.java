package top.ruilink.realworld.auth.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import top.ruilink.realworld.auth.domain.User;
import top.ruilink.realworld.auth.domain.support.UserProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
	private final static String header = "Authorization";
	private final static String authPrefix = "Bearer ";
	
	@Autowired
	private JwtTokenService jwtTokenService;
	@Autowired
	private UserProvider userProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwtToken = parseJwtToken(request.getHeader(header));
			if (jwtToken != null && jwtTokenService.verifyJwtToken(jwtToken)) {
				SecurityContext context = SecurityContextHolder.createEmptyContext();
				final String username = jwtTokenService.getUsernameFromToken(jwtToken);
				User user = userProvider.findByEmail(username).get();
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,
						user.getPassword());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				context.setAuthentication(authenticationToken);
				SecurityContextHolder.setContext(context);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// logger.error("Cannot set user authentication: {}", e);
		}
		filterChain.doFilter(request, response);
	}

	private String parseJwtToken(String headerAuth) {
		if (headerAuth != null && headerAuth.startsWith(authPrefix)) {
			return headerAuth.substring(7);
		}
		return null;
	}
}
