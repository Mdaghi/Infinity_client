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
import tn.esprit.infinity_server.interfaces.OptionRemote;
import tn.esprit.infinity_server.interfaces.TradorRemote;
import tn.esprit.infinity_server.interfaces.UserRemote;
import tn.esprit.infinity_server.persistence.Client;
import tn.esprit.infinity_server.persistence.OptionCall;
import tn.esprit.infinity_server.persistence.OptionPut;
public class PutController implements Initializable {
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
    	
    	ViewOptionPut.setOnMouseClicked(new EventHandler<MouseEvent>() {
	           @Override
        public void handle(MouseEvent event) {
	        	   if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
               OptionPut ev =ViewOptionPut.getItems().get(ViewOptionPut.getSelectionModel().getSelectedIndex());
               System.out.println("dsfg");
            
               		double strike=ev.getStrikePrice();
            		   double value=ev.getPriceOption();
            		   String statut=ev.getstatut();
             
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
    private Label lbMensagem;

    @FXML
    private VBox boxNotas;

    @FXML
    private Button ButtonAdd;
    @FXML
    private Button
    afficheMaturity;
    @FXML
    private TextField SPOT_PRICE;

    @FXML
    private TextField STRIKE_PRICE;

    @FXML
    private TextField RATE;

    @FXML
    private TextField VOLATILITY;



    @FXML
    private DatePicker STRAT_DATE;

    @FXML
    private DatePicker EXPIRED_DATE;
    
    @FXML
    private Button CALCULATEP;

    @FXML
    private Label PRICE;
    @FXML
    private Label
    Maturity;

    @FXML
    private TextField AN_RATE;
    
    
    @FXML
    private TableColumn<OptionPut, String> C_CODE;

    @FXML
    private TableColumn<OptionPut, String> C_STRIKEPRICE;

    @FXML
    private TableColumn<OptionPut, String> C_PRICEOPTION;

    @FXML
    private TableColumn<OptionPut, String> C_STARTDATE;

    @FXML
    private TableColumn<OptionPut, String> C_EXPIREDDATE;

    @FXML
    private TableColumn<OptionPut, String> C_STATUT;
    

    @FXML
    private TableColumn<OptionPut,Boolean> BuyOptionPut;
    

    @FXML
    private TableView<OptionPut> ViewOptionPut;
    
    @FXML
    private LineChart<?, ?> LineChart;

    @FXML
    private CategoryAxis X;

    @FXML
    private NumberAxis Y;
    
    @FXML
    private ToggleButton   CallButton;

    @FXML
    void Activate(ActionEvent event) {

    }

    @FXML
    void Desactivate(ActionEvent event) {

    }

    @FXML
    void Profile(ActionEvent event) {

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
    
    @FXML
    void AddPutOption (ActionEvent event) throws NamingException,IOException, ParseException{
  
    	String jndiName ="infinity_server-ear/infinity_server-ejb/ClientOptionService!tn.esprit.infinity_server.interfaces.OptionRemote";
		Context context = new InitialContext();
		OptionRemote optionproxy = (OptionRemote) context.lookup(jndiName) ;
   	 	System.out.println("kkkOOOkk");
   	
   	 	Date duration2=java.sql.Date.valueOf(EXPIRED_DATE.getValue());
	    Calendar c = Calendar.getInstance ();
	    Date date = c.getTime();
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    System.out.println(dateFormat.format(date));
	    
	    String duration1= dateFormat.format(date);


	 String code="OPTPUT";
		OptionPut o =new OptionPut(duration1,duration2,Double.parseDouble(SPOT_PRICE.getText()),
				Double.parseDouble(STRIKE_PRICE.getText()),
				Double.parseDouble(PRICE.getText()),"NotAvailable",
				Double.parseDouble(VOLATILITY.getText()),Double.parseDouble(RATE.getText()),
				Double.parseDouble(Maturity.getText()),Double.parseDouble(AN_RATE.getText()));
	
	
		optionproxy.createPutOption(o,id);
		
    	
    }
    
    @FXML
    void calculatePrice(ActionEvent event) throws NamingException {
    	String jndiName ="infinity_server-ear/infinity_server-ejb/ClientOptionService!tn.esprit.infinity_server.interfaces.OptionRemote";
		Context context = new InitialContext();
		OptionRemote optionproxy = (OptionRemote) context.lookup(jndiName) ;
		double S= Double.parseDouble(SPOT_PRICE.getText());
		double K=Double.parseDouble(STRIKE_PRICE.getText());
		double T =1;  // Function to calculate time To maturity 
		double R =Double.parseDouble(RATE.getText()) ;
		double q =Double.parseDouble(AN_RATE.getText());
		double v=	Double.parseDouble(VOLATILITY.getText()) ;
		
	double price =optionproxy.calculateOptionPrice(S, K, T, R, q, v) ;
	
	PRICE.setText(Double.toString(price));
	
	
    }
    @FXML
    void afficheMaturity(ActionEvent event) throws NamingException {
    	String jndiName ="infinity_server-ear/infinity_server-ejb/ClientOptionService!tn.esprit.infinity_server.interfaces.OptionRemote";
		Context context = new InitialContext();
		OptionRemote optionproxy = (OptionRemote) context.lookup(jndiName) ;
   	 	System.out.println("kkkOOOkk");
   	
   	 	Date duration2=java.sql.Date.valueOf(EXPIRED_DATE.getValue());
	    Calendar c = Calendar.getInstance ();
	    Date date = c.getTime();
	
	    double m=optionproxy.calculateMaturity(date,duration2);
   	 	System.out.println(m);
	Maturity.setText(Double.toString(m));
    }
    
    
    @FXML
    void ViewPutOptions (ActionEvent event) throws NamingException{
    String jndiName = "infinity_server-ear/infinity_server-ejb/ClientOptionService!tn.esprit.infinity_server.interfaces.OptionRemote";
	Context context = new InitialContext();
	OptionRemote optionproxy = (OptionRemote) context.lookup(jndiName) ;
	 final ObservableList<OptionPut> OptionList= FXCollections.observableArrayList(optionproxy.StatutListOptionPut("NotAvailable",id));
	
	 
	
	 System.out.println("marwaaaaaaaaa");	
	    C_CODE.setCellValueFactory(new PropertyValueFactory<OptionPut,String>("code"));
	    C_STRIKEPRICE.setCellValueFactory(new PropertyValueFactory<OptionPut,String>("strikePrice"));
	    C_PRICEOPTION.setCellValueFactory(new PropertyValueFactory<OptionPut,String>("priceOption"));
	    C_STARTDATE.setCellValueFactory(new PropertyValueFactory<OptionPut,String>("startdate"));
	    C_EXPIREDDATE.setCellValueFactory(new PropertyValueFactory<OptionPut,String>("expireddate"));
	    C_STATUT.setCellValueFactory(new PropertyValueFactory<OptionPut,String>("statut"));
	   
	
	
	  
	  BuyOptionPut.setSortable(false);

	    // define a simple boolean cell value for the action column so that the column will only be shown for non-empty rows.
	  BuyOptionPut.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OptionPut, Boolean>, ObservableValue<Boolean>>() {
	      @Override public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<OptionPut, Boolean> features) {
	        return new SimpleBooleanProperty(features.getValue() != null);
	      }
	    });

	 
	//Define the button cell
	   class ButtonCell extends TableCell<OptionPut, Boolean> {
	 
	        final Button cellButton = new Button("Action");
	 
	        ButtonCell() {
	 
	            cellButton.setOnAction(new EventHandler<ActionEvent>()  {
	 
	                @Override
	                public void handle(ActionEvent t)	{
	                	OptionPut currentPut = (OptionPut) ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
	                	int idOpt =optionproxy.findOptionPutById(currentPut.getCode());
	                	OptionPut o=optionproxy.getOptionById(idOpt);
	                    optionproxy.UpdatePutOption(o,"Available");
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
		  BuyOptionPut.setCellFactory(new Callback<TableColumn<OptionPut, Boolean>,TableCell<OptionPut, Boolean>>() {
		      @Override public TableCell<OptionPut, Boolean> call(TableColumn<OptionPut, Boolean> personBooleanTableColumn) {
		    	
		    	  return new ButtonCell();
		       
		      }
		    });
		  
    
	ViewOptionPut.setItems(OptionList);
	
	
    }
    @FXML
    void ViewPutOptionsBuy (ActionEvent event) throws NamingException{
    String jndiName = "infinity_server-ear/infinity_server-ejb/ClientOptionService!tn.esprit.infinity_server.interfaces.OptionRemote";
	Context context = new InitialContext();
	OptionRemote optionproxy = (OptionRemote) context.lookup(jndiName) ;
	 final ObservableList<OptionPut> OptionList= FXCollections.observableArrayList(optionproxy.AvailableStatutListOptionPut("Available",id));
	
    
	
	 System.out.println("marwaaaaaaaaa");	
	    C_CODE.setCellValueFactory(new PropertyValueFactory<OptionPut,String>("code"));
	    C_STRIKEPRICE.setCellValueFactory(new PropertyValueFactory<OptionPut,String>("strikePrice"));
	    C_PRICEOPTION.setCellValueFactory(new PropertyValueFactory<OptionPut,String>("priceOption"));
	    C_STARTDATE.setCellValueFactory(new PropertyValueFactory<OptionPut,String>("startdate"));
	    C_EXPIREDDATE.setCellValueFactory(new PropertyValueFactory<OptionPut,String>("expireddate"));
	    C_STATUT.setCellValueFactory(new PropertyValueFactory<OptionPut,String>("statut"));
	   
	
	
	  
	  BuyOptionPut.setSortable(false);

	    // define a simple boolean cell value for the action column so that the column will only be shown for non-empty rows.
	  BuyOptionPut.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OptionPut, Boolean>, ObservableValue<Boolean>>() {
	      @Override public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<OptionPut, Boolean> features) {
	        return new SimpleBooleanProperty(features.getValue() != null);
	      }
	    });

	 
	//Define the button cell
	   class ButtonCell extends TableCell<OptionPut, Boolean> {
	 
	        final Button cellButton = new Button("Action");
	        
	 
	        ButtonCell() {
	 
	            cellButton.setOnAction(new EventHandler<ActionEvent>() {
	 
	                @Override
	                public void handle(ActionEvent t) {
	                	   // get Selected Item
	                	OptionPut currentPut = (OptionPut) ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());

	                	int idOpt =optionproxy.findOptionPutById(currentPut.getCode());
	                	System.out.println(currentPut.getCode());
	                	System.out.println(idOpt);
	                	OptionPut o=optionproxy.getOptionById(idOpt);
	                    optionproxy.UpdatePutOption(o,id);
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
		  BuyOptionPut.setCellFactory(new Callback<TableColumn<OptionPut, Boolean>,TableCell<OptionPut, Boolean>>() {
		      @Override public TableCell<OptionPut, Boolean> call(TableColumn<OptionPut, Boolean> personBooleanTableColumn) {
		    	
		    	  return new ButtonCell();
		       
		      }
		    });
		  
    
	ViewOptionPut.setItems(OptionList);
	
	
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
   @FXML
   private void MoveToCallOption(ActionEvent event) throws IOException {
   	
   	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/view/OptionCall.fxml"));
       Parent root = loader.load();

       container.getScene().setRoot(root);
   }
   
}
