/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.Future;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.controlsfx.control.Notifications;
import org.controlsfx.control.action.Action;

import com.sun.xml.internal.ws.api.server.Container;

import Util.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Node;
import tn.esprit.infinity_server.interfaces.AddressRemote;
import tn.esprit.infinity_server.interfaces.TradorRemote;
import tn.esprit.infinity_server.interfaces.UserRemote;
import tn.esprit.infinity_server.persistence.User;

/**
 * FXML Controller class
 *
 * @author Mdaghi
 */
public class ClientController implements Initializable {
	
	UserRemote userProxy;
	AddressRemote addressProxy;
	TradorRemote traderProxy;
	/////////
	
    @FXML
    private Label lbUser;
    @FXML
    private AnchorPane container;
    @FXML
    private Button BtnLogout;
    @FXML
    private ToggleButton btnEditProfile;
    @FXML
    private ToggleButton BtnChangePassword;
    @FXML
    private ToggleButton btnDesactivate;
    
    @FXML
    private ToggleButton btnRealTimeData;
    @FXML
    private ToggleButton btnCalculator;
    @FXML
    private ToggleButton btnManageFuture;
    

    @FXML
    private ToggleButton tgWatchList;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	try {
			initServiceJNDI();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
    	lbUser.setText(Session.getUser().getLogin());
    	
    }    

    @FXML
    private void menuDashboard(MouseEvent event) {
    }
	@FXML
    private void Logout(ActionEvent event) throws IOException {
		clearSound();
    	Session.setUser(new User());
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/view/Login.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		Stage primaryStage = (Stage) container.getScene().getWindow();
		primaryStage.close();
    }
	@FXML
    private void editProfile(ActionEvent event) throws IOException {
		clearSound();
		container.getChildren().clear();
		container.getChildren().add((Node) FXMLLoader.load(getClass().getResource("/fxml/view/Profile.fxml")));
    }
	@FXML
    private void calculate(ActionEvent event) throws IOException {
		FutureController.stopListener();
    	container.getChildren().removeAll();
    	container.getChildren().clear();
		container.getChildren().add((Node) FXMLLoader.load(getClass().getResource("/fxml/view/CalculateFuture.fxml")));
    }
	@FXML
    private void historic(ActionEvent event) throws IOException {
		container.getChildren().clear();
    	container.getChildren().add((Node) FXMLLoader.load(getClass().getResource("/fxml/view/Historique.fxml")));
    	clearSound();
    }
    @FXML
    private void changePassword(ActionEvent event) throws IOException {
    	container.getChildren().clear();
    	container.getChildren().add((Node) FXMLLoader.load(getClass().getResource("/fxml/view/ChangePassword.fxml")));
    	clearSound();
    }
    @FXML
    private void RealTimeData(ActionEvent event) throws IOException {
    	CalculateFutureController.stopListener();
    	container.getChildren().removeAll();
    	container.getChildren().clear();
		container.getChildren().add((Node) FXMLLoader.load(getClass().getResource("/fxml/view/Future.fxml")));
    }

    @FXML
    private void desactivate(ActionEvent event) {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Disactivation");
    	alert.setHeaderText("Disactivate your account");
    	alert.setContentText("Are you sure ?");
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		User user = Session.getUser();
    		user.setActivate(0);
    	    userProxy.updateUser(user);
    	    Notifications notificationBuilder = Notifications.create().title("")
    				.text("your account has been disactivate").darkStyle().graphic(null)
    				.hideAfter(Duration.seconds(10)).position(Pos.BOTTOM_RIGHT);
    		notificationBuilder.showConfirm();
    		container.getChildren().clear();
    	} 
    }
    
    
    public void initServiceJNDI() throws NamingException
    {
    	String jndiName = "infinity_server-ear/infinity_server-ejb/UserService!tn.esprit.infinity_server.interfaces.UserRemote";
		Context context = new InitialContext();
		userProxy = (UserRemote) context.lookup(jndiName);
		
		jndiName = "infinity_server-ear/infinity_server-ejb/ServiceAddress!tn.esprit.infinity_server.interfaces.AddressRemote";
		context = new InitialContext();
		addressProxy = (AddressRemote) context.lookup(jndiName);
    }
    public void clearSound()
    {
    	FutureController.stopListener();
    	CalculateFutureController.stopListener();
    }
    
    
}
