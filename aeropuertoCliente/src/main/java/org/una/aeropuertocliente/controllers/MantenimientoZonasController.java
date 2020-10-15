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
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author cfugu
 */
public class MantenimientoZonasController implements Initializable {

    @FXML
    private Label lblZonas;
    @FXML
    private Label titulo;
    @FXML
    private Label lblNombre;
    @FXML
    private Label nombre;
    @FXML
    private Label lbltxtNombre;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private Label lblCodigo;
    @FXML
    private Label codigo;
    @FXML
    private Label lbltxtCodigo;
    @FXML
    private JFXTextField txtCodigo;
    @FXML
    private Label lblEstado;
    @FXML
    private Label estado;
    @FXML
    private Label lblcmbEstado;
    @FXML
    private JFXComboBox<?> cmbEstado;
    @FXML
    private Label lblDescripcion;
    @FXML
    private Label descripcion;
    @FXML
    private Label lbltxtDescripcion;
    @FXML
    private JFXTextField txtDescripcion;
    @FXML
    private Label lblCancelar;
    @FXML
    private JFXButton btnRegistrar;
    @FXML
    private Label lblGuardar;
    @FXML
    private Label lbltxtBusqueda;
    @FXML
    private JFXTextField txtBusqueda;
    @FXML
    private Label lblTipo;
    @FXML
    private Label busqueda;
    @FXML
    private Label lblCmbFiltro;
    @FXML
    private JFXComboBox<?> cmbFiltro;
    @FXML
    private Label lblFiltrar;
    @FXML
    private JFXButton btnFiltrar;
    @FXML
    private Label lblTable;
    @FXML
    private TableView<?> tableZonas;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onActionCancelar(ActionEvent event) {
    }

    @FXML
    private void onActionGuardar(ActionEvent event) {
    }

    @FXML
    private void onActionFiltrar(ActionEvent event) {
    }
    
}
