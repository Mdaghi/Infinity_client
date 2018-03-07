package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.sun.corba.se.spi.ior.Identifiable;

import Util.WatchListTechnical;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.esprit.infinity_server.interfaces.WatchListRemote;
import tn.esprit.infinity_server.persistence.WatchList;
import javafx.event.ActionEvent;
import javafx.event.*;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;



public class WatchListDisplayController implements Initializable {
	/***************************/
	@FXML
	private TextField txtSearch;

	@FXML
	private Button btSalvar;

	@FXML
	private Label lbTitulo;

	@FXML
	private ToggleGroup menu;

	@FXML
	private HBox hbCreateWatchList;

	@FXML
	private ListView<WatchList> lvWatchList;
	Image imgPart = new Image("/fxml/img/information.png", 60, 60, true, true);
	Image imgDelete = new Image("/fxml/img/nota-erro.png", 60, 60, true, true);

	ListCell cell;    
	ObservableList<WatchList> watchLists = FXCollections.observableArrayList();

	@FXML
	void salvar(ActionEvent event) {

	}

	@FXML
	void telaCadastro(ActionEvent event) {

	}

	@FXML
	void telaEdicao(ActionEvent event) {

	}

	@FXML
	void telaExcluir(ActionEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources)  {
		String jndiName="infinity_server-ear/infinity_server-ejb/WatchListService!tn.esprit.infinity_server.interfaces.WatchListRemote";
		Context context;
		try {
			context = new InitialContext();
			WatchListRemote proxy=(WatchListRemote)context.lookup(jndiName);
			proxy.readAllWatchlistsUser(1);
			/***************************Search********************************/
			txtSearch.setOnKeyPressed(e->{
				watchLists=FXCollections.observableArrayList(proxy.searchWatchList(txtSearch.getText(), 1));
				lvWatchList.setItems(watchLists);

				lvWatchList.setCellFactory(new Callback<ListView<WatchList>, ListCell<WatchList>>() {
					@Override
					public ListCell<WatchList> call(ListView<WatchList> param) {
						ListCell<WatchList> cell=new ListCell<WatchList>(){
							@Override
							protected void updateItem(WatchList w, boolean bln) {
								super.updateItem(w, bln);
								if(w!=null)
								{
									HBox hBox = new HBox(1);
									hBox.setPrefHeight(240);
									hBox.setPrefWidth(500);
									hBox.setStyle("");

									HBox hBoxSecond = new HBox(1);
									hBoxSecond.setPrefHeight(240);
									hBoxSecond.setPrefWidth(500);
									hBoxSecond.setStyle("");

									HBox hBoxThird = new HBox(1);
									hBoxThird.setPrefHeight(240);
									hBoxThird.setPrefWidth(500);
									hBoxThird.setStyle("");

									HBox hBoxFourth = new HBox(1);
									hBoxFourth.setPrefHeight(240);
									hBoxFourth.setPrefWidth(500);
									hBoxFourth.setStyle("");

									VBox vBox = new VBox(5);
									vBox.setPrefHeight(240);
									vBox.setPrefWidth(300);

									VBox vBoxTwo = new VBox(5);
									vBoxTwo.setPrefHeight(240);
									vBoxTwo.setPrefWidth(300);

									VBox vBoxThree = new VBox(5);
									vBoxThree.setPrefHeight(240);
									vBoxThree.setPrefWidth(300);

									VBox vBoxFour = new VBox(5);
									vBoxFour.setPrefHeight(240);
									vBoxFour.setPrefWidth(300);


									HBox hBoxContainer = new HBox(4);
									hBoxContainer.setPrefHeight(240);
									hBoxContainer.setPrefWidth(300);
									hBoxContainer.setStyle("-fx-padding: 10;" 
											+ "-fx-border-width: 2;" + "-fx-border-insets: 5;"
											+ "-fx-border-radius: 5;");

									Label name = new Label(w.getName());
									name.setStyle("-fx-font-weight: bold;-fx-font-size : 24 ;");

									Label description = new Label(w.getDescription());
									description.setStyle("-fx-font-weight: bold;");

									Label creationDate = new Label(w.getCreationDate().toString());
									creationDate.setStyle("-fx-font-weight: bold;");

									Label id = new Label(String.valueOf(w.getId()));
									creationDate.setStyle("-fx-font-weight: bold;");

									final ImageView imageDisplay = new ImageView(imgPart);
									final ImageView imageRemove = new ImageView(imgDelete);

									imageDisplay.setOnMouseClicked(new EventHandler<MouseEvent>(){
										@Override
										public void handle(MouseEvent event) {
											FXMLLoader loader=new FXMLLoader();
											try {

												Parent parent = loader.load(getClass().getResourceAsStream("/fxml/view/watchListDetails.fxml"));       
												Scene scene = new Scene(parent);

												WatchListDetailsController detailsController= (WatchListDetailsController) loader.getController();
												//detailsController.initData(w);
												System.out.println(w);
												Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
												app_stage.setScene(scene);

												app_stage.show();  
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}       





										}

									});



									imageRemove.setOnMousePressed(e->{
										String jndiName="infinity_server-ear/infinity_server-ejb/WatchListService!tn.esprit.infinity_server.interfaces.WatchListRemote";
										Context context;

										Date date = new Date();
										try {
											context = new InitialContext();
											WatchListRemote proxy=(WatchListRemote)context.lookup(jndiName);
											try{

												proxy.deleteWatchList(w.getId());
												lvWatchList.refresh();
											}catch(NumberFormatException ex){ // handle your exception
												ex.printStackTrace();
											}

										} catch (Exception e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}

									});

									vBoxThree.getChildren().add(imageDisplay);
									vBoxThree.getChildren().add(imageRemove);

									hBoxSecond.getChildren().add(description);
									hBoxThird.getChildren().add(creationDate);
									hBoxFourth.getChildren().add(id);
									vBoxTwo.getChildren().add(name);
									vBox.getChildren().addAll(hBoxSecond,hBoxThird,hBoxFourth);
									vBox.getChildren().get(2).setVisible(false);
									hBoxContainer.getChildren().addAll(vBoxTwo,vBox,vBoxThree,vBoxFour);
									setGraphic(hBoxContainer);

								}
								else
									setGraphic(null);
							}
						};
						return cell;
					}
				});
			});


			/**************************List View**************************************/    
			List<WatchList> watchlistL =  proxy.readAllWatchlistsUser(1);
			watchLists=FXCollections.observableArrayList(watchlistL);


			if(watchLists.size()!=0)
			{
				hbCreateWatchList.setVisible(false);
				lvWatchList.setFocusModel(null);
				lvWatchList.setItems(watchLists);
				lvWatchList.setCellFactory(new Callback<ListView<WatchList>, ListCell<WatchList>>() {
					@Override
					public ListCell<WatchList> call(ListView<WatchList> param) {
						ListCell<WatchList> cell=new ListCell<WatchList>(){
							@Override
							protected void updateItem(WatchList w, boolean bln) {
								super.updateItem(w, bln);
								if(w!=null)
								{
									HBox hBox = new HBox(1);
									hBox.setPrefHeight(240);
									hBox.setPrefWidth(500);
									hBox.setStyle("");

									HBox hBoxSecond = new HBox(1);
									hBoxSecond.setPrefHeight(240);
									hBoxSecond.setPrefWidth(500);
									hBoxSecond.setStyle("");

									HBox hBoxThird = new HBox(1);
									hBoxThird.setPrefHeight(240);
									hBoxThird.setPrefWidth(500);
									hBoxThird.setStyle("");

									HBox hBoxFourth = new HBox(1);
									hBoxFourth.setPrefHeight(240);
									hBoxFourth.setPrefWidth(500);
									hBoxFourth.setStyle("");

									VBox vBox = new VBox(5);
									vBox.setPrefHeight(240);
									vBox.setPrefWidth(300);

									VBox vBoxTwo = new VBox(5);
									vBoxTwo.setPrefHeight(240);
									vBoxTwo.setPrefWidth(300);

									VBox vBoxThree = new VBox(5);
									vBoxThree.setPrefHeight(240);
									vBoxThree.setPrefWidth(300);

									VBox vBoxFour = new VBox(5);
									vBoxFour.setPrefHeight(240);
									vBoxFour.setPrefWidth(300);


									HBox hBoxContainer = new HBox(4);
									hBoxContainer.setPrefHeight(240);
									hBoxContainer.setPrefWidth(300);
									hBoxContainer.setStyle("-fx-padding: 10;" 
											+ "-fx-border-width: 2;" + "-fx-border-insets: 5;"
											+ "-fx-border-radius: 5;");

									Label name = new Label(w.getName());
									name.setStyle("-fx-font-weight: bold;-fx-font-size : 24 ;");

									Label description = new Label(w.getDescription());
									description.setStyle("-fx-font-weight: bold;");

									Label creationDate = new Label(w.getCreationDate().toString());
									creationDate.setStyle("-fx-font-weight: bold;");

									Label id = new Label(String.valueOf(w.getId()));
									creationDate.setStyle("-fx-font-weight: bold;");

									final ImageView imageDisplay = new ImageView(imgPart);
									final ImageView imageRemove = new ImageView(imgDelete);

									imageDisplay.setOnMouseClicked(new EventHandler<MouseEvent>(){
										@Override
										public void handle(MouseEvent event) {
											     

											 FXMLLoader loader=new FXMLLoader();
												try {
													 WatchListTechnical.setWatchList(w);
												        
												        Parent parent = loader.load(getClass().getResource("/fxml/view/watchListDetails.fxml"));       
												        Scene scene = new Scene(parent);
												        WatchListDetailsController d= (WatchListDetailsController) loader.getController();
												       
												        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
												        app_stage.setScene(scene);
												        app_stage.show();
												
												} catch (IOException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}       
										                 



										}

									});



									imageRemove.setOnMousePressed(e->{
										String jndiName="infinity_server-ear/infinity_server-ejb/WatchListService!tn.esprit.infinity_server.interfaces.WatchListRemote";
										Context context;

										Date date = new Date();
										try {
											context = new InitialContext();
											WatchListRemote proxy=(WatchListRemote)context.lookup(jndiName);
											try{

												proxy.deleteWatchList(w.getId());
												lvWatchList.refresh();
											}catch(NumberFormatException ex){ // handle your exception
												ex.printStackTrace();
											}

										} catch (Exception e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}

									});

									vBoxThree.getChildren().add(imageDisplay);
									vBoxThree.getChildren().add(imageRemove);

									hBoxSecond.getChildren().add(description);
									hBoxThird.getChildren().add(creationDate);
									hBoxFourth.getChildren().add(id);
									vBoxTwo.getChildren().add(name);
									vBox.getChildren().addAll(hBoxSecond,hBoxThird,hBoxFourth);
									vBox.getChildren().get(2).setVisible(false);
									hBoxContainer.getChildren().addAll(vBoxTwo,vBox,vBoxThree,vBoxFour);
									setGraphic(hBoxContainer);

								}
								else
									setGraphic(null);
							}
						};
						return cell;
					}
				});

			}
			else
			{
				hbCreateWatchList.setVisible(true);
				lvWatchList.setVisible(false);
			}


		} catch (NamingException e) {
			e.printStackTrace();
		}	

	}


}