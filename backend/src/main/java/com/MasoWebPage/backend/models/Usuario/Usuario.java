package com.MasoWebPage.backend.models.Usuario;

import com.MasoWebPage.backend.api.dto.UsuarioDTO;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.swing.plaf.PanelUI;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of = {"login", "senha"})
@NoArgsConstructor
@Document("usuario")
public class Usuario implements UserDetails {
    @Id
    private String _id;
    private String login;
    private String senha;
    private Role role;
    private Boolean valido;

    public Usuario(String login, String senha, Role role, Boolean valido) {
        this.login = login;
        this.senha = senha;
        this.role = role;
        this.valido = valido;
    }

    public Usuario(UsuarioDTO usuario) {
        this.login = usuario.login();
        this.senha = usuario.senha();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role != null) {
            return List.of(new SimpleGrantedAuthority( role.name()));
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


}

