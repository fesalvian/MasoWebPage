package com.MasoWebPage.backend.api.controllers;

import com.MasoWebPage.backend.api.dto.TokenDTO;
import com.MasoWebPage.backend.api.dto.UsuarioDTO;
import com.MasoWebPage.backend.api.dto.administrador.AdministradorDTO;
import com.MasoWebPage.backend.exceptions.UsuarioException;
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



    @Autowired
    private AdministradorService administradorService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenServices tokenServices;

    @PostMapping("/cadastro")
    public ResponseEntity<Administrador> cadastro(@RequestBody @Valid AdministradorDTO dados, UriComponentsBuilder uriBuilder){
      try {
          var administrador = administradorService.salvar(new Administrador(dados));
          var uri = uriBuilder.path("/adm/{id}").buildAndExpand(administrador.getId()).toUri();

          return ResponseEntity.created(uri).body(administrador);
      }catch (UsuarioException e){
          return ResponseEntity.badRequest().build();
      }

    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid UsuarioDTO dados) {
        try {


            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
            Authentication authenticate = manager.authenticate(token);

            String tokenJWT = tokenServices.gerarToken((Usuario) authenticate.getPrincipal());

            return ResponseEntity.ok(new TokenDTO(tokenJWT));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new TokenDTO(e.getMessage()));
        }
    }

}
