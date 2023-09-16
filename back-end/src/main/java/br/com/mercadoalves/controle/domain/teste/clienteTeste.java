package br.com.mercadoalves.controle.domain.teste;

import br.com.mercadoalves.controle.domain.cliente.Cliente;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class clienteTeste {
    @Test
    public void testEquals() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String nome = "Joao";
        String nome2 = "Maria";
        Date nascimento = dateFormat.parse("1990-01-01");
        String cpf = "23263021455";
        String telefone = "996326522";
        String email = "joao@email.com";
        String endereco = "Avenida dos Pardais, 520. Jardim Flores";
        String cidade = "Santos";
        String estado = "SP";
        String cep = "09811222";

        Cliente cliente1 = new Cliente(nome, (java.sql.Date) nascimento, cpf, telefone, email, endereco, cidade, estado, cep);
        Cliente cliente2 = new Cliente(nome2, (java.sql.Date) nascimento, cpf, telefone, email, endereco, cidade, estado, cep);

        assertEquals(cliente1, cliente2);
    }
}
