package com.MasoWebPage.backend.api.dto.administrador;

import com.MasoWebPage.backend.api.dto.UsuarioDTOAtualizacao;
import com.fasterxml.jackson.annotation.JsonAlias;

public record AdministradorAtualizar(

        String nome,

        String cpf,

        String email,

        @JsonAlias("usuario")
        UsuarioDTOAtualizacao usuario
) {
}
