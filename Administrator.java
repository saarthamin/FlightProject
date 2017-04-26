package CSKAirlines;

import java.util.Date;

public class Administrator extends Customer {

public Administrator(String firstName, String lastName, String address, String zipCode, String state,
			String username, String password, String email, String SSN){
		super(firstName, lastName, address, zipCode, state, username, password, email, SSN);
	}	
	public Flight createFlight(String departureCity, String arrivalCity, Date departureTime,
			Date arrivalTime, int flightNumber, int capacity) throws Exception {
		Flight flight = new Flight(departureCity, arrivalCity, departureTime, arrivalTime, flightNumber, capacity);
		
		//check if flight already exists 
		Flight[] flightArray = DB.getInstance().getAllFlights();
		for(Flight f: flightArray){
			if(flight.equals(f))
				throw new Exception("Flight already exists");
			else continue;
		}
		DB.getInstance().create(flight);
		return flight;
	}
	
	public void updateDepartureCity(Flight flight, String departureCity){
		flight.setDepartureCity(departureCity);
		DB.getInstance().update(flight);
	}
	
	public void updateArrivalCity(Flight flight, String arrivalCity){
		flight.setArrivalCity(arrivalCity);
		DB.getInstance().update(flight);
	}
	
	public void updateDepartureTime(Flight flight, Date departureTime){
		flight.setDepartureTime(departureTime);
		DB.getInstance().update(flight);
	}
	
	public void updateArrivalTime(Flight flight, Date arrivalTime){
		flight.setArrivalTime(arrivalTime);
		DB.getInstance().update(flight);
	}
	
	public void updateFlightNumber(Flight flight, int flightNumber){
		flight.setFlightNumber(flightNumber);
		DB.getInstance().update(flight);
	}
	
	public void updateCapacity(Flight flight, int capacity){
		flight.setCapacity(capacity);
		DB.getInstance().update(flight);
	}
		
	public boolean deleteFlight(Flight flight) throws Exception {
		// should check if we have reservations
		int count = flight.getBookingCount();
		if(count != 0) {
			throw new Exception("There are "+count+" reservations on this flight. You may want to remove them first");
		}
		
		DB.getInstance().delete(flight);
		return true;
	}	
}
