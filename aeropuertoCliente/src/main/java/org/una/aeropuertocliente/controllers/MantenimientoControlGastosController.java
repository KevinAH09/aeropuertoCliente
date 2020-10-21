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

/**
 * FXML Controller class
 *
 * @author cfugu
 */
public class MantenimientoControlGastosController extends Controller implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private Label titulo;
    @FXML
    private Label lbltxtId;
    @FXML
    private JFXTextField txtId;
    @FXML
    private Label lbltxtNombre;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private Label lblTxtResponsable;
    @FXML
    private JFXTextField txtResponsable;
    @FXML
    private Label lblCmbEstado;
    @FXML
    private JFXComboBox<String> cmbEstado;
    @FXML
    private Label lblcmbEsatdo;
    @FXML
    private Label lblFechaRegistro;
    @FXML
    private JFXTextField txtFecha;
    @FXML
    private Label lbltxtMonto;
    @FXML
    private JFXTextField txtMonto;
    @FXML
    private Label lbltxtArea;
    @FXML
    private Label lblbtnArea;
    @FXML
    private JFXButton btnArea;
    @FXML
    private Label lblTitulo2;
    @FXML
    private Label lbltxtObservacion;
    @FXML
    private Label lbltxtTipo;
    @FXML
    private JFXTextField txtTipoServico;
    @FXML
    private Label lbltxtDuracion;
    @FXML
    private JFXTextField txtDuracion;
    @FXML
    private Label lbltxtPeridiocidad;
    @FXML
    private JFXTextField txtPeridiocidad;
    @FXML
    private Label lblCancelar;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private Label lblGuardar;
    @FXML
    private JFXButton btnRegistrar;
    @FXML
    private JFXTextField txtObservacion;
    @FXML
    private JFXComboBox<String> cmbEstadoPago;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onActionAreas(ActionEvent event) {
    }


    @FXML
    private void onActionCancelar(ActionEvent event) {
    }

    @FXML
    private void onActionRegistrar(ActionEvent event) {
    }

    @Override
    public void initialize() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
