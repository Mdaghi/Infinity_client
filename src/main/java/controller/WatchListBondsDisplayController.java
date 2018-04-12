package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import Util.WatchListTechnical;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.esprit.infinity_server.interfaces.WatchListRemote;
import tn.esprit.infinity_server.interfaces.WatchListsBondsRemote;
import tn.esprit.infinity_server.persistence.WatchList;
import tn.esprit.infinity_server.persistence.WatchListBonds;

public class WatchListBondsDisplayController implements Initializable {

	  @FXML
	    private ListView<WatchListBonds> lvWatchListBonds;

	  @FXML
	    private AnchorPane container;

	    @FXML
	    private ToggleGroup grupoCatalogacao;

	    @FXML
	    private HBox hbCreateWatchList;

	    @FXML
	    private ToggleGroup grupoVisitantes11;

	    @FXML
	    private VBox boxVisitas;

	    @FXML
	    private ToggleButton btnEditProfile;

	    @FXML
	    private VBox boxLocalizacao;

	    @FXML
	    private VBox boxUtilitarios;

	    @FXML
	    private ToggleButton btnDesactivate;

	    @FXML
	    private ToggleGroup grupoVisitantes1;

	    @FXML
	    private VBox boxEmprestimo;

	    @FXML
	    private ToggleGroup grupoVisitantes111;

	    @FXML
	    private ToggleGroup grupoUtilidades1;

	    @FXML
	    private ToggleGroup grupoUtilidades2;

	    @FXML
	    private Label lbEmpty;

	    @FXML
	    private ToggleGroup menu;

	    @FXML
	    private ToggleGroup grupoVisitantes;

	    @FXML
	    private Label lbTitle;

	    @FXML
	    private ToggleButton BtnChangePassword;

	    @FXML
	    private Button BtnLogout;

	    @FXML
	    private VBox boxNotas;

	    @FXML
	    private ToggleGroup grupoMenus;


	    @FXML
	    private TextField txtSearch;

	    @FXML
	    private Label lbUser;

	    @FXML
	    private ToggleGroup grupoUtilidades;

	    @FXML
	    private VBox boxCatalogacao;
	    /************************************/
	    private ListView<WatchList> lvWatchList;
		Image imgPart = new Image("/fxml/img/information.png", 60, 60, true, true);
		Image imgDelete = new Image("/fxml/img/delete.png", 60, 60, true, true);

		ListCell cell;    
		ObservableList<WatchListBonds> watchLists = FXCollections.observableArrayList();
		
		  

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String jndiName="infinity_server-ear/infinity_server-ejb/WatchListsBondsService!tn.esprit.infinity_server.interfaces.WatchListsBondsRemote";
		Context context;
		try {
			context = new InitialContext();
			WatchListsBondsRemote proxy=(WatchListsBondsRemote)context.lookup(jndiName);
			
			/**************************List View**************************************/    
			List<WatchListBonds> watchlistBondsL =  proxy.readAllWatchlistsBondsUser(1);
			watchLists=FXCollections.observableArrayList(watchlistBondsL);


			if(!watchLists.isEmpty())
			{
				hbCreateWatchList.setVisible(false);
				lvWatchListBonds.setFocusModel(null);
				lvWatchListBonds.setItems(watchLists);
				/***********************************************/
				FilteredList<WatchListBonds> filteredWatchLists = new FilteredList<>(watchLists, e -> true);
				
				
				
				
				/**************************************************/
				lvWatchListBonds.setCellFactory(new Callback<ListView<WatchListBonds>, ListCell<WatchListBonds>>() {
					@Override
					public ListCell<WatchListBonds> call(ListView<WatchListBonds> param) {
						ListCell<WatchListBonds> cell=new ListCell<WatchListBonds>(){
							@Override
							protected void updateItem(WatchListBonds w, boolean bln) {
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
									vBox.setStyle("-fx-padding: 50 50 50 50;");

									VBox vBoxTwo = new VBox(5);
									vBoxTwo.setPrefHeight(240);
									vBoxTwo.setPrefWidth(300);
									vBoxTwo.setStyle("-fx-padding: 25 25 25 25;");

									VBox vBoxThree = new VBox(5);
									vBoxThree.setPrefHeight(240);
									vBoxThree.setPrefWidth(300);
									vBoxThree.setStyle("-fx-padding: 25 25 25 25;");

									VBox vBoxFour = new VBox(5);
									vBoxFour.setPrefHeight(240);
									vBoxFour.setPrefWidth(300);
									vBoxFour.setStyle("-fx-padding: 25 25 25 25;");


									HBox hBoxContainer = new HBox(4);
									hBoxContainer.setPrefHeight(240);
									hBoxContainer.setPrefWidth(300);
									hBoxContainer.setStyle("-fx-padding: 10;"+ "-fx-border-width: 2;" + "-fx-border-insets: 5;"+ "-fx-border-radius: 5;");

									Label name = new Label(w.getBond().getAddress());
									name.setStyle("-fx-font-weight: bold;-fx-font-size : 24 ;");

									Label description = new Label(w.getBond().getOwner());
									description.setStyle("-fx-font-weight: bold;");

									Label creationDate = new Label(w.getBond().getOwner());
									creationDate.setStyle("-fx-font-weight: bold;");

									Label id = new Label(String.valueOf(w.getBond().getOwner()));
									creationDate.setStyle("-fx-font-weight: bold;");

									final ImageView imageDisplay = new ImageView(imgPart);
									final ImageView imageRemove = new ImageView(imgDelete);

									imageDisplay.setOnMouseClicked(new EventHandler<MouseEvent>(){
										@Override
										public void handle(MouseEvent event) {
											     

											 FXMLLoader loader=new FXMLLoader();
												try {
												        
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
										

									});
									vBoxThree.getChildren().add(imageDisplay);
									vBoxFour.getChildren().add(imageRemove);
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
				lvWatchListBonds.setVisible(false);
			}


		} catch (NamingException e) {
			e.printStackTrace();
		}	

		
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

	    @FXML
	    void menuDashboard(ActionEvent event) {

	    }

	    @FXML
	    void editProfile(ActionEvent event) {

	    }

	    @FXML
	    void changePassword(ActionEvent event) {

	    }

	    @FXML
	    void desactivate(ActionEvent event) {

	    }

	    @FXML
	    void Logout(ActionEvent event) {

	    }
}
