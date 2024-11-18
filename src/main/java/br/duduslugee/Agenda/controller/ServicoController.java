package br.duduslugee.Agenda.controller;

import br.duduslugee.Agenda.model.Cliente;
import br.duduslugee.Agenda.model.Servico;
import br.duduslugee.Agenda.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoService servicoService;

    // Listar todos os serviços
    @GetMapping
    public String listarServicos(Model model) {
        List<Servico> servicos = servicoService.listarTodosServicos();
        model.addAttribute("servicos", servicos);
        return "servicos/servicos";
    }

    // Exibe o formulário de criação de serviço
    @GetMapping("/criar")
    public String criarServico(Model model) {
        model.addAttribute("servico", new Servico());
        return "servicos/criar";
    }

    @GetMapping("/editar/{id}")
    public String editarServico(@PathVariable("id") Integer id, Model model) {
        Optional<Servico> servicoOptional = servicoService.buscarPorId(id);
        if (servicoOptional.isPresent()) {
            model.addAttribute("servico", servicoOptional.get());
            return "servicos/criar";
        } else {
            return "redirect:/servicos";
        }
    }

    @PostMapping("/salvar")
    public String salvarServico(@ModelAttribute("servico") Servico servico) {
        if (servico.getId() != null) {
            servicoService.salvarServico(servico);
        } else {
            servicoService.salvarServico(servico);
        }
        return "redirect:/servicos";
    }

    // Excluir um serviço
    @GetMapping("/excluir/{id}")
    public String excluirServico(@PathVariable int id) {
        servicoService.excluirServico(id);
        return "redirect:/servicos";
    }
}
