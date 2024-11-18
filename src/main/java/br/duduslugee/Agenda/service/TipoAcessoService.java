package br.duduslugee.Agenda.service;

import br.duduslugee.Agenda.repository.TipoAcessoRepository;
import br.duduslugee.Agenda.model.TipoAcesso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoAcessoService {
    @Autowired
    private TipoAcessoRepository tipoAcessoRepository;

    // Lista todos os tipos de acesso
    public List<TipoAcesso> listarTodosTiposDeAcesso() {
        return tipoAcessoRepository.findAll();
    }

    // Método para adicionar tipos de acesso (já implementado)
    public void adicionarTiposDeAcesso() {
        if (tipoAcessoRepository.count() == 0) {
            TipoAcesso admin = new TipoAcesso();
            admin.setDescricao("ADMIN");

            TipoAcesso usuario = new TipoAcesso();
            usuario.setDescricao("USUARIO");

            TipoAcesso convidado = new TipoAcesso();
            convidado.setDescricao("CONVIDADO");

            TipoAcesso moderador = new TipoAcesso();
            moderador.setDescricao("MODERADOR");

            TipoAcesso suporte = new TipoAcesso();
            suporte.setDescricao("SUPORTE");

            tipoAcessoRepository.save(admin);
            tipoAcessoRepository.save(usuario);
            tipoAcessoRepository.save(convidado);
            tipoAcessoRepository.save(moderador);
            tipoAcessoRepository.save(suporte);
        }
    }
}
