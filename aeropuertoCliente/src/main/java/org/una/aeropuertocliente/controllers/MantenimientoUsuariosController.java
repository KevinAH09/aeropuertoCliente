/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
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
public class MantenimientoUsuariosController implements Initializable {

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
    private Label lbltxtCedula;
    @FXML
    private JFXTextField txtCedula;
    @FXML
    private Label lbltxtCorreo;
    @FXML
    private JFXTextField txtCorreo;
    @FXML
    private Label lblTxtContrasena;
    @FXML
    private JFXPasswordField txtResponsable;
    @FXML
    private Label lblCmbEstado;
    @FXML
    private JFXComboBox<?> cmbEstado;
    @FXML
    private Label lblcmbRol;
    @FXML
    private JFXComboBox<?> cmbRoles;
    @FXML
    private Label lblcmbJefe;
    @FXML
    private Label lbltxtArea;
    @FXML
    private JFXTextField txtArea;
    @FXML
    private Label lblbtnArea;
    @FXML
    private JFXButton btnArea;
    @FXML
    private Label lbltxtFecha;
    @FXML
    private JFXTextField txtFecha;
    @FXML
    private Label lblGuardar;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private Label lblCancelar;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXComboBox<?> combJefe;

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
    private void onActionGuardar(ActionEvent event) {
    }

    @FXML
    private void onActionCancelar(ActionEvent event) {
    }
    
}
