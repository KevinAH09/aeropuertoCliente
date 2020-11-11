/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
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
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.una.aeropuertocliente.dtos.RegistrosAccionesDTO;
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
    public List<Node> modDesarrollo = new ArrayList<>();
    public List<String> modDesarrolloAxiliar = new ArrayList<>();
    @FXML
    private Label titulo;

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
        llenarListaNodos();
        desarrollo();
    }

    @FXML
    private void actionBuscarUsuario(ActionEvent event) {
        AppContext.getInstance().set("ModoBuscarUsuario", true);
        FlowController.getInstance().goViewInWindowModal("usuarios/Usuarios", ((Stage) btnBuscarUsuario.getScene().getWindow()), true);
        AppContext.getInstance().set("ModoBuscarUsuario", null);
        if (AppContext.getInstance().get("usuId") != null) {
            listRegistroAcciones = RegistrosAccionesService.UsuarioRegistrosAcciones((Long) AppContext.getInstance().get("usuId"));
            if (listRegistroAcciones != null) {
                llenarTableView();
                tableRegistroAcciones.setItems(FXCollections.observableArrayList(listRegistroAcciones));
            } else {
                notificar(0);
            }
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Buscar registros", ((Stage) fechaRegistro.getScene().getWindow()), "No selecciono ningun usuario");
        }
    }

    @FXML
    private void actionFiltrarFecha(ActionEvent event) {
        if (fechaRegistro.getValue() != null) {
            limpiarTableView();
            listRegistroAcciones = RegistrosAccionesService.FechaRegistrosAcciones(fechaRegistro.getValue().toString());
            if (listRegistroAcciones != null) {
                llenarTableView();
                tableRegistroAcciones.setItems(FXCollections.observableArrayList(listRegistroAcciones));
            } else {
                notificar(0);
            }
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Buscar registros", ((Stage) fechaRegistro.getScene().getWindow()), "Seleccione una  fecha");
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

    public void llenarListaNodos() {
        modDesarrollo.clear();
        modDesarrolloAxiliar.clear();
        modDesarrolloAxiliar.add(titulo.getText());
        modDesarrolloAxiliar.add(lblTable.getText());
        modDesarrolloAxiliar.add(btnBuscarUsuario.getText());
        modDesarrolloAxiliar.add(cmbFiltro.getPromptText());
        modDesarrolloAxiliar.add(fechaRegistro.getPromptText());
        modDesarrollo.addAll(Arrays.asList(titulo, lblTable, btnBuscarUsuario, cmbFiltro, fechaRegistro));
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
            if (modDesarrollo.get(i) instanceof JFXDatePicker) {
                dato = modDesarrolloAxiliar.get(i);
                ((JFXDatePicker) modDesarrollo.get(i)).setPromptText(dato);
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
            if (node instanceof JFXDatePicker) {
                dato = ((JFXDatePicker) node).getId();
                ((JFXDatePicker) node).setPromptText(dato);
            }
            if (node instanceof Label) {
                if (node == lblTable) {
                    dato = tableRegistroAcciones.getId();
                    ((Label) node).setText(dato);
                } else {
                    dato = ((Label) node).getId();
                    ((Label) node).setText(dato);
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
