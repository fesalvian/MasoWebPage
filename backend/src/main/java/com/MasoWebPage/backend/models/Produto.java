package com.MasoWebPage.backend.models;

import com.MasoWebPage.backend.api.dto.ProdutoDTO;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document("produto")
public class Produto {

    @Id
    private String id;

    private String nome;
    private String descricao;
    private String ambiente;

    private String capaUrl;

    private List<String> imagensUrls = new ArrayList<>();

    private List<String> tags = new ArrayList<>();

    private List<String> coresSelecionadas = new ArrayList<>();

    @DBRef
    private List<Lead> leads = new ArrayList<>();


    public Produto(ProdutoDTO dto){
        this.nome = dto.nome();
        this.descricao = dto.descricao();
        this.ambiente = dto.ambiente();
        this.capaUrl = dto.capaUrl();
        this.imagensUrls = dto.imagensUrls();
        this.tags = dto.tags();
        this.coresSelecionadas = dto.coresSelecionadas();
    }


    public void atualiza(Produto produto){

        if (produto.getNome() != null && !produto.getNome().isBlank()){
            this.nome = produto.getNome();
        }

        if (produto.getDescricao() != null && !produto.getDescricao().isBlank()){
            this.descricao = produto.getDescricao();
        }

        if (produto.getAmbiente() != null && !produto.getAmbiente().isBlank()){
            this.ambiente = produto.getAmbiente();
        }

        if (produto.getCapaUrl() != null){
            this.capaUrl = produto.getCapaUrl();
        }

        if (produto.getImagensUrls() != null){
            this.imagensUrls = produto.getImagensUrls();
        }

        if (produto.getTags() != null){
            this.tags = produto.getTags();
        }

        if (produto.getCoresSelecionadas() != null){
            this.coresSelecionadas = produto.getCoresSelecionadas();
        }
    }


    public void addLead(Lead lead){
        leads.add(lead);
    }

    public void removeLead(Lead lead){
        this.leads = this.getLeads()
                .stream()
                .filter(l -> !l.getId().equals(lead.getId()))
                .collect(Collectors.toList());
    }
}