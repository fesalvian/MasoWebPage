package com.MasoWebPage.backend.services;

import com.MasoWebPage.backend.api.dto.lead.LeadDTO;
import com.MasoWebPage.backend.api.dto.lead.LeadDTOAtualizacao;
import com.MasoWebPage.backend.exceptions.UsuarioException;
import com.MasoWebPage.backend.models.Lead;
import com.MasoWebPage.backend.models.Usuario.Usuario;
import com.MasoWebPage.backend.repositories.LeadRepository;
import com.MasoWebPage.backend.repositories.UsuarioRepository;
import com.MasoWebPage.backend.services.emailValidacao.EmailValidacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LeadService{
    @Autowired
    private LeadRepository leadRepository;


    @Autowired
    private EmailValidacaoService emailValidacaoService;

    public LeadService(LeadRepository leadRepository, EmailValidacaoService emailValidacaoService){
        this.leadRepository = leadRepository;
        this.emailValidacaoService = emailValidacaoService;
    }
    public  Lead salvar(LeadDTO dados) throws UsuarioException {
        Lead lead = new Lead();
        lead.setNome(dados.nome());
        lead.setEmail(dados.email());
        lead.geraTokenValidacao();
        lead.setValido(false);
        Lead leadSalvo;
        //salva lead no banco
        if(!leadRepository.existsByEmail(lead.getEmail())) {
            leadSalvo = leadRepository.save(lead);
        }else{
            throw new UsuarioException("email existente");
        }
        //validando email
        emailValidacaoService.enviarEmailDeValidacao(lead.getTokenDeValidacao(), new LeadDTO(lead));


        return leadSalvo;

    }



    public Lead atualizar(LeadDTOAtualizacao dados, String email) {
        Lead leadCarregado = leadRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioException("Lead não encontrado"));

        boolean emailMudou = !leadCarregado.getEmail().equals(dados.email());

        leadCarregado.atualiza(dados);

        if (emailMudou) {
            if (leadRepository.existsByEmail(dados.email())) {
                throw new UsuarioException("E-mail já em uso");
            }
            leadCarregado.geraTokenValidacao();
            emailValidacaoService.enviarEmailDeValidacao(
                    leadCarregado.getTokenDeValidacao(),
                    new LeadDTO(leadCarregado)
            );
            leadCarregado.setValido(false);
        }

        return leadRepository.save(leadCarregado);
    }


    public Lead validarEmail(String token) {
        Lead lead = leadRepository.findBytokenDeValidacao(token)
                .orElseThrow(() -> new UsuarioException("Token não encontrado"));

        if (!lead.isTokenValido()) {
            throw new UsuarioException("Token inválido");
        }

        lead.setValido(true);
        lead.setTokenDeValidacao(null);
        return leadRepository.save(lead);

    }

    public Lead buscaLead(String email){
        Lead lead = leadRepository.findByEmail(email).get();
        if(lead.getValido()){
            return lead;
        }else{
            throw new UsuarioException("usuario nao encontrado");
        }
    }


    public void deletar(String username) {
        Lead lead = leadRepository.findByEmail(username).get();
        lead.setValido(false);
        leadRepository.save(lead);
    }

}
