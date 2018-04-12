package controller;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.jfoenix.controls.JFXButton;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.esprit.infinity_server.interfaces.BondsOffers;
import tn.esprit.infinity_server.interfaces.CommentRemote;
import tn.esprit.infinity_server.interfaces.LikeRemote;
import tn.esprit.infinity_server.interfaces.WatchListsBondsRemote;
import tn.esprit.infinity_server.persistence.Client;
import tn.esprit.infinity_server.persistence.Comment;
import tn.esprit.infinity_server.persistence.CommentPk;
import tn.esprit.infinity_server.persistence.Commentaire;
import tn.esprit.infinity_server.persistence.LikePk;
import tn.esprit.infinity_server.persistence.Likes;
import tn.esprit.infinity_server.persistence.WatchList;
import tn.esprit.infinity_server.persistence.WatchListBonds;
import tn.esprit.infinity_server.persistence.WatchlistsBond;

public class WatchListBondOfferController implements Initializable{

    @FXML
    private ListView<Commentaire> lvComments;
    @FXML
    private GridPane telaCadastro;

    @FXML
    private Label lbPrintDate;

    @FXML
    private ToggleGroup grupoCatalogacao;

    @FXML
    private Label dislikesLab;

    @FXML
    private Label lbSeries;

    @FXML
    private JFXButton dislike;

    @FXML
    private Label lbSecurityNumber;

    @FXML
    private Label lbPrintPlace;

    @FXML
    private Label lbAdress;

    @FXML
    private Label lbInterestRate;

    @FXML
    private VBox sideBarVbox;

    @FXML
    private ToggleGroup grupoVisitantes11;

    @FXML
    private Label lbFrequencyRate;

    @FXML
    private VBox boxVisitas;

    @FXML
    private ToggleButton btnEditProfile;

    @FXML
    private Label lbDenomination;

    @FXML
    private VBox boxLocalizacao;

    @FXML
    private VBox boxUtilitarios;

    @FXML
    private ToggleButton btnDesactivate;

    @FXML
    private Label lbIssueDate;

    @FXML
    private Hyperlink hlFeedbacks;

    @FXML
    private ToggleGroup grupoVisitantes1;

    @FXML
    private Hyperlink linkDetails;

    @FXML
    private VBox boxEmprestimo;

    @FXML
    private ToggleGroup grupoVisitantes111;

    @FXML
    private JFXButton like;

    @FXML
    private Label lbCoupoonRate;

    @FXML
    private ToggleGroup grupoUtilidades1;

    @FXML
    private ToggleGroup grupoUtilidades2;

    @FXML
    private JFXButton follow2;
    @FXML
    private JFXButton StatisticsButton;

    @FXML
    private ToggleGroup grupoVisitantes;

    @FXML
    private GridPane telaCadastro1;

    @FXML
    private Label lbLikesBetween;

    @FXML
    private Label lbTitle;

    @FXML
    private Label lbOwner;

    @FXML
    private ToggleButton BtnChangePassword;

    @FXML
    private Button BtnLogout;

    @FXML
    private VBox boxNotas;

    @FXML
    private ToggleGroup grupoMenus;

    @FXML
    private Label lbMaturityDate;

    @FXML
    private Label likesLab;

    @FXML
    private ComboBox<String> cbLikes;

    @FXML
    private Label lbUser;

    @FXML
    private ToggleGroup grupoUtilidades;

    @FXML
    private VBox boxCatalogacao;

    @FXML
    private TreeTableView<Commentaire> tvComments;
    
    @FXML
    private TreeTableColumn<Commentaire, Integer> tcIdClient=new TreeTableColumn<>("ID Client");
    @FXML
    private TreeTableColumn<Commentaire, String> tcMessage=new TreeTableColumn<>("Message");
    @FXML
    private TreeTableColumn<Commentaire, String> tcCommentDate=new TreeTableColumn<>("Date of Comment");
    
