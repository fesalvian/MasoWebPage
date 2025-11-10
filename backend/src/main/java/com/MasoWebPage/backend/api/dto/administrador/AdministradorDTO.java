package com.MasoWebPage.backend.api.dto.administrador;

import com.MasoWebPage.backend.api.dto.UsuarioDTO;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdministradorDTO(
        @NotBlank
        String nome,
        @NotBlank
        String cpf,
        @NotNull
        @JsonAlias("usuario")
        UsuarioDTO usuario

) {
}
