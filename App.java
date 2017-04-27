package UI;
import java.util.HashMap;

import java.util.Map;
import java.util.Date;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import CSKAirlines.*;
// this class creates Menus, forms, and stiches them together. Presenting Menus to user.
public class App {

    final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    final static DateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
 // creating collection of Menu and Form because HashMap allows us to go back back and fetch Menus or forms
    static Map<String, Menu> menus = new HashMap<String, Menu>();
    static Map<String, Form> forms = new HashMap<String, Form>();
    /*
    ** These are current selection
    */
    static Menu currentMenu = null;// because no menu is assigned
    static public User user = null;// because no one is logged in
    static public Flight flight = null;// because no flight is selected
    static public Reservation reservation = null;// because no reservation is selected


// sets current menu
    public static void setCurrentMenu(String menu) {
        if(menus.get(menu) == null) {
            Exception e = new Exception("Menu not found : "+menu);
            e.printStackTrace();
            DB.getInstance().close();
            System.exit(1);
        }
        currentMenu = menus.get(menu);
    }
    public static void main(String args[]) {
        createMenus();
        createFormLogin();
        createFormRegisterUser();
        createFormBookBy_DC_AC();
        createFormBookBy_FN_DC_DD();
        createFormBookBy_All();
        createFormCreateFlight();
        createFormDeleteFlight();
        createFormCancelReservation();
        createFormEditFlight();
        createFormForgotPassword();
        setCurrentMenu("root");

        // load data
        //DBLoader.load();

        while(true) {
            currentMenu.run();
        }
    }
    static void createMenus() {
        MenuAction voidAction = new MenuAction() { public void execute(String option) { } };
        // excutes when user selects login
        MenuAction loginAction = new MenuAction() {
            public void execute(String option) {
            	// show login form and ask user to login
                if(forms.get("login").display()) {
                // user successfully logged in 
                    if(App.user instanceof Administrator)
                        setCurrentMenu("MainAdmin");
                    else
                        setCurrentMenu("Main");
                } else
                	// user is unable to login to the root menu
                    setCurrentMenu("root");
            }
        };
         MenuAction registerAction = new MenuAction() {
            public void execute(String option) {
                forms.get("register").display();
                setCurrentMenu("root");
            }
         };
         MenuAction quitAction = new MenuAction() {
            public void execute(String option) {
                System.out.println("Thank you for using CSKAirlines");
                DB.getInstance().close();
                System.exit(0);
            }
         };
         MenuAction forgotPasswordAction = new MenuAction() {
             public void execute(String option) {
            	 	forms.get("forgotPassword").display();
            	 	setCurrentMenu("root");
             }
          };
        menus.put("root", 
            new Menu("Welcome to CSKAirlines booking App")
            .add("l", "Login", loginAction)
            .add("r", "Register new User", registerAction)
            .add("f", "Forgot my password", forgotPasswordAction)
            .add("q", "Quit", quitAction));
        
        MenuAction mainMenuAction = new MenuAction() { public void execute(String option) { 
                    if(App.user instanceof Administrator)
                        setCurrentMenu("MainAdmin");
                    else
                        setCurrentMenu("Main");
        }};
            
         MenuAction bookFlightAction = new MenuAction() { public void execute(String option) { setCurrentMenu("bookFlight"); } };
         MenuAction logoutAction = new MenuAction() { public void execute(String option) { App.user = null; setCurrentMenu("root");} };
         MenuAction createFlightAction = new MenuAction() { public void execute(String option) { forms.get("createFlight").display(); } };
         MenuAction deleteFlightAction = new MenuAction() { public void execute(String option) { forms.get("deleteFlight").display(); } };
         MenuAction cancelReservationAction = new MenuAction() { public void execute(String option) { forms.get("cancelReservation").display(); } };
         MenuAction editFlightAction = new MenuAction() { public void execute(String option) { forms.get("editFlight").display(); } };
          
   // customer main menu
        menus.put("Main", 
            new Menu("Main Menu")
            .add("b", "Book Flight", bookFlightAction)
            .add("c", "Cancel Reservation", cancelReservationAction)
            .add("l", "Log out", logoutAction));
       // Administrative privileges
        menus.put("MainAdmin", 
            new Menu("Main Menu")
            .add("f", "Create Flight", createFlightAction)
            .add("d", "Delete Flight", deleteFlightAction)
            .add("e", "Edit Flight",  editFlightAction)
            .add("b", "Book Flight", bookFlightAction)
            .add("c", "Cancel Reservation", cancelReservationAction)
            .add("l", "Log out", logoutAction));
        
        MenuAction bookBy_FN_DC_DD_Action = new MenuAction() { 
            public void execute(String option) {
                forms.get("bookBy_FN_DC_DD").display();
                setCurrentMenu("bookFlight");
             }};

        MenuAction bookBy_DC_AC_Action = new MenuAction() { 
            public void execute(String option) {
                forms.get("bookBy_DC_AC").display();
                setCurrentMenu("bookFlight");
             }};
        MenuAction bookBy_All_Action = new MenuAction() { 
            public void execute(String option) {
                forms.get("bookBy_All").display();
                setCurrentMenu("bookFlight");
             }};
        menus.put("bookFlight",
            new Menu("Book Flight - Search by")
            .add("1", "Flight Number, Departure City and Date", bookBy_FN_DC_DD_Action)
            .add("2", "City Pair", bookBy_DC_AC_Action)
            .add("3", "Departure City, Arrival City and Date", voidAction)
            .add("4", "Departure City and Date", voidAction)
            .add("a", "All flights", bookBy_All_Action)
            .add("m", "Main Menu", mainMenuAction));
        
    }
    /*
    ** Login user
    */
    static void createFormLogin() {
       FormAction loginAction = new FormAction() {
         public boolean submit(Form f) { 
               // validate user id and password
            String username = f.getFieldValue("username"),
                   password = f.getFieldValue("password");
            User user = User.getUserByID(username);
            if(user == null || !user.getPassword().equals(password)) {
                System.out.println("User or password does not match");
                return false;
            }
            App.user = user;
            // set global logged in user
            return true;
         }
         public boolean cancel(Form f) {
             return false;
         }
       };
       Form loginForm = new Form("Login", loginAction)
            .addField("username","User Name")
            .addField("password","Password");
       forms.put("login",loginForm);
    }
    /*
    ** Register user
    */
    static void createFormRegisterUser() {
       FormAction action = new FormAction() {
         public boolean submit(Form f) { 
            User user;
            String type;
            if(f.getFieldValue("isadmin").toUpperCase().charAt(0) == 'Y') {
                user = new Administrator(
                            f.getFieldValue("firstName"),
                            f.getFieldValue("lastName"),
                            f.getFieldValue("address"),
                            f.getFieldValue("zipCode"),
                            f.getFieldValue("state"),
                            f.getFieldValue("username"),
                            f.getFieldValue("password"),
                            f.getFieldValue("email"),
                            f.getFieldValue("SSN"),
                            f.getFieldValue("securityQuestion"),
                            f.getFieldValue("securityAnswer")
                );
                type = "Adminstrator";
            } else {
                user = new Customer(
                            f.getFieldValue("firstName"),
                            f.getFieldValue("lastName"),
                            f.getFieldValue("address"),
                            f.getFieldValue("zipCode"),
                            f.getFieldValue("state"),
                            f.getFieldValue("username"),
                            f.getFieldValue("password"),
                            f.getFieldValue("email"),
                            f.getFieldValue("SSN"),
                            f.getFieldValue("securityQuestion"),
                            f.getFieldValue("securityAnswer")
                );
                type = "Customer";
            }
            User.register(user);
            System.out.println("User is registred as "+type);
            return true;
         }
         public boolean cancel(Form f) {
             return false;
         }
       };
       FieldValidator usernameValidator = new FieldValidator() {
           public boolean validate(Field field, Form form) {
                if(User.isIDExists(field.value)) {
                    System.out.println("User id is already taken, please use different id");
                    return false;
                }
                return true;
           }
       };
       FieldValidator confirmPwd = new FieldValidator() {
           public boolean validate(Field field, Form form) {
                if(field.value.equals(form.getFieldValue("password")))
                        return true;
                System.out.println("Password does not match");
                return false;
           }
       };
       Form form = new Form("Register new user", action)
            .addField("username","User Name", usernameValidator)
            .addField("password","Password")
            .addField("password2","Confirm Password", confirmPwd)
            .addField("firstName","First Name")
            .addField("lastName","Last Name")
            .addField("address","Address")
            .addField("state","State")
            .addField("email","Email address")
            .addField("SSN","Social Security Number")
            .addField("securityQuestion","Security Question")
            .addField("securityAnswer","Answer to your security question")
            .addField("isadmin","Is user admin(y/n)");
       forms.put("register",form);
    }
    
