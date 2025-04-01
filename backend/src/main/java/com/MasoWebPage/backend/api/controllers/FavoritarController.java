package com.MasoWebPage.backend.api.controllers;

import com.MasoWebPage.backend.models.Lead;
import com.MasoWebPage.backend.models.Produto;
import com.MasoWebPage.backend.models.Usuario.Role;
import com.MasoWebPage.backend.models.Usuario.Usuario;
import com.MasoWebPage.backend.services.FavoritosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favoritos")
public class FavoritarController {

    @Autowired
    private FavoritosService favoritosService;

    @PutMapping("/favoritar")
    @PreAuthorize("@AuthUtil.notADM()")
    public ResponseEntity favoritar(@RequestParam("id") String id, @AuthenticationPrincipal Usuario usuario){

        favoritosService.favoritar(usuario.getId(), id);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    @PreAuthorize("@AuthUtil.notADM()")
    public ResponseEntity favoritosDoLead(@AuthenticationPrincipal Usuario usuario){
        List<Produto> favoritos = favoritosService.buscaFavoritos(usuario);
        return ResponseEntity.ok(favoritos);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@AuthUtil.isADM()")
    public ResponseEntity listaDeLeadsInteressados(@PathVariable String id){
        List<Lead> leads = favoritosService.listaDeLeadsInterressados(id);
        return  ResponseEntity.ok(leads);
    }
}
