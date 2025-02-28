package com.MasoWebPage.backend.services;

import com.MasoWebPage.backend.api.dto.lead.LeadDTO;
import com.MasoWebPage.backend.api.dto.lead.LeadDTOSemUsuario;
import com.MasoWebPage.backend.exceptions.UsuarioException;
import com.MasoWebPage.backend.models.Lead;
import com.MasoWebPage.backend.models.Usuario.Role;
import com.MasoWebPage.backend.models.Usuario.Usuario;
import com.MasoWebPage.backend.repositories.LeadRepository;
import com.MasoWebPage.backend.repositories.UsuarioRepository;
import com.MasoWebPage.backend.services.emailValidacao.EmailValidacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LeadService {
    @Autowired
    private LeadRepository leadRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailValidacaoService emailValidacaoService;


    public  Lead salvar(Lead lead) throws UsuarioException {
        //validacoes de dados
        valida(lead);

        //criptografa a senha
        String senhaC = encoder.encode(lead.getUsuario().getPassword());
        lead.getUsuario().setSenha(senhaC);

        //set da role de usuario comun
        lead.getUsuario().setRole(Role.COMUM);

        //salva anteriormente o usuario
        Usuario usuarioSave = usuarioRepository.save(lead.getUsuario());

        //set do usuario ao lead
        lead.setUsuario(usuarioSave);

        //salva lead no banco
        Lead leadSalvo = leadRepository.save(lead);

        //validando email

        emailValidacaoService.enviarEmailDeValidacao(lead.getTokenDeValidacao(), new LeadDTOSemUsuario(lead));


        return leadSalvo;

    }

    private void valida(Lead lead) {
        if (lead.getUsuario().getLogin() == null || lead.getUsuario().getSenha() == null) {
            throw new UsuarioException("login ou senha nulos");
        }
    }

    public Lead atualizar(LeadDTO dados, String login) {
        Lead leadCarregado = leadRepository.findByUsuarioLogin(login);
        leadCarregado.atualiza(dados);
        return leadRepository.save(leadCarregado);
    }


    public Lead validarEmail(String token) {
        Optional<Lead> leadOpt = leadRepository.findBytokenDeValidacao(token);
        if (!leadOpt.isEmpty()) {

            Lead lead = leadOpt.get();

            if (lead.isTokenValido()) {
                lead.getUsuario().setValido(true);
                lead.setTokenDeValidacao(null);
                return lead;
            }else{
                throw new UsuarioException("Token invalido");
            }
        }else{
            throw new UsuarioException("Token nao encontrado");
        }

    }

}
