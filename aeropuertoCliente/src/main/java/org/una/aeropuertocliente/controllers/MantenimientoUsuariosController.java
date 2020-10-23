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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.una.aeropuertocliente.dtos.AreasTrabajosDTO;
import org.una.aeropuertocliente.dtos.RolesDTO;
import org.una.aeropuertocliente.dtos.UsuariosDTO;
import org.una.aeropuertocliente.entitiesServices.AreasTrabajosService;
import org.una.aeropuertocliente.entitiesServices.RolesService;
import org.una.aeropuertocliente.entitiesServices.UsuariosService;
import org.una.aeropuertocliente.utils.AppContext;

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
    private Label lblcmbRol;
    @FXML
    private JFXComboBox<String> cmbRoles;
    @FXML
    private Label lblcmbJefe;
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
    private Label lbltxtContrasena;
    @FXML
    private PasswordField txtPassOculto;
    @FXML
    private TextField txtPassMostrado;
    @FXML
    private Label lblMostrar;
    @FXML
    private ImageView imgViewPassword;
    @FXML
    private ImageView imgNotPassword;
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
            txtId.setText(usuario.getId().toString());
            txtNombre.setText(usuario.getNombreCompleto());
            txtFecha.setText(new SimpleDateFormat("dd-MM-yyyy").format(usuario.getFechaRegistro()));
            txtCorreo.setText(usuario.getCorreo());
            txtCorreo.setText(usuario.getCedula());
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
                System.out.println(usuario);
                System.out.println(UsuariosService.createUsuario(usuario));
            } else {
                UsuariosService.updateUsuario(usuario);
            }
        }
    }

    private AreasTrabajosDTO valueCbAreaTrabajo(String codigo) {
        return (AreasTrabajosDTO) listAreas.stream().filter(x -> x.getNombreAreaTrabajo().equals(codigo)).collect(Collectors.toList());
    }

    @FXML
    private void onActionCancelar(ActionEvent event) {
    }

    @FXML
    private void actionViewPass(MouseEvent event) {
    }

    @FXML
    private void onActionCambiarContrasena(ActionEvent event) {
    }

}
