package com.MasoWebPage.backend.api.dto.lead;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LeadDTOIn(
        @Email
        @NotBlank
        String email

) {

}
