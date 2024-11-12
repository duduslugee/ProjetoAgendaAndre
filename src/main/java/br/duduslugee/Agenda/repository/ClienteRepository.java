package br.duduslugee.Agenda.repository;

import br.duduslugee.Agenda.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    List<Cliente> findByNomeContainingIgnoreCase(String nome);
    Optional<Cliente> findByTelefone(String telefone);

    List<Cliente> findByTelefoneContaining(String telefone);
}