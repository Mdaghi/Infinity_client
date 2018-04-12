package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import Util.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.infinity_server.interfaces.AddressRemote;
import tn.esprit.infinity_server.interfaces.AdmininstratorRemote;
import tn.esprit.infinity_server.interfaces.TradorRemote;
import tn.esprit.infinity_server.interfaces.UserRemote;
import tn.esprit.infinity_server.persistence.Address;
import tn.esprit.infinity_server.persistence.Administrator;
import tn.esprit.infinity_server.persistence.Client;
import tn.esprit.infinity_server.persistence.Trader;
import tn.esprit.infinity_server.persistence.User;
import tn.esprit.infinity_server.services.SymboleService;

public class LoginController implements Initializable {

	UserRemote userProxy;
	AddressRemote addressProxy;
	TradorRemote traderProxy;
	AdmininstratorRemote adminProxy;

	//////////////////////////////////
	@FXML
	private AnchorPane pane;
	@FXML
	private TextField txtUsername;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private Label login;
	@FXML
	private Hyperlink btnRegistration;

	@FXML
	private void login(ActionEvent event) throws NamingException, IOException {
		
		/****
		// Ajout simple User
		// addUser(txtUsername.getText(),txtPassword.getText());
		// Ajout Trader
		//addTrader(txtUsername.getText(),txtPassword.getText());
		// Ajout Admin
		//addAdmin(txtUsername.getText(), txtPassword.getText());
		 *****/
		String jndiName = "infinity_server-ear/infinity_server-ejb/UserService!tn.esprit.infinity_server.interfaces.UserRemote";
		Context context = new InitialContext();
		userProxy = (UserRemote) context.lookup(jndiName);
		User user = userProxy.authenticate(txtUsername.getText(), txtPassword.getText());
		if (user != null) {
			if (user instanceof Client) {
				// interface Client
				Session.setUser(user);
				if (user.getActivate() == 1) {
					Parent root = FXMLLoader.load(getClass().getResource("/fxml/view/Client.fxml"));
					Stage stage = new Stage();
					Scene scene = new Scene(root);
					stage.setScene(scene);
					stage.show();
					Stage primaryStage = (Stage) pane.getScene().getWindow();
					primaryStage.close();
				} else {
					Parent root = FXMLLoader.load(getClass().getResource("/fxml/view/Activation.fxml"));
					Stage stage = new Stage();
					Scene scene = new Scene(root);
					stage.setScene(scene);
					stage.show();
					Stage primaryStage = (Stage) pane.getScene().getWindow();
					primaryStage.close();
				}
			} else if (user instanceof Trader) {
				// Interface Trader
				Session.setUser(user);
				if (user.getActivate() == 1) {
					Parent root = FXMLLoader.load(getClass().getResource("/fxml/view/Trader.fxml"));
					Stage stage = new Stage();
					Scene scene = new Scene(root);
					stage.setScene(scene);
					stage.show();
					Stage primaryStage = (Stage) pane.getScene().getWindow();
					primaryStage.close();
				} else {
					Parent root = FXMLLoader.load(getClass().getResource("/fxml/view/Activation.fxml"));
					Stage stage = new Stage();
					Scene scene = new Scene(root);
					stage.setScene(scene);
					stage.show();
					Stage primaryStage = (Stage) pane.getScene().getWindow();
					primaryStage.close();
				}
			} else if(user instanceof Administrator) {
				// Admin
				Session.setUser(user);
				Stage stage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/fxml/view/Administrator.fxml"));
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
				Stage primaryStage = (Stage) pane.getScene().getWindow();
				primaryStage.close();
			} 
		}

	}

	@FXML
	private void registration(ActionEvent event) throws IOException {
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/view/Registration.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		Stage primaryStage = (Stage) pane.getScene().getWindow();
		primaryStage.close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Logger.getLogger(SymboleService.class.getName()).log(Level.WARNING, " initialize ");

	}

