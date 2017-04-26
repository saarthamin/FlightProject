package CSKAirlines;

import java.util.Date;

public class Customer extends User{
	public Customer(String firstName, String lastName, String address, String zipCode, String state,
			String username, String password, String email, String SSN){
		super(firstName, lastName, address, zipCode, state, username, password, email, SSN);
	}
	@Override
	public Flight[] searchFlightByCityPair(String departureCity, String arrivalCity) {
		return DB.getInstance().getFlightByDepartureCityAndArrivalCity(departureCity, arrivalCity);
	}
	@Override
	public Flight[] searchFlightByDepartureCityAndDepartureDate(String departureCity, Date date) {
		return DB.getInstance().getFlightByDepartureCityAndDate(departureCity,date);
	}
	@Override
	public Flight getFlightByDepartureCityAndDateAndNumber(String departureCity, Date departureDate, int flightNumber){
		return DB.getInstance().getFlightByDepartureCityAndDateAndNumber(departureCity, departureDate, flightNumber);
	}
	
	
	public boolean isAllowed(Date depA, Date arrA, Date depB, Date arrB) {
		if(arrB.compareTo(depA) < 0 || depB.compareTo(arrA) > 0)//compares dates of current reservation and flight User wants to book
			return true;										//if arrival time is 
		return false;
	}
	public Reservation findOverlap(Reservation[] reservations, Flight flight) {
		for(Reservation r : reservations) {
			if(!isAllowed(
					r.getFlight().getDepartureTime(),
					r.getFlight().getArrivalTime(),
					flight.getDepartureTime(),
					flight.getArrivalTime()
					)) {
				return r;
			}
		}
		return null;
	}
	
	
	@Override
	public Reservation bookFlight(Flight flight) throws Exception {
        //reservation number is auto incremented in DB and set by create()
	
		// Check if flight has capacity
		int count = DB.getInstance().getBookingCount(flight);
		if(count >= flight.getCapacity()) {
			throw new Exception("Flight is full, you can not book it");
		}
		
		Reservation r = findOverlap(this.getBookedFlights(), flight);
			if(r != null)
				throw new Exception("Can not book flight because of overlap with " + r.toString()); 
			
		Reservation rNew = new Reservation(0, this, flight);
		DB.getInstance().create(rNew);
		this.bookedFlights.add(rNew);
		flight.reservations.add(rNew);
		return rNew;
	}
	@Override
	public boolean cancelReservation(Reservation reservation) throws Exception {
		for(int i = 0; i < this.bookedFlights.size(); i++){
			if(reservation.isEqual(this.bookedFlights.get(i))){
				DB.getInstance().delete(reservation);
				this.bookedFlights.remove(reservation);//remove from customer bookedFlights array list
				reservation.flight.reservations.remove(reservation);//remove from flight reservations array list
				return true;
			}
		}
		throw new Exception("Reservation does not exist");
	}
}
