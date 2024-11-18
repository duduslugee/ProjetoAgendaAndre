package br.duduslugee.Agenda.service;

import br.duduslugee.Agenda.model.TipoAcesso;
import br.duduslugee.Agenda.repository.UsuarioRepository;
import br.duduslugee.Agenda.model.Usuario;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TipoAcessoService tipoAcessoService;

    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        usuario.setDtCadastro(LocalDateTime.now());
        return usuarioRepository.save(usuario);
    }

    public void adicionarUsuariosAleatorios() {
        List<TipoAcesso> tiposAcesso = tipoAcessoService.listarTodosTiposDeAcesso();
        if (tiposAcesso.isEmpty()) {
            throw new RuntimeException("Não existem tipos de acesso disponíveis.");
        }

        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            Usuario usuario = new Usuario();
            usuario.setNomeUsuario("usuario" + (i + 1));
            usuario.setSenha("senha" + (i + 1));  // Gerando senhas simples para o exemplo
            usuario.setTipoAcesso(tiposAcesso.get(random.nextInt(tiposAcesso.size()))); // Atribuindo um tipo de acesso aleatório
            usuario.setDtCadastro(LocalDateTime.now());

            // Salvando o usuário no banco de dados
            usuarioRepository.save(usuario);
        }
    }

    @PostConstruct
    public void iniciar() {
        tipoAcessoService.adicionarTiposDeAcesso();
        adicionarUsuariosAleatorios();
    }

    @Transactional
    public Usuario atualizarUsuario(Integer id, Usuario usuarioAtualizado) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuarioExistente.setNomeUsuario(usuarioAtualizado.getNomeUsuario());
        usuarioExistente.setSenha(usuarioAtualizado.getSenha());
        usuarioExistente.setTipoAcesso(usuarioAtualizado.getTipoAcesso());

        return usuarioRepository.save(usuarioExistente);
    }

    @Transactional
    public void excluirUsuario(int id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }
    }
}
