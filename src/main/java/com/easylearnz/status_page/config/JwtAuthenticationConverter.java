package com.easylearnz.status_page.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationConverter implements Converter<Jwt, Collection<GrantedAuthority>> {


    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Extract permissions from the JWT token
        List<String> permissions = jwt.getClaimAsStringList("permissions");
        if (permissions != null) {
            for (String permission : permissions) {
                authorities.add(new SimpleGrantedAuthority(permission));
            }
        }

        // Extract roles if present
        List<String> roles = jwt.getClaimAsStringList("https://your-domain.com/roles");
        if (roles != null) {
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
        }

        return authorities;
    }

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        return source.getClaimAsStringList("roles").stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}