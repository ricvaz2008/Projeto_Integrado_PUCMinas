package br.com.mercadoalves.controle.domain.itensVenda;

import java.math.BigDecimal;
import java.util.Objects;

public class ItensVenda {

    private Integer quantidade;
    private String produto;
    private BigDecimal valor;
    private String cupom;
    private String numeroVenda;
    private String codProduto;

    public ItensVenda(DadosItensVenda dados) {
        this.quantidade = dados.quantidade();
        this.produto = dados.codProduto();
        this.valor = dados.valor();
        this.cupom = dados.cupom();
        this.numeroVenda = dados.numeroVenda();
        this.codProduto = dados.codProduto();
    }

    public ItensVenda(String quantidade, String codProduto, BigDecimal valor, String cupom, String numeroVenda) {
        this.quantidade = Integer.valueOf(quantidade);
        this.produto = codProduto;
        this.valor = valor;
        this.cupom = cupom;
        this.numeroVenda = numeroVenda;
        this.codProduto = codProduto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItensVenda itensVenda = (ItensVenda) o;
        return Objects.equals(quantidade, itensVenda.quantidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantidade);
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public String getProduto() {
        return produto;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getCupom() {
        return cupom;
    }

    public String getNumeroVenda() {
        return numeroVenda;
    }

    public String getCodigo() {
        return codProduto;
    }

    public void setCupom(String cupom) {
        this.cupom = cupom;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public void setNumeroVenda(String numeroVenda) {
        this.numeroVenda = numeroVenda;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public void setCodigo(String codigo) {
        this.codProduto = codigo;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}