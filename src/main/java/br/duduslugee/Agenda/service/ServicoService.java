package br.duduslugee.Agenda.service;

import br.duduslugee.Agenda.model.Servico;
import br.duduslugee.Agenda.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    /**
     * Lista todos os serviços cadastrados.
     */
    public List<Servico> listarTodosServicos() {
        return servicoRepository.findAll();
    }

    /**
     * Busca um serviço específico pelo seu ID.
     */
    public Optional<Servico> buscarPorId(int id) {
        return servicoRepository.findById(id);
    }

    /**
     * Salva um novo serviço.
     */
    @Transactional
    public Servico salvarServico(Servico servico) {
        return servicoRepository.save(servico);
    }

    /**
     * Atualiza um serviço existente.
     */
    @Transactional
    public Servico atualizarServico(int id, Servico servicoAtualizado) {
        Servico servicoExistente = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        servicoExistente.setNome(servicoAtualizado.getNome());
        servicoExistente.setValor(servicoAtualizado.getValor());

        return servicoRepository.save(servicoExistente);
    }

    /**
     * Exclui um serviço pelo seu ID.
     */
    @Transactional
    public void excluirServico(int id) {
        if (servicoRepository.existsById(id)) {
            servicoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Serviço não encontrado");
        }
    }

    /**
     * Busca serviços pelo nome, utilizando um filtro parcial.
     */
    public List<Servico> buscarPorNome(String nome) {
        return servicoRepository.findByNomeContainingIgnoreCase(nome);
    }
}

