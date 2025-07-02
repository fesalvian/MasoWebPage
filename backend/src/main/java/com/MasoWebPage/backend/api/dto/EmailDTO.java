package com.MasoWebPage.backend.api.dto;

import jakarta.validation.constraints.Email;

public record EmailDTO(

        @Email String email
) {
}
