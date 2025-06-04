package com.MasoWebPage.backend.api.controllers.restrito;

import com.MasoWebPage.backend.models.Lead;
import com.MasoWebPage.backend.models.Produto;
import com.MasoWebPage.backend.models.Usuario.Usuario;
import com.MasoWebPage.backend.services.FavoritosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restrito/favoritos")
@PreAuthorize("@AuthUtil.isADM()")
public class FavoritoControllerRestrito {

    @Autowired
    private FavoritosService favoritosService;

    @GetMapping("/{id}")

    public ResponseEntity listaDeLeadsInteressados(@PathVariable String id){
        List<Lead> leads = favoritosService.listaDeLeadsInterressados(id);
        return  ResponseEntity.ok(leads);
    }
}
