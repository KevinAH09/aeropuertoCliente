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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author cfugu
 */
public class CambiarContrasenaController implements Initializable {

    @FXML
    private Label lblZonas;
    @FXML
    private Label titulo;
    @FXML
    private Label lbltxtNombre;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private Label lbltxtCedula;
    @FXML
    private JFXTextField txtCedula;
    @FXML
    private Label lblCmbEstado;
    @FXML
    private JFXComboBox<?> cmbEstado;
    @FXML
    private Label lblcmbRol;
    @FXML
    private JFXComboBox<?> cmbRoles;
    @FXML
    private Label lblContrasena;
    @FXML
    private Label lbltxtContrasena;
    @FXML
    private PasswordField txtPassOculto;
    @FXML
    private TextField txtPassMostrado;
    @FXML
    private Label lblMostrar;
    @FXML
    private ImageView imgViewPassword;
    @FXML
    private ImageView imgNotPassword;
    @FXML
    private Label lblRegistrar;
    @FXML
    private JFXButton btnRegistrar;
    @FXML
    private Label lblCancelar;
    @FXML
    private JFXButton btnCancelar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void actionViewPass(MouseEvent event) {
    }

    @FXML
    private void onActionRegistrar(ActionEvent event) {
    }

    @FXML
    private void onActionCancelar(ActionEvent event) {
    }
    
}
