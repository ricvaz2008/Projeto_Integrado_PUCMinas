package br.com.mercadoalves.controle.domain.venda;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

public class Venda {

    private String codigo;
    private String produto;
    private BigDecimal valor;
    private String cpf;
    private Date data;
    private Time hora;
    private String cupom;
    private String pagamento;

    public Venda(DadosVenda dados) {
        this.codigo = dados.codigo();
        this.produto = dados.produto();
        this.valor = dados.valor();
        this.cpf = dados.cpf();
        this.data = dados.data();
        this.cupom = dados.cupom();
        this.pagamento = dados.pagamento();
    }

    public Venda(DadosComprasPorCpf dados) {
        this.data = dados.data();
        this.hora = dados.hora();
        this.valor = dados.valor();
        this.cupom = dados.cupom();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venda venda = (Venda) o;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cupom);
    }

    public String getProduto() {
        return produto;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getCpf() {
        return cpf;
    }

    public Date getData() {
        return data;
    }

    public String getCupom() {
        return cupom;
    }

    public String getPagamento() {
        return pagamento;
    }
}