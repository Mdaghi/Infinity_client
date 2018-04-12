package controller;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.print.attribute.standard.DateTimeAtCompleted;

import org.controlsfx.control.Rating;

import com.jfoenix.controls.JFXComboBox;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import tn.esprit.infinity_server.interfaces.LikeRemote;
import tn.esprit.infinity_server.interfaces.RatingRemote;

public class BondOfferDashboardController implements Initializable {

	  @FXML
	    private AnchorPane container;

	    @FXML
	    private CategoryAxis xAxis;

	    @FXML
	    private ToggleGroup grupoCatalogacao;

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
	    private PieChart pieChart;

	    @FXML
	    private ToggleGroup grupoVisitantes111;

	    @FXML
	    private ToggleGroup grupoUtilidades1;

	    @FXML
	    private ToggleGroup grupoUtilidades2;

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
	    private NumberAxis yAxis;

	    @FXML
	    private ToggleGroup grupoMenus;

	    @FXML
	    private Label lbUser;

	    @FXML
	    private ToggleGroup grupoUtilidades;

	    @FXML
	    private VBox boxCatalogacao;

	    @FXML
	    private LineChart<Integer, Integer> LineChart;

	    @FXML
	    private JFXComboBox<String> cbPeriod;
	    
	    @FXML
	    private BarChart<Integer, Integer> barChart;

	    @FXML
	    private NumberAxis yBar;

	    @FXML
	    private CategoryAxis xBar;
	    

	    @FXML
	    private Rating rNote;

	    @FXML
	    private Label lbLikes;

	    @FXML
	    private Label lbDislikes;

	    @FXML
	    private Label lbNote;
	    
	    

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
	    public void setBond(String bond)
	    {
	    	lbTitle.setText(bond);
	    }

		@Override
		public void initialize(URL location, ResourceBundle resources) {
				lbTitle.setText("1");
				cbPeriod.getItems().add(0, "Last Week");
				cbPeriod.getItems().add(1, "Last Month");
				cbPeriod.getItems().add(2, "Since Ever");
				String jndiName="infinity_server-ear/infinity_server-ejb/LikeService!tn.esprit.infinity_server.interfaces.LikeRemote";
				  Context context;
				  try {
					context = new InitialContext();
					LikeRemote likeRemote=(LikeRemote) context.lookup(jndiName);
					Integer likes=likeRemote.readBondLikesDisLikes(Integer.parseInt(lbTitle.getText()), 1);
					Integer dislikes=likeRemote.readBondLikesDisLikes(Integer.parseInt(lbTitle.getText()), 2);
					/********Setting Likes & dislikes**********/
					lbLikes.setText(String.valueOf(likeRemote.readBondLikesDisLikes(Integer.parseInt(lbTitle.getText()), 1)));
					lbDislikes.setText(String.valueOf(likeRemote.readBondLikesDisLikes(Integer.parseInt(lbTitle.getText()), 2)));
					/********Setting Likes & dislikes**********/
					/******************PieChart***************/
					ObservableList<PieChart.Data> pieChartData =
				                FXCollections.observableArrayList(
				                new PieChart.Data("Likes", likes),
				                new PieChart.Data("Dislikes", dislikes));
					
					  pieChart.setData(pieChartData);
				      pieChart.setTitle("Likes vs Dislikes");
				      /*************LinChart*******************/
				      XYChart.Series likesSeries =	new XYChart.Series<>();
				      cbPeriod.valueProperty().addListener(new ChangeListener<String>() {
				          @Override public void changed(ObservableValue ov, String t, String t1) {				        	 
 
				        	  Date dNow =new Date();
				        	  
								if(t1.equals("Last Week"))
					              {

					            	  for(int i=0;i<7;i++)
					            	  {
					            		  Date d0=new Date();
					            		  Date d=new Date();
					            		  d.setDate(d0.getDate()-7);
					            		 
						            	   d.setDate(d.getDate()+i);
						            		 System.out.println(d.getDate());

						                   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						            	   Integer x=likeRemote.likesDate(1, dateFormat.format(d));						            	   
						            	   likesSeries.getData().add(new XYChart.Data(dateFormat.format(d),x));  
					            	  }
					            		 
					            	  LineChart.getData().addAll(likesSeries); 
					            	  
					              }
					              else if (t1.equals("Last Month"))
					              {
					            	  for(int i=0;i<30;i++)
					            	  {
					            		  /*Date d0=new Date();
					            		  Date d=new Date();
					            		  d.setDate(d0.getDate()-30);
					            		 
						            	   d.setDate(d.getDate()+i);
						            		 System.out.println(d.getDate());

						                   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						            	   Integer x=likeRemote.likesDate(1, dateFormat.format(d));						            	   
						            	   likesSeries.getData().add(new XYChart.Data(dateFormat.format(d),x));  
					            	  	*/
					            	  }
					            		 
					            	  LineChart.getData().addAll(likesSeries); 
					              }
					              else if(t1.equals("Since Ever"))
					              {
					            	  
					              }
				        	  
				        	 
				              
				          }

						 
				      });
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/****************Rating***************/	
				
				  String jndiName2="infinity_server-ear/infinity_server-ejb/RatingService!tn.esprit.infinity_server.interfaces.RatingRemote";
				  Context context2;
					try {
						context2 = new InitialContext();
						RatingRemote ratingRemote=(RatingRemote) context2.lookup(jndiName2); 
						int exist=ratingRemote.rated(2, Integer.parseInt(lbTitle.getText()));
						System.out.println(exist);
						rNote.ratingProperty().addListener(new ChangeListener<Number>() {
				            @Override public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
				              System.out.println("Rating : "+ t1.toString());
				              ratingRemote.addRating(2,1, Double.parseDouble(t1.toString()));
				            }
				        });
					} catch (NamingException e) {
						e.printStackTrace();
					}
			
		}

	

	
}
