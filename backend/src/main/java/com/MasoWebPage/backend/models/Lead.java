package com.MasoWebPage.backend.models;

import com.MasoWebPage.backend.api.dto.lead.LeadDTO;
import com.MasoWebPage.backend.api.dto.lead.LeadDTOAtualizacao;
import com.MasoWebPage.backend.models.Usuario.Role;
import com.MasoWebPage.backend.models.Usuario.Usuario;
import com.MasoWebPage.backend.services.EmailService;
import com.MasoWebPage.backend.services.emailValidacao.EmailValidacaoService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Document("lead")
@Getter
@Setter
@NoArgsConstructor
public class Lead implements UserDetails {

    @Id
    private String id;
    private String nome;
    private String email;
    @DBRef
    private List<Produto> produtosFavoritos = new ArrayList<>();
    private String tokenDeValidacao;
    private LocalDateTime tokenExpiracao;
    private Boolean valido;

    public void atualiza(LeadDTOAtualizacao dados) {

        if (dados.nome() != null && !dados.nome().trim().isBlank()) this.nome = dados.nome();
        if (dados.email() != null && !dados.email().trim().isBlank()) {
            this.email = dados.email();
            this.valido = false;
            geraTokenValidacao();
            new EmailValidacaoService().enviarEmailDeValidacao(this.tokenDeValidacao, new LeadDTO(dados.email(),this.nome));
        }
    }

    public Lead(String nome, String email) {
        this.nome = nome;
        this.email = email;

    }

    public void geraTokenValidacao() {
        this.tokenDeValidacao = UUID.randomUUID().toString();
        this.tokenExpiracao = LocalDateTime.now().plusHours(24); // E
    }


    public boolean isTokenValido() {
        return this.tokenDeValidacao != null && LocalDateTime.now().isBefore(this.tokenExpiracao);
    }

    public void addProduto(Produto produto) {
        this.produtosFavoritos.add(produto);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return new ArrayList<>(){{{
           new SimpleGrantedAuthority(Role.LEAD.name());
       }}};
    }
    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
