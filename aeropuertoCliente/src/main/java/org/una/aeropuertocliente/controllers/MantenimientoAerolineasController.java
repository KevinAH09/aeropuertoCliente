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
    private JFXButton txtGuardar;
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
    
}
