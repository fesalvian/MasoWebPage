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

import java.time.LocalDateTime;
import java.util.UUID;

@Document("lead")
@Getter
@Setter
@NoArgsConstructor
public class Lead {

    @Id
    private String id;
    private String nome;
    private String email;
    @Field("usuario")
    private Usuario usuario;
    private String tokenDeValidacao;
    private LocalDateTime tokenExpiracao;
    public void atualiza(LeadDTO dados) {
        if(dados.email() != null && !dados.email().trim().isBlank()) this.email = dados.email();
        if(dados.nome() != null && !dados.nome().trim().isBlank()) this.nome = dados.nome();
    }

    public Lead(String nome, String email, Usuario usuario){
        this.nome = nome;
        this.email = email;
        this.usuario = usuario;
    }
    public void geraTokenValidacao(){
        this.tokenDeValidacao = UUID.randomUUID().toString();
        this.tokenExpiracao = LocalDateTime.now().plusHours(24); // E
    }


    public boolean isTokenValido() {
        return this.tokenDeValidacao != null && LocalDateTime.now().isBefore(this.tokenExpiracao);
    }
}
