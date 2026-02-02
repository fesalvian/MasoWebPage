package com.MasoWebPage.backend.api.controllers.restrito;

import com.MasoWebPage.backend.api.dto.administrador.AdministradorAtualizar;
import com.MasoWebPage.backend.api.dto.administrador.AdministradorDTO;
import com.MasoWebPage.backend.exceptions.UsuarioException;
import com.MasoWebPage.backend.models.Administrador;
import com.MasoWebPage.backend.security.TokenServices;
import com.MasoWebPage.backend.services.AdministradorService;
import com.MasoWebPage.backend.services.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/restrito")
@PreAuthorize("@AuthUtil.isADM()")
public class AdministradorController {



    @Autowired
    private AdministradorService administradorService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenServices tokenServices;

    @PostMapping("/cadastro")
    public ResponseEntity<Administrador> cadastro(@RequestBody @Valid AdministradorDTO dados, UriComponentsBuilder uriBuilder){
      try {
          System.out.println("cadastro");
          var administrador = administradorService.salvar(new Administrador(dados));
          var uri = uriBuilder.path("/adm/{id}").buildAndExpand(administrador.getId()).toUri();

          return ResponseEntity.created(uri).body(administrador);
      }catch (UsuarioException e){
          return ResponseEntity.badRequest().build();
      } catch (Exception e) {
          throw new RuntimeException(e);
      }


    }

    @PutMapping("/{login}")
    @PreAuthorize("#login == authentication.principal.login")
    public ResponseEntity<Administrador> atualizar(@RequestBody AdministradorAtualizar dados, @PathVariable String login) throws Exception {

        return ResponseEntity.ok(administradorService.atualizar(dados, login));
    }

    @DeleteMapping("/{login}")
    @PreAuthorize("#login == authentication.principal.login")
    public ResponseEntity exclusaoLogica(@PathVariable String login){
        administradorService.excluisaoLogica(login);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Administrador> buscaPorLogin(@AuthenticationPrincipal String login){
        System.out.println(login);
        return ResponseEntity.ok(administradorService.buscaAdmPorLogin(login));
    }
}


