package com.MasoWebPage.backend.api.dto;

import jakarta.validation.constraints.NotBlank;

public record UsuarioDTO(
        @NotBlank
        String login,
        @NotBlank
        String senha
) {
}
