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
import javafx.scene.control.Button;
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
public class ActivationController implements Initializable {

	UserRemote userProxy;

	@FXML
	private AnchorPane pane;
	@FXML
	private TextField txtEmail;
	@FXML
	private PasswordField txtCode;
	@FXML
	private Hyperlink linkResend;
	@FXML
	private Button activate;
	@FXML
	private Button btnChangingMail;

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
	private void resendValidationCode(ActionEvent event) throws MessagingException {
		User client =  userProxy.getUserById(Session.getUser());
		client.setCode(StringGenerator.generateString());
		userProxy.updateUser(client);
		SendMail mail = new SendMail("Account activation Code",client.getEmail(), client.getCode());
		Notifications notificationBuilder = Notifications.create().title("")
				.text("we have send to you a new activation code please check your email").darkStyle().graphic(null)
				.hideAfter(Duration.seconds(10)).position(Pos.BOTTOM_RIGHT);
		notificationBuilder.showConfirm();
	}

	@FXML
	private void activationAccount(ActionEvent event) throws IOException {
		// validation
		if (Session.getUser().getCode().equals(txtCode.getText())) {
			userProxy.activateAccount(Session.getUser().getLogin());
			Notifications notificationBuilder = Notifications.create().title("")
					.text("Thank you " + Session.getUser().getLogin() + "Your account is now activate").darkStyle()
					.graphic(null).hideAfter(Duration.seconds(10)).position(Pos.BOTTOM_RIGHT);
			notificationBuilder.showConfirm();
			// Redirection to Client.FXML
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/view/Client.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			Stage primaryStage = (Stage) pane.getScene().getWindow();
			primaryStage.close();
		} else {
			Notifications notificationBuilder = Notifications.create().title("").text("Check your activation code")
					.darkStyle().graphic(null).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);

			notificationBuilder.showError();
		}

	}

	@FXML
	private void changingMailAddress(ActionEvent event) throws MessagingException {
		// Email Empty
		if (txtEmail.getText().isEmpty()) {
			Notifications notificationBuilder = Notifications.create().title("").text("Email field is empty  ")
					.darkStyle().graphic(null).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);

			notificationBuilder.showError();
			return;
		}
		// RegEx
		Pattern patternEmail = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		Matcher regexEmail = patternEmail.matcher(txtEmail.getText());
		if (!regexEmail.matches()) {
			Notifications notificationBuilder = Notifications.create().title("").text("Invalid Email Format")
					.darkStyle().graphic(null).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);

			notificationBuilder.showError();
			return;
		}
		// Update mail and Sending new verification Code
		System.out.println(Session.getUser());
		User client =  userProxy.getUserById(Session.getUser());
		client.setCode(StringGenerator.generateString());
		client.setEmail(txtEmail.getText());
		userProxy.updateUser(client);
		SendMail mail = new SendMail("Account activation Code", txtEmail.getText(), client.getCode());
		Notifications notificationBuilder = Notifications.create().title("")
				.text("check your new email you will find the activation code ").darkStyle().graphic(null)
				.hideAfter(Duration.seconds(10)).position(Pos.BOTTOM_RIGHT);

		notificationBuilder.showConfirm();
		Session.getUser().setEmail(client.getEmail());
		Session.getUser().setCode(client.getCode());

	}

}
