package com.MasoWebPage.backend.services;


import com.MasoWebPage.backend.models.Usuario.Usuario;
import com.MasoWebPage.backend.repositories.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService  {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario byLogin = usuarioRepository.getByLogin(username);
        if(byLogin != null){
            return byLogin;
        }else{
            throw new UsernameNotFoundException("usuario nao encontrado");
        }
    }
}