    int counter=0;
    Image imgPart = new Image("/fxml/img/checkmark.png", 25	, 25, true, true);
	Image imgDelete = new Image("/fxml/img/delete.png", 25, 25, true, true);
	Image imgComment = new Image("/fxml/img/Comments.png", 25, 25, true, true);
    @FXML
    private ListView<WatchlistsBond> lvWatchLists;
    ListCell cell;    
	ObservableList<WatchlistsBond> watchLists = FXCollections.observableArrayList();


/*******************************/
	ObservableList<Commentaire> comments = FXCollections.observableArrayList();

    
    
    @FXML
    void follow(ActionEvent event) {
    	
    	String jndiName="infinity_server-ear/infinity_server-ejb/WatchListsBondsService!tn.esprit.infinity_server.interfaces.WatchListsBondsRemote";
		Context context;
		try {
			context = new InitialContext();
			WatchListsBondsRemote proxy=(WatchListsBondsRemote)context.lookup(jndiName);
			List<WatchlistsBond> watchListL =  proxy.readWatchListsBond(1, 2);
			watchLists=FXCollections.observableArrayList(watchListL);
			for(int i=0;i<watchListL.size();i++)
			{
				if(watchListL.get(i).getIdBond()==null)
					watchListL.get(i).setIdBond(-1);
					
			}
			if(watchLists.size()!=0)
			{
			
    			lvWatchLists.setFocusModel(null);
    			lvWatchLists.setItems(watchLists);
				 lvWatchLists.setVisible(true);

    			lvWatchLists.setCellFactory(new Callback<ListView<WatchlistsBond>, ListCell<WatchlistsBond>>() {
					
    				@Override
					public ListCell<WatchlistsBond> call(ListView<WatchlistsBond> param) {
						ListCell<WatchlistsBond> cell=new ListCell<WatchlistsBond>(){
							@Override
							protected void updateItem(WatchlistsBond w, boolean bln) {
								super.updateItem(w, bln);
								if(w!=null)
								{
									HBox hBox = new HBox(3);
									hBox.setPrefHeight(50);
									hBox.setPrefWidth(500);
									hBox.setStyle("");
									VBox vBox = new VBox(1);
									vBox.setPrefHeight(40);
									vBox.setPrefWidth(500);
									vBox.setStyle("-fx-padding: 5 5 5 5;");

									VBox vBoxTwo = new VBox(1);
									vBoxTwo.setPrefHeight(40);
									vBoxTwo.setPrefWidth(345);
									vBoxTwo.setStyle("-fx-padding: 5 5 5 5;");
									
									VBox vBoxThree = new VBox(1);
									vBoxThree.setPrefHeight(40);
									vBoxThree.setPrefWidth(70);
									vBoxThree.setStyle("-fx-padding: 5 5 5 5;");
									
									VBox vBoxFour = new VBox(1);
									vBoxFour.setPrefHeight(40);
									vBoxFour.setPrefWidth(70);
									vBoxFour.setStyle("-fx-padding: 5 5 5 5;");
									
									final ImageView imageDisplay = new ImageView(imgPart);
									final ImageView imageRemove = new ImageView(imgDelete);
									


									

									Label name = new Label(w.getName());
									name.setStyle("-fx-font-weight: bold;-fx-font-size : 24 ;");
									Label id = new Label(String.valueOf(w.getId()));
									id.setStyle("-fx-font-weight: bold;-fx-font-size : 24 ;");
									
									Label idBond = new Label(String.valueOf(w.getIdBond()));
									idBond.setStyle("-fx-font-weight: bold;-fx-font-size : 24 ;");
									if(w.getIdBond()==-1 )
										vBoxThree.getChildren().add(imageRemove);
									else
										vBoxThree.getChildren().add(imageDisplay);
									
									
									vBoxThree.setOnMouseClicked(new EventHandler<MouseEvent>(){
										@Override
										public void handle(MouseEvent event) {
											if(w.getIdBond()==-1 )
												
												 proxy.createWatchListsBonds(1, w.getId());
											else
											 proxy.deleteWatchListsBonds(1, w.getId());
											 lvWatchLists.setVisible(false);
											 counter++;
												System.out.println(w.getIdBond()+"   "+counter);

										}

									});
									
									
									vBox.getChildren().add(id);
									vBoxTwo.getChildren().add(name);
									vBoxFour.getChildren().add(idBond);
									hBox.getChildren().addAll(vBoxFour,vBoxTwo,vBox,vBoxThree);
									hBox.getChildren().get(0).setVisible(false);
									hBox.getChildren().get(2).setVisible(false);

									setGraphic(hBox);
										

								}
								else
									setGraphic(null);
							}
						};
						return cell;
					}
				});

			}
			

			
		}catch(NamingException e){
			e.printStackTrace();
		}
		
		counter++;
    	if(counter%2==1)
    	//lvWatchLists. toFront();
		 lvWatchLists.setVisible(true);
    	else
		 lvWatchLists.setVisible(false);
        	
        	
    	
		
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		String jndiName="infinity_server-ear/infinity_server-ejb/WatchListsBondsService!tn.esprit.infinity_server.interfaces.WatchListsBondsRemote";
		Context context;
		try {
			context = new InitialContext();
			WatchListsBondsRemote proxy=(WatchListsBondsRemote)context.lookup(jndiName);
			tn.esprit.infinity_server.persistence.BondsOffers bond=proxy.readBondOffer(1);
			lbPrintDate.setText(bond.getPrintDate().toString());
			lbIssueDate.setText(bond.getIssueDate().toString());
			lbMaturityDate.setText(bond.getMaturityDate().toString());
			lbDenomination.setText(bond.getDenomination().toString());
			lbCoupoonRate.setText(bond.getCouponRate().toString());
			lbFrequencyRate.setText(bond.getFrequencyRate());
			lbInterestRate.setText(bond.getInterestRate());
			lbPrintPlace.setText(bond.getPrintPlace());
			lbOwner.setText(bond.getOwner());
			lbSecurityNumber.setText(bond.getSecurityNumber().toString());
			lbSeries.setText(bond.getSeries());
			lbTitle.setText(String.valueOf(bond.getId()));
			lbAdress.setText(bond.getAddress());
			  String jndiName1="infinity_server-ear/infinity_server-ejb/LikeService!tn.esprit.infinity_server.interfaces.LikeRemote";
			  Context context1;
			  context1 = new InitialContext();
			  LikeRemote likeRemote=(LikeRemote) context.lookup(jndiName1);
			  
			likesLab.setText(likesLab.getText()+likeRemote.readBondLikesDisLikes(1,1));
			dislikesLab.setText(dislikesLab.getText()+likeRemote.readBondLikesDisLikes(1,2));
			 LikePk like=new LikePk();
			 like.setIdBond(Integer.parseInt(lbTitle.getText()));
			 like.setIdClient(2);
			Integer result=likeRemote.likedBondClient(like);
			if(result==2)
				this.dislike.setVisible(false);
			else if(result==1)
				this.like.setVisible(false);
		}catch(NamingException e){
			e.printStackTrace();
		}
		
		
		/****************Visibility***********************/
    	lvWatchLists. toBack();
    	lvWatchLists.setVisible(false);
    	//tvComments.setVisible(false);

    	/******************Comments***************/
    	String jndiName1="infinity_server-ear/infinity_server-ejb/CommentService!tn.esprit.infinity_server.interfaces.CommentRemote";
		  Context context1;
			try {
				context1 = new InitialContext();
				CommentRemote likeRemote=(CommentRemote) context1.lookup(jndiName1);
			    List<Commentaire> comment=likeRemote.readAllCommentBond(2);
			    
		    	   
			   
			    comments=FXCollections.observableArrayList(comment);
			lvComments.setItems(comments);

	    	lvComments.setCellFactory(new Callback<ListView<Commentaire>, ListCell<Commentaire>>() {
				@Override
				public ListCell<Commentaire> call(ListView<Commentaire> param) {
					ListCell<Commentaire> cell=new ListCell<Commentaire>(){
						@Override
						protected void updateItem(Commentaire w, boolean bln) {
							super.updateItem(w, bln);
							if(w!=null)
							{
								HBox hBox = new HBox(1);
								hBox.setPrefHeight(240);
								hBox.setPrefWidth(500);
								hBox.setStyle("");

								

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

								Label name = new Label(w.getIdClient().toString());
								name.setStyle("-fx-font-weight: bold;-fx-font-size : 24 ;");

								Label description = new Label(w.getMessage());
								description.setStyle("-fx-font-weight: bold;");

								Label creationDate = new Label(w.getCommentDate().toString());
								creationDate.setStyle("-fx-font-weight: bold;");

								

								final ImageView imageSubComment = new ImageView(imgComment);
								final ImageView imageRemove = new ImageView(imgDelete);

								imageSubComment.setOnMouseClicked(new EventHandler<MouseEvent>(){
									
									@Override
									public void handle(MouseEvent event) {
										System.out.println("3asbaa");
										CommentPk pk=new CommentPk();
								        pk.setIdClient(w.getIdClient());
								        pk.setIdBondOffer(w.getIdBondOffer());
								        pk.setCommentDate(w.getCommentDate());
								        System.out.println(pk.getIdClient()+" "+pk.getIdBondOffer()+" "+pk.getCommentDate());

										List<Commentaire> c=likeRemote.readAllSubCommentsBond(pk);
										for(Commentaire x:c)
										{
											System.out.println(x);
										}
										/*************************************/
										
										Stage stage=new Stage();
								    	Scene scene = new Scene(new Group());
								        stage.setTitle("Sub Comments");
								        stage.setWidth(500);
								        stage.setHeight(500);
								        								       
								        //lvSubComments
								      
										/**********************************/
									}
									
								});



								imageRemove.setOnMousePressed(e->{
									

								});
								//vBoxThree.getChildren().add(imageDisplay);
								vBoxFour.getChildren().add(imageSubComment);
								vBoxTwo.getChildren().add(creationDate);
								vBoxThree.getChildren().add(description);
								vBox.getChildren().add(name);
								hBoxContainer.getChildren().addAll(vBox,vBoxTwo,vBoxThree,vBoxFour);
								setGraphic(hBoxContainer);

							}
							else
								setGraphic(null);
						}
					};
					return cell;
				}
			});
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			



    	
	}
	
	  @FXML
	    void like(ActionEvent event) throws NamingException {
		  String jndiName="infinity_server-ear/infinity_server-ejb/LikeService!tn.esprit.infinity_server.interfaces.LikeRemote";
		  Context context;
		  context = new InitialContext();
		  LikeRemote likeRemote=(LikeRemote) context.lookup(jndiName);
		  LikePk likePk1=new LikePk();
		  likePk1.setIdBond(Integer.parseInt(lbTitle.getText()));
		  likePk1.setIdClient(2);
		  Integer result=likeRemote.likedBondClient(likePk1);

		  Likes like=new Likes();
		  LikePk likePk=new LikePk();
		  tn.esprit.infinity_server.persistence.BondsOffers b=new tn.esprit.infinity_server.persistence.BondsOffers();
		  likePk.setIdBond(Integer.parseInt(lbTitle.getText()));
		  likePk.setIdClient(2);
		  like.setLikePk(likePk);
		  like.setLove(1);
		  if(result==0)
			  likeRemote.LikeDislike(like);
		  else
			  likeRemote.UpdateLikeDislike(like);
		  this.like.setVisible(false);
		  this.dislike.setVisible(true);
		  likesLab.setText("Total Likes:"+likeRemote.readBondLikesDisLikes(1,1));

	    }

	    @FXML
	    void Dislike(ActionEvent event) throws NamingException {
			  String jndiName="infinity_server-ear/infinity_server-ejb/LikeService!tn.esprit.infinity_server.interfaces.LikeRemote";
			  Context context;
			  context = new InitialContext();
			  LikeRemote likeRemote=(LikeRemote) context.lookup(jndiName);
			  LikePk likePk1=new LikePk();
			  likePk1.setIdBond(Integer.parseInt(lbTitle.getText()));
			  likePk1.setIdClient(2);
			  Integer result=likeRemote.likedBondClient(likePk1);
			  Likes like=new Likes();
			  LikePk likePk=new LikePk();
			  tn.esprit.infinity_server.persistence.BondsOffers b=new tn.esprit.infinity_server.persistence.BondsOffers();
			  likePk.setIdBond(Integer.parseInt(lbTitle.getText()));
			  likePk.setIdClient(2);
			  like.setLikePk(likePk);
			  like.setLove(2);
			  System.out.println(result);
			  if(result==0)
				  likeRemote.LikeDislike(like);
			  else
				  likeRemote.UpdateLikeDislike(like);

			  this.dislike.setVisible(false);
			  this.like.setVisible(true);
			  dislikesLab.setText("");
			  dislikesLab.setText("Total Dislikes:"+likeRemote.readBondLikesDisLikes(1,2));
	    }
	    
	    @FXML
	    void showComments(ActionEvent event) {
	    	tvComments.setVisible(true);
	    	String jndiName1="infinity_server-ear/infinity_server-ejb/CommentService!tn.esprit.infinity_server.interfaces.CommentRemote";
			  Context context1;
			  try {
				context1 = new InitialContext();
				CommentRemote likeRemote=(CommentRemote) context1.lookup(jndiName1);
			    TreeTableView<Commentaire> Commentaires=new TreeTableView<>();
			    List<Commentaire> comment=likeRemote.readAllCommentBond(2);
			    
		    	   
			   
			    comments=FXCollections.observableArrayList(comment);
			   
			    /******************************/
			    //System.out.println(comment.size());
			    	   TreeItem<Commentaire> item = new TreeItem();
			    	  

			    		for(int i=0;i<comment.size();i++){
			   					System.out.println("*****"+i+"*****");
			    				System.out.println(comment.get(i));
			    				/*********************************/
					    	   TreeItem<Commentaire> root = new TreeItem<>(comment.get(i));
					    	   CommentPk pk=new CommentPk();
					    	   pk.setIdBondOffer(comment.get(i).getIdBondOffer());
					    	   pk.setIdClient(comment.get(i).getIdClient());
					    	   pk.setCommentDate(comment.get(i).getCommentDate());
					    	   System.out.println(pk);
							    List<Commentaire> subComments=likeRemote.readAllSubCommentsBond(pk);
							    for(int j=0;j<subComments.size();j++){
							    	
					    			System.out.println("/************subComments*******/");
					    			System.out.println(subComments.get(j));
							    	   item=new TreeItem<>(subComments.get(j));
						    		   root.getChildren().add(item);
						    		   for(TreeItem<Commentaire> c:root.getChildren())
						    		   {
						    			   System.out.println(c);
						    		   }
					    		}
							    System.out.println("/******************/\n"+root.getValue()+root.getChildren().get(0)+"\n/************************/");
						    	   tvComments.setRoot(root);


			    		}
			    	   
			    	  
			    	   
			    	   
			    	   tcIdClient.setCellValueFactory(
				               (TreeTableColumn.CellDataFeatures<Commentaire, Integer> param) -> 
				               new SimpleIntegerProperty(param.getValue().getValue().getIdClient()).asObject()
				           );
				    	   tcMessage.setCellValueFactory(
				               (TreeTableColumn.CellDataFeatures<Commentaire, String> param) -> 
				               new SimpleStringProperty(param.getValue().getValue().getMessage())
				           );
				           tcCommentDate.setCellValueFactory(
				        		   	(TreeTableColumn.CellDataFeatures<Commentaire, String> param) -> 
						               new SimpleStringProperty(param.getValue().getValue().getCommentDate().toString())
						               );

				        			     
			    
			    
			    /********************************/
			    
			  } catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
	    	
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
	    

	    @FXML
	    void statistics(ActionEvent event) throws IOException {
	    	/*FXMLLoader loader=new FXMLLoader();
	    	Parent parent = loader.load(getClass().getResourceAsStream("/fxml/view/BondOfferDashboard.fxml"));       
	        Scene scene = new Scene(parent);*/
	       
	    	Parent parent = FXMLLoader.load(getClass().getResource("/fxml/view/BondOfferDashboard.fxml"));       
	        Scene scene = new Scene(parent);
	        //BondOfferDashboardController d= (BondOfferDashboardController) loader.getController();
	        //d.setBond(lbTitle.getText());
	    	Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        app_stage.setScene(scene);
	        app_stage.setTitle("BondOffer Statistics");
	        app_stage.show();
	    }
	    
	    @FXML
	    void feedBacks(ActionEvent event) throws IOException {
	    	
	    }
	    

}