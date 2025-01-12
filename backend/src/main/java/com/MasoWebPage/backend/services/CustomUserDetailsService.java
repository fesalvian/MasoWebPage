package com.MasoWebPage.backend.services;


import com.MasoWebPage.backend.models.Usuario.Role;
import com.MasoWebPage.backend.models.Usuario.Usuario;
import com.MasoWebPage.backend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));


        // Verifica se o usuário é válido antes de retornar
        if (!usuario.isEnabled()) {
            throw new IllegalStateException("Usuário está inválido ou desativado");
        }

        return usuario;
    }

    public Boolean isADM() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<? extends GrantedAuthority> collect = authorities.stream().filter(a -> a.toString() == Role.ADM.toString()).toList();


        return !collect.isEmpty();

    }

    public Boolean verificaAutenticidade(String login) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario principal =(Usuario) authentication.getPrincipal();
        return principal.getLogin().equals(login);
    }
}
