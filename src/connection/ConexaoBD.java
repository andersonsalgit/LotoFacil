package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {

	private static Connection instance = null;

	public static Connection getInstance() throws SQLException {
		if (instance == null || instance.isClosed()) {
			new ConexaoBD();
		}
		return instance;
	}
	
	/*private ConexaoBD() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		try {
			instance = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Loto", "postgres", "postgres");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}*/

	private ConexaoBD() {
		
		String endpoint = "local-db.cr5f3avcvrct.sa-east-1.rds.amazonaws.com";
		
		System.out.println("\n");
		System.out.println("Endpoint: "+endpoint);
		System.out.println("\n");
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		try {
			instance = DriverManager.getConnection("jdbc:postgresql://"+endpoint+":5432/loto", "saldevdb", "saldevdb");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

}
