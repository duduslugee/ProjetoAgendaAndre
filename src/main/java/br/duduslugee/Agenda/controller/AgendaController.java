package br.duduslugee.Agenda.controller;

import br.duduslugee.Agenda.model.Agenda;
import br.duduslugee.Agenda.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
@RequestMapping("/agenda")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private ServicoService servicoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/criar")
    public String criarAgenda(Model model) {
        model.addAttribute("agenda", new Agenda()); // Inicializa o objeto
        model.addAttribute("clientes", clienteService.listarTodosClientes());
        model.addAttribute("funcionarios", funcionarioService.listarTodosFuncionarios());
        model.addAttribute("servicos", servicoService.listarTodosServicos());
        model.addAttribute("usuarios", usuarioService.listarTodosUsuarios());
        return "agenda/criar";
    }

    @PostMapping("/salvar")
    public String salvarAgenda(@Valid @ModelAttribute("agenda") Agenda agenda, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("clientes", clienteService.listarTodosClientes());
            model.addAttribute("funcionarios", funcionarioService.listarTodosFuncionarios());
            model.addAttribute("servicos", servicoService.listarTodosServicos());
            model.addAttribute("usuarios", usuarioService.listarTodosUsuarios());
            return "agenda/criar";
        }
        agendaService.salvarAgenda(agenda);
        return "redirect:/agenda";
    }

    @GetMapping
    public String listarAgendas(Model model) {
        List<Agenda> agendas = agendaService.listarTodasAgendas();
        System.out.println("Agendas retornadas: " + agendas);
        model.addAttribute("agendas", agendas);
        return "agenda/agenda";  // Isso mapeia para templates/agenda/agenda.html
    }

    @GetMapping("/editar/{id}")
    public String editarAgenda(@PathVariable("id") Integer id, Model model) {
        Agenda agenda = agendaService.buscarAgendaPorId(id).orElseThrow(() -> new IllegalArgumentException("Agenda não encontrada"));
        model.addAttribute("agenda", agenda);
        model.addAttribute("clientes", clienteService.listarTodosClientes());
        model.addAttribute("funcionarios", funcionarioService.listarTodosFuncionarios());
        model.addAttribute("servicos", servicoService.listarTodosServicos());
        model.addAttribute("usuarios",usuarioService.listarTodosUsuarios());
        return "/agenda/criar";
    }

    @PostMapping("/atualizar")
    public String atualizarAgenda(@ModelAttribute Agenda agenda) {
        agendaService.atualizarAgenda(agenda.getId(), agenda);
        return "redirect:/agenda";
    }

    @GetMapping("/excluir/{id}")
    public String excluirAgenda(@PathVariable("id") Integer id) {
        agendaService.excluirAgenda(id);
        return "redirect:/agenda";
    }

    @PostMapping("/realizar/{id}")
    public String realizarServico(@RequestParam("id") int id) {
        try {
            agendaService.realizarServico(id);
            return "redirect:/agenda";  // Redireciona de volta para a lista de agendas
        } catch (RuntimeException e) {
            return "error";
        }
    }
}
