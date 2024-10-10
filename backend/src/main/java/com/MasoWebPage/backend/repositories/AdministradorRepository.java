package com.MasoWebPage.backend.repositories;

import com.MasoWebPage.backend.models.Administrador;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministradorRepository extends MongoRepository<Administrador,String> {
    Administrador getByUsuarioLogin(String Login);
}
