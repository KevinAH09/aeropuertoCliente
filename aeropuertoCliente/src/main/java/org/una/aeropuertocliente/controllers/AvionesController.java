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
public class AvionesController implements Initializable {

    @FXML
    private Label labTitulo;
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
    private TableView<?> tableZonas;
   
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


}
