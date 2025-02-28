package com.MasoWebPage.backend.services;

import com.MasoWebPage.backend.api.dto.lead.LeadDTOSemUsuario;
import com.MasoWebPage.backend.services.emailValidacao.EmailValidacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmailServiceTest {
    @Autowired
    private EmailValidacaoService emailService;

    @Test
    void enviarEmail() {

        emailService.enviarEmailDeValidacao("E O ZEDON DO PAI KKKKKKKKKKKK !!!!",
                new LeadDTOSemUsuario("fernandorifpro@gmail.com", "ze"));



    }
}