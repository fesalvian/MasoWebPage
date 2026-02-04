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

import java.util.List;
import java.util.Optional;

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

        // Verifica se já existe administrador com este CPF
        Optional<Administrador> existenteOpt = administradorRepository.findByCpf(adm.getCpf());

        if (existenteOpt.isPresent()) {
            // ADMINISTRADOR EXISTENTE - RECUPERAÇÃO
            Administrador existente = existenteOpt.get();

            // Atualiza dados do administrador existente
            existente.setNome(adm.getNome());
            existente.setEmail(adm.getEmail());

            // Recupera/atualiza usuário
            Usuario usuarioExistente = existente.getUsuario();
            usuarioExistente.setValido(true);

            // Se o login foi alterado, atualiza
            if (!usuarioExistente.getLogin().equals(adm.getUsuario().getLogin())) {
                usuarioExistente.setLogin(adm.getUsuario().getLogin());
            }

            // Atualiza senha se fornecida
            if (adm.getUsuario().getPassword() != null && !adm.getUsuario().getPassword().isEmpty()) {
                String novaSenha = encoder.encode(adm.getUsuario().getPassword());
                usuarioExistente.setSenha(novaSenha);
            }

            // Garante que tem a role ADM
            if (!usuarioExistente.getRoles().contains(Role.ADM)) {
                usuarioExistente.addRole(Role.ADM);
            }

            // Salva primeiro o usuário
            Usuario usuarioSalvo = usuarioRepository.save(usuarioExistente);
            existente.setUsuario(usuarioSalvo);

            // Salva o administrador atualizado
            return administradorRepository.save(existente);

        } else {
            // NOVO ADMINISTRADOR - CRIAÇÃO
            String senhaCriptografada = encoder.encode(adm.getUsuario().getPassword());
            adm.getUsuario().setSenha(senhaCriptografada);
            adm.getUsuario().addRole(Role.ADM);
            adm.getUsuario().setValido(true);

            // Salva primeiro o usuário
            Usuario usuarioSalvo = usuarioRepository.save(adm.getUsuario());
            adm.setUsuario(usuarioSalvo);

            // Salva o administrador
            return administradorRepository.save(adm);
        }
    }

    private Administrador atualizarAdministradorExistente(Administrador existente, Administrador novosDados) {
        // Atualiza dados básicos
        existente.setNome(novosDados.getNome());
        existente.setEmail(novosDados.getEmail());

        // Atualiza usuário
        Usuario usuario = existente.getUsuario();
        usuario.setValido(true);
        usuario.setLogin(novosDados.getUsuario().getLogin());

        // Atualiza senha se fornecida
        if (novosDados.getUsuario().getPassword() != null &&
                !novosDados.getUsuario().getPassword().isEmpty()) {
            usuario.setSenha(encoder.encode(novosDados.getUsuario().getPassword()));
        }

        // Garante role ADM
        if (!usuario.getRoles().contains(Role.ADM)) {
            usuario.addRole(Role.ADM);
        }

        // Salva
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        existente.setUsuario(usuarioSalvo);

        return administradorRepository.save(existente);
    }

    private Administrador criarNovoAdministrador(Administrador adm) {
        // Criptografa senha
        adm.getUsuario().setSenha(encoder.encode(adm.getUsuario().getPassword()));
        adm.getUsuario().addRole(Role.ADM);
        adm.getUsuario().setValido(true);

        // Salva usuário primeiro
        Usuario usuarioSalvo = usuarioRepository.save(adm.getUsuario());
        adm.setUsuario(usuarioSalvo);

        // Salva administrador
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
       return administradorRepository.getByUsuario(usuarioRepository.getByLogin(login));
    }

    public List<Administrador> buscaTodos() {
        return administradorRepository.findAll();
    }
}
