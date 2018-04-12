package controller;



import java.beans.EventHandler;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import tn.esprit.infinity_server.interfaces.BondsOffers;
import tn.esprit.infinity_server.persistence.Contact;
public class ContactAddController implements Initializable{
	
	@FXML
    private ToggleButton btCatalogacao;

    @FXML
    private ToggleGroup grupoMenus;

    @FXML
    private VBox boxCatalogacao;

    @FXML
    private ToggleButton btCatalogar;

    @FXML
    private ToggleGroup grupoCatalogacao;

    @FXML
    private ToggleButton btDesginacao;

    @FXML
    private ToggleButton btEstratigrafia;

    @FXML
    private ToggleButton btColecao;

    @FXML
    private ToggleButton btVisitas;

    @FXML
    private VBox boxVisitas;

    @FXML
    private ToggleButton btVisitantes;

    @FXML
    private ToggleGroup grupoVisitantes;

    @FXML
    private ToggleButton btInstituicao;

    @FXML
    private ToggleButton btEmprestimos;

    @FXML
    private VBox boxEmprestimo;

    @FXML
    private ToggleButton btInstituicao1;

    @FXML
    private ToggleGroup grupoVisitantes1;

    @FXML
    private ToggleButton btInstituicao11;

    @FXML
    private ToggleGroup grupoVisitantes11;

    @FXML
    private ToggleButton btInstituicao111;

    @FXML
    private ToggleGroup grupoVisitantes111;

    @FXML
    private ToggleButton btRelatorios;

    @FXML
    private VBox boxLocalizacao;

    @FXML
    private ToggleButton btUtilitarios;

    @FXML
    private VBox boxUtilitarios;

    @FXML
    private ToggleButton btnProfile;

    @FXML
    private ToggleGroup grupoUtilidades2;

    @FXML
    private ToggleButton btnActivate;

    @FXML
    private ToggleGroup grupoUtilidades;

    @FXML
    private ToggleButton btnDesactivate;

    @FXML
    private ToggleGroup grupoUtilidades1;

    @FXML
    private ToggleButton btUtilitarios1;

    @FXML
    private ToggleGroup grupoMenus1;

    @FXML
    private Label lbUser;

    @FXML
    private AnchorPane container;

    @FXML
    private Label lbMensagem;

    @FXML
    private VBox boxNotas;
    
    @FXML
    private TextField content;
    
    @FXML
    private TextField firstname;

    @FXML
    private TextField lastname;
    
    @FXML
    private TextField email;

    @FXML
    private TextField phonenumber;

    @FXML
    private DatePicker birthdate;
    
    @FXML
    private ImageView fb_img;
    
    @FXML
    private ComboBox<String> combosubject;
    

    @FXML
    private Button btn1;

    @FXML
    void Activate(ActionEvent event) {

    }
    
    @FXML
    void goFacebook(MouseEvent event) {
    	Hyperlink link = new Hyperlink();
    	link.setText("http://www.facebook.com");
    

    }

    @FXML
    void Desactivate(ActionEvent event) {

    }
    
    @FXML
    void fb(ActionEvent event) {

    	Hyperlink link = new Hyperlink();
    	link.setText("http://www.facebook.com");
    	
    	
    }

    @FXML
    void Profile(ActionEvent event) {

    }

    @FXML
    void menuCatalogacao(ActionEvent event) {

    }

    @FXML
    void menuDashboard(MouseEvent event) {

    }

    @FXML
    void menuEmprestimo(ActionEvent event) {

    }

    @FXML
    void menuRelatorios(ActionEvent event) {

    }

    @FXML
    void menuSair(ActionEvent event) {

    }

    @FXML
    void menuUtilitario(ActionEvent event) {

    }

    @FXML
    void menuVisitas(ActionEvent event) {

    }

    @FXML
    void siteMuseu(ActionEvent event) {

    }

    @FXML
    void subCatalogar(ActionEvent event) {

    }

    @FXML
    void subColecao(ActionEvent event) {

    }

    @FXML
    void subDesignacao(ActionEvent event) {

    }

    @FXML
    void subEstratigrafia(ActionEvent event) {

    }

    @FXML
    void subInstituicao(ActionEvent event) {

    }

    @FXML
    void subVisitantes(ActionEvent event) {

    }

    @FXML
    void addContact(ActionEvent event) throws NamingException {
    	 
    	Date date = null;
    	String jndiName="infinity_server-ear/infinity_server-ejb/ContactServices!tn.esprit.infinity_server.interfaces.Contact";
    	Context context = new InitialContext();
    	tn.esprit.infinity_server.interfaces.Contact proxy = (tn.esprit.infinity_server.interfaces.Contact) context.lookup(jndiName);
    	Contact contact = new Contact();
    	contact.setFirstName(firstname.getText());
    	contact.setLastName(lastname.getText());
    	contact.setContent(content.getText());
    	contact.setSubject(combosubject.getSelectionModel().getSelectedItem());
    	contact.setEmail(email.getText());
    	//contact.setContactDate(date);
    	contact.setPhoneNumber(Integer.parseInt(phonenumber.getText()));
    	contact.setBirthDate(java.sql.Date.valueOf(birthdate.getValue()));
    	contact.setStatuts("Not Yet");
    	proxy.addContact(contact);
    	


    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		combosubject.getItems().setAll("Fraude","Incident","Inappropriate behavior","Other");

		
	}


}
