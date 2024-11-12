package br.duduslugee.Agenda.service;

import br.duduslugee.Agenda.model.TipoAcesso;
import br.duduslugee.Agenda.repository.UsuarioRepository;
import br.duduslugee.Agenda.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Lista todos os usuários cadastrados.
     */
    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * Busca um usuário específico pelo seu ID.
     */
    public Optional<Usuario> buscarPorId(int id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Salva um novo usuário.
     */
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    /**
     * Atualiza um usuário existente.
     */
    @Transactional
    public Usuario atualizarUsuario(int id, Usuario usuarioAtualizado) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuarioExistente.setUsuario(usuarioAtualizado.getUsuario());
        usuarioExistente.setSenha(usuarioAtualizado.getSenha());
        usuarioExistente.setTipoAcesso(usuarioAtualizado.getTipoAcesso());

        return usuarioRepository.save(usuarioExistente);
    }

    /**
     * Exclui um usuário pelo seu ID.
     */
    @Transactional
    public void excluirUsuario(int id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }
    }

    /**
     * Busca usuários pelo nome de usuário.
     */
    public List<Usuario> buscarPorUsuario(String usuario) {
        return usuarioRepository.findByUsuarioContainingIgnoreCase(usuario);
    }

    /**
     * Busca usuários por tipo de acesso.
     */
    public List<Usuario> buscarPorTipoAcesso(TipoAcesso tipoAcesso) {
        return usuarioRepository.findByTipoAcesso(tipoAcesso);
    }
}

