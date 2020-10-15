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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Bosco
 */
public class AerolineasController implements Initializable {

    @FXML
    private Button btnFiltrar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TableView<?> tableview;
    @FXML
    private JFXTextField txtFilter;
    @FXML
    private JFXComboBox<?> combFilter;
    @FXML
    private Label labTitulo;
    @FXML
    private Label labTxtFiltro;
    @FXML
    private Label labTxtFild;
    @FXML
    private Label labComb;
    @FXML
    private Label labBtnFiltrar;
    @FXML
    private Label labBtnCancelar;
    @FXML
    private Label labbtnRegistrarAvion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
