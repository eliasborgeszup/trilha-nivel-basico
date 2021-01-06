package br.com.zup.primeiro.desafio.connection.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	public Connection obterConexao() {
		try {
			return DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/trilha_cliente?user=root&password=root&useTimezone=true&serverTimezone=UTC");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
