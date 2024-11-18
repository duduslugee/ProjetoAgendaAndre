package br.duduslugee.Agenda.controller;

import br.duduslugee.Agenda.model.Endereco;
import br.duduslugee.Agenda.model.Funcionario;
import br.duduslugee.Agenda.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    // Lista todos os funcionários
    @GetMapping
    public String listarFuncionarios(Model model) {
        List<Funcionario> funcionarios = funcionarioService.listarTodosFuncionarios();
        model.addAttribute("funcionarios", funcionarios);
        return "funcionarios/funcionarios";
    }

    // Exibe o formulário para criar ou editar um funcionário
    @GetMapping("/criar")
    public String exibirFormulario(Model model, @RequestParam(required = false) Integer id) {
        Funcionario funcionario = (id != null) ?
                funcionarioService.buscarPorId(id).orElse(new Funcionario()) :
                new Funcionario();

        if (funcionario.getEndereco() == null) {
            funcionario.setEndereco(new Endereco());
        }

        model.addAttribute("funcionario", funcionario);
        return "funcionarios/criar";
    }

    // Salva ou atualiza um funcionário
    @PostMapping("/salvar")
    public String salvarFuncionario(@ModelAttribute Funcionario funcionario) {
        if (funcionario.getEndereco() == null) {
            funcionario.setEndereco(new Endereco());
        }
        funcionarioService.salvarFuncionario(funcionario);
        return "redirect:/funcionarios";
    }

    // Exclui um funcionário
    @GetMapping("/excluir/{id}")
    public String excluirFuncionario(@PathVariable int id) {
        funcionarioService.excluirFuncionario(id);
        return "redirect:/funcionarios";
    }
}
