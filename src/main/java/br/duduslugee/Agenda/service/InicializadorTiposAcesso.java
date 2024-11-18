package br.duduslugee.Agenda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

@Component
public class InicializadorTiposAcesso {

    @Autowired
    private TipoAcessoService tipoAcessoService;

    //iniciando a bagacera dos tipo de acesso
    @PostConstruct
    public void init() {
        tipoAcessoService.adicionarTiposDeAcesso();
    }
}
