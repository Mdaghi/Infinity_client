package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
import tn.esprit.infinity_server.interfaces.TradorRemote;
import tn.esprit.infinity_server.interfaces.UserRemote;
import tn.esprit.infinity_server.persistence.Address;
import tn.esprit.infinity_server.persistence.Administrator;
import tn.esprit.infinity_server.persistence.Client;
import tn.esprit.infinity_server.persistence.Trader;
import tn.esprit.infinity_server.persistence.User;

public class LoginController implements Initializable {

	UserRemote UserProxy;
	AddressRemote AddressProxy;
	TradorRemote TraderProxy;
	
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
    private Hyperlink BtnRegistration;

	@FXML
	private void login(ActionEvent event) throws NamingException, IOException {
		// Ajout simple User
		//addUser(txtUsername.getText(),txtPassword.getText());
		// Ajout Trader
		//addTrader(txtUsername.getText(),txtPassword.getText());
		String jndiName = "infinity_server-ear/infinity_server-ejb/UserService!tn.esprit.infinity_server.interfaces.UserRemote";
		Context context = new InitialContext();
		UserProxy = (UserRemote) context.lookup(jndiName);

		System.out.println(txtUsername.getText());
		System.out.println(txtPassword.getText());
		User user = UserProxy.authenticate(txtUsername.getText(), txtPassword.getText());
		if (user != null) {
			if (user instanceof Trader) {
				// interface Trader
				Session.setUser(user);
				Trader trader = (Trader) user;
				Stage stage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/fxml/view/Client.fxml"));
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
				Stage primaryStage = (Stage) pane.getScene().getWindow();
				primaryStage.close();
				System.out.println(trader.getGrade());				
			} else if (user instanceof Client) {
				// Client
			} else if (user instanceof Administrator) {
				// Admin
			} else {
				// User
				Session.setUser(user);
				Stage stage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/fxml/view/Client.fxml"));
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
				Stage primaryStage = (Stage) pane.getScene().getWindow();
				primaryStage.close();
			}
		}

	}
	
	@FXML
    private void Registration(ActionEvent event) throws IOException {
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

	}

	public void addUser(String login, String password) throws NamingException {

		//
		String jndiName = "infinity_server-ear/infinity_server-ejb/UserService!tn.esprit.infinity_server.interfaces.UserRemote";
		Context context = new InitialContext();
		UserProxy = (UserRemote) context.lookup(jndiName);
		//
		jndiName = "infinity_server-ear/infinity_server-ejb/ServiceAddress!tn.esprit.infinity_server.interfaces.AddressRemote";
		context = new InitialContext();
		AddressProxy = (AddressRemote) context.lookup(jndiName);

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
		u.setActivate(false);
		u.setCode("147852369");
		u.setEmail("seifeddine.mdaghi@esprit.tn");
		u.setFirstname("Mdaghi");
		u.setLastname("Seifeddine");
		u.setPhoneNumber("52461623");
		//
		// List<SaveArticle> Articles = new ArrayList<SaveArticle>();
		// u.setSaveArticles(Articles);
		//
		// List<SubscribeNewsSource> subscribe = new
		// ArrayList<SubscribeNewsSource>();
		// u.setSubscribeNewsSource(subscribe);
		///
		UserProxy.CreateUser(u);
	}
	// Add Trader
	public void addTrader(String login, String password) throws NamingException {

		//
		String jndiName = "infinity_server-ear/infinity_server-ejb/ServiceTrador!tn.esprit.infinity_server.interfaces.TradorRemote";
		Context context = new InitialContext();
		TraderProxy = (TradorRemote) context.lookup(jndiName);
		//
		jndiName = "infinity_server-ear/infinity_server-ejb/ServiceAddress!tn.esprit.infinity_server.interfaces.AddressRemote";
		context = new InitialContext();
		AddressProxy = (AddressRemote) context.lookup(jndiName);

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
		trader.setActivate(false);
		trader.setCode("147852369");
		trader.setEmail("seifeddine.mdaghi@esprit.tn");
		trader.setFirstname("Mdaghi");
		trader.setLastname("Seifeddine");
		trader.setPhoneNumber("52461623");
		trader.setGrade(5);
		//
		// List<SaveArticle> Articles = new ArrayList<SaveArticle>();
		// u.setSaveArticles(Articles);
		//
		// List<SubscribeNewsSource> subscribe = new
		// ArrayList<SubscribeNewsSource>();
		// u.setSubscribeNewsSource(subscribe);
		///
		TraderProxy.CreateTrader(trader);
	}
	
	

}
