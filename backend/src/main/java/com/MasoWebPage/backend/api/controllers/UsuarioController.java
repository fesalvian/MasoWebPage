package com.MasoWebPage.backend.api.controllers;

import com.MasoWebPage.backend.api.dto.TokenDTO;
import com.MasoWebPage.backend.api.dto.UsuarioDTO;
import com.MasoWebPage.backend.models.Administrador;
import com.MasoWebPage.backend.models.Usuario.Usuario;
import com.MasoWebPage.backend.security.TokenServices;
import com.MasoWebPage.backend.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenServices tokenServices;


    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid UsuarioDTO dados) {
        try {

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha() );
            Authentication authenticate = manager.authenticate(token);

            String tokenJWT = tokenServices.gerarToken((Usuario) authenticate.getPrincipal());

            return ResponseEntity.ok(new TokenDTO(tokenJWT));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new TokenDTO(e.getMessage()));
        }
    }
    @DeleteMapping("/{login}")
    @PreAuthorize("#login == authentication.principal.login")
    public ResponseEntity exclusaoLogica(@PathVariable String login){
        usuarioService.excluisaoLogica(login);
        return ResponseEntity.noContent().build();
    }
}
