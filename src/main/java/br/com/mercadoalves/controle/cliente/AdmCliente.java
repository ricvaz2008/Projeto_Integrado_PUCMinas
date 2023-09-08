package br.com.mercadoalves.controle.cliente;

import java.sql.Date;
import java.util.Objects;

public class AdmCliente {
    private String cpf;
    private String nome;
    private Date ultimaCompra;
    private String cupom;

    public AdmCliente(DadosCliente dados) {
        this.cpf = dados.cpf();
        this.nome = dados.nome();
        this.ultimaCompra = dados.ultimaCompra();
        this.cupom = dados.cupom();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public Date getUltimaCompra() {
        return ultimaCompra;
    }

    public String getCupom() {
        return cupom;
    }
}