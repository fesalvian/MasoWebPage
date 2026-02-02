package com.MasoWebPage.backend.services;

import com.MasoWebPage.backend.api.dto.administrador.AdministradorAtualizar;
import com.MasoWebPage.backend.exceptions.UsuarioException;
import com.MasoWebPage.backend.models.Administrador;
import com.MasoWebPage.backend.models.Usuario.Role;
import com.MasoWebPage.backend.models.Usuario.Usuario;
import com.MasoWebPage.backend.repositories.AdministradorRepository;
import com.MasoWebPage.backend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Administrador salvar(Administrador adm) throws UsuarioException {
        valida(adm);

        String senhaC = encoder.encode(adm.getUsuario().getPassword());

        adm.getUsuario().setSenha(senhaC);

        adm.getUsuario().addRole(Role.ADM);

        Usuario usuario = adm.getUsuario();
        usuario.setValido(true);


        Usuario usuarioSave = usuarioRepository.save(usuario);
        adm.setUsuario(usuarioSave);

        return administradorRepository.save(adm);

    }

    @Transactional
    public Administrador atualizar(AdministradorAtualizar adm, String login) throws Exception {

        Usuario usuario = usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new UsuarioException("login nao encontrado"));

        Administrador administrador = administradorRepository
                .findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Administrador não encontrado"));

        administrador.atualizar(adm);

        if (adm.usuario() != null && adm.usuario().senha() != null
                && !adm.usuario().senha().isBlank()) {

            administrador.getUsuario()
                    .setSenha(encoder.encode(adm.usuario().senha()));
        }

        return administradorRepository.save(administrador);
    }




    private void valida(Administrador adm) throws UsuarioException {
        if (adm.getUsuario().getLogin() == null || adm.getUsuario().getSenha() == null) {
            throw new UsuarioException("login ou senha nulos");
        }
    }

    public void excluisaoLogica(String login) {
        Usuario byLogin = usuarioRepository.getByLogin(login);
        byLogin.setValido(false);
        usuarioRepository.save(byLogin);
    }

    public Administrador buscaAdmPorLogin(String login) {
        Usuario usuario = usuarioRepository.findByLogin(login).get();
        return administradorRepository.getByUsuario(usuario);
    }
}
