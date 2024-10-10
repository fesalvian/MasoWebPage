package com.MasoWebPage.backend.models;

import com.MasoWebPage.backend.models.Usuario.Usuario;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter//{
@Setter//       anotacoes do lombok sao apenas para simplificar codigo, nao afeta comportamento!!!!!!
@AllArgsConstructor//
@NoArgsConstructor//}

@ToString
@Document("administrador")
public class Administrador {

    @Id
    private String id;
    private String nome;

    @Field("usuario")
    private Usuario usuario;
}
