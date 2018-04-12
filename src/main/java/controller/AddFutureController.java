/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.infinity_server.interfaces.FutureContractServiceRemote;
import tn.esprit.infinity_server.interfaces.SymboleServiceRemote;
import tn.esprit.infinity_server.persistence.FutureContract;
import tn.esprit.infinity_server.persistence.Symbole;
import tn.esprit.infinity_server.util.Future;
import tn.esprit.infinity_server.util.FutureJson;

/**
 * FXML Controller class
 *
 * @author Mdaghi
 */
public class AddFutureController implements Initializable {

	@FXML
	private Label currentPrice;
	@FXML
	private ComboBox<String> comboName;
	@FXML
	private DatePicker maturityDate;
	@FXML
	private TextField maturityPrice;
	@FXML
	private TextField bershellNumber;
	@FXML
	private Button btnAdd;
	@FXML
	private Label lbValidation;
	@FXML
	private Label maturityPrice1;

	SymboleServiceRemote symboleProxy;
	
	FutureContractServiceRemote futureContractProxy;

	private ObservableList<String> symboleObser = FXCollections.observableArrayList();

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		try {
			initCombo();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void add(ActionEvent event) throws NamingException {
		if(!validationDate())
			return;
		/**************************/
		FutureContract futureContract = new FutureContract();
		LocalDate date = maturityDate.getValue();
		java.sql.Date dateMaturite = java.sql.Date.valueOf(date);
		/**************************/
		String jndiName = "infinity_server-ear/infinity_server-ejb/SymboleService!tn.esprit.infinity_server.interfaces.SymboleServiceRemote";
		Context context = new InitialContext();
		symboleProxy = (SymboleServiceRemote) context.lookup(jndiName);
		Symbole symbole = symboleProxy.getSymboleByName(comboName.getValue());
		/**************************/
		futureContract.setDateMaturite(dateMaturite);
		futureContract.setMaturityPrice(Double.parseDouble(maturityPrice.getText()));
		futureContract.setSize(Integer.parseInt(bershellNumber.getText()));
		futureContract.setName(comboName.getValue());
		futureContract.setSymbole(symbole);
		/**************************/
		jndiName = "infinity_server-ear/infinity_server-ejb/FutureContractService!tn.esprit.infinity_server.interfaces.FutureContractServiceRemote";
		futureContractProxy = (FutureContractServiceRemote) context.lookup(jndiName);
		futureContractProxy.createFutureContract(futureContract);
		Stage primaryStage = (Stage) comboName.getScene().getWindow();
		primaryStage.close();
		FutureContractController.getDataTimer().play();

	}
	
	@FXML
    private void select(ActionEvent event) throws NamingException {
		String jndiName = "infinity_server-ear/infinity_server-ejb/SymboleService!tn.esprit.infinity_server.interfaces.SymboleServiceRemote";
		Context context = new InitialContext();
		symboleProxy = (SymboleServiceRemote) context.lookup(jndiName);
		FutureJson futureJson = new FutureJson();
		List<Future> lst = futureJson.showFutureData(comboName.getValue());
		currentPrice.setText(lst.get(0).getLastPrice());
    }

	public void initCombo() throws NamingException {
		String jndiName = "infinity_server-ear/infinity_server-ejb/SymboleService!tn.esprit.infinity_server.interfaces.SymboleServiceRemote";
		Context context = new InitialContext();
		symboleProxy = (SymboleServiceRemote) context.lookup(jndiName);
		List<Symbole> lst = symboleProxy.findAllSymbole();
		for (Symbole s : lst)
		{
			symboleObser.add(s.getName());
		}
		comboName.setItems(symboleObser);
	}
	public boolean validationDate()
	{
		LocalDate date = maturityDate.getValue();
		java.sql.Date maturitydate = java.sql.Date.valueOf(date);
		java.util.Date currentDate = new java.util.Date();
		return maturitydate.after(currentDate);
		
	}

}
