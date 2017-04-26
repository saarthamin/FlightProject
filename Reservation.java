package CSKAirlines;

public class Reservation implements Persistable{
	protected User customer;
	protected Flight flight;
	protected boolean _isDirty = false;//see if database needs to be updated - set to true when you are changing something - setter methods
	protected int reservationNumber; //Unique reservation number across the system - incremented in database 
	
	public Reservation(int reservationNumber, User customer, Flight flight){
		this.reservationNumber = reservationNumber;
		this.customer = customer;
		this.flight = flight;
	}
	
	public boolean isDirty() {
		return _isDirty;
	}

	public void save() {//after changes are made by the User have to call this method to update database
		if(_isDirty)
			DB.getInstance().update(this);
		_isDirty = false;
	}
	
	public User getCustomer() {
		return customer;
	}
	public void setCustomer(User customer) {
		this.customer = customer;
		_isDirty = true;
	}
	public Flight getFlight() {
		return flight;
	}
	public void setFlight(Flight flight) {
		this.flight = flight;
		_isDirty = true;
	}

	public int getReservationNumber() {
		return reservationNumber;
	}
	public void setReservationNumber(int reservationNumber) {
		this.reservationNumber = reservationNumber;
		_isDirty = true;
	}

	public static Reservation[] getReservationsByCustomer(User customer){
		return DB.getInstance().getReservationsByCustomer(customer);
	}
	public static Reservation getReservationByNumber(int reservationNumber){
		return DB.getInstance().getReservationByNumber(reservationNumber);
	}
	
	public static Reservation[] getReservationsByFlight(Flight flight){
		return DB.getInstance().getReservationsByFlight(flight);
	}
    
	public boolean isEqual(Reservation other){
		if(this.reservationNumber == other.reservationNumber && this.customer.isEqual(other.customer) && this.flight.equals(other.flight))
			return true;
		else
			return false;
	}
	
	public String toString(){
		return "Reservation[ "+ this.reservationNumber + " " 
				+ (this.customer == null ? " Customer[null] " : this.customer)  
				+ (this.flight == null ? " Flight[null] " : this.flight) +
				" ]";

	}
}
