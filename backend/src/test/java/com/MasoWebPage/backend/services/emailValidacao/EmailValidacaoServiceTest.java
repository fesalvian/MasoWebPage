package com.MasoWebPage.backend.services.emailValidacao;

import com.MasoWebPage.backend.api.dto.lead.LeadDTO;
import com.MasoWebPage.backend.models.Lead;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class EmailValidacaoServiceTest {


    @Autowired
    private EmailValidacaoService emailValidacaoService;

    private Lead lead;
    private LeadDTO leadDTO;

    @Value("${email_teste_integrado}")
    private String emailTestIntegrado;

    @Value("${link_validacao_email}")
    private String linkValidacao;

    @BeforeEach
    void setUp() {
        lead = new Lead("teste", emailTestIntegrado);
        leadDTO = new LeadDTO(lead);
    }

    @Test
    void deveExecutarEnvioDeEmailSemErro() {


        // O teste vai apenas garantir que o método executa sem lançar exceção
        assertDoesNotThrow(() -> {

            emailValidacaoService.enviarEmailDeValidacao(lead.getTokenDeValidacao(), leadDTO);
        });


    }
}
