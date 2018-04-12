package controller;

import javafx.scene.paint.Color;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.*;
import javafx.scene.Group;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import tn.esprit.infinity_server.interfaces.AffectationRemote;
import tn.esprit.infinity_server.interfaces.BondsDemandRemote;
import tn.esprit.infinity_server.persistence.Affectation;
import tn.esprit.infinity_server.persistence.BondsOffers;
import tn.esprit.infinity_server.persistence.DemandeBond;
import tn.esprit.infinity_server.services.BondsDemande;
import tn.esprit.infinity_server.services.BondsOffersServices;
import javafx.scene.control.*;

public class DemandeAffectationController implements Initializable {
	
	LocalDate datenow;

	@FXML
	private Button btnupdate;
    @FXML
    private Button statdemand;
    
	 @FXML
	private Label lblid1;
	 
	 @FXML
		private Label lblida1;
	 
    @FXML
    private Button add;

    @FXML
    private DatePicker MatureDate;
    
    @FXML
    private DatePicker MatureDate1;

    @FXML
    private TextField Price;
    
    @FXML
    private TextField Price1;

    @FXML
    private TextField ineteretcoupon;
    
    @FXML
    private TextField ineteretcoupon1;

    @FXML
    private DatePicker SendDate;
    
    @FXML
    private DatePicker SendDate1;
    @FXML
    private TableView<DemandeBond> tableViewDemand;
    
    @FXML
    private TableView<BondsOffers> tableViewDemand1;

    @FXML
    private TableColumn<DemandeBond, String > columndate1;

    @FXML
    private TableColumn<DemandeBond, String> columndate2;
    
    @FXML
    private TableColumn<?, ?> statusoffer;
    
    @FXML
    private TableColumn<DemandeBond, String> columndate0;

    @FXML
    private TableColumn<DemandeBond, String> columncoupon;
    
    @FXML
    private TableColumn<BondsOffers, String > columndate11;
    
    
    @FXML
    private TableView<Affectation> tableViewDemand1000;
    @FXML
    private Button offersstats;
    
   
    @FXML
    private TableColumn<Affectation, String > columndate100;

    @FXML
    private TableColumn<Affectation, String> columndate101;
    
    @FXML
    private TableColumn<Affectation, String> columndate102;

    @FXML
    private TableColumn<Affectation, String> columncoupon103;
    
    @FXML
    private TableColumn<BondsOffers, String > columndate104;
    
    @FXML
    private TableColumn<BondsOffers, String > columnprice105;
    
    
    @FXML
    private TableColumn<?, ?> statusdemand;

    @FXML
    private TableColumn<BondsOffers, String> columndate21;
    
    @FXML
    private TableColumn<BondsOffers, String> columndate01;

    @FXML
    private TableColumn<BondsOffers, String> columncoupon1;
    
    @FXML
    private TableColumn<BondsOffers, String> columnprice1;
    
    @FXML
    private Button btndelete;
    
    @FXML
    private Button affecter;
    

    @FXML
    private TextField txtPesquisar;
    
    @FXML
    private TableColumn<DemandeBond, String> columnprice;

    DemandeBond olSouvenirs=new DemandeBond();   
    private ObservableList<DemandeBond>souvs;
    BondsDemande ss=new BondsDemande();
    DemandeBond new_souv=new DemandeBond();
    BondsDemandRemote proxy;
    private ObservableList<DemandeBond> zz;
    
    private ObservableList<Affectation>souvs3;
    
    BondsOffers olSouvenirs1=new BondsOffers();   
    private ObservableList<BondsOffers>souvs1;
    BondsOffersServices ss1=new BondsOffersServices();
    BondsOffers new_souv1=new BondsOffers();
    tn.esprit.infinity_server.interfaces.BondsOffers proxy1;
    private ObservableList<BondsOffers> zz1;
    
    AffectationRemote  proxy2;
    AffectationRemote proxy3;
    
    
    //  les demandes ********************
  
