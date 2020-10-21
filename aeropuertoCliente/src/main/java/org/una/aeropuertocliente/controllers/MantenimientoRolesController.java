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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.una.aeropuertocliente.dtos.AreasTrabajosDTO;
import org.una.aeropuertocliente.dtos.RolesDTO;
import org.una.aeropuertocliente.entitiesServices.AreasTrabajosService;
import org.una.aeropuertocliente.entitiesServices.RolesService;
import org.una.aeropuertocliente.utils.AppContext;
import org.una.aeropuertocliente.utils.FlowController;
import org.una.aeropuertocliente.utils.Mensaje;

/**
 * FXML Controller class
 *
 * @author cfugu
 */
public class MantenimientoRolesController extends Controller implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private Label titulo;
    @FXML
    private Label lbltxtNombre;
    @FXML
    private JFXTextField txtCodigo;
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
    RolesDTO rolesFilt;
    RolesDTO roles;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbEstado.setItems(FXCollections.observableArrayList("Activo", "Inactivo"));
        roles = null;
        txtDescripcion.clear();
        txtId.clear();
        txtCodigo.clear();
        txtId.setDisable(true);
        roles = (RolesDTO) AppContext.getInstance().get("rol");
        if (roles != null) {
            txtDescripcion.setText(roles.getDescripcion());
            txtId.setText(roles.getId().toString());
            txtCodigo.setText(roles.getCodigo());
            if (roles.isEstado()) {
                cmbEstado.setValue("Activo");
            } else {
                cmbEstado.setValue("Inactivo");
            }
        }
    }

    @FXML
    private void onActionCancelar(ActionEvent event) {
        txtId.clear();
        txtCodigo.clear();
        txtDescripcion.clear();
        roles = null;
        PrincipalController.cambiarVistaPrincipal("roles/Roles");
    }

    @FXML
    private void onActionGuardar(ActionEvent event) {
        if (roles == null) {
            if (!txtCodigo.getText().isEmpty() && !cmbEstado.getValue().isEmpty() && !txtDescripcion.getText().isEmpty()) {
                roles = new RolesDTO();
                if (cmbEstado.getValue().equals("Activo")) {
                    roles.setEstado(true);
                } else {
                    roles.setEstado(false);
                }
                roles.setDescripcion(txtDescripcion.getText());
                roles.setCodigo(txtCodigo.getText());
                if (RolesService.createRol(roles) == 201) {
                    new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Guardar rol", ((Stage) txtCodigo.getScene().getWindow()), "Se guardó correctamente");
                    PrincipalController.cambiarVistaPrincipal("roles/Roles");
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error al guardar rol", ((Stage) txtCodigo.getScene().getWindow()), "No se guardó correctamente");
                }
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error al crear rol", ((Stage) txtCodigo.getScene().getWindow()), "Rellene los campos necesarios");
            }

        } else {
            if (!txtCodigo.getText().isEmpty() && !cmbEstado.getValue().isEmpty() && !txtId.getText().isEmpty() && !txtDescripcion.getText().isEmpty()) {
                if (cmbEstado.getValue().equals("Activo")) {
                    roles.setEstado(true);
                } else {
                    roles.setEstado(false);
                }
                roles.setDescripcion(txtDescripcion.getText());
                roles.setCodigo(txtCodigo.getText());
                if (RolesService.updateRol(roles) == 200) {
                    new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Guardar rol", ((Stage) txtCodigo.getScene().getWindow()), "Se guardó correctamente");
                    PrincipalController.cambiarVistaPrincipal("roles/Roles");
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error al guardar rol", ((Stage) txtCodigo.getScene().getWindow()), "No se guardó correctamente");
                }
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error al guardar rol", ((Stage) txtCodigo.getScene().getWindow()), "Rellene los campos necesarios");
            }
        }
    }

    @Override
    public void initialize() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
