package br.com.mercadoalves.controle.domain.usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private final Connection conn;

    UsuarioDAO(Connection connection) {
        this.conn = connection;
    }

    public List<Usuario> listar(String coluna, String ordem) {
        PreparedStatement ps;
        ResultSet resultSet;
        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT * FROM usuarios " +
                "ORDER BY CASE " +
                "WHEN " + coluna + " REGEXP '^[0-9]+$' THEN CAST(" + coluna + " AS UNSIGNED) " +
                "ELSE " + coluna + " END " + ordem + ", " +
                "CAST(id AS UNSIGNED) " + ordem;

        try {
            ps = conn.prepareStatement(sql);
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString(1);
                String nome = resultSet.getString(2);
                String cargo = resultSet.getString(3);
                String login = resultSet.getString(4);
                String senha = resultSet.getString(5);
                String acesso = resultSet.getString(6);

                if (id == null || nome == null || cargo == null || login == null || senha == null || acesso == null) {
                    continue;
                }

                DadosCadastroUsuario dadosCadastroUsuarios = new DadosCadastroUsuario(id, nome, cargo, login, senha, acesso);
                Usuario usuario = new Usuario(dadosCadastroUsuarios);
                usuarios.add(usuario);
            }
            resultSet.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuarios;
    }

    public Usuario listarUsuarioPorId(String id) {
        PreparedStatement ps;
        ResultSet resultSet;
        Usuario usuario = null;

        String sql = "SELECT * FROM usuarios WHERE id = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            resultSet = ps.executeQuery();

            if (resultSet.next()) {
                String userId = resultSet.getString(1);
                String nome = resultSet.getString(2);
                String cargo = resultSet.getString(3);
                String login = resultSet.getString(4);
                String senha = resultSet.getString(5);
                String acesso = resultSet.getString(6);

                if (nome != null && cargo != null && login != null && senha != null && acesso != null) {
                    DadosCadastroUsuario dadosCadastroUsuarios = new DadosCadastroUsuario(userId, nome, cargo, login, senha, acesso);
                    usuario = new Usuario(dadosCadastroUsuarios);
                }
            }

            resultSet.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return usuario;
    }

    public void alterarUsuario(String id, String nome, String cargo, String login, String senha, String acesso) {
        PreparedStatement ps;
        ResultSet resultSet;
        Usuario usuario = null;

        String sql = "UPDATE usuarios SET nome = ?, cargo = ?, login = ?, senha = ?, acesso = ? WHERE id = ?";

        try {
            conn.setAutoCommit(false);

            ps = conn.prepareStatement(sql);

            ps.setString(1, nome);
            ps.setString(2, cargo);
            ps.setString(3, login);
            ps.setString(4, senha);
            ps.setString(5, acesso);
            ps.setString(6, id);
            ps.execute();
            ps.close();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        } finally {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void apagarUsuario(String id) {
        PreparedStatement ps;
        ResultSet resultSet;
        Usuario usuario = null;

        String sql = "DELETE FROM usuarios WHERE id = ?";

        try {
            ps = conn.prepareStatement(sql);

            ps.setString(1, id);

            ps.execute();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Usuario verificaNivelAcesso(String login, String senha) {
        PreparedStatement ps;
        ResultSet resultSet;
        Usuario usuario = null;
        String sql = "SELECT * FROM usuarios WHERE login = ? AND senha = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, login);
            ps.setString(2, senha);
            resultSet = ps.executeQuery();

            if (resultSet.next()) {
                String userId = resultSet.getString(1);
                String nome = resultSet.getString(2);
                String cargo = resultSet.getString(3);
                String acesso = resultSet.getString(6);

                if (userId != null && nome != null && cargo != null && acesso != null) {
                    DadosCadastroUsuario dadosCadastroUsuarios = new DadosCadastroUsuario(userId, nome, cargo, login, senha, acesso);
                    usuario = new Usuario(dadosCadastroUsuarios);
                }
            } else {
                usuario = new Usuario(new DadosCadastroUsuario("0", "Incorrect", "Incorrect", "", "", ""));
            }

            resultSet.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return usuario;
    }

    public void cadastrarUsuario(String id, String nome, String cargo, String login, String senha, String acesso) {
        PreparedStatement ps;
        ResultSet resultSet;
        Usuario usuario = null;

        String sql = "INSERT INTO usuarios (id, nome, cargo, login, senha, acesso) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            ps = conn.prepareStatement(sql);

            ps.setString(1, id);
            ps.setString(2, nome);
            ps.setString(3, cargo);
            ps.setString(4, login);
            ps.setString(5, senha);
            ps.setString(6, acesso);

            ps.execute();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}