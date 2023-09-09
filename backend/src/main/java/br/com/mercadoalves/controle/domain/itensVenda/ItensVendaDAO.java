package br.com.mercadoalves.controle.domain.itensVenda;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItensVendaDAO {

    private final Connection conn;

    ItensVendaDAO(Connection connection) {
        this.conn = connection;
    }

    public List<ItensVenda> listarItensVendas(String originalCupom) {
        PreparedStatement ps;
        ResultSet resultSet;
        List<ItensVenda> itensVendas = new ArrayList<>();

        String cupom = originalCupom;
        int suffix = 0;
        cupom = originalCupom + "-" + suffix;

        while (true) {
            String sql = "SELECT i.quantidade, e.produto, i.valor, i.numeroVenda\n" +
                    "FROM itensvenda i\n" +
                    "JOIN estoque e ON i.codigo = e.codigo\n" +
                    "WHERE cupom = ?";
            try {
                ps = conn.prepareStatement(sql);
                ps.setString(1, cupom);
                resultSet = ps.executeQuery();

                if (resultSet.next()) {
                    Integer quantidade = resultSet.getInt(1);
                    String codProduto = resultSet.getString(2);
                    BigDecimal valor = resultSet.getBigDecimal(3);
                    String numeroVenda = resultSet.getString(4);
                    if (quantidade != null && codProduto != null && valor != null) {
                        DadosItensVenda dadosItensVenda = new DadosItensVenda(quantidade, codProduto, valor, cupom, numeroVenda);
                        ItensVenda itensVenda = new ItensVenda(dadosItensVenda);
                        itensVendas.add(itensVenda);
                    }
                } else {
                    break;
                }

                resultSet.close();
                ps.close();

                suffix++;
                cupom = originalCupom + "-" + suffix;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return itensVendas;
    }

    public void novoItensVendaBatch(List<ItensVenda> items) {
        String sql = "INSERT INTO itensvenda (cupom, numeroVenda, quantidade, codigo, valor) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (ItensVenda item : items) {
                ps.setString(1, item.getCupom());
                ps.setString(2, item.getNumeroVenda());
                ps.setInt(3, item.getQuantidade());
                ps.setString(4, item.getCodigo());
                ps.setBigDecimal(5, item.getValor());
                ps.addBatch();
            }

            int[] batchResults = ps.executeBatch();
            for (int i = 0; i < batchResults.length; i++) {
                if (batchResults[i] == Statement.EXECUTE_FAILED) {
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}