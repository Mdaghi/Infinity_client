/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import tn.esprit.infinity_server.interfaces.SymboleServiceRemote;
import tn.esprit.infinity_server.persistence.Symbole;
import tn.esprit.infinity_server.services.SymboleService;
import tn.esprit.infinity_server.services.UserService;
import tn.esprit.infinity_server.util.Future;
import tn.esprit.infinity_server.util.FutureJson;
import javafx.scene.chart.XYChart.Series;

/**
 * FXML Controller class
 *
 * @author Mdaghi
 */
public class HistoricDataController implements Initializable {

	@FXML
	private Label lbTitulo;
	@FXML
	private AnchorPane telaEdicao;
	@FXML
	private FlowPane flow;
	@FXML
	private Pagination pagination;
	@FXML
	private TextField txtSearch;

	SymboleServiceRemote symboleProxy;
	
	public static final String JNDI_NAME = "infinity_server-ear/infinity_server-ejb/SymboleService!tn.esprit.infinity_server.interfaces.SymboleServiceRemote";

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		Context context;
		try {
			context = new InitialContext();
			symboleProxy = (SymboleServiceRemote) context.lookup(JNDI_NAME);
		} catch (Exception e) {
			Logger.getLogger(UserService.class.getName()).log(Level.WARNING,
					"Initial Comtext SymboleService erreur : " + e);
		}
		flow.setVgap(11);
		flow.setHgap(11);
		try {
			pagination();
		} catch (NamingException e1) {
			Logger.getLogger(SymboleService.class.getName()).log(Level.WARNING, " pagination : "+ e1);
		}
		List<Symbole> symboles = symboleProxy.findAll(0);
		try {
			remplirAccueil(symboles);
		} catch (IOException e) {
			Logger.getLogger(SymboleService.class.getName()).log(Level.WARNING, " remplir Accueil : "+ e);
		}
		
	}

	
	@SuppressWarnings("unchecked")
	public void remplirAccueil(List<Symbole> symboles) throws IOException {
		flow.getChildren().clear();
		int count = 0;
		for (Symbole s : symboles) {
			if (count == 2) {
				break;
			}
			AnchorPane paneVolume = FXMLLoader.load(getClass().getResource("/fxml/view/CourbeVolume.fxml"));
			AnchorPane paneClose = FXMLLoader.load(getClass().getResource("/fxml/view/Courbe.fxml"));
			/*********/
			ObservableList<XYChart.Series<String, Double>> answerVolume = FXCollections.observableArrayList();
			ObservableList<XYChart.Series<String, Double>> answerClose = FXCollections.observableArrayList();
			Series<String, Double> seriesClose = new Series<>();
			Series<String, Double> seriesVolume = new Series<>();
			LineChart<String, Double> chartVolume = (LineChart<String, Double>) paneVolume.lookup("#volumechart");
			LineChart<String, Double> chartClose = (LineChart<String, Double>) paneClose.lookup("#historychart");
			FutureJson futureJson = new FutureJson();
			List<Future> lstFuture = futureJson.showHistoriqueDataBySymbole(s.getName());
			/********/
			for (Future future : lstFuture) {

				seriesVolume.getData().add(new XYChart.Data<String, Double>(future.getTradeTimestamp(),
						Double.parseDouble(future.getVolume())));
				seriesClose.getData().add(new XYChart.Data<String, Double>(future.getTradeTimestamp(),
						Double.parseDouble(future.getClose())));
			}

			seriesVolume.setName("volume");
			chartVolume.getXAxis().setLabel("Period");
			chartVolume.getYAxis().setLabel("Value");
			chartVolume.setTitle("Volume Historic data about : " + s.getName());
			/********/
			seriesClose.setName("Price");
			chartClose.getXAxis().setLabel("Period");
			chartClose.getYAxis().setLabel("Value");
			chartClose.setTitle("Price Historic data about : " + s.getName());
			/*********/
			answerVolume.addAll(seriesVolume);
			answerClose.addAll(seriesClose);
			/********/

			chartVolume.getData().addAll(answerVolume);
			chartClose.getData().addAll(seriesClose);
			flow.getChildren().add(paneVolume);
			flow.getChildren().add(paneClose);
			count++;
		}
	}

	public void pagination() throws NamingException {
		Context context = new InitialContext();
		symboleProxy = (SymboleServiceRemote) context.lookup(JNDI_NAME);
		
		int nbr = (int) symboleProxy.countSymbole();
		int nbrpage = Math.round( ((float)nbr / 2)) + 1;
		pagination.setMaxPageIndicatorCount(nbrpage);
		pagination.setPageCount(nbrpage);
		pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> {
			try {
				symboleProxy = (SymboleServiceRemote) context.lookup(JNDI_NAME);
			} catch (Exception e1) {
				Logger.getLogger(SymboleService.class.getName()).log(Level.WARNING, " pagination : "+ e1);
			}
			List<Symbole> symboles;
			symboles = symboleProxy.findAll(newIndex.intValue());
			try {
				remplirAccueil(symboles);
			} catch (Exception e) {
				Logger.getLogger(SymboleService.class.getName()).log(Level.WARNING, " remplirAccueil : "+ e);
			}
		});
	}

	@FXML
	public void search() throws IOException, NamingException {
		Context context = new InitialContext();
		symboleProxy = (SymboleServiceRemote) context.lookup(JNDI_NAME);
		
		List<Symbole> symboles = symboleProxy.findBycritere(txtSearch.getText(),0);
		remplirAccueil(symboles);
		paginationCritere(txtSearch.getText());

	}
	
	public void paginationCritere(String critere) throws NamingException {
		Context context = new InitialContext();
		symboleProxy = (SymboleServiceRemote) context.lookup(JNDI_NAME);
	
        int nbr = (int)symboleProxy.countSymboleByCritere(critere);
        int nbrpage = (Math.round( ((float)nbr / 2)) + 1);
        pagination.setMaxPageIndicatorCount(nbrpage);
        pagination.setPageCount(nbrpage);
        pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex)
                -> {
                	List<Symbole> symboles;
        			symboles = symboleProxy.findBycritere(critere,newIndex.intValue());
        			try {
        				remplirAccueil(symboles);
        			} catch (Exception e) {
        				Logger.getLogger(SymboleService.class.getName()).log(Level.WARNING, " remplirAccueil : "+ e);
        			}

        });

    }

}
