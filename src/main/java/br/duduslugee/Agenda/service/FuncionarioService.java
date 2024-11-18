package br.duduslugee.Agenda.service;

import br.duduslugee.Agenda.model.Endereco;
import br.duduslugee.Agenda.model.Funcionario;
import br.duduslugee.Agenda.repository.FuncionarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

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

    private final Random random = new Random();

    @PostConstruct
    public void adicionarFuncionariosAleatorios() {
        for (int i = 0; i < 5; i++) {
            Funcionario funcionario = new Funcionario();
            funcionario.setNome("Funcionário " + random.nextInt(1000));
            funcionario.setTelefone("9" + (random.nextInt(1000000000) + 100000000));
            funcionario.setEmail("funcionario" + random.nextInt(1000) + "@email.com");
            funcionario.setSalario(2000 + random.nextDouble() * 5000);

            // Criando um endereço aleatório
            Endereco endereco = new Endereco();
            endereco.setLogradouro("Rua " + random.nextInt(1000));
            endereco.setUf("SP");
            endereco.setEstado("São Paulo");
            endereco.setCep("12345-678");
            endereco.setCidade("Cidade " + random.nextInt(100));
            endereco.setNumero(random.nextInt(1000) + 1);
            endereco.setComplemento("Complemento " + random.nextInt(100));

            // Associando o endereço ao funcionário
            funcionario.setEndereco(endereco);

            // Salvando o funcionário (o endereço será salvo automaticamente devido ao CascadeType.ALL)
            funcionarioRepository.save(funcionario);
        }
    }
}
