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
import org.una.aeropuertocliente.dtos.AreasTrabajosDTO;
import org.una.aeropuertocliente.dtos.ZonasDTO;
import org.una.aeropuertocliente.entitiesServices.AreasTrabajosService;
import org.una.aeropuertocliente.utils.AppContext;
import org.una.aeropuertocliente.utils.FlowController;
import org.una.aeropuertocliente.utils.Mensaje;

/**
 * FXML Controller class
 *
 * @author cfugu
 */
public class AreasTrabajoController extends Controller implements Initializable {

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
    private Label lblFiltrar;
    @FXML
    private JFXButton btnFiltrar;
    @FXML
    private Label lblTable;
    @FXML
    private Label lblRegistrar;
    @FXML
    private JFXButton btnRegistrar;
    @FXML
    private Label lblTitulo;
    @FXML
    private TableView<AreasTrabajosDTO> tableAreas;
    public AreasTrabajosService areaService;
    public List<AreasTrabajosDTO> areasList = new ArrayList<AreasTrabajosDTO>();
    public AreasTrabajosDTO areasFilt;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        actionZonasClick();
        llenarAreas();
        cmbFiltro.setItems(FXCollections.observableArrayList("Id", "Nombre"));
    }

    @FXML
    private void onActionFiltrar(ActionEvent event) {
        if (txtBusqueda.getText() == null || txtBusqueda.getText().isEmpty()) {
            tableAreas.getItems().clear();
            areasList = areaService.allAreasTrabajos();
            tableAreas.setItems(FXCollections.observableArrayList(areasList));
        }
        if (cmbFiltro.getValue().equals("Id") && !txtBusqueda.getText().isEmpty()) {
            System.out.println("Entro Areas");
            tableAreas.getItems().clear();
            areasFilt = areaService.idAreaTrabajo(Long.valueOf(txtBusqueda.getText()));
            tableAreas.setItems(FXCollections.observableArrayList(areasFilt));
        }
        if (cmbFiltro.getValue().equals("Nombre") && !txtBusqueda.getText().isEmpty()) {
            tableAreas.getItems().clear();
            areasList = areaService.nombreAreasTrabajos(txtBusqueda.getText().toUpperCase());
            tableAreas.setItems(FXCollections.observableArrayList(areasList));
        }
    }

    @FXML
    private void onActionRegistrar(ActionEvent event) {
        FlowController.getInstance().goView("mantenimientoAreasTrabajo/MantenimientoAreasTrabajo");
    }

    private void llenarAreas() {
        TableColumn<AreasTrabajosDTO, String> colId = new TableColumn("Id");
        colId.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getId().toString()));
        TableColumn<AreasTrabajosDTO, String> colNombre = new TableColumn("Nombre");
        colNombre.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getNombreAreaTrabajo()));
        TableColumn<AreasTrabajosDTO, String> colDescrpcion = new TableColumn("Descripción");
        colDescrpcion.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getDescripcion()));

        tableAreas.getColumns().addAll(colId, colNombre, colDescrpcion);

        try {
            areasList = areaService.allAreasTrabajos();
            if (areasList != null && !areasList.isEmpty()) {
                tableAreas.setItems(FXCollections.observableArrayList(areasList));
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error de tramite", null, "La lista está nula o vacía");
            }
        } catch (Exception e) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de tramite", null, "Hubo un error al obtener los datos a cargar");
        }
    }

    private void actionZonasClick() {
        tableAreas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2 && tableAreas.selectionModelProperty().get().getSelectedItem() != null) {
                    AreasTrabajosDTO area = (AreasTrabajosDTO) tableAreas.selectionModelProperty().get().getSelectedItem();
                    AppContext.getInstance().set("area", area);
                    System.out.println(area.getNombreAreaTrabajo());
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
