package controller;


import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javafx.fxml.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import tn.esprit.infinity_server.interfaces.BondsDemandRemote;
import tn.esprit.infinity_server.interfaces.ClaimRemote;
import tn.esprit.infinity_server.persistence.Claim;
import tn.esprit.infinity_server.persistence.DemandeBond;
import tn.esprit.infinity_server.services.BondsDemande;
import tn.esprit.infinity_server.services.ClaimService;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.cell.PropertyValueFactory;

public class ComparateurController implements Initializable{

	
	
	
	 final static String simple = "Simple";
	    final static String compoundannually = "Cpd_Annually";
	    final static String semiannualy = "Cpd_SemiAnnually";
	    final static String quarterly = "Cpd_Quarterly";
	    final static String monthly = "Cpd_Monthly";
	    final static String daily = "Cpd_Daily";
	    
	    
	 @FXML
	    private TableColumn<?, ?> colCidade;

	    @FXML
	    private TableColumn<?, ?> colFax;

	    @FXML
	    private TextField couponvalue;

	    @FXML
	    private TableView<?> tbOrganizacao;

	    @FXML
	    private TableColumn<?, ?> colEstado;

	    @FXML
	    private DatePicker MatureDate;

	    @FXML
	    private TextField ineteretcoupon;

	    @FXML
	    private Button delete;
	    
	    @FXML
	    private AnchorPane anchorpane2;

	    @FXML
	    private TableColumn<?, ?> colEmail;

	    @FXML
	    private TableColumn<?, ?> colSigla;
	    
	    @FXML
	    private Button Chart;

	    @FXML
	    private TableColumn<?, ?> colLogradouro;

	    @FXML
	    private TextField txtPesquisar;

	    @FXML
	    private TableColumn<?, ?> colDescricao;

	    @FXML
	    private DatePicker SendDate;

	    @FXML
	    private TableColumn<?, ?> colId;

	    @FXML
	    private AnchorPane telaEdicao;

	    @FXML
	    private TableColumn<?, ?> colTelefone;

	    @FXML
	    private Label lblid1;
	    
	    

	    @FXML
	    private TextField futurevalue;

	    @FXML
	    private TableColumn<?, ?> colBairro;

	    @FXML
	    private TextField couponnumber;

	    @FXML
	    private ComboBox<String> periodBond;
	    @FXML
	    
	    private ComboBox<String> typeBond;
	    @FXML
	    private ToggleGroup menu;

	    @FXML
	    private TableColumn<?, ?> colNome;

	    @FXML
	    private TableColumn<?, ?> colPais;

	    @FXML
	    private TextField Price;

	    @FXML
	    private Label lbTitulo;

	    @FXML
	    private TextField interet;

