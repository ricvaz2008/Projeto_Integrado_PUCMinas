package br.com.mercadoalves.controle.domain.itensVenda;

import br.com.mercadoalves.controle.domain.ConnectionFactory;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ItensVendaService {

    private final ConnectionFactory connection;

    public ItensVendaService() {
        this.connection = new ConnectionFactory();
    }

    public List<ItensVenda> listarItensVendas(String cupom) {
        Connection conn = connection.recuperarConexao();
        return new ItensVendaDAO(conn).listarItensVendas(cupom);
    }

    public void novoItensVenda(String quantidade, String codProduto, BigDecimal valor, String cupom, String numeroVenda) {
        Connection conn = connection.recuperarConexao();
        List<ItensVenda> items = new ArrayList<>();

        ItensVenda item = new ItensVenda(quantidade, codProduto, valor, cupom, numeroVenda);
        item.setCupom(cupom);
        item.setProduto(codProduto);
        item.setNumeroVenda(numeroVenda);
        item.setQuantidade(Integer.valueOf(quantidade));
        item.setCodigo(codProduto);
        item.setValor(valor);

        items.add(item);

        new ItensVendaDAO(conn).novoItensVendaBatch(items);
    }
}