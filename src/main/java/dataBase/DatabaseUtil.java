package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;

import com.nttdata.internship.ui.network.SocketConnection;

public class DatabaseUtil {

	private static final String DB_USER = "sa";
	private static String DB_URL;
	private static final String DB_PASSWORD = "";

	static {
		DB_URL = String.format("jdbc:h2:tcp://%s:9092/~/test", SocketConnection.gameProperties.getProperty("game.host"));
	}

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
