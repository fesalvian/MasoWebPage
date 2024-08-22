package com.MasoWebPage.backend.api.controllers;

import com.MasoWebPage.backend.api.dto.ProdutoDTO;
import com.MasoWebPage.backend.models.Lead;
import com.MasoWebPage.backend.models.Produto;
import com.MasoWebPage.backend.repositories.FavoritosRepository;
import com.MasoWebPage.backend.repositories.LeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lead")
public class LeadController {
    @Autowired
    private LeadRepository leadRepository;

    @Autowired
    private FavoritosRepository favoritosRepository;
    @PostMapping
    public ResponseEntity<Lead> salvaLead(@RequestBody Lead lead){
         leadRepository.save(lead);
         return ResponseEntity.ok(lead);
    }
    @GetMapping("/{email}")
    public ResponseEntity<Lead> buscaLeadPorId(@PathVariable String email){
        return ResponseEntity.ok(leadRepository.findByEmail(email));
    }
    @GetMapping("/{email}/favoritos")
    public ResponseEntity<Lead> buscaFavoritos(@PathVariable String email){
        Lead lead = leadRepository.findByEmail(email);
        List<Produto> favoritos = favoritosRepository.buscaProdutosFavoritos(lead.getId());
        lead.setFavoritos(favoritos);

        return ResponseEntity.ok(lead);
    }


}
