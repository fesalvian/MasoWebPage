package com.MasoWebPage.backend.repositories;

import com.MasoWebPage.backend.models.Lead;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeadRepository extends  MongoRepository<Lead,String> {

    Optional<Lead> findBytokenDeValidacao(String token);


    Optional<Lead> findByEmail(String email);

    Optional<Lead> findByValidoTrue(String email);
    Boolean existsByEmail(String email);


}
