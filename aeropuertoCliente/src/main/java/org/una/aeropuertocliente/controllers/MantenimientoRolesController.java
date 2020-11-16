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
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.una.aeropuertocliente.dtos.AreasTrabajosDTO;
import org.una.aeropuertocliente.dtos.RegistrosAccionesDTO;
import org.una.aeropuertocliente.dtos.RolesDTO;
import org.una.aeropuertocliente.entitiesServices.AreasTrabajosService;
import org.una.aeropuertocliente.entitiesServices.RegistrosAccionesService;
import org.una.aeropuertocliente.entitiesServices.RolesService;
import org.una.aeropuertocliente.sharedService.Token;
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
    private List<Node> requeridos = new ArrayList<>();
    public List<Node> modDesarrollo = new ArrayList<>();
    public List<String> modDesarrolloAxiliar = new ArrayList<>();

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
        indicarRequeridos();
        llenarFormulario();
        llenarListaNodos();
        desarrollo();
        if (Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_ADMIN")) {
            btnRegistrar.setDisable(true);
        }
    }

    private void llenarFormulario() {
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
        String validacion = validarRequeridos();
        if (roles == null) {
            guardarNuevoRol(validacion);

        } else {
            guardarEdicionRol(validacion);
        }
    }

    private void guardarEdicionRol(String validacion) {
        if (validacion == null) {
            if (cmbEstado.getValue().equals("Activo")) {
                roles.setEstado(true);
            } else {
                roles.setEstado(false);
            }
            roles.setDescripcion(txtDescripcion.getText());
            roles.setCodigo(txtCodigo.getText());
            if (RolesService.updateRol(roles) == 200) {
                RegistrosAccionesService.createRegistroAccion(new RegistrosAccionesDTO(Token.getInstance().getUsuario(), "Edito rol " + roles.getId(), new Date()));
                new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Guardar rol", ((Stage) txtCodigo.getScene().getWindow()), "Se guardó correctamente");
                PrincipalController.cambiarVistaPrincipal("roles/Roles");
            } else {
                new Mensaje().showModal(Alert.AlertType.INFORMATION, "Error al guardar rol", ((Stage) txtCodigo.getScene().getWindow()), "No se pudo guardar");
            }
        } else {
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Error al guardar rol", ((Stage) txtCodigo.getScene().getWindow()), validacion);
        }
    }

    private void guardarNuevoRol(String validacion) {
        if (validacion == null) {
            roles = new RolesDTO();
            if (cmbEstado.getValue().equals("Activo")) {
                roles.setEstado(true);
            } else {
                roles.setEstado(false);
            }
            roles.setDescripcion(txtDescripcion.getText());
            roles.setCodigo(txtCodigo.getText());
            if (RolesService.createRol(roles) == 201) {
                RegistrosAccionesService.createRegistroAccion(new RegistrosAccionesDTO(Token.getInstance().getUsuario(), "Creo rol", new Date()));
                new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Guardar rol", ((Stage) txtCodigo.getScene().getWindow()), "Se guardó correctamente");
                PrincipalController.cambiarVistaPrincipal("roles/Roles");
            } else {
                new Mensaje().showModal(Alert.AlertType.INFORMATION, "Error al guardar rol", ((Stage) txtCodigo.getScene().getWindow()), "No se pudo guardar");
            }
        } else {
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Error al crear rol", ((Stage) txtCodigo.getScene().getWindow()), validacion);
        }
    }

    public void indicarRequeridos() {
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txtDescripcion, txtCodigo, cmbEstado));
    }

    public String validarRequeridos() {
        Boolean validos = true;
        String invalidos = "";
        for (Node node : requeridos) {
            if (node instanceof JFXTextField && (((JFXTextField) node).getText() == null || ((JFXTextField) node).getText().isEmpty())) {
                if (validos) {
                    invalidos += ((JFXTextField) node).getPromptText();
                } else {
                    invalidos += "," + ((JFXTextField) node).getPromptText();
                }
                validos = false;

            } else if (node instanceof JFXComboBox && (((JFXComboBox) node).getValue() == null)) {
                if (validos) {
                    invalidos += ((JFXComboBox) node).getPromptText();
                } else {
                    invalidos += "," + ((JFXComboBox) node).getPromptText();
                }
                validos = false;
            }
        }
        if (validos) {
            return null;
        } else {
            return "Los siguientes campos son requeridos " + "[" + invalidos + "].";
        }
    }

    public void llenarListaNodos() {
        modDesarrollo.clear();
        modDesarrolloAxiliar.clear();
        modDesarrolloAxiliar.add(titulo.getText());
        modDesarrolloAxiliar.add(txtCodigo.getPromptText());
        modDesarrolloAxiliar.add(txtId.getPromptText());
        modDesarrolloAxiliar.add(txtDescripcion.getPromptText());
        modDesarrolloAxiliar.add(cmbEstado.getPromptText());
        modDesarrolloAxiliar.add(btnCancelar.getText());
        modDesarrolloAxiliar.add(btnRegistrar.getText());
        modDesarrollo.addAll(Arrays.asList(titulo,txtCodigo, txtId, txtDescripcion, cmbEstado, btnCancelar, btnRegistrar));
    }

    public void desarrollo() {
        String dato = "";
        boolean validos1 = (Boolean) AppContext.getInstance().get("mod");
        if (validos1) {
            validarBooleanoTrue();
        } else {
            validarBooleanoFalse();
        }
    }

    private void validarBooleanoFalse() {
        String dato;
        for (int i = 0; i < modDesarrollo.size(); i++) {
            if (modDesarrollo.get(i) instanceof JFXButton) {
                dato = modDesarrolloAxiliar.get(i);
                ((JFXButton) modDesarrollo.get(i)).setText(dato);
            }
            if (modDesarrollo.get(i) instanceof JFXTextField) {
                dato = modDesarrolloAxiliar.get(i);
                ((JFXTextField) modDesarrollo.get(i)).setPromptText(dato);
            }
            if (modDesarrollo.get(i) instanceof JFXComboBox) {
                dato = modDesarrolloAxiliar.get(i);
                ((JFXComboBox) modDesarrollo.get(i)).setPromptText(dato);
            }
            if (modDesarrollo.get(i) instanceof Label) {
                dato = modDesarrolloAxiliar.get(i);
                ((Label) modDesarrollo.get(i)).setText(dato);
            }
        }
    }

    private void validarBooleanoTrue() {
        String dato;
        for (Node node : modDesarrollo) {
            if (node instanceof JFXTextField) {
                dato = ((JFXTextField) node).getId();
                ((JFXTextField) node).setPromptText(dato);
            }
            if (node instanceof JFXButton) {
                dato = ((JFXButton) node).getId();
                ((JFXButton) node).setText(dato);
            }
            if (node instanceof JFXComboBox) {
                dato = ((JFXComboBox) node).getId();
                ((JFXComboBox) node).setPromptText(dato);
            }
            if (node instanceof Label) {
                dato = ((Label) node).getId();
                ((Label) node).setText(dato);
            }
        }
    }

    @Override
    public void initialize() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void modoDesarrollo(KeyEvent event) {
        KeyCombination cntrlD = new KeyCodeCombination(KeyCode.D, KeyCodeCombination.CONTROL_DOWN);
        if (Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_ADMIN")) {
            if (cntrlD.match(event)) {
                boolean validos1 = (Boolean) AppContext.getInstance().get("mod");
                if (validos1) {
                    AppContext.getInstance().set("mod", false);
                    desarrollo();
                } else {
                    AppContext.getInstance().set("mod", true);
                    desarrollo();
                }
            }
        }
    }

}
