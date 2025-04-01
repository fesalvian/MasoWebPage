package com.MasoWebPage.backend.models;

import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

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
    private String cor; // conversa com felipe sobre como funciona as cores
    private String urlImagem;
    @DBRef
    private List<Lead> leads  = new ArrayList<>();


    public void atualiza(Produto produto) {
        if (produto.getNome() != null && produto.getNome().trim().isBlank() == false){
            this.nome =produto.getNome();
        }
        if (produto.getCor() != null && produto.getCor().trim().isBlank() == false){
            this.cor = produto.getCor();
        }
        if (produto.getUrlImagem() != null && produto.getUrlImagem().trim().isBlank() == false){
            this.urlImagem = produto.getUrlImagem();
        }

    }
    public void addLead(Lead lead){
        leads.add(lead);
    }
}
