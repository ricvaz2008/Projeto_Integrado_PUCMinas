package br.com.mercadoalves.controle.cliente;

import java.sql.Date;
import java.util.Objects;

public class Cliente {
    private String cpf;
    private String nome;
    private Date nascimento;
    private String telefone;
    private String email;
    private String endereco;
    private String cidade;
    private String estado;
    private String cep;

    public Cliente(DadosCadastroCliente dados) {
        this.cpf = dados.cpf();
        this.nome = dados.nome();
        this.nascimento = dados.nascimento();
        this.telefone = dados.telefone();
        this.email = dados.email();
        this.endereco = dados.endereco();
        this.cidade = dados.cidade();
        this.estado = dados.estado();
        this.cep = dados.cep();
    }

    public Cliente(String nome, Date nascimento, String cpf, String telefone, String email, String endereco, String cidade, String estado, String cep) {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public String getCep() {
        return cep;
    }
}