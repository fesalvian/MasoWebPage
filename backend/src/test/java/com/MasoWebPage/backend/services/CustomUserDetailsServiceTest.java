package com.MasoWebPage.backend.services;

import com.MasoWebPage.backend.models.Usuario.Usuario;
import com.MasoWebPage.backend.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomUserDetailsServiceTest {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void loadUserByUsername() {
        Usuario a = usuarioRepository.getByLogin("xpto");
        System.out.println("aaaaaaaaaa");
        System.out.println(a);
    }
}