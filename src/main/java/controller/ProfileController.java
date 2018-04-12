/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.controlsfx.control.Notifications;

import Util.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import tn.esprit.infinity_server.interfaces.AddressRemote;
import tn.esprit.infinity_server.interfaces.UserRemote;
import tn.esprit.infinity_server.persistence.Address;
import tn.esprit.infinity_server.persistence.Client;
import tn.esprit.infinity_server.persistence.User;
import tn.esprit.infinity_server.services.SymboleService;

/**
 * FXML Controller class
 *
 * @author Mdaghi
 */
public class ProfileController implements Initializable {

	UserRemote userProxy;
	AddressRemote addressProxy;
	////////////

	@FXML
	private Label lbTitulo;
	@FXML
	private GridPane telaCadastro;
	@FXML
	private TextField txtFirstname;
	@FXML
	private TextField txtLastname;
	@FXML
	private TextField txtStreet;
	@FXML
	private TextField txtCountry;
	@FXML
	private TextField txtCity;
	@FXML
	private TextField txtPostalCode;
	@FXML
	private TextField txtNumber;
	@FXML
	private TextField txtNome1;
	@FXML
	private TextField txtPhoneNumber;
	@FXML
	private AnchorPane telaEdicao;

	@FXML
	private Button btnvalide;
	@FXML
	private TableColumn<?, ?> colId;
	@FXML
	private TableColumn<?, ?> colNome;
	@FXML
	private TableColumn<?, ?> colSigla;
	@FXML
	private TableColumn<?, ?> colEmail;
	@FXML
	private TableColumn<?, ?> colFax;
	@FXML
	private TableColumn<?, ?> colTelefone;
	@FXML
	private TableColumn<?, ?> colLogradouro;
	@FXML
	private TableColumn<?, ?> colBairro;
	@FXML
	private TableColumn<?, ?> colCidade;
	@FXML
	private TableColumn<?, ?> colEstado;
	@FXML
	private TableColumn<?, ?> colPais;
	@FXML
	private TableColumn<?, ?> colDescricao;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		try {
			initUser();
		} catch (NamingException e) {
			Logger.getLogger(SymboleService.class.getName()).log(Level.WARNING, " init user :" + e);
		}
		////

	}

	@FXML
	private void valide(ActionEvent event) {
		if (valideRegExEmptyRegistration()) {
			//
			Address address = null;
			if (Session.getUser().getAddress()!= null) {
				address = addressProxy.findAddressById(Session.getUser().getAddress().getId());
				address.setCity(txtCity.getText());
				address.setCountry(txtCountry.getText());
				address.setNumber(Integer.valueOf(txtNumber.getText()));
				address.setPostalCode(txtPostalCode.getText());
				address.setStreet(txtStreet.getText());
			} else {
				address = new Address();
				address.setCity(txtCity.getText());
				address.setCountry(txtCountry.getText());
				address.setNumber(Integer.valueOf(txtNumber.getText()));
				address.setPostalCode(txtPostalCode.getText());
				address.setStreet(txtStreet.getText());
			}
			//
			Client client = (Client) Session.getUser();
			client.setFirstname(txtFirstname.getText());
			client.setLastname(txtLastname.getText());
			client.setPhoneNumber(txtPhoneNumber.getText());
			client.setAddress(address);
			if (Session.getUser().getAddress() != null)
				addressProxy.UpdateAddress(address);
			userProxy.updateUser(client);

			Notifications notificationBuilder = Notifications.create().title("").text("Update with Success ")
					.darkStyle().graphic(null).hideAfter(Duration.seconds(15)).position(Pos.BOTTOM_RIGHT);
			notificationBuilder.showConfirm();

		}

	}

	public void initServiceJNDI() throws NamingException {
		String jndiName = "infinity_server-ear/infinity_server-ejb/UserService!tn.esprit.infinity_server.interfaces.UserRemote";
		Context context = new InitialContext();
		userProxy = (UserRemote) context.lookup(jndiName);

		jndiName = "infinity_server-ear/infinity_server-ejb/ServiceAddress!tn.esprit.infinity_server.interfaces.AddressRemote";
		context = new InitialContext();
		addressProxy = (AddressRemote) context.lookup(jndiName);
	}

	public void initUser() throws NamingException {
		initServiceJNDI();
		User user = Session.getUser();

		user = userProxy.getUserById(user);
		// Firstname
		if (user.getFirstname() != null)
			txtFirstname.setText(user.getFirstname());
		else
			txtFirstname.setText("");

		// Lastname
		if (user.getLastname() != null)
			txtLastname.setText(user.getLastname());
		else
			txtLastname.setText("");
		// Phone
		if (user.getPhoneNumber() != null)
			txtPhoneNumber.setText(user.getPhoneNumber());
		else
			txtPhoneNumber.setText("");

		// Address
		if (user.getAddress() != null) {
			if (user.getAddress().getStreet() != null)
				txtStreet.setText(user.getAddress().getStreet());
			else
				txtStreet.setText("");

			if (user.getAddress().getCity() != null)
				txtCity.setText(user.getAddress().getCity());
			else
				txtCity.setText("");

			if (user.getAddress().getPostalCode() != null)
				txtPostalCode.setText(user.getAddress().getPostalCode());
			else
				txtPostalCode.setText("");

			if (user.getAddress().getNumber() != 0)
				txtNumber.setText(user.getAddress().getNumber() + "");
			else
				txtNumber.setText("");

			if (user.getAddress().getCountry() != null)
				txtCountry.setText(user.getAddress().getCountry() + "");
			else
				txtCountry.setText("");
		}
	}

	// RegEx validation and lenght
	public boolean valideRegExEmptyRegistration() {

		if (txtPhoneNumber.getText().isEmpty()) {
			Notifications notificationBuilder = Notifications.create().title("").text("Phone Number field is empty  ")
					.darkStyle().graphic(null).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);
			notificationBuilder.showError();
			return false;
		}
		if (txtFirstname.getText().isEmpty()) {
			Notifications notificationBuilder = Notifications.create().title("").text("Firstname field is empty  ")
					.darkStyle().graphic(null).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);

			notificationBuilder.showError();
			return false;
		}
		if (txtLastname.getText().isEmpty()) {
			Notifications notificationBuilder = Notifications.create().title("").text("Lastname field is empty  ")
					.darkStyle().graphic(null).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);

			notificationBuilder.showError();
			return false;
		}

		if (txtCity.getText().isEmpty()) {
			Notifications notificationBuilder = Notifications.create().title("").text("City field is empty  ")
					.darkStyle().graphic(null).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);

			notificationBuilder.showError();
			return false;
		}
		if (txtCountry.getText().isEmpty()) {
			Notifications notificationBuilder = Notifications.create().title("").text("Country field is empty  ")
					.darkStyle().graphic(null).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);

			notificationBuilder.showError();
			return false;
		}
		if (txtNumber.getText().isEmpty()) {
			Notifications notificationBuilder = Notifications.create().title("").text("Number field is empty  ")
					.darkStyle().graphic(null).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);

			notificationBuilder.showError();
			return false;
		}
		if (txtPostalCode.getText().isEmpty()) {
			Notifications notificationBuilder = Notifications.create().title("").text("Postal code field is empty  ")
					.darkStyle().graphic(null).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);

			notificationBuilder.showError();
			return false;
		}
		if (txtStreet.getText().isEmpty()) {
			Notifications notificationBuilder = Notifications.create().title("").text("Street field is empty  ")
					.darkStyle().graphic(null).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);

			notificationBuilder.showError();
			return false;
		}

		String regName = "^[a-zA-Z]+$";
		Pattern patternName = Pattern.compile(regName, Pattern.CASE_INSENSITIVE);
		String regNumber = "^[0-9]+$";
		Pattern patternNumber = Pattern.compile(regNumber);
		// Firstname
		Matcher regexName = patternName.matcher(txtFirstname.getText());
		if (!regexName.matches()) {
			Notifications notificationBuilder = Notifications.create().title("").text("Invalid Firstname Format")
					.darkStyle().graphic(null).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);

			notificationBuilder.showError();
			return false;
		}
		// LastName
		regexName = patternName.matcher(txtLastname.getText());
		if (!regexName.matches()) {
			Notifications notificationBuilder = Notifications.create().title("").text("Invalid Lastname Format")
					.darkStyle().graphic(null).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);

			notificationBuilder.showError();
			return false;
		}
		// City
		regexName = patternName.matcher(txtCity.getText());
		if (!regexName.matches()) {
			Notifications notificationBuilder = Notifications.create().title("").text("Invalid City  Format")
					.darkStyle().graphic(null).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);

			notificationBuilder.showError();
			return false;
		}
		// Country
		regexName = patternName.matcher(txtCountry.getText());
		if (!regexName.matches()) {
			Notifications notificationBuilder = Notifications.create().title("").text("Invalid Country  Format")
					.darkStyle().graphic(null).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);

			notificationBuilder.showError();
			return false;
		}
		// Phone Number
		Matcher regexNumber = patternNumber.matcher(txtPhoneNumber.getText());
		if (!regexNumber.matches()) {
			Notifications notificationBuilder = Notifications.create().title("").text("Invalid Lastname Format")
					.darkStyle().graphic(null).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);

			notificationBuilder.showError();
			return false;
		}
		// Address Number
		regexNumber = patternNumber.matcher(txtNumber.getText());
		if (!regexNumber.matches()) {
			Notifications notificationBuilder = Notifications.create().title("").text("Invalid Number Format")
					.darkStyle().graphic(null).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);

			notificationBuilder.showError();
			return false;
		}
		// Postal Code
		regexNumber = patternNumber.matcher(txtPostalCode.getText());
		if (!regexNumber.matches()) {
			Notifications notificationBuilder = Notifications.create().title("").text("Invalid Postal Code Format")
					.darkStyle().graphic(null).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);

			notificationBuilder.showError();
			return false;
		}
		// Lenght
		if (txtPhoneNumber.getText().length() != 8) {
			Notifications notificationBuilder = Notifications.create().title("")
					.text("Phone number must containe 8 numbers").darkStyle().graphic(null)
					.hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);

			notificationBuilder.showError();
			return false;
		}

		if (txtFirstname.getText().length() > 50) {
			Notifications notificationBuilder = Notifications.create().title("")
					.text("Firstname must be less than 50 character").darkStyle().graphic(null)
					.hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);

			notificationBuilder.showError();
			return false;
		}
		if (txtLastname.getText().length() > 50) {
			Notifications notificationBuilder = Notifications.create().title("")
					.text("Lastname must be less than 50 character").darkStyle().graphic(null)
					.hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);

			notificationBuilder.showError();
			return false;
		}
		if (txtPostalCode.getText().length() > 4) {
			Notifications notificationBuilder = Notifications.create().title("")
					.text("Lastname must be less than 4 character").darkStyle().graphic(null)
					.hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);

			notificationBuilder.showError();
			return false;
		}
		if (txtNumber.getText().length() > 6) {
			Notifications notificationBuilder = Notifications.create().title("")
					.text("Number must be less than 4 character").darkStyle().graphic(null)
					.hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);

			notificationBuilder.showError();
			return false;
		}
		return true;
	}

}
