/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Bosco
 */
public class MantenimientoAvionController implements Initializable {

    @FXML
    private TextField txtTipoAvion;
    @FXML
    private TextField txtZona;
    @FXML
    private Button btnZona;
    @FXML
    private TextField txtFiltrar;
    @FXML
    private Button btnFiltrar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TableView<?> tableview;
    @FXML
    private Label labTituloAviones;
    @FXML
    private Label labTipoAvion;
    @FXML
    private Label labTxtFildTipoAvion;
    @FXML
    private Label labTxtMatricula;
    @FXML
    private Label labTxtFildMatricula;
    @FXML
    private Label labTxtEstado;
    @FXML
    private Label labCombEstado;
    @FXML
    private Label labTxtZona;
    @FXML
    private Label labTxtFildZona;
    @FXML
    private Label labBtnAgregar;
    @FXML
    private Label labTxtVuelo;
    @FXML
    private Label labTxtFildVuelo;
    @FXML
    private Label labCombFiltro;
    @FXML
    private Label labBtnFiltro;
    @FXML
    private Label labBtnCancelar;
    @FXML
    private Label labTable;
    @FXML
    private Label labBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
