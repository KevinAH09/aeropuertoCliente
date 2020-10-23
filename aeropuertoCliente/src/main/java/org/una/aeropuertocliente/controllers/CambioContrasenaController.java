/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.controllers;

import com.jfoenix.controls.JFXButton;
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
 * @author colo7
 */
public class CambioContrasenaController extends Controller implements Initializable {

    @FXML
    private Label lblNombre;
    @FXML
    private Label lblCedula;
    @FXML
    private JFXTextField txtcontrasena;
    @FXML
    private JFXTextField txtConfirmarcontrasena;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXButton btnCambiar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void actionCancelar(ActionEvent event) {
    }

    @FXML
    private void actionCambiar(ActionEvent event) {
    }

    @Override
    public void initialize() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
