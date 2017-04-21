package jdbc;

import java.sql.DriverManager;

import java.sql.*;

public class Driver {

	public static void main(String[] args) {
		
		Connection connection = null;
		String dburl = "jdbc:mysql://192.168.2.105:3306/CSKAirlines?autoReconnect=true&useSSL=false";
		String userName = "CSKAirlines";
		String passWord = "3270!?ChungSaarthKrishna.";



		 try {
			 	//this part doesn't work because i don't have a table named Example, but if you get "Database connection terminated" then i think we're good to go
		        Class.forName("com.mysql.jdbc.Driver");

		        connection = DriverManager.getConnection(dburl, userName, passWord);
		        Statement st = connection.createStatement();                                 

		        String query = "INSERT INTO Example (`TestColumn`) VALUES('hello')";
		        int rsI = st.executeUpdate(query);
		        System.out.println("Hi");
		        }catch (Exception e) {
		        System.out.println(e);
		    } finally {
		        if (connection != null) {
		            try {
		                connection.close();
		                System.out.println("Database connection terminated");
		            } catch (Exception e) { /* ignore close errors */ }
		        }
		    }
	}
}
