package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import tn.esprit.infinity_server.interfaces.AddressRemote;
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
        address = proxy.findAddressById(1);
        System.out.println(address);
        // Test findAllAddress
        System.out.println("Test Liste address");
        List<Address> lst = proxy.findAllAddress();
        for( Address a : lst)
        {
        	System.out.println(a);	
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
