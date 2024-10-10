package com.MasoWebPage.backend.api.controllers;

import com.MasoWebPage.backend.api.dto.TokenDTO;
import com.MasoWebPage.backend.api.dto.UsuarioDTO;
import com.MasoWebPage.backend.models.Administrador;
import com.MasoWebPage.backend.models.Usuario.Usuario;
import com.MasoWebPage.backend.security.TokenServices;
import com.MasoWebPage.backend.services.AdministradorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



import com.MasoWebPage.backend.api.dto.TokenDTO;
import com.MasoWebPage.backend.api.dto.UsuarioDTO;
import com.MasoWebPage.backend.models.Usuario.Usuario;
import com.MasoWebPage.backend.security.TokenServices;
import com.MasoWebPage.backend.services.AdministradorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/adm")
public class    AdministradorController {

    private static final Logger logger = LoggerFactory.getLogger(AdministradorController.class);

    @Autowired
    private AdministradorService administradorService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenServices tokenServices;

    @PostMapping("/cadastro")
    public ResponseEntity<Administrador> cadastro(@RequestBody Administrador dados, UriComponentsBuilder uriBuilder){
        var administrador = administradorService.salvar(dados);
        var uri = uriBuilder.path("/estudante/{id}").buildAndExpand(administrador.getId()).toUri();
        logger.info("Administrador cadastrado: {}", administrador.getUsuario().getLogin());
        return ResponseEntity.created(uri).body(administrador);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody UsuarioDTO dados) {
        try {
            logger.info("Login attempt: {}", dados.login());

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
            Authentication authenticate = manager.authenticate(token);

            logger.info("Authentication successful for user: {}", dados.login());
            String tokenJWT = tokenServices.gerarToken((Usuario) authenticate.getPrincipal());

            return ResponseEntity.ok(new TokenDTO(tokenJWT));
        } catch (Exception e) {
            logger.error("Authentication failed for user: {} - Error: {}", dados.login(), e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new TokenDTO("Falha na autenticação"));
        }
    }

    @GetMapping
    public String busca(){
        return "oi";
    }
}
