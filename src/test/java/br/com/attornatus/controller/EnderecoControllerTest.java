package br.com.attornatus.controller;

import br.com.attornatus.model.EnderecoModel;
import br.com.attornatus.service.EnderecoService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@Sql(scripts = "/sql/inserir-pessoa.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/inserir-endereco.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class EnderecoControllerTest {
    @Autowired
    private EnderecoController enderecoController;
    @Autowired
    private EnderecoService enderecoService;
    private final Long id = 9999999L;
    private EnderecoModel enderecoModel = new EnderecoModel(id, "Rua General Costa", "85901160", 1010L, "Toledo", true, null);
    private ResponseEntity<Object> response;
    private ResponseEntity<List<EnderecoModel>> responseList;
    private List<EnderecoModel> enderecos;

    @Test
    public void testeCriarEndereco() {
        response = enderecoController.criarEnderecoPessoa(id, enderecoModel);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(enderecoModel, response.getBody());
    }

    @Test
    public void testeEditarEndereco() {
        enderecoModel.setLogradouro("Rua General Costa Neto");
        response = enderecoController.atualizarEndereco(id, enderecoModel);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(enderecoModel, response.getBody());
    }

    @Test
    public void testeBuscarEnderecoPorId() {
        response = enderecoController.buscarEnderecoPorId(id);
        EnderecoModel enderecoModelBanco = (EnderecoModel) response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(enderecoModel.getId(), enderecoModelBanco.getId());
    }

    @Test
    public void testeBuscarTodosEnderecos() {
        enderecos = enderecoService.findAll();
        responseList = enderecoController.buscarTodosEnderecos();
        assertEquals(HttpStatus.OK, responseList.getStatusCode());
        assertEquals(enderecos, responseList.getBody());
    }

    @Test
    public void testeBuscarTodosEnderecosPorPessoaId() {
        enderecos = enderecoService.findAll();
        responseList = enderecoController.buscarTodosEnderecosPorPessoaId(id);
        assertEquals(HttpStatus.OK, responseList.getStatusCode());
        assertEquals(enderecos, responseList.getBody());
    }

    @Test
    public void testeDeletarEndereco() {
        response = enderecoController.deletarEndereco(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Endereço removido com sucesso!", response.getBody());
    }
}
