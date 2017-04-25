package CSKAirlines;

import java.util.Date;
import java.util.Locale;
import java.util.Calendar;
import java.util.ArrayList;
import java.sql.*;
/*
** Db is simple simluator of database
** This provides seem for implementing database operation later
*/
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DB {
	
	
	long nextReservationNumber = 1;
	
	/*
	** User CRUD operations
	*/
	public void create(User user) {
		  int isAdmin = user instanceof Administrator ? 1 : 0;//1 = Admin, 0 = Customer
        
		String query = "INSERT INTO USER (USERNAME, PASSWORD, SECURITY_QUESTION, SECURITY_ANSWER, FIRST_NAME, LAST_NAME, SSN, ADDRESS, ZIP_CODE, STATE, EMAIL, IS_ADMIN) VALUES" +
					   "('" + user.getUsername() + ins + user.getPassword() + ins + user.getSecurityQuestion() + ins + user.getSecurityQuestionAnswer() + ins + user.getFirstName() + ins
					   + user.getLastName() + ins + user.getSSN() + ins + user.getAddress() + ins + user.getZipCode() + ins + user.getState() + ins + user.getEmail() + ins + isAdmin + "');";
		try {//try executing query 
			Driver.executeInsert(query);
		} catch(SQLException e) {
			System.err.println("Exception while creating user "+e);//Prints as error
		}

		
	}
	
	private User createUser(ResultSet rs) throws SQLException {//creates user from result set - db
		User user = null;
		
			if(rs.getInt("IS_ADMIN") == 1) {
				user = new Administrator(
						rs.getString("FIRST_NAME"),
						rs.getString("LAST_NAME"),
						rs.getString("ADDRESS"),
						rs.getString("ZIP_CODE"),
						rs.getString("STATE"),
						rs.getString("USERNAME"),
						rs.getString("PASSWORD"),
						rs.getString("EMAIL"),
						rs.getString("SSN")
					);
			} else {
				user = new Customer(
						rs.getString("FIRST_NAME"),
						rs.getString("LAST_NAME"),
						rs.getString("ADDRESS"),
						rs.getString("ZIP_CODE"),
						rs.getString("STATE"),
						rs.getString("USERNAME"),
						rs.getString("PASSWORD"),
						rs.getString("EMAIL"),
						rs.getString("SSN")
				);
			
		}
			return user;
	}
	public User getUserByID(String id) {//USERNAME
		User user = null;
		if((user = findUserByID(id)) != null)
			return user;
		String query = "SELECT FIRST_NAME, LAST_NAME, ADDRESS, ZIP_CODE, STATE, USERNAME, PASSWORD, EMAIL, SSN, IS_ADMIN from USER WHERE USERNAME = '" + id + "'";
		try {
			ResultSet rs = Driver.executeQuery(query);//catches exception from Driver
			if(rs.next())//see if Users are in rs
				user = createUser(rs);//Exception from createUser
			rs.close();//make room for other resources - connection and memory 
		} catch(SQLException e) {
			System.err.println("Exception while getting User "+e);
		}
			return user;
	}
	
	public void update(User user) {//user is passed already updated
		String query = "UPDATE USER SET FIRST_NAME = '" + user.getFirstName() + "', LAST_NAME = '" + user.getLastName() + "', ADDRESS = '" + user.getAddress() + "', ZIP_CODE = '" + 
						user.getZipCode() + "', STATE = '" + user.getState() + "', PASSWORD = '" + user.getPassword() + "', EMAIL = '" + user.getEmail() + "', SECURITY_QUESTION = '" + 
						user.getSecurityQuestion() + "', SECURITY_ANSWER = '" + user.getSecurityQuestionAnswer() + "'" + "WHERE USERNAME = '" + user.getUsername() + "'";
		try {
        	Driver.executeUpdate(query);//need to add update method in Driver class
		} catch(SQLException e) {
			System.err.println("Exception while updating user "+e);
		}
	}
	
	public  void delete(User user) {
		String query = "DELETE FROM USER WHERE USERNAME = '" + user.getUsername() + "';";//USERNAME is distinct
		try{
			Driver.executeDelete(query);
			remove(user);

		}catch(Exception e) {
			System.err.println("Exception while deleting user" + e);
		}
	}

	/*
	** Flight CRUD operations
	*/
	public void create(Flight flight){
		//add flight to database
		
	}
	
	private Flight createFlight(ResultSet rs) throws SQLException {
		
	}
	
	
	
	
	public  Flight getFlightByDepartureCityAndDateAndNumber(String departureCity, Date departureDate, int flightNumber){
		
	}
	
	
	
	public Flight[] getFlightByNumberAndDepartureDate(int flightNumber, Date departureDate){//next 4 methods returns array b/c more than 1 flight can have same characteristics
		
	}
	
	public Flight[] getFlightByDepartureCityAndDate(String departureCity, Date departureDate){
		
	}
	
	public  Flight[] getFlightByDepartureCityAndArrivalCity(String departureCity, String arrivalCity){
		
	}
	public  Flight[] getAllFlights() {
		
	}
	
	public void update(Flight flight){//like in update Customer - the flight is passed in with updated 
		
	}
	
	public  void delete(Flight flight){
		
	}


	/*
	** Reservation CRUD operations
	*/
	
	
}
