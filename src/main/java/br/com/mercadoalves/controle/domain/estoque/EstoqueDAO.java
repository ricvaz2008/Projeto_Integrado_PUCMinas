package br.com.mercadoalves.controle.domain.estoque;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class EstoqueDAO {

    private final Connection conn;

    EstoqueDAO(Connection connection) {
        this.conn = connection;
    }

    public List<Estoque> listar(String coluna, String ordem) {
        PreparedStatement ps;
        ResultSet resultSet;
        List<Estoque> estoques = new ArrayList<>();

        String sql = "SELECT * FROM estoque ORDER BY " + coluna + " " + ordem;

        try {
            ps = conn.prepareStatement(sql);
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                String codigo = resultSet.getString(1);
                String produto = resultSet.getString(2);
                String lote = resultSet.getString(3);
                Double quantidade = resultSet.getDouble(4);
                BigDecimal valor = resultSet.getBigDecimal(5);
                Date vencimento = resultSet.getDate(6);
                String status = resultSet.getString(7);
                String foto = resultSet.getString(8);

                if (codigo == null || produto == null || lote == null || quantidade == null || valor == null || vencimento == null || status == null || foto == null) {
                    continue;
                }

                DadosCadastroEstoque dadosCadastroEstoque = new DadosCadastroEstoque(codigo, produto, lote, quantidade, valor, vencimento, status, foto);
                Estoque estoque = new Estoque(dadosCadastroEstoque);
                estoques.add(estoque);
            }
            resultSet.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return estoques;
    }

    public Estoque listarEstoquePorCodigo(String codigo) {
        PreparedStatement ps;
        ResultSet resultSet;
        Estoque estoque = null;

        String sql = "SELECT * FROM estoque WHERE codigo = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, codigo);
            resultSet = ps.executeQuery();

            if (resultSet.next()) {
                String codigoProduto = resultSet.getString(1);
                String produto = resultSet.getString(2);
                String lote = resultSet.getString(3);
                Double quantidade = resultSet.getDouble(4);
                BigDecimal valor = resultSet.getBigDecimal(5);
                Date vencimento = resultSet.getDate(6);
                String status = resultSet.getString(7);
                String foto = resultSet.getString(8);

                if (codigoProduto != null && produto != null && lote != null && quantidade != null && valor != null && vencimento != null && status != null && foto != null) {
                    DadosCadastroEstoque dadosCadastroEstoque = new DadosCadastroEstoque(codigoProduto, produto, lote, quantidade, valor, vencimento, status, foto);
                    estoque = new Estoque(dadosCadastroEstoque);
                }
            }

            resultSet.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return estoque;
    }

    public void alterarEstoque(String codigo, String produto, String lote, Double quantidade, BigDecimal valor, Date vencimento, String status, String foto) {
        PreparedStatement ps;

        String sql = "UPDATE estoque SET produto = ?, lote = ?, quantidade = ?, valor = ?, vencimento = ?, status = ?, foto = ? WHERE codigo = ?";

        try {
            conn.setAutoCommit(false);

            ps = conn.prepareStatement(sql);

            ps.setString(1, produto);
            ps.setString(2, lote);
            ps.setDouble(3, quantidade);
            ps.setBigDecimal(4, valor);
            ps.setDate(5, vencimento);
            ps.setString(6, status);
            ps.setString(7, foto);
            ps.setString(8, codigo);

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

    public void alterarQuantidadeEstoque(String codigo, Double quantidade) {
        PreparedStatement ps;

        String sql = "UPDATE estoque SET quantidade = ? WHERE codigo = ?";

        try {
            conn.setAutoCommit(false);

            ps = conn.prepareStatement(sql);

            ps.setDouble(1, quantidade);
            ps.setString(2, codigo);

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

    public void apagarEstoque(String codigo) {
        PreparedStatement ps;

        String sql = "DELETE FROM estoque WHERE codigo = ?";

        try {
            ps = conn.prepareStatement(sql);

            ps.setString(1, codigo);

            ps.execute();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cadastrarEstoque(String codigo, String produto, String lote, Double quantidade, BigDecimal valor, Date vencimento, String status, String foto) {
        PreparedStatement ps;

        String sql = "INSERT INTO estoque (codigo, produto, lote, quantidade, valor, vencimento, status, foto) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            ps = conn.prepareStatement(sql);

            ps.setString(1, codigo);
            ps.setString(2, produto);
            ps.setString(3, lote);
            ps.setDouble(4, quantidade);
            ps.setBigDecimal(5, valor);
            ps.setDate(6, vencimento);
            ps.setString(7, status);
            ps.setString(8, foto);

            ps.execute();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}