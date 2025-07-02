package com.MasoWebPage.backend.security;

import com.MasoWebPage.backend.repositories.LeadRepository;
import com.MasoWebPage.backend.repositories.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenServices tokenServices;


    @Autowired
    private List<UserDetailsService> services;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String tokenJWT = getToken(request);

        if (tokenJWT != null) {
            try {
                String subject = tokenServices.getSubject(tokenJWT);

                UserDetails user = null;

                for (UserDetailsService service : services) {
                    try {
                        user = service.loadUserByUsername(subject);
                        if (user != null) break;
                    } catch (Exception ignored) {}
                }

                if (user != null) {
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token inválido");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
                return authorizationHeader.replace("Bearer ", "");
        }

        return null;
    }
}
