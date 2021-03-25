package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

	
	// (1)
	public static Connection getConnection() throws SQLException {
		String jdbcURL = "jdbc:mysql://localhost/iscritticorsi?user=root&password=federica";
		return DriverManager.getConnection(jdbcURL);
		// quando scrivo questo return mi sottolinea perche
		// potrei scatenare un'eccezione quindi la aggiungo
	}

}
