package br.com.mercadoalves.controle.domain.venda;

import br.com.mercadoalves.controle.domain.ConnectionFactory;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public class VendaService {

    private final ConnectionFactory connection;

    public VendaService() {
        this.connection = new ConnectionFactory();
    }

    public List<DadosVenda> listarVendas(String coluna, String ordem) {
        Connection conn = connection.recuperarConexao();
        return new VendaDAO(conn).listarVendas(coluna, ordem);
    }

    public InformacoesCupom informacoesGeraisCupom(String cupom) {
        Connection conn = connection.recuperarConexao();
        return new VendaDAO(conn).informacoesGeraisCupom(cupom);
    }

    public List<ComprasPorCpf> listarComprasPorCpf(String coluna, String ordem, String cpf) {
        Connection conn = connection.recuperarConexao();
        return new VendaDAO(conn).listarComprasPorCpf(coluna, ordem, cpf);
    }

    public void cadastrarVendaRealizada(String numeroVenda, String cpf, BigDecimal valorTotal, Date data, String hora, String pagamento, String caixa) {
        Connection conn = connection.recuperarConexao();
        new VendaDAO(conn).cadastrarVendaRealizada(numeroVenda, cpf, valorTotal, data, hora, pagamento, caixa);
    }
}