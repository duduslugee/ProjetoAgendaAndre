package br.duduslugee.Agenda.service;

import br.duduslugee.Agenda.model.Servico;
import br.duduslugee.Agenda.repository.ServicoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.transaction.annotation.Transactional;


@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

        public List<Servico> listarTodosServicos() {
        return servicoRepository.findAll();
    }
    @Transactional
    public Servico criarServico(Servico servico) {
            return servicoRepository.save(servico);
    }

    @Transactional
    public Servico salvarServico(Servico servico) {
        return servicoRepository.save(servico);
    }

    @Transactional
    public void excluirServico(Integer id) {
        if (servicoRepository.existsById(id)) {
            servicoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Serviço não encontrado");
        }
    }

    public Optional<Servico> buscarPorId(int id) {
        return servicoRepository.findById(id);
    }

    // Método para adicionar 5 serviços aleatórios
    public void adicionarServicosAleatorios() {
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            Servico servico = new Servico();
            // Gerar nome aleatório
            servico.setNome("Serviço " + (i + 1));

            // Gerar valor aleatório entre 10.00 e 1000.00
            double valor = 10 + (1000 - 10) * random.nextDouble();
            servico.setValor(valor);

            // Salvar o serviço no banco de dados
            servicoRepository.save(servico);
        }
    }
    @PostConstruct
    public void inicializar() {
        adicionarServicosAleatorios();
    }
}

