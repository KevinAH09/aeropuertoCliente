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
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import org.una.aeropuertocliente.dtos.AerolineasDTO;
import org.una.aeropuertocliente.utils.AppContext;
import org.una.aeropuertocliente.utils.FlowController;

/**
 * FXML Controller class
 *
 * @author cfugu
 */
public class MantenimientoAerolineasController extends Controller implements Initializable {

    @FXML
    private JFXTextField txtId;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextField txtResponsable;
    @FXML
    private JFXComboBox<String> cmbEstado;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private TableView<?> tableAviones;
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lbltxtId;
    @FXML
    private Label lbltxtNombre;
    @FXML
    private Label titulo;
    @FXML
    private Label lblTxtResponsable;
    @FXML
    private Label lblCmbEstado;
    @FXML
    private Label lblGuardar;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private Label lblCancelar;
    @FXML
    private Label lblEditar;
    @FXML
    private Label lblTable;
    @FXML
    private Label lblRegistrar;
    @FXML
    private JFXButton btnRegistrar;
    AerolineasDTO aerolinea;

  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
        cmbEstado.setItems(FXCollections.observableArrayList("Activo", "Inactivo"));
            
        if(AppContext.getInstance().get("aerolinea")!=null)
        {
            
            aerolinea=(AerolineasDTO) AppContext.getInstance().get("aerolinea");
            if (aerolinea.isEstado()) {
                cmbEstado.setValue("Activo");
            } else {
                cmbEstado.setValue("Inactivo");
            }
            txtNombre.setDisable(true);
            txtId.setDisable(true);
            txtResponsable.setDisable(true);
            cmbEstado.setDisable(true);
            txtNombre.setText(aerolinea.getNombreAerolinea());
            txtId.setText(aerolinea.getId().toString());
            txtResponsable.setText(aerolinea.getNombreResponsable());
            
            btnCancelar.setVisible(false);
            btnEditar.setVisible(false);
            btnGuardar.setVisible(false);
            
        }
        
    }    

    @FXML
    private void onActionGuardar(ActionEvent event) {
    }

    @FXML
    private void onActionCancelar(ActionEvent event) {
    }

    @FXML
    private void onActionEditar(ActionEvent event) {
    }

    @FXML
    private void onActionRegistrar(ActionEvent event) {
        FlowController.getInstance().goView("mantenimientoAviones/MantenimientoAvion");
    }

    @Override
    public void initialize() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
