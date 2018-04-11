package controller;

import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.ListCellRenderer;

import Util.Controls;
import javafx.scene.control.ListView;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ListCell;
import tn.esprit.infinity_server.interfaces.WatchListRemote;
import tn.esprit.infinity_server.persistence.WatchList;
import javafx.fxml.Initializable;

public class WatchListController implements Initializable{
	/*@FXML
    private GridPane telaCadastro;*/

    @FXML
    private Label legenda;

    @FXML
    private TextField txtName;

    @FXML
    private TextArea txtDescription;

    @FXML
    private Button btAddNewWatchList;

    @FXML
    private ToggleGroup menu;

    @FXML
    private Label lbTitle;
    
    @FXML()
    private Label lbCaracters;

    private String descriptionContainer="";
    Controls control=new Controls();

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
    void addNewWatchList(ActionEvent event) throws NamingException, IOException {
    	String jndiName="infinity_server-ear/infinity_server-ejb/WatchListService!tn.esprit.infinity_server.interfaces.WatchListRemote";
    	Context context;
    	
    	Date date = new Date();
			context = new InitialContext();

	    	WatchListRemote proxy2=(WatchListRemote)context.lookup(jndiName);
	    	WatchList watchList=new WatchList();
	    	watchList.setName(txtName.getText());
			watchList.setDescription(txtDescription.getText());
			watchList.setCreationDate(date);
			proxy2.createWatchList(watchList,1);
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
			txtDescription.setOnKeyPressed(e->{
			int length=255-txtDescription.getText().length();
			if(length>=0)
			{
			descriptionContainer="";
			lbCaracters.setText(length+"/255 caracters");
			}
			else
			{	
			if (descriptionContainer.equals(""))
				descriptionContainer=txtDescription.getText();
			else
				txtDescription.setText(descriptionContainer);

			lbCaracters.setText("0/255 caracters");
			}
			});
			txtName.setOnKeyPressed(e->{
				System.out.println(control.validateName(txtName.getText()));
				txtName.setPromptText(control.validateName(txtName.getText()));
			});
			
	}

}
