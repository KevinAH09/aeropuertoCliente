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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.una.aeropuertocliente.dtos.ParametrosDTO;
import org.una.aeropuertocliente.dtos.RegistrosAccionesDTO;
import org.una.aeropuertocliente.dtos.UsuariosDTO;
import org.una.aeropuertocliente.entitiesServices.ParametrosService;
import org.una.aeropuertocliente.entitiesServices.RegistrosAccionesService;
import org.una.aeropuertocliente.entitiesServices.UsuariosService;
import org.una.aeropuertocliente.sharedService.Token;
import org.una.aeropuertocliente.utils.AppContext;
import org.una.aeropuertocliente.utils.Mensaje;

/**
 * FXML Controller class
 *
 * @author colo7
 */
public class CambioContrasenaController extends Controller implements Initializable {

    @FXML
    private Label lblNombre;
    @FXML
    private Label lblCedula;
    @FXML
    private JFXTextField txtcontrasena;
    @FXML
    private JFXTextField txtConfirmarcontrasena;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXButton btnCambiar;
    UsuariosDTO usuDto = new UsuariosDTO();
    private List<Node> requeridos = new ArrayList<>();
    public List<Node> modDesarrollo = new ArrayList<>();
    public List<String> modDesarrolloAxiliar = new ArrayList<>();
    private ParametrosDTO minimiCaracteres = new ParametrosDTO();
    private ParametrosDTO caracteresEspeciales = new ParametrosDTO();
    @FXML
    private Text txtRegla1;
    @FXML
    private Text txtRegla2;
    @FXML
    private Text txtRegla3;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuDto = (UsuariosDTO) AppContext.getInstance().get("usuarioContrasena");
        lblNombre.setText(usuDto.getNombreCompleto());
        lblCedula.setText(usuDto.getCedula());
        llenarReglas();
        llenarListaNodos();
        desarrollo();
    }

    @FXML
    private void actionCancelar(ActionEvent event) {
        ((Stage) btnCancelar.getScene().getWindow()).close();
    }

    public void llenarReglas() {
        minimiCaracteres = ParametrosService.nombreParametros("password");
        caracteresEspeciales = ParametrosService.nombreParametros("caracteresPassword");
        txtRegla1.setText("1) La contraseña tiene que tener minimo " + minimiCaracteres.getValor() + " caracteres y sin espacios");
        txtRegla2.setText("2) La contraseña tiene que tener al menos un cararter especial como estos " + caracteresEspeciales.getValor());
        txtRegla3.setText("3) La contraseña no debe contar con mas de 4 letras de su nombre");
    }

    public boolean validarContrasenaCaracteresEspeciales(String contra) {
        for (int i = 0; i < contra.length(); i++) {
            for (int j = 0; j < caracteresEspeciales.getValor().length(); j++) {
                if (contra.charAt(i) == caracteresEspeciales.getValor().charAt(j)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean validarContrasenaCaracteresMinimos(String contra) {

        if (contra.length() >= Integer.valueOf(minimiCaracteres.getValor())) {
            return true;
        }
        return false;
    }

    @FXML
    private void actionCambiar(ActionEvent event) {
        String validacion = validarRequeridos();
        if (validacion == null) {

            if (txtcontrasena.getText().equals(txtConfirmarcontrasena.getText())) {
                if (validarContrasenaCaracteresEspeciales(txtcontrasena.getText())) {
                    if (validarContrasenaCaracteresMinimos(txtcontrasena.getText())) {
                        usuDto.setContrasenaEncriptada(txtcontrasena.getText());
                        if (UsuariosService.updateContrasenaUsuario(usuDto) == 200) {
                            RegistrosAccionesService.createRegistroAccion(new RegistrosAccionesDTO(Token.getInstance().getUsuario(), "Cambio la contraseña del usuario " + usuDto.getId(), new Date()));
                            new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Cambiar contraseña", ((Stage) txtcontrasena.getScene().getWindow()), "La contraseña se cambió correctamente");
                            ((Stage) btnCancelar.getScene().getWindow()).close();
                        } else {
                            new Mensaje().showModal(Alert.AlertType.ERROR, "Cambiar contraseña", ((Stage) txtcontrasena.getScene().getWindow()), "La contraseña no se cambió correctamente");
                        }
                    }else {
                        new Mensaje().showModal(Alert.AlertType.ERROR, "Cambiar contraseña", ((Stage) txtcontrasena.getScene().getWindow()), "La contraseña tiene que tener mas de " + minimiCaracteres.getValor() +" caracteres");
                    }
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Cambiar contraseña", ((Stage) txtcontrasena.getScene().getWindow()), "La contraseña no contiene " + caracteresEspeciales.getValor());
                }
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Cambiar contraseña", ((Stage) txtcontrasena.getScene().getWindow()), "Las contraseñas no coinciden");
            }
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cambiar contraseña", ((Stage) txtcontrasena.getScene().getWindow()), validacion);
        }
    }

    public void indicarRequeridos() {
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txtConfirmarcontrasena, txtcontrasena));
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
            }
        }
        if (validos) {
            return null;
        } else {
            return "Los siguientes campos son requeridos " + "[" + invalidos + "].";
        }
    }

    @Override
    public void initialize() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void llenarListaNodos() {
        modDesarrollo.clear();
        modDesarrolloAxiliar.clear();
        modDesarrolloAxiliar.add(txtcontrasena.getPromptText());
        modDesarrolloAxiliar.add(txtConfirmarcontrasena.getPromptText());
        modDesarrolloAxiliar.add(btnCancelar.getText());
        modDesarrolloAxiliar.add(btnCambiar.getText());
        modDesarrollo.addAll(Arrays.asList(txtcontrasena, txtConfirmarcontrasena, btnCancelar, btnCambiar));
    }

    public void desarrollo() {
        String dato = "";
        boolean validos1 = (Boolean) AppContext.getInstance().get("mod");
        if (validos1) {
            for (Node node : modDesarrollo) {
                if (node instanceof JFXTextField) {
                    dato = ((JFXTextField) node).getId();
                    ((JFXTextField) node).setPromptText(dato);
                }
                if (node instanceof JFXButton) {
                    dato = ((JFXButton) node).getId();
                    ((JFXButton) node).setText(dato);
                }
            }
        } else {
            for (int i = 0; i < modDesarrollo.size(); i++) {
                if (modDesarrollo.get(i) instanceof JFXButton) {
                    dato = modDesarrolloAxiliar.get(i);
                    ((JFXButton) modDesarrollo.get(i)).setText(dato);
                }
                if (modDesarrollo.get(i) instanceof JFXTextField) {
                    dato = modDesarrolloAxiliar.get(i);
                    ((JFXTextField) modDesarrollo.get(i)).setPromptText(dato);
                }
            }
        }
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
