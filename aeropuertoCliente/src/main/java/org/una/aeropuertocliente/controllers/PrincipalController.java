/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.una.aeropuertocliente.App;
import org.una.aeropuertocliente.dtos.AuthenticationRequest;
import org.una.aeropuertocliente.dtos.AuthenticationResponse;
import org.una.aeropuertocliente.dtos.ParametrosDTO;
import org.una.aeropuertocliente.entitiesServices.LoginService;
import org.una.aeropuertocliente.entitiesServices.ParametrosService;
import org.una.aeropuertocliente.sharedService.Token;
import org.una.aeropuertocliente.utils.AppContext;
import org.una.aeropuertocliente.utils.FlowController;
import org.una.aeropuertocliente.utils.Mensaje;

/**
 * FXML Controller class
 *
 * @author colo7
 */
public class PrincipalController extends Controller implements Initializable {

    @FXML
    private AnchorPane ancgor;
    @FXML
    private JFXTreeView<String> treeAcciones;
    @FXML
    private VBox vboxPrincipal;

    public static VBox vboxPrincipalStatic;
    @FXML
    private Text textInfoNombre;
    @FXML
    private Text textInfoRol;
    @FXML
    private Text textInfoArea;
    @FXML
    private JFXButton btnCerraSesion;
    @FXML
    private Label lblTree;
    @FXML
    private Label lblVbox;
    public List<Node> modDesarrollo = new ArrayList<>();
    public List<String> modDesarrolloAxiliar = new ArrayList<>();
    Date day = new Date();
    Thread hilo = new Thread();
    Node imgInformacion = new ImageView(new Image("org/una/aeropuertocliente/views/principal/informacion.png"));
    Node imgAdmin = new ImageView(new Image("org/una/aeropuertocliente/views/principal/lengueta.png"));
    Node imgCambioDiv = new ImageView(new Image("org/una/aeropuertocliente/views/principal/intercambio.png"));
    Node imgReportes = new ImageView(new Image("org/una/aeropuertocliente/views/principal/newspaper.png"));

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        vboxPrincipalStatic = vboxPrincipal;
        crearHiloTokenExpiracion();

    }

    @Override
    public void initialize() {
        llenarLabelInformacionUsuario();
        agregarImagenFondo();
        TreeItem<String> root = crearTreeItemRoot();
        if (Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_ADMIN")) {
            crearTreeItemAdministrador(root);
        } else if (Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_AUDITOR")) {
            crearFuncionesAuditor(root);
        } else if (Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_GESTOR") || Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_GERENTE")) {
            if (Token.getInstance().getUsuario().getAreaTrabajoId().getNombreAreaTrabajo().equals("_RRHH")) {
                crearTreeItemFuncionesGerenteRHH(root);
            } else if (Token.getInstance().getUsuario().getAreaTrabajoId().getNombreAreaTrabajo().equals("_OPER_AERO")) {
                crearTreeItemFuncionesGerenteOpeAerolineas(root);
            } else if (Token.getInstance().getUsuario().getAreaTrabajoId().getNombreAreaTrabajo().equals("_GAST_MANT")) {
                crearTreeItemFuncionesGastMantenimiento(root);
            }

        }
        crearTreeItemCambioDivisas(root);
        crearActionTreeView();
        llenarListaNodos();
        desarrollo();
    }

    private void crearHiloTokenExpiracion() {
        day = new Date(new Date().getTime() + tiempoExpiracion() * 1000);
        hilo = new Thread(runnable);
        hilo.start();
        FlowController.getInstance().getStage().setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    private void crearActionTreeView() {
        treeAcciones.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    crearOpcionesCambiarVista();
                }
            }
        });
    }

    private void crearOpcionesCambiarVista() {
        TreeItem<String> item = (TreeItem<String>) treeAcciones.getSelectionModel().getSelectedItem();
        if (item.getValue().equals("Aerolineas")) {
            cambiarVistaPrincipal("aerolineas/Aerolineas");
        } else if (item.getValue().equals("Parametros del sistema")) {
            cambiarVistaPrincipal("parametros/Parametros");
        } else if (item.getValue().equals("Registro de Acciones")) {
            cambiarVistaPrincipal("registroAcciones/RegistroAcciones");
        } else if (item.getValue().equals("Zonas")) {
            cambiarVistaPrincipal("mantenimientoZonas/MantenimientoZonas");
        } else if (item.getValue().equals("Aviones")) {
            cambiarVistaPrincipal("aviones/Aviones");
        } else if (item.getValue().equals("Vuelos")) {
            AppContext.getInstance().set("avionAVuelos", null);
            cambiarVistaPrincipal("vuelos/Vuelos");
        } else if (item.getValue().equals("Control Gastos de manteniento")) {
            cambiarVistaPrincipal("controlGastos/ControlGastos");
        } else if (item.getValue().equals("Usuarios")) {
            cambiarVistaPrincipal("usuarios/Usuarios");
        } else if (item.getValue().equals("Roles")) {
            cambiarVistaPrincipal("roles/Roles");
        } else if (item.getValue().equals("Areas de Trabajo")) {
            cambiarVistaPrincipal("areasTrabajo/AreasTrabajo");
        } else if (item.getValue().equals("Cambio de Divisas")) {
            AppContext.getInstance().set("cambiodivisas", false);
            cambiarVistaPrincipal("cambioDivisas/CambioDivisas");
        } else if (item.getValue().equals("Recorrido de aviones")) {
            cambiarVistaPrincipal("reporteAviones/ReporteAviones");
        } else if (item.getValue().equals("Gastos de mantenimiento")) {
            cambiarVistaPrincipal("reporteGastoMant/ReporteGastoMant");
        }
    }

    private void crearTreeItemCambioDivisas(TreeItem<String> root) {
        TreeItem<String> itemCambioDivisas = new TreeItem<>("Cambio de Divisas");
        itemCambioDivisas.setGraphic(imgCambioDiv);
        root.getChildren().add(itemCambioDivisas);
    }

    private void crearTreeItemFuncionesGastMantenimiento(TreeItem<String> root) {
        TreeItem<String> itemInformacion = crearTreeItemInformacion(root);
        crearTreeItemAreaTrabajo(itemInformacion);
        TreeItem<String> itemAdministracion = crearTreeItemAdministracion(root);
        TreeItem<String> itemGastoMantenimientos = new TreeItem<>("Gastos de manteniento");
        itemAdministracion.getChildren().add(itemGastoMantenimientos);
    }

    private void crearTreeItemFuncionesGerenteOpeAerolineas(TreeItem<String> root) {
        TreeItem<String> itemInformacion = crearTreeItemInformacion(root);
        TreeItem<String> itemAdministracion = crearTreeItemAdministracion(root);
        crearTreeItemAerolineas(itemInformacion);
        crearTreeItemZonas(itemInformacion);
        crearTreeItemVuelos(itemAdministracion);
    }

    private void crearTreeItemFuncionesGerenteRHH(TreeItem<String> root) {
        TreeItem<String> itemInformacion = crearTreeItemInformacion(root);
        crearTreeItemUsuarios(itemInformacion);
        crearTreeItemRoles(itemInformacion);
        crearTreeItemAreaTrabajo(itemInformacion);
    }

    private void crearFuncionesAuditor(TreeItem<String> root) {
        TreeItem<String> itemInformacion = crearTreeItemInformacion(root);
        TreeItem<String> itemAdministracion = crearTreeItemAdministracion(root);
        TreeItem<String> itemReporte = crearTreeItemReportes(root);
        crearTreeItemUsuarios(itemInformacion);
        crearTreeItemRoles(itemInformacion);
        crearTreeItemAreaTrabajo(itemInformacion);
        crearTreeItemAerolineas(itemInformacion);
        crearTreeItemZonas(itemInformacion);
        crearTreeItemRocorridoAviones(itemReporte);
        crearTreeItemReporteGastosMantenimiento(itemReporte);
        crearTreeItemControlGastosMantenimiento(itemAdministracion);
        crearTreeItemVuelos(itemAdministracion);
        crearTreeItemRegistroAcciones(itemInformacion);
    }

    private void crearTreeItemAdministrador(TreeItem<String> root) {
        TreeItem<String> itemInformacion = crearTreeItemInformacion(root);
        TreeItem<String> itemAdministracion = crearTreeItemAdministracion(root);
        TreeItem<String> itemReporte = crearTreeItemReportes(root);
        TreeItem<String> itemParametros = new TreeItem<>("Parametros del sistema");
        itemAdministracion.getChildren().add(itemParametros);
        crearTreeItemUsuarios(itemInformacion);
        crearTreeItemRoles(itemInformacion);
        crearTreeItemAreaTrabajo(itemInformacion);
        crearTreeItemAerolineas(itemInformacion);
        crearTreeItemZonas(itemInformacion);
        crearTreeItemRocorridoAviones(itemReporte);
        crearTreeItemReporteGastosMantenimiento(itemReporte);
        crearTreeItemControlGastosMantenimiento(itemAdministracion);
        crearTreeItemVuelos(itemAdministracion);
        crearTreeItemRegistroAcciones(itemInformacion);
    }

    private void crearTreeItemRegistroAcciones(TreeItem<String> itemInformacion) {
        TreeItem<String> itemRegistroAcciones = new TreeItem<>("Registro de Acciones");
        itemInformacion.getChildren().add(itemRegistroAcciones);
    }

    private void crearTreeItemVuelos(TreeItem<String> itemAdministracion) {
        TreeItem<String> itemRegistroVuelo = new TreeItem<>("Vuelos");
        itemAdministracion.getChildren().add(itemRegistroVuelo);
    }

    private void crearTreeItemControlGastosMantenimiento(TreeItem<String> itemAdministracion) {
        TreeItem<String> itemGastoMantenimientos = new TreeItem<>("Control Gastos de manteniento");
        itemAdministracion.getChildren().add(itemGastoMantenimientos);
    }

    private void crearTreeItemReporteGastosMantenimiento(TreeItem<String> itemReporte) {
        TreeItem<String> itemReporteGastosMant = new TreeItem<>("Gastos de mantenimiento");
        itemReporte.getChildren().add(itemReporteGastosMant);
    }

    private void crearTreeItemRocorridoAviones(TreeItem<String> itemReporte) {
        TreeItem<String> itemReporteVuelos = new TreeItem<>("Recorrido de aviones");
        itemReporte.getChildren().add(itemReporteVuelos);
    }

    private void crearTreeItemZonas(TreeItem<String> itemInformacion) {
        TreeItem<String> itemZonas = new TreeItem<>("Zonas");
        itemInformacion.getChildren().add(itemZonas);
    }

    private void crearTreeItemAerolineas(TreeItem<String> itemInformacion) {
        TreeItem<String> itemAerolinea = new TreeItem<>("Aerolineas");
        itemInformacion.getChildren().add(itemAerolinea);
    }

    private void crearTreeItemAreaTrabajo(TreeItem<String> itemInformacion) {
        TreeItem<String> itemAreaTrabajo = new TreeItem<>("Areas de Trabajo");
        itemInformacion.getChildren().add(itemAreaTrabajo);
    }

    private void crearTreeItemRoles(TreeItem<String> itemInformacion) {
        TreeItem<String> itemRoles = new TreeItem<>("Roles");
        itemInformacion.getChildren().add(itemRoles);
    }

    private void crearTreeItemUsuarios(TreeItem<String> itemInformacion) {
        TreeItem<String> itemUsuarios = new TreeItem<>("Usuarios");
        itemInformacion.getChildren().add(itemUsuarios);
    }

    private TreeItem<String> crearTreeItemReportes(TreeItem<String> root) {
        TreeItem<String> itemReporte = new TreeItem<>("Reportes");
        itemReporte.setGraphic(imgReportes);
        root.getChildren().add(itemReporte);
        return itemReporte;
    }

    private TreeItem<String> crearTreeItemAdministracion(TreeItem<String> root) {
        TreeItem<String> itemAdministracion = new TreeItem<>("Administracion");
        itemAdministracion.setGraphic(imgAdmin);
        root.getChildren().add(itemAdministracion);
        return itemAdministracion;
    }

    private TreeItem<String> crearTreeItemInformacion(TreeItem<String> root) {
        TreeItem<String> itemInformacion = new TreeItem<>("Informacion");
        itemInformacion.setGraphic(imgInformacion);
        root.getChildren().add(itemInformacion);
        return itemInformacion;
    }

    private TreeItem<String> crearTreeItemRoot() {
        Node imgroot = new ImageView(new Image("org/una/aeropuertocliente/views/principal/menu.png"));
        TreeItem<String> root = new TreeItem<>("Funciones");
        root.setGraphic(imgroot);
        root.setExpanded(true);
        treeAcciones.setRoot(root);
        return root;
    }

    private void llenarLabelInformacionUsuario() {
        textInfoNombre.setText(" " + Token.getInstance().getUsuario().getNombreCompleto());
        textInfoRol.setText(" " + Token.getInstance().getUsuario().getRolId().getDescripcion());
        if (!Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_ADMIN") && !Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_AUDITOR")) {
            textInfoArea.setText(" " + Token.getInstance().getUsuario().getAreaTrabajoId().getDescripcion());
        } else {
            textInfoArea.setText(" No posee");
        }
    }

    private void agregarImagenFondo() {
        ImageView imageView2 = new ImageView(new Image("org/una/aeropuertocliente/views/shared/fondo.png"));
        imageView2.setFitWidth(300);
        imageView2.setFitHeight(300);
        vboxPrincipalStatic.getChildren().add(imageView2);
    }

    @FXML

    private void actionCerrarSesion(ActionEvent event) {
        hilo.stop();
        if (Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_ADMIN")) {
            boolean validos1 = (Boolean) AppContext.getInstance().get("mod");
            if (validos1) {
                AppContext.getInstance().set("mod", false);
            }
        }
        FlowController.getInstance().goMain();
        FlowController.eliminar("login/Login");
        FlowController.getInstance().goView("login/Login");
    }

    @FXML
    private void actionTamano(MouseEvent event) {
    }

    public static void cambiarVistaPrincipal(String ruta) {
        try {
            vboxPrincipalStatic.getChildren().clear();
            Parent root = FXMLLoader.load(App.class
                    .getResource("views/" + ruta + ".fxml"));
            vboxPrincipalStatic.getChildren()
                    .add(root);
        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void modoDesarrollo(KeyEvent event) {
        KeyCombination cntrlD = new KeyCodeCombination(KeyCode.D, KeyCodeCombination.CONTROL_DOWN);
    }

    public void llenarListaNodos() {
        modDesarrollo.clear();
        modDesarrolloAxiliar.clear();
        modDesarrolloAxiliar.add(lblVbox.getText());
        modDesarrolloAxiliar.add(lblTree.getText());
        modDesarrolloAxiliar.add(btnCerraSesion.getText());
        modDesarrollo.addAll(Arrays.asList(lblVbox, lblTree, btnCerraSesion));
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
                if (node instanceof Label) {
                    if (node == lblVbox) {
                        dato = vboxPrincipal.getId();
                        ((Label) node).setText(dato);
                    }
                    if (node == lblTree) {
                        dato = treeAcciones.getId();
                        ((Label) node).setText(dato);
                    }
                }
            }
        } else {
            for (int i = 0; i < modDesarrollo.size(); i++) {
                if (modDesarrollo.get(i) instanceof JFXButton) {
                    dato = modDesarrolloAxiliar.get(i);
                    ((JFXButton) modDesarrollo.get(i)).setText(dato);
                }
                if (modDesarrollo.get(i) instanceof Label) {
                    dato = modDesarrolloAxiliar.get(i);
                    ((Label) modDesarrollo.get(i)).setText(dato);
                }
            }
        }
    }
    public boolean consul = true;
    public Runnable runnable = () -> {

        while (consul) {
            try {
                if (new Date().after(day)) {
                    Platform.runLater(() -> alertaConfirmacion());
                    hilo.stop();
                }
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    private int tiempoExpiracion() {
        ParametrosDTO parametro = ParametrosService.nombreParametros("expiracionToken");
        if (parametro != null) {
            System.out.println(parametro.getValor());
            return Integer.valueOf(parametro.getValor()) * 3600;
        }
        return 3600;
    }

    private void alertaConfirmacion() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Sesión expirada");
        alert.setHeaderText("Su sesión ha expirado, debe autenticarse de nuevo");
        alert.setContentText("Desea autenticarse?, de no hacerlo se le redirigirá al inicio de sesión.");
        ButtonType buttonTypeYes = new ButtonType("Sí", ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("No", ButtonData.NO);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeYes) {
            alertaIngreso();
        } else {
            FlowController.getInstance().goView("login/Login");
        }
    }

    private void reingresar(String user, String pass) {
        if (user != null && pass != null) {
            Token.setInstance(null);
            AuthenticationRequest authenticationRequest = new AuthenticationRequest(user, pass);
            AuthenticationResponse authenticationResponse = LoginService.login(authenticationRequest);

            if (authenticationResponse != null) {
                Token.setInstance(authenticationResponse);
                if (Token.getInstance().getUsuario().isEstado()) {
                    day = new Date(new Date().getTime() + tiempoExpiracion() * 1000);
                    hilo = new Thread(runnable);
                    hilo.start();
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error de incio de Sesion", ((Stage) btnCerraSesion.getScene().getWindow()), "El usuario esta inactivo");
                }
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error de incio de Sesion", ((Stage) btnCerraSesion.getScene().getWindow()), "La contraseña o cedula estan incorecctas");
            }

        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de inicio de Sesion", ((Stage) btnCerraSesion.getScene().getWindow()), "Datos incompletos");
        }
    }

    private void alertaIngreso() {
        Dialog<Pair<String, String>> dialog = crearDialog();
        ButtonType loginButtonType = crearButtonDialog(dialog);
        GridPane grid = crearGridPaneDialog();
        JFXTextField username = crearTXTUserDialog();
        JFXPasswordField password = crearTXTPassDialog();
        crearNodeButtonDialog(dialog, loginButtonType, username);
        grid.add(username, 1, 0);
        grid.add(password, 1, 2);
        dialog.getDialogPane().setContent(grid);
        crearFuncionabilidadDialog(username, dialog, loginButtonType, password);
    }

    private void crearFuncionabilidadDialog(JFXTextField username, Dialog<Pair<String, String>> dialog, ButtonType loginButtonType, JFXPasswordField password) {
        Platform.runLater(() -> username.requestFocus());
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            } else {
                FlowController.getInstance().goView("login/Login");
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(usernamePassword -> {
            System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
            System.out.println(Token.getInstance().getUsuario().getCedula());
            if (Token.getInstance().getUsuario().getCedula().equals(usernamePassword.getKey())) {
                reingresar(usernamePassword.getKey(), usernamePassword.getValue());
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Datos incoherentes", ((Stage) btnCerraSesion.getScene().getWindow()), "Debe ingresar las credenciales con las que inició anteriormente");
                alertaIngreso();
            }
        });
    }

    private void crearNodeButtonDialog(Dialog<Pair<String, String>> dialog, ButtonType loginButtonType, JFXTextField username) {
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });
    }

    private JFXPasswordField crearTXTPassDialog() {
        JFXPasswordField password = new JFXPasswordField();
        password.setPromptText("Contraseña");
        password.setLabelFloat(true);
        password.setPrefWidth(150);
        return password;
    }

    private JFXTextField crearTXTUserDialog() {
        JFXTextField username = new JFXTextField();
        username.setPromptText("Cédula");
        username.setLabelFloat(true);
        username.setPrefWidth(150);
        return username;
    }

    private GridPane crearGridPaneDialog() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        return grid;
    }

    private ButtonType crearButtonDialog(Dialog<Pair<String, String>> dialog) {
        ButtonType loginButtonType = new ButtonType("Accesar", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
        return loginButtonType;
    }

    private Dialog<Pair<String, String>> crearDialog() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Habilitar el modo desarrollador");
        dialog.setHeaderText("Ingrese las credenciales del usuario administrador");
        dialog.setGraphic(new ImageView(new Image("org/una/aeropuertocliente/views/shared/user.png")));
        return dialog;
    }

}
