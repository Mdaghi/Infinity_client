package controller;

import javafx.scene.control.Label;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.hibernate.result.ResultSetOutput;

import com.sun.glass.events.KeyEvent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.input.InputEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.infinity_server.persistence.Bond;
import tn.esprit.infinity_server.persistence.BondsOffers;

public class BondsOffersDetails implements Initializable {

	@FXML
	private Label lbTitulo;

	@FXML
	private ToggleGroup menu;

	@FXML
	private TextField txtNumOrdem;

	@FXML
	private ComboBox<?> cbOrganizacao;

	@FXML
	private ComboBox<?> cbLocal;

	@FXML
	private ComboBox<?> cbSetor;

	@FXML
	private TableView<BondsOffers> bondsofferstabel;

	@FXML
	private TableColumn<BondsOffers, Long> colDenomination;

	@FXML
	private TableColumn<BondsOffers, Long> colSecurityNumber;

	@FXML
	private TableColumn<?, ?> colOwner;

	@FXML
	private TableColumn<?, ?> colSeries;

	@FXML
	private TableColumn<?, ?> colIssueDate;
	
	@FXML
    private TextField rechercheTF;

	@FXML
	private TableColumn<?, ?> colAdress;

	@FXML
	private TableColumn<?, ?> colPrintPlace;

	@FXML
	private TableColumn<?, ?> colPrintDate;

	@FXML
	private TableColumn<?, ?> colCouponRate;

	@FXML
	private TableColumn<?, ?> colMaturityDate;

	@FXML
	private TableColumn<?, ?> colInterestRate;

	@FXML
	private TableColumn<?, ?> colFrequencyRate;

	@FXML
	private TextField denomination;

	@FXML
	private TextField series;
	
	 @FXML
	 private TextField partfuturevalue;

	@FXML
	private TextField couponrate;

	@FXML
	private TextField securitynumber;

	@FXML
	private TextField adress;

	@FXML
	private TextField frequencyrate;

	@FXML
	private TextField owner;

	@FXML
	private TextField printplace;

	@FXML
	private TextField interestrate;
	
    @FXML
    private TextField presentvalue;

    @FXML
    private TextField futurevalue;
    
    @FXML
    private Button estimate;

	@FXML
	private Label legenda;

	ObservableList<BondsOffers> listbondsoffers = FXCollections.observableArrayList();

	@FXML
	void adicionar(ActionEvent event) {

	}

	@FXML
	void bondsdetails() {

		if (bondsofferstabel.getSelectionModel().getSelectedItem() != null) {
			BondsOffers r = bondsofferstabel.getSelectionModel().getSelectedItem();
			securitynumber.setText(r.getSecurityNumber() + "");
			series.setText(r.getSeries() + "");
			denomination.setText(r.getDenomination() + "");
			frequencyrate.setText(r.getFrequencyRate() + "");
			interestrate.setText(r.getInterestRate() + "");
			printplace.setText(r.getPrintPlace() + "");
			couponrate.setText(r.getCouponRate() + "");
			adress.setText(r.getAddress() + "");
			owner.setText(r.getOwner() + "");
			presentvalue.setText(r.getDenomination() + "");

		}

	}

	@FXML
	void remover(ActionEvent event) throws NamingException {

		String jndiName = "infinity_server-ear/infinity_server-ejb/BondsOffersServices!tn.esprit.infinity_server.interfaces.BondsOffers";
		Context ctx = new InitialContext();
		tn.esprit.infinity_server.interfaces.BondsOffers proxy = (tn.esprit.infinity_server.interfaces.BondsOffers) ctx
				.lookup(jndiName);

		proxy.removeBondsOffers(bondsofferstabel.getSelectionModel().getSelectedItem().getId());
		bondsofferstabel.getItems().clear();

	}

	@FXML
	void telaLocalizacao(ActionEvent event) {

	}

