package com.MasoWebPage.backend.models;

import com.MasoWebPage.backend.api.dto.ProdutoDTO;
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
    private String cor;
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

    public Produto(ProdutoDTO dto){
        this.nome = dto.nome();
        this.cor = dto.cor();
        this.urlImagem = dto.urlImagem();

    }
    public void addLead(Lead lead){
        leads.add(lead);
    }
}
