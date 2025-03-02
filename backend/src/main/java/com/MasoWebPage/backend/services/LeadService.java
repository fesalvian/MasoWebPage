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


    public  Lead salvar( LeadDTO dados) throws UsuarioException {
        var usuarioDto = dados.usuario();

        String encode = encoder.encode(usuarioDto.senha());

        Usuario usuario = new Usuario(usuarioDto.login(), encode, Role.COMUM, false);
        var lead =  new Lead();

        lead.setNome(dados.nome());
        lead.setEmail(dados.email());
        lead.geraTokenValidacao();



        Usuario usuarioSave = usuarioRepository.save(usuario);

        lead.setUsuario(usuarioSave);

        //salva lead no banco
        Lead leadSalvo = leadRepository.save(lead);

        //validando email

        emailValidacaoService.enviarEmailDeValidacao(lead.getTokenDeValidacao(), new LeadDTOSemUsuario(lead));


        return leadSalvo;

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
                usuarioRepository.save(lead.getUsuario());
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
