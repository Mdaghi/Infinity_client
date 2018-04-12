
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import tn.esprit.infinity_server.services.SymboleService;

/**
 * FXML Controller class
 *
 * @author Mdaghi
 */
public class CourbeVolumeController implements Initializable {

    @FXML
    private LineChart<String,Double> volumechart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	Logger.getLogger(SymboleService.class.getName()).log(Level.WARNING, " initialize ");
    }    
    
}
