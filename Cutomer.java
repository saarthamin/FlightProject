package one;

import java.util.Date;

public class Customer extends User{
	
	public Customer(String firstName, String lastName, String address, int zipCode, String state,
			String username, String password, String email, long SSN, String id){
		super(firstName, lastName, address, zipCode, state, username, password, email, SSN, id);
	}
  
  @Override
  public Flight[] searchFlightByCityPair(String departureCity, String arrivalCity){
  }
	
  @Override
	public Flight[] searchFlightByDepartureCityAndDepartureDate(String departureCity, Date date){
  }
	
  @Override
	public Reservation bookFlight(Flight flight){
  }
	
  @Override
	public boolean cancelReservation(Reservation reservation) throws Exception{
  }
  
}
