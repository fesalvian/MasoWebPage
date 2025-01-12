package com.MasoWebPage.backend.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EmailServiceTest {
    @Autowired
    private EmailService emailService;

    @Test
    void enviarEmail() {
        emailService.enviarEmail("fernandorifpro@gmail.com", "primeiro email enviado por codigo", "HELLO WORLD");
    }
}