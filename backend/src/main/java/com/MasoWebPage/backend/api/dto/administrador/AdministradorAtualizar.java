package com.MasoWebPage.backend.api.dto.administrador;

import com.MasoWebPage.backend.api.dto.UsuarioDTOAtualizacao;
import com.fasterxml.jackson.annotation.JsonAlias;

public record AdministradorAtualizar(

        String nome,

        String cpf,

        @JsonAlias("usuario")
        UsuarioDTOAtualizacao usuario
) {
}
