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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;

import Util.SymboleTw;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import tn.esprit.infinity_server.interfaces.SymboleServiceRemote;
import tn.esprit.infinity_server.persistence.Symbole;
import tn.esprit.infinity_server.services.SymboleService;

/**
 * FXML Controller class
 *
 * @author Mdaghi
 */
public class SymboleController implements Initializable {

	@FXML
	private Label lbTitulo;
	@FXML
	private TextField txtSearch;
	@FXML
	private AnchorPane telaEdicao;
	@FXML
	private TableView<SymboleTw> twSymbole;
	@FXML
	private TableColumn<SymboleTw, Integer> colId;
	@FXML
	private TableColumn<SymboleTw, String> colSymbole;
	@FXML
	private TableColumn<SymboleTw, java.lang.String> colURL;
	@FXML
	private TableColumn<SymboleTw, Button> colDelete;
	@FXML
	private Button btnAdd;

	ObservableList<SymboleTw> symboleObser = FXCollections.observableArrayList();

	SymboleServiceRemote symboleProxy;

	private static Timeline dataTimer = new Timeline();

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		dataTimer = new Timeline(new KeyFrame(Duration.seconds(0.1), (ActionEvent event) -> {
			try {
				symboleObser.clear();
				twSymbole.setItems(symboleObser);
				buildData();
			} catch (Exception e) {
				Logger.getLogger(SymboleService.class.getName()).log(Level.WARNING, " build data :" + e);
			}
		}));
		dataTimer.play();
	}

	@FXML
	private void searchAction(KeyEvent event) throws NamingException {
		symboleObser.clear();
		twSymbole.setItems(symboleObser);
		if (txtSearch.getText().equals(""))
			buildData();
		else {
			//
			colId.setCellValueFactory(new PropertyValueFactory<SymboleTw, Integer>("id"));
			colSymbole.setCellValueFactory(new PropertyValueFactory<SymboleTw, String>("symbole"));
			colURL.setCellValueFactory(new PropertyValueFactory<SymboleTw, java.lang.String>("url"));
			colDelete.setCellValueFactory(new PropertyValueFactory<SymboleTw, Button>("delete"));
			List<Symbole> lst = symboleProxy.searchDynamiqueSymbole(txtSearch.getText());
			for (Symbole symbole : lst) {
				SymboleTw symboleTw = new SymboleTw();
				symboleTw.setId(symbole.getId());
				symboleTw.setSymbole(symbole.getName());
				JFXButton btnDelete = new JFXButton("DELETE");
				btnDelete.setOnAction((ActionEvent e) -> {
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Remove");
						alert.setHeaderText("Delete this Symbole");
						alert.setContentText("Are you sure ?");
						Optional<ButtonType> result = alert.showAndWait();
						if ( result.isPresent() && result.get() == ButtonType.OK) {
							symboleProxy.removeSymbole(symbole.getId());
							Notifications notificationBuilder = Notifications.create().title("").text("Symbole deleted")
									.darkStyle().graphic(null).hideAfter(Duration.seconds(10))
									.position(Pos.BOTTOM_RIGHT);
							notificationBuilder.showConfirm();
							try {
								symboleObser.clear();
								twSymbole.setItems(symboleObser);
								buildData();
							} catch (NamingException ex) {
								Logger.getLogger(SymboleService.class.getName()).log(Level.WARNING,
										" find All list :" + ex);
							}
						}
				});
				btnDelete.setStyle("-fx-background-color:#D9534F;-fx-text-fill:white");
				symboleTw.setDelete(btnDelete);
				symboleObser.add(symboleTw);
			}
			twSymbole.setItems(symboleObser);
		}
	}

	@FXML
	private void add(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/view/AddSymbole.fxml"));
		Parent sceneMain = loader.load();
		//
		Stage st = new Stage();
		st.setScene(new Scene(sceneMain));
		st.initOwner((Stage) btnAdd.getScene().getWindow());
		st.initModality(Modality.WINDOW_MODAL);
		st.show();
	}

	public void buildData() throws NamingException {
		String jndiName = "infinity_server-ear/infinity_server-ejb/SymboleService!tn.esprit.infinity_server.interfaces.SymboleServiceRemote";
		Context context = new InitialContext();
		symboleProxy = (SymboleServiceRemote) context.lookup(jndiName);
		//
		colId.setCellValueFactory(new PropertyValueFactory<SymboleTw, Integer>("id"));
		colSymbole.setCellValueFactory(new PropertyValueFactory<SymboleTw, String>("symbole"));
		colURL.setCellValueFactory(new PropertyValueFactory<SymboleTw, java.lang.String>("url"));
		colDelete.setCellValueFactory(new PropertyValueFactory<SymboleTw, Button>("delete"));
		List<Symbole> lst = symboleProxy.findAllSymbole();
		for (Symbole symbole : lst) {
			SymboleTw symboleTw = new SymboleTw();
			symboleTw.setId(symbole.getId());
			symboleTw.setSymbole(symbole.getName());
			symboleTw.setUrl(symbole.getUrl());
			JFXButton btnDelete = new JFXButton("Delete");
			btnDelete.setOnAction((ActionEvent event) -> {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Delete");
					alert.setHeaderText("Delete this Symbole");
					alert.setContentText("Are you sure ?");
					Optional<ButtonType> result = alert.showAndWait();
						if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
							symboleProxy.removeSymbole(symbole.getId());
							Notifications notificationBuilder = Notifications.create().title("").text("Symbole deleted")
									.darkStyle().graphic(null).hideAfter(Duration.seconds(10))
									.position(Pos.BOTTOM_RIGHT);
							notificationBuilder.showConfirm();
							try {
								symboleObser.clear();
								twSymbole.setItems(symboleObser);
								buildData();
							} catch (NamingException e) {
								Logger.getLogger(SymboleService.class.getName()).log(Level.WARNING,
										" find All list :" + e);
							}
						}
			});
			btnDelete.setStyle("-fx-background-color:#D9534F;-fx-text-fill:white");
			symboleTw.setDelete(btnDelete);
			symboleObser.add(symboleTw);
		}
		twSymbole.setItems(symboleObser);
	}

	public static Timeline getDataTimer() {
		return dataTimer;
	}

	public static void setDataTimer(Timeline dataTimer) {
		SymboleController.dataTimer = dataTimer;
	}

}
