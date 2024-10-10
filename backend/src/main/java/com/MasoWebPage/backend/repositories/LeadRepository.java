package com.MasoWebPage.backend.repositories;

import com.MasoWebPage.backend.models.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeadRepository extends JpaRepository<Lead,Long> {
    Lead findByEmail(String email);

    boolean existsByEmail(String email);
}
