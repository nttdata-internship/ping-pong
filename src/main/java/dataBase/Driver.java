package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class Driver {

	public static Connection DB() {

		try {
			Class.forName("org.h2.Driver");
			Connection con = DriverManager
					.getConnection("jdbc:h2:~/test", "sa", "");
			JOptionPane.showMessageDialog(null, "DB Connected");

			return con;

		} catch (Exception ex) {
			// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, ex);
			return null;
		}
	}
}
