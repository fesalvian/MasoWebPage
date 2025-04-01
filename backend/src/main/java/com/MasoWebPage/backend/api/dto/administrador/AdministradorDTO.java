package com.MasoWebPage.backend.api.dto.administrador;

import com.MasoWebPage.backend.api.dto.UsuarioDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdministradorDTO(
        @NotBlank
        String nome,
        @NotBlank
        String cpf,
        @NotNull
        UsuarioDTO usuario

) {
}
