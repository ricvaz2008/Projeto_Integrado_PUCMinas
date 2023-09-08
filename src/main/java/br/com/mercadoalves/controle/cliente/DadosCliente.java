package br.com.mercadoalves.controle.cliente;

import java.sql.Date;

public record DadosCliente (String cpf, String nome, Date ultimaCompra, String cupom) {
}
