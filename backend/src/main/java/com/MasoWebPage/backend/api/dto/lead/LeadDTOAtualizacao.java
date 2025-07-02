package com.MasoWebPage.backend.api.dto.lead;

import com.MasoWebPage.backend.models.Lead;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LeadDTOAtualizacao(
        String nome,

        String email





) {


        public LeadDTOAtualizacao(Lead lead) {
                this(lead.getNome(), lead.getEmail()     );
        }
}
