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
final String ins = "', '";

public class DB {
	
	
	int nextReservationNumber = 1;
	
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
			if(rs.next())//see if Users are in rs - cursur starts before first row
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
		String query = "INSERT INTO FLIGHT (FLIGHT_NUM, DEPARTURE_CITY, DEPARTURE_TIME, ARRIVAL_CITY, ARRIVAL_TIME, CAPACITY) VALUE" +
						"('" + flight.getFlightNumber() + ins + flight.getDepartureCity() + ins + flight.getDepartureTime() + ins + flight.getArrivalCity() + 
						ins + flight.getArrivalTime() + ins + flight.getCapacity() + "');";
		try {
			Driver.executeInsert(query);
		} catch(Exception e) {
        		System.err.println("Exception while inserting flight"+e);
		}
	}
	
	private Flight createFlight(ResultSet rs) throws SQLException {
		Flight flight = null;
		flight = new Flight(rs.getString("DEPARTURE_CITY"), rs.getString("ARRIVAL_CITY"), rs.getTimestamp("DEPARTURE_TIME").getTime(), 
					rs.getTimestamp("ARRIVAL_TIME").getTime(), rs.getInt("FLIGHT_NUM"), rs.getInt("CAPACITY"));//creates flight from database
		
		return flight;
	}
	
	
	
	
	public  Flight getFlightByDepartureCityAndDateAndNumber(String departureCity, Date departureDate, int flightNumber){//same logic when getting User from database
		Flight flight = null;//have to create flight outside of try
				   
		String query = "SELECT FLIGHT_NUM, DEPARTURE_CITY, DEPARTURE_TIME, ARRIVAL_CITY, ARRIVAL_TIME, CAPACITY from FLIGHT WHERE DEPARTURE_CITY = '" + 
						departureCity + "' AND " + "DEPARTURE_TIME = '" + departureDate + "' AND " + "FLIGHT_NUM = '" + flightNumber + "'";
		try{
			ResultSet rs = Driver.executeQuery(query);
			if(rs.next())
				flight = createFlight(rs);
			rs.close();
		}catch(SQLException e) {
			System.err.println("Exception while getting Flight " + e);
		}
		return flight;
	}
	
	public Flight[] getFlights(String query){//used in next 4 methods to get array of Flights based on query
		ArrayList<Flight> flights = new ArrayList<Flight>();
		try{
			ResultSet rs = Driver.executeQuery(query);
			while(rs.next())//gets all flights in rs including 1st
				flights.add(createFlight(rs));//creates flight and adds to ArrayList
			rs.close();
		}catch(SQLException e) {
			System.err.println("Exception while getting Flight " + e);
		}
		return flights.toArray(new Flight[0]);//returns an array of the flights which were in the ArrayList, instead of making whole new array 
	}
	
	public Flight[] getFlightByNumberAndDepartureDate(int flightNumber, Date departureDate){//next 4 methods returns array b/c more than 1 flight can have same characteristics
		
		String query = "SELECT FLIGHT_NUM, DEPARTURE_CITY, DEPARTURE_TIME, ARRIVAL_CITY, ARRIVAL_TIME, CAPACITY from FLIGHT WHERE FLIGHT_NUM = '" + 
						flightNumber + "' AND " + "DEPARTURE_TIME = '" + departureDate + "'";
		return getFlights(query);
	}
	
	public Flight[] getFlightByDepartureCityAndDate(String departureCity, Date departureDate){
		
		String query = "SELECT FLIGHT_NUM, DEPARTURE_CITY, DEPARTURE_TIME, ARRIVAL_CITY, ARRIVAL_TIME, CAPACITY from FLIGHT WHERE DEPARTURE_CITY = '" + 
						departureCity + "' AND " + "DEPARTURE_TIME = '" + departureDate + "'";
		return getFlights(query);
	}
	
	public  Flight[] getFlightByDepartureCityAndArrivalCity(String departureCity, String arrivalCity){
		String query = "SELECT FLIGHT_NUM, DEPARTURE_CITY, DEPARTURE_TIME, ARRIVAL_CITY, ARRIVAL_TIME, CAPACITY from FLIGHT WHERE DEPARTURE_CITY = '" + 
						departureCity + "' AND " + "ARRIVAL_CITY = '" + arrivalCity + "'";
		return getFlights(query);
	}
	public  Flight[] getAllFlights() {
		String query = "SELECT FLIGHT_NUM, DEPARTURE_CITY, DEPARTURE_TIME, ARRIVAL_CITY, ARRIVAL_TIME, CAPACITY from FLIGHT";
		return getFlights(query);
	}
	
	public void update(Flight flight){//like in update User - the flight is passed in with updated - same logicc
		String query = "UPDATE FLIGHT SET DEPARTURE_CITY = '" + flight.getDepartureCity() + "', DEPARTURE_TIME = '" + flight.getDepartureTime() + "', ARRIVAL_CITY = '" + 
						flight.getArrivalCity() + "', ARRIVAL_TIME = '" + flight.getArrivalTime() + "', CAPACITY = '" + flight.getCapacity() + "' WHERE FLIGHT_NUM = '" + 
						flight.getFlightNumber() + "'";
		try{
			Driver.executeUpdate(query);
		}catch(Exception e) {
			System.err.println("Exception while updating flight" + e);
		}
	}
	
	public void delete(Flight flight){
		String query = "DELETE FROM FLIGHT WHERE DEPARTURE_CITY = '" + 
						flight.getDepartureCity() + "' AND " + "DEPARTURE_TIME = '" + flight.getDepartureTime() + "' AND " + "FLIGHT_NUM = '" + flight.getFlightNumber() + "';";
		try{
			Driver.executeDelete(query);
			remove(flight);
		}catch(Exception e) {
			System.err.println("Exception while deleting Flight" + e);
		}
	}


	/*
	** Reservation CRUD operations - same logic as in flight 
	*/
	public void create(Reservation reservation){//create Reservation in database
		String depDt = datetimeFormat.format(reservation.getFlight().getDepartureTime())+":00";
		
		String query = "INSERT INTO RESERVATION (RESERVATION_NUM, USERNAME, FLIGHT_NUM, DEPARTURE_CITY, DEPARTURE_TIME) VALUE" + "('" + reservation.getReservationNumber() + ins + 
						reservation.getCustomer().getUsername() + ins + reservation.getFlight().getFlightNumber() + ins + reservation.getFlight().getDepartureCity() + ins + 
						depDt +  "')";
        // Need to return reservation number
		String getQuery = "SELECT RESERVATION_NUM FROM RESERVATION WHERE USERNAME = '" + reservation.getCustomer().getUsername() + "' AND FLIGHT_NUM = "  + reservation.getFlight().getFlightNumber() + " AND DEPARTURE_CITY = '" +
			reservation.getFlight().getDepartureCity() + "' AND DEPARTURE_TIME = '" + depDt + "'";

		try {
			Driver.executeInsert(query);
			ResultSet rs = Driver.executeQuery(getQuery);
			if(rs.next()) {
				int resNum = rs.getInt("RESERVATION_NUM");
				reservation.reservationNumber = resNum;
			}
			rs.close();
		} catch(Exception e) {
        	System.err.println("Exception while inserting flight"+e);
		}
	}
	
	public Reservation createReservation(ResultSet rs) throws SQLException {//create Reservation from query 
		Reservation reservation = null;
		User user = DB.getInstance().getUserByID(rs.getString("USERNAME"));

		Date depDt = new Date(rs.getTimestamp("DEPARTURE_TIME").getTime());
		Flight flight = DB.getInstance().getFlightByDepartureCityAndDateAndNumber(rs.getString("DEPARTURE_CITY"), depDt, rs.getInt("FLIGHT_NUM"));
		flight.reservations.add(reservation);
        user.addBookedFlight(reservation);
		reservation = new Reservation(rs.getInt("RESERVATION_NUM"), (Customer)user , flight);
		add(reservation);
		return reservation;
	}
	
	public  Reservation getReservationByNumber(int reservationNumber){
		Reservation reservation = null;
		String query = "SELECT RESERVATION_NUM, USERNAME, FLIGHT_NUM, DEPARTURE_CITY, DEPARTURE_TIME from RESERVATION WHERE RESERVATION_NUM = '" + reservationNumber + "'";
		try{
			ResultSet rs = Driver.executeQuery(query);
			reservation = createReservation(rs);
			rs.close();
		}catch(SQLException e) {
			System.err.println("Exception while getting Reservation " + e);
		}
		return reservation;
	}
	
	public Reservation[] getReservations(String query) {
		ArrayList<Reservation> reservations = new ArrayList<Reservation>();
		try{
			ResultSet rs = Driver.executeQuery(query);
			while(rs.next())
				reservations.add(createReservation(rs));
			rs.close();
		}catch(SQLException e) {
			System.err.println("Exception while getting Reservations " + e);
		}
		return reservations.toArray(new Reservation[0]);
	}
	
	public  Reservation[] getReservationsByCustomer(User user){//Customer may have more than 1 Reservation - flight booking
		String query = "SELECT RESERVATION_NUM, USERNAME, FLIGHT_NUM, DEPARTURE_CITY, DEPARTURE_TIME from RESERVATION WHERE USERNAME = '" + user.getUsername() + "'"; 
		return getReservations(query);
	}
	
	
	public  Reservation[] getReservationsByFlight(Flight flight){

		String depDt = datetimeFormat.format(flight.getDepartureTime())+":00";
		
		String query = "SELECT RESERVATION_NUM, USERNAME, FLIGHT_NUM, DEPARTURE_CITY, DEPARTURE_TIME from RESERVATION WHERE DEPARTURE_CITY = '" 
				+ flight.getDepartureCity() + "' AND " + "DEPARTURE_TIME = '" + depDt + "' AND " + "FLIGHT_NUM = '" + flight.getFlightNumber() + "'";
		return getReservations(query);
	}
	
	public void update(Reservation reservation){
		String query = "UPDATE RESERVATION SET USERNAME = '" + reservation.getCustomer().getUsername() + "', FLIGHT_NUM = '" + reservation.getFlight().getFlightNumber() + "' WHERE RESERVATION_NUM = '" + reservation.getReservationNumber() + "'";
		try{
			Driver.executeUpdate(query);
		}catch(Exception e) {
			System.err.println("Exception while updating reservation" + e);
		}
	}
	
	public void delete(Reservation reservation){
		String query = "DELETE FROM RESERVATION WHERE RESERVATION_NUM = '" + reservation.getReservationNumber() + "';";
		try{
			Driver.executeDelete(query);
		}catch(Exception e) {
			System.err.println("Exception while deleting reservation" + e);
		}
	}
	
	
	
	
	
	
	
	
}
