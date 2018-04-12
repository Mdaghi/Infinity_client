/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import tn.esprit.infinity_server.interfaces.FutureContractServiceRemote;
import tn.esprit.infinity_server.interfaces.SymboleServiceRemote;
import tn.esprit.infinity_server.persistence.FutureContract;
import tn.esprit.infinity_server.services.FutureContractService;
import tn.esprit.infinity_server.util.Future;
import tn.esprit.infinity_server.util.FutureJson;

/**
 * FXML Controller class
 *
 * @author Mdaghi
 */
public class SpeculationFutureController implements Initializable {

    @FXML
    private Label change;
    @FXML
    private Label changePercent;
    @FXML
    private Label currentPrice;
    @FXML
    private Label maturityPrice;
    @FXML
    private Label gain;
    @FXML
    private Label burshell;
    @FXML
    private Label symbol;
    @FXML
    private Label maturityDate;
    
	SymboleServiceRemote symboleProxy;
	
	FutureContractServiceRemote futureContractProxy;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	try {
    	String jndiName = "infinity_server-ear/infinity_server-ejb/FutureContractService!tn.esprit.infinity_server.interfaces.FutureContractServiceRemote";
    	Context context;
		context = new InitialContext();
		futureContractProxy = (FutureContractServiceRemote) context.lookup(jndiName);
		FutureContract futureContract = futureContractProxy.findFutureContractById(FutureContractController.getIdContract());
		FutureJson futureJson = new FutureJson();
		List<Future> lst = futureJson.showFutureData(futureContract.getName());
		/***/
		Double currentP = Double.parseDouble(lst.get(0).getLastPrice());
		Double maturityP = futureContract.getMaturityPrice();
		Double changePrice = currentP - maturityP;
		Double changeP;
		if(changePrice>0)
		{
			change.setStyle("-fx-text-fill:green");
			gain.setStyle("-fx-text-fill:green");
			changePercent.setStyle("-fx-text-fill:green");
			change.setText("+"+changePrice);
			changeP = (changePrice*100)/(Double)currentP;
			
		}
		else
		{
			change.setStyle("-fx-text-fill:red");
			changePercent.setStyle("-fx-text-fill:red");
			gain.setStyle("-fx-text-fill:red");
			change.setText(String.format("%.2f",changePrice));
			changeP = (changePrice*100)/(Double)maturityP;
		}
		changePercent.setText(String.format("%.2f",changeP));
		/***/
		currentPrice.setText(lst.get(0).getLastPrice());
		maturityPrice.setText(futureContract.getMaturityPrice()+"");
		gain.setText(String.format("%.2f",(futureContract.getSize()*changePrice)));
		burshell.setText(futureContract.getSize()+"");
		symbol.setText(futureContract.getName());
		maturityDate.setText(futureContract.getDateMaturite().toString());
		
    	} catch (NamingException e) {
    		Logger.getLogger(FutureContractService.class.getName()).log(Level.WARNING, "Exception=" + e);
		}
    }    
    
}
