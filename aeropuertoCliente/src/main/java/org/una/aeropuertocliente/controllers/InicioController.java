/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.controllers;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import org.una.aeropuertocliente.utils.AppContext;
import org.una.aeropuertocliente.utils.FlowController;
import org.una.aeropuertocliente.utils.Mensaje;

/**
 * FXML Controller class
 *
 * @author colo7
 */
public class InicioController extends Controller implements Initializable {

    @FXML
    private JFXButton btnSalir;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void actionLogin(ActionEvent event) {
        FlowController.getInstance().goView("login/Login");
    }

    @FXML
    private void actionCambioDivisas(ActionEvent event) {
        AppContext.getInstance().set("cambiodivisas", true);
        FlowController.getInstance().goViewInWindowModalFullScrean("cambioDivisas/CambioDivisas", ((Stage) btnSalir.getScene().getWindow()), true);
    }

    @FXML
    private void actionSalir(ActionEvent event) {
        if (new Mensaje().showConfirmation("Cerrar aplicacion", ((Stage) btnSalir.getScene().getWindow()), "Desea salir de la aplicacion")) {
            ((Stage) btnSalir.getScene().getWindow()).close();
        }
    }

    @Override
    public void initialize() {
       
    }
}
