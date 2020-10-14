/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Bosco
 */
public class MantenimientoAerolineaController implements Initializable {

    @FXML
    private TextField txtFiltrar;
    @FXML
    private ComboBox<?> combFiltrar;
    @FXML
    private Button btnFiltrar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TableView<?> tableview;
    @FXML
    private Button btnRegitrarAerolinea;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
