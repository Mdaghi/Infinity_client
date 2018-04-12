package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.sun.glass.events.KeyEvent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import tn.esprit.infinity_server.persistence.BondsOffers;
import tn.esprit.infinity_server.persistence.Contact;

public class ContactDetailsController implements Initializable {

    @FXML
    private Label lbTitulo;

    @FXML
    private TextField rechercheTF;

    @FXML
    private TextField firstname;

    @FXML
    private TextField subject;

    @FXML
    private TextField email;

    @FXML
    private TextField lastname;

    @FXML
    private TextField content;

    @FXML
    private TextField phonenumber;

    @FXML
    private TextField age;

    @FXML
    private TextField contactdate;

    @FXML
    private TextField statuts;

    @FXML
    private Label legenda;

    @FXML
    private Button deletebutton;

    @FXML
    private TableView<Contact> contacttabel;

    @FXML
    private TableColumn<?, ?> colFirstName;

    @FXML
    private TableColumn<?, ?> colLastName;

    @FXML
    private TableColumn<?, ?> colAge;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colPhoneNumber;

    @FXML
    private TableColumn<?, ?> colContactDate;

    @FXML
    private TableColumn<?, ?> colsubject;

    @FXML
    private TableColumn<?, ?> colContent;

    @FXML
    private TableColumn<?, ?> colAnswer;
    
    @FXML
    private TableColumn<?, ?> colBirthDate;

    @FXML
    private TableColumn<?, ?> colStatuts;

    @FXML
    private TextArea answer;
    
	ObservableList<Contact> listcontact = FXCollections.observableArrayList();


    @FXML
    void contactdetails(MouseEvent event) {
    	

		if (contacttabel.getSelectionModel().getSelectedItem() != null) {
			Contact c = contacttabel.getSelectionModel().getSelectedItem();
			firstname.setText(c.getFirstName() + "");
			lastname.setText(c.getLastName() + "");
			statuts.setText(c.getStatuts() + "");
			email.setText(c.getEmail() + "");
		}

    }

    @FXML
    void rech(KeyEvent event) {

    }

    @FXML
    void remover(ActionEvent event) throws NamingException {

    	String jndiName="infinity_server-ear/infinity_server-ejb/ContactServices!tn.esprit.infinity_server.interfaces.Contact";
    	Context context = new InitialContext();
    	tn.esprit.infinity_server.interfaces.Contact proxy = (tn.esprit.infinity_server.interfaces.Contact) context.lookup(jndiName);
    	proxy.removeContact(contacttabel.getSelectionModel().getSelectedItem().getId());
    	contacttabel.getItems().clear();
		RemplirTable();

    }

//    @FXML
//    void update(ActionEvent event) throws NamingException, MessagingException {
//
//    	String email_api;
//    	String answer_mail;
//    	String jndiName="infinity_server-ear/infinity_server-ejb/ContactServices!tn.esprit.infinity_server.interfaces.Contact";
//    	Context context = new InitialContext();
//    	tn.esprit.infinity_server.interfaces.Contact proxy = (tn.esprit.infinity_server.interfaces.Contact) context.lookup(jndiName);
//    	 if (contacttabel.getSelectionModel().getSelectedItem() != null)
//    	 {
//    		
//    		 answer_mail=answer.getText();
//    		 email_api=email.getText();
//    		 Contact c = contacttabel.getSelectionModel().getSelectedItem();
//    		 c.setAnswer(answer.getText());
//    		 c.setStatuts("Traited");
//    		 proxy.updateContact(c);
//    		 contacttabel.refresh();
//    		 SendMail s = new SendMail("Infinity Contact",email_api,answer_mail);
//    	 }
//
//    }

    
    void RemplirTable() throws NamingException{

    	String jndiName="infinity_server-ear/infinity_server-ejb/ContactServices!tn.esprit.infinity_server.interfaces.Contact";
    	Context context = new InitialContext();
    	tn.esprit.infinity_server.interfaces.Contact proxy = (tn.esprit.infinity_server.interfaces.Contact) context.lookup(jndiName);
    	colFirstName.setCellValueFactory(new PropertyValueFactory("FirstName"));
    	colLastName.setCellValueFactory(new PropertyValueFactory("LastName"));
    	colBirthDate.setCellValueFactory(new PropertyValueFactory("BirthDate"));
    	colContactDate.setCellValueFactory(new PropertyValueFactory("ContactDate"));
    	colPhoneNumber.setCellValueFactory(new PropertyValueFactory("PhoneNumber"));
    	colEmail.setCellValueFactory(new PropertyValueFactory("Email"));
    	colAnswer.setCellValueFactory(new PropertyValueFactory("Answer"));
    	colsubject.setCellValueFactory(new PropertyValueFactory("Subject"));
    	colContent.setCellValueFactory(new PropertyValueFactory("Content"));
    	
    	colStatuts.setCellValueFactory(new PropertyValueFactory("statuts"));
    	Contact c = new Contact();
    	List<Contact> o = proxy.findAllContact();
    	for (Contact e : o) {
			listcontact.add(e);
		}
		contacttabel.setItems(listcontact);
    	
    	
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		try {
			RemplirTable();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
