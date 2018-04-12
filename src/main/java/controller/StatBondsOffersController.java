package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import tn.esprit.infinity_server.interfaces.BondsOffers;

public class StatBondsOffersController implements Initializable{

	
	 @FXML
	    private Label lbTitulo;

	    @FXML
	    private GridPane telaCadastro;

	    @FXML
	    private PieChart pie;

	    @FXML
	    private Text nbrtrader;

	    @FXML
	    private Text nbrclient;

	    @FXML
	    private Text Moyenne;

	
	    
	    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public void Stat() throws NamingException {

    	String jndiName = "infinity_server-ear/infinity_server-ejb/BondsOffersServices!tn.esprit.infinity_server.interfaces.BondsOffers";
    	Context context = new InitialContext();
    	BondsOffers proxy = (BondsOffers) context.lookup(jndiName);
		//
		
		
	/*	ArrayList<Bonds> lst = (ArrayList<User>) userProxy.getAllClient();
		int compteClient = 0;
		int compteTrader = 0;
    	for(User u : lst)
    	{
    		if(u instanceof Client)
    			compteClient++;
    		else if(u instanceof Trader)
    			compteTrader++;
    	}
		dataStat.add(new PieChart.Data("Client ",compteClient));
		dataStat.add(new PieChart.Data("Trader ",compteTrader));
		pie.setData(dataStat);
		
		nbrtrader.setText("Number Trader :"+compteTrader);
		nbrclient.setText("Number Client :"+compteClient);
		Moyenne.setText("portion : Trader per client  "+(float)(compteClient/compteTrader));
		
		// Title
		 pie.setTitle("Average clients per trader ");
		 //
		 
		/*final Label caption = new Label("");
		caption.setTextFill(Color.DARKORANGE);
		caption.setStyle("-fx-font: 24 arial;");

		for (final PieChart.Data data : pie.getData()) {
		    data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
		        new EventHandler<MouseEvent>() {
		            @Override public void handle(MouseEvent e) {
		                caption.setTranslateX(e.getSceneX());
		                caption.setTranslateY(e.getSceneY());
		                caption.setText(String.valueOf(data.getPieValue()) + "%");
		             }
		        });
		}*/
      

    }


}
