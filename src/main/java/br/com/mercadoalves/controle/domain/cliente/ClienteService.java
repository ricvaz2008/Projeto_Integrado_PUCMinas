package br.com.mercadoalves.controle.domain.cliente;

import br.com.mercadoalves.controle.domain.ConnectionFactory;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public class ClienteService {

    private final ConnectionFactory connection;

    public ClienteService() {
        this.connection = new ConnectionFactory();
    }

    public List<AdmCliente> listarClientes(String coluna, String ordem) {
        Connection conn = connection.recuperarConexao();
        return new ClienteDAO(conn).listar(coluna, ordem);
    }

    public Cliente listarClientePorCpf(String cpf) {
        Connection conn = connection.recuperarConexao();
        return new ClienteDAO(conn).listarClientePorCpf(cpf);
    }

    public void cadastrarCliente(String cpf, String nome, Date nascimento, String telefone, String email, String endereco, String cidade, String estado, String cep) {
        Connection conn = connection.recuperarConexao();
        new ClienteDAO(conn).cadastrarCliente(cpf, nome, nascimento, telefone, email, endereco, cidade, estado, cep);
    }

    public void alterarCliente(String nome, Date nascimento, String cpf, String telefone, String email, String endereco, String cidade, String estado, String cep) {
        Connection conn = connection.recuperarConexao();
        new ClienteDAO(conn).alterarCliente(nome, nascimento, cpf, telefone, email, endereco, cidade, estado, cep);
    }

    public void apagarCliente(String cpf) {
        Connection conn = connection.recuperarConexao();
        new ClienteDAO(conn).apagarCliente(cpf);
    }
}