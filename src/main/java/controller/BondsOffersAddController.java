package controller;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import tn.esprit.infinity_server.interfaces.BondsOffers;

public class BondsOffersAddController {
	

    @FXML
    private TextField txtPesquisar;
    
    @FXML
    private Button button;

    @FXML
    private ToggleGroup menu;

    @FXML
    private GridPane telaCadastro;

    @FXML
    private TextField denomination;

    @FXML
    private TextField owner;

    @FXML
    private TextField printplace;

    @FXML
    private TextField couponrate;

    @FXML
    private TextField frequencyrate;

    @FXML
    private DatePicker issuedate;

    @FXML
    private AnchorPane telaEdicao;

    @FXML
    private TableView<?> tbMovimentacao;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colTipo;

    @FXML
    private TableColumn<?, ?> colResponsavel;

    @FXML
    private TableColumn<?, ?> colObjetos;

    @FXML
    private TableColumn<?, ?> colOrigem;

    @FXML
    private TableColumn<?, ?> colDestino;

    @FXML
    private TableColumn<?, ?> colData;

    @FXML
    private TableColumn<?, ?> colDescricao;

    @FXML
    private Button btSalvar;

    @FXML
    private Label legenda;

    @FXML
    private GridPane telaCadastro1;

    @FXML
    private TextField securitynumber;

    @FXML
    private TextField series;

    @FXML
    private TextField interestrate;

    @FXML
    private TextField adress;

    @FXML
    private DatePicker maturitydate;

    @FXML
    private DatePicker printdate;

    @FXML
    void addbondsoffer(ActionEvent event) throws NamingException {

    	String jndiName = "infinity_server-ear/infinity_server-ejb/BondsOffersServices!tn.esprit.infinity_server.interfaces.BondsOffers";
    	Context context = new InitialContext();
    	BondsOffers proxy = (BondsOffers) context.lookup(jndiName);
    	try{
    		
    	Float a=Float.parseFloat(couponrate.getText());
    	Long b=Long.parseLong(securitynumber.getText());
    	Long c=Long.parseLong(denomination.getText());
    	
    	if(couponrate.getText().isEmpty()||adress.getText().isEmpty()||denomination.getText().isEmpty()||frequencyrate.getText().isEmpty()||interestrate.getText().isEmpty()||owner.getText().isEmpty()||printplace.getText().isEmpty()||securitynumber.getText().isEmpty()||series.getText().isEmpty()){
    		Alert missingFields = new Alert(Alert.AlertType.INFORMATION);
            missingFields.setHeaderText(null);
            missingFields.setContentText("Please complete all fields");
            missingFields.initModality(Modality.APPLICATION_MODAL);
            missingFields.showAndWait();
    	}
    	else if(Float.parseFloat(couponrate.getText())<0||Long.parseLong(securitynumber.getText())<0||Long.parseLong(denomination.getText())<0){
    		Alert missingFields = new Alert(Alert.AlertType.INFORMATION);
            missingFields.setHeaderText(null);
            missingFields.setContentText("Coupon Rate");
            missingFields.initModality(Modality.APPLICATION_MODAL);
            missingFields.showAndWait();
    	}
    	else if(java.sql.Date.valueOf(issuedate.getValue()).after(java.sql.Date.valueOf(maturitydate.getValue()))){
    		Alert missingFields = new Alert(Alert.AlertType.INFORMATION);
            missingFields.setHeaderText(null);
            missingFields.setContentText("The Maturity Date must be after than Issue Date");
            missingFields.initModality(Modality.APPLICATION_MODAL);
            missingFields.showAndWait();
    	}
    	else{
    	tn.esprit.infinity_server.persistence.BondsOffers bondsoffer = new tn.esprit.infinity_server.persistence.BondsOffers(); 
        bondsoffer.setAddress(adress.getText());
        bondsoffer.setCouponRate(Float.parseFloat(couponrate.getText()));
        bondsoffer.setIssueDate(java.sql.Date.valueOf(issuedate.getValue()));
        bondsoffer.setDenomination(Long.parseLong(denomination.getText()));
        bondsoffer.setFrequencyRate(frequencyrate.getText());
        bondsoffer.setInterestRate(interestrate.getText());
        bondsoffer.setOwner(owner.getText());
        bondsoffer.setPrintPlace(printplace.getText());
        bondsoffer.setSecurityNumber(Long.parseLong(securitynumber.getText()));
        bondsoffer.setSeries(series.getText());
        bondsoffer.setMaturityDate(java.sql.Date.valueOf(maturitydate.getValue()));
        bondsoffer.setPrintDate(java.sql.Date.valueOf(printdate.getValue()));
        bondsoffer.setStatusOffer("not affected");
        proxy.addBondsOffers(bondsoffer,1);
        
       
    	}
    	}
    	 catch (NumberFormatException nfe) 
    	{
    	  Alert alert = new Alert(Alert.AlertType.ERROR);
    	          alert.setTitle("Add");
    	          alert.setHeaderText(null);
    	          alert.setContentText("Denomination , Coupon Rate and Security Number must be integer");
    	          alert.showAndWait();
    	    
    	}

    }

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

}
