package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.print.DocFlavor.STRING;


import com.jfoenix.controls.JFXButton.ButtonType;
import com.mashape.unirest.http.options.Option;

import Util.Session;
import javafx.scene.Parent;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label ;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn.CellDataFeatures;

import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.Optional;
import tn.esprit.infinity_server.interfaces.AddressRemote;
import tn.esprit.infinity_server.interfaces.OptionCallRemote;
import tn.esprit.infinity_server.interfaces.TradorRemote;
import tn.esprit.infinity_server.interfaces.UserRemote;
import tn.esprit.infinity_server.persistence.OptionCall;
import tn.esprit.infinity_server.persistence.OptionPut;

public class CallController implements Initializable {
	UserRemote userProxy;
	AddressRemote addressProxy;
	TradorRemote traderProxy;
     int id =Session.getUser().getId();
 	@Override
 	public void initialize(URL location, ResourceBundle resources) {
 		try {
 			initServiceJNDI();
 		} catch (NamingException e) {
 		
 			System.out.println(e);
 		}
     	lbUser.setText(Session.getUser().getLogin());
     	
     	ViewOptionCall.setOnMouseClicked(new EventHandler<MouseEvent>() {
 	           @Override
         public void handle(MouseEvent event) {
 	        	   if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                OptionCall ev =ViewOptionCall.getItems().get(ViewOptionCall.getSelectionModel().getSelectedIndex());
                System.out.println("dsfg");
             
                		double strike=ev.getStrikePrice();
             		   double value=ev.getPriceOption();
             		   String statut=ev.getStatut();
              
                	try {
 							Payoffdiagram(strike,value,statut);
 						} catch (SQLException | NamingException e) {
 							// TODO Auto-generated catch block
 							e.printStackTrace();
 						}
                
             }
         }

 			
     });
 		
 		
 	
	}
	@FXML
    private ToggleButton btCatalogacao;

    @FXML
    private ToggleGroup grupoMenus;

    @FXML
    private VBox boxCatalogacao;

    @FXML
    private ToggleButton btCatalogar;

    @FXML
    private ToggleGroup grupoCatalogacao;

    @FXML
    private ToggleButton btDesginacao;

    @FXML
    private ToggleButton btEstratigrafia;

    @FXML
    private ToggleButton btColecao;

    @FXML
    private ToggleButton btVisitas;

    @FXML
    private VBox boxVisitas;

    @FXML
    private ToggleButton btVisitantes;

    @FXML
    private ToggleGroup grupoVisitantes;

    @FXML
    private ToggleButton btInstituicao;

    @FXML
    private ToggleButton btEmprestimos;

    @FXML
    private VBox boxEmprestimo;

    @FXML
    private ToggleButton btInstituicao1;

    @FXML
    private ToggleGroup grupoVisitantes1;

    @FXML
    private ToggleButton btInstituicao11;

    @FXML
    private ToggleGroup grupoVisitantes11;

    @FXML
    private ToggleButton btInstituicao111;

    @FXML
    private ToggleGroup grupoVisitantes111;

    @FXML
    private ToggleButton btRelatorios;

    @FXML
    private VBox boxLocalizacao;

    @FXML
    private ToggleButton btUtilitarios;

    @FXML
    private VBox boxUtilitarios;

    @FXML
    private ToggleButton btnProfile;

    @FXML
    private ToggleGroup grupoUtilidades2;

    @FXML
    private ToggleButton btnActivate;

    @FXML
    private ToggleGroup grupoUtilidades;

    @FXML
    private ToggleButton btnDesactivate;

    @FXML
    private ToggleGroup grupoUtilidades1;

    @FXML
    private Label lbUser;

    @FXML
    private AnchorPane container;

    @FXML
    private TextField STRIKE_PRICE_C;

    @FXML
    private TextField RATE_C;

    @FXML
    private TextField VOLATILITY_C;

    @FXML
    private TextField OPTION_PRICE_C;

    @FXML
    private DatePicker EXPIRED_DATE_C;

    @FXML
    private TextField T_MATURITY_C;

    @FXML
    private TextField AN_RATE_C;

    @FXML
    private Label PRICE_C;

    @FXML
    private Label Maturity_C;

    @FXML
    private Button afficheMaturity_C;

    @FXML
    private Button CALCULATEP_C;

    @FXML
    private Button ButtonAdd_C;

    @FXML
    private TableView<OptionCall> ViewOptionCall;

    @FXML
    private TableColumn<OptionCall, String> C_CODE;

    @FXML
    private TableColumn<OptionCall, String> C_STRIKEPRICE;

    @FXML
    private TableColumn<OptionCall, String> C_PRICEOPTION;

    @FXML
    private TableColumn<OptionCall, String> C_STARTDATE;

    @FXML
    private TableColumn<OptionCall, String> C_EXPIREDDATE;

    @FXML
    private TableColumn<OptionCall, String> C_STATUT;

    @FXML
    private TableColumn<OptionCall,Boolean> BuyOptionCall;
 

    @FXML
    private LineChart<OptionCall, String> LineChart;

    @FXML
    private CategoryAxis X;

    @FXML
    private NumberAxis Y;

    @FXML
    private TextField SPOT_PRICE_C;

    @FXML
    private Label lbMensagem;

    @FXML
    private VBox boxNotas;

    @FXML
    void Activate(ActionEvent event) {

    }

    @FXML
    void AddCallOption(ActionEvent event) throws NamingException {
    	String jndiName ="infinity_server-ear/infinity_server-ejb/ClientOptionCallService!tn.esprit.infinity_server.interfaces.OptionCallRemote";
		Context context = new InitialContext();
		OptionCallRemote optionproxy = (OptionCallRemote) context.lookup(jndiName) ;
   	 	System.out.println("kkkOOOkk");
   	
   	 	Date duration2=java.sql.Date.valueOf(EXPIRED_DATE_C.getValue());
	    Calendar c = Calendar.getInstance ();
	    Date date = c.getTime();
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    System.out.println(dateFormat.format(date));
	    
	    String duration1= dateFormat.format(date);


	 String code="OPTPUT";
		OptionCall o =new OptionCall(duration1,duration2,Double.parseDouble(SPOT_PRICE_C.getText()),
				Double.parseDouble(STRIKE_PRICE_C.getText()),
				Double.parseDouble(PRICE_C.getText()),"NotAvailable",
				Double.parseDouble(VOLATILITY_C.getText()),Double.parseDouble(RATE_C.getText()),
				Double.parseDouble(Maturity_C.getText()),Double.parseDouble(AN_RATE_C.getText()));
	
	
		optionproxy.createCallOption(o, id);
		
    	

    }

    @FXML
    void Desactivate(ActionEvent event) {

    }

    @FXML
    void Profile(ActionEvent event) {

    }

    @FXML
    void ViewCallOptions(ActionEvent event) throws NamingException{
    	String jndiName = "infinity_server-ear/infinity_server-ejb/ClientOptionCallService!tn.esprit.infinity_server.interfaces.OptionCallRemote";
    	Context context = new InitialContext();
    	OptionCallRemote optionproxy = (OptionCallRemote) context.lookup(jndiName) ;
    	 final ObservableList<OptionCall> OptionList= FXCollections.observableArrayList(optionproxy.StatutListOptionCall("NotAvailable",id));
    	
    	 
    	
    	 System.out.println("marwaaaaaaaaa");	
    	    C_CODE.setCellValueFactory(new PropertyValueFactory<OptionCall,String>("code"));
    	    C_STRIKEPRICE.setCellValueFactory(new PropertyValueFactory<OptionCall,String>("strikePrice"));
    	    C_PRICEOPTION.setCellValueFactory(new PropertyValueFactory<OptionCall,String>("priceOption"));
    	    C_STARTDATE.setCellValueFactory(new PropertyValueFactory<OptionCall,String>("startdate"));
    	    C_EXPIREDDATE.setCellValueFactory(new PropertyValueFactory<OptionCall,String>("expireddate"));
    	    C_STATUT.setCellValueFactory(new PropertyValueFactory<OptionCall,String>("statut"));
    	   
    	
    	
    	  
    	  BuyOptionCall.setSortable(false);

    	    // define a simple boolean cell value for the action column so that the column will only be shown for non-empty rows.
    	
    	  BuyOptionCall.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OptionCall, Boolean>, ObservableValue<Boolean>>() {
    	      @Override public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<OptionCall, Boolean> features) {
    	        return new SimpleBooleanProperty(features.getValue() != null);
    	      }
    	    });

    	 
    	//Define the button cell
    	   class ButtonCell extends TableCell<OptionCall, Boolean> {
    	 
    	        final Button cellButton = new Button("Action");
    	 
    	        ButtonCell() {
    	 
    	            cellButton.setOnAction(new EventHandler<ActionEvent>()  {
    	 
    	                @Override
    	                public void handle(ActionEvent t)	{
    	                	OptionCall currentCall = (OptionCall) ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
    	                	int idOpt =optionproxy.findOptionCallById(currentCall.getCode());
    	                	OptionCall o=optionproxy.getOptionById(idOpt);
    	                    optionproxy.UpdateCallOption(o,"Available");
    	                }
    	            });
    	        
    	        
    	        }
    	   
    	        @Override
    	        protected void updateItem(Boolean t, boolean empty) {
    	            super.updateItem(t, empty);
    	            if(!empty){
    	                setGraphic(cellButton);
    	            }
    	        }
    	   }
    	    // create a cell value factory with an add button for each row in the table.
    		  BuyOptionCall.setCellFactory(new Callback<TableColumn<OptionCall, Boolean>,TableCell<OptionCall, Boolean>>() {
    		      @Override public TableCell<OptionCall, Boolean> call(TableColumn<OptionCall, Boolean> personBooleanTableColumn) {
    		    	
    		    	  return new ButtonCell();
    		       
    		      }
    		    });
    		  
        
    	ViewOptionCall.setItems(OptionList);
    	
    		
    		
    	    }

    @FXML
    void ViewCallOptionsBuy(ActionEvent event) {

    }

    @FXML
    void afficheMaturity(ActionEvent event) throws NamingException {
    	String jndiName ="infinity_server-ear/infinity_server-ejb/ClientOptionCallService!tn.esprit.infinity_server.interfaces.OptionCallRemote";
		Context context = new InitialContext();
		OptionCallRemote optionproxy = (OptionCallRemote) context.lookup(jndiName) ;
   	 	System.out.println("kkkOOOkk");
   	
   	 	Date duration2=java.sql.Date.valueOf(EXPIRED_DATE_C.getValue());
	    Calendar c = Calendar.getInstance ();
	    Date date = c.getTime();
	
	    double m=optionproxy.calculateMaturity(date,duration2);
   	 	System.out.println(m);
	Maturity_C.setText(Double.toString(m));
    }

    @FXML
    void calculatePrice(ActionEvent event) throws NamingException {
       String jndiName ="infinity_server-ear/infinity_server-ejb/ClientOptionCallService!tn.esprit.infinity_server.interfaces.OptionCallRemote";
		Context context = new InitialContext();
		OptionCallRemote optionproxy = (OptionCallRemote) context.lookup(jndiName) ;
		double S= Double.parseDouble(SPOT_PRICE_C.getText());
		double K=Double.parseDouble(STRIKE_PRICE_C.getText());
		double T =1;  // Function to calculate time To maturity 
		double R =Double.parseDouble(RATE_C.getText()) ;
		double q =Double.parseDouble(AN_RATE_C.getText());
		double v=	Double.parseDouble(VOLATILITY_C.getText()) ;
		
	double price =optionproxy.calculateOptionPrice(S, K, T, R, q, v) ;
	
	PRICE_C.setText(Double.toString(price));
	
    }

    @FXML
    void menuCatalogacao(ActionEvent event) {

    }

    @FXML
    void menuDashboard(MouseEvent event) {

    }

    @FXML
    void menuEmprestimo(ActionEvent event) {

    }

    @FXML
    void menuRelatorios(ActionEvent event) {

    }

    @FXML
    void menuSair(ActionEvent event) {

    }

    @FXML
    void menuUtilitario(ActionEvent event) {

    }

    @FXML
    void menuVisitas(ActionEvent event) {

    }

    @FXML
    void siteMuseu(ActionEvent event) {

    }

    @FXML
    void subCatalogar(ActionEvent event) {

    }

    @FXML
    void subColecao(ActionEvent event) {

    }

    @FXML
    void subDesignacao(ActionEvent event) {

    }

    @FXML
    void subEstratigrafia(ActionEvent event) {

    }

    @FXML
    void subInstituicao(ActionEvent event) {

    }

    @FXML
    void subVisitantes(ActionEvent event) {

    }
    public void initServiceJNDI() throws NamingException
    {
    	String jndiName = "infinity_server-ear/infinity_server-ejb/UserService!tn.esprit.infinity_server.interfaces.UserRemote";
		Context context = new InitialContext();
		userProxy = (UserRemote) context.lookup(jndiName);
		
		jndiName = "infinity_server-ear/infinity_server-ejb/ServiceAddress!tn.esprit.infinity_server.interfaces.AddressRemote";
		context = new InitialContext();
		addressProxy = (AddressRemote) context.lookup(jndiName);
    }
   public void Payoffdiagram(double strike,double value,String statut) throws SQLException,NamingException{
	   String s= Double.toString(strike);
       String s1= Double.toString(strike+20);
	    String max=Double.toString(strike-value);
		//Defining X axis  
		
		X.setLabel("Stock Price"); 
	
		//Defining y axis 
	
		Y.setLabel("Option value");
		

	   
	  
		  XYChart.Series series = new XYChart.Series(); 
		if(statut.equals("Available")) {
		 
		series.setName("Buyer Position Payoff Diagram");
	      
		series.getData().add(new XYChart.Data("0", value+20)); 
		series.getData().add(new XYChart.Data(max,0)); 
		series.getData().add(new XYChart.Data(s, -value)); 
		series.getData().add(new XYChart.Data(s1, -value));
		
	   }
	   else if(statut.equals("NotAvailable")) {
	
		series.setName("Seller Position Payoff Diagram");
	      
		series.getData().add(new XYChart.Data("0", -value-20)); 
		series.getData().add(new XYChart.Data(max,0)); 
		series.getData().add(new XYChart.Data(s,value)); 
		series.getData().add(new XYChart.Data(s1, value)); 

	   }
		
		LineChart.getData().add(series);  
	
	
		
	}
   


}