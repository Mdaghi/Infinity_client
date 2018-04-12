/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import tn.esprit.infinity_server.interfaces.UserRemote;
import tn.esprit.infinity_server.persistence.Client;
import tn.esprit.infinity_server.persistence.Trader;
import tn.esprit.infinity_server.persistence.User;
import tn.esprit.infinity_server.services.SymboleService;

/**
 * FXML Controller class
 *
 * @author Mdaghi
 */
public class StatistiqueController implements Initializable {

	@FXML
	private Label lbTitulo;
	@FXML
	private GridPane telaCadastro;
	@FXML
	private Text nbrtrader;
	@FXML
	private Text nbrclient;
	@FXML
	private Text moyenne;
	@FXML
	private PieChart pie;

	UserRemote userProxy;
	ObservableList<PieChart.Data> dataStat = FXCollections.observableArrayList();

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		try {
			stat();
		} catch (NamingException e) {
			Logger.getLogger(SymboleService.class.getName()).log(Level.WARNING, " init "+e);
		}
	}

	public void stat() throws NamingException {

		String jndiName = "infinity_server-ear/infinity_server-ejb/UserService!tn.esprit.infinity_server.interfaces.UserRemote";
		Context context = new InitialContext();
		userProxy = (UserRemote) context.lookup(jndiName);
		//

		ArrayList<User> lst = (ArrayList<User>) userProxy.getAllClient();
		int compteClient = 0;
		int compteTrader = 0;
		for (User u : lst) {
			if (u instanceof Client)
				compteClient++;
			else if (u instanceof Trader)
				compteTrader++;
		}
		dataStat.add(new PieChart.Data("Client ", compteClient));
		dataStat.add(new PieChart.Data("Trader ", compteTrader));
		pie.setData(dataStat);

		nbrtrader.setText("Number Trader :" + compteTrader);
		nbrclient.setText("Number Client :" + compteClient);
		if(compteTrader!=0)
		moyenne.setText("portion : Trader per client  " +  ((double)compteClient /compteTrader));

		// Title
		pie.setTitle("Average clients per trader ");

		/****
		 * final Label caption = new Label("");
		 * caption.setTextFill(Color.DARKORANGE); caption.setStyle(
		 * "-fx-font: 24 arial;");
		 * 
		 * for (final PieChart.Data data : pie.getData()) {
		 * data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new
		 * EventHandler<MouseEvent>() {
		 * 
		 * @Override public void handle(MouseEvent e) {
		 *           caption.setTranslateX(e.getSceneX());
		 *           caption.setTranslateY(e.getSceneY());
		 *           caption.setText(String.valueOf(data.getPieValue()) + "%");
		 *           } }); }
		 ****/

	}

}
