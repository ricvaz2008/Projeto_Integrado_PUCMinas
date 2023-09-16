package br.com.mercadoalves.controle.domain.itensVenda;

import br.com.mercadoalves.controle.domain.venda.Venda;

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
    public void novoItensVenda(String quantidade, String codProduto, BigDecimal valor, String cupom, String numeroVenda){
        PreparedStatement ps;
        ResultSet resultSet;

        String sql = "INSERT INTO itensvenda (cupom, numeroVenda, quantidade, codigo, valor) VALUES (?, ?, ?, ?, ?)";
        try {
            ps = conn.prepareStatement(sql);

            ps.setString(1, cupom);
            ps.setString(2, numeroVenda);
            ps.setString(3, quantidade);
            ps.setString(4, codProduto);
            ps.setBigDecimal(5, valor);

            ps.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}