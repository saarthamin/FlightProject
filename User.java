package CSKAirlines;

import java.util.ArrayList;
import java.util.Date;

public abstract class User {
	protected String firstName;
	protected String lastName;
	protected String customerID;  
	protected String address;
	protected int zipCode;
	protected String state;
	protected String username;
	protected String password;
	protected String email;
	protected long SSN;
	protected String securityQuestion;
	protected String securityQuestionAnswer;
	
	
	public User(){}
	
	public User(String firstName, String lastName, String address, int zipCode, String state,
			String username, String password, String email, long SSN, String id){
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.zipCode = zipCode;
		this.state = state;
		this.username = username;
		this.password = password;
		this.email = email;
		this.SSN = SSN;
		this.customerID = id;
	}

 
	//
	public void register(){
	}
	
	public void login(){
		
	}
	
	public void logout(){
		
	}
	
	/*
	** getters and setters
	*/
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getSSN() {
		return SSN;
	}

	public void setSSN(long sSN) {
		SSN = sSN;
	}

	public ArrayList<Reservation> getBookedFlights() {
		return bookedFlights;
	}

	public void setBookedFlights(ArrayList<Reservation> bookedFlights) {
		this.bookedFlights = bookedFlights;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setSecurityQuestion(String securityQuestion){
		this.securityQuestion = securityQuestion;
  }
	
	public String getSecurityQuestion(){
		return securityQuestion;
	}
	
	public void setSecurityQuestionAnswer(String securityQuestionAnswer){
		this.securityQuestionAnswer = securityQuestionAnswer;
	}
	
	public String getSecurityQuestionAnswer(){
		return securityQuestionAnswer;
	}
	
	public boolean checkSecurityQuestionAnswer(String answer){
		if(this.securityQuestionAnswer.equalsIgnoreCase(answer)){
			return true;
		}
		
		else
			return false;
	}
	
	
	public boolean isEqual(User other) {
		if(this.firstName.equals(other.firstName) &&
				this.lastName.equals(other.lastName) &&
				this.address.equals(other.address) &&
				this.zipCode == other.zipCode &&
				this.state.equals(other.state) &&
				this.username.equals(other.username) &&
				this.password.equals(other.password) &&
				this.email.equals(other.email) &&
				this.customerID.equals(other.customerID) &&
				this.SSN == other.SSN){
					return true;
		}
		else
			return false;
	}
	
	public String toString(){
		return this.getFirstName() + ins + this.getLastName() + ins + this.getAddress() + ins + this.getState() + ins + this.getZipCode() + ins
				+ this.getUsername() + ins + this.getEmail();
	}
    
	public abstract Flight[] searchFlightByCityPair(String departureCity, String arrivalCity);
	
	public abstract Flight[] searchFlightByDepartureCityAndDepartureDate(String departureCity, Date date);
	
	public abstract Reservation bookFlight(Flight flight);
	
	public abstract boolean cancelReservation(Reservation reservation) throws Exception;
	
}