    @Override
	public void initialize(URL url, ResourceBundle rb) {
    	afficher();
    	getDemande();
    	getDemande1();
    	lblid1.setVisible(false);
    	afficher1();
    	afficher3();
    	lblida1.setVisible(false);
    	
	}
  
    
    public void afficher(){
    	try {
        	String jndiName = "infinity_server-ear/infinity_server-ejb/BondsDemande!tn.esprit.infinity_server.interfaces.BondsDemandRemote";
        	Context context = new InitialContext();
        	proxy = (BondsDemandRemote) context.lookup(jndiName);
        	
            columndate0.setCellValueFactory(new PropertyValueFactory("dateDemande"));
            columndate1.setCellValueFactory(new PropertyValueFactory("DateEmission"));
            columndate2.setCellValueFactory(new PropertyValueFactory("DateMaturite"));
            columnprice.setCellValueFactory(new PropertyValueFactory("Prix"));
            columncoupon.setCellValueFactory(new PropertyValueFactory("TauxCoupon"));
            statusdemand.setCellValueFactory(new PropertyValueFactory("Statutbond"));
          List<DemandeBond> lst=proxy.getDemandeBond();  
         souvs=FXCollections.observableArrayList(lst);    
         tableViewDemand.setItems(souvs);  
       //sakly
         
         tableViewDemand.setRowFactory(lv -> new TableRow<DemandeBond>() {
             @Override
             protected void updateItem(DemandeBond c, boolean empty) {
                 super.updateItem(c, empty);
                 if (empty) {
                     setText(null);
                     setStyle("");
                 } else {

              	   if(c.getPrix()<=100000)

              	   {
                         setStyle("-fx-background-color: #F2D4D7");

              	   }
              	   else if(c.getPrix()>=10000 & c.getPrix()<=499000)
              	   {
                         setStyle("-fx-background-color: #05C5DE");

              	   }
              	   else if(c.getPrix()>=500000)
              	   {
                         setStyle("-fx-background-color: #B56DEF");

              	   }
              	  
                         
                     
                 }
             }
         });
         
         //sakly
    		} catch (NamingException e) {
    		
    			e.printStackTrace();
    		}
    }
    
    
    
    public void getDemande() {
        tableViewDemand.setOnMousePressed(new EventHandler<MouseEvent>()
        		{
        			 @Override
        	            public void handle(MouseEvent event) {
        	                DemandeBond demandeBond= (DemandeBond) souvs.get(tableViewDemand.getSelectionModel().getSelectedIndex());
        	                lblid1.setText(String.valueOf(demandeBond.getId()));
        	                SendDate.setValue(LocalDate.of(demandeBond.getDateEmission().getYear()+1900, demandeBond.getDateEmission().getMonth(), demandeBond.getDateEmission().getDayOfMonth()));
        	                MatureDate.setValue(LocalDate.of(demandeBond.getDateMaturite().getYear()+1900, demandeBond.getDateMaturite().getMonth(), demandeBond.getDateMaturite().getDayOfMonth()));
        	                Price.setText(String.valueOf(demandeBond.getPrix()));
        	                ineteretcoupon.setText(String.valueOf(demandeBond.getTauxCoupon()));
        	               datenow=demandeBond.getDateDemande();
        	            
        	            }
        		});
        		
   }
  
	public void clear(){
		
		SendDate.setValue(null);
        MatureDate.setValue(null);
        Price.clear();
        ineteretcoupon.clear();
	}
	
	
	// les offres *********************
	
	    
	    public void afficher1(){
	    	try {
	        	String jndiName = "infinity_server-ear/infinity_server-ejb/BondsOffersServices!tn.esprit.infinity_server.interfaces.BondsOffers";
	        	Context context = new InitialContext();
	        	proxy1 = (tn.esprit.infinity_server.interfaces.BondsOffers) context.lookup(jndiName);
	        	columndate01.setCellValueFactory(new PropertyValueFactory("printDate"));
	            columndate11.setCellValueFactory(new PropertyValueFactory("issueDate"));
	            columndate21.setCellValueFactory(new PropertyValueFactory("maturityDate"));
	            columnprice1.setCellValueFactory(new PropertyValueFactory("denomination"));
	            columncoupon1.setCellValueFactory(new PropertyValueFactory("couponRate"));
	            statusoffer.setCellValueFactory(new PropertyValueFactory("statusOffer"));
	          List<BondsOffers> lst1=proxy1.findAllBondsOffers();  
	         souvs1=FXCollections.observableArrayList(lst1);    
	         tableViewDemand1.setItems(souvs1);  
	         
	         //sakly
	         
	         tableViewDemand1.setRowFactory(lv -> new TableRow<BondsOffers>() {
                 @Override
                 protected void updateItem(BondsOffers c, boolean empty) {
                     super.updateItem(c, empty);
                     if (empty) {
                         setText(null);
                         setStyle("");
                     } else {

                  	   if(c.getDenomination()<=100000)

                  	   {
                             setStyle("-fx-background-color: #F2D4D7");

                  	   }
                  	   else if(c.getDenomination()>=10000 & c.getDenomination()<=499000)
                  	   {
                             setStyle("-fx-background-color: #05C5DE");

                  	   }
                  	   else if(c.getDenomination()>=500000)
                  	   {
                             setStyle("-fx-background-color: #B56DEF");

                  	   }
                  	  
                             
                         
                     }
                 }
             });
	         
	         //sakly
	    		} catch (NamingException e) {
	    		
	    			e.printStackTrace();
	    		}
	    }
	    
	    
	    
