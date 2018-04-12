
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Util.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.infinity_server.persistence.User;

/**
 * FXML Controller class
 *
 * @author Mdaghi
 */
public class TraderController implements Initializable {

    @FXML
    private ToggleGroup grupoMenus;
    @FXML
    private VBox boxCatalogacao;
    @FXML
    private ToggleGroup grupoCatalogacao;
    @FXML
    private VBox boxVisitas;
    @FXML
    private ToggleGroup grupoVisitantes;
    @FXML
    private VBox boxEmprestimo;
    @FXML
    private ToggleGroup grupoVisitantes1;
    @FXML
    private ToggleGroup grupoVisitantes11;
    @FXML
    private ToggleGroup grupoVisitantes111;
    @FXML
    private VBox boxLocalizacao;
    @FXML
    private VBox boxUtilitarios;
    @FXML
    private ToggleButton btnEditProfile;
    @FXML
    private ToggleGroup grupoUtilidades2;
    @FXML
    private ToggleButton BtnChangePassword;
    @FXML
    private ToggleGroup grupoUtilidades;
    @FXML
    private ToggleButton btnDesactivate;
    @FXML
    private ToggleGroup grupoUtilidades1;
    @FXML
    private Label lbUser;
    @FXML
    private Button BtnLogout;
    @FXML
    private AnchorPane container;
    @FXML
    private VBox boxNotas;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void menuDashboard(MouseEvent event) {
    }

    @FXML
    private void editProfile(ActionEvent event) {
    }

    @FXML
    private void changePassword(ActionEvent event) {
    }

    @FXML
    private void desactivate(ActionEvent event) {
    }

    @FXML
    private void Logout(ActionEvent event) throws IOException {
    	Session.setUser(new User());
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/view/Login.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		Stage primaryStage = (Stage) container.getScene().getWindow();
		primaryStage.close();
    }
    
}

