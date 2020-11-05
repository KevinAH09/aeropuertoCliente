/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author colo7
 */
public class ReporteGastoMantController implements Initializable {

    @FXML
    private JFXComboBox<String> cbFiltro;
    @FXML
    private JFXDatePicker fechaIni;
    @FXML
    private JFXDatePicker fechaFin;
    @FXML
    private JFXComboBox<String> cbEstadoPago;
    @FXML
    private JFXComboBox<String> cbAreaTrabajo;
    @FXML
    private JFXTextField txtEmpresa;
    @FXML
    private JFXButton btnLimpiar;
    @FXML
    private JFXButton btnGenerar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void actionFiltroSelect(ActionEvent event) {
    }

    @FXML
    private void actionLimpiar(ActionEvent event) {
    }

    @FXML
    private void actionGenerar(ActionEvent event) {
    }
    
}
