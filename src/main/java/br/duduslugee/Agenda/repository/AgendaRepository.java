package br.duduslugee.Agenda.repository;

import br.duduslugee.Agenda.model.Agenda;
import br.duduslugee.Agenda.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.Optional;

public interface AgendaRepository extends JpaRepository<Agenda, Integer> {

    boolean existsByDataAndCliente(LocalDateTime data, Cliente cliente);

    Optional<Agenda> findByClienteAndData(Cliente cliente, LocalDateTime data);
}
