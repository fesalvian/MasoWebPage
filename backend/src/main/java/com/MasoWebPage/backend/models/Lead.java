package com.MasoWebPage.backend.models;

import com.MasoWebPage.backend.api.dto.lead.LeadDTO;
import com.MasoWebPage.backend.models.Usuario.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("lead")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lead {

    @Id
    private String id;
    private String email;
    @Field("usuario")
    private Usuario usuario;


    public void atualiza(LeadDTO dados) {
        if(dados.email() != null && !dados.email().trim().isBlank()) this.email = dados.email();

    }
}
