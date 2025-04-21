package egovframework.let.auth.web;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private final String token;

    public JwtAuthenticationToken(User principal, String token, Collection<? extends GrantedAuthority> authorities) {
        super(principal, token, authorities);
        this.token = token;
    }

    public String getToken() {
        return token;
    }
} 