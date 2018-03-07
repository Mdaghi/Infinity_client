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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dash.fxml"));
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
    public static void main(String[] args) throws NamingException {
		/*Properties jndiProps = new Properties(); //l
		jndiProps.put(Context.PROVIDER_URL,"http-remoting://localhost:8080");
		String jndiName="infinity_server-ear/infinity_server-ejb/ServiceNewsSource!tn.esprit.infinity_server.interfaces.NewsSourceRemote";
		Context context=new InitialContext(jndiProps);
		NewsSourceRemote proxy=(NewsSourceRemote)context.lookup(jndiName);
		NewsSource ns = new NewsSource();
		ns.setDescription("aze");
		ns.setUrl("aze");
		ns.setImage("aze");
		proxy.addNewsSource(ns);
		List<NewsSource> s = proxy.getAllNewsSource();
		System.out.println("result: ");
		System.out.println(s);*/
    	launch(args);
    	System.out.println("wowe");
    }

}
