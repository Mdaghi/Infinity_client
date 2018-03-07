/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.controlsfx.control.Notifications;

import Util.SendMail;
import Util.Session;
import Util.StringGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import tn.esprit.infinity_server.interfaces.UserRemote;
import tn.esprit.infinity_server.persistence.Client;
import tn.esprit.infinity_server.persistence.User;

/**
 * FXML Controller class
 *
 * @author Mdaghi
 */
public class RegistrationController implements Initializable {

	UserRemote userProxy;

	@FXML
	private AnchorPane pane;
	@FXML
	private TextField txtEmail;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private TextField txtLogin;
	@FXML
	private PasswordField txtRetypePassword;
	@FXML
	private Hyperlink btnLogin;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		String jndiName = "infinity_server-ear/infinity_server-ejb/UserService!tn.esprit.infinity_server.interfaces.UserRemote";
		Context context;
		try {
			context = new InitialContext();
			userProxy = (UserRemote) context.lookup(jndiName);
		} catch (NamingException e) {
			System.out.println(e);
		}

	}

	@FXML
	private void registration(ActionEvent event) throws MessagingException, IOException, NamingException {

		// check Empty fields
		if (!valideEmptyRegistration())
			return;
		// check RegEx
		if (!valideRegExRegistration()) {
			return;
		}
		// check password with retype password
		if (!validePasswordWithRetypePassword())
			return;
		// check unique login
		if (!valideUniqueLogin(txtLogin.getText()))
			return;
		// Creation User instance
		User client = new Client();
		client.setEmail(txtEmail.getText());
		client.setLogin(txtLogin.getText());
		client.setPassword(txtPassword.getText());
		client.setActivate(0);
		// Generate Random code
		client.setCode(StringGenerator.generateString());
		// Sending verification Mail to valid Email
		SendMail mail = new SendMail("Account activation Code", txtEmail.getText(), client.getCode());
		// Add User and configuration of the Session
		userProxy.CreateUser(client);
		User user = userProxy.getUserByLogin(client.getLogin());
		Session.setUser(user);
		// Redirection to validation Account activation Code Interface
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/view/Activation.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		Stage primaryStage = (Stage) pane.getScene().getWindow();
		primaryStage.close();
	}

	@FXML
	private void backLogin(ActionEvent event) throws IOException {
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/view/login.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		Stage primaryStage = (Stage) pane.getScene().getWindow();
		primaryStage.close();
	}
	// Validation//

	/// validation Empty Registration form
	public boolean valideEmptyRegistration() {
		if (txtLogin.getText().isEmpty()) {
			Notifications notificationBuilder = Notifications.create().title("").text("Login field is empty  ")
					.darkStyle().graphic(null).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);

			notificationBuilder.showError();
			return false;
		}
		if (txtEmail.getText().isEmpty()) {
			Notifications notificationBuilder = Notifications.create().title("").text("Email field is empty  ")
					.darkStyle().graphic(null).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);

			notificationBuilder.showError();
			return false;
		}
		if (txtPassword.getText().isEmpty()) {
			Notifications notificationBuilder = Notifications.create().title("").text("Password field is empty  ")
					.darkStyle().graphic(null).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);

			notificationBuilder.showError();
			return false;
		}
		if (txtRetypePassword.getText().isEmpty()) {
			Notifications notificationBuilder = Notifications.create().title("")
					.text("Retype password field is empty  ").darkStyle().graphic(null).hideAfter(Duration.seconds(5))
					.position(Pos.BOTTOM_RIGHT);

			notificationBuilder.showError();
			return false;
		}
		return true;
	}

	// RegEx validation and lenght
	public boolean valideRegExRegistration() {
		String pattern = "^([_a-zA-Z0-9-']+(\\.[_a-zA-Z0-9-']+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
		Pattern patternEmail = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
		Matcher regexEmail = patternEmail.matcher(txtEmail.getText());
		if (!regexEmail.matches()) {
			Notifications notificationBuilder = Notifications.create().title("").text("Invalid Email Format")
					.darkStyle().graphic(null).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);

			notificationBuilder.showError();
			return false;
		}
		if (txtLogin.getText().length() > 50) {
			Notifications notificationBuilder = Notifications.create().title("")
					.text("Login must be less than 50 character").darkStyle().graphic(null)
					.hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);

			notificationBuilder.showError();
			return false;
		}
		if (txtPassword.getText().length() > 50) {
			Notifications notificationBuilder = Notifications.create().title("")
					.text("Password must be less than 50 character").darkStyle().graphic(null)
					.hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);

			notificationBuilder.showError();
			return false;
		}
		return true;
	}

	// validation Password equals to RetypePassword
	public boolean validePasswordWithRetypePassword() {
		if (!txtPassword.getText().equals(txtRetypePassword.getText())) {
			Notifications notificationBuilder = Notifications.create().title("")
					.text("Password and Retype password not the same ").darkStyle().graphic(null)
					.hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);

			notificationBuilder.showError();
			return false;
		}
		return true;
	}

	// validation unique login
	public boolean valideUniqueLogin(String login) throws NamingException {
		if (!userProxy.checkUniqueLogin(login)) {
			Notifications notificationBuilder = Notifications.create().title("").text("Login already exist ")
					.darkStyle().graphic(null).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);

			notificationBuilder.showError();
			return false;
		}
		return true;
	}

}
