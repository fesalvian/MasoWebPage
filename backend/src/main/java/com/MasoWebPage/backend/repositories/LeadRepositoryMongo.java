package com.MasoWebPage.backend.repositories;

import com.MasoWebPage.backend.models.LeadMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeadRepositoryMongo extends MongoRepository<LeadMongo, String> {

}
