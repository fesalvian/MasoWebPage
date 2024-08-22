package com.MasoWebPage.backend.api.controllers;

import com.MasoWebPage.backend.models.Favoritos;
import com.MasoWebPage.backend.models.Lead;
import com.MasoWebPage.backend.models.Produto;
import com.MasoWebPage.backend.repositories.FavoritosRepository;
import com.MasoWebPage.backend.repositories.LeadRepository;
import com.MasoWebPage.backend.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lead")
public class LeadController {
    @Autowired
    private LeadRepository leadRepository;

    @Autowired
    private FavoritosRepository favoritosRepository;

    @Autowired
    private ProdutoRepository  produtoRepository;
    @PostMapping
    public ResponseEntity<Lead> salvaLead(@RequestBody Lead lead){
         leadRepository.save(lead);
         return ResponseEntity.ok(lead);
    }
    @GetMapping("/{email}")
    public ResponseEntity<Lead> buscaLeadPorEmail(@PathVariable String email){
        return ResponseEntity.ok(leadRepository.findByEmail(email));
    }
    @GetMapping("/{email}/favoritos")
    public ResponseEntity<Lead> buscaFavoritos(@PathVariable String email){
        Lead lead = leadRepository.findByEmail(email);
        List<Produto> favoritos = favoritosRepository.buscaProdutosFavoritos(lead.getId());
        lead.setFavoritos(favoritos);

        return ResponseEntity.ok(lead);
    }

    @PostMapping("/{email}/favoritar")
    public ResponseEntity<Favoritos> favoritaProduto(@PathVariable String email, @RequestParam(name="id") Long idProduto){
        Produto produtoBuscado = produtoRepository.getReferenceById(idProduto);
        Lead leadBuscado = leadRepository.findByEmail(email);
        Favoritos favoritos = new Favoritos(produtoBuscado, leadBuscado);
        favoritosRepository.save(favoritos);
        return ResponseEntity.ok(favoritos);
    }


}
