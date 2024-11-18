package br.duduslugee.Agenda.controller;

import br.duduslugee.Agenda.model.Cliente;
import br.duduslugee.Agenda.service.ClienteService;
import br.duduslugee.Agenda.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EnderecoService enderecoService;

    // Listar todos os clientes
    @GetMapping
    public String listarClientes(Model model) {
        List<Cliente> clientes = clienteService.listarTodosClientes();
        model.addAttribute("clientes", clientes);
        return "clientes/clientes";
    }

    // Exibir formulário para criar ou editar cliente
    @GetMapping("/criar")
    public String criarCliente(Model model, @RequestParam(required = false) Integer id) {
        Cliente cliente = id != null ?
                clienteService.buscarPorId(id).orElse(new Cliente()) :
                new Cliente();
        model.addAttribute("cliente", cliente);
        return "/clientes/criar";
    }

    @GetMapping("/editar/{id}")
    public String editarCliente(@PathVariable("id") Integer id, Model model) {
        Optional<Cliente> clienteOptional = clienteService.buscarPorId(id);
        if (clienteOptional.isPresent()) {
            model.addAttribute("cliente", clienteOptional.get());
            return "clientes/criar";
        } else {
            return "redirect:/clientes";
        }
    }

    @PostMapping("/salvar")
    public String salvarCliente(@Valid @ModelAttribute Cliente cliente, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("cliente", cliente);
            return "/clientes/criar"; // Retorna para o formulário de criação em caso de erro
        }
        clienteService.salvarCliente(cliente);
        return "redirect:/clientes"; // Redireciona para a lista de clientes
    }

    // Excluir cliente
    @GetMapping("/excluir/{id}")
    public String excluirCliente(@PathVariable Integer id) {
        clienteService.excluirCliente(id);
        return "redirect:/clientes";
    }

    // Buscar cliente por nome
    @GetMapping("/buscar")
    public String buscarPorNome(@RequestParam String nome, Model model) {
        List<Cliente> clientes = clienteService.buscarPorNome(nome);
        model.addAttribute("clientes", clientes);
        return "clientes/clientes";
    }
}
