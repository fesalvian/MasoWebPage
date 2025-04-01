package com.MasoWebPage.backend.services;

import com.MasoWebPage.backend.exceptions.UsuarioException;
import com.MasoWebPage.backend.models.Administrador;
import com.MasoWebPage.backend.models.Usuario.Role;
import com.MasoWebPage.backend.models.Usuario.Usuario;
import com.MasoWebPage.backend.repositories.AdministradorRepository;
import com.MasoWebPage.backend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Administrador salvar(Administrador adm) throws UsuarioException{
        valida(adm);

        String senhaC = encoder.encode(adm.getUsuario().getPassword());

        adm.getUsuario().setSenha(senhaC);
        adm.getUsuario().setRole(Role.ADM);

        Usuario usuario = adm.getUsuario();
        usuario.setValido(true);
        Usuario usuarioSave = usuarioRepository.save(usuario);
        adm.setUsuario(usuarioSave);
        return administradorRepository.save(adm);

    }
    public Administrador atualizar(Administrador adm, String login) throws Exception {
        var find = administradorRepository.findByUsuarioLogin(login);
        if(find.isPresent()){
            Administrador administrador = find.get();
            administrador.atualizar(adm);
            return administradorRepository.save(administrador);
        }else{
            throw new UsuarioException("login nao encontrado");
        }
    }

    private void valida(Administrador adm) throws UsuarioException{
        if(adm.getUsuario().getLogin() == null || adm.getUsuario().getSenha() == null){
            throw new UsuarioException("login ou senha nulos");
        }
    }
}
