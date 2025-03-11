package com.MasoWebPage.backend.services;

import com.MasoWebPage.backend.models.Lead;
import com.MasoWebPage.backend.models.Produto;
import com.MasoWebPage.backend.repositories.LeadRepository;
import com.MasoWebPage.backend.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class FavoritosService {
    @Autowired
    private LeadRepository leadRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    public void favoritar(String leadId, String produtoId) {
        Optional<Lead> byUsuarioId = leadRepository.findByUsuarioId(leadId);
        Optional<Produto> byId = produtoRepository.findById(produtoId);
        Produto produto = byId.get();
        Lead lead = byUsuarioId.get();
        produto.addLead(lead);
        lead.addProduto(produto); // codigo possivelmente redundante e duplicado
        produtoRepository.save(produto);
        leadRepository.save(lead);



    }
}
