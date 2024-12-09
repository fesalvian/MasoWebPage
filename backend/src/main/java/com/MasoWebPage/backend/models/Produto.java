package com.MasoWebPage.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
}
