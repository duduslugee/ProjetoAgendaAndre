package br.duduslugee.Agenda.controller;

import br.duduslugee.Agenda.model.Funcionario;
import br.duduslugee.Agenda.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping
    public List<Funcionario> listarFuncionarios() {
        return funcionarioService.listarTodosFuncionarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> buscarFuncionarioPorId(@PathVariable int id) {
        return funcionarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Funcionario> criarFuncionario(@RequestBody Funcionario funcionario) {
        Funcionario novoFuncionario = funcionarioService.salvarFuncionario(funcionario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoFuncionario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> atualizarFuncionario(@PathVariable int id, @RequestBody Funcionario funcionarioAtualizado) {
        Funcionario funcionario = funcionarioService.atualizarFuncionario(id, funcionarioAtualizado);
        return ResponseEntity.ok(funcionario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirFuncionario(@PathVariable int id) {
        funcionarioService.excluirFuncionario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public List<Funcionario> buscarPorNome(@RequestParam String nome) {
        return funcionarioService.buscarPorNome(nome);
    }

    @GetMapping("/salarioMaiorQue")
    public List<Funcionario> buscarPorSalarioMaiorQue(@RequestParam double salario) {
        return funcionarioService.buscarPorSalarioAcimaDe(salario);
    }
}
