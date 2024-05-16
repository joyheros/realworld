package top.ruilink.realworld.auth.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import top.ruilink.realworld.auth.domain.User;

class MockAuthContextFactory implements WithSecurityContextFactory<MockAuthPrincipal> {
    @Override
    public SecurityContext createSecurityContext(MockAuthPrincipal principal) {
        User user = new User(principal.email(), principal.username(), principal.password(), principal.bio(), principal.image());
        user.setId(principal.id());
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(user, principal.password(), null);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        return context;
    }
}