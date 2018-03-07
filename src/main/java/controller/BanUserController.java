/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.controlsfx.control.Notifications;
import org.omg.CORBA.TCKind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import tn.esprit.infinity_server.interfaces.UserRemote;
import tn.esprit.infinity_server.persistence.Client;
import tn.esprit.infinity_server.persistence.User;

/**
 * FXML Controller class
 *
 * @author Mdaghi
 */
public class BanUserController implements Initializable {

    @FXML
    private Label lbTitulo;
    @FXML
    private TextField txtSearch;
    @FXML
    private AnchorPane telaEdicao;
    @FXML
    private TableView<Client> twClient;
    @FXML
    private TableColumn<?, ?> colId;
    @FXML
    private TableColumn<?, ?> colFirstname;
    @FXML
    private TableColumn<?, ?> colLastname;
    @FXML
    private TableColumn<?, ?> colEmail;
    @FXML
    private TableColumn<?, ?> colPhone;
    @FXML
    private TableColumn<?, ?> colBan;
    @FXML
    private Button btnBanUser;
    @FXML
    private Button btnAuthorized;
    
    ObservableList<Client> userObser = FXCollections.observableArrayList();
    
    UserRemote userProxy;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	try {
			buildData();
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }    

    @FXML
    private void ban(ActionEvent event) throws SQLException, NamingException {
    	if (twClient.getSelectionModel().getSelectedItem() == null) {
    		Notifications notificationBuilder = Notifications.create().title("")
					.text("You must choose a client").darkStyle().graphic(null).hideAfter(Duration.seconds(5))
					.position(Pos.BOTTOM_RIGHT);
			notificationBuilder.showError();
            return;
        }
    	Client client = twClient.getSelectionModel().getSelectedItem();
    	if(client.getIsBanned()==1)
    	{
    		Notifications notificationBuilder = Notifications.create().title("")
					.text("Client already banned ").darkStyle().graphic(null).hideAfter(Duration.seconds(5))
					.position(Pos.BOTTOM_RIGHT);
			notificationBuilder.showError();
            return;
    	}
    	else
    	{
    		client.setIsBanned(1);
    		userProxy.updateUser(client);
    		Notifications notificationBuilder = Notifications.create().title("")
					.text("Client banned with success ").darkStyle().graphic(null).hideAfter(Duration.seconds(5))
					.position(Pos.BOTTOM_RIGHT);
			notificationBuilder.showConfirm();
    	}
    	ObservableList<Client> uObser = FXCollections.observableArrayList();
    	uObser.clear();
    	twClient.setItems(uObser);
    	buildData();
    }

    @FXML
    private void authorized(ActionEvent event) throws SQLException, NamingException {
    	if (twClient.getSelectionModel().getSelectedItem() == null) {
    		Notifications notificationBuilder = Notifications.create().title("")
					.text("You must choose a client").darkStyle().graphic(null).hideAfter(Duration.seconds(5))
					.position(Pos.BOTTOM_RIGHT);
			notificationBuilder.showError();
            return;
        }
    	Client client = twClient.getSelectionModel().getSelectedItem();
    	if(client.getIsBanned()==0)
    	{
    		Notifications notificationBuilder = Notifications.create().title("")
					.text("Client already authorized ").darkStyle().graphic(null).hideAfter(Duration.seconds(5))
					.position(Pos.BOTTOM_RIGHT);
			notificationBuilder.showError();
            return;
    	}
    	else
    	{
    		client.setIsBanned(0);
    		userProxy.updateUser(client);
    		Notifications notificationBuilder = Notifications.create().title("")
					.text("Client authorized with success ").darkStyle().graphic(null).hideAfter(Duration.seconds(5))
					.position(Pos.BOTTOM_RIGHT);
			notificationBuilder.showConfirm();
    	}
    	ObservableList<Client> uObser = FXCollections.observableArrayList();
    	uObser.clear();
    	twClient.setItems(uObser);
    	buildData();
    	
    }
    
    public void buildData() throws SQLException, NamingException {
    	String jndiName = "infinity_server-ear/infinity_server-ejb/UserService!tn.esprit.infinity_server.interfaces.UserRemote";
		Context context = new InitialContext();
		userProxy = (UserRemote) context.lookup(jndiName);
		//
    	colId.setCellValueFactory(new PropertyValueFactory("id"));
    	colFirstname.setCellValueFactory(new PropertyValueFactory("firstname"));
    	colLastname.setCellValueFactory(new PropertyValueFactory("lastname"));
    	colEmail.setCellValueFactory(new PropertyValueFactory("email"));
    	colBan.setCellValueFactory(new PropertyValueFactory("isBanned"));
    	colPhone.setCellValueFactory(new PropertyValueFactory("phoneNumber"));
    	ArrayList<User> lst = (ArrayList<User>) userProxy.getAllClient();
    	for(User u : lst)
    	{
    		if(u instanceof Client)
    		{
    			Client client = (Client)u;
    			userObser.add(client);
    		}
    	}
    	twClient.setItems(userObser);
    	
    	///////////
    	FilteredList<Client> filteredData = new  FilteredList<>(userObser, client -> true);
    	txtSearch.setOnKeyReleased( client ->{
    		txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
    			filteredData.setPredicate((java.util.function.Predicate<? super User>) User -> {
               if (newValue == null || newValue.isEmpty()){
                     return true;
                 }
                String lowerCaseFilter = newValue.toLowerCase();
              
               if (User.getLogin().toLowerCase().contains(CharSequence.class.cast(lowerCaseFilter))){
                     return true;
                 }         
                 return false;
              
                 
             });
         });
         SortedList<Client> sortedData = new SortedList<>(filteredData);
         sortedData.comparatorProperty().bind(twClient.comparatorProperty());
         twClient.setItems(sortedData);
         
        });
       
    }
    
    
}
