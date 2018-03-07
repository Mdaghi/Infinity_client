/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.criteria.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import tn.esprit.infinity_server.interfaces.UserRemote;
import tn.esprit.infinity_server.persistence.Client;
import tn.esprit.infinity_server.persistence.User;
/**
 * FXML Controller class
 *
 * @author Mdaghi
 */
public class ListUserController implements Initializable {

	UserRemote userProxy;
    @FXML
    private Label lbTitulo;
    @FXML
    private GridPane telaCadastro;
    @FXML
    private TextField txtNome;
    @FXML
    private TextArea txtDescricao;
    @FXML
    private AnchorPane telaEdicao;
    @FXML
    private TextField txtSearch;
    @FXML
    private TableView<User> twClient;
    @FXML
    private TableColumn<?, ?> colId;
    @FXML
    private TableColumn<?, ?> colFirstname;
    @FXML
    private TableColumn<?, ?> colLastname;
    @FXML
    private TableColumn<?, ?> colEmail;
    @FXML
    private TableColumn<?, ?> colLogin;
    @FXML
    private TableColumn<?, ?> colPhone;

    ObservableList<User> userObser = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    	try {
			buildData();
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }    
    public void buildData() throws SQLException, NamingException {
    	String jndiName = "infinity_server-ear/infinity_server-ejb/UserService!tn.esprit.infinity_server.interfaces.UserRemote";
		Context context = new InitialContext();
		userProxy = (UserRemote) context.lookup(jndiName);
		//
    	colId.setCellValueFactory(new PropertyValueFactory("id"));
    	colFirstname.setCellValueFactory(new PropertyValueFactory("firstname"));
    	colLastname.setCellValueFactory(new PropertyValueFactory("lastname"));
    	colEmail.setCellValueFactory(new PropertyValueFactory("email"));
    	colLogin.setCellValueFactory(new PropertyValueFactory("login"));
    	colPhone.setCellValueFactory(new PropertyValueFactory("phoneNumber"));
    	ArrayList<User> lst = (ArrayList<User>) userProxy.getAllClient();
    	for(User u : lst)
    	{
    		if(u instanceof Client)
    		{
    			userObser.add(u);
    		}
    	}
    	twClient.setItems(userObser);
    	
    	///////////
    	FilteredList<User> filteredData = new  FilteredList<>(userObser, u -> true);
    	txtSearch.setOnKeyReleased( u ->{
    		txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
    			filteredData.setPredicate((java.util.function.Predicate<? super User>) User -> {
               if (newValue == null || newValue.isEmpty()){
                     return true;
                 }
                String lowerCaseFilter = newValue.toLowerCase();
              
               if (User.getLogin().toLowerCase().contains(CharSequence.class.cast(lowerCaseFilter))){
                     return true;
                 }         
                 return false;
              
                 
             });
         });
         SortedList<User> sortedData = new SortedList<>(filteredData);
         sortedData.comparatorProperty().bind(twClient.comparatorProperty());
         twClient.setItems(sortedData);
         
        });
       
    }
    private void searchDynamic() {
    	

    	
    }

    
}
