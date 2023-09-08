package br.com.mercadoalves.controle.cliente;

import java.sql.Date;

public record DadosCadastroCliente (String cpf, String nome, Date nascimento, String telefone, String email, String endereco, String cidade, String estado, String cep) {
}
