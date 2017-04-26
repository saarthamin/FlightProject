
package application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Welcome extends Application {

	 @Override
	    public void start(Stage stage) throws Exception {
	       Parent root = FXMLLoader.load(getClass().getResource("/view/welcome.fxml"));
	    
	        Scene scene = new Scene(root, 300, 275);
	    
	        stage.setTitle("CSKAirlines");
	        stage.setScene(scene);
	        stage.show();
	    }

	public static void main(String[] args) {
		launch(args);
	}
}
