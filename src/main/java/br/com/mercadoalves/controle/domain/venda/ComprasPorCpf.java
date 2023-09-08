package br.com.mercadoalves.controle.domain.venda;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

public class ComprasPorCpf {
    private Date data;
    private Time hora;
    private BigDecimal valor;
    private String cupom;

    public ComprasPorCpf(DadosComprasPorCpf dados) {
        this.data = dados.data();
        this.hora = dados.hora();
        this.valor = dados.valor();
        this.cupom = dados.cupom();
    }

    public ComprasPorCpf(Date data, Time hora, BigDecimal valor, String cupom) {
        this.data = data;
        this.hora = hora;
        this.valor = valor;
        this.cupom = cupom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComprasPorCpf comprasPorCpf = (ComprasPorCpf) o;
        return Objects.equals(data, comprasPorCpf.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    public Date getData() {
        return data;
    }

    public Time getHora() {
        return hora;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getCupom() {
        return cupom;
    }
}