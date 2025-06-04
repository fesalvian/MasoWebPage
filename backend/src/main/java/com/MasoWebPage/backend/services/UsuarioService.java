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

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> opt = usuarioRepository.findByLogin(username);
        Usuario usuario;
        if(opt.isPresent()){
            usuario = opt.get();
            return usuario;
        }else {
            usuario = null;
            return usuario;
        }


    }


}
