package CSKAirlines;

import java.sql.*;

public class Driver {

	// String dburl = "jdbc:mysql://192.168.2.105:3306/CSKAirlines?autoReconnect=true&useSSL=false";
	final static String dburl = "jdbc:mysql://127.0.0.1:3306/CSKAirlines?useSSL=false";
	final static String userName = "CSKAirlines";
	final static String passWord = "3270!?ChungSaarthKrishna.";
	static private Connection connection = null;

	public static void init() {// establishes a connection
		try {
			if (connection == null) {
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(dburl, userName, passWord);
			}
		} catch (Exception e) {
			System.err.println("Exception while connecting to DB " + e);
		}
		return;
	}

	public static ResultSet executeQuery(String query) throws SQLException {
		try {
			Statement statement = connection.createStatement();
			System.out.println("Executing query " + query);
			return statement.executeQuery(query);
		} catch (Exception e) {
			System.err.println("Exception while executing query " + query + " Error " + e);
		}
		// dont close statement because ResultSet would automatically close
		return null;
	}

	public static boolean executeInsert(String query) throws SQLException {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			System.out.println("Executing query " + query);
			return statement.execute(query);
		} catch (Exception e) {
			System.err.println("Exception while executing query " + query + " Error " + e);
		} finally {
			if (statement != null)
				statement.close();
		}
		return false;
	}

	public static boolean executeDelete(String query) throws SQLException {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			System.out.println("Executing query " + query);
			return statement.execute(query);
		} catch (Exception e) {
			System.err.println("Exception while executing query " + query + " Error " + e);
		} finally {
			if (statement != null)
				statement.close();
		}
		return false;
	}

}
