package com.MasoWebPage.backend.repositories;

import com.MasoWebPage.backend.models.Administrador;
import com.MasoWebPage.backend.models.Usuario.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministradorRepository extends MongoRepository<Administrador,String> {
    Administrador getByUsuario(Usuario usuario);


    Optional<Administrador> findByUsuario(Usuario usuario);

    // Mudar para Optional
    Optional<Administrador> findByCpf(String cpf);

    // Mantém o booleano se precisar
    boolean existsByCpf(String cpf);
}