	    public void getDemande1() {
	        tableViewDemand1.setOnMousePressed(new EventHandler<MouseEvent>()
	        		{
	        			 @Override
	        	            public void handle(MouseEvent event) {
	        	                BondsOffers offerBond= (BondsOffers) souvs1.get(tableViewDemand1.getSelectionModel().getSelectedIndex());
	        	                lblida1.setText(String.valueOf(offerBond.getId()));
	        	                SendDate1.setValue(LocalDate.of(offerBond.getIssueDate().getYear()+1900, offerBond.getIssueDate().getMonth(), offerBond.getIssueDate().getDay()));
	        	                MatureDate1.setValue(LocalDate.of(offerBond.getMaturityDate().getYear()+1900, offerBond.getMaturityDate().getMonth(), offerBond.getMaturityDate().getDay()));
	        	                Price1.setText(String.valueOf(offerBond.getDenomination()));
	        	                ineteretcoupon1.setText(String.valueOf(offerBond.getCouponRate()));
	        	              
	        	            
	        	            }
	        		});
	        		
	   }
	  
		public void clear1(){
			
			SendDate1.setValue(null);
	        MatureDate1.setValue(null);
	        Price1.clear();
	        ineteretcoupon1.clear();
		}
		
		

	
	 
	

	
	
