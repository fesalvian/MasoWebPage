package com.MasoWebPage.backend.api.dto.lead;

import com.MasoWebPage.backend.api.dto.UsuarioDTO;
import com.MasoWebPage.backend.models.Lead;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LeadDTOSemUsuario(
        @Email
        @NotBlank
        String email,

        @NotBlank
        String nome



) {


        public LeadDTOSemUsuario(Lead lead) {
                this(lead.getEmail(), lead.getNome());
        }
}