	/****
	public void addUser(String login, String password) throws NamingException {

		//
		String jndiName = "infinity_server-ear/infinity_server-ejb/UserService!tn.esprit.infinity_server.interfaces.UserRemote";
		Context context = new InitialContext();
		userProxy = (UserRemote) context.lookup(jndiName);
		//
		jndiName = "infinity_server-ear/infinity_server-ejb/ServiceAddress!tn.esprit.infinity_server.interfaces.AddressRemote";
		context = new InitialContext();
		addressProxy = (AddressRemote) context.lookup(jndiName);

		/// Creation D'adresse
		Address address = new Address();
		address.setCity("Tunis");
		address.setCountry("Tunisia");
		address.setNumber(9);
		address.setPostalCode("2048");
		address.setStreet("Habib bourguiba");
		/// Ajout User
		User u = new User();
		u.setAddress(address);
		u.setLogin(login);
		u.setPassword(password);
		u.setActivate(0);
		u.setCode("147852369");
		u.setEmail("seifeddine.mdaghi@esprit.tn");
		u.setFirstname("Mdaghi");
		u.setLastname("Seifeddine");
		u.setPhoneNumber("52461623");
		userProxy.CreateUser(u);
	}
	****/

	// Add Trader
	public void addTrader(String login, String password) throws NamingException {

		//
		String jndiName = "infinity_server-ear/infinity_server-ejb/ServiceTrador!tn.esprit.infinity_server.interfaces.TradorRemote";
		Context context = new InitialContext();
		traderProxy = (TradorRemote) context.lookup(jndiName);
		//
		jndiName = "infinity_server-ear/infinity_server-ejb/ServiceAddress!tn.esprit.infinity_server.interfaces.AddressRemote";
		context = new InitialContext();
		addressProxy = (AddressRemote) context.lookup(jndiName);

		/// Creation D'adresse
		Address address = new Address();
		address.setCity("Tunis");
		address.setCountry("Tunisia");
		address.setNumber(9);
		address.setPostalCode("2048");
		address.setStreet("Habib bourguiba");
		/// Ajout trader
		Trader trader = new Trader();
		trader.setAddress(address);
		trader.setLogin(login);
		trader.setPassword(password);
		trader.setActivate(0);
		trader.setCode("147852369");
		trader.setEmail("seifeddine.mdaghi@esprit.tn");
		trader.setFirstname("Mdaghi");
		trader.setLastname("Seifeddine");
		trader.setPhoneNumber("52461623");
		trader.setGrade(5);
		traderProxy.CreateTrader(trader);
	}

	// Add Admin
	public void addAdmin(String login, String password) throws NamingException {

		//
		String jndiName = "infinity_server-ear/infinity_server-ejb/AdmininstratorService!tn.esprit.infinity_server.interfaces.AdmininstratorRemote";
		Context context = new InitialContext();
		adminProxy = (AdmininstratorRemote) context.lookup(jndiName);
		//
		jndiName = "infinity_server-ear/infinity_server-ejb/ServiceAddress!tn.esprit.infinity_server.interfaces.AddressRemote";
		context = new InitialContext();
		addressProxy = (AddressRemote) context.lookup(jndiName);

		/// Creation D'adresse
		Address address = new Address();
		address.setCity("Tunis");
		address.setCountry("Tunisia");
		address.setNumber(9);
		address.setPostalCode("2048");
		address.setStreet("Habib bourguiba");
		/// Ajout trader
		Administrator admin = new Administrator();
		admin.setAddress(address);
		admin.setLogin(login);
		admin.setPassword(password);
		admin.setActivate(0);
		admin.setCode("147852369");
		admin.setEmail("seifeddine.mdaghi@esprit.tn");
		admin.setFirstname("Mdaghi");
		admin.setLastname("Seifeddine");
		admin.setPhoneNumber("52461623");
		admin.setRole("super admin");
		adminProxy.CreateAdmin(admin);
	}

}
