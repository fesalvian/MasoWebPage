package com.MasoWebPage.backend.api.controllers;

import com.MasoWebPage.backend.models.Lead;
import com.MasoWebPage.backend.models.Usuario.Role;
import com.MasoWebPage.backend.models.Usuario.Usuario;
import com.MasoWebPage.backend.services.FavoritosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/favoritos")
public class FavoritarController {

    @Autowired
    private FavoritosService favoritosService;

    @PutMapping("/favoritar")
    @PreAuthorize("@AuthUtil.notADM()")
    public ResponseEntity favoritar(@RequestParam("id") String id, @AuthenticationPrincipal Usuario usuario){

        favoritosService.favoritar(usuario.getId(), id);
        return null;
    }
}
