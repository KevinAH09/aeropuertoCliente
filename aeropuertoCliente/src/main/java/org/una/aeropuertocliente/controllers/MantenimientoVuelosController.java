/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private Label labOrigen;
    @FXML
    private Label labtxtOrigen;
    @FXML
    private JFXTextField txtOrigen;
    @FXML
    private Label labHoraInico;
    @FXML
    private Label labtimePikerInicio;
    @FXML
    private JFXTimePicker pikerInicio;
    @FXML
    private Label labCargaoPasajero;
    @FXML
    private Label labCombCargaPasajero;
    @FXML
    private JFXComboBox<?> combCargaPasajero;
    @FXML
    private Label labCargaCombustible;
    @FXML
    private Label lablabCombCargaCombustible;
    @FXML
    private Label labAvion;
    @FXML
    private Label labtxtAvion;
    @FXML
    private JFXTextField txtAvion;
    @FXML
    private Label labbtnAvion;
    @FXML
    private JFXButton btnAgregar;
    @FXML
    private Label labDestino;
    @FXML
    private Label labtxtDestino;
    @FXML
    private JFXTextField txtDestino;
    @FXML
    private Label labHoraFinal;
    @FXML
    private Label labtimePikerFinal;
    @FXML
    private JFXTimePicker pikerFinal;
    @FXML
    private Label labTorre;
    @FXML
    private Label labCombTorre;
    @FXML
    private JFXComboBox<?> combTorre;
    @FXML
    private Label labEstado;
    @FXML
    private Label labCombEstado;
    @FXML
    private Label labBitacora;
    @FXML
    private Label labTxtBitacora;
    @FXML
    private JFXTextField txtBitacora;
    @FXML
    private Label labBtnAgregarBitacora;
    @FXML
    private JFXButton btnAgregar1;
    @FXML
    private JFXButton btnGuardar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
