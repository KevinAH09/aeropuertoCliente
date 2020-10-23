/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.una.aeropuertocliente.App;
import org.una.aeropuertocliente.sharedService.Token;
import org.una.aeropuertocliente.utils.AppContext;
import org.una.aeropuertocliente.utils.FlowController;

/**
 * FXML Controller class
 *
 * @author colo7
 */
public class PrincipalController extends Controller implements Initializable {

    @FXML
    private AnchorPane ancgor;
    @FXML
    private Label lblHora;
    @FXML
    private TreeView<String> treeAcciones;
    @FXML
    private VBox vboxPrincipal;

    public static VBox vboxPrincipalStatic;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vboxPrincipalStatic = vboxPrincipal;
    }

    @Override
    public void initialize() {
        Node imgroot = new ImageView(new Image("org/una/aeropuertocliente/views/principal/menu.png"));
        Node imgInformacion = new ImageView(new Image("org/una/aeropuertocliente/views/principal/informacion.png"));
        Node imgAdmin = new ImageView(new Image("org/una/aeropuertocliente/views/principal/lengueta.png"));
        Node imgCambioDiv = new ImageView(new Image("org/una/aeropuertocliente/views/principal/intercambio.png"));
        TreeItem<String> root = new TreeItem<>("Funciones");
        root.setGraphic(imgroot);
        treeAcciones.setRoot(root);
        if (Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_ADMIN")) {
            TreeItem<String> itemParametros = new TreeItem<>("Parametros del sistema");
            root.getChildren().add(itemParametros);
            TreeItem<String> itemRegistroAcciones = new TreeItem<>("Registro de Acciones");
            root.getChildren().add(itemRegistroAcciones);
        } else if (Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_AUDITOR")) {
            TreeItem<String> itemInformacion = new TreeItem<>("Informacion");
            itemInformacion.setGraphic(imgInformacion);
            root.getChildren().add(itemInformacion);
            TreeItem<String> itemAdministracion = new TreeItem<>("Administracion");
            itemAdministracion.setGraphic(imgAdmin);
            root.getChildren().add(itemAdministracion);
            TreeItem<String> itemUsuarios = new TreeItem<>("Usuarios");
            itemInformacion.getChildren().add(itemUsuarios);
            TreeItem<String> itemRoles = new TreeItem<>("Roles");
            itemInformacion.getChildren().add(itemRoles);
            TreeItem<String> itemAreaTrabajo = new TreeItem<>("Areas de Trabajo");
            itemInformacion.getChildren().add(itemAreaTrabajo);
            TreeItem<String> itemAerolinea = new TreeItem<>("Aerolineas");
            itemInformacion.getChildren().add(itemAerolinea);
            TreeItem<String> itemZonas = new TreeItem<>("Zonas");
            itemInformacion.getChildren().add(itemZonas);
            TreeItem<String> itemAvion = new TreeItem<>("Aviones");
            itemInformacion.getChildren().add(itemAvion);
            TreeItem<String> itemGastoMantenimientos = new TreeItem<>("Gastos de manteniento");
            itemAdministracion.getChildren().add(itemGastoMantenimientos);
            TreeItem<String> itemRegistroVuelo = new TreeItem<>("Vuelos");
            itemAdministracion.getChildren().add(itemRegistroVuelo);
            TreeItem<String> itemRegistroAcciones = new TreeItem<>("Registro de Acciones");
            itemInformacion.getChildren().add(itemRegistroAcciones);
        } else if (Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_GESTOR") || Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_GERENTE")) {
            if (Token.getInstance().getUsuario().getAreaTrabajoId().getNombreAreaTrabajo().equals("_RRHH")) {
                TreeItem<String> itemInformacion = new TreeItem<>("Informacion");
                itemInformacion.setGraphic(imgInformacion);
                root.getChildren().add(itemInformacion);
                TreeItem<String> itemAdministracion = new TreeItem<>("Administracion");
                itemAdministracion.setGraphic(imgAdmin);
                root.getChildren().add(itemAdministracion);
                TreeItem<String> itemUsuarios = new TreeItem<>("Usuarios");
                itemInformacion.getChildren().add(itemUsuarios);
                TreeItem<String> itemRoles = new TreeItem<>("Roles");
                itemInformacion.getChildren().add(itemRoles);
                TreeItem<String> itemAreaTrabajo = new TreeItem<>("Areas de Trabajo");
                itemInformacion.getChildren().add(itemAreaTrabajo);
            } else if (Token.getInstance().getUsuario().getAreaTrabajoId().getNombreAreaTrabajo().equals("_OPER_AERO")) {
                TreeItem<String> itemInformacion = new TreeItem<>("Informacion");
                itemInformacion.setGraphic(imgInformacion);
                root.getChildren().add(itemInformacion);
                TreeItem<String> itemAdministracion = new TreeItem<>("Administracion");
                itemAdministracion.setGraphic(imgAdmin);
                root.getChildren().add(itemAdministracion);
                TreeItem<String> itemAerolinea = new TreeItem<>("Aerolineas");
                itemInformacion.getChildren().add(itemAerolinea);
                TreeItem<String> itemZonas = new TreeItem<>("Zonas");
                itemInformacion.getChildren().add(itemZonas);
                TreeItem<String> itemAvion = new TreeItem<>("Aviones");
                itemInformacion.getChildren().add(itemAvion);
                TreeItem<String> itemRegistroVuelo = new TreeItem<>("Vuelos");
                itemAdministracion.getChildren().add(itemRegistroVuelo);
            } else if (Token.getInstance().getUsuario().getAreaTrabajoId().getNombreAreaTrabajo().equals("_GAST_MANT")) {
                TreeItem<String> itemInformacion = new TreeItem<>("Informacion");
                itemInformacion.setGraphic(imgInformacion);
                root.getChildren().add(itemInformacion);
                TreeItem<String> itemAreaTrabajo = new TreeItem<>("Areas de Trabajo");
                itemInformacion.getChildren().add(itemAreaTrabajo);
                TreeItem<String> itemAdministracion = new TreeItem<>("Administracion");
                itemAdministracion.setGraphic(imgAdmin);
                root.getChildren().add(itemAdministracion);
                TreeItem<String> itemGastoMantenimientos = new TreeItem<>("Gastos de manteniento");
                itemAdministracion.getChildren().add(itemGastoMantenimientos);
            }

        }
        TreeItem<String> itemCambioDivisas = new TreeItem<>("Cambio de Divisas");
        itemCambioDivisas.setGraphic(imgCambioDiv);
        root.getChildren().add(itemCambioDivisas);

        treeAcciones.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if (mouseEvent.getClickCount() == 2) {
                    TreeItem<String> item = (TreeItem<String>) treeAcciones.getSelectionModel()
                            .getSelectedItem();

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
                        cambiarVistaPrincipal("vuelos/Vuelos");
                    } else if (item.getValue().equals("Gastos de manteniento")) {
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
                    }

                }

            }
        });
    }

    @FXML
    private void actionCerrarSesion(ActionEvent event) {
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

}