    /*
     * Forgot Password
     */

    static void createFormForgotPassword() {

        FieldValidator usernameValidator = new FieldValidator() {
            public boolean validate(Field field, Form form) {
            		User user = User.getUserByID(field.value);
            		if(user == null) {
            			System.out.println("User does not exist with that id");
            			return false;
            		}
            		System.out.println("Your Security Question is: "+user.getSecurityQuestion());
                 return true;
            }
        };
       FormAction action = new FormAction() {
         public boolean submit(Form f) { 
               // validate user id and password
            String username = f.getFieldValue("username"),
                   securityAnswer = f.getFieldValue("securityAnswer");
            User user = User.getUserByID(username);
            if(user == null || !user.getSecurityQuestionAnswer().equals(securityAnswer)) {
                System.out.println("Answer does not match with, please call 1-800-CSKAIR1 for help");
                return false;
            }
            System.out.println("Your password is: "+user.getPassword());
            user = null;
            // set global logged in user
            return true;
         }
         public boolean cancel(Form f) {
             return false;
         }
       };
       Form form = new Form("Forgot Password", action)
            .addField("username","User Name",usernameValidator)
            .addField("securityAnswer","Answer to your security question");
       forms.put("forgotPassword",form);
    }
    /*
    ** Create Flight
    */
    static void createFormCreateFlight() {
       FormAction action = new FormAction() {
           public boolean submit(Form form) {
                Date depDt = parseDateTime(form.getFieldValue("departureTime")),
                    arrDt = parseDateTime(form.getFieldValue("arrivalTime"));
                try {
                    Flight flight = ((Administrator) App.user).createFlight(
                        form.getFieldValue("departureCity"),
                        form.getFieldValue("arrivalCity"),
                        depDt,
                        arrDt,
                        Integer.parseInt(form.getFieldValue("flightNumber")),
                        Integer.parseInt(form.getFieldValue("capacity"))
                    );
                } catch(Exception e) {
                    System.out.println("Flight already exist");
                    return false;
                }
                App.flight = flight;
                System.out.println("Flight created");
                return true;
            }
            public boolean cancel(Form form) {return false;}
        };
        Form form = new Form("Create Flight", action)
        .addField("flightNumber","Flight Number")
        .addField("departureCity","Departure City (3 char)")
        .addField("arrivalCity","Arrival City (3 char)")
        .addField("departureTime","Departure Date Time (YYYY-MM-dd HH:mm)")
        .addField("arrivalTime","Arrival Date Time (YYYY-MM-dd HH:mm)")
        .addField("capacity","Capacity");
      forms.put("createFlight",form);
    }

