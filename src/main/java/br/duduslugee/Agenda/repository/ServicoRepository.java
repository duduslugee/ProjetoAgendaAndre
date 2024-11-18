package br.duduslugee.Agenda.repository;

import br.duduslugee.Agenda.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Integer> {
    List<Servico> findByNomeContainingIgnoreCase(String nome);
}