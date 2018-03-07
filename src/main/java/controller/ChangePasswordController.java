/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import tn.esprit.infinity_server.interfaces.UserRemote;
import tn.esprit.infinity_server.persistence.User;

/**
 * FXML Controller class
 *
 * @author Mdaghi
 */
public class ChangePasswordController implements Initializable {

    @FXML
    private Label lbTitulo;
    @FXML
    private GridPane telaCadastro;
    @FXML
    private AnchorPane telaEdicao;
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
    @FXML
    private Button btnChange;
    @FXML
    private PasswordField txtCurrentPass;
    @FXML
    private PasswordField txtNew;
    @FXML
    private PasswordField txtRetype;
    @FXML
    private Label lbCurrentPassword;
    @FXML
    private Label lbNewPassword;
    @FXML
    private Label lbRetype;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void change(ActionEvent event) throws NamingException {
    	String jndiName = "infinity_server-ear/infinity_server-ejb/UserService!tn.esprit.infinity_server.interfaces.UserRemote";
		Context context = new InitialContext();
		UserRemote userProxy = (UserRemote) context.lookup(jndiName);
		Session.getUser().setPassword(txtNew.getText());
		User user = Session.getUser();
		user.setPassword(txtNew.getText());
		userProxy.updateUser(user);
		Notifications notificationBuilder = Notifications.create().title("")
				.text("Your Password is up to date ").darkStyle().graphic(null)
				.hideAfter(Duration.seconds(15)).position(Pos.BOTTOM_RIGHT);
		notificationBuilder.showConfirm();
		
    }
    //
    public boolean passwordContainOneNumber()
    {
    	char ch;
    	boolean numberFlag = false;
    	 for(int i=0;i < txtNew.getText().length();i++) {
             ch = txtNew.getText().charAt(i);
             if(Character.isDigit(ch)) 
            	 numberFlag = true;
    	 }
    	 return numberFlag;
    }
    //
    public boolean passwordContainCharMaj()
    {
    	char ch;
    	boolean capitalFlag = false;
    	 for(int i=0;i < txtNew.getText().length();i++) {
             ch = txtNew.getText().charAt(i);
             if(Character.isUpperCase(ch)) 
            	 capitalFlag = true;
    	 }
    	 return capitalFlag;
    }
    //
    public boolean passwordContainCharLower()
    {
    	char ch;
    	boolean lowerCaseFlag = false;
    	 for(int i=0;i < txtNew.getText().length();i++) {
             ch = txtNew.getText().charAt(i);
             if(Character.isLowerCase(ch))
            	 lowerCaseFlag = true;
    	 }
    	 return lowerCaseFlag;
    }
    //
    public boolean valideNewPasswordWithRetypePassword() {
		if (!txtNew.getText().equals(txtRetype.getText())) {
			return false;
		}
		return true;
	}
    //
    public boolean checkCurrentPassword()
    {
    	System.out.println(Session.getUser().getPassword());
    	return txtCurrentPass.getText().equals(Session.getUser().getPassword());
    }
    //
    public void checkNewPassword()
    {
    	if(!passwordContainOneNumber())
    	{
    		lbNewPassword.setStyle("-fx-text-fill: red;");
    		lbNewPassword.setText("At least one number");
    		return;
    	}
    	if(!passwordContainCharMaj())
    	{
    		lbNewPassword.setStyle("-fx-text-fill: red;");
    		lbNewPassword.setText("At least one uppercase Char");
    		return;
    	}
    	if(!passwordContainCharLower())
    	{
    		lbNewPassword.setStyle("-fx-text-fill: red;");
    		lbNewPassword.setText("At least one lowercase Char");
    		return;
    	}
    	if(txtNew.getText().length()<8)
    	{
    		lbNewPassword.setStyle("-fx-text-fill: red;");
    		lbNewPassword.setText("must containe at least 8 chararctere");
    		lbRetype.setText("");
    		return;
    	}
    	lbNewPassword.setStyle("-fx-text-fill: green;");
    	lbNewPassword.setText("✓");
    	checkRetypePassword();
    }
    //
    public void checkRetypePassword()
    {
    	if(!valideNewPasswordWithRetypePassword())
    	{
    		lbRetype.setStyle("-fx-text-fill: red;");
    		lbRetype.setText("Not the same as the new Password field");
    		return;
    	}
    	lbRetype.setStyle("-fx-text-fill: green;");
    	lbRetype.setText("✓");
    }
    public void checkPassword()
    {
    	if(!checkCurrentPassword())
    	{
    		lbCurrentPassword.setStyle("-fx-text-fill: red;");
    		lbCurrentPassword.setText("Check your current password");
    		txtNew.setDisable(true);
    		txtNew.setText("");
    		txtRetype.setDisable(true);
    		txtRetype.setText("");
    		lbNewPassword.setText("");
    		lbRetype.setText("");
    		return;
    	}
    	lbCurrentPassword.setStyle("-fx-text-fill: green;");
    	txtNew.setDisable(false);
		txtRetype.setDisable(false);
		lbCurrentPassword.setText("✓");
    }
  
    
}
