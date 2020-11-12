/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import org.una.aeropuertocliente.dtos.AuthenticationRequest;
import org.una.aeropuertocliente.dtos.AuthenticationResponse;
import org.una.aeropuertocliente.dtos.RegistrosAccionesDTO;
import org.una.aeropuertocliente.entitiesServices.LoginService;
import org.una.aeropuertocliente.entitiesServices.RegistrosAccionesService;
import org.una.aeropuertocliente.sharedService.Token;
import org.una.aeropuertocliente.utils.AppContext;
import org.una.aeropuertocliente.utils.FlowController;
import org.una.aeropuertocliente.utils.Mensaje;

/**
 * FXML Controller class
 *
 * @author colo7
 */
public class LoginController extends Controller implements Initializable {

    @FXML
    private JFXTextField txtUsuario;
    @FXML
    private JFXTextField txtPassMostrado;
    @FXML
    private JFXPasswordField txtPassOculto;
    @FXML
    private ImageView imgViewPassword;
    @FXML
    private ImageView imgNotPassword;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXButton btnIngresar;

    private String pass;
    private List<Node> requeridos = new ArrayList<>();
    private List<Node> modDesarrollo = new ArrayList<>();
    private List<String> modDesarrolloAxiliar = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtPassMostrado.setVisible(false);
        txtPassOculto.setText(txtPassMostrado.getText());
        txtPassOculto.setVisible(true);
        imgNotPassword.setVisible(false);
        imgViewPassword.setVisible(true);
        indicarRequeridos();
        llenarListaNodos();
        desarrollo();
    }

    @FXML
    private void actionViewPass(MouseEvent event) {
        if (txtPassMostrado.isVisible()) {
            txtPassMostrado.setVisible(false);
            txtPassOculto.setText(txtPassMostrado.getText());
            txtPassOculto.setVisible(true);
            imgNotPassword.setVisible(false);
            imgViewPassword.setVisible(true);

        } else {
            txtPassOculto.setVisible(false);
            txtPassMostrado.setText(txtPassOculto.getText());
            txtPassMostrado.setVisible(true);
            imgNotPassword.setVisible(true);
            imgViewPassword.setVisible(false);
        }
    }

    @FXML
    private void actionIngresar(ActionEvent event) {
        if (txtPassMostrado.isVisible()) {
            pass = txtPassMostrado.getText();
        } else {
            pass = txtPassOculto.getText();
        }
        txtPassMostrado.setText(txtPassOculto.getText());
        txtPassOculto.setText(txtPassMostrado.getText());
        String validacion = validarRequeridos();
        if (validacion == null) {

            Token.setInstance(null);
            AuthenticationRequest authenticationRequest = new AuthenticationRequest(txtUsuario.getText(), pass);
            AuthenticationResponse authenticationResponse = LoginService.login(authenticationRequest);
            if (authenticationResponse != null) {
                Token.setInstance(authenticationResponse);
                if (Token.getInstance().getUsuario().isEstado()) {
                    if (!Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_ADMIN")) {
                        AppContext.getInstance().set("mod", false);
                    }
                    RegistrosAccionesService.createRegistroAccion(new RegistrosAccionesDTO(Token.getInstance().getUsuario(), "Inicio de Sesion", new Date()));
                    FlowController.getInstance().goView("principal/Principal");
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error de incio de Sesion", ((Stage) txtPassOculto.getScene().getWindow()), "El usuario esta inactivo");
                }
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error de incio de Sesion", ((Stage) txtPassOculto.getScene().getWindow()), "La contraseña o cedula estan incorecctas");
            }

        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de incio de Sesion", ((Stage) txtPassOculto.getScene().getWindow()), validacion);
        }
    }

    @FXML
    private void actionSalir(ActionEvent event) {
        if (new Mensaje().showConfirmation("Volver a inicio", ((Stage) btnCancelar.getScene().getWindow()), "¿Desea salir del login?")) {
            FlowController.getInstance().goView("inicio/Inicio");
        }
    }

    @FXML
    private void actionKeyUsuario(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB) {
            if (txtPassMostrado.isVisible()) {
                txtPassMostrado.requestFocus();
            } else {
                txtPassOculto.requestFocus();
            }
        } else if (event.getCode() == KeyCode.ENTER) {
            actionIngresar(null);
        }
    }

    @FXML
    private void actionKeyPass(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB) {
            txtUsuario.requestFocus();
        } else if (event.getCode() == KeyCode.ENTER) {
            actionIngresar(null);
        }
    }

    public void indicarRequeridos() {
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txtPassMostrado, txtPassOculto, txtUsuario));
    }

    public void llenarListaNodos() {
        modDesarrollo.clear();
        modDesarrolloAxiliar.clear();
        modDesarrolloAxiliar.add(txtPassMostrado.getPromptText());
        modDesarrolloAxiliar.add(txtPassOculto.getPromptText());
        modDesarrolloAxiliar.add(txtUsuario.getPromptText());
        modDesarrolloAxiliar.add(btnIngresar.getText());
        modDesarrolloAxiliar.add(btnCancelar.getText());
        modDesarrollo.addAll(Arrays.asList(txtPassMostrado, txtPassOculto, txtUsuario, btnIngresar, btnCancelar));
    }

    public void desarrollo() {
        boolean validos1 = (Boolean) AppContext.getInstance().get("mod");
        String dato = "";
        if (validos1) {
            for (Node node : modDesarrollo) {
                if (node instanceof JFXTextField) {
                    dato = ((JFXTextField) node).getId();
                    ((JFXTextField) node).setPromptText(dato);
                }
                if (node instanceof JFXPasswordField) {
                    dato = ((JFXPasswordField) node).getId();
                    ((JFXPasswordField) node).setPromptText(dato);
                }
                if (node instanceof JFXButton) {
                    dato = ((JFXButton) node).getId();
                    ((JFXButton) node).setText(dato);
                }
            }
        } else {
            for (int i = 0; i < modDesarrollo.size(); i++) {
                if (modDesarrollo.get(i) instanceof JFXTextField) {
                    dato = modDesarrolloAxiliar.get(i);
                    ((JFXTextField) modDesarrollo.get(i)).setPromptText(dato);
                }
                if (modDesarrollo.get(i) instanceof JFXPasswordField) {
                    dato = modDesarrolloAxiliar.get(i);
                    ((JFXPasswordField) modDesarrollo.get(i)).setPromptText(dato);
                }
                if (modDesarrollo.get(i) instanceof JFXButton) {
                    dato = modDesarrolloAxiliar.get(i);
                    ((JFXButton) modDesarrollo.get(i)).setText(dato);
                }
            }
        }
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

    @FXML
    private void modoDesarrollo(KeyEvent event) {
        KeyCombination cntrlD = new KeyCodeCombination(KeyCode.D, KeyCodeCombination.CONTROL_DOWN);
        if (cntrlD.match(event)) {
            if (Token.getInstance().getUsuario() != null) {
                if (Token.getInstance().getUsuario().getRolId().getCodigo() == "ROLE_ADMIN") {
                    cambiarModo();
                } else {
                    alertaIngreso();
                    if (Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_ADMIN") && Token.getInstance() != null) {
                        cambiarModo();
                    } else {
                        new Mensaje().showModal(Alert.AlertType.ERROR, "Error de activación de modo desarrollador", ((Stage) btnIngresar.getScene().getWindow()), "El usuario con el que intenta ingresar no es administrador");
                    }
                }
            } else {
                alertaIngreso();
                if (Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_ADMIN") && Token.getInstance() != null) {
                    cambiarModo();
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error de activación de modo desarrollador", ((Stage) btnIngresar.getScene().getWindow()), "El usuario con el que intenta ingresar no es administrador");
                }
            }
        }
    }

    private void IngresarModoDesarrollador(String user, String pass) {
        if (user != null && pass != null) {
            Token.setInstance(null);
            AuthenticationRequest authenticationRequest = new AuthenticationRequest(user, pass);
            AuthenticationResponse authenticationResponse = LoginService.login(authenticationRequest);

            if (authenticationResponse != null) {
                Token.setInstance(authenticationResponse);
                if (Token.getInstance().getUsuario().isEstado()) {
                    System.out.println("El usuario se encuentra activo");
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error de incio de Sesion", ((Stage) btnIngresar.getScene().getWindow()), "El usuario esta inactivo");
                }
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error de incio de Sesion", ((Stage) btnIngresar.getScene().getWindow()), "La contraseña o cedula estan incorecctas");
            }

        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de inicio de Sesion", ((Stage) btnIngresar.getScene().getWindow()), "Datos incompletos");
        }
    }

    private void alertaIngreso() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Habilitar el modo desarrollador");
        dialog.setHeaderText("Ingrese las credenciales del usuario administrador");

        dialog.setGraphic(new ImageView(new Image("org/una/aeropuertocliente/views/shared/user.png")));

        ButtonType loginButtonType = new ButtonType("Accesar", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        JFXTextField username = new JFXTextField();
        username.setPromptText("Cédula");
        username.setLabelFloat(true);
        username.setPrefWidth(150);

        JFXPasswordField password = new JFXPasswordField();
        password.setPromptText("Contraseña");
        password.setLabelFloat(true);
        password.setPrefWidth(150);

        grid.add(username, 1, 0);
        grid.add(password, 1, 2);

        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });
        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> username.requestFocus());
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(usernamePassword -> {
            System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
            IngresarModoDesarrollador(usernamePassword.getKey(), usernamePassword.getValue());
        });
    }

    private void cambiarModo() {
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
