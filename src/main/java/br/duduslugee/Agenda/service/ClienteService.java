package br.duduslugee.Agenda.service;

import br.duduslugee.Agenda.model.Cliente;
import br.duduslugee.Agenda.model.Endereco;
import br.duduslugee.Agenda.repository.ClienteRepository;
import br.duduslugee.Agenda.repository.EnderecoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    // Lista todos os clientes cadastrados
    public List<Cliente> listarTodosClientes() {
        return clienteRepository.findAll();
    }

    // Busca um cliente específico pelo seu ID
    public Optional<Cliente> buscarPorId(int id) {
        return clienteRepository.findById(id);
    }

    public Cliente salvarCliente(Cliente cliente) {
        Endereco endereco = cliente.getEndereco();
        if (endereco != null && endereco.getCep() != null && endereco.getCep().length() == 8) {
            enderecoRepository.save(endereco); // Salva o endereço apenas se for válido
        }
        return clienteRepository.save(cliente);
    }

    // Atualiza um cliente existente
    @Transactional
    public Cliente atualizarCliente(int id, Cliente clienteAtualizado) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        clienteExistente.setNome(clienteAtualizado.getNome());
        clienteExistente.setTelefone(clienteAtualizado.getTelefone());
        clienteExistente.setEmail(clienteAtualizado.getEmail());
        clienteExistente.setEndereco(clienteAtualizado.getEndereco());

        return clienteRepository.save(clienteExistente);
    }

    // Exclui um cliente pelo seu ID
    @Transactional
    public void excluirCliente(int id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isPresent()) {
            clienteRepository.delete(clienteOptional.get());
        }
    }

    // Busca clientes pelo nome, utilizando um filtro parcial
    public List<Cliente> buscarPorNome(String nome) {
        return clienteRepository.findByNomeContainingIgnoreCase(nome);
    }

    // Busca clientes por telefone
    public List<Cliente> buscarPorTelefone(String telefone) {
        return clienteRepository.findByTelefoneContaining(telefone);
    }

    private Random random = new Random();

    // Método para adicionar 5 clientes aleatórios
    @PostConstruct
    public void adicionarClientesAleatorios() {
        for (int i = 0; i < 5; i++) {
            Cliente cliente = new Cliente();
            cliente.setNome("Cliente " + random.nextInt(1000));
            cliente.setTelefone("9" + (random.nextInt(1000000000) + 100000000));
            cliente.setEmail("cliente" + random.nextInt(1000) + "@email.com");

            // Criando um endereço aleatório
            Endereco endereco = new Endereco();
            endereco.setLogradouro("Rua " + random.nextInt(1000));
            endereco.setUf("SP");
            endereco.setEstado("São Paulo");
            endereco.setCep("12345-678");
            endereco.setCidade("Cidade " + random.nextInt(100));
            endereco.setNumero(random.nextInt(1000) + 1);
            endereco.setComplemento("Complemento " + random.nextInt(100));

            // Atribuindo o endereço ao cliente
            cliente.setEndereco(endereco);

            // Salvando cliente no banco de dados
            clienteRepository.save(cliente);
        }
    }

}
