package top.ruilink.realworld.auth.security;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = MockAuthContextFactory.class)
public @interface MockAuthPrincipal {

    long id() default 1L;
    String email() default "tester@email.com";
    String username() default "tester";
    String bio() default "";
    String image() default "";
    String password() default "password";
}
