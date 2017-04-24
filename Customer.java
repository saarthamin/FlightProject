package CSKAirlines;

import java.util.Date;

public class Customer extends User{
	public Customer(String firstName, String lastName, String address, String zipCode, String state,
			String username, String password, String email, String SSN){
		super(firstName, lastName, address, zipCode, state, username, password, email, SSN);
	}
	@Override
	public Flight[] searchFlightByCityPair(String departureCity, String arrivalCity) {
		r
	}
	@Override
	public Flight[] searchFlightByDepartureCityAndDepartureDate(String departureCity, Date date) {
		
	}

	@Override
	public Reservation bookFlight(Flight flight) {
		
	}
	@Override
	public boolean cancelReservation(Reservation reservation) throws Exception {
		
	}
}
