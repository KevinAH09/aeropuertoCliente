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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.una.aeropuertocliente.dtos.UsuariosDTO;
import org.una.aeropuertocliente.entitiesServices.UsuariosService;
import org.una.aeropuertocliente.utils.Mensaje;

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
    UsuariosDTO usuDto = new UsuariosDTO();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblNombre.setText(usuDto.getNombreCompleto());
        lblCedula.setText(usuDto.getCedula());
    }

    @FXML
    private void actionCancelar(ActionEvent event) {
        ((Stage) btnCancelar.getScene().getWindow()).close();
    }

    @FXML
    private void actionCambiar(ActionEvent event) {
        if (txtcontrasena.getText() != null && txtConfirmarcontrasena.getText() != null) {
            if (txtcontrasena.getText().equals(txtConfirmarcontrasena.getText())) {
                usuDto.setContrasenaEncriptada(txtcontrasena.getText());
                if (UsuariosService.updateContrasenaUsuario(usuDto) == 200) {
                    new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Cambiar contraseña", ((Stage) txtcontrasena.getScene().getWindow()), "La contraseña se cambió correctamente");
                    ((Stage) btnCancelar.getScene().getWindow()).close();
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Cambiar contraseña", ((Stage) txtcontrasena.getScene().getWindow()), "La contraseña no se cambió correctamente");
                }

            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Cambiar contraseña", ((Stage) txtcontrasena.getScene().getWindow()), "Las contraseñas no coinciden");
            }
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cambiar contraseña", ((Stage) txtcontrasena.getScene().getWindow()), "Faltan campos por rellenar");
        }
    }

    @Override
    public void initialize() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
