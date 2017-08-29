package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseUtil {

	private static final String DB_USER = "sa";
	private static String DB_URL = "jdbc:h2:tcp://localhost/~/test";
	private static final String DB_PASSWORD = "";

	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("org.h2.Driver");
			con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return con;
	}
}
