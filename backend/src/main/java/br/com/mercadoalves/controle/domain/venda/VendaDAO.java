package br.com.mercadoalves.controle.domain.venda;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {

    private final Connection conn;

    VendaDAO(Connection connection) {
        this.conn = connection;
    }

    public List<DadosVenda> listarVendas(String coluna, String ordem) {
        PreparedStatement ps;
        ResultSet resultSet;
        List<DadosVenda> vendas = new ArrayList<>();

        String sql = "SELECT i.codigo, e.produto, i.valor, v.cpf, v.data, i.cupom, v.pagamento\n" +
                "FROM itensvenda i\n" +
                "JOIN vendas v ON i.numeroVenda = v.numeroVenda\n" +
                "JOIN estoque e ON i.codigo = e.codigo\n" +
                "ORDER BY " + coluna + " " + ordem;

        try {
            ps = conn.prepareStatement(sql);
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                String codigo = resultSet.getString(1);
                String produto = resultSet.getString(2);
                BigDecimal valor = resultSet.getBigDecimal(3);
                String cpf = resultSet.getString(4);
                Date data = resultSet.getDate(5);
                String cupom = resultSet.getString(6);
                String pagamento = resultSet.getString(7);

                if (codigo == null || produto == null || valor == null || cpf == null || data == null || cupom == null || pagamento == null) {
                    continue;
                }

                DadosVenda dadosVenda = new DadosVenda(codigo, produto, valor, cpf, data, cupom, pagamento);
                Venda venda = new Venda(dadosVenda);
                vendas.add(dadosVenda);
            }
            resultSet.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vendas;
    }

    public InformacoesCupom informacoesGeraisCupom(String cupom) {
        PreparedStatement ps;
        ResultSet resultSet;
        InformacoesCupom informacoesCupom = null;

        String sql = "SELECT v.data, v.hora, v.valorTotal, v.caixa\n" +
                "FROM vendas v\n" +
                "JOIN itensVenda i ON v.numeroVenda = i.numeroVenda\n" +
                "WHERE i.cupom = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, cupom);
            resultSet = ps.executeQuery();

            if (resultSet.next()) {
                Date data = resultSet.getDate(1);
                Time hora = resultSet.getTime(2);
                BigDecimal valor = resultSet.getBigDecimal(3);
                String caixa = resultSet.getString(4);

                if (data != null && hora != null && valor != null && caixa != null) {
                    DadosInformacoesCupom dadosInformacoesCupom = new DadosInformacoesCupom(data, hora, valor, caixa);
                    informacoesCupom = new InformacoesCupom(dadosInformacoesCupom);
                }
            }

            resultSet.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return informacoesCupom;
    }

    public List<ComprasPorCpf> listarComprasPorCpf(String coluna, String ordem, String cpf) {
        PreparedStatement ps;
        ResultSet resultSet;
        List<ComprasPorCpf> compras = new ArrayList<>();

        String sql = "SELECT v.data, v.hora, v.valorTotal, i.base_cupom " +
                "FROM vendas v " +
                "JOIN (" +
                "    SELECT v2.numeroVenda, SUBSTRING_INDEX(MAX(i2.cupom), '-', 1) AS base_cupom " +
                "    FROM vendas v2 " +
                "    JOIN itensVenda i2 ON v2.numeroVenda = i2.numeroVenda " +
                "    WHERE v2.cpf = ? " +
                "    GROUP BY v2.numeroVenda" +
                ") i ON v.numeroVenda = i.numeroVenda " +
                "WHERE v.cpf = ? " +
                "ORDER BY " + coluna + " " + ordem;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, cpf);
            ps.setString(2, cpf);
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Date data = resultSet.getDate(1);
                Time hora = resultSet.getTime(2);
                BigDecimal valor = resultSet.getBigDecimal(3);
                String cupom = resultSet.getString(4);

                if (data == null || hora == null || valor == null || cupom == null) {
                    continue;
                }

                DadosComprasPorCpf dadosComprasPorCpf = new DadosComprasPorCpf(data, hora, valor, cupom);
                ComprasPorCpf comprasPorCpf = new ComprasPorCpf(dadosComprasPorCpf);
                compras.add(comprasPorCpf);
            }
            resultSet.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return compras;
    }

    public void cadastrarVendaRealizada(String numeroVenda, String cpf, BigDecimal valorTotal, Date data, String hora, String pagamento, String caixa) {
        PreparedStatement ps;
        ResultSet resultSet;
        Venda venda = null;

        String sql = "INSERT INTO vendas (numeroVenda, cpf, valorTotal, data, hora, pagamento, caixa) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, numeroVenda);
            ps.setString(2, cpf);
            ps.setBigDecimal(3, valorTotal);
            ps.setDate(4, data);
            ps.setString(5, hora);
            ps.setString(6, pagamento);
            ps.setString(7, caixa);
            ps.execute();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}