	@FXML
	    void update(ActionEvent event) throws NamingException {
	    	
	    	String jndiName = "infinity_server-ear/infinity_server-ejb/BondsOffersServices!tn.esprit.infinity_server.interfaces.BondsOffers";
	    	Context context = new InitialContext();
	    	tn.esprit.infinity_server.interfaces.BondsOffers proxy = (tn.esprit.infinity_server.interfaces.BondsOffers) context.lookup(jndiName);
	    	tn.esprit.infinity_server.persistence.BondsOffers bondsoffer = new tn.esprit.infinity_server.persistence.BondsOffers(); 
	    	 if (bondsofferstabel.getSelectionModel().getSelectedItem() != null)
	    	 {
	    		
	    		 BondsOffers bond = bondsofferstabel.getSelectionModel().getSelectedItem();
	    		 bond.setAddress(adress.getText());
	    		 bond.setCouponRate(Float.parseFloat(couponrate.getText()));
	    		 bond.setDenomination(Long.parseLong(denomination.getText()));
	    		 bond.setFrequencyRate(frequencyrate.getText());
	    		 bond.setInterestRate(interestrate.getText());
	    		 bond.setOwner(owner.getText());
	    		 bond.setPrintPlace(printplace.getText());
	    		 bond.setSecurityNumber(Long.parseLong(securitynumber.getText()));
	    		 bond.setSeries(series.getText());
	    		 proxy.updateBondsOffers(bond);
	    		 bondsofferstabel.refresh();
	    		 
	    	 }

	    }
	
	 @FXML
	    void rech(InputEvent event) throws NamingException {

		 String jndiName = "infinity_server-ear/infinity_server-ejb/BondsOffersServices!tn.esprit.infinity_server.interfaces.BondsOffers";
			Context ctx = new InitialContext();
			tn.esprit.infinity_server.interfaces.BondsOffers proxy = (tn.esprit.infinity_server.interfaces.BondsOffers) ctx.lookup(jndiName);
			listbondsoffers.clear();
			listbondsoffers.addAll(proxy.findBonds(rechercheTF.getText()));
			bondsofferstabel.setItems(listbondsoffers);
		 
	    }

	void showdetails(BondsOffers b) {
		String idnew = Integer.toString(b.getId());

		denomination.setText(denomination.getText().toString());
		securitynumber.setText(securitynumber.getText().toString());
		series.setText(series.getText().toString());

	}
	
	 @FXML
	    void estimateAction() {
		 if (bondsofferstabel.getSelectionModel().getSelectedItem() != null) {
				BondsOffers r = bondsofferstabel.getSelectionModel().getSelectedItem();
				 
				int m= r.getMaturityDate().getYear();
				int i= r.getIssueDate().getYear();
				int years=m-i;
				
				Float interest = Float.parseFloat(r.getInterestRate());
				Float coupon = Float.parseFloat(r.getFrequencyRate());
				Float result , result1;
				result =(((r.getDenomination()*(interest/100))+(coupon/100))*years)+r.getDenomination();
				result1=((r.getDenomination()*(interest/100))+(coupon/100))+r.getDenomination();
				
				

				futurevalue.setText(result+" "+years+" Year(s) after");
				partfuturevalue.setText(result1+" After only one year");
		 }
	    }

	public void RemplirTable() throws NamingException {
		String jndiName = "infinity_server-ear/infinity_server-ejb/BondsOffersServices!tn.esprit.infinity_server.interfaces.BondsOffers";
		Context ctx = new InitialContext();
		tn.esprit.infinity_server.interfaces.BondsOffers proxy = (tn.esprit.infinity_server.interfaces.BondsOffers) ctx
				.lookup(jndiName);

		colDenomination.setCellValueFactory(new PropertyValueFactory("denomination"));
		colSecurityNumber.setCellValueFactory(new PropertyValueFactory("securityNumber"));
		colAdress.setCellValueFactory(new PropertyValueFactory("adress"));
		colCouponRate.setCellValueFactory(new PropertyValueFactory("couponRate"));
		colFrequencyRate.setCellValueFactory(new PropertyValueFactory("frequencyRate"));
		colMaturityDate.setCellValueFactory(new PropertyValueFactory("maturityDate"));
		colIssueDate.setCellValueFactory(new PropertyValueFactory("issueDate"));
		colOwner.setCellValueFactory(new PropertyValueFactory("owner"));
		colPrintDate.setCellValueFactory(new PropertyValueFactory("printDate"));
		colPrintPlace.setCellValueFactory(new PropertyValueFactory("printPlace"));
		colSeries.setCellValueFactory(new PropertyValueFactory("series"));
		colInterestRate.setCellValueFactory(new PropertyValueFactory("interestRate"));

		BondsOffers b = new BondsOffers();
		List<BondsOffers> o = proxy.findAllBondsOffers();
		for (BondsOffers e : o) {
			listbondsoffers.add(e);
		}
		bondsofferstabel.setItems(listbondsoffers);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			RemplirTable();
			

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
