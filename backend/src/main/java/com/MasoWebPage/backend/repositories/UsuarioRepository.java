package com.MasoWebPage.backend.repositories;

import com.MasoWebPage.backend.models.Administrador;
import com.MasoWebPage.backend.models.Usuario.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario,String> {

    Usuario getByLogin(String login);

    Optional<Usuario> findByLogin(String login);

    Optional<Usuario> findByLoginAndSenha(String username);
}
