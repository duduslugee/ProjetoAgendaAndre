package br.duduslugee.Agenda.service;

import br.duduslugee.Agenda.model.Funcionario;
import br.duduslugee.Agenda.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    // Lista todos os funcionários cadastrados
    public List<Funcionario> listarTodosFuncionarios() {
        return funcionarioRepository.findAll();
    }

    // Busca um funcionário específico pelo seu ID
    public Optional<Funcionario> buscarPorId(int id) {
        return funcionarioRepository.findById(id);
    }

    // Salva um novo funcionário
    @Transactional
    public Funcionario salvarFuncionario(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    // Atualiza um funcionário existente
    @Transactional
    public Funcionario atualizarFuncionario(int id, Funcionario funcionarioAtualizado) {
        Funcionario funcionarioExistente = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        funcionarioExistente.setNome(funcionarioAtualizado.getNome());
        funcionarioExistente.setTelefone(funcionarioAtualizado.getTelefone());
        funcionarioExistente.setEmail(funcionarioAtualizado.getEmail());
        funcionarioExistente.setEndereco(funcionarioAtualizado.getEndereco());
        funcionarioExistente.setSalario(funcionarioAtualizado.getSalario());

        return funcionarioRepository.save(funcionarioExistente);
    }

    // Exclui um funcionário pelo seu ID
    @Transactional
    public void excluirFuncionario(int id) {
        if (funcionarioRepository.existsById(id)) {
            funcionarioRepository.deleteById(id);
        } else {
            throw new RuntimeException("Funcionário não encontrado");
        }
    }

    // Busca funcionários pelo nome, utilizando um filtro parcial
    public List<Funcionario> buscarPorNome(String nome) {
        return funcionarioRepository.findByNomeContainingIgnoreCase(nome);
    }

    // Busca funcionários com salário maior que um valor específico
    public List<Funcionario> buscarPorSalarioAcimaDe(double salario) {
        return funcionarioRepository.findBySalarioGreaterThan(salario);
    }
}
