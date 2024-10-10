package com.MasoWebPage.backend.services;

import com.MasoWebPage.backend.exceptions.EmailExistenteException;
import com.MasoWebPage.backend.exceptions.LeadNaoEncontradoException;
import com.MasoWebPage.backend.models.Favoritos;
import com.MasoWebPage.backend.models.Lead;
import com.MasoWebPage.backend.models.Produto;
import com.MasoWebPage.backend.repositories.FavoritosRepository;
import com.MasoWebPage.backend.repositories.LeadRepository;
import com.MasoWebPage.backend.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeadServices {
    @Autowired
    private LeadRepository leadRepository;

    @Autowired
    private FavoritosRepository favoritosRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public Lead salvarLead(Lead lead) {
        if (leadRepository.existsByEmail(lead.getEmail())) {
            throw new EmailExistenteException("email existe!!");
        } else {
            return leadRepository.findByEmail(lead.getEmail());
        }
    }

    public Lead buscaLeadPorEmail(String email) {
        Lead lead = leadRepository.findByEmail(email);
        if (lead != null) {
            return lead;
        } else {
            throw new LeadNaoEncontradoException("Lead com o email " + email + " não encontrado");
        }


    }

    public Lead buscaFavoritos(String email) {
        Lead lead = leadRepository.findByEmail(email);

        if (lead != null) {
            List<Produto> favoritos = favoritosRepository.buscaProdutosFavoritos(lead.getId());
            lead.setFavoritos(favoritos);
            return lead;
        } else {
            throw new LeadNaoEncontradoException("Lead com o email " + email + " não encontrado");
        }

    }

    public Favoritos favoritaProduto(String email, Long idProduto) {
        Produto produto = produtoRepository.getReferenceById(idProduto);
        Lead lead = leadRepository.findByEmail(email);

        if(lead != null && produto != null){
            Favoritos favoritos = new Favoritos(produto, lead);
            return favoritosRepository.save(favoritos);
        }else{
            throw new RuntimeException("lead com email "+email+" não existe ou produto "+produto.getNome()+" não existe");
        }
    }
}
