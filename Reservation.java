public class Reservation implements Persistable{
	protected Customer customer;
	protected Flight flight;
	protected boolean _isDirty = false;
	protected long reservationNumber;  // Unique reservation number across the system
	
	public Reservation(long reservationNumber, Customer customer, Flight flight){
		this.reservationNumber = reservationNumber;
		this.customer = customer;
		this.flight = flight;
	}
	
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
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
  
  //get Reservation methods will get from DB
	public static Reservation[] getReservationsByCustomer(Customer customer){
	
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
}
