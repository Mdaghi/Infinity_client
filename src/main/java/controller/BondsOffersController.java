package controller;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import tn.esprit.infinity_server.interfaces.BondsOffers;

public class BondsOffersController implements Initializable {

	@FXML
    private AnchorPane container;

    @FXML
    private Button button;

    @FXML
    private TextField securitynumber;

    @FXML
    private TextField series;

    @FXML
    private TextField interestrate;

    @FXML
    private TextField couponrate;

    @FXML
    private TextField owner;

    @FXML
    private TextField printplace;

    @FXML
    private TextField denomination;

    @FXML
    private TextField frequencyrate;

    @FXML
    private TextField adress;

    @FXML
    private DatePicker issuedate;

    @FXML
    private DatePicker printdate;

    @FXML
    private DatePicker maturitydate;

    @FXML
    void addbondsoffers(ActionEvent event) throws NamingException {
    	String jndiName = "infinity_server-ear/infinity_server-ejb/BondsOffersServices!tn.esprit.infinity_server.interfaces.BondsOffers";
    	Context context = new InitialContext();
    	BondsOffers proxy = (BondsOffers) context.lookup(jndiName);
    	tn.esprit.infinity_server.persistence.BondsOffers bondsoffer = new tn.esprit.infinity_server.persistence.BondsOffers(); 
        bondsoffer.setAddress(adress.getText());
        bondsoffer.setCouponRate(Float.parseFloat(couponrate.getText()));
        //bondsoffer.setIssueDate(issuedate.);
        bondsoffer.setDenomination(Long.parseLong(denomination.getText()));
        bondsoffer.setFrequencyRate(frequencyrate.getText());
        bondsoffer.setInterestRate(interestrate.getText());
        bondsoffer.setOwner(owner.getText());
        bondsoffer.setPrintPlace(printplace.getText());
        bondsoffer.setSecurityNumber(Long.parseLong(securitynumber.getText()));
        bondsoffer.setSeries(series.getText());
        //bondsoffer.getMaturityDate(java.util.Date.parse(maturitydate.gettex));
        //bondsoffer.getPrintDate()
        proxy.addBondsOffers(bondsoffer);
        
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		String jndiName = "infinity_server-ear/infinity_server-ejb/BondsOffersServices!tn.esprit.infinity_server.interfaces.BondsOffers";
    	Context context = null;
		try {
			context = new InitialContext();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			BondsOffers proxy = (BondsOffers) context.lookup(jndiName);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}