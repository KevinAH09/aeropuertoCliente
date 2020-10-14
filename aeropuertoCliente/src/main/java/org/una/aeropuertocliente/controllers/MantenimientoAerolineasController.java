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
public class MantenimientoAerolineasController implements Initializable {

    @FXML
    private JFXTextField txtId;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextField txtResponsable;
    @FXML
    private JFXComboBox<?> cmbEstado;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private TableView<?> tableAviones;
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblId;
    @FXML
    private Label lbltxtId;
    @FXML
    private Label lblNombre;
    @FXML
    private Label lbltxtNombre;
    @FXML
    private Label titulo;
    @FXML
    private Label id;
    @FXML
    private Label nombre;
    @FXML
    private Label lblResponsable;
    @FXML
    private Label responsable;
    @FXML
    private Label lblTxtResponsable;
    @FXML
    private Label lblEstado;
    @FXML
    private Label estado;
    @FXML
    private Label lblCmbEstado;
    @FXML
    private Label lblGuardar;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private Label lblCancelar;
    @FXML
    private Label lblEditar;
    @FXML
    private Label lblTable;
    @FXML
    private Label lblRegistrar;
    @FXML
    private JFXButton btnRegistrar;

  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onActionGuardar(ActionEvent event) {
    }

    @FXML
    private void onActionCancelar(ActionEvent event) {
    }

    @FXML
    private void onActionEditar(ActionEvent event) {
    }

    @FXML
    private void onActionRegistrar(ActionEvent event) {
    }
    
}
