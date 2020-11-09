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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.una.aeropuertocliente.dtos.AreasTrabajosDTO;
import org.una.aeropuertocliente.dtos.RegistrosAccionesDTO;
import org.una.aeropuertocliente.dtos.RolesDTO;
import org.una.aeropuertocliente.dtos.UsuariosDTO;
import org.una.aeropuertocliente.entitiesServices.AreasTrabajosService;
import org.una.aeropuertocliente.entitiesServices.RegistrosAccionesService;
import org.una.aeropuertocliente.entitiesServices.RolesService;
import org.una.aeropuertocliente.entitiesServices.UsuariosService;
import org.una.aeropuertocliente.sharedService.Token;
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
    private List<Node> requeridos = new ArrayList<>();
    @FXML
    private Label lbltextPass;
    @FXML
    private Label lblcmbRol;
    @FXML
    private Label lblcmbJefe;
    public List<Node> modDesarrollo = new ArrayList<>();
    public List<String> modDesarrolloAxiliar = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        List<String> llenarComboBox = new ArrayList<>();
        cmbEstado.setItems(FXCollections.observableArrayList("Activo", "Inactivo"));
        combJefe.setItems(FXCollections.observableArrayList("Si", "No"));
        cargarComboboxRoles(llenarComboBox);
        cargarComboboxAreas(llenarComboBox);

        llenarFormulario();
        indicarRequeridos();
        llenarListaNodos();
        desarrollo();
    }

    private void llenarFormulario() {
        usuario = (UsuariosDTO) AppContext.getInstance().get("usu");
        if (usuario != null) {
            txtPassMostrado.setText(usuario.getContrasenaEncriptada());
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
            if (usuario.getAreaTrabajoId() != null) {
                cmbArea.setValue(usuario.getAreaTrabajoId().getNombreAreaTrabajo());
            } else {
                cmbArea.setDisable(true);
            }
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

    private void cargarComboboxAreas(List<String> llenarComboBox) {
        listAreas = AreasTrabajosService.allAreasTrabajos();
        llenarComboBox.clear();
        for (AreasTrabajosDTO lisArea : listAreas) {
            llenarComboBox.add(lisArea.getNombreAreaTrabajo());
        }
        cmbArea.setItems(FXCollections.observableArrayList(llenarComboBox));
    }

    private void cargarComboboxRoles(List<String> llenarComboBox) {
        listRoles = RolesService.allRoles();
        for (RolesDTO listRole : listRoles) {
            llenarComboBox.add(listRole.getCodigo());
        }
        cmbRoles.setItems(FXCollections.observableArrayList(llenarComboBox));
    }

    @FXML
    private void onActionGuardar(ActionEvent event) {
        String validacion = validarRequeridos();
        if (validacion == null && validarEmail()) {
            if ((UsuariosDTO) AppContext.getInstance().get("usu") == null) {
                guardarNuevoUsuario();
            } else {
                guardarEdicionUsuario();
            }
        } else {
            if (validacion != null) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar usuario", ((Stage) txtCorreo.getScene().getWindow()), validacion);
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar usuario", ((Stage) txtCorreo.getScene().getWindow()), "El correo no tiene el formato example@dominio.cr");
            }
        }
    }

    private void guardarEdicionUsuario() {
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
            RegistrosAccionesService.createRegistroAccion(new RegistrosAccionesDTO(Token.getInstance().getUsuario(), "Edito usuario " + usuario.getId(), new Date()));
            new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Guardar usuario", ((Stage) txtCorreo.getScene().getWindow()), "El usuario se guardo correctamente");
            PrincipalController.cambiarVistaPrincipal("usuarios/Usuarios");
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar usuario", ((Stage) txtCorreo.getScene().getWindow()), "El usuario no se guardo correctamente");
        }
    }

    private void guardarNuevoUsuario() {
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
            RegistrosAccionesService.createRegistroAccion(new RegistrosAccionesDTO(Token.getInstance().getUsuario(), "Creo nuevo usuario", new Date()));
            new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Guardar usuario", ((Stage) txtCorreo.getScene().getWindow()), "El usuario se guardo correctamente");
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar usuario", ((Stage) txtCorreo.getScene().getWindow()), "El usuario no se guardo correctamente");
        }
    }

    private AreasTrabajosDTO valueCbAreaTrabajo(String codigo) {
        return (AreasTrabajosDTO) listAreas.stream().filter(x -> x.getNombreAreaTrabajo().equals(codigo)).collect(Collectors.toList());
    }

    @FXML
    private void onActionCancelar(ActionEvent event) {
        PrincipalController.cambiarVistaPrincipal("usuarios/Usuarios");
    }

    @FXML
    private void onActionCambiarContrasena(ActionEvent event) {

        AppContext.getInstance().set("usuarioContrasena", usuario);
        FlowController.getInstance().goViewInWindowModal("cambioContrasena/cambioContrasena", ((Stage) txtCorreo.getScene().getWindow()), Boolean.TRUE);
    }

    public void indicarRequeridos() {
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txtCedula, txtCorreo, txtPassMostrado, txtNombre, cmbArea, cmbRoles, combJefe, cmbEstado));
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

    private boolean validarEmail() {
        boolean ban;
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        String email = txtCorreo.getText();
        Matcher mather = pattern.matcher(email);
        if (mather.find() == true) {
            ban = true;
        } else {
            ban = false;
        }
        return ban;
    }

    public void llenarListaNodos() {
        modDesarrollo.clear();
        modDesarrolloAxiliar.clear();
        modDesarrolloAxiliar.add(titulo.getText());
        modDesarrolloAxiliar.add(txtId.getPromptText());
        modDesarrolloAxiliar.add(txtNombre.getPromptText());
        modDesarrolloAxiliar.add(txtCedula.getPromptText());
        modDesarrolloAxiliar.add(txtCorreo.getPromptText());
        modDesarrolloAxiliar.add(cmbRoles.getPromptText());
        modDesarrolloAxiliar.add(cmbEstado.getPromptText());
        modDesarrolloAxiliar.add(txtFecha.getPromptText());
        modDesarrolloAxiliar.add(combJefe.getPromptText());
        modDesarrolloAxiliar.add(cmbArea.getPromptText());
        modDesarrolloAxiliar.add(txtPassMostrado.getPromptText());
        modDesarrolloAxiliar.add(btnGuardar.getText());
        modDesarrolloAxiliar.add(btnCancelar.getText());
        modDesarrolloAxiliar.add(btnCambiarContrasena.getText());
        modDesarrollo.addAll(Arrays.asList(titulo, txtId, txtNombre, txtCedula, txtCorreo, cmbRoles,
                cmbEstado, txtFecha, combJefe, cmbArea, txtPassMostrado, btnGuardar, btnCancelar, btnCambiarContrasena));
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

    @FXML
    private void modoDesarrollo(KeyEvent event) {
        KeyCombination cntrlD = new KeyCodeCombination(KeyCode.D, KeyCodeCombination.CONTROL_DOWN);
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
