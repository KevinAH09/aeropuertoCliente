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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import org.una.aeropuertocliente.utils.AppContext;
import org.una.aeropuertocliente.utils.FlowController;
import org.una.aeropuertocliente.utils.Mensaje;
import org.una.aeropuertocliente.dtos.AuthenticationRequest;
import org.una.aeropuertocliente.dtos.AuthenticationResponse;
import org.una.aeropuertocliente.entitiesServices.LoginService;
import org.una.aeropuertocliente.sharedService.Token;

/**
 * FXML Controller class
 *
 * @author colo7
 */
public class InicioController extends Controller implements Initializable {

    @FXML
    private JFXButton btnSalir;
    @FXML
    private JFXButton bntLogin;
    @FXML
    private JFXButton btnDivisas;

    private List<String> modDesarrolloAxiliar = new ArrayList<>();
    private List<Node> modDesarrollo = new ArrayList<>();
    Boolean mod = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AppContext.getInstance().set("mod", mod);
        llenarListaNodos();
        desarrollo();
    }

    @FXML
    private void actionLogin(ActionEvent event) {
        FlowController.getInstance().goView("login/Login");
    }

    @FXML
    private void actionCambioDivisas(ActionEvent event) {
        AppContext.getInstance().set("cambiodivisas", true);
        FlowController.getInstance().goViewInWindowModalFullScrean("cambioDivisas/CambioDivisas", ((Stage) btnSalir.getScene().getWindow()), true);
    }

    @FXML
    private void actionSalir(ActionEvent event) {
        if (new Mensaje().showConfirmation("Cerrar aplicacion", ((Stage) btnSalir.getScene().getWindow()), "Desea salir de la aplicacion")) {
            ((Stage) btnSalir.getScene().getWindow()).close();
        }
    }

    public void llenarListaNodos() {
        modDesarrollo.clear();
        modDesarrolloAxiliar.clear();
        modDesarrolloAxiliar.add(bntLogin.getText());
        modDesarrolloAxiliar.add(btnSalir.getText());
        modDesarrolloAxiliar.add(btnDivisas.getText());

        modDesarrollo.addAll(Arrays.asList(bntLogin, btnSalir, btnDivisas));
    }

    public void desarrollo() {
        String dato = "";
        boolean validos1 = (Boolean) AppContext.getInstance().get("mod");
        if (validos1) {
            for (Node node : modDesarrollo) {
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
            }
        }
    }

    @Override
    public void initialize() {

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
                        new Mensaje().showModal(Alert.AlertType.ERROR, "Error de activación de modo desarrollador", ((Stage) btnSalir.getScene().getWindow()), "El usuario con el que intenta ingresar no es administrador");
                    }
                }
            } else {
                alertaIngreso();
                if (Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_ADMIN") && Token.getInstance() != null) {
                    cambiarModo();
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error de activación de modo desarrollador", ((Stage) btnSalir.getScene().getWindow()), "El usuario con el que intenta ingresar no es administrador");
                }
            }
        }
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
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error de incio de Sesion", ((Stage) btnSalir.getScene().getWindow()), "El usuario esta inactivo");
                }
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error de incio de Sesion", ((Stage) btnSalir.getScene().getWindow()), "La contraseña o cedula estan incorecctas");
            }

        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de inicio de Sesion", ((Stage) btnSalir.getScene().getWindow()), "Datos incompletos");
        }
    }
}
