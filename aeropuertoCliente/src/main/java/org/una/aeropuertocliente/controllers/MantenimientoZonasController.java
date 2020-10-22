/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.una.aeropuertocliente.dtos.ZonasDTO;
import org.una.aeropuertocliente.entitiesServices.ZonasService;
import org.una.aeropuertocliente.sharedService.Token;
import org.una.aeropuertocliente.utils.Mensaje;

/**
 * FXML Controller class
 *
 * @author cfugu
 */
public class MantenimientoZonasController extends Controller implements Initializable {

    @FXML
    private Label lblZonas;
    @FXML
    private Label titulo;
    @FXML
    private Label lbltxtNombre;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private Label lbltxtCodigo;
    @FXML
    private JFXTextField txtCodigo;
    @FXML
    private Label lblcmbEstado;
    @FXML
    private JFXComboBox<String> cmbEstado;
    @FXML
    private Label lbltxtDescripcion;
    @FXML
    private JFXTextField txtDescripcion;
    @FXML
    private Label lblCancelar;
    @FXML
    private JFXButton btnRegistrar;
    @FXML
    private Label lblGuardar;
    @FXML
    private Label lbltxtBusqueda;
    @FXML
    private JFXTextField txtBusqueda;
    @FXML
    private Label lblCmbFiltro;
    @FXML
    private JFXComboBox<String> cmbFiltro;
    @FXML
    private Label lblFiltrar;
    @FXML
    private JFXButton btnFiltrar;
    @FXML
    private Label lblTable;
    @FXML
    private TableView<ZonasDTO> tableZonas;
    ZonasDTO zonas;
    ZonasDTO zonasFilt;
    public List<ZonasDTO> zonasList = new ArrayList<ZonasDTO>();
    String mensaje;
    @FXML
    private JFXButton btnCancelar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (!Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_GESTOR")) {
            btnRegistrar.setVisible(false);
            btnRegistrar.setDisable(true);
            btnCancelar.setVisible(false);
            btnCancelar.setDisable(true);
        } else {
            btnRegistrar.setVisible(true);
            btnRegistrar.setDisable(false);
            btnCancelar.setVisible(true);
            btnCancelar.setDisable(false);
        }
        actionZonasClick();
        llenarZonas();
        cmbFiltro.setItems(FXCollections.observableArrayList("Id", "Estado", "Nombre", "Código"));
        cmbEstado.setItems(FXCollections.observableArrayList("Activo", "Inactivo"));
    }

    @FXML
    private void onActionCancelar(ActionEvent event) {
        txtNombre.clear();
        txtCodigo.clear();
        txtDescripcion.clear();
        zonas = null;
    }

    @FXML
    private void onActionGuardar(ActionEvent event) {
        if (zonas == null) {
            if (!txtNombre.getText().isEmpty() && !cmbEstado.getValue().isEmpty() && !txtCodigo.getText().isEmpty() && !txtDescripcion.getText().isEmpty()) {
                zonas = new ZonasDTO();
                if (cmbEstado.getValue().equals("Activo")) {
                    zonas.setEstado(true);
                } else {
                    zonas.setEstado(false);
                }
                zonas.setDescripcion(txtDescripcion.getText());
                zonas.setCodigo(txtCodigo.getText());
                zonas.setNombreZona(txtNombre.getText());
                if (ZonasService.createZona(zonas) == 201) {
                    new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Guardar Zona", ((Stage) txtNombre.getScene().getWindow()), "Se guardó correctamente");
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error al guardar la Zona", ((Stage) txtNombre.getScene().getWindow()), "No se guardó correctamente");
                }
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error al crear Zona", ((Stage) txtNombre.getScene().getWindow()), "Rellene los campos necesarios");
            }

        } else {
            if (!txtNombre.getText().isEmpty() && !cmbEstado.getValue().isEmpty() && !txtCodigo.getText().isEmpty() && !txtDescripcion.getText().isEmpty()) {
                if (cmbEstado.getValue().equals("Activo")) {
                    zonas.setEstado(true);
                } else {
                    zonas.setEstado(false);
                }
                zonas.setDescripcion(txtDescripcion.getText());
                zonas.setCodigo(txtCodigo.getText());
                zonas.setNombreZona(txtNombre.getText());
                if (ZonasService.updateZona(zonas) == 200) {
                    new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Guardar Zona", ((Stage) txtNombre.getScene().getWindow()), "Se guardó correctamente");
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error al guardar la Zona", ((Stage) txtNombre.getScene().getWindow()), "No se guardó correctamente");
                }
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error al guardar la Zona", ((Stage) txtNombre.getScene().getWindow()), "Rellene los campos necesarios");
            }
        }
        tableZonas.getItems().clear();
        zonasList = ZonasService.allZonas();
        tableZonas.setItems(FXCollections.observableArrayList(zonasList));
    }

    @FXML
    private void onActionFiltrar(ActionEvent event) {
        if (txtBusqueda.getText() == null || txtBusqueda.getText().isEmpty()|| cmbFiltro.getValue().isEmpty()) {
            tableZonas.getItems().clear();
            mensaje="Por favor debe ingresar un datos en el campo de búsqueda";
        }
        if (cmbFiltro.getValue().equals("Id") && !txtBusqueda.getText().isEmpty()) {
            tableZonas.getItems().clear();
            zonasFilt = ZonasService.idZona(Long.valueOf(txtBusqueda.getText()));
            if (zonasFilt != null) {
                tableZonas.setItems(FXCollections.observableArrayList(zonasFilt));
            } else {
                mensaje="No se encontró coincidencias";
                notificar(0);
            }
        }
        if (cmbFiltro.getValue().equals("Estado") && !txtBusqueda.getText().isEmpty()) {
            if (txtBusqueda.getText().toLowerCase().equals("true")) {
                tableZonas.getItems().clear();
                zonasList = ZonasService.estadoZona(true);
                if (zonasList != null) {
                    tableZonas.setItems(FXCollections.observableArrayList(zonasList));
                } else {
                    mensaje="No se encontró coincidencias";
                    notificar(0);
                }
            }
            if (txtBusqueda.getText().equals("false")) {
                tableZonas.getItems().clear();
                zonasList = ZonasService.estadoZona(false);
                if (zonasList != null) {
                    tableZonas.setItems(FXCollections.observableArrayList(zonasList));
                } else {
                    mensaje="No se encontró coincidencias";
                    notificar(0);
                }
            }
            if (zonasList != null) {
                mensaje="No se encontró coincidencias";
                notificar(0);
            }
        }
        if (cmbFiltro.getValue().equals("Nombre") && !txtBusqueda.getText().isEmpty()) {
            tableZonas.getItems().clear();
            zonasList = ZonasService.nombreZona(txtBusqueda.getText());
            if (zonasList != null) {
                tableZonas.setItems(FXCollections.observableArrayList(zonasList));
            } else {
                notificar(0);
            }
        }
        if (cmbFiltro.getValue().equals("Código") && !txtBusqueda.getText().isEmpty()) {
            tableZonas.getItems().clear();
            zonasList = ZonasService.codigoZona(txtBusqueda.getText());
            if (zonasList != null) {
                tableZonas.setItems(FXCollections.observableArrayList(zonasList));
            } else {
                notificar(0);
            }
        }
    }

    private void actionZonasClick() {
        zonas = null;
        tableZonas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2 && tableZonas.selectionModelProperty().get().getSelectedItem() != null) {
                    zonas = (ZonasDTO) tableZonas.selectionModelProperty().get().getSelectedItem();
                    System.out.println(zonas.getNombreZona());
                    editar();
                }

            }
        });
    }

    private void llenarZonas() {
        TableColumn<ZonasDTO, String> colId = new TableColumn("Id");
        colId.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getId().toString()));
        TableColumn<ZonasDTO, String> colNombre = new TableColumn("Nombre");
        colNombre.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getNombreZona()));
        TableColumn<ZonasDTO, String> colEstado = new TableColumn("Estado");
        colEstado.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().isEstado()));
        TableColumn<ZonasDTO, String> colCodigo = new TableColumn("Código");
        colCodigo.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getCodigo()));
        TableColumn<ZonasDTO, String> colDescripcion = new TableColumn("Descripcion");
        colDescripcion.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getDescripcion()));
        tableZonas.getColumns().addAll(colId, colNombre, colEstado, colCodigo, colDescripcion);
        notificar(1);
