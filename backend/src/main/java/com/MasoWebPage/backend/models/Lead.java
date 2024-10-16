package com.MasoWebPage.backend.models;
import com.MasoWebPage.backend.api.dto.lead.LeadDTOIn;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter//{
@Setter//       anotacoes do lombok sao apenas para simplificar codigo, nao afeta comportamento!!!!!!
@AllArgsConstructor//
@NoArgsConstructor//}

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

@Entity
@Table
public class Lead {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email
    private String email;

    @Transient
    private List<Produto> favoritos;

    public Lead(LeadDTOIn lead) {
        if(lead.email() != null && !lead.email().isBlank()){
            this.email = lead.email();
        }
    }
}
