package br.com.mercadoalves.controle.domain.estoque;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

public class Estoque {

    private String codigo;
    private String produto;
    private String lote;
    private Double quantidade;
    private BigDecimal valor;
    private Date vencimento;
    private String status;
    private String foto;

    public Estoque(DadosCadastroEstoque dados) {
        this.codigo = dados.codigo();
        this.produto = dados.produto();
        this.lote = dados.lote();
        this.quantidade = dados.quantidade();
        this.valor = dados.valor();
        this.vencimento = dados.vencimento();
        this.status = dados.status();
        this.foto = dados.foto();
    }

    public Estoque(String codigo, String produto, String lote, Double quantidade, BigDecimal valor, Date vencimento, String status, String foto) {
        this.codigo = codigo;
        this.produto = produto;
        this.lote = lote;
        this.quantidade = quantidade;
        this.valor = valor;
        this.vencimento = vencimento;
        this.status = status;
        this.foto = foto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estoque estoque = (Estoque) o;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    public String getCodigo() {
        return codigo;
    }

    public String getProduto() {
        return produto;
    }

    public String getLote() {
        return lote;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public String getStatus() {
        return status;
    }

    public String getFoto() {
        return foto;
    }
}