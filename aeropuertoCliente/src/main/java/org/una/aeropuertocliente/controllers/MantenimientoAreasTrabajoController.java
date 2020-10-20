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
import javafx.stage.Stage;
import org.una.aeropuertocliente.dtos.AreasTrabajosDTO;
import org.una.aeropuertocliente.utils.AppContext;

/**
 * FXML Controller class
 *
 * @author cfugu
 */
public class MantenimientoAreasTrabajoController extends Controller implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private Label titulo;
    @FXML
    private Label lbltxtNombre;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private Label lbltxtId;
    @FXML
    private JFXTextField txtId;
    @FXML
    private Label lbltxtDescripcion;
    @FXML
    private JFXTextField txtDescripcion;
    @FXML
    private Label lblcmbEstado;
    @FXML
    private JFXComboBox<String> cmbEstado;
    @FXML
    private Label lblCancelar;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private Label lblGuardar;
    @FXML
    private JFXButton btnRegistrar;
    AreasTrabajosDTO areaTrabajoDTO;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbEstado.setItems(FXCollections.observableArrayList("Activo", "Inactivo"));
        areaTrabajoDTO = (AreasTrabajosDTO) AppContext.getInstance().get("area");
        if (areaTrabajoDTO != null) {
            txtDescripcion.setText(areaTrabajoDTO.getDescripcion());
            txtId.setText(areaTrabajoDTO.getId().toString());
            txtNombre.setText(areaTrabajoDTO.getNombreAreaTrabajo());
            if (areaTrabajoDTO.isEstado()) {
                cmbEstado.setValue("Activo");
            } else {
                cmbEstado.setValue("Inactivo");
            }
        }else{
            txtId.setDisable(true);
        }

    }

    @FXML
    private void onActionCancelar(ActionEvent event) {
        areaTrabajoDTO = null;
        txtDescripcion.clear();
        txtId.clear();
        txtNombre.clear();
        ((Stage) txtId.getScene().getWindow()).close();
    }

    @FXML
    private void onActionGuardar(ActionEvent event) {
    }

    @Override
    public void initialize() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
