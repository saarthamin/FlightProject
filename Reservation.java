package CSKAirlines;

public class Reservation {
	protected User customer;
	protected Flight flight;
	protected long reservationNumber;  // Unique reservation number across the system
	
	public Reservation(long reservationNumber, User customer, Flight flight){
		this.reservationNumber = reservationNumber;
		this.customer = customer;
		this.flight = flight;
	}
	
	
	public User getCustomer() {
		return customer;
	}
	public void setCustomer(User customer) {
		this.customer = customer;
	}
	public Flight getFlight() {
		return flight;
	}
	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public long getReservationNumber() {
		return reservationNumber;
	}
	public void setReservationNumber(long reservationNumber) {
		this.reservationNumber = reservationNumber;
	}
  
  //methods to get from DB
	public static Reservation[] getReservationsByCustomer(User customer){
	}
	
  public static Reservation getReservationByNumber(long reservationNumber){
	}
	
	public static Reservation[] getReservationsByFlight(Flight flight){
	}
    
	public boolean isEqual(Reservation other){
		if(this.reservationNumber == other.reservationNumber && this.customer.isEqual(other.customer) && this.flight.equals(other.flight))
			return true;
		else
			return false;
	}
	
	public String toString(){
		return "Reservation[ "+reservationNumber + " " 
				+ (this.customer == null ? " Customer[null] " : this.customer)  
				+ (this.flight == null ? " Flight[null] " : this.flight) +
				" ]";

	}
}
