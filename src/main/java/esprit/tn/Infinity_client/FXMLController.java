package esprit.tn.Infinity_client;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import tn.esprit.infinity_server.interfaces.AddressRemote;
import tn.esprit.infinity_server.interfaces.BondsOffers;
import tn.esprit.infinity_server.persistence.Address;
public class FXMLController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws NamingException {
    	String jndiName="infinity_server-ear/infinity_server-ejb/ServiceAddress!tn.esprit.infinity_server.interfaces.AddressRemote";
		Context context=new InitialContext();
		AddressRemote proxy=(AddressRemote)context.lookup(jndiName);
		//
		Address address = new Address();
		address.setCity("Tunis");
		address.setCountry("Tunisia");
		address.setNumber(9);
		address.setPostalCode("2048");
		address.setStreet("Habib bourguiba");
		//
		proxy.CreateAddress(address);
        label.setText("Insert the first Address in DB with success ");
    }
    @FXML
    private void addbondsoffers(ActionEvent event) throws NamingException {
    	Long l=(long) 0;
		Float f=(float) 0.0;
		Date date =new Date();
    	String jndiName="infinity_server-ear/infinity_server-ejb/BondsOffersServices!tn.esprit.infinity_server.interfaces.BondsOffers";
		Context context=new InitialContext();
        BondsOffers proxy=(BondsOffers)context.lookup(jndiName);
        tn.esprit.infinity_server.persistence.BondsOffers bondsoffers = new tn.esprit.infinity_server.persistence.BondsOffers();
    	bondsoffers.setAddress("a");
		bondsoffers.setCouponRate(f);
		bondsoffers.setDenomination(l);
		bondsoffers.setFrequencyRate("a");
		bondsoffers.setMaturityDate(date);
		bondsoffers.setImgPath("a");
		bondsoffers.setOwner("a");
		bondsoffers.setInterestRate("a");
		bondsoffers.setPrintPlace("a");
		bondsoffers.setSecurityNumber(l);
		bondsoffers.setIssueDate(date);
		bondsoffers.setSeries("a");
		bondsoffers.setPrintDate(date);
		proxy.addBondsOffers(bondsoffers);
    }
    @FXML
    private void deleteBondsOffers(ActionEvent event) throws NamingException {

    	String jndiName="infinity_server-ear/infinity_server-ejb/BondsOffersServices!tn.esprit.infinity_server.interfaces.BondsOffers";
		Context context=new InitialContext();
        BondsOffers proxy=(BondsOffers)context.lookup(jndiName);
        
        proxy.removeBondsOffers(1);
    
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	Long l=(long) 0;
		Float f=(float) 0.0;
		Date date =new Date();
    	String jndiName="infinity_server-ear/infinity_server-ejb/BondsOffersServices!tn.esprit.infinity_server.interfaces.BondsOffers";
		Context context;
		try {
			context = new InitialContext();
			BondsOffers proxy=(BondsOffers)context.lookup(jndiName);
	        tn.esprit.infinity_server.persistence.BondsOffers bondsoffers = new tn.esprit.infinity_server.persistence.BondsOffers();
	    	bondsoffers.setId(2);
	        bondsoffers.setAddress("b");
			bondsoffers.setCouponRate(f);
			bondsoffers.setDenomination(l);
			bondsoffers.setFrequencyRate("b");
			bondsoffers.setMaturityDate(date);
			bondsoffers.setImgPath("b");
			bondsoffers.setOwner("b");
			bondsoffers.setInterestRate("a");
			bondsoffers.setPrintPlace("a");
			bondsoffers.setSecurityNumber(l);
			bondsoffers.setIssueDate(date);
			bondsoffers.setSeries("a");
			bondsoffers.setPrintDate(date);
			proxy.updateBondsOffers(bondsoffers);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	
		
	
		
    }    
}
