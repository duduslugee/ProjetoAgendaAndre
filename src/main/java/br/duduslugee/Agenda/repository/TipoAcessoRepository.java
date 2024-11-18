package br.duduslugee.Agenda.repository;

import br.duduslugee.Agenda.model.TipoAcesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoAcessoRepository extends JpaRepository<TipoAcesso, Integer> {
}
