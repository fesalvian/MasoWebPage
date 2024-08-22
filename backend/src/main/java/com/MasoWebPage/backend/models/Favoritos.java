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
public class Favoritos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;
    @ManyToOne
    @JoinColumn(name = "lead_id", nullable = false)
    private Lead lead;

    public Favoritos(Produto produto, Lead lead) {
        this.produto  = produto;
        this.lead = lead;
    }
}
