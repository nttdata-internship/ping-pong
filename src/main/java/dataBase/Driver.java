package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Driver {
	 Connection con =  null;
	    Statement st;
	    ResultSet rs;
	    
	    public static Connection DB(){
	        
	        try{
	            Class.forName("org.sqlite.JDBC");
	            Connection con = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\ioana.constantin\\Desktop\\ping-pong.db");
	            JOptionPane.showMessageDialog(null,"Connected");
	            
	            return con;
	           
	            
	        }catch(Exception ex){
	            JOptionPane.showMessageDialog(null,ex);
	            return null;
	        }
	}
}