    /*
    ** Delete Flight
    */
   static void createFormDeleteFlight() {
       FormAction action = new FormAction() {
         public boolean submit(Form f) { 
            Flight[] flights = Flight.getAllFlights();
            if(flights.length == 0) {
                System.out.println("No flight found ");
                return false;
            }
            Flight flight = getFlightSelectionFromUser(flights);
            if(flight == null)
            		return false;
            try { 
                ( (Administrator) App.user).deleteFlight(flight);
            } catch(Exception e) {
                System.out.println(e);
                e.printStackTrace();
                return false;
            }
            return true;
        };
        public boolean cancel(Form f) { return false; }
       };
      
       Form form = new Form("Delete Flight - All flights will be searched ", action);
       forms.put("deleteFlight",form);
    }
    /*
     * Edit Flight
     */
   static Form getEditFlightForm(Flight flight) {
       FormAction action = new FormAction() {
           public boolean submit(Form form) {
                Date arrDt = parseDateTime(form.getFieldValue("arrivalTime")),
                		depDt = parseDateTime(form.getFieldValue("departureTime"));
                try {
                		flight.setFlightNumber(Integer.parseInt(form.getFieldValue("flightNumber")));
                		flight.setDepartureCity(form.getFieldValue("departureCity"));
                		flight.setArrivalCity(form.getFieldValue("arrivalCity"));
                		flight.setDepartureTime(depDt);
                		flight.setArrivalTime(arrDt);
                		flight.setCapacity(Integer.parseInt(form.getFieldValue("capacity")));
                		flight.save();
                } catch(Exception e) {
                		e.printStackTrace();
                    return false;
                }
                App.flight = null;
                System.out.println("Flight Updated");
                return true;
            }
            public boolean cancel(Form form) {return false;}
        };
        int bookings = flight.getBookingCount();
        Form form = new Form("Edit Flight - current bookings: "+bookings, action, true)
        .addField("flightNumber","Flight Number",""+flight.getFlightNumber())    // Note : Primary keys can not be modified - user delete and create
        .addField("departureCity","Departure City (3 char)",flight.getDepartureCity())
        .addField("arrivalCity","Arrival City (3 char)",flight.getArrivalCity())
        .addField("departureTime","Departure Date Time (YYYY-MM-dd HH:mm)", datetimeFormat.format(flight.getDepartureTime()))
        .addField("arrivalTime","Arrival Date Time (YYYY-MM-dd HH:mm)", datetimeFormat.format(flight.getArrivalTime()))
        .addField("capacity","Capacity", ""+flight.getCapacity());
        return form;
    }
   	static void createFormEditFlight() {
   		FormAction action = new FormAction() {
   			public boolean submit(Form f) {
   				Flight[] flights = Flight.getAllFlights();
   				if(flights.length == 0) {
   					System.out.println("No flight found");
   					return false;
   				}
   				Flight flight = getFlightSelectionFromUser(flights);
   				if(flight == null)
   					return false;
   				int count = flight.getBookingCount(); 
   				if(count > 0) {
   					System.out.println("Flight has "+count + " bookings. Please remove them to edit.");
   					return false;
   				}
   				Form editForm = getEditFlightForm(flight);
   				editForm.display();
   				return true;
   			};
   	        public boolean cancel(Form f) { return false; };
   		};
   		Form form = new Form("Edit Flight - All flights will be displayed to select ", action);
        forms.put("editFlight",form);
   	}

