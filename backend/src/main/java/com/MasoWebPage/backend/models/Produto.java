package com.MasoWebPage.backend.models;

import com.MasoWebPage.backend.api.dto.ProdutoDTO;
import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter//{
@Setter//       anotacoes do lombok sao apenas para simplificar codigo, nao afeta comportamento!!!!!!
@AllArgsConstructor//
@NoArgsConstructor//}
@ToString
@Document("produto")
public class Produto {

    @Id
    private String id;
    private String nome;
    private String descricao;
    private String colecao;
    private List<String> urlsImagens = new ArrayList<>();
    @DBRef
    private List<Lead> leads  = new ArrayList<>();


    public void atualiza(Produto produto) {
        if (produto.getNome() != null && produto.getNome().trim().isBlank() == false){
            this.nome =produto.getNome();
        }
        if (produto.getColecao() != null && produto.getColecao().trim().isBlank() == false){
            this.colecao = produto.getColecao();
        }


    }

    public Produto(ProdutoDTO dto){
        this.nome = dto.nome();
        this.colecao = dto.colecao();
        this.urlsImagens = dto.urlsImagens ();

    }
    public void addLead(Lead lead){
        leads.add(lead);
    }
    public void removeLead(Lead lead){
        this.leads = this.getLeads().stream().filter(l -> !l.getId().equals(lead.getId())).collect(Collectors.toList());

    }
}
