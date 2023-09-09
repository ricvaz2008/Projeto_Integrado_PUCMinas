package br.com.mercadoalves.controle.domain.usuario;

import br.com.mercadoalves.controle.domain.ConnectionFactory;
import java.sql.Connection;
import java.util.List;

public class UsuarioService {
    private final ConnectionFactory connection;

    public UsuarioService() {
        this.connection = new ConnectionFactory();
    }

    public List<Usuario> listarUsuarios(String coluna, String ordem) {
        Connection conn = connection.recuperarConexao();
        return new UsuarioDAO(conn).listar(coluna, ordem);
    }

    public Usuario listarUsuarioPorId(String id) {
        Connection conn = connection.recuperarConexao();
        return new UsuarioDAO(conn).listarUsuarioPorId(id);
    }

    public Usuario verificaNivelAcesso(String usuario, String senha) {
        Connection conn = connection.recuperarConexao();
        return new UsuarioDAO(conn).verificaNivelAcesso(usuario, senha);
    }

    public void alterarUsuario(String id, String nome, String cargo, String login, String senha) {
        Connection conn = connection.recuperarConexao();
        new UsuarioDAO(conn).alterarUsuario(id, nome, cargo, login, senha);
    }

    public void apagarUsuario(String id) {
        Connection conn = connection.recuperarConexao();
        new UsuarioDAO(conn).apagarUsuario(id);
    }

    public void cadastrarUsuario(String id, String nome, String cargo, String login, String senha) {
        Connection conn = connection.recuperarConexao();
        new UsuarioDAO(conn).cadastrarUsuario(id, nome, cargo, login, senha);
    }
}