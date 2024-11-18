package br.duduslugee.Agenda.controller;

import br.duduslugee.Agenda.model.*;
import br.duduslugee.Agenda.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DecimalFormat;
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
        List<Agenda> agendas = agendaRepository.findAll();
        List<Servico> servicos = servicoRepository.findAll();
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        List<Cliente> clientes = clienteRepository.findAll();

        // Formatar o valor de cada serviço
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        for (Servico servico : servicos) {
            servico.setValorFormatted(decimalFormat.format(servico.getValor()));
        }

        // Adicionar os dados ao modelo
        model.addAttribute("agendas", agendas);
        model.addAttribute("servicos", servicos);
        model.addAttribute("funcionarios", funcionarios);
        model.addAttribute("clientes", clientes);

        return "relatorios/relatorios";
    }
}
