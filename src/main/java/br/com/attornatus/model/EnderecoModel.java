package br.com.attornatus.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.Objects;

@Entity(name = "endereco")
@Table(name = "endereco")
public class EnderecoModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = 100)
    @NotBlank
    private String logradouro;

    @Column(nullable = false, length = 9)
    @NotBlank
    private String cep;

    @Column(nullable = false)
    private Long numero;

    @Column(nullable = false, length = 100)
    @NotBlank
    private String cidade;

    @Column
    private boolean principal;

    @ManyToOne
    @JoinColumn(name = "pessoa")
    @JsonIgnore
    private PessoaModel pessoa;

    public EnderecoModel() {}

    public EnderecoModel(Long id, String logradouro, String cep, Long numero, String cidade, boolean principal, PessoaModel pessoa) {
        this.id = id;
        this.logradouro = logradouro;
        this.cep = cep;
        this.numero = numero;
        this.cidade = cidade;
        this.principal = principal;
        this.pessoa = pessoa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public PessoaModel getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaModel pessoa) {
        this.pessoa = pessoa;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnderecoModel that = (EnderecoModel) o;
        return principal == that.principal && Objects.equals(id, that.id) && Objects.equals(logradouro, that.logradouro) && Objects.equals(cep, that.cep) && Objects.equals(numero, that.numero) && Objects.equals(cidade, that.cidade) && Objects.equals(pessoa, that.pessoa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, logradouro, cep, numero, cidade, principal, pessoa);
    }
}
