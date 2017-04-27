package control;

import java.sql.PreparedStatement;

import CSKAirlines.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.RegisteredUser;

public class LoginController {
	
	 @FXML private Text actiontarget;
	 @FXML private TextField userName;
	 @FXML private TextField password;
	    @FXML protected void validateUser(ActionEvent event) {
	    	System.out.println("In Login Controllervalidate User()");
	    	//User user = new User();
	    	// TODO logic here to verify user
	    	RegisteredUser user = new RegisteredUser();
	    	user.setUsername(userName.getText());
	    	user.setPassword(password.getText());
	    	//boolean isValidUser = DB.getInstance().isValidUser(userName.getText(),password.getText() );
	    	boolean isValidUser = true;
	    	if(isValidUser){
	  //  		System.out.println("In if block");
	    		 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/welcome.fxml"));
	    		 try{
	             Parent root = (Parent) fxmlLoader.load();
	             
	             /*Stage stage = new Stage();
	             stage.initModality(Modality.APPLICATION_MODAL);
	             stage.initStyle(StageStyle.UNDECORATED);
	             stage.setTitle("Register User");
	             stage.setScene(new Scene(root));  
	             stage.show();*/
	             
	             Scene scene = new Scene(root, 400, 300);
	             Stage stage = new Stage();
	    	        stage.setTitle("Flight Project  Login");
	    	        stage.setScene(scene);
	    	        stage.show();
	    		 }catch(Exception e){
	    			 System.out.println("Exception in forwarding to register page : "+ e.getMessage());
	    		 }
	    		
	    	}
	    	
	    	
	    	actiontarget.setText("Sign in button pressed");
	    }
	    
	 @FXML  protected void  registerNewUser(ActionEvent event){
		 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/registerUser.fxml"));
		 try{
         Parent root = (Parent) fxmlLoader.load();
         
         /*Stage stage = new Stage();
         stage.initModality(Modality.APPLICATION_MODAL);
         stage.initStyle(StageStyle.UNDECORATED);
         stage.setTitle("Register User");
         stage.setScene(new Scene(root));  
         stage.show();*/
         
         Scene scene = new Scene(root, 600, 875);
         Stage stage = new Stage();
	        stage.setTitle("Flight Project  Login");
	        stage.setScene(scene);
	        stage.show();
		 }catch(Exception e){
			 System.out.println("Exception in forwarding to register page : "+ e.getMessage());
		 }
		 
	 }
	 
	 

}
