package com.MasoWebPage.backend.api.controllers;

import com.MasoWebPage.backend.api.dto.ProdutoDTO;
import com.MasoWebPage.backend.models.Lead;
import com.MasoWebPage.backend.models.Produto;
import com.MasoWebPage.backend.models.Usuario.Usuario;
import com.MasoWebPage.backend.services.FavoritosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/favoritos")
public class FavoritoController {

    @Autowired
    private FavoritosService favoritosService;

    @PutMapping("/favoritar")
    public ResponseEntity favoritar(  @RequestParam("id") String produtoId, @AuthenticationPrincipal Lead lead){

        favoritosService.favoritar(lead.getId() , produtoId);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/desfavoritar")
    public ResponseEntity desfavoritar(  @RequestParam("id") String produtoId, @AuthenticationPrincipal Lead lead){
        System.out.println(produtoId);
        favoritosService.desfavoritar(lead.getId() , produtoId);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> favoritosDoLead(@AuthenticationPrincipal Lead lead){
        List<Produto> favoritos = favoritosService.buscaFavoritos(lead.getEmail());
        List<ProdutoDTO> favoritosDto = favoritos.stream().map(ProdutoDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(favoritosDto);
    }

}
