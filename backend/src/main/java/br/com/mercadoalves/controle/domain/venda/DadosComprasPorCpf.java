package br.com.mercadoalves.controle.domain.venda;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

public record DadosComprasPorCpf(Date data, Time hora, BigDecimal valor, String cupom) {
}
