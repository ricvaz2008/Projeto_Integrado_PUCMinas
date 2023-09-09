package br.com.mercadoalves.controle.domain.estoque;

import java.math.BigDecimal;

public record DadosCarrinho (String descricao, BigDecimal quantidade, BigDecimal valor, BigDecimal valorTotal) {
}
