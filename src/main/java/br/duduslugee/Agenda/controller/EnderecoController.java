package br.duduslugee.Agenda.controller;

import br.duduslugee.Agenda.model.Endereco;
import br.duduslugee.Agenda.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping
    public String listarEnderecos(Model model) {
        List<Endereco> enderecos = enderecoService.listarEnderecos();
        model.addAttribute("enderecos", enderecos);
        return "/endereco/listar";
    }

    @GetMapping("/adicionar")
    public String adicionarEndereco(Model model) {
        model.addAttribute("endereco", new Endereco());
        return "/endereco/adicionar";
    }

    @PostMapping("/salvar")
    public String salvarEndereco(@ModelAttribute Endereco endereco) {
        enderecoService.salvarEndereco(endereco);
        return "redirect:/endereco/adicionar";
    }

    @GetMapping("/buscarPorCep/{cep}")
    @ResponseBody
    public Endereco buscarEnderecoPorCep(@PathVariable String cep) {
        Endereco endereco = enderecoService.buscarEnderecoExternoPorCep(cep); // Chama a API externa
        return endereco;
    }

    @GetMapping("/buscar/{cep}")
    public String buscarEnderecoBanco(@PathVariable String cep, Model model) {
        enderecoService.buscarPorCep(cep)
                .ifPresentOrElse(
                        endereco -> model.addAttribute("endereco", endereco),
                        () -> model.addAttribute("mensagem", "Endereço não encontrado.")
                );
        return "/endereco/detalhes-endereco";
    }

    @GetMapping("/editar/{id}")
    public String editarEndereco(@PathVariable int id, Model model) {
        Optional<Endereco> enderecoOptional = enderecoService.buscarPorId(id);
        if (enderecoOptional.isPresent()) {
            model.addAttribute("endereco", enderecoOptional.get());
            return "/endereco/editar";
        }
        model.addAttribute("mensagem", "Endereço não encontrado.");
        return "redirect:/endereco";
    }

    @PostMapping("/atualizar")
    public String atualizarEndereco(@ModelAttribute Endereco endereco) {
        enderecoService.salvarEndereco(endereco);
        return "redirect:/endereco";
    }

    @DeleteMapping("/excluir/{id}")
    public String excluirEndereco(@PathVariable int id) {
        enderecoService.excluirEndereco(id);
        return "redirect:/endereco"; // Redireciona após a exclusão
    }
}
