package com.MasoWebPage.backend.api.dto;

import com.MasoWebPage.backend.models.Usuario.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioDTO(
        @NotBlank
        String login,
        @NotBlank
        String senha

) {
    public UsuarioDTO(Usuario usuario) {
        this(usuario.getLogin(), usuario.getPassword());
    }
}
