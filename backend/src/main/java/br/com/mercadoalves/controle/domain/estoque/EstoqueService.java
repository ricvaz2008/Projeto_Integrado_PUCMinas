package br.com.mercadoalves.controle.domain.estoque;

import br.com.mercadoalves.controle.domain.ConnectionFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public class EstoqueService {

    private final ConnectionFactory connection;

    public EstoqueService() {
        this.connection = new ConnectionFactory();
    }

    public List<Estoque> listarEstoque(String coluna, String ordem) {
        Connection conn = connection.recuperarConexao();
        return new EstoqueDAO(conn).listar(coluna, ordem);
    }

    public Estoque listarEstoquePorCodigo(String codigo) {
        Connection conn = connection.recuperarConexao();
        return new EstoqueDAO(conn).listarEstoquePorCodigo(codigo);
    }

    public void alterarEstoque(String codigo, String produto, String lote, Double quantidade, BigDecimal valor, Date vencimento, String status, String foto) {
        Connection conn = connection.recuperarConexao();
        new EstoqueDAO(conn).alterarEstoque(codigo, produto, lote, quantidade, valor, vencimento, status, foto);
    }

    public void alterarQuantidadeEstoque(String codigo, Double quantidade) {
        Connection conn = connection.recuperarConexao();
        new EstoqueDAO(conn).alterarQuantidadeEstoque(codigo, quantidade);
    }

    public void apagarEstoque(String codigo) {
        System.out.println(codigo);
        Connection conn = connection.recuperarConexao();
        new EstoqueDAO(conn).apagarEstoque(codigo);
    }

    public void cadastrarEstoque(String codigo, String produto, String lote, Double quantidade, BigDecimal valor, Date vencimento, String status, String foto) {
        Connection conn = connection.recuperarConexao();
        new EstoqueDAO(conn).cadastrarEstoque(codigo, produto, lote, quantidade, valor, vencimento, status, foto);
    }
}