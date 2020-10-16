/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Bosco
 */
public class MantenimientoVuelosController implements Initializable {

    @FXML
    private Label labTitulo;
    @FXML
    private Label labtxtOrigen;
    @FXML
    private JFXTextField txtOrigen;
    @FXML
    private Label labtimePikerInicio;
    @FXML
    private Label labtxtAvion;
    @FXML
    private JFXTextField txtAvion;
    @FXML
    private Label labbtnAvion;
    @FXML
    private JFXButton btnAgregar;
    @FXML
    private Label labtxtDestino;
    @FXML
    private JFXTextField txtDestino;
    @FXML
    private Label labtimePikerFinal;
    @FXML
    private Label labTorre;
    @FXML
    private Label labCombEstado;
    @FXML
    private Label labBitacora;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private DatePicker datePikerInicio;
    @FXML
    private DatePicker datePikerFinal;
    @FXML
    private Label labTxtTipoBitacora;
    @FXML
    private JFXTextField txtTipoBita;
    @FXML
    private Label labZona;
    @FXML
    private JFXRadioButton radioZona;
    @FXML
    private Label labradioCargaPasajero;
    @FXML
    private JFXRadioButton radioSubioCargaPasajero;
    @FXML
    private Label labRadioCargoCombustible;
    @FXML
    private JFXRadioButton radioCargoCombustible;
    @FXML
    private Label labHoras;
    @FXML
    private JFXRadioButton radioHoras;
    @FXML
    private Label labBtnGuardar;
    @FXML
    private JFXComboBox<?> combEstado;
    @FXML
    private JFXRadioButton radioTorre;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void agregarAvion(ActionEvent event) {
    }

    @FXML
    private void guardar(ActionEvent event) {
    }
    
}
