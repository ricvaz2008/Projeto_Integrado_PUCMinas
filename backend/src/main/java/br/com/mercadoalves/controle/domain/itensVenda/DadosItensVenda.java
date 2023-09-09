package br.com.mercadoalves.controle.domain.itensVenda;

import java.math.BigDecimal;
import java.sql.Date;

public record DadosItensVenda (Integer quantidade, String codProduto, BigDecimal valor, String cupom, String numeroVenda) {
}
