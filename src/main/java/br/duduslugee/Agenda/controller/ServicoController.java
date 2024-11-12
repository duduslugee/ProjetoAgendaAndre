package br.duduslugee.Agenda.controller;

import br.duduslugee.Agenda.model.Agenda;
import br.duduslugee.Agenda.model.Servico;
import br.duduslugee.Agenda.service.AgendaService;
import br.duduslugee.Agenda.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoService servicoService;

    @GetMapping
    public List<Servico> listarServicos() {
        return servicoService.listarTodosServicos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servico> buscarServicoPorId(@PathVariable int id) {
        return servicoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Servico> criarServico(@RequestBody Servico servico) {
        Servico novoServico = servicoService.salvarServico(servico);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoServico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servico> atualizarServico(@PathVariable int id, @RequestBody Servico servicoAtualizado) {
        Servico servico = servicoService.atualizarServico(id, servicoAtualizado);
        return ResponseEntity.ok(servico);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirServico(@PathVariable int id) {
        servicoService.excluirServico(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public List<Servico> buscarPorNome(@RequestParam String nome) {
        return servicoService.buscarPorNome(nome);
    }
}