	    @FXML
	    private Label labelperiod;
	    
	    
	    
	    
	    @Override
	  		public void initialize(URL location, ResourceBundle resources) {
	  	    	periodBond.setVisible(false);
	          	labelperiod.setVisible(false);
	  	    	futurevalue.setEditable(false);
	  	    	couponnumber.setEditable(false);
	  	    	couponvalue.setEditable(false);
	  	    	interet.setEditable(false);
	  	    	 typeBond.getItems().setAll("Simple", "Compound");
	  	    	 typeBond.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
	  	    	      @Override public void changed(ObservableValue<? extends String> selected, String oldFruit, String newFruit) {
	  	    	        if ("Compound".equals(typeBond.getSelectionModel().getSelectedItem())) {
	  	    	             periodBond.getItems().setAll("Annually","semiAnnually","Quarterly","Monthly","Daily");
	  	    	     periodBond.setVisible(true);
	  	    	     labelperiod.setVisible(true);
	  	    	          }
	  	    	        else{
	  	    	        	periodBond.setVisible(false);
	  	    	        	labelperiod.setVisible(false);
	  	    	        }
	  	    	      }
	  	    	 });
	  		}

	    
	    
	    
	    
	    @FXML
	    void deleteAction(ActionEvent event) {
	    	try{
	    	   
	    		 if(SendDate.getValue().isAfter(MatureDate.getValue()))
	             {
	              Alert alert =new Alert(Alert.AlertType.ERROR);
	              alert.setTitle("Mature date must be after Send date");
	              alert.setHeaderText(null);
	              alert.setContentText("choose a valid Send date and Mature date !");
	              alert.showAndWait();
	              return;
	             }
	             if(ineteretcoupon.getText().isEmpty()||Price.getText().isEmpty()||typeBond.getValue().isEmpty())
	             {
	                Alert alert =new Alert(Alert.AlertType.ERROR);
	              alert.setTitle("erreur");
	              alert.setHeaderText(null);
	              alert.setContentText("veuillez remplir tous les champs");
	              alert.showAndWait();
	              return; 
	             }
	   
 // simple Bond 
 	    	        if ("Simple".equals(typeBond.getSelectionModel().getSelectedItem())) {  
 	    	        	 //    lbl.setText(""+result);  
	    	             LocalDate senddate = SendDate.getValue();
	    	             LocalDate maturedate = MatureDate.getValue();
	    	             int dif=maturedate.getYear()-senddate.getYear();    
	    	             //double diff=Math.abs(dur.toMillis());
	    	             double result2 = dif/365;
 	    	        	 Double result = (Double.parseDouble(Price.getText().toString()))* (1+((Double.parseDouble(ineteretcoupon.getText().toString()))/100*dif));
 	    	             	    	        	
             couponnumber.setText(""+dif);   
             futurevalue.setText(""+result);
             Double result3 = (Double.parseDouble(futurevalue.getText().toString()))-(Double.parseDouble(Price.getText().toString()));
             interet.setText(""+result3);
             Double result4 = (Double.parseDouble(interet.getText().toString()))/(Double.parseDouble(couponnumber.getText().toString()));
             couponvalue.setText(""+result4);
             
 	    	       }
 	    	        
 	    /// compound Bond 
  	    	        else if("Compound".equals(typeBond.getSelectionModel().getSelectedItem())) {  
  	    	        	if("Annually".equals(periodBond.getSelectionModel().getSelectedItem())) {
  	    	        	  //    lbl.setText(""+result);  
  		    	             LocalDate senddate = SendDate.getValue();
  		    	             LocalDate maturedate = MatureDate.getValue();
  		    	             int dif=maturedate.getYear()-senddate.getYear();    
  		    	             //double diff=Math.abs(dur.toMillis());
  		    	             double result2 = dif/365; 
  	    	        		
  	    	        		Double result = (Double.parseDouble(Price.getText().toString()))* Math.pow((1+(Double.parseDouble(ineteretcoupon.getText().toString()))/100),dif);
	    	           	    	        	
            couponnumber.setText(""+dif);   
            futurevalue.setText(""+result);
            Double result3 = (Double.parseDouble(futurevalue.getText().toString()))-(Double.parseDouble(Price.getText().toString()));
            interet.setText(""+result3);
            Double result4 = (Double.parseDouble(interet.getText().toString()))/(Double.parseDouble(couponnumber.getText().toString()));
            couponvalue.setText(""+result4);
  	    	        	}
  	    	        	else if ("semiAnnually".equals(periodBond.getSelectionModel().getSelectedItem())) {
							
  		    	             LocalDate senddate = SendDate.getValue();
  		    	             LocalDate maturedate = MatureDate.getValue();
  		    	             int dif=maturedate.getYear()-senddate.getYear();    
  		    	             //double diff=Math.abs(dur.toMillis());
  		    	             double result2 = dif/365; 	
   	    	        		Double result = (Double.parseDouble(Price.getText().toString()))* Math.pow((1+(Double.parseDouble(ineteretcoupon.getText().toString()))/200),(dif*2));  		    	             //    lbl.setText(""+result);  

  	            couponnumber.setText(""+2*dif);   
  	            futurevalue.setText(""+result);
  	            Double result3 = (Double.parseDouble(futurevalue.getText().toString()))-(Double.parseDouble(Price.getText().toString()));
  	            interet.setText(""+result3);
  	            Double result4 = (Double.parseDouble(interet.getText().toString()))/(Double.parseDouble(couponnumber.getText().toString()));
  	            couponvalue.setText(""+result4);	    	      	
						}
  	    	        	else if ("Quarterly".equals(periodBond.getSelectionModel().getSelectedItem())) {
							
 		    	             LocalDate senddate = SendDate.getValue();
 		    	             LocalDate maturedate = MatureDate.getValue();
 		    	             int dif=maturedate.getYear()-senddate.getYear();    
 		    	             //double diff=Math.abs(dur.toMillis());
 		    	             double result2 = dif/365; 	
  	    	        		Double result = (Double.parseDouble(Price.getText().toString()))* Math.pow((1+(Double.parseDouble(ineteretcoupon.getText().toString()))/400),(dif*4));  		    	             //    lbl.setText(""+result);  

 	            couponnumber.setText(""+4*dif);   
 	            futurevalue.setText(""+result);
 	            Double result3 = (Double.parseDouble(futurevalue.getText().toString()))-(Double.parseDouble(Price.getText().toString()));
 	            interet.setText(""+result3);
 	            Double result4 = (Double.parseDouble(interet.getText().toString()))/(Double.parseDouble(couponnumber.getText().toString()));
 	            couponvalue.setText(""+result4);	    	      	
						}
  	    	        	else if ("Monthly".equals(periodBond.getSelectionModel().getSelectedItem())) {
							
 		    	             LocalDate senddate = SendDate.getValue();
 		    	             LocalDate maturedate = MatureDate.getValue();
 		    	             int dif=maturedate.getYear()-senddate.getYear();    
 		    	             //double diff=Math.abs(dur.toMillis());
 		    	             double result2 = dif/365; 	
  	    	        		Double result = (Double.parseDouble(Price.getText().toString()))* Math.pow((1+(Double.parseDouble(ineteretcoupon.getText().toString()))/1200),(dif*12));  		    	             //    lbl.setText(""+result);  

 	            couponnumber.setText(""+12*dif);   
 	            futurevalue.setText(""+result);
 	            Double result3 = (Double.parseDouble(futurevalue.getText().toString()))-(Double.parseDouble(Price.getText().toString()));
 	            interet.setText(""+result3);
 	            Double result4 = (Double.parseDouble(interet.getText().toString()))/(Double.parseDouble(couponnumber.getText().toString()));
 	            couponvalue.setText(""+result4);	    	      	
						}
  	    	        	else if ("Daily".equals(periodBond.getSelectionModel().getSelectedItem())) {
							
 		    	             LocalDate senddate = SendDate.getValue();
 		    	             LocalDate maturedate = MatureDate.getValue();
 		    	             int dif=maturedate.getYear()-senddate.getYear();    
 		    	             //double diff=Math.abs(dur.toMillis());
 		    	             double result2 = dif/365; 	
  	    	        		Double result = (Double.parseDouble(Price.getText().toString()))* Math.pow((1+(Double.parseDouble(ineteretcoupon.getText().toString()))/36500),(dif*365));  		    	             //    lbl.setText(""+result);  

 	            couponnumber.setText(""+365*dif);   
 	            futurevalue.setText(""+result);
 	            Double result3 = (Double.parseDouble(futurevalue.getText().toString()))-(Double.parseDouble(Price.getText().toString()));
 	            interet.setText(""+result3);
 	            Double result4 = (Double.parseDouble(interet.getText().toString()))/(Double.parseDouble(couponnumber.getText().toString()));
 	            couponvalue.setText(""+result4);	    	      	
						}
  	    	      }
 	    	      
  	    	 
             
             
	    	}catch(UnsupportedTemporalTypeException e)
	    
             {
            	e.printStackTrace(); 
             }
	    }
	  
	    
	    
	    @FXML
	    void chartAction(ActionEvent event) {
	    	Stage stage=new Stage();
	    	stage.setTitle("Bar Chart Sample");
	        final CategoryAxis xAxis = new CategoryAxis();
	        final NumberAxis yAxis = new NumberAxis();
	        final BarChart<String,Number> bc = 
	            new BarChart<>(xAxis,yAxis);
	        bc.setTitle("Bonds comparator");
	        xAxis.setLabel("type bonds");       
	        yAxis.setLabel("Value");
	 
	        LocalDate senddate = SendDate.getValue();
	        LocalDate maturedate = MatureDate.getValue();
	        int dif=maturedate.getYear()-senddate.getYear();    
	        //double diff=Math.abs(dur.toMillis());
	        double result2 = dif/365;
	        
	        XYChart.Series series1 = new XYChart.Series();
	        series1.setName("Furure Value");       
	        series1.getData().add(new XYChart.Data(simple, (Double.parseDouble(Price.getText().toString()))* (1+((Double.parseDouble(ineteretcoupon.getText().toString()))/100*dif))
	        		));
	        series1.getData().add(new XYChart.Data(compoundannually, (Double.parseDouble(Price.getText().toString()))* Math.pow((1+(Double.parseDouble(ineteretcoupon.getText().toString()))/100),dif)));
	        series1.getData().add(new XYChart.Data(semiannualy, (Double.parseDouble(Price.getText().toString()))* Math.pow((1+(Double.parseDouble(ineteretcoupon.getText().toString()))/200),dif*2)));
	        series1.getData().add(new XYChart.Data(quarterly, (Double.parseDouble(Price.getText().toString()))* Math.pow((1+(Double.parseDouble(ineteretcoupon.getText().toString()))/400),dif*4)));
	        series1.getData().add(new XYChart.Data(monthly, (Double.parseDouble(Price.getText().toString()))* Math.pow((1+(Double.parseDouble(ineteretcoupon.getText().toString()))/1200),dif*12)));      
	        series1.getData().add(new XYChart.Data(daily, (Double.parseDouble(Price.getText().toString()))* Math.pow((1+(Double.parseDouble(ineteretcoupon.getText().toString()))/36500),dif*365)));
	        XYChart.Series series2 = new XYChart.Series();
	        series2.setName("Interest");
	        series2.getData().add(new XYChart.Data(compoundannually, (Double.parseDouble(Price.getText().toString()))* Math.pow((1+(Double.parseDouble(ineteretcoupon.getText().toString()))/100),dif)-(Double.parseDouble(Price.getText().toString()))));
	        series2.getData().add(new XYChart.Data(semiannualy, (Double.parseDouble(Price.getText().toString()))* Math.pow((1+(Double.parseDouble(ineteretcoupon.getText().toString()))/200),dif*2)-(Double.parseDouble(Price.getText().toString()))));
	        series2.getData().add(new XYChart.Data(quarterly, (Double.parseDouble(Price.getText().toString()))* Math.pow((1+(Double.parseDouble(ineteretcoupon.getText().toString()))/400),dif*4)-(Double.parseDouble(Price.getText().toString()))));
	        series2.getData().add(new XYChart.Data(monthly, (Double.parseDouble(Price.getText().toString()))* Math.pow((1+(Double.parseDouble(ineteretcoupon.getText().toString()))/1200),dif*12)-(Double.parseDouble(Price.getText().toString()))));
	        series2.getData().add(new XYChart.Data(daily, (Double.parseDouble(Price.getText().toString()))* Math.pow((1+(Double.parseDouble(ineteretcoupon.getText().toString()))/36500),dif*365)-(Double.parseDouble(Price.getText().toString()))));
	        series2.getData().add(new XYChart.Data(simple, (Double.parseDouble(Price.getText().toString()))* (1+((Double.parseDouble(ineteretcoupon.getText().toString()))/100*dif))-(Double.parseDouble(Price.getText().toString()))));
	        
	        Scene scene  = new Scene(bc,600,700);
	        bc.getData().addAll(series1,series2);
	        stage.setScene(scene);
	        stage.show();

	    }
}
