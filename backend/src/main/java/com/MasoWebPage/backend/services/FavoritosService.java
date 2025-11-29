package com.MasoWebPage.backend.services;

import com.MasoWebPage.backend.models.Lead;
import com.MasoWebPage.backend.models.Produto;
import com.MasoWebPage.backend.models.Usuario.Usuario;
import com.MasoWebPage.backend.repositories.LeadRepository;
import com.MasoWebPage.backend.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class FavoritosService {
    @Autowired
    private LeadRepository leadRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    public void favoritar(String leadId, String produtoId) {
        Optional<Lead> byUsuarioId = leadRepository.findById(leadId);
        Optional<Produto> byId = produtoRepository.findById(produtoId);
        Produto produto = byId.get();
        Lead lead = byUsuarioId.get();
        produto.addLead(lead);
        lead.addProduto(produto); // codigo possivelmente redundante e duplicado
        produtoRepository.save(produto);
        leadRepository.save(lead);

    }
    public void desfavoritar(String leadId, String produtoId) {
        Optional<Lead> byUsuarioId = leadRepository.findById(leadId);
        Optional<Produto> byId = produtoRepository.findById(produtoId);
        Produto produto = byId.get();
        Lead lead = byUsuarioId.get();
        produto.removeLead(lead);
        lead.removeProduto(produto); // codigo possivelmente redundante e duplicado
        produtoRepository.save(produto);
        leadRepository.save(lead);

    }

    public List<Produto> buscaFavoritos(String email) {
        Lead lead =  leadRepository.findByEmail(email).get();
        List<String> IDs = lead.getProdutosFavoritos().stream().map(produto -> produto.getId()).collect(Collectors.toList());
        List<Produto> produtos = produtoRepository.findAllById(IDs);

        return produtos;
    }

    public List<Lead> listaDeLeadsInterressados(String id) {
        System.out.println("id");
        System.out.println(id);
        Produto produto = produtoRepository.findById(id).get();
        System.out.println(produto.getNome());
        System.out.println(produto.getLeads());
        List<String> IDs = produto.getLeads().stream().map(lead -> lead.getId()).collect(Collectors.toList());
        List<Lead> leads = leadRepository.findAllById(IDs);

        return leads;
    }
}
