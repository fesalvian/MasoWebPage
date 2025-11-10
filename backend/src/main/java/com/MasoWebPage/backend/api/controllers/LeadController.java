package com.MasoWebPage.backend.api.controllers;

import com.MasoWebPage.backend.api.dto.EmailDTO;
import com.MasoWebPage.backend.api.dto.TokenDTO;
import com.MasoWebPage.backend.api.dto.TokenValidaEmailDTO;
import com.MasoWebPage.backend.api.dto.UsuarioDTO;
import com.MasoWebPage.backend.api.dto.lead.LeadDTO;
import com.MasoWebPage.backend.api.dto.lead.LeadDTOAtualizacao;
import com.MasoWebPage.backend.exceptions.UsuarioException;
import com.MasoWebPage.backend.models.Lead;
import com.MasoWebPage.backend.models.Usuario.Usuario;
import com.MasoWebPage.backend.security.LeadAuthProvider;
import com.MasoWebPage.backend.security.TokenServices;
import com.MasoWebPage.backend.services.UsuarioService;
import com.MasoWebPage.backend.services.LeadService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.websocket.server.PathParam;
import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/lead")
public class LeadController {
    @Autowired
    private LeadService leadService;
    @Autowired
    private UsuarioService detailsService;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenServices tokenServices;
    @Autowired
    private LeadAuthProvider manager;

    @PutMapping("/{email}")
    public ResponseEntity<LeadDTO> atualizacao(@RequestBody LeadDTOAtualizacao dados, @PathVariable String  email) {
        return ResponseEntity.ok(new LeadDTO(leadService.atualizar(dados, email)));
    }


    @PostMapping("/cadastro")
    public ResponseEntity<Map> cadastro(@RequestBody @Valid LeadDTO dados, UriComponentsBuilder uriBuilder){
        try {
            var lead = leadService.salvar(dados);
            var uri = uriBuilder.path("/lead/{id}").buildAndExpand(lead.getId()).toUri();
            HashMap<String, String> response = new HashMap<>();
            response.put("lead", objectMapper.writeValueAsString(lead));
            response.put("leadStatus","invalido");
            return ResponseEntity.created(uri).body(response);
        }catch (UsuarioException e){
            return ResponseEntity.badRequest().build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody  @Valid EmailDTO dados){
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dados.email(), "");
            Authentication authenticate = manager.authenticate(token);
            String tokenJWT = tokenServices.gerarToken((Lead) authenticate.getPrincipal());
            return ResponseEntity.ok(new TokenDTO(tokenJWT));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new TokenDTO(e.getMessage()));
        }
    }




    @PostMapping("/validarEmail")
    public  ResponseEntity validarEmail(@RequestBody TokenValidaEmailDTO dados) {
        leadService.validarEmail(dados.token());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity buscaLead(@AuthenticationPrincipal UserDetails auth){
        Lead lead = leadService.buscaLead(auth.getUsername());
        return ResponseEntity.ok(new LeadDTO(lead));

    }

    @DeleteMapping
    public ResponseEntity deletar(@AuthenticationPrincipal UserDetails auth) {
        leadService.deletar(auth.getUsername());
        return ResponseEntity.noContent().build();
    }

}
