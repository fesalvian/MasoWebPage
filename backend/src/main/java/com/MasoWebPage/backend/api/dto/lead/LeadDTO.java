package com.MasoWebPage.backend.api.dto.lead;

import com.MasoWebPage.backend.api.dto.UsuarioDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LeadDTO(
        @Email
        @NotBlank
        String email,
        @NotNull
        UsuarioDTO usuario

) {

}
