package com.MasoWebPage.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter//{
@Setter//       anotacoes do lombok sao apenas para simplificar codigo, nao afeta comportamento!!!!!!
@AllArgsConstructor//
@NoArgsConstructor//}

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

@Entity
@Table
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cor; // conversa com felipe sobre como funciona as cores
    private String url_imagem;
}
