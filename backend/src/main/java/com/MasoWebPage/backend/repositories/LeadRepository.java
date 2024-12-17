package com.MasoWebPage.backend.repositories;

import com.MasoWebPage.backend.models.Lead;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeadRepository extends MongoRepository<Lead,String> {
    Lead findByUsuarioLogin(String login);
}
