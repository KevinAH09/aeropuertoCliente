/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
    private Label labTxtFildTipoAvion;
    @FXML
    private Label labTxtFildMatricula;
    @FXML
    private Label labCombEstado;
    @FXML
    private Label labTxtFildZona;
    @FXML
    private Label labBtnAgregar;
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
    @FXML
    private JFXTextField txtMatricula;
    @FXML
    private JFXComboBox<?> combEstado;
    @FXML
    private JFXComboBox<?> combFiltro;
    @FXML
    private JFXButton btnGuardar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void agregarZona(ActionEvent event) {
    }

    @FXML
    private void filtrar(ActionEvent event) {
    }

    @FXML
    private void combFiltro(ActionEvent event) {
    }

    @FXML
    private void btnFiltrar(ActionEvent event) {
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
    }

    @FXML
    private void guardar(ActionEvent event) {
    }
    
}
