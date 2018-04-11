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

import Util.FutureContractTw;
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
import tn.esprit.infinity_server.interfaces.FutureContractServiceRemote;
import tn.esprit.infinity_server.persistence.FutureContract;
import tn.esprit.infinity_server.services.SymboleService;

/**
 * FXML Controller class
 *
 * @author Mdaghi
 */
public class FutureContractController implements Initializable {

	@FXML
	private Label lbTitulo;
	@FXML
	private TextField txtSearch;
	@FXML
	private AnchorPane telaEdicao;
	@FXML
	private TableView<FutureContractTw> twFuture;
	@FXML
	private TableColumn<FutureContractTw, Integer> colId;
	@FXML
	private TableColumn<FutureContractTw, String> colName;
	@FXML
	private TableColumn<FutureContractTw, Double> colPrice;
	@FXML
	private TableColumn<FutureContractTw, java.util.Date> colDate;
	@FXML
	private TableColumn<FutureContractTw, Integer> colSize;
	@FXML
	private TableColumn<FutureContractTw, Button> colDelete;
	@FXML
	private TableColumn<FutureContractTw, Button> colUpdate;
	@FXML
	private TableColumn<FutureContractTw, Button> colSpeculation;
	@FXML
	private Button btnAdd;

	FutureContractServiceRemote futureContractProxy;

	ObservableList<FutureContractTw> futureObser = FXCollections.observableArrayList();
	
