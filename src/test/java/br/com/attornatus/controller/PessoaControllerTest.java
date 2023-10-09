package br.com.attornatus.controller;

import br.com.attornatus.model.PessoaModel;
import br.com.attornatus.service.PessoaService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@Sql(scripts = "/sql/inserir-pessoa.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class PessoaControllerTest {

    @Autowired
    private PessoaController pessoaController;
    @Autowired
    private PessoaService pessoaService;
    private final Long id = 9999999L;
    private PessoaModel pessoaModel = new PessoaModel(id, "João Silva", LocalDate.of(1990, 01, 12), List.of());
    private ResponseEntity<Object> response;
    private ResponseEntity<List<PessoaModel>> responseList;
    private List<PessoaModel> pessoas;

    @Test
    public void testeCriarPessoa() {
        response = pessoaController.criarPessoa(pessoaModel);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(pessoaModel, response.getBody());
    }

    @Test
    public void testeAtualizarPessoa() {
        pessoaModel.setNome("João Silva Nunes");
        response = pessoaController.atualizarPessoa(id, pessoaModel);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pessoaModel, response.getBody());
    }

    @Test
    public void testeBuscarPessoaPorId() {
        response = pessoaController.buscarPessoaPorId(id);
        PessoaModel pessoaModelBanco = (PessoaModel) response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pessoaModel.getId(), pessoaModelBanco.getId());
    }

    @Test
    public void testeBuscarTodasPessoas() {
        pessoas = pessoaService.findAll();
        responseList = pessoaController.buscarTodasPessoas();
        assertEquals(HttpStatus.OK, responseList.getStatusCode());
        assertEquals(pessoas, responseList.getBody());
    }

    @Test
    public void testeDeletarPessoa() {
        response = pessoaController.deletarPessoa(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Pessoa removida com sucesso!", response.getBody());
    }
}
