package br.duduslugee.Agenda.service;

import br.duduslugee.Agenda.model.Cliente;
import br.duduslugee.Agenda.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // Lista todos os clientes cadastrados
    public List<Cliente> listarTodosClientes() {
        return clienteRepository.findAll();
    }

    // Busca um cliente específico pelo seu ID
    public Optional<Cliente> buscarPorId(int id) {
        return clienteRepository.findById(id);
    }

    // Salva um novo cliente
    @Transactional
    public Cliente salvarCliente(Cliente cliente) {
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
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
        } else {
            throw new RuntimeException("Cliente não encontrado");
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

}
