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
		
      
		
	}
	
	private User createUser(ResultSet rs) throws SQLException {
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
		
	}
	
	public void update(User user) {//user is passed already updated
	
	}
	
	public  void delete(User user) {
	
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
