/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.una.aeropuertocliente.dtos.AreasTrabajosDTO;
import org.una.aeropuertocliente.dtos.RolesDTO;
import org.una.aeropuertocliente.dtos.UsuariosDTO;
import org.una.aeropuertocliente.entitiesServices.AreasTrabajosService;
import org.una.aeropuertocliente.entitiesServices.RolesService;
import org.una.aeropuertocliente.entitiesServices.UsuariosService;
import org.una.aeropuertocliente.utils.AppContext;
import org.una.aeropuertocliente.utils.FlowController;
import org.una.aeropuertocliente.utils.Mensaje;

/**
 * FXML Controller class
 *
 * @author cfugu
 */
public class MantenimientoUsuariosController implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private Label titulo;
    @FXML
    private Label lbltxtId;
    @FXML
    private JFXTextField txtId;
    @FXML
    private Label lbltxtNombre;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private Label lbltxtCedula;
    @FXML
    private JFXTextField txtCedula;
    @FXML
    private Label lbltxtCorreo;
    @FXML
    private JFXTextField txtCorreo;
    @FXML
    private Label lblCmbEstado;
    @FXML
    private JFXComboBox<String> cmbEstado;
    @FXML
    private JFXComboBox<String> cmbRoles;
    @FXML
    private Label lbltxtFecha;
    @FXML
    private JFXTextField txtFecha;
    @FXML
    private Label lblGuardar;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private Label lblCancelar;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXComboBox<String> combJefe;
    @FXML
    private JFXTextField txtPassMostrado;
    @FXML
    private Label lblCambi;
    @FXML
    private JFXButton btnCambiarContrasena;
    @FXML
    private Label lblcmbArea;
    @FXML
    private JFXComboBox<String> cmbArea;

    UsuariosDTO usuario = new UsuariosDTO();
    List<RolesDTO> listRoles = new ArrayList();
    List<AreasTrabajosDTO> listAreas = new ArrayList();
    @FXML
    private Label lbltextPass;
    @FXML
    private Label lblcmbRol;
    @FXML
    private Label lblcmbJefe;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        List<String> llenarComboBox = new ArrayList<>();
        usuario = (UsuariosDTO) AppContext.getInstance().get("usu");
        cmbEstado.setItems(FXCollections.observableArrayList("Activo", "Inactivo"));
        combJefe.setItems(FXCollections.observableArrayList("Si", "No"));
        listRoles = RolesService.allRoles();
        for (RolesDTO listRole : listRoles) {
            llenarComboBox.add(listRole.getCodigo());
        }

        cmbRoles.setItems(FXCollections.observableArrayList(llenarComboBox));
        listAreas = AreasTrabajosService.allAreasTrabajos();
        llenarComboBox.clear();
        for (AreasTrabajosDTO lisArea : listAreas) {
            llenarComboBox.add(lisArea.getNombreAreaTrabajo());
        }
        cmbArea.setItems(FXCollections.observableArrayList(llenarComboBox));
        if (usuario != null) {
            txtPassMostrado.setDisable(true);
            txtPassMostrado.setVisible(false);
            txtId.setText(usuario.getId().toString());
            txtId.setDisable(true);
            txtNombre.setText(usuario.getNombreCompleto());
            txtFecha.setDisable(true);
            txtFecha.setText(new SimpleDateFormat("dd-MM-yyyy").format(usuario.getFechaRegistro()));
            txtCorreo.setText(usuario.getCorreo());
            txtCedula.setText(usuario.getCedula());
            if (usuario.isEstado()) {
                cmbEstado.setValue("Activo");
            } else {
                cmbEstado.setValue("Inactivo");
            }
            if (usuario.isJefeId()) {
                combJefe.setValue("Si");
            } else {
                combJefe.setValue("No");
            }
            cmbArea.setValue(usuario.getAreaTrabajoId().getNombreAreaTrabajo());
            cmbRoles.setValue(usuario.getRolId().getCodigo());
            btnCambiarContrasena.setDisable(false);
            btnCambiarContrasena.setVisible(true);

        } else {
            txtId.setText("Nuevo");
            txtId.setDisable(true);
            txtFecha.setDisable(true);
            txtFecha.setText(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
            cmbEstado.setDisable(true);
            cmbEstado.setValue("Activo");
            btnCambiarContrasena.setDisable(true);
            btnCambiarContrasena.setVisible(false);

        }
    }

    @FXML
    private void onActionGuardar(ActionEvent event) {
        if (txtNombre.getText() != null && txtCedula.getText() != null && txtCorreo.getText() != null && txtPassMostrado.getText() != null && cmbArea.getValue() != null && cmbRoles.getValue() != null && combJefe.getValue() != null) {
            if ((UsuariosDTO) AppContext.getInstance().get("usu") == null) {
                usuario = new UsuariosDTO();
                for (RolesDTO listRole : listRoles) {
                    if (listRole.getCodigo().equals(cmbRoles.getValue())) {
                        usuario.setRolId(listRole);
                    }
                }
                for (AreasTrabajosDTO lisArea : listAreas) {
                    if (lisArea.getNombreAreaTrabajo().equals(cmbArea.getValue())) {
                        usuario.setAreaTrabajoId(lisArea);
                    }
                }
                usuario.setCedula(txtCedula.getText());
                usuario.setCorreo(txtCorreo.getText());
                usuario.setEstado(true);
                usuario.setNombreCompleto(txtNombre.getText());
                usuario.setFechaRegistro(new Date());
                if (combJefe.getValue().equals("Si")) {
                    usuario.setJefeId(true);
                } else {
                    usuario.setJefeId(false);
                }
                usuario.setContrasenaEncriptada(txtPassMostrado.getText());
                usuario = UsuariosService.createUsuario(usuario);
                if (usuario != null) {
                    new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Guardar usuario", ((Stage) txtCorreo.getScene().getWindow()), "El usuario se guardo correctamente");
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar usuario", ((Stage) txtCorreo.getScene().getWindow()), "El usuario no se guardo correctamente");
                }
            } else {
                for (RolesDTO listRole : listRoles) {
                    if (listRole.getCodigo().equals(cmbRoles.getValue())) {
                        usuario.setRolId(listRole);
                    }
                }
                for (AreasTrabajosDTO lisArea : listAreas) {
                    if (lisArea.getNombreAreaTrabajo().equals(cmbArea.getValue())) {
                        usuario.setAreaTrabajoId(lisArea);
                    }
                }
                usuario.setCedula(txtCedula.getText());
                usuario.setCorreo(txtCorreo.getText());
                if (cmbEstado.getValue().equals("Activo")) {
                    usuario.setEstado(true);
                } else {
                    usuario.setEstado(false);
                }
                usuario.setNombreCompleto(txtNombre.getText());
                if (combJefe.getValue().equals("Si")) {
                    usuario.setJefeId(true);
                } else {
                    usuario.setJefeId(false);
                }
                if (UsuariosService.updateUsuario(usuario) == 200) {
                    new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Guardar usuario", ((Stage) txtCorreo.getScene().getWindow()), "El usuario se guardo correctamente");
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar usuario", ((Stage) txtCorreo.getScene().getWindow()), "El usuario no se guardo correctamente");
                }
            }
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar usuario", ((Stage) txtCorreo.getScene().getWindow()), "Faltan campos por rellenar");
        }
    }

    private AreasTrabajosDTO valueCbAreaTrabajo(String codigo) {
        return (AreasTrabajosDTO) listAreas.stream().filter(x -> x.getNombreAreaTrabajo().equals(codigo)).collect(Collectors.toList());
    }

    @FXML
    private void onActionCancelar(ActionEvent event) {
    }

    @FXML
    private void onActionCambiarContrasena(ActionEvent event) {
        
        AppContext.getInstance().set("usuarioContrasena", usuario);
        FlowController.getInstance().goViewInWindowModal("cambioContrasena/cambioContrasena", ((Stage) txtCorreo.getScene().getWindow()), Boolean.TRUE);
    }

}
