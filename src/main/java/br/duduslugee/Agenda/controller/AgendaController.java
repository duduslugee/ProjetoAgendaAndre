package br.duduslugee.Agenda.controller;

import br.duduslugee.Agenda.model.Agenda;
import br.duduslugee.Agenda.service.AgendaService;
import br.duduslugee.Agenda.service.ClienteService;
import br.duduslugee.Agenda.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/agenda")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping("/form")
    public String formAgenda(Model model, @RequestParam(required = false) Integer id) {
        Agenda agenda = id != null ? agendaService.buscarAgendaPorId(id).orElse(new Agenda()) : new Agenda();
        model.addAttribute("agenda", agenda);
        model.addAttribute("clientes", clienteService.listarTodosClientes());
        model.addAttribute("funcionarios", funcionarioService.listarTodosFuncionarios());
        return "agenda/form";
    }

    @PostMapping("/save")
    public String salvarAgenda(@ModelAttribute Agenda agenda) {
        agendaService.salvarAgenda(agenda);
        return "redirect:/agenda/listar";
    }

    @GetMapping("/listar")
    public String listarAgendas(Model model) {
        List<Agenda> agendas = agendaService.listarTodasAgendas();
        model.addAttribute("agendas", agendas);
        return "agenda/listar";
    }

    @GetMapping("/editar/{id}")
    public String editarAgenda(@PathVariable("id") Integer id, Model model) {
        Agenda agenda = agendaService.buscarAgendaPorId(id).orElseThrow(() -> new IllegalArgumentException("Agenda não encontrada"));
        model.addAttribute("agenda", agenda);
        model.addAttribute("clientes", clienteService.listarTodosClientes());
        model.addAttribute("funcionarios", funcionarioService.listarTodosFuncionarios());
        return "agenda/form";
    }

    @PostMapping("/atualizar")
    public String atualizarAgenda(@ModelAttribute Agenda agenda) {
        agendaService.atualizarAgenda(agenda.getId(), agenda);
        return "redirect:/agenda/listar";
    }

    @GetMapping("/excluir/{id}")
    public String excluirAgenda(@PathVariable("id") Integer id) {
        agendaService.excluirAgenda(id);
        return "redirect:/agenda/listar";
    }
}
