package br.com.attornatus.controller;

import br.com.attornatus.model.PessoaModel;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.attornatus.service.PessoaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {
    private String NOT_FOUND = "Pessoa não consta na base de dados!";

    final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping
    public ResponseEntity<Object> criarPessoa(@RequestBody @Valid PessoaModel pessoaModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaService.save(pessoaModel));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarPessoa(@PathVariable (value = "id") Long id, @RequestBody PessoaModel pessoaAtualizada) {
        Optional<PessoaModel> pessoaModelOptional = pessoaService.findById(id);
        if (!pessoaModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
        }
        var pessoaModel = pessoaModelOptional.get();
        pessoaAtualizada.setId(pessoaModel.getId());

        if (pessoaAtualizada.getNome() == null) {
            pessoaAtualizada.setNome(pessoaModel.getNome());
        }
        if (pessoaAtualizada.getDataNascimento() == null) {
            pessoaAtualizada.setDataNascimento(pessoaModel.getDataNascimento());
        }
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.save(pessoaAtualizada));
    }

    @GetMapping
    public ResponseEntity<List<PessoaModel>> buscarTodasPessoas() {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPessoaPorId(@PathVariable (value = "id") Long id) {
        Optional<PessoaModel> pessoaModelOptional = pessoaService.findById(id);
        if (!pessoaModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.OK).body(pessoaModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarPessoa(@PathVariable(value = "id") Long id){
        Optional<PessoaModel> pessoaModelOptional = pessoaService.findById(id);
        if (!pessoaModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND);
        }
        pessoaService.delete(pessoaModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Pessoa removida com sucesso!");
    }
}
