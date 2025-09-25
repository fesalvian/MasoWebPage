package com.MasoWebPage.backend.services;

import com.MasoWebPage.backend.api.dto.lead.LeadDTO;
import com.MasoWebPage.backend.api.dto.lead.LeadDTOAtualizacao;
import com.MasoWebPage.backend.exceptions.UsuarioException;
import com.MasoWebPage.backend.models.Lead;
import com.MasoWebPage.backend.repositories.LeadRepository;
import com.MasoWebPage.backend.services.emailValidacao.EmailValidacaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class LeadServiceTest {


    @Mock
    private LeadRepository leadRepository;
    @Mock
    private EmailValidacaoService emailValidacaoService;
    private LeadService leadService;
    private Lead leadMock;

    private Lead leadValido;
    private Lead leadExistente;
    private String token;
    private LocalDateTime tokenExpiracao;
    @BeforeEach
    void setUp() {
        leadService = new LeadService(leadRepository, emailValidacaoService);

        leadMock = new Lead(); // ou use builder se tiver

        leadMock.setNome("test");
        leadMock.setEmail("test@test.com");

        leadExistente = new Lead("Nome Antigo", "email@teste.com");
        leadExistente.geraTokenValidacao();
        leadExistente.setValido(true);


        token = UUID.randomUUID().toString();
        tokenExpiracao = LocalDateTime.now().plusHours(24);
        leadValido = new Lead("Teste", "teste@email.com");
        leadValido.setTokenDeValidacao(token);
        leadValido.setTokenExpiracao(tokenExpiracao);
        leadValido.setValido(false);
    }

    @Test
    void salvar() {
        // Arrange
        when(leadRepository.existsByEmail(leadMock.getEmail())).thenReturn(false);
        when(leadRepository.save(any(Lead.class))).thenAnswer(invocation -> {
            Lead lead = invocation.getArgument(0);
            lead.setId("idtest1"); // simula o banco setando um ID
            return lead;
        });

        // Act
        Lead salvo = leadService.salvar(new LeadDTO(leadMock));

        // Assert
        assertNotNull(salvo);
        assertEquals("test", salvo.getNome());
        assertEquals("test@test.com", salvo.getEmail());
        assertNotNull(salvo.getTokenDeValidacao());
        assertFalse(salvo.getValido());
        verify(emailValidacaoService, times(1))
                .enviarEmailDeValidacao(anyString(), any(LeadDTO.class));
    }

    @Test
    void deveRetornaUsuarioException() {
        // Arrange
        when(leadRepository.existsByEmail(leadMock.getEmail())).thenReturn(true);

        // Act

        assertThrows(UsuarioException.class, () ->
                leadService.salvar(new LeadDTO(leadMock))
        );

        // Assert
        verify(leadRepository, never()).save(any());
        verify(emailValidacaoService, never()).enviarEmailDeValidacao(anyString(), any());
    }

    @Test
    void deveAtualizarNomeSemEnviarEmail() {
        // Arrange
        LeadDTOAtualizacao dados = new LeadDTOAtualizacao("Nome Novo", "email@teste.com");

        when(leadRepository.findByEmail("email@teste.com"))
                .thenReturn(Optional.of(leadExistente));
        when(leadRepository.save(any(Lead.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        // Act
        Lead atualizado = leadService.atualizar(dados, "email@teste.com");

        // Assert
        assertEquals("Nome Novo", atualizado.getNome());
        assertEquals("email@teste.com", atualizado.getEmail());

        verify(emailValidacaoService, never()).enviarEmailDeValidacao(any(), any());
        verify(leadRepository).save(leadExistente);
    }

    @Test
    void deveAtualizarEmailEGerarTokenEEnviarEmail() {
        // Arrange
        LeadDTOAtualizacao dados = new LeadDTOAtualizacao("Nome Novo", "novo@teste.com");

        when(leadRepository.findByEmail("email@teste.com"))
                .thenReturn(Optional.of(leadExistente));
        when(leadRepository.existsByEmail("novo@teste.com"))
                .thenReturn(false);
        when(leadRepository.save(any(Lead.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        // Act
        Lead atualizado = leadService.atualizar(dados, "email@teste.com");

        // Assert
        assertEquals("Nome Novo", atualizado.getNome());
        assertEquals("novo@teste.com", atualizado.getEmail());
        assertFalse(atualizado.isValido());
        assertNotNull(atualizado.getTokenDeValidacao());

        verify(emailValidacaoService).enviarEmailDeValidacao(anyString(), any(LeadDTO.class));
        verify(leadRepository).save(leadExistente);
    }

    @Test
    void deveLancarExcecaoQuandoEmailJaExiste() {
        // Arrange
        LeadDTOAtualizacao dados = new LeadDTOAtualizacao("Outro Nome", "jaexiste@teste.com");

        when(leadRepository.findByEmail("email@teste.com"))
                .thenReturn(Optional.of(leadExistente));
        when(leadRepository.existsByEmail("jaexiste@teste.com"))
                .thenReturn(true);

        // Act + Assert
        assertThrows(UsuarioException.class,
                () -> leadService.atualizar(dados, "email@teste.com"));

        verify(emailValidacaoService, never()).enviarEmailDeValidacao(any(), any());
        verify(leadRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoLeadNaoEncontrado() {
        // Arrange
        LeadDTOAtualizacao dados = new LeadDTOAtualizacao("Qualquer", "qualquer@teste.com");

        when(leadRepository.findByEmail("email@inexistente.com"))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(UsuarioException.class,
                () -> leadService.atualizar(dados, "email@inexistente.com"));

        verify(emailValidacaoService, never()).enviarEmailDeValidacao(any(), any());
        verify(leadRepository, never()).save(any());
    }


    @Test
    void deveValidarEmailComTokenValido() {

        when(leadRepository.findBytokenDeValidacao(token))
                .thenReturn(Optional.of(leadValido));
        when(leadRepository.save(leadValido)).thenReturn(leadValido);
        // isTokenValido() deve retornar true
        // supondo que ele já funcione assim para este lead
        Lead resultado = leadService.validarEmail(token);

        assertTrue(resultado.isValido());
        assertNull(resultado.getTokenDeValidacao());
        verify(leadRepository).save(leadValido);
    }

    @Test
    void deveLancarExcecaoQuandoTokenNaoEncontrado() {
        when(leadRepository.findBytokenDeValidacao("tokenInexistente"))
                .thenReturn(Optional.empty());

        UsuarioException ex = assertThrows(UsuarioException.class, () ->
                leadService.validarEmail("tokenInexistente"));

        assertEquals("Token não encontrado", ex.getMessage());
        verify(leadRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoTokenInvalido() {
        leadValido.setTokenDeValidacao("tokenExpirado");

        // Simula que o token existe
        when(leadRepository.findBytokenDeValidacao("tokenExpirado"))
                .thenReturn(Optional.of(leadValido));

        // aqui precisamos simular que o token é inválido
        // se isTokenValido() é baseado em regra de data, talvez precise ajustar
        // para este exemplo, vamos forçar o método a retornar false
        Lead spyLead = spy(leadValido);
        when(spyLead.isTokenValido()).thenReturn(false);

        when(leadRepository.findBytokenDeValidacao("tokenExpirado"))
                .thenReturn(Optional.of(spyLead));

        UsuarioException ex = assertThrows(UsuarioException.class, () ->
                leadService.validarEmail("tokenExpirado"));

        assertEquals("Token inválido", ex.getMessage());
        verify(leadRepository, never()).save(any());
    }


}