package br.CaixaMA.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(
				"jdbc:postgresql://10.1.1.10:5432/DB", "postgres", "12345");
	}
}
