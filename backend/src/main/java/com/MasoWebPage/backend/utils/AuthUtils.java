package com.MasoWebPage.backend.utils;

import com.MasoWebPage.backend.models.Usuario.Role;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component("AuthUtil")

public class AuthUtils {

    public Authentication getAuthenticaion() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Boolean isADM() {
        Collection<? extends GrantedAuthority> authorities = getAuthenticaion().getAuthorities();
        List<? extends GrantedAuthority> collect =authorities.stream()
                .filter(a -> a.toString().equals(Role.ADM.toString()))
                .toList();

        return !collect.isEmpty();
    }
    public Boolean notADM() {
        Collection<? extends GrantedAuthority> authorities = getAuthenticaion().getAuthorities();
        List<? extends GrantedAuthority> collect = authorities.stream()
                .filter(a -> a.toString().equals(Role.ADM.toString()))
                .toList();


        return collect.isEmpty();
    }


}