    /*
    ** Booking 
    */
    static Date parseDateTime(String datetime) {
        Date date = null;
        try {
            date = datetimeFormat.parse(datetime);
        } catch(Exception e) {
            System.err.println("Exception while parsing datetime "+datetime);
            e.printStackTrace();
            date = new Date();
        }
        return date;
    }
    static Date MMDDHHMM2Date(int MM, int DD, int HH, int mm) {
        Calendar now = Calendar.getInstance();
        String YYMMDDHHmm = 
                    MM < now.get(Calendar.MONTH) ? "" + (now.get(Calendar.YEAR) + 1) : ""+now.get(Calendar.YEAR)
                    + "-"
                    + (MM < 10 ? "0"+MM : ""+MM)
                    + "-"
                    + (DD < 10 ? "0"+DD : ""+DD)
                    + " "
                    + (HH < 10 ? "0"+HH : ""+HH)
                    + ":"
                    + (mm < 10 ? "0"+mm : ""+mm);
        Date date = null;
        try {
            date = datetimeFormat.parse(YYMMDDHHmm);
        } catch(Exception e) {
            e.printStackTrace();
            date = new Date();
        }
        return date;
    }
    static Date MMDD2Date(int MM, int DD) {
        Calendar now = Calendar.getInstance();
        String YYMMDD = 
                    MM < now.get(Calendar.MONTH) ? "" + (now.get(Calendar.YEAR) + 1) : ""+now.get(Calendar.YEAR)
                    + "-"
                    + (MM < 10 ? "0"+MM : ""+MM)
                    + "-"
                    + (DD < 10 ? "0"+DD : ""+DD);
        Date date = null;
        try {
            date = dateFormat.parse(YYMMDD);
        } catch(Exception e) {
            e.printStackTrace();
            date = new Date();
        }
        return date;
    }
    static Menu createMenuFlightSelection(Flight[] flights, MenuAction selectionAction) {
        Menu menu = new Menu("Select Flight");
        for(int i = 0; i < flights.length; i++) {
            menu.add(""+i, flights[i].toString(), selectionAction);
        }
        menu.add("c", "Cancel", selectionAction);
        return menu;
    }
    static Flight getFlightSelectionFromUser(Flight[] flights) {
        MenuAction selectionAction = new MenuAction() { public void execute(String option) {
        		if(option.equals("c")){
        			App.flight = null;
        			return;
        		}
            int i = Integer.parseInt(option);
            flight = flights[i];
        }};
        Menu m = createMenuFlightSelection(flights, selectionAction);
        m.run();
        return flight;
    }
    static FieldValidator monthValidator = new FieldValidator() {
           public boolean validate(Field field, Form form) {
               boolean valid = true;
               try {
                   int i = Integer.parseInt(field.value);
                   valid = !(i < 1 || i > 12);
               } catch(Exception e) {
                    valid = false;
               }
               if(!valid) 
                       System.out.println("Please valid value for month [1,12]");
               return valid;
           }
    };
    static FieldValidator dayValidator = new FieldValidator() {
           public boolean validate(Field field, Form form) {
               boolean valid = true;
               try {
                   int i = Integer.parseInt(field.value);
                   valid = !(i < 1 || i > 30);
               } catch(Exception e) {
                    valid = false;
               }
               if(!valid) 
                       System.out.println("Please valid value for day of month [1,31]");
               return valid;
           }
    };
    static void createFormBookBy_FN_DC_DD() {
       FormAction action = new FormAction() {
         public boolean submit(Form f) { 
            String FN = f.getFieldValue("flightNumber"),
                   DC = f.getFieldValue("departureCity"),
                   DM = f.getFieldValue("departureMonth"),
                   DD = f.getFieldValue("departureDay");
            int flightNumber = Integer.parseInt(FN);
            Date departureDate = MMDD2Date(Integer.parseInt(DM), Integer.parseInt(DD));
            Flight flight = user.getFlightByDepartureCityAndDateAndNumber(DC, departureDate, flightNumber);
            if(flight == null) {
                System.out.println("Flight not found for selected criteria");
                return false;
            }
            try {
        		user.bookFlight(flight);
        } catch(Exception e){
        		System.out.println(e);
        		return false;
        }
            System.out.println("Flight is booked");
            return true;
        };
        public boolean cancel(Form f) { return false; }
       };
      
       Form form = new Form("Search Criteria", action)
            .addField("flightNumber","Flight Number")
            .addField("departureCity","Departure City (3 Character code)")
            .addField("departureMonth","Departure Month", monthValidator)
            .addField("departureDay","Departure Day", dayValidator);
       forms.put("bookBy_FN_DC_DD",form);
    }

