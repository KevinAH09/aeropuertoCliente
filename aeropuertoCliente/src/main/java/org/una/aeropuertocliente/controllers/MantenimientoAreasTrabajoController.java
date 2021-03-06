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
import java.util.ArrayList;
import java.util.Arrays;
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
import org.una.aeropuertocliente.dtos.ZonasDTO;
import org.una.aeropuertocliente.entitiesServices.AreasTrabajosService;
import org.una.aeropuertocliente.entitiesServices.RegistrosAccionesService;
import org.una.aeropuertocliente.entitiesServices.ZonasService;
import org.una.aeropuertocliente.sharedService.Token;
import org.una.aeropuertocliente.utils.AppContext;
import org.una.aeropuertocliente.utils.FlowController;
import org.una.aeropuertocliente.utils.Mensaje;

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
    private List<Node> requeridos = new ArrayList<>();
    public List<Node> modDesarrollo = new ArrayList<>();
    public List<String> modDesarrolloAxiliar = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbEstado.setItems(FXCollections.observableArrayList("Activo", "Inactivo"));
        areaTrabajoDTO = null;
        txtDescripcion.clear();
        txtId.clear();
        txtNombre.clear();
        txtId.setDisable(true);
        llenarFormulario();
        llenarListaNodos();
        desarrollo();
        if (Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_ADMIN")) {
            btnRegistrar.setDisable(true);
        }
    }

    private void llenarFormulario() {
        areaTrabajoDTO = (AreasTrabajosDTO) AppContext.getInstance().get("area");
        indicarRequeridos();
        if (areaTrabajoDTO != null) {
            txtDescripcion.setText(areaTrabajoDTO.getDescripcion());
            txtId.setText(areaTrabajoDTO.getId().toString());
            txtNombre.setText(areaTrabajoDTO.getNombreAreaTrabajo());
            if (areaTrabajoDTO.isEstado()) {
                cmbEstado.setValue("Activo");
            } else {
                cmbEstado.setValue("Inactivo");
            }
        }
    }

    @FXML
    private void onActionCancelar(ActionEvent event) {
        areaTrabajoDTO = null;
        txtDescripcion.clear();
        txtId.clear();
        txtNombre.clear();
        PrincipalController.cambiarVistaPrincipal("areasTrabajo/AreasTrabajo");
    }

    @FXML
    private void onActionGuardar(ActionEvent event) {
        String validacion = validarRequeridos();
        if (areaTrabajoDTO == null) {
            guardarNuevaArea(validacion);

        } else {
            guardarEdicionArea(validacion);
        }
    }

    private void guardarEdicionArea(String validacion) {
        if (validacion == null) {
            if (cmbEstado.getValue().equals("Activo")) {
                areaTrabajoDTO.setEstado(true);
            } else {
                areaTrabajoDTO.setEstado(false);
            }
            areaTrabajoDTO.setDescripcion(txtDescripcion.getText());
            areaTrabajoDTO.setNombreAreaTrabajo(txtNombre.getText());
            if (AreasTrabajosService.updateAreaTrabajo(areaTrabajoDTO) == 200) {
                RegistrosAccionesService.createRegistroAccion(new RegistrosAccionesDTO(Token.getInstance().getUsuario(), "Edito area de trabajo " + areaTrabajoDTO.getId(), new Date()));
                new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Guardar Area de trabajo", ((Stage) txtNombre.getScene().getWindow()), "Se guardó correctamente");
                PrincipalController.cambiarVistaPrincipal("areasTrabajo/AreasTrabajo");
            } else {
                new Mensaje().showModal(Alert.AlertType.INFORMATION, "Error al guardar Area de trabajo", ((Stage) txtNombre.getScene().getWindow()), "No se pudo  guardar");
            }
        } else {
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Error al guardar Area de trabajo", ((Stage) txtNombre.getScene().getWindow()), validacion);
        }
    }

    private void guardarNuevaArea(String validacion) {
        if (validacion == null) {
            areaTrabajoDTO = new AreasTrabajosDTO();
            if (cmbEstado.getValue().equals("Activo")) {
                areaTrabajoDTO.setEstado(true);
            } else {
                areaTrabajoDTO.setEstado(false);
            }
            areaTrabajoDTO.setDescripcion(txtDescripcion.getText());
            areaTrabajoDTO.setNombreAreaTrabajo(txtNombre.getText());
            if (AreasTrabajosService.createAreaTrabajo(areaTrabajoDTO) == 201) {
                RegistrosAccionesService.createRegistroAccion(new RegistrosAccionesDTO(Token.getInstance().getUsuario(), "Creó una area de trabajo", new Date()));
                new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Guardar Area de trabajo", ((Stage) txtNombre.getScene().getWindow()), "Se guardó correctamente");
                PrincipalController.cambiarVistaPrincipal("areasTrabajo/AreasTrabajo");
            } else {
                new Mensaje().showModal(Alert.AlertType.INFORMATION, "Error al guardar Area de trabajo", ((Stage) txtNombre.getScene().getWindow()), "No se pudo guardar");
            }
        } else {
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Error al crear Area de trabajo", ((Stage) txtNombre.getScene().getWindow()), validacion);
        }
    }

    public void indicarRequeridos() {
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txtDescripcion, txtNombre, cmbEstado));
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
        modDesarrolloAxiliar.add(txtNombre.getPromptText());
        modDesarrolloAxiliar.add(txtId.getPromptText());
        modDesarrolloAxiliar.add(txtDescripcion.getPromptText());
        modDesarrolloAxiliar.add(cmbEstado.getPromptText());
        modDesarrolloAxiliar.add(btnCancelar.getText());
        modDesarrolloAxiliar.add(btnRegistrar.getText());
        modDesarrollo.addAll(Arrays.asList(titulo, txtNombre, txtId, txtDescripcion, cmbEstado, btnCancelar, btnRegistrar));
    }

    public void desarrollo() {
        boolean validos1 = (Boolean) AppContext.getInstance().get("mod");
        if (validos1) {
            validarBooleanoTrue();
        } else {
            validarBooleanoFalse();
        }
    }

    private void validarBooleanoFalse() {
        String dato = "";
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
        String dato = "";
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
