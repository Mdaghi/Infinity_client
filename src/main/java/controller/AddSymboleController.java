/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import tn.esprit.infinity_server.interfaces.SymboleServiceRemote;
import tn.esprit.infinity_server.persistence.Symbole;
import tn.esprit.infinity_server.util.Future;
import tn.esprit.infinity_server.util.FutureJson;

/**
 * FXML Controller class
 *
 * @author Mdaghi
 */
public class AddSymboleController implements Initializable {

	@FXML
	private TextField txtSymbole;
	@FXML
	private Label name;
	@FXML
	private TextArea txtUrl;
	@FXML
	private Button btnAdd;

	@FXML
	private Label lbValidation;

	SymboleServiceRemote symboleProxy;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		btnAdd.setDisable(true);
	}

	@FXML
	private void add(ActionEvent event){
		Symbole symbole = new Symbole();
		symbole.setName(txtSymbole.getText());
		symbole.setUrl(txtUrl.getText());
		symboleProxy.createSymbole(symbole);
		Stage primaryStage = (Stage) txtSymbole.getScene().getWindow();
		primaryStage.close();
		SymboleController.getDataTimer().play();
	}

	@FXML
	private void validation(KeyEvent event) throws NamingException {

		String jndiName = "infinity_server-ear/infinity_server-ejb/SymboleService!tn.esprit.infinity_server.interfaces.SymboleServiceRemote";
		Context context = new InitialContext();
		symboleProxy = (SymboleServiceRemote) context.lookup(jndiName);

		if (txtSymbole.getText().equals("")) {
			btnAdd.setDisable(true);
			name.setText("");
			txtUrl.setText("");
			lbValidation.setText("");
			return;
		}

		if (!symboleProxy.checkUniqueSymbole(txtSymbole.getText())) {
			lbValidation.setStyle("-fx-text-fill: red;");
			lbValidation.setText("symbole already exist");
			btnAdd.setDisable(true);
			name.setText("");
			txtUrl.setText("");
			return;
		}

		FutureJson jsonFuture = new FutureJson();
		List<Future> lst = jsonFuture.showFutureData(txtSymbole.getText());
		if (lst != null) {
			txtUrl.setText("https://marketdata.websol.barchart.com"
					+ "/getQuote.json?apikey=3b317303ebe00e5e3d59400561b0a17d&symbols=" + txtSymbole.getText());
			name.setText(lst.get(0).getName());
			lbValidation.setStyle("-fx-text-fill: green;");
			lbValidation.setText("✓");
			btnAdd.setDisable(false);
		} else {
			name.setText("");
			txtUrl.setText("");
			lbValidation.setStyle("-fx-text-fill: red;");
			lbValidation.setText("invalid symbole ✕");
			btnAdd.setDisable(true);
		}
	}

}
