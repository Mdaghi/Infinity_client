package esprit.tn.Infinity_client;

import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.infinity_server.interfaces.NewsArticleRemote;
import tn.esprit.infinity_server.interfaces.NewsSourceRemote;
import tn.esprit.infinity_server.persistence.*;

public class MainAppDainer extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/test.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        stage.show();	
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/view/dash.fxml"));
        loader.setRoot(root);
        loader.load();

    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     * @throws NamingException 
     */
    public static void main(String args[]) throws NamingException {
    	launch(args);
    }

}
