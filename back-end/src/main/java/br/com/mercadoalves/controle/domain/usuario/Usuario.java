package br.com.mercadoalves.controle.domain.usuario;

import java.util.Objects;

public class Usuario {
    private String id;
    private String nome;
    private String cargo;
    private String login;
    private String senha;
    private String acesso;

    public Usuario(DadosCadastroUsuario dados) {
        this.id = dados.id();
        this.nome = dados.nome();
        this.cargo = dados.cargo();
        this.login = dados.login();
        this.senha = dados.senha();
        this.acesso = dados.acesso();
    }

    public Usuario(String id, String nome, String cargo, String login, String senha, String acesso) {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", cargo='" + cargo + '\'' +
                ", login='" + login + '\'' +
                ", senha='" + senha + '\'' +
                ", acesso='" + acesso + '\'' +
                '}';
    }

    public String getNome() {
        return nome;
    }

    public String getId() {
        return id;
    }

    public String getCargo() {
        return cargo;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }
    public String getAcesso() {return acesso;}
}