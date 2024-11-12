package br.duduslugee.Agenda.service;

import br.duduslugee.Agenda.model.Agenda;
import br.duduslugee.Agenda.repository.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AgendaService {

    @Autowired
    private AgendaRepository agendaRepository;

    public List<Agenda> listarTodasAgendas() {
        return agendaRepository.findAll();
    }

    public Optional<Agenda> buscarAgendaPorId(int id) {
        return agendaRepository.findById(id);
    }

    @Transactional
    public Agenda salvarAgenda(Agenda agenda) {
        validarDuplicidadeDeAgendamento(agenda);
        agenda.setStatus(false); // Define status como "não realizado" por padrão
        return agendaRepository.save(agenda);
    }

    @Transactional
    public Agenda atualizarAgenda(int id, Agenda agendaAtualizada) {
        Agenda agendaExistente = agendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agenda não encontrada"));

        if (agendaExistente.isStatus()) {
            throw new RuntimeException("Agenda já realizada. Não é possível editar.");
        }

        validarDuplicidadeDeAgendamento(agendaAtualizada);

        agendaExistente.setDescricao(agendaAtualizada.getDescricao());
        agendaExistente.setServico(agendaAtualizada.getServico());
        agendaExistente.setData(agendaAtualizada.getData());
        agendaExistente.setCliente(agendaAtualizada.getCliente());
        agendaExistente.setFuncionario(agendaAtualizada.getFuncionario());
        agendaExistente.setUsuario(agendaAtualizada.getUsuario());

        return agendaRepository.save(agendaExistente);
    }

    @Transactional
    public boolean excluirAgenda(int id) {
        Agenda agendaExistente = agendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agenda não encontrada"));

        if (agendaExistente.isStatus()) {
            throw new RuntimeException("Agenda já realizada. Não é possível excluir.");
        }

        agendaRepository.deleteById(id);
        return true;
    }

    @Transactional
    public void realizarServico(int id) {
        Agenda agendaExistente = agendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agenda não encontrada"));

        agendaExistente.setStatus(true);
        agendaRepository.save(agendaExistente);
    }

    private void validarDuplicidadeDeAgendamento(Agenda agenda) {
        Optional<Agenda> agendaExistente = agendaRepository
                .findByClienteAndData(agenda.getCliente(), agenda.getData());

        if (agendaExistente.isPresent() && agendaExistente.get().getId() != agenda.getId()) {
            throw new RuntimeException("Já existe um agendamento para o cliente na mesma data e horário.");
        }
    }
}
