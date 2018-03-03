/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Mdaghi
 */
public class ClientController implements Initializable {

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
    private ToggleButton btRelatorios;
    @FXML
    private VBox boxLocalizacao;
    @FXML
    private ToggleButton btUtilitarios;
    @FXML
    private VBox boxUtilitarios;
    @FXML
    private ToggleGroup grupoUtilidades;
    @FXML
    private Label lbUser;
    @FXML
    private Label lbMensagem;
    @FXML
    private VBox boxNotas;
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
    private ToggleGroup grupoUtilidades2;
    @FXML
    private ToggleGroup grupoUtilidades1;
    @FXML
    private ToggleButton btnProfile;
    @FXML
    private ToggleButton btnActivate;
    @FXML
    private ToggleButton btnDesactivate;
    @FXML
    private AnchorPane container;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void menuDashboard(MouseEvent event) {
    }

    @FXML
    private void menuCatalogacao(ActionEvent event) {
    }

    @FXML
    private void subCatalogar(ActionEvent event) {
    }

    @FXML
    private void subDesignacao(ActionEvent event) {
    }

    @FXML
    private void subEstratigrafia(ActionEvent event) {
    }

    @FXML
    private void subColecao(ActionEvent event) {
    }

    @FXML
    private void menuVisitas(ActionEvent event) {
    }

    @FXML
    private void subVisitantes(ActionEvent event) {
    }

    @FXML
    private void subInstituicao(ActionEvent event) {
    }


    @FXML
    private void menuEmprestimo(ActionEvent event) {
    }


    @FXML
    private void menuRelatorios(ActionEvent event) {
    }


    @FXML
    private void menuUtilitario(ActionEvent event) {
    }


    @FXML
    private void menuSair(ActionEvent event) {
    }

    @FXML
    private void siteMuseu(ActionEvent event) {
    }

    @FXML
    private void Profile(ActionEvent event) {
    }

    @FXML
    private void Activate(ActionEvent event) {
    }

    @FXML
    private void Desactivate(ActionEvent event) {
    }
    
}
