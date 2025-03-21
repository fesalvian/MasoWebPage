package com.MasoWebPage.backend.api.dto.lead;

import com.MasoWebPage.backend.api.dto.UsuarioDTO;
import com.MasoWebPage.backend.models.Lead;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LeadDTO(
        @Email
        @NotBlank
        String email,

        @NotBlank
        String nome,
        @NotNull
        UsuarioDTO usuario

) {


        public LeadDTO(Lead lead) {
                this(lead.getEmail(), lead.getNome(), new UsuarioDTO(lead.getUsuario()));
        }
}
