package com.MasoWebPage.backend.models;

import com.MasoWebPage.backend.api.dto.administrador.AdministradorDTO;
import com.MasoWebPage.backend.models.Usuario.Role;
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
    @Field("usuario_id")
    @DBRef
    private Usuario usuario;

    public Administrador(){}
    public Administrador(AdministradorDTO dados) throws Exception {
        if(ValidaCPF.isValido(dados.cpf())){
        this.nome = dados.nome();
        Usuario usuarioAux = new Usuario(dados.usuario());
        usuarioAux.setRole(Role.ADM);
        this.usuario = usuarioAux;}
        else{
            throw new Exception("cpf invalido");
        }
    }

    public void atualizar(Administrador adm) throws Exception {
        if(adm.getNome() != null && !adm.getNome().trim().isBlank()) this.nome = adm.getNome();
        if(adm.getCpf() != null && !adm.getCpf().trim().isBlank()){
            if(ValidaCPF.isValido(adm.getCpf())) this.cpf = adm.getCpf();
            else throw new Exception("cpf invalido");
        }
    }
}