	// affectation **********************
	   @FXML
	    void affecterAction(ActionEvent event) throws NamingException {

			String jndiName = "infinity_server-ear/infinity_server-ejb/AffectationService!tn.esprit.infinity_server.interfaces.AffectationRemote";
        	Context context = new InitialContext();
        	proxy2 = (AffectationRemote) context.lookup(jndiName);
        	Affectation bondsoffer = new Affectation();
        	int offid=Integer.parseInt(lblida1.getText());
        	int demid=Integer.parseInt(lblid1.getText());
        	double price=Double.parseDouble(Price.getText());
            LocalDate send =SendDate.getValue();
            LocalDate mature =MatureDate.getValue();
            LocalDate now =LocalDate.now();
           if((Math.abs(((Double.parseDouble(Price.getText()))-(Double.parseDouble(Price1.getText())))*100)/(Double.parseDouble(Price.getText())))>=25)
            	//if((Double.parseDouble(Price.getText()))>=100)
            {
            	Alert alert =new Alert(Alert.AlertType.ERROR);
	              alert.setTitle("dont accept more than 10% difference ");
	              alert.setHeaderText(null);
	              alert.setContentText("not tolerated more than 20% between offer and demand price !");
	              alert.showAndWait();
	              return;
	
            }
            else{
            double coupon=Double.parseDouble(ineteretcoupon.getText());
           proxy2.ajouterAffectation(offid, demid, now, send, mature, price, coupon);
           //update
           String jndiName2 = "infinity_server-ear/infinity_server-ejb/BondsDemande!tn.esprit.infinity_server.interfaces.BondsDemandRemote";
       	Context context2 = new InitialContext();
       	proxy = (BondsDemandRemote) context2.lookup(jndiName2);
           
           
         	DemandeBond d=proxy.getdemandebond(demid);
   		  d.setStatutbond("affected");
   		String jndiName4 = "infinity_server-ear/infinity_server-ejb/BondsOffersServices!tn.esprit.infinity_server.interfaces.BondsOffers";
    	Context context4 = new InitialContext();
    	tn.esprit.infinity_server.interfaces.BondsOffers proxy1 = (tn.esprit.infinity_server.interfaces.BondsOffers) context4.lookup(jndiName4);
    	
    	
    	
    	
       	BondsOffers d1=proxy1.getofferbond(offid);
 		  d1.setStatusOffer("affected");
 		
   		
          try {
              proxy.updateDemande(d);
              proxy1.updateBondsOffers(d1);
               afficher();
               afficher1();
                
                 
          } catch (Exception ex) {
              System.err.println(ex.getMessage());
          }
          
       
          
          
   		  
   		  

   	    
   	
           
           //fin update
           
           
           
           
           afficher3();
            clear();
	    }
	   }
	   
	   
	   public void afficher3(){
	    	try {
	        	String jndiName = "infinity_server-ear/infinity_server-ejb/AffectationService!tn.esprit.infinity_server.interfaces.AffectationRemote";
	        	Context context = new InitialContext();
	        	 proxy3 = (AffectationRemote) context.lookup(jndiName);
	        	
	        	columndate100.setCellValueFactory(new PropertyValueFactory("dateBond"));
	        	columndate102.setCellValueFactory(new PropertyValueFactory("issueDate"));
	            columndate101.setCellValueFactory(new PropertyValueFactory("matureDate"));
	            columnprice105.setCellValueFactory(new PropertyValueFactory("price"));
	            columncoupon103.setCellValueFactory(new PropertyValueFactory("couponRate"));
	            List<Affectation> lst5=proxy3.getAffectation();  
		         souvs3=FXCollections.observableArrayList(lst5);  		         
		         tableViewDemand1000.setItems(souvs3); 
	            
	            ///sakly
		         tableViewDemand1000.setRowFactory(lv -> new TableRow<Affectation>() {
	                   @Override
	                   protected void updateItem(Affectation c, boolean empty) {
	                       super.updateItem(c, empty);
	                       if (empty) {
	                           setText(null);
	                           setStyle("");
	                       } else {

	                    	   if(c.getPrice()<=100000)

	                    	   {
	                               setStyle("-fx-background-color: #F2D4D7");

	                    	   }
	                    	   else if(c.getPrice()>=10000 & c.getPrice()<=499000)
	                    	   {
	                               setStyle("-fx-background-color: #05C5DE");

	                    	   }
	                    	   else if(c.getPrice()>=500000)
	                    	   {
	                               setStyle("-fx-background-color: #B56DEF");

	                    	   }
	                    	  
	                               
	                           
	                       }
	                   }
	               });
	   
	            
	            ///finsakly
	            
	            
	            
	          
	         
	    		} catch (NamingException e) {
	    		
	    			e.printStackTrace();
	    		}
	    }
	    
	   
	    @FXML
	    void statAction(ActionEvent event) throws NamingException {
	    	Stage stage=new Stage();
	    	Scene scene = new Scene(new Group());
	        stage.setTitle("Affectation stats");
	        stage.setWidth(500);
	        stage.setHeight(500);
	        String jndiName10 = "infinity_server-ear/infinity_server-ejb/BondsDemande!tn.esprit.infinity_server.interfaces.BondsDemandRemote";
	       	Context context10 = new InitialContext();
	       	proxy = (BondsDemandRemote) context10.lookup(jndiName10);
	        ObservableList<PieChart.Data> pieChartData =
	                FXCollections.observableArrayList(
	               
	                new PieChart.Data("Affected", ((proxy.getSommePrice()*100)/(proxy.getSommePrice()+proxy.getSommePrice2()))),
	                new PieChart.Data("not Affected", ((proxy.getSommePrice2()*100)/(proxy.getSommePrice2()+ proxy.getSommePrice()))));
	        final PieChart chart = new PieChart(pieChartData);
	        chart.setTitle("Affectation stats");

	        ((Group) scene.getRoot()).getChildren().add(chart);
	        stage.setScene(scene);
	        stage.show();
	    	
	    }
	    
	    
	    @FXML
	    void offersstatsAction(ActionEvent event) throws NamingException {
	    	Stage stage=new Stage();
	    	Scene scene = new Scene(new Group());
	        stage.setTitle("Affectation stats");
	        stage.setWidth(500);
	        stage.setHeight(500);
	        String jndiName4 = "infinity_server-ear/infinity_server-ejb/BondsOffersServices!tn.esprit.infinity_server.interfaces.BondsOffers";
	    	Context context4 = new InitialContext();
	    	proxy1 = (tn.esprit.infinity_server.interfaces.BondsOffers) context4.lookup(jndiName4);
	        ObservableList<PieChart.Data> pieChartData =
	                FXCollections.observableArrayList(
	               
	                new PieChart.Data("Affected", ((proxy1.getSommePrice3()*100)/(proxy1.getSommePrice4()+proxy1.getSommePrice3()))),
	                new PieChart.Data("not Affected", ((proxy1.getSommePrice4()*100)/(proxy1.getSommePrice4()+ proxy1.getSommePrice3()))));
	        final PieChart chart = new PieChart(pieChartData);
	        chart.setTitle("Affectation stats");
	        final Label caption = new Label("");
	        caption.setTextFill(Color.AQUA);
	        caption.setStyle("-fx-font: 40 arial;");

	        for (final PieChart.Data data : chart.getData()) {
	            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
	                new EventHandler<MouseEvent>() {
	                    @Override public void handle(MouseEvent e) {
	                        caption.setTranslateX(e.getSceneX());
	                        caption.setTranslateY(e.getSceneY());
	                        caption.setText(String.valueOf(data.getPieValue()) + "%");
	                     }
	                });
	        }
	        ((Group) scene.getRoot()).getChildren().add(chart);
	        stage.setScene(scene);
	        stage.show();
	        
	        
	    	
	    }
	    
}
