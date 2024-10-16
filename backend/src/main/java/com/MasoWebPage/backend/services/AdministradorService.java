package com.MasoWebPage.backend.services;

import com.MasoWebPage.backend.exceptions.UsuarioException;
import com.MasoWebPage.backend.models.Administrador;
import com.MasoWebPage.backend.models.Usuario.Roles;
import com.MasoWebPage.backend.models.Usuario.Usuario;
import com.MasoWebPage.backend.repositories.AdministradorRepository;
import com.MasoWebPage.backend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static org.apache.coyote.http11.Constants.a;

@Service
public class AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Administrador salvar(Administrador adm){
        valida(adm);

        String senhaC = encoder.encode(adm.getUsuario().getPassword());

        adm.getUsuario().setSenha(senhaC);
        adm.getUsuario().setRole(Roles.ADM);

        Usuario usuarioSave = usuarioRepository.save(adm.getUsuario());
        adm.setUsuario(usuarioSave);
        return administradorRepository.save(adm);

    }

    private void valida(Administrador adm){
        if(adm.getUsuario().getLogin() == null || adm.getUsuario().getSenha() == null){
            throw new UsuarioException("login ou senha nulos");
        }
    }
}
