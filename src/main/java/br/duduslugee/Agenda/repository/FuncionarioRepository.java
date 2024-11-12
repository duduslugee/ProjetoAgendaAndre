package br.duduslugee.Agenda.repository;

import br.duduslugee.Agenda.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
    List<Funcionario> findByNomeContainingIgnoreCase(String nome);
    List<Funcionario> findBySalarioGreaterThan(double salario);
}