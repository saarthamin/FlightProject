package CSKAirlines;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Flight implements Persistable {
	protected String departureCity;
	protected String arrivalCity;
	protected Date departureTime;  // local date time
	protected Date arrivalTime;    // local date time
	protected int flightNumber;
	
	protected int capacity;
	ArrayList<Reservation> reservations = new ArrayList<Reservation>();

	protected boolean _isDirty = false;
	
	public Flight(String departureCity, String arrivalCity, Date departureTime,
			Date arrivalTime, int flightNumber, int capacity){
		this.departureCity = departureCity;
		this.arrivalCity = arrivalCity;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.flightNumber = flightNumber;
		this.capacity = capacity;
	}
	public boolean isDirty() {
		return _isDirty;
	}

	public void save() {//after updates are made - you must call this method to save to database 
		if(_isDirty)
			DB.getInstance().update(this);
		_isDirty = false;
	}

	/*
	** search flights
	*/
	
	public static Flight[] getAllFlights() {
		return DB.getInstance().getAllFlights();
	}
	public static Flight getFlightByDepartureCityAndDateAndNumber(String departureCity, Date departureDate, int flightNumber){
		return DB.getInstance().getFlightByDepartureCityAndDateAndNumber(departureCity, departureDate, flightNumber);
	}
	
	public static Flight[] getFlightByNumberAndDepartureDate(int flightNumber, Date departureDate){
		return DB.getInstance().getFlightByNumberAndDepartureDate(flightNumber, departureDate);
	}
	
	public static Flight[] getFlightByDepartureCityAndDate(String departureCity, Date departureDate){
		return DB.getInstance().getFlightByDepartureCityAndDate(departureCity, departureDate);
	}
	
	public static Flight[] getFlightByDepartureCityAndArrivalCity(String departureCity, String arrivalCity){
		return DB.getInstance().getFlightByDepartureCityAndArrivalCity(departureCity, arrivalCity);
	}

	public boolean equals(Flight other) {
		if(
			this.departureCity.equalsIgnoreCase(other.departureCity) &&
			this.arrivalCity.equalsIgnoreCase(other.arrivalCity) &&
			this.departureTime.equals(other.departureTime) &&
			this.arrivalTime.equals(other.arrivalTime) &&
			this.flightNumber == other.flightNumber &&
			this.capacity == other.capacity
		)
			return true;
		return false;
	}
	
	@Override
	public String toString() {
		return "Flight[" + flightNumber + ", " + departureCity + ", " + arrivalCity + ", " + departureTime + ", " + arrivalTime + ", " + capacity + "]";
	}

	/*
	** getter and setters
	*/

	public String getDepartureCity() {
		return departureCity;
	}
	public void setDepartureCity(String departureCity) {
		this.departureCity = departureCity;
		_isDirty = true;
	}
	public String getArrivalCity() {
		return arrivalCity;
	}
	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
		_isDirty = true;
	}
	public Date getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
		_isDirty = true;
	}
	public Date getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
		_isDirty = true;
	}
	public int getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(int flightNumber) {
		this.flightNumber = flightNumber;
		_isDirty = true;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
		_isDirty = true;
	}
	public int getBookingCount() {
		return DB.getInstance().getBookingCount(this);
	}
	public ArrayList<Reservation> getReservations() {
		return reservations;
	}
	public void setReservations(ArrayList<Reservation> reservations) {
		this.reservations = reservations;
		_isDirty = true;
	}
}
