package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class TestController implements Initializable {
	
	@FXML
	public void login(ActionEvent event)
	{
		System.out.print("clicked");
	}
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

}