	private static Timeline dataTimer = new Timeline();

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		dataTimer = new Timeline(new KeyFrame(Duration.seconds(0.1), (ActionEvent event) -> {
			try {
				futureObser.clear();
				twFuture.setItems(futureObser);
				buildData();
			} catch (Exception e) {
				Logger.getLogger(SymboleService.class.getName()).log(Level.WARNING, " build data :" + e);
			}
		}));
		dataTimer.play();
	}

	@FXML
	private void searchAction(KeyEvent event) throws NamingException {
		/****/
		futureObser.clear();
		twFuture.setItems(futureObser);
		if (txtSearch.getText().equals(""))
			buildData();
		else {
			//
			colId.setCellValueFactory(new PropertyValueFactory<FutureContractTw, Integer>("id"));
			colName.setCellValueFactory(new PropertyValueFactory<FutureContractTw, String>("symbol"));
			colPrice.setCellValueFactory(new PropertyValueFactory<FutureContractTw, Double>("maturityPrice"));
			colDate.setCellValueFactory(new PropertyValueFactory<FutureContractTw, java.util.Date>("dateMaturite"));
			colSize.setCellValueFactory(new PropertyValueFactory<FutureContractTw, Integer>("size"));
			colDelete.setCellValueFactory(new PropertyValueFactory<FutureContractTw, Button>("delete"));
			colUpdate.setCellValueFactory(new PropertyValueFactory<FutureContractTw, Button>("update"));
			colSpeculation.setCellValueFactory(new PropertyValueFactory<FutureContractTw, Button>("speculation"));
			String jndiName = "infinity_server-ear/infinity_server-ejb/FutureContractService!tn.esprit.infinity_server.interfaces.FutureContractServiceRemote";
			Context context = new InitialContext();
			futureContractProxy = (FutureContractServiceRemote) context.lookup(jndiName);
			List<FutureContract> lst = futureContractProxy.searchDynamiqueFutureContract(txtSearch.getText());
			for (FutureContract futureContract : lst) {
				FutureContractTw futureContractTw = new FutureContractTw();
				futureContractTw.setId(futureContract.getId());
				futureContractTw.setSymbol(futureContract.getName());
				futureContractTw.setDateMaturite(futureContract.getDateMaturite());
				futureContractTw.setMaturityPrice(futureContract.getMaturityPrice());
				futureContractTw.setSize(futureContract.getSize());
				/**************/
				JFXButton btnDelete = new JFXButton("Delete");
				btnDelete.setOnAction((ActionEvent event1) -> {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Delete");
					alert.setHeaderText("Delete this Contract");
					alert.setContentText("Are you sure ?");
					Optional<ButtonType> result = alert.showAndWait();
					if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
						futureContractProxy.removeFutureContract(futureContract.getId());
						Notifications notificationBuilder = Notifications.create().title("").text("Contract deleted")
								.darkStyle().graphic(null).hideAfter(Duration.seconds(10)).position(Pos.BOTTOM_RIGHT);
						notificationBuilder.showConfirm();
						try {
							futureObser.clear();
							twFuture.setItems(futureObser);
							buildData();
						} catch (NamingException e) {
							Logger.getLogger(SymboleService.class.getName()).log(Level.WARNING, " find All list :" + e);
						}
					}
				});
				btnDelete.setStyle("-fx-background-color:#D9534F;-fx-text-fill:white");
				futureContractTw.setDelete(btnDelete);
				/*********************/
				JFXButton btnUpdate = new JFXButton("Update");
				btnUpdate.setOnAction((ActionEvent event2) -> {
					try {
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/view/UpdateFuture.fxml"));
						Parent sceneMain;
						sceneMain = loader.load();
						Stage st = new Stage();
						st.setScene(new Scene(sceneMain));
						st.initOwner((Stage) btnAdd.getScene().getWindow());
						st.initModality(Modality.WINDOW_MODAL);
						st.show();
					} catch (Exception e) {
						Logger.getLogger(SymboleService.class.getName()).log(Level.WARNING, " Load :" + e);
					}
					//
				});
				btnUpdate.setStyle("-fx-background-color:#eea236;-fx-text-fill:white");
				futureContractTw.setUpdate(btnUpdate);
				/**********************/
				JFXButton btnSpeculation = new JFXButton("speculation");
				btnSpeculation.setOnAction((ActionEvent event3) -> {
					try {
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/view/AddFuture.fxml"));
						Parent sceneMain;
						sceneMain = loader.load();
						Stage st = new Stage();
						st.setScene(new Scene(sceneMain));
						st.initOwner((Stage) btnAdd.getScene().getWindow());
						st.initModality(Modality.WINDOW_MODAL);
						st.show();
					} catch (Exception e) {
						Logger.getLogger(SymboleService.class.getName()).log(Level.WARNING, " Load :" + e);
					}
					//
				});
				btnSpeculation.setStyle("-fx-background-color:#2e6da4;-fx-text-fill:white");
				futureContractTw.setSpeculation(btnSpeculation);
				/***************/
				futureObser.add(futureContractTw);
			}
			twFuture.setItems(futureObser);
		}
	}

	@FXML
	private void add(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/view/AddFuture.fxml"));
		Parent sceneMain = loader.load();
		//
		Stage st = new Stage();
		st.setScene(new Scene(sceneMain));
		st.initOwner((Stage) btnAdd.getScene().getWindow());
		st.initModality(Modality.WINDOW_MODAL);
		st.show();
	}

	public void buildData() throws NamingException {
		/****/
		String jndiName = "infinity_server-ear/infinity_server-ejb/FutureContractService!tn.esprit.infinity_server.interfaces.FutureContractServiceRemote";
		Context context = new InitialContext();
		futureContractProxy = (FutureContractServiceRemote) context.lookup(jndiName);
		/****/
		colId.setCellValueFactory(new PropertyValueFactory<FutureContractTw, Integer>("id"));
		colName.setCellValueFactory(new PropertyValueFactory<FutureContractTw, String>("symbol"));
		colPrice.setCellValueFactory(new PropertyValueFactory<FutureContractTw, Double>("maturityPrice"));
		colDate.setCellValueFactory(new PropertyValueFactory<FutureContractTw, java.util.Date>("dateMaturite"));
		colSize.setCellValueFactory(new PropertyValueFactory<FutureContractTw, Integer>("size"));
		colDelete.setCellValueFactory(new PropertyValueFactory<FutureContractTw, Button>("delete"));
		colUpdate.setCellValueFactory(new PropertyValueFactory<FutureContractTw, Button>("update"));
		colSpeculation.setCellValueFactory(new PropertyValueFactory<FutureContractTw, Button>("speculation"));
		List<FutureContract> lst = futureContractProxy.findAllFutureContract();
		for (FutureContract futureContract : lst) {
			FutureContractTw futureContractTw = new FutureContractTw();
			futureContractTw.setId(futureContract.getId());
			futureContractTw.setSymbol(futureContract.getName());
			futureContractTw.setDateMaturite(futureContract.getDateMaturite());
			futureContractTw.setMaturityPrice(futureContract.getMaturityPrice());
			futureContractTw.setSize(futureContract.getSize());
			/**************/
			JFXButton btnDelete = new JFXButton("Delete");
			btnDelete.setOnAction((ActionEvent event) -> {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Delete");
				alert.setHeaderText("Delete this Contract");
				alert.setContentText("Are you sure ?");
				Optional<ButtonType> result = alert.showAndWait();
				if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
					futureContractProxy.removeFutureContract(futureContract.getId());
					Notifications notificationBuilder = Notifications.create().title("").text("Contract deleted")
							.darkStyle().graphic(null).hideAfter(Duration.seconds(10)).position(Pos.BOTTOM_RIGHT);
					notificationBuilder.showConfirm();
					try {
						futureObser.clear();
						twFuture.setItems(futureObser);
						buildData();
					} catch (NamingException e) {
						Logger.getLogger(SymboleService.class.getName()).log(Level.WARNING, " find All list :" + e);
					}
				}
			});
			btnDelete.setStyle("-fx-background-color:#D9534F;-fx-text-fill:white");
			futureContractTw.setDelete(btnDelete);
			/*********************/
			JFXButton btnUpdate = new JFXButton("Update");
			btnUpdate.setOnAction((ActionEvent event) -> {
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/view/UpdateFuture.fxml"));
					Parent sceneMain;
					sceneMain = loader.load();
					Stage st = new Stage();
					st.setScene(new Scene(sceneMain));
					st.initOwner((Stage) btnAdd.getScene().getWindow());
					st.initModality(Modality.WINDOW_MODAL);
					st.show();
				} catch (Exception e) {
					Logger.getLogger(SymboleService.class.getName()).log(Level.WARNING, " Load :" + e);
				}
				//
			});
			btnUpdate.setStyle("-fx-background-color:#eea236;-fx-text-fill:white");
			futureContractTw.setUpdate(btnUpdate);
			/**********************/
			JFXButton btnSpeculation = new JFXButton("speculation");
			btnSpeculation.setOnAction((ActionEvent event) -> {
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/view/AddFuture.fxml"));
					Parent sceneMain;
					sceneMain = loader.load();
					Stage st = new Stage();
					st.setScene(new Scene(sceneMain));
					st.initOwner((Stage) btnAdd.getScene().getWindow());
					st.initModality(Modality.WINDOW_MODAL);
					st.show();
				} catch (Exception e) {
					Logger.getLogger(SymboleService.class.getName()).log(Level.WARNING, " Load :" + e);
				}
				//
			});
			btnSpeculation.setStyle("-fx-background-color:#2e6da4;-fx-text-fill:white");
			futureContractTw.setSpeculation(btnSpeculation);
			/***************/
			futureObser.add(futureContractTw);
		}
		twFuture.setItems(futureObser);

	}
	
	public static Timeline getDataTimer() {
		return dataTimer;
	}

	public static void setDataTimer(Timeline dataTimer) {
		FutureContractController.dataTimer = dataTimer;
	}

}
