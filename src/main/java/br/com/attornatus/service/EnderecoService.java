package br.com.attornatus.service;

import br.com.attornatus.model.EnderecoModel;
import br.com.attornatus.model.PessoaModel;
import br.com.attornatus.repository.EnderecoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    final EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    @Transactional
    public EnderecoModel save(EnderecoModel enderecoModel) {
        return enderecoRepository.save(enderecoModel);
    }

    @Transactional
    public List<EnderecoModel> saveAll(List<EnderecoModel> enderecos) {
        return enderecoRepository.saveAll(enderecos);
    }

    @Transactional
    public void delete(EnderecoModel enderecoModel) {
        enderecoRepository.delete(enderecoModel);
    }

    public List<EnderecoModel> findAll() {
        return enderecoRepository.findAll();
    }

    public Optional<EnderecoModel> findById(Long id) {
        return enderecoRepository.findById(id);
    }

    public void setarNovoEnderecoPrincipal(PessoaModel pessoaModel) {
        List<EnderecoModel> enderecos = pessoaModel.getEnderecos();
        if (!enderecos.isEmpty()) {
            for (EnderecoModel endereco : enderecos) {
                endereco.setPrincipal(false);
            }
            enderecoRepository.saveAll(enderecos);
        }
    }

    public void setarUltimoEnderecoPrincipal (PessoaModel pessoaModel) {
        List<EnderecoModel> enderecos = pessoaModel.getEnderecos();
        if (!enderecos.isEmpty() && enderecos.size() == 1) {
            enderecos.get(0).setPrincipal(true);
            enderecoRepository.saveAll(enderecos);
        }
    }
}
