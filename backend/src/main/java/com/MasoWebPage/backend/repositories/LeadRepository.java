package com.MasoWebPage.backend.repositories;

import com.MasoWebPage.backend.models.Lead;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeadRepository extends MongoRepository<Lead,String> {
    Lead findByUsuarioLogin(String login);

    Optional<Lead> findBytokenDeValidacao(String token);

    Optional<Lead>  findByUsuarioId(String leadId);
}
