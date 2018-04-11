package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.mail.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import Util.WatchListTechnical;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import tn.esprit.infinity_server.interfaces.WatchListRemote;
import tn.esprit.infinity_server.persistence.WatchList;

public class WatchListDetailsController implements Initializable{
	 @FXML
	    private GridPane telaCadastro;

	    @FXML
	    private Button btUpdate;

	    @FXML
	    private Label legenda;

	    @FXML
	    private Label lbId;

	    @FXML
	    private TextField txtName;

	    @FXML
	    private TextArea txtDescription;

	    @FXML
	    private ToggleGroup menu;

	    @FXML
	    private Label lbTitle;

	    @FXML
	    void telaCadastro(ActionEvent event) {

	    }

	    @FXML
	    void telaEdicao(ActionEvent event) {

	    }

	    @FXML
	    void telaExcluir(ActionEvent event) {

	    }

	    @FXML
	    void update(ActionEvent event) throws IOException {
	    	System.out.println("hahahaha");
	    	
	    	String jndiName="infinity_server-ear/infinity_server-ejb/WatchListService!tn.esprit.infinity_server.interfaces.WatchListRemote";
	    	Context context;
	    	
				try {
					context = new InitialContext();
					WatchListRemote proxy2=(WatchListRemote)context.lookup(jndiName);	
					WatchList watchList=new WatchList();
					watchList.setId(Integer.parseInt(lbId.getText()));
					watchList.setName(txtDescription.getText());
					watchList.setDescription(txtDescription.getText());
					proxy2.updateWatchList(watchList);
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/********Navigation to DisplayPage********/
			     Parent parent = FXMLLoader.load(getClass().getResource("/fxml/view/watchListsdisplay.fxml"));       
			        Scene scene = new Scene(parent);

			        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			        app_stage.setScene(scene);
			        app_stage.setTitle("My WatchLists");
			        app_stage.show();
	    }

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			lbId.setText(String.valueOf(WatchListTechnical.getId()));
			lbId.setVisible(false);
			txtName.setText(WatchListTechnical.getName());
			txtDescription.setText(WatchListTechnical.getDescription());
			
		}

}
