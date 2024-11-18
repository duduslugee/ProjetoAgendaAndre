package br.duduslugee.Agenda.controller;

import br.duduslugee.Agenda.model.*;
import br.duduslugee.Agenda.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public String getRelatorios(Model model) {
        // Carregar todos os dados necessários
        model.addAttribute("agendas", agendaRepository.findAll());
        model.addAttribute("servicos", servicoRepository.findAll());
        model.addAttribute("funcionarios", funcionarioRepository.findAll());
        model.addAttribute("clientes", clienteRepository.findAll());
        return "relatorios/relatorios";
    }
}
