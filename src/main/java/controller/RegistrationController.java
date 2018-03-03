/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mdaghi
 */
public class RegistrationController implements Initializable {

	 @FXML
	    private AnchorPane pane;
	    @FXML
	    private TextField txtEmail;
	    @FXML
	    private PasswordField txtPassword;
	    @FXML
	    private TextField txtUsername;
	    @FXML
	    private PasswordField txtRetypePassword;
	    @FXML
	    private Hyperlink BtnLogin;

	 

	    /**
	     * Initializes the controller class.
	     */
	    @Override
	    public void initialize(URL url, ResourceBundle rb) {
	        // TODO
	    }    

	    @FXML
	    private void Registration(ActionEvent event) {
	    }

	    @FXML
	    private void BackLogin(ActionEvent event) throws IOException {
	    	Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/view/login.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			Stage primaryStage = (Stage) pane.getScene().getWindow();
			primaryStage.close();
	    }

}
