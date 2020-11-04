/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.controllers;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
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
    @FXML
    private JFXButton bntLogin;
    @FXML
    private JFXButton btnDivisas;

    private List<String> modDesarrolloAxiliar = new ArrayList<>();
    private List<Node> modDesarrollo = new ArrayList<>();
    Boolean mod = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AppContext.getInstance().set("mod", mod);
        llenarListaNodos();
        desarrollo();
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

    public void llenarListaNodos() {
        modDesarrollo.clear();
        modDesarrolloAxiliar.clear();
        modDesarrolloAxiliar.add(bntLogin.getText());
        modDesarrolloAxiliar.add(btnSalir.getText());
        modDesarrolloAxiliar.add(btnDivisas.getText());

        modDesarrollo.addAll(Arrays.asList(bntLogin, btnSalir, btnDivisas));
    }

    public void desarrollo() {
        String dato = "";
        boolean validos1 = (Boolean) AppContext.getInstance().get("mod");
        if (validos1) {
            for (Node node : modDesarrollo) {
                if (node instanceof JFXButton) {
                    dato = ((JFXButton) node).getId();
                    ((JFXButton) node).setText(dato);
                }
            }
        } else {
            for (int i = 0; i < modDesarrollo.size(); i++) {
                if (modDesarrollo.get(i) instanceof JFXButton) {
                    dato = modDesarrolloAxiliar.get(i);
                    ((JFXButton) modDesarrollo.get(i)).setText(dato);
                }
            }
        }
    }

    @Override
    public void initialize() {

    }

    @FXML
    private void modoDesarrollo(KeyEvent event) {
        KeyCombination cntrlD = new KeyCodeCombination(KeyCode.D, KeyCodeCombination.CONTROL_DOWN);
        System.out.println("Entró");
        if (cntrlD.match(event)) {
            boolean validos1 = (Boolean) AppContext.getInstance().get("mod");
            if (validos1) {
                AppContext.getInstance().set("mod", false);
                desarrollo();
            }else
            {
                AppContext.getInstance().set("mod", true);
                desarrollo();
            }
        }
    }
}
