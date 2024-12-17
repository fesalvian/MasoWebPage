package com.MasoWebPage.backend.services;

import com.MasoWebPage.backend.api.dto.lead.LeadDTO;
import com.MasoWebPage.backend.exceptions.UsuarioException;
import com.MasoWebPage.backend.models.Administrador;
import com.MasoWebPage.backend.models.Lead;
import com.MasoWebPage.backend.models.Produto;
import com.MasoWebPage.backend.models.Usuario.Role;
import com.MasoWebPage.backend.models.Usuario.Usuario;
import com.MasoWebPage.backend.repositories.LeadRepository;
import com.MasoWebPage.backend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LeadService {
    @Autowired
    private LeadRepository leadRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private UsuarioRepository usuarioRepository;
    public Lead salvar(Lead lead) throws UsuarioException {
        valida(lead);

        String senhaC = encoder.encode(lead.getUsuario().getPassword());

        lead.getUsuario().setSenha(senhaC);
        lead.getUsuario().setRole(Role.COMUM);

        Usuario usuarioSave = usuarioRepository.save(lead.getUsuario());
        lead.setUsuario(usuarioSave);
        return leadRepository.save(lead);

    }
    private void valida(Lead lead){
        if(lead.getUsuario().getLogin() == null || lead.getUsuario().getSenha() == null){
            throw new UsuarioException("login ou senha nulos");
        }
    }

    public Lead atualizar(LeadDTO dados, String login) {
        Lead leadCarregado = leadRepository.findByUsuarioLogin(login);
        leadCarregado.atualiza(dados);
        return leadRepository.save(leadCarregado);

    }
}
