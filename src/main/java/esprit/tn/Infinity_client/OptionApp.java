package esprit.tn.Infinity_client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;	


public class OptionApp extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		 Parent root = FXMLLoader.load(getClass().getResource("/fxml/view/OptionPut.fxml"));
	        Scene scene = new Scene(root);
	        stage.setTitle("JavaFX and Maven");
	        stage.setScene(scene);
	        stage.show(); 
		
	}

}
