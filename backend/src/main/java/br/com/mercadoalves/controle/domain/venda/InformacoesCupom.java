package br.com.mercadoalves.controle.domain.venda;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

public class InformacoesCupom {
    private Date data;
    private Time hora;
    private BigDecimal valor;
    private String caixa;

    public InformacoesCupom(DadosInformacoesCupom dados) {
        this.data = dados.data();
        this.hora = dados.hora();
        this.valor = dados.valor();
        this.caixa = dados.caixa();
    }

    public InformacoesCupom(Date data, Time hora, BigDecimal valor, String caixa) {
        this.data = data;
        this.hora = hora;
        this.valor = valor;
        this.caixa = caixa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InformacoesCupom cupom = (InformacoesCupom) o;
        return Objects.equals(data, cupom.data);
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

    public String getCaixa() {
        return caixa;
    }
}