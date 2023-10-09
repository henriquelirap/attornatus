package br.com.attornatus.service;

import br.com.attornatus.model.EnderecoModel;

import jakarta.transaction.Transactional;
import br.com.attornatus.model.PessoaModel;
import org.springframework.stereotype.Service;
import br.com.attornatus.repository.PessoaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {
    final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Transactional
    public PessoaModel save(PessoaModel pessoaModel) {
        if (pessoaModel.getEnderecos() != null && !pessoaModel.getEnderecos().isEmpty()) {
            for (EnderecoModel endereco : pessoaModel.getEnderecos()) {
                endereco.setPessoa(pessoaModel);
            }
        }
        return pessoaRepository.save(pessoaModel);
    }

    @Transactional
    public void delete(PessoaModel pessoaModel) {
        pessoaRepository.delete(pessoaModel);
    }

    public List<PessoaModel> findAll() {
        return pessoaRepository.findAll();
    }

    public Optional<PessoaModel> findById(Long id) {
        return pessoaRepository.findById(id);
    }
}