//        try {
//            zonasList = ZonasService.allZonas();
//            if (zonasList != null && !zonasList.isEmpty()) {
//                tableZonas.setItems(FXCollections.observableArrayList(zonasList));
//            } else {
//                new Mensaje().showModal(Alert.AlertType.ERROR, "Error de tramite", null, "La lista está nula o vacía");
//            }
//        } catch (Exception e) {
//            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de tramite", null, "Hubo un error al obtener los datos a cargar");
//        }
    }

    private void editar() {
        if (zonas != null) {
            txtNombre.setText(zonas.getNombreZona());
            txtCodigo.setText(zonas.getCodigo());
            txtDescripcion.setText(zonas.getDescripcion());
            if (zonas.isEstado()) {
                cmbEstado.setValue("Activo");
            } else {
                cmbEstado.setValue("Desactivo");
            }

        }

    }

    private void notificar(int num) {
        tableZonas.getItems().clear();
        if (num == 1) {
            ImageView imageView = new ImageView(new Image("org/una/aeropuertocliente/views/shared/info.png"));
            Text lab = new Text("Para mostrar datos en este apartado debe realizar el filtro correspondiente");
            lab.setFill(Color.web("#0076a3"));
            VBox box = new VBox();
            box.setAlignment(Pos.CENTER);
            box.getChildren().add(imageView);
            box.getChildren().add(lab);
            tableZonas.setPlaceholder(box);
        } else {
            ImageView imageView2 = new ImageView(new Image("org/una/aeropuertocliente/views/shared/warning.png"));
            Text lab = new Text(mensaje);
            lab.setFill(Color.web("#0076a3"));
            VBox box = new VBox();
            box.setAlignment(Pos.CENTER);
            box.getChildren().add(imageView2);
            box.getChildren().add(lab);
            tableZonas.setPlaceholder(box);
        }
    }

    @Override
    public void initialize() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
