package br.com.mercadoalves.controle.domain;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private String sshHostname = "20.127.8.100";
    private String sshUsername = "azureuser";
    private String mysqlHostname = "mercado-alves-mysql.mysql.database.azure.com";
    private int mysqlPort = 3306;
    private String mysqlUsername = "mercado23alves";
    private String mysqlPassword = "SqlPuc123";
    private String databaseName = "mercado";

    public Connection recuperarConexao() {
        String jdbcUrl = "jdbc:mysql://" + mysqlHostname + ":" + mysqlPort + "/" + databaseName +
                "?user=" + mysqlUsername + "&password=" + mysqlPassword;

        if (sshHostname != null && !sshHostname.isEmpty()) {
            // If using SSH tunnel
            jdbcUrl += "&useSSL=false&requireSSL=false&autoReconnect=true&" +
                    "sshHost=" + sshHostname + "&sshPort=22&sshUser=" + sshUsername + "&sshPassword=";
        }

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl);
            // Now, you can use the 'connection' object to execute SQL queries.
            System.out.println("Connected to the database!");
            return connection; // Return the connection to the caller
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Handle connection failure
        }
    }

    private HikariDataSource createDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + mysqlHostname + ":" + mysqlPort + "/" + databaseName);
        config.setUsername(mysqlUsername);
        config.setPassword(mysqlPassword);
        config.setMaximumPoolSize(10);

        return new HikariDataSource(config);
    }
}
