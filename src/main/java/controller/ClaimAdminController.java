package controller;


import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;




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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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


public class ClaimAdminController implements Initializable{

	    @FXML
	    private TableColumn<Claim, String> Description;

	  
	   
	    @FXML
	    private TableView<Claim> tableViewDemand;

	    @FXML
	    private Button refuserclaim;

	    @FXML
	    private TextField cherchclaim;

	   

	    @FXML
	    private TableColumn<?, ?> colDescricao;

	    @FXML
	    private Button acceptclaim;

	  
	    @FXML
	    private Label lblid2;

	    @FXML
	    private TextArea txtareaDescription;

	   
	    @FXML
	    private TableColumn<Claim, String> columndateclaim;

	  
	    @FXML
	    private TableColumn<Claim, String> status;

	    
	    Claim olSouvenirs=new Claim(); 
	    private ObservableList<Claim>souvs;
	    ClaimService ss=new ClaimService();
	    Claim new_souv=new Claim();
	    ClaimRemote proxy;
	    private ObservableList<Claim> zz;
	    
	    public void afficher(){
	    	try {
		        	String jndiName = "infinity_server-ear/infinity_server-ejb/ClaimService!tn.esprit.infinity_server.interfaces.ClaimRemote";
		        	Context context = new InitialContext();
		        	proxy = (ClaimRemote) context.lookup(jndiName);
		        	
		            columndateclaim.setCellValueFactory(new PropertyValueFactory("DateClaim"));
		            Description.setCellValueFactory(new PropertyValueFactory("Description"));
		            status.setCellValueFactory(new PropertyValueFactory("status"));

		          List<Claim> lst=proxy.getClaimsforAdmin();  
		         souvs=FXCollections.observableArrayList(lst);    
		         tableViewDemand.setItems(souvs);  
		         
		    		} catch (NamingException e) {
		    		
		    			e.printStackTrace();
	    		}
	    }
	    
	    public void getClaim() {
	        tableViewDemand.setOnMousePressed(new EventHandler<MouseEvent>()
	        		{
	        			 @Override
	        	            public void handle(MouseEvent event) {
	        	                Claim claim= (Claim) souvs.get(tableViewDemand.getSelectionModel().getSelectedIndex());
	        	                lblid2.setText(String.valueOf(claim.getId()));
	        	                txtareaDescription.setText(String.valueOf(claim.getDescription()));
	        	               datenow=claim.getDateClaim();
	        	            
	        	            }
	        		});
	        		
	   }
	
	    
	    public void clear(){
			
			
	        // columndateclaim.setValue(null);
	         txtareaDescription.clear();
	       
	 	}
	    
	    @FXML
	    void acceptclaimAction(ActionEvent event) throws NamingException{

	    	 String jndiName = "infinity_server-ear/infinity_server-ejb/ClaimService!tn.esprit.infinity_server.interfaces.ClaimRemote";
	       	Context context = new InitialContext();
	       	proxy = (ClaimRemote) context.lookup(jndiName);
	       	new_souv.setId(Integer.parseInt(lblid2.getText()));
	       	new_souv.setDescription(txtareaDescription.getText());
	       	new_souv.setDateClaim(datenow);
	 		  new_souv.setStatus("claim treated");
	 		 
	        try {
	            proxy.updateClaim(new_souv);
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

	    @FXML
	    void refuserclaimAction(ActionEvent event) throws NamingException{

	    	 String jndiName = "infinity_server-ear/infinity_server-ejb/ClaimService!tn.esprit.infinity_server.interfaces.ClaimRemote";
	       	Context context = new InitialContext();
	       	proxy = (ClaimRemote) context.lookup(jndiName);
	       	new_souv.setId(Integer.parseInt(lblid2.getText()));
	       	new_souv.setDescription(txtareaDescription.getText());
	       	new_souv.setDateClaim(datenow);
	 		  new_souv.setStatus("claim refused");
	 	      
	        try {
	            proxy.updateClaim(new_souv);
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

	
	
	
	    LocalDate datenow;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		afficher();
    	getClaim();
    	lblid2.setVisible(false);
	}

	
	
}
