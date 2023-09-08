package br.com.mercadoalves.controle.domain.venda;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

public record DadosInformacoesCupom (Date data, Time hora, BigDecimal valor, String caixa) {
}
