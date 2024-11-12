package br.duduslugee.Agenda.repository;

import br.duduslugee.Agenda.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico, Integer> {
    List<Servico> findByNomeContainingIgnoreCase(String nome);
}