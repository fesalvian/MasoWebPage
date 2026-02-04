package com.MasoWebPage.backend.models;

import com.MasoWebPage.backend.api.dto.administrador.AdministradorAtualizar;
import com.MasoWebPage.backend.api.dto.administrador.AdministradorDTO;
import com.MasoWebPage.backend.models.Usuario.Usuario;
import com.MasoWebPage.backend.utils.ValidaCPF;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
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
    private String cpf;
    private String email;

    @Field("usuario_id")
    @DBRef
    private Usuario usuario;

    public Administrador() {
    }

    public Administrador(AdministradorDTO dados) throws Exception {
        //ValidaCPF.isValido(dados.cpf())
        if (true) {
            this.nome = dados.nome();
            this.cpf = dados.cpf();
            this.email = dados.email();
            Usuario usuarioAux = new Usuario(dados.usuario());

            this.usuario = usuarioAux;
        } else {
            throw new Exception("cpf invalido");
        }
    }

    public void atualizar(AdministradorAtualizar adm) throws Exception {
        if (adm.nome() != null && !adm.nome().trim().isBlank()) this.nome = adm.nome();
        if (adm.email() != null && !adm.email().trim().isBlank()) this.email = adm.email();
        if (adm.cpf() != null && !adm.cpf().trim().isBlank()) {
            if (ValidaCPF.isValido(adm.cpf())) this.cpf = adm.cpf();
            else throw new Exception("cpf invalido");
        }
        if (adm.usuario() != null) {
            this.usuario.atualiza(adm.usuario());
        }
    }

    @Override
    public String toString() {
        return "Administrador{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", usuario=" + usuario +
                '}';
    }
}
