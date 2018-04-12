package controller;

import java.awt.Label;
import java.net.URL;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import tn.esprit.infinity_server.interfaces.BondsOffers;

public class SuggestionController implements Initializable {

	@FXML
    private javafx.scene.control.Label t_result;


    @FXML
    private javafx.scene.control.Label t_maxcoupon;
  
    @FXML
    private javafx.scene.control.Label  t_maxIssueDate;

   

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String jndiName = "infinity_server-ear/infinity_server-ejb/BondsOffersServices!tn.esprit.infinity_server.interfaces.BondsOffers";
    	Context context;
		try {
			context = new InitialContext();
	    	BondsOffers proxy;

			proxy = (BondsOffers) context.lookup(jndiName);
			System.out.println(proxy.totalOfBonds());
			t_result.setText(String.valueOf(proxy.minCouponRate()));
			t_maxcoupon.setText(String.valueOf(proxy.maxCouponRate()));
			t_maxIssueDate.setText(String.valueOf(proxy.minIssueDate()));
			


		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
}
