package com.MasoWebPage.backend.models;

import com.MasoWebPage.backend.api.dto.administrador.AdministradorDTO;
import com.MasoWebPage.backend.models.Usuario.Role;
import com.MasoWebPage.backend.models.Usuario.Usuario;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter//{
@Setter//       anotacoes do lombok sao apenas para simplificar codigo, nao afeta comportamento!!!!!!
@AllArgsConstructor//


@ToString
@Document("administrador")
public class Administrador {

    @Id
    private String id;
    private String nome;

    @Field("usuario")
    private Usuario usuario;

    public Administrador(){}
    public Administrador(AdministradorDTO dados){
        this.nome = dados.nome();
        Usuario usuarioAux = new Usuario(dados.usuario());
        usuarioAux.setRole(Role.ADM);
        this.usuario = usuarioAux;
    }

    public void atualizar(Administrador adm) {
        if(adm.getNome() != null && !adm.nome.trim().isBlank()) this.nome = adm.getNome();

    }
}
