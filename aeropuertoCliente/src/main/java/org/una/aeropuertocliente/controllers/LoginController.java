/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import org.una.aeropuertocliente.controllers.BaseController;
import java.io.IOException;
import org.una.aeropuertocliente.controllers.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.una.aeropuertocliente.controllers.Controller;
import org.una.aeropuertocliente.dtos.AuthenticationRequest;
import org.una.aeropuertocliente.dtos.AuthenticationResponse;
import org.una.aeropuertocliente.dtos.UsuariosDTO;
import org.una.aeropuertocliente.entitiesServices.LoginService;
import org.una.aeropuertocliente.entitiesServices.UsuariosService;
import org.una.aeropuertocliente.sharedService.Token;
import org.una.aeropuertocliente.utils.FlowController;
import org.una.aeropuertocliente.utils.Mensaje;

/**
 * FXML Controller class
 *
 * @author colo7
 */
public class LoginController extends Controller implements Initializable {

    private final String urlstring = "http://localhost:8099/";
    @FXML
    private JFXTextField txtUsuario;
    @FXML
    private JFXTextField txtPassMostrado;
    @FXML
    private JFXPasswordField txtPassOculto;
    @FXML
    private ImageView imgViewPassword;
    @FXML
    private ImageView imgNotPassword;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXButton btnIngresar;

    private String pass;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtPassMostrado.setVisible(false);
        txtPassOculto.setText(txtPassMostrado.getText());
        txtPassOculto.setVisible(true);
        imgNotPassword.setVisible(false);
        imgViewPassword.setVisible(true);
    }

    @Override
    public void initialize() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void actionViewPass(MouseEvent event) {
        if (txtPassMostrado.isVisible()) {
            txtPassMostrado.setVisible(false);
            txtPassOculto.setText(txtPassMostrado.getText());
            txtPassOculto.setVisible(true);
            imgNotPassword.setVisible(false);
            imgViewPassword.setVisible(true);

        } else {
            txtPassOculto.setVisible(false);
            txtPassMostrado.setText(txtPassOculto.getText());
            txtPassMostrado.setVisible(true);
            imgNotPassword.setVisible(true);
            imgViewPassword.setVisible(false);
        }
    }

    @FXML
    private void actionIngresar(ActionEvent event) {
        if (txtPassMostrado.isVisible()) {
            pass = txtPassMostrado.getText();
        } else {
            pass = txtPassOculto.getText();
        }
        if (!txtPassMostrado.getText().isEmpty() && ((!txtPassMostrado.getText().isEmpty())) || (!txtPassOculto.getText().isEmpty())) {
            Token.setInstance(null);
            AuthenticationRequest authenticationRequest = new AuthenticationRequest(txtUsuario.getText(), pass);
            Token.setInstance(LoginService.login(authenticationRequest));
            if (Token.getInstance() != null) {
                FlowController.getInstance().goView("principal/Principal");
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error de incio de Sesion", null, "La contraseña o cedula estan incorecctas");
            }

        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de incio de Sesion", null, "Por favor complete todos campos");
        }
    }

    @FXML
    private void actionCambioDivisas(ActionEvent event) {
        FlowController.getInstance().goViewInWindowModal("cambioDivisas/CambioDivisas", ((Stage) txtPassOculto.getScene().getWindow()), true);
    }

    @FXML
    private void actionSalir(ActionEvent event) {
        if (new Mensaje().showConfirmation("Cerrar aplicacion", ((Stage) btnCancelar.getScene().getWindow()), "Desea salir de la aplicacion")) {
            ((Stage) btnCancelar.getScene().getWindow()).close();
        }
    }

}
