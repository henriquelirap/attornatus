package br.com.attornatus.controller;

import br.com.attornatus.model.EnderecoModel;
import br.com.attornatus.model.PessoaModel;
import br.com.attornatus.service.EnderecoService;
import br.com.attornatus.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired
    private PessoaService pessoaService;
    private String NOT_FOUND = "Endereço não consta na base de dados!";
    final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @PostMapping("/pessoa/{id}")
    public ResponseEntity<Object> criarEnderecoPessoa(@PathVariable(value = "id") Long id, @RequestBody @Valid EnderecoModel enderecoModel) {
        Optional<PessoaModel> pessoaModelOptional = pessoaService.findById(id);
        if (!pessoaModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não consta na base de dados!");
        }
        enderecoModel.setPessoa(pessoaModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(enderecoService.save(enderecoModel));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarEndereco(@PathVariable(value = "id") Long id, @RequestBody EnderecoModel enderecoAtualizado) {
        Optional<EnderecoModel> enderecoModelOptional = enderecoService.findById(id);
        if (!enderecoModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
        }
        var enderecoModel = enderecoModelOptional.get();

        if (enderecoAtualizado.getLogradouro() == null) {
            enderecoAtualizado.setLogradouro(enderecoModel.getLogradouro());
        }
        if (enderecoAtualizado.getNumero() == null ) {
            enderecoAtualizado.setNumero(enderecoModel.getNumero());
        }
        if (enderecoAtualizado.getCep() == null) {
            enderecoAtualizado.setCep(enderecoModel.getCep());
        }
        if (enderecoAtualizado.getCidade() == null) {
            enderecoAtualizado.setCidade(enderecoModel.getCidade());
        }
        if (enderecoAtualizado.getPessoa() == null) {
            enderecoAtualizado.setPessoa(enderecoModel.getPessoa());
        }
        if (enderecoAtualizado.isPrincipal()) {
            enderecoService.setarNovoEnderecoPrincipal(enderecoModel.getPessoa());
        }
        enderecoAtualizado.setId(enderecoModel.getId());
        enderecoAtualizado.setPessoa(enderecoModel.getPessoa());
        return ResponseEntity.status(HttpStatus.OK).body(enderecoService.save(enderecoAtualizado));
    }

    @GetMapping
    public ResponseEntity<List<EnderecoModel>> buscarTodosEnderecos() {
        return ResponseEntity.status(HttpStatus.OK).body(enderecoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarEnderecoPorId(@PathVariable(value = "id") Long id) {
        Optional<EnderecoModel> enderecoModelOptional = enderecoService.findById(id);
        if (!enderecoModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.OK).body(enderecoModelOptional.get());
    }

    @GetMapping("/pessoa/{id}")
    public ResponseEntity<List<EnderecoModel>> buscarTodosEnderecosPorPessoaId(@PathVariable(value = "id") Long id) {
        Optional<PessoaModel> pessoaModelOptional = pessoaService.findById(id);
        if (!pessoaModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
        return ResponseEntity.status(HttpStatus.OK).body(pessoaModelOptional.get().getEnderecos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarEndereco(@PathVariable(value = "id") Long id) {
        Optional<EnderecoModel> enderecoModelOptional = enderecoService.findById(id);
        if (!enderecoModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
        }
        enderecoService.delete(enderecoModelOptional.get());
        enderecoModelOptional.get().getPessoa().getEnderecos().remove(enderecoModelOptional.get());
        enderecoService.setarUltimoEnderecoPrincipal(enderecoModelOptional.get().getPessoa());
        return ResponseEntity.status(HttpStatus.OK).body("Endereço removido com sucesso!");
    }


}
