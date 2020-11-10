/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.una.aeropuertocliente.dtos.RegistrosAccionesDTO;
import org.una.aeropuertocliente.entitiesServices.RegistrosAccionesService;
import org.una.aeropuertocliente.sharedService.Token;
import org.una.aeropuertocliente.utils.FlowController;

/**
 * FXML Controller class
 *
 * @author colo7
 */
public class RegistroAccionesController extends Controller implements Initializable {

    @FXML
    private JFXButton btnBuscarUsuario;
    @FXML
    private JFXDatePicker fechaRegistro;
    @FXML
    private JFXComboBox<String> cmbFiltro;
    @FXML
    private Label lblTable;
    @FXML
    private TableView<RegistrosAccionesDTO> tableRegistroAcciones;

    List<RegistrosAccionesDTO> listRegistroAcciones = new ArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnBuscarUsuario.setVisible(false);
        fechaRegistro.setVisible(false);
        llenarComboBox();
        notificar(1);
    }

    private void llenarComboBox() {
        cmbFiltro.setItems(FXCollections.observableArrayList("Filtrar por fecha", "Filtrar por usuario"));
    }

    @FXML
    private void actionBuscarUsuario(ActionEvent event) {

        FlowController.getInstance().goViewInWindowModal("usuarios/Usuarios", ((Stage) btnBuscarUsuario.getScene().getWindow()), true);
    }

    @FXML
    private void actionFiltrarFecha(ActionEvent event) {
        limpiarTableView();
        listRegistroAcciones = RegistrosAccionesService.FechaRegistrosAcciones(fechaRegistro.getValue().toString());
        if (listRegistroAcciones != null) {
            llenarTableView();
            tableRegistroAcciones.setItems(FXCollections.observableArrayList(listRegistroAcciones));
        } else {
            notificar(0);
        }

    }

    private void limpiarTableView() {
        tableRegistroAcciones.getItems().clear();
        tableRegistroAcciones.getColumns().clear();
    }

    private void notificar(int num) {
        limpiarTableView();
        if (num == 1) {
            alertar1();
        } else {
            alertar2();
        }
    }

    private void alertar2() {
        ImageView imageView2 = new ImageView(new Image("org/una/aeropuertocliente/views/shared/warning.png"));
        Text lab = new Text("No se encontró coincidencias");
        lab.setFill(Color.web("#0076a3"));
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(imageView2);
        box.getChildren().add(lab);
        tableRegistroAcciones.setPlaceholder(box);
    }

    private void alertar1() {
        ImageView imageView = new ImageView(new Image("org/una/aeropuertocliente/views/shared/info.png"));
        Text lab = new Text("Para mostrar datos en este apartado debe realizar el filtro correspondiente");
        lab.setFill(Color.web("#0076a3"));
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(imageView);
        box.getChildren().add(lab);
        tableRegistroAcciones.setPlaceholder(box);
    }

    private void llenarTableView() {
        TableColumn<RegistrosAccionesDTO, Long> colId = new TableColumn("Id");
        colId.setCellValueFactory((param) -> new SimpleObjectProperty<>(param.getValue().getId()));
        TableColumn<RegistrosAccionesDTO, String> colAccion = new TableColumn("Accion realizada");
        colAccion.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getAccion()));
        TableColumn<RegistrosAccionesDTO, String> colFecha = new TableColumn("Fecha Registro");
        colFecha.setCellValueFactory((param) -> new SimpleObjectProperty(new SimpleDateFormat("dd-MM-yyyy").format(param.getValue().getFechaRegistro())));
        TableColumn<RegistrosAccionesDTO, String> colUsuario = new TableColumn("Usuario");
        colUsuario.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getUsuarioId().getNombreCompleto()));

        tableRegistroAcciones.getColumns().addAll(colId, colAccion, colFecha, colUsuario);
    }

    @FXML
    private void actionOptionFiltrar(ActionEvent event) {
        if (cmbFiltro.getValue().equals("Filtrar por fecha")) {
            fechaRegistro.setVisible(true);
            btnBuscarUsuario.setVisible(false);
        } else if (cmbFiltro.getValue().equals("Filtrar por usuario")) {
            fechaRegistro.setVisible(false);
            btnBuscarUsuario.setVisible(true);
        }
    }

    @Override
    public void initialize() {

    }

}
