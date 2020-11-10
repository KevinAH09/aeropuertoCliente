/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.una.aeropuertocliente.dtos.RegistrosAccionesDTO;
import org.una.aeropuertocliente.utils.FlowController;

/**
 * FXML Controller class
 *
 * @author colo7
 */
public class RegistroAccionesController implements Initializable {

    @FXML
    private JFXButton btnBuscarUsuario;
    @FXML
    private JFXDatePicker fechaRegistro;
    @FXML
    private JFXComboBox<String> cmbFiltro;
    @FXML
    private Label lblTable;
    @FXML
    private TableView<RegistrosAccionesDTO> tableRegistroAcciones;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnBuscarUsuario.setVisible(false);
        fechaRegistro.setVisible(false);
    }    

    @FXML
    private void actionBuscarUsuario(ActionEvent event) {
        FlowController.getInstance().goViewInWindowModal("usuarios/Usuarios", ((Stage) btnBuscarUsuario.getScene().getWindow()), true);
    }

    @FXML
    private void actionFiltrarFecha(ActionEvent event) {
    }

    @FXML
    private void actionOptionFiltrar(ActionEvent event) {
    }
    
}
