package com.MasoWebPage.backend.repositories;

import com.MasoWebPage.backend.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends MongoRepository<Produto,String> {

}
