package br.duduslugee.Agenda.repository;

import br.duduslugee.Agenda.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
    List<Funcionario> findByNomeContainingIgnoreCase(String nome);
    List<Funcionario> findBySalarioGreaterThan(double salario);
}