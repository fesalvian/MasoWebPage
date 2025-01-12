package com.MasoWebPage.backend.api.controllers;

import com.MasoWebPage.backend.api.dto.TokenDTO;
import com.MasoWebPage.backend.api.dto.UsuarioDTO;
import com.MasoWebPage.backend.api.dto.administrador.AdministradorDTO;
import com.MasoWebPage.backend.api.dto.lead.LeadDTO;
import com.MasoWebPage.backend.exceptions.UsuarioException;
import com.MasoWebPage.backend.models.Administrador;
import com.MasoWebPage.backend.models.Lead;
import com.MasoWebPage.backend.models.Usuario.Usuario;
import com.MasoWebPage.backend.security.TokenServices;
import com.MasoWebPage.backend.services.AdministradorService;
import com.MasoWebPage.backend.services.CustomUserDetailsService;
import com.MasoWebPage.backend.services.LeadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
@RestController
@RequestMapping("/lead")
public class LeadController {
    @Autowired
    private LeadService leadService;
    @Autowired
    private CustomUserDetailsService detailsService;
    @PostMapping("/cadastro")
    public ResponseEntity<Lead> cadastro(@RequestBody @Valid LeadDTO dados, UriComponentsBuilder uriBuilder){
        try {


            var lead = leadService.salvar(new Lead(null, dados.email(), new Usuario(dados.usuario())));
            var uri = uriBuilder.path("/lead/{id}").buildAndExpand(lead.getId()).toUri();

            return ResponseEntity.created(uri).body(lead);
        }catch (UsuarioException e){
            return ResponseEntity.badRequest().build();
        }

    }


    @PutMapping("/{login}")
    public ResponseEntity<Lead> atualizar(LeadDTO dados, @PathVariable String login) {

        if(detailsService.verificaAutenticidade(login)){
            return ResponseEntity.ok(leadService.atualizar(dados, login));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }



}
