package com.MasoWebPage.backend.models.Usuario;

import com.MasoWebPage.backend.api.dto.UsuarioDTO;
import com.MasoWebPage.backend.api.dto.UsuarioDTOAtualizacao;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.swing.plaf.PanelUI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
@EqualsAndHashCode(of = {"login", "senha"})
@NoArgsConstructor
@Document("usuario")
public class Usuario implements UserDetails {
    @Id
    private String id;
    private String login;
    private String senha;
    @Field("role")
    private ArrayList<String> roles = new ArrayList<>();
    private Boolean valido;

    public Usuario(String login, String senha, Boolean valido) {
        this.login = login;
        this.senha = senha;
        this.valido = valido;
    }

    public Usuario(UsuarioDTO usuario) {
        this.login = usuario.login();
        this.senha = usuario.senha();
    }

    public void addRole(Role role) {
        this.roles.add(role.name());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles != null) {
            return new ArrayList<>(roles.stream().map(role -> new SimpleGrantedAuthority(role)).toList());
        }
        return List.of();
    }



    // Método abstrato para retornar a role

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return valido;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", login='" + login + '\'' +
                ", senha='" + senha + '\'' +
                ", roles=" + roles +
                ", valido=" + valido +
                '}';
    }

    public void atualiza(UsuarioDTOAtualizacao usuario) {
        if(usuario.login() != null && !usuario.login().trim().isBlank()){
            this.login = usuario.login();
        }
        if(usuario.senha() != null && !usuario.senha().trim().isBlank()){
            this.senha = usuario.senha();
        }
    }
}

