package br.duduslugee.Agenda.service;

import br.duduslugee.Agenda.model.Endereco;
import br.duduslugee.Agenda.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    // RestTemplate como bean para reutilização
    @Autowired
    private RestTemplate restTemplate;

    public Endereco salvarEndereco(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    public List<Endereco> listarEnderecos() {
        return enderecoRepository.findAll();
    }

    public Optional<Endereco> buscarPorCep(String cep) {
        return enderecoRepository.findByCep(cep);
    }

    public Endereco buscarEnderecoExternoPorCep(String cep) {
        // Verifica se o CEP é válido antes de fazer a requisição
        if (cep == null || !cep.matches("[0-9]{8}")) {
            return null;  // Retorna null se o CEP não for válido
        }

        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        return restTemplate.getForObject(url, Endereco.class);
    }

    public void excluirEndereco(Integer id) {
        enderecoRepository.deleteById(id);  // Exclui o endereço pelo ID
    }

    public Optional<Endereco> buscarPorId(int id) {
        return enderecoRepository.findById(id);  // Busca endereço por ID
    }
}
