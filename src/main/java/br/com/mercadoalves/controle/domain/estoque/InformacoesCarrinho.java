package br.com.mercadoalves.controle.domain.estoque;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class InformacoesCarrinho {
    private String descricao;
    private String codProduto;
    private BigDecimal quantidade;
    private BigDecimal valor;
    private BigDecimal valorTotal;
    private BigDecimal subtotal;

    public InformacoesCarrinho(
            @JsonProperty("descricao") String descricao,
            @JsonProperty("codProduto") String codProduto,
            @JsonProperty("quantidade") BigDecimal quantidade,
            @JsonProperty("valor") BigDecimal valor,
            @JsonProperty("valorTotal") BigDecimal valorTotal,
            @JsonProperty("subtotal") BigDecimal subtotal
    ) {
        this.descricao = descricao;
        this.codProduto = codProduto;
        this.quantidade = quantidade;
        this.valor = valor;
        this.valorTotal = valorTotal;
        this.subtotal = subtotal;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getCodProduto() {
        return codProduto;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }
}