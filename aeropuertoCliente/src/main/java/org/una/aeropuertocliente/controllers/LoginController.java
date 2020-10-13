/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.controllers;

import java.io.IOException;
import org.una.aeropuertocliente.controllers.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.una.aeropuertocliente.dtos.AuthenticationRequest;
import org.una.aeropuertocliente.utils.Conexion;

/**
 * FXML Controller class
 *
 * @author colo7
 */
public class LoginController extends Controller implements Initializable {

    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtPassOculto;
    @FXML
    private TextField txtPassMostrado;
    @FXML
    private ImageView imgViewPassword;
    @FXML
    private ImageView imgNotPassword;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnIngresar;

     private final String urlstring = "http://localhost:8099/";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
    }

    @Override
    public void initialize() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void actionViewPass(MouseEvent event) {
    }

    @FXML
    private void actionSalir(ActionEvent event) {
    }

    @FXML
    private void actionIngresar(ActionEvent event) {
         try {
            Conexion conexion = new Conexion();
            AuthenticationRequest authenticationRequest = new AuthenticationRequest(txtUsuario.getText(), txtPassMostrado.getText());
            System.out.println(conexion.ObjectLogin(urlstring+"login/login", authenticationRequest));
        } catch (IOException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
