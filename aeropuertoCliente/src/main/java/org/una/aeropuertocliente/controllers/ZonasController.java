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
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.una.aeropuertocliente.dtos.ZonasDTO;
import org.una.aeropuertocliente.entitiesServices.ZonasService;
import org.una.aeropuertocliente.utils.AppContext;
import org.una.aeropuertocliente.utils.FlowController;
import org.una.aeropuertocliente.utils.Mensaje;

/**
 * FXML Controller class
 *
 * @author cfugu
 */
public class ZonasController extends Controller implements Initializable {

    @FXML
    private Label lblZonas;
    @FXML
    private Label titulo;
    @FXML
    private Label lbltxtBusqueda;
    @FXML
    private JFXTextField txtBusqueda;
    @FXML
    private Label lblCmbFiltro;
    @FXML
    private JFXComboBox<String> cmbFiltro;
    @FXML
    private Label lblTable;
    @FXML
    private TableView<ZonasDTO> tableZonas;
    @FXML
    private Label lblRegistrar;
    @FXML
    private JFXButton btnRegistrar;
    @FXML
    private Label lblFiltrar;
    @FXML
    private JFXButton btnFiltrar;
    ZonasDTO zonas;
    ZonasDTO zonasFilt;
    public List<ZonasDTO> zonasList = new ArrayList<ZonasDTO>();
    ZonasService zonSer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        actionZonasClick();
        llenarZonas();
        cmbFiltro.setItems(FXCollections.observableArrayList("Id", "Estado", "Nombre", "Código"));
    }

    @FXML
    private void onActionRegistrar(ActionEvent event) {
        FlowController.getInstance().goView("mantenimientoZonas/MantenimientoZonas");
    }

    @FXML
    private void onActionFiltrar(ActionEvent event) {
        if (txtBusqueda.getText() == null || txtBusqueda.getText().isEmpty()) {
            tableZonas.getItems().clear();
            zonasList = ZonasService.allZonas();
            tableZonas.setItems(FXCollections.observableArrayList(zonasList));
        }
        if (cmbFiltro.getValue().equals("Id") && !txtBusqueda.getText().isEmpty()) {
            System.out.println("Entro Zonas");
            tableZonas.getItems().clear();
            zonasFilt = ZonasService.idZona(Long.valueOf(txtBusqueda.getText()));
            tableZonas.setItems(FXCollections.observableArrayList(zonasFilt));
        }
        if (cmbFiltro.getValue().equals("Estado")&& !txtBusqueda.getText().isEmpty()) {
            if (txtBusqueda.getText().equals("true")) {
                tableZonas.getItems().clear();
                zonasList = ZonasService.estadoZona(true);
                tableZonas.setItems(FXCollections.observableArrayList(zonasList));
            }
            if (txtBusqueda.getText().equals("false")) {
                tableZonas.getItems().clear();
                zonasList = ZonasService.estadoZona(false);
                tableZonas.setItems(FXCollections.observableArrayList(zonasList));
            }
        }
        if (cmbFiltro.getValue().equals("Nombre")&& !txtBusqueda.getText().isEmpty()) {
            tableZonas.getItems().clear();
            zonasList = ZonasService.nombreZona(txtBusqueda.getText());
            tableZonas.setItems(FXCollections.observableArrayList(zonasList));
        }
        if (cmbFiltro.getValue().equals("Código")&& !txtBusqueda.getText().isEmpty()) {
            tableZonas.getItems().clear();
            zonasList = ZonasService.codigoZona(txtBusqueda.getText());
            System.out.println(zonasList.size());
            tableZonas.setItems(FXCollections.observableArrayList(zonasList));
        }
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

        try {
            zonasList = ZonasService.allZonas();
            if (zonasList != null && !zonasList.isEmpty()) {
                tableZonas.setItems(FXCollections.observableArrayList(zonasList));
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error de tramite", null, "La lista está nula o vacía");
            }
        } catch (Exception e) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de tramite", null, "Hubo un error al obtener los datos a cargar");
        }
    }

    private void actionZonasClick() {
        tableZonas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2 && tableZonas.selectionModelProperty().get().getSelectedItem() != null) {
                    ZonasDTO zona = (ZonasDTO) tableZonas.selectionModelProperty().get().getSelectedItem();
                    AppContext.getInstance().set("zon", zona);
                    System.out.println(zona.getNombreZona());
//                    ((Stage) btnFiltrar.getScene().getWindow()).close();
                }

            }
        });
    }

    @Override
    public void initialize() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
