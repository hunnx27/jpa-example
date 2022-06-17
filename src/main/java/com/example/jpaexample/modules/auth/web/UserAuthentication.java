package com.example.jpaexample.modules.auth.web;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class UserAuthentication extends UsernamePasswordAuthenticationToken {

    public UserAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public UserAuthentication(Object principal, Object credentials,
                              List<GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
