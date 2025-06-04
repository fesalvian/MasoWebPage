package com.MasoWebPage.backend.security;

import com.MasoWebPage.backend.models.Lead;
import com.MasoWebPage.backend.services.LeadUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class LeadAuthProvider implements AuthenticationProvider {

    @Autowired
    private LeadUserDetailsService leadService;

    @Override
    public Authentication authenticate(Authentication authentication) {
        String email = authentication.getName();
        Lead lead = leadService.loadUserByUsername(email);

        if (lead == null) throw new BadCredentialsException("Lead não encontrado.");

        return new UsernamePasswordAuthenticationToken(lead, null, lead.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
