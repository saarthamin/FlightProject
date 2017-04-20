public class Flight implements Persistable {
	protected String departureCity;
	protected String arrivalCity;
	protected Date departureTime;  // local date time
	protected Date arrivalTime;    // local date time
	protected int flightNumber;
	
	protected int capacity; // if we have more than one type of flight
	
	
	public Flight(String departureCity, String arrivalCity, Date departureTime,
			Date arrivalTime, int flightNumber, int capacity){
		this.departureCity = departureCity;
		this.arrivalCity = arrivalCity;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.flightNumber = flightNumber;
		this.capacity = capacity;
	}

	/*
	** Need to write search flights methods
	*/
	

	public boolean isEqual(Flight other) {
		if(
			this.departureCity == other.departureCity &&
			this.arrivalCity == other.arrivalCity &&
			this.departureTime.equals(other.departureTime) &&
			this.arrivalTime.equals(other.arrivalTime) &&
			this.flightNumber == other.flightNumber &&
			this.capacity == other.capacity
		)
			return true;
		return false;
	}

	/*
	** getter and setters
	*/
	
	public String getDepartureCity() {
		return departureCity;
	}
	public void setDepartureCity(String departureCity) {
		this.departureCity = departureCity;
	}
	public String getArrivalCity() {
		return arrivalCity;
	}
	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}
	public Date getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}
	public Date getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public int getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(int flightNumber) {
		this.flightNumber = flightNumber;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
}