    static void createFormBookBy_DC_AC() {
       FormAction action = new FormAction() {
         public boolean submit(Form f) { 
            String 
                   DC = f.getFieldValue("departureCity"),
                   AC = f.getFieldValue("arrivalCity");
            Flight[] flights = user.searchFlightByCityPair(DC, AC);
            if(flights.length == 0) {
                System.out.println("No flight found for selected criteria");
                return false;
            }
            Flight flight = getFlightSelectionFromUser(flights);
            if(flight == null)
            		return false;
            try {
        		user.bookFlight(flight);
        } catch(Exception e){
        		System.out.println(e);
        		return false;
        }
            System.out.println("Flight is booked");
            return true;
        };
        public boolean cancel(Form f) { return false; }
       };
      
       Form form = new Form("Search Criteria", action)
            .addField("departureCity","Departure City (3 Character code)")
            .addField("arrivalCity","Arrival City (3 Character code)");
       forms.put("bookBy_DC_AC",form);
    }
    static void createFormBookBy_All() {
       FormAction action = new FormAction() {
         public boolean submit(Form f) { 
            Flight[] flights = Flight.getAllFlights();
            if(flights.length == 0) {
                System.out.println("No flight found for selected criteria");
                return false;
            }
            Flight flight = getFlightSelectionFromUser(flights);
            if(flight == null)
            		return false;
            try {
            		user.bookFlight(flight);
            } catch(Exception e){
            		System.out.println(e);
            		return false;
            }
            System.out.println("Flight is booked");
            return true;
        };
        public boolean cancel(Form f) { return false; }
       };
      
       Form form = new Form("Search All flights", action);
       forms.put("bookBy_All",form);
    }
    /*
    ** Cancel Reservation
    */
    static Menu createMenuReservationSelection(Reservation[] reservations, MenuAction selectionAction) {
        Menu menu = new Menu("Select Reservation");
        for(int i = 0; i < reservations.length; i++) {
            menu.add(""+i, reservations[i].toString(), selectionAction);
        }
        menu.add("c", "Cancel", selectionAction);
        return menu;
    }
    static Reservation getReservationSelectionFromUser(Reservation[] reservations) {
        MenuAction selectionAction = new MenuAction() { public void execute(String option) {

	    		if(option.equals("c")) {
	    			App.reservation = null;
	    			return;
	    		}
	    		int i = Integer.parseInt(option);
            App.reservation = reservations[i];
        }};
        Menu m = createMenuReservationSelection(reservations, selectionAction);
        m.run();
        return App.reservation;
    }
   static void createFormCancelReservation() {
       FormAction action = new FormAction() {
         public boolean submit(Form f) { 
            Reservation[] reservations = App.user.getBookedFlights();
            if(reservations.length == 0) {
                System.out.println("No reservation found ");
                return false;
            }
            Reservation reservation = getReservationSelectionFromUser(reservations);
            if(reservation == null)
            		return false;
            try { 
                App.user.cancelReservation(reservation);
                System.out.println("Your reservation is cancelled");
            } catch(Exception e) {
                System.out.println("Exception occured while cancelling reservation");
                e.printStackTrace();
                return false;
            }
            return true;
        };
        public boolean cancel(Form f) { return false; }
       };
       Form form = new Form("Cancel Reservation - All reservations will be searched ", action);
       forms.put("cancelReservation",form);
    }
}
