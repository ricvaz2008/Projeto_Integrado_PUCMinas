package br.com.mercadoalves.controle.domain.venda;

import java.math.BigDecimal;
import java.sql.Date;

public record DadosVenda (String codigo, String produto, BigDecimal valor, String cpf, Date data, String cupom, String pagamento) {
}
