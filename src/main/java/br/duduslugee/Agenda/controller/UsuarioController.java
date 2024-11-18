package br.duduslugee.Agenda.controller;

import br.duduslugee.Agenda.model.TipoAcesso;
import br.duduslugee.Agenda.model.Usuario;
import br.duduslugee.Agenda.service.TipoAcessoService;
import br.duduslugee.Agenda.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TipoAcessoService tipoAcessoService;

    // Método para listar todos os usuários
    @GetMapping
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioService.listarTodosUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "usuarios/usuarios";  // Página de listagem de usuários
    }



    // Método para exibir o formulário de criação de usuário
    @GetMapping("/adicionar")
    public String adicionarUsuario(Model model) {
        List<TipoAcesso> tiposAcesso = tipoAcessoService.listarTodosTiposDeAcesso();
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("tiposAcesso", tiposAcesso);
        return "usuarios/criar";
    }

    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable int id, Model model) {
        Usuario usuario = usuarioService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<TipoAcesso> tiposAcesso = tipoAcessoService.listarTodosTiposDeAcesso();

        model.addAttribute("usuario", usuario);
        model.addAttribute("tiposAcesso", tiposAcesso);

        return "usuarios/criar";
    }

    @PostMapping("/salvar")
    public String salvarUsuario(@ModelAttribute @Valid Usuario usuario, BindingResult result) {
        if (result.hasErrors()) {
            return "usuarios/criar";
        }
        if (usuario.getId() != 0) {
            usuarioService.atualizarUsuario(usuario.getId(), usuario);
        } else {
            usuario.setDtCadastro(LocalDateTime.now());
            usuarioService.salvarUsuario(usuario);
        }

        return "redirect:/usuarios";
    }

    // Método para excluir o usuário
    @GetMapping("/excluir/{id}")
    public String excluirUsuario(@PathVariable int id) {
        usuarioService.excluirUsuario(id);  // Excluindo o usuário do banco
        return "redirect:/usuarios";  // Redirecionando para a lista de usuários
    }
}
