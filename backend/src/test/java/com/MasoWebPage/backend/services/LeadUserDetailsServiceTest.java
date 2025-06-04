package com.MasoWebPage.backend.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LeadUserDetailsServiceTest {

    @Autowired
    private LeadUserDetailsService userDetailsService;
    @Test
    void loadUserByUsername() {

        userDetailsService.loadUserByUsername("fernandorifpro@gmail.com");
    }
}