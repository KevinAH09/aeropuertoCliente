/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Bosco
 */
public class VuelosController implements Initializable {

    @FXML
    private Label lablTitulo;
    @FXML
    private Label titulo;
    @FXML
    private JFXTextField txtId;
    @FXML
    private Label lbltxtNombre;
    @FXML
    private Label labTxtFild;
    @FXML
    private JFXTextField txtFilter;
    @FXML
    private Label labComb;
    @FXML
    private JFXComboBox<?> combFilter;
    @FXML
    private Label labBtnFiltrar;
    @FXML
    private JFXButton btnFiltrar;
    @FXML
    private Label labBtnCancelar;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private Label lblTable;
    @FXML
    private TableView<?> tableAviones;
    @FXML
    private Label labTxtFild1;
    @FXML
    private JFXTextField txtFilter1;
    @FXML
    private Label labComb1;
    @FXML
    private JFXComboBox<?> combFilter1;
    @FXML
    private Label labBtnFiltrar1;
    @FXML
    private JFXButton btnFiltrar1;
    @FXML
    private Label labBtnCancelar1;
    @FXML
    private JFXButton btnCancelar1;
    @FXML
    private Label lblTable1;
    @FXML
    private TableView<?> tableAviones1;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private Label lbltxtTipo;
    @FXML
    private JFXTextField txtmatricula;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void filtrar(ActionEvent event) {
    }

    @FXML
    private void cancelar(ActionEvent event) {
    }

    @FXML
    private void filtrar1(ActionEvent event) {
    }

    @FXML
    private void cancelar1(ActionEvent event) {
    }

    @FXML
    private void registrarVuelos(ActionEvent event) {
    }
    
}
