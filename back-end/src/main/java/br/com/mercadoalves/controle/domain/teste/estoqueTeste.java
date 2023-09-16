package br.com.mercadoalves.controle.domain.teste;

import br.com.mercadoalves.controle.domain.cliente.Cliente;
import br.com.mercadoalves.controle.domain.estoque.DadosCadastroEstoque;
import br.com.mercadoalves.controle.domain.estoque.Estoque;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class estoqueTeste {
    @Test
    public void testEquals() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String codigo = "0123456789012";
        String codigo1 = "1122334455667";
        String produto = "Banana";
        String produto1 = "Achocolatado";
        String lote = "665HH201L";
        String lote1 = "9996JKL33";
        Double quantidade = 86.0;
        Double quantidade1 = 965.0;
        BigDecimal valor = new BigDecimal(85);
        BigDecimal valor1 = new BigDecimal(66);
        Date vencimento = dateFormat.parse("2023-11-05");
        Date vencimento1 = dateFormat.parse("2025-05-05");
        String status = "Ativo";
        String status1 = "Ativo";
        String foto = "./assets/banana.jpg";
        String foto1 = "./assets/achocolatado.jpg";

        Estoque estoque1 = new Estoque(codigo, produto, lote, quantidade, valor, (java.sql.Date) vencimento, status, foto);
        Estoque estoque2 = new Estoque(codigo1, produto, lote, quantidade, valor, (java.sql.Date) vencimento, status, foto);
        Estoque estoque3 = new Estoque(codigo1, produto1, lote1, quantidade1, valor1, (java.sql.Date) vencimento1, status1, foto1);

        assertEquals(estoque1, estoque2);
        assertEquals(estoque2, estoque3);
    }
}
