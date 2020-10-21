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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void initialize() {
        Node imgroot = new ImageView(new Image("org/una/aeropuertocliente/views/principal/menu.png"));
        Node imgInformacion = new ImageView(new Image("org/una/aeropuertocliente/views/principal/informacion.png"));
        Node imgAdmin = new ImageView(new Image("org/una/aeropuertocliente/views/principal/lengueta.png"));
        TreeItem<String> root = new TreeItem<>("Funciones");
        root.setGraphic(imgroot);
        treeAcciones.setRoot(root);
        TreeItem<String> itemInformacion = new TreeItem<>("Informacion");
        itemInformacion.setGraphic(imgInformacion);
        root.getChildren().add(itemInformacion);
        TreeItem<String> itemAdministracion = new TreeItem<>("Administracion");
        itemAdministracion.setGraphic(imgAdmin);
        root.getChildren().add(itemAdministracion);

        TreeItem<String> itemTramites = new TreeItem<>("Aerolineas");
        itemInformacion.getChildren().add(itemTramites);

        treeAcciones.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if (mouseEvent.getClickCount() == 2) {
                    TreeItem<String> item = (TreeItem<String>) treeAcciones.getSelectionModel()
                            .getSelectedItem();
                    try {
                        if (item.getValue().equals("Aerolineas")) {
                            cambiarAerolineas();
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
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

    void cambiarAerolineas() throws IOException {
        vboxPrincipal.getChildren().clear();
        Parent root = FXMLLoader.load(App.class
                .getResource("views/aerolineas/Aerolineas.fxml"));
        vboxPrincipal.getChildren()
                .add(root);
    }

}
