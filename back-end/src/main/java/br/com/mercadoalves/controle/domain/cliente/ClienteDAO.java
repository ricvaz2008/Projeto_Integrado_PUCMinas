package br.com.mercadoalves.controle.domain.cliente;

import br.com.mercadoalves.controle.domain.estoque.Estoque;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private final Connection conn;

    ClienteDAO(Connection connection) {
        this.conn = connection;
    }

    public List<AdmCliente> listar(String coluna, String ordem) {
        PreparedStatement ps;
        ResultSet resultSet;
        List<AdmCliente> clientes = new ArrayList<>();

        String sql = "WITH LatestSales AS ( " +
                "    SELECT " +
                "        v.cpf, " +
                "        v.data, " +
                "        i.cupom, " +
                "        ROW_NUMBER() OVER (PARTITION BY v.cpf ORDER BY v.data DESC) AS rn " +
                "    FROM " +
                "        vendas v " +
                "    LEFT JOIN " +
                "        itensVenda i ON v.numeroVenda = i.numeroVenda " +
                ") " +
                "SELECT " +
                "    c.cpf, " +
                "    c.nome, " +
                "    LS.data AS latest_data, " +
                "    LS.cupom AS latest_cupom " +
                "FROM " +
                "    clientes c " +
                "LEFT JOIN " +
                "    LatestSales LS ON c.cpf = LS.cpf AND LS.rn = 1 " +
                "ORDER BY " + coluna + " " + ordem;

        try {
            ps = conn.prepareStatement(sql);
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                String cpf = resultSet.getString(1);
                String nome = resultSet.getString(2);
                Date ultimaCompra = resultSet.getDate(3);
                String cupom = resultSet.getString(4);

                if (cpf == null || nome == null) {
                    continue;
                }

                DadosCliente dadosCliente = new DadosCliente(cpf, nome, ultimaCompra, cupom);
                AdmCliente admCliente = new AdmCliente(dadosCliente);
                clientes.add(admCliente);
            }

            resultSet.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return clientes;
    }

    public Cliente listarClientePorCpf(String cpf) {
        PreparedStatement ps;
        ResultSet resultSet;
        Cliente cliente = null;

        String sql = "SELECT * FROM clientes WHERE cpf = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, cpf);
            resultSet = ps.executeQuery();

            if (resultSet.next()) {
                String cpfDoCliente = resultSet.getString(1);
                String nome = resultSet.getString(2);
                Date nascimento = resultSet.getDate(3);
                String telefone = resultSet.getString(4);
                String email = resultSet.getString(5);
                String endereco = resultSet.getString(6);
                String cidade = resultSet.getString(7);
                String estado = resultSet.getString(8);
                String cep = resultSet.getString(9);

                if (cpfDoCliente != null && nome != null && nascimento != null && telefone != null && email != null && endereco != null && cidade != null && estado != null && cep != null) {
                    DadosCadastroCliente dadosCadastroCliente = new DadosCadastroCliente(cpfDoCliente, nome, nascimento, telefone, email, endereco, cidade, estado, cep);
                    cliente = new Cliente(dadosCadastroCliente);
                }
            }

            resultSet.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cliente;
    }

    public void alterarCliente(String nome, Date nascimento, String cpf, String telefone, String email, String endereco, String cidade, String estado, String cep) {
        PreparedStatement ps;
        ResultSet resultSet;
        Estoque estoque = null;

        String sql = "UPDATE clientes SET nome = ?, nascimento = ?, telefone = ?, email = ?, endereco = ?, cidade = ?, estado = ?, cep = ? WHERE cpf = ?";

        try {
            conn.setAutoCommit(false);

            ps = conn.prepareStatement(sql);

            ps.setString(1, nome);
            ps.setDate(2, nascimento);
            ps.setString(3, telefone);
            ps.setString(4, email);
            ps.setString(5, endereco);
            ps.setString(6, cidade);
            ps.setString(7, estado);
            ps.setString(8, cep);
            ps.setString(9, cpf);

            ps.execute();
            ps.close();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        } finally {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void apagarCliente(String cpf) {
        PreparedStatement ps;
        ResultSet resultSet;
        Cliente cliente = null;

        String sql = "DELETE FROM clientes WHERE cpf = ?";
        try {
            ps = conn.prepareStatement(sql);

            ps.setString(1, cpf);

            ps.execute();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cadastrarCliente(String cpf, String nome, Date nascimento, String telefone, String email, String endereco, String cidade, String estado, String cep) {
        PreparedStatement ps;
        ResultSet resultSet;
        Cliente cliente = null;

        String sql = "INSERT INTO clientes (cpf, nome, nascimento, telefone, email, endereco, cidade, estado, cep) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = conn.prepareStatement(sql);

            ps.setString(1, cpf);
            ps.setString(2, nome);
            ps.setDate(3, nascimento);
            ps.setString(4, telefone);
            ps.setString(5, email);
            ps.setString(6, endereco);
            ps.setString(7, cidade);
            ps.setString(8, estado);
            ps.setString(9, cep);

            ps.execute();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}