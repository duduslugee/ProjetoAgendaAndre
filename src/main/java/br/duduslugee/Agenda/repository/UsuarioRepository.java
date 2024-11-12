package br.duduslugee.Agenda.repository;

import br.duduslugee.Agenda.model.TipoAcesso;
import br.duduslugee.Agenda.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByUsuario(String usuario);
    List<Usuario> findByTipoAcesso(TipoAcesso tipoAcesso);

    List<Usuario> findByUsuarioContainingIgnoreCase(String usuario);

    List<Usuario> findByTipoAcessoDescricaoContainingIgnoreCase(String tipoAcesso);
}