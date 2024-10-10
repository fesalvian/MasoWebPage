package com.MasoWebPage.backend.api.controllers;

import com.MasoWebPage.backend.api.dto.lead.LeadDTOIn;
import com.MasoWebPage.backend.models.Favoritos;
import com.MasoWebPage.backend.models.Lead;
import com.MasoWebPage.backend.models.Produto;
import com.MasoWebPage.backend.repositories.FavoritosRepository;
import com.MasoWebPage.backend.repositories.LeadRepository;
import com.MasoWebPage.backend.repositories.ProdutoRepository;
import com.MasoWebPage.backend.services.LeadServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lead")
public class LeadController {
    @Autowired
    private LeadServices leadServices;
    @PostMapping
    public ResponseEntity<Lead> salvaLead(@RequestBody LeadDTOIn leadIn){
        Lead lead = leadServices.salvarLead(new Lead(leadIn));
        return ResponseEntity.ok(lead);
    }
    @GetMapping("/{email}")
    public ResponseEntity<Lead> buscaLeadPorEmail(@PathVariable String email){
        return ResponseEntity.ok(leadServices.buscaLeadPorEmail(email));
    }
    @GetMapping("/{email}/favoritos")
    public ResponseEntity<Lead> buscaFavoritos(@PathVariable String email){
        Lead lead = leadServices.buscaFavoritos(email);
        return ResponseEntity.ok(lead);
    }

    @PostMapping("/{email}/favoritar")
    public ResponseEntity<Favoritos> favoritaProduto(@PathVariable String email, @RequestParam(name="id") Long idProduto){
        Favoritos favoritos  = leadServices.favoritaProduto(email, idProduto);
        return ResponseEntity.ok(favoritos);
    }


}
