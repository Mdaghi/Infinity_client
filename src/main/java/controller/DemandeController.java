package controller;



import javafx.scene.control.Alert;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;


import javafx.scene.layout.AnchorPane;
import tn.esprit.infinity_server.interfaces.BondsDemandRemote;
import tn.esprit.infinity_server.persistence.DemandeBond;
import tn.esprit.infinity_server.services.BondsDemande;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.cell.PropertyValueFactory;





public class DemandeController implements Initializable {
	
	LocalDate datenow;

	@FXML
	private Button btnupdate;
	
	 @FXML
	private Label lblid1;
    @FXML
    private Button add;
    @FXML
    private TextField count;
    @FXML
    private DatePicker MatureDate;

    @FXML
    private TextField Price;

    @FXML
    private TextField ineteretcoupon;

    @FXML
    private DatePicker SendDate;
    @FXML
    private TableView<DemandeBond> tableViewDemand;


    @FXML
    private TableColumn<?, ?> columnstatus;
    
    @FXML
    private TableColumn<DemandeBond, String > columndate1;

    @FXML
    private TableColumn<DemandeBond, String> columndate2;
    
    @FXML
    private TableColumn<DemandeBond, String> columndate0;

    @FXML
    private TableColumn<DemandeBond, String> columncoupon;
    
    @FXML
    private Button btndelete;
    

    @FXML
    private TextField txtPesquisar;
    
    @FXML
    private TableColumn<?, ?> columnprice;

    DemandeBond olSouvenirs=new DemandeBond(); 
    private ObservableList<DemandeBond>souvs;
    BondsDemande ss=new BondsDemande();
    DemandeBond new_souv=new DemandeBond();
    BondsDemandRemote proxy;
    private ObservableList<DemandeBond> zz;
  
    @Override
	public void initialize(URL url, ResourceBundle rb) {
    	afficher();
    	getDemande();
    	lblid1.setVisible(false);
    	
	}
    
    @FXML
    void deleteAction(ActionEvent event) {
    	try {
    	 Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
         alert.setTitle("Delete Memory");
         alert.setHeaderText(null);
         alert.setContentText("Do you really want to delete this memory ?");
         Optional<ButtonType> action = alert.showAndWait();
         if (action.get() == ButtonType.OK) {
        	 Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                 Optional<ButtonType> action2 = alert2.showAndWait();
                 if (action2.get() == ButtonType.OK) {
                	 String jndiName = "infinity_server-ear/infinity_server-ejb/BondsDemande!tn.esprit.infinity_server.interfaces.BondsDemandRemote";
                 	Context context = new InitialContext();
                 	proxy = (BondsDemandRemote) context.lookup(jndiName);
                     proxy.deletedemande(Integer.parseInt(lblid1.getText()));
                     	afficher();
                     	clear();
                 }

             }
    	} catch (NamingException e) {
    		
			e.printStackTrace();
		}
         
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
            columnstatus.setCellValueFactory(new PropertyValueFactory("Statutbond"));
          List<DemandeBond> lst=proxy.getDemandeBond();  
          
         souvs=FXCollections.observableArrayList(lst);    
         tableViewDemand.setItems(souvs);  
         
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

	@FXML
    void addAction(ActionEvent event) throws NamingException {
    	
		 if(SendDate.getValue().isAfter(MatureDate.getValue()))
         {
          Alert alert =new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Mature date must be after Send date");
          alert.setHeaderText(null);
          alert.setContentText("choose a valid Send date and Mature date !");
          alert.showAndWait();
          return;
         }
         if(ineteretcoupon.getText().isEmpty()||Price.getText().isEmpty())
         {
            Alert alert =new Alert(Alert.AlertType.ERROR);
          alert.setTitle("erreur");
          alert.setHeaderText(null);
          alert.setContentText("veuillez remplir tous les champs");
          alert.showAndWait();
          return; 
         }
    	String jndiName = "infinity_server-ear/infinity_server-ejb/BondsDemande!tn.esprit.infinity_server.interfaces.BondsDemandRemote";
    	Context context = new InitialContext();
    	 proxy = (BondsDemandRemote) context.lookup(jndiName);
    	DemandeBond bondsoffer = new DemandeBond(); 
    	bondsoffer.setPrix(Double.parseDouble(Price.getText()));
        bondsoffer.setDateEmission(SendDate.getValue());
        bondsoffer.setDateMaturite(MatureDate.getValue());
        bondsoffer.setTauxCoupon(Double.parseDouble(ineteretcoupon.getText()));
        bondsoffer.setDateDemande(LocalDate.now());
        bondsoffer.setStatutbond("not affected");
        proxy.ajouterDemande(bondsoffer);
        afficher();
        clear();
    }
	
	  @FXML
	    void updateAction(ActionEvent event) throws NamingException {
		  
		  
		  String jndiName = "infinity_server-ear/infinity_server-ejb/BondsDemande!tn.esprit.infinity_server.interfaces.BondsDemandRemote";
      	Context context = new InitialContext();
      	proxy = (BondsDemandRemote) context.lookup(jndiName);
		  new_souv.setId(Integer.parseInt(lblid1.getText()));
		  new_souv.setDateEmission(SendDate.getValue());
		  new_souv.setStatutbond("not affected");
		  new_souv.setDateMaturite(MatureDate.getValue());
		  new_souv.setPrix(Double.parseDouble(Price.getText()));
	        new_souv.setTauxCoupon(Double.parseDouble(ineteretcoupon.getText()));
	        new_souv.setDateDemande(datenow);
       try {
           proxy.updateDemande(new_souv);
            afficher();
             
           Alert a = new Alert(Alert.AlertType.INFORMATION);
           a.setTitle("Update Memory");
           a.setHeaderText(null);
           a.setContentText("update sucess !");
           a.showAndWait();
           clear();
              
       } catch (Exception ex) {
           System.err.println(ex.getMessage());
       }
       
       
		  
		  

	    }
	
	


	

}
