/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.controlsfx.control.Notifications;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import tn.esprit.infinity_server.services.UserService;

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
	private TableColumn<Client, Integer> colId;
	@FXML
	private TableColumn<Client, String> colFirstname;
	@FXML
	private TableColumn<Client, String> colLastname;
	@FXML
	private TableColumn<Client, String> colEmail;
	@FXML
	private TableColumn<Client, String> colPhone;
	@FXML
	private TableColumn<Client, Integer> colBan;
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
		} catch (Exception e) {
			Logger.getLogger(UserService.class.getName()).log(Level.WARNING, "Build data : " + e);
		}
	}

	@FXML
	private void ban(ActionEvent event) throws NamingException {
		if (twClient.getSelectionModel().getSelectedItem() == null) {
			Notifications notificationBuilder = Notifications.create().title("").text("You must choose a client")
					.darkStyle().graphic(null).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);
			notificationBuilder.showError();
			return;
		}
		Client client = twClient.getSelectionModel().getSelectedItem();
		if (client.getIsBanned() == 1) {
			Notifications notificationBuilder = Notifications.create().title("").text("Client already banned ")
					.darkStyle().graphic(null).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);
			notificationBuilder.showError();
			return;
		} else {
			client.setIsBanned(1);
			userProxy.updateUser(client);
			Notifications notificationBuilder = Notifications.create().title("").text("Client banned with success ")
					.darkStyle().graphic(null).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);
			notificationBuilder.showConfirm();
		}
		userObser.clear();
		twClient.setItems(userObser);
		buildData();
	}

	@FXML
	private void authorized(ActionEvent event) throws NamingException {
		if (twClient.getSelectionModel().getSelectedItem() == null) {
			Notifications notificationBuilder = Notifications.create().title("").text("You must choose a client")
					.darkStyle().graphic(null).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);
			notificationBuilder.showError();
			return;
		}
		Client client = twClient.getSelectionModel().getSelectedItem();
		if (client.getIsBanned() == 0) {
			Notifications notificationBuilder = Notifications.create().title("").text("Client already authorized ")
					.darkStyle().graphic(null).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);
			notificationBuilder.showError();
			return;
		} else {
			client.setIsBanned(0);
			userProxy.updateUser(client);
			Notifications notificationBuilder = Notifications.create().title("").text("Client authorized with success ")
					.darkStyle().graphic(null).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);
			notificationBuilder.showConfirm();
		}
		userObser.clear();
		twClient.setItems(userObser);
		buildData();

	}

	public void buildData() throws NamingException {
		String jndiName = "infinity_server-ear/infinity_server-ejb/UserService!tn.esprit.infinity_server.interfaces.UserRemote";
		Context context = new InitialContext();
		userProxy = (UserRemote) context.lookup(jndiName);
		//
		colId.setCellValueFactory(new PropertyValueFactory<Client, Integer>("id"));
		colFirstname.setCellValueFactory(new PropertyValueFactory<Client, String>("firstname"));
		colLastname.setCellValueFactory(new PropertyValueFactory<Client, String>("lastname"));
		colEmail.setCellValueFactory(new PropertyValueFactory<Client, String>("email"));
		colBan.setCellValueFactory(new PropertyValueFactory<Client, Integer>("isBanned"));
		colPhone.setCellValueFactory(new PropertyValueFactory<Client, String>("phoneNumber"));
		ArrayList<User> lst = (ArrayList<User>) userProxy.getAllClient();
		for (User u : lst) {
			if (u instanceof Client) {
				Client client = (Client) u;
				userObser.add(client);
			}
		}
		twClient.setItems(userObser);

		/*********
		 * FilteredList<Client> filteredData = new FilteredList<>(userObser,client -> true); txtSearch.setOnKeyReleased( client ->{
		 * txtSearch.textProperty().addListener((observable, oldValue, newValue)
		 * -> { filteredData.setPredicate((java.util.function.Predicate<? super
		 * User>) User -> { if (newValue == null || newValue.isEmpty()){ return
		 * true; } String lowerCaseFilter = newValue.toLowerCase();
		 * 
		 * if (User.getLogin().toLowerCase().contains(CharSequence.class.cast(
		 * lowerCaseFilter))){ return true; } return false;
		 * 
		 * 
		 * }); }); SortedList<Client> sortedData = new
		 * SortedList<>(filteredData);
		 * sortedData.comparatorProperty().bind(twClient.comparatorProperty());
		 * twClient.setItems(sortedData); });
		 ********/

	}

}
