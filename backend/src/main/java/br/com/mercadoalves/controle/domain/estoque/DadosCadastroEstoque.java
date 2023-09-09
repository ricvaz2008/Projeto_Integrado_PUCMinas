package br.com.mercadoalves.controle.domain.estoque;

import java.math.BigDecimal;
import java.sql.Date;

public record DadosCadastroEstoque (String codigo, String produto, String lote, double quantidade, BigDecimal valor, Date vencimento, String status, String foto) {
}
