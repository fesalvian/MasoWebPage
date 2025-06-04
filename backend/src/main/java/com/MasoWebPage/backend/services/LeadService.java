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
public class LeadService {
    @Autowired
    private LeadRepository leadRepository;


    @Autowired
    private EmailValidacaoService emailValidacaoService;


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
        Lead leadCarregado = leadRepository.findByEmail(email).get();
        leadCarregado.atualiza(dados);
        return leadRepository.save(leadCarregado);
    }


    public Lead validarEmail(String token) {
        Optional<Lead> leadOpt = leadRepository.findBytokenDeValidacao(token);
        if (!leadOpt.isEmpty()) {
            Lead lead = leadOpt.get();

            if (lead.isTokenValido()) {
                lead.setValido(true);
                lead.setTokenDeValidacao(null);
                leadRepository.save(lead);
                return lead;
            }else{
                throw new UsuarioException("Token invalido");
            }
        }else{
            throw new UsuarioException("Token nao encontrado");
        }

    }


}
