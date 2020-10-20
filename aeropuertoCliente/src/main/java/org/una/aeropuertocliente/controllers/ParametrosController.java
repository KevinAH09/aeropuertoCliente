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
import org.una.aeropuertocliente.dtos.ParametrosDTO;
import org.una.aeropuertocliente.dtos.ZonasDTO;
import org.una.aeropuertocliente.entitiesServices.ParametrosService;
import org.una.aeropuertocliente.entitiesServices.ZonasService;
import org.una.aeropuertocliente.utils.Mensaje;

/**
 * FXML Controller class
 *
 * @author cfugu
 */
public class ParametrosController extends Controller implements Initializable {

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
    private JFXTextField txtValor;
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
    private TableView<ParametrosDTO> tableParametros;
    ParametrosDTO parametros;
    ParametrosDTO parametrosFilt;
    public List<ParametrosDTO> parametrosList = new ArrayList<ParametrosDTO>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbEstado.setItems(FXCollections.observableArrayList("Activo", "Inactivo"));
        cmbFiltro.setItems(FXCollections.observableArrayList("Id", "Estado", "Nombre"));
        actionParametrosClick();
        llenarParametros();
    }

    @FXML
    private void onActionCancelar(ActionEvent event) {
        txtNombre.clear();
        txtValor.clear();
        txtDescripcion.clear();
        parametros = null;
    }

    @FXML
    private void onActionGuardar(ActionEvent event) {
        if (parametros == null) {
            if (!txtNombre.getText().isEmpty() && !cmbEstado.getValue().isEmpty() && !txtValor.getText().isEmpty() && !txtDescripcion.getText().isEmpty()) {
                parametros = new ParametrosDTO();
                if (cmbEstado.getValue().equals("Activo")) {
                    parametros.setEstado(true);
                } else {
                    parametros.setEstado(false);
                }
                parametros.setDescripcion(txtDescripcion.getText());
                parametros.setNombreParametro(txtNombre.getText());
                parametros.setValor(txtValor.getText());
                if (ParametrosService.createParametro(parametros) == 201) {
                    new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Guardar parametro", ((Stage) txtNombre.getScene().getWindow()), "Se guardó correctamente");
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error al guardar el parametro", ((Stage) txtNombre.getScene().getWindow()), "No se guardó correctamente");
                }
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error al crear el parametro", ((Stage) txtNombre.getScene().getWindow()), "Rellene los campos necesarios");
            }

        } else {
            if (!txtNombre.getText().isEmpty() && !cmbEstado.getValue().isEmpty() && !txtValor.getText().isEmpty() && !txtDescripcion.getText().isEmpty()) {
                if (cmbEstado.getValue().equals("Activo")) {
                    parametros.setEstado(true);
                } else {
                    parametros.setEstado(false);
                }
                parametros.setDescripcion(txtDescripcion.getText());
                parametros.setNombreParametro(txtNombre.getText());
                parametros.setValor(txtValor.getText());
                if (ParametrosService.updateParametro(parametros) == 200) {
                    new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Guardar parametro", ((Stage) txtNombre.getScene().getWindow()), "Se guardó correctamente");
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error al guardar el parametro", ((Stage) txtNombre.getScene().getWindow()), "No se guardó correctamente");
                }
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error al crear el parametro", ((Stage) txtNombre.getScene().getWindow()), "Rellene los campos necesarios");
            }
        }
        tableParametros.getItems().clear();
        parametrosList = ParametrosService.allParametros();
        tableParametros.setItems(FXCollections.observableArrayList(parametrosList));
    }

    @FXML
    private void onActionFiltrar(ActionEvent event) {
        if (txtBusqueda.getText() == null || txtBusqueda.getText().isEmpty()) {
            tableParametros.getItems().clear();
            parametrosList = ParametrosService.allParametros();
            tableParametros.setItems(FXCollections.observableArrayList(parametrosList));
        }
        if (cmbFiltro.getValue().equals("Id") && !txtBusqueda.getText().isEmpty()) {
            tableParametros.getItems().clear();
            parametrosFilt = ParametrosService.idParametro(Long.valueOf(txtBusqueda.getText()));
            tableParametros.setItems(FXCollections.observableArrayList(parametrosFilt));
        }
        if (cmbFiltro.getValue().equals("Estado") && !txtBusqueda.getText().isEmpty()) {
            if (txtBusqueda.getText().equals("true")) {
                tableParametros.getItems().clear();
                parametrosList = ParametrosService.estadoParametros(true);
                tableParametros.setItems(FXCollections.observableArrayList(parametrosList));
            }
            if (txtBusqueda.getText().equals("false")) {
                tableParametros.getItems().clear();
                parametrosList = ParametrosService.estadoParametros(false);
                tableParametros.setItems(FXCollections.observableArrayList(parametrosList));
            }
        }
        if (cmbFiltro.getValue().equals("Nombre") && !txtBusqueda.getText().isEmpty()) {
            tableParametros.getItems().clear();
            parametrosList = ParametrosService.nombreParametros(txtBusqueda.getText());
            tableParametros.setItems(FXCollections.observableArrayList(parametrosList));
        }
    }

    private void actionParametrosClick() {
        parametros = null;
        tableParametros.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2 && tableParametros.selectionModelProperty().get().getSelectedItem() != null) {
                    parametros = (ParametrosDTO) tableParametros.selectionModelProperty().get().getSelectedItem();
                    System.out.println(parametros.getNombreParametro());
                    editar();
                }

            }
        });
    }

    private void llenarParametros() {
        TableColumn<ParametrosDTO, String> colId = new TableColumn("Id");
        colId.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getId().toString()));
        TableColumn<ParametrosDTO, String> colNombre = new TableColumn("Nombre");
        colNombre.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getNombreParametro()));
        TableColumn<ParametrosDTO, String> colEstado = new TableColumn("Estado");
        colEstado.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().isEstado()));
        TableColumn<ParametrosDTO, String> colCodigo = new TableColumn("Valor Parametro");
        colCodigo.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getValor()));
        TableColumn<ParametrosDTO, String> colDescripcion = new TableColumn("Descripcion");
        colDescripcion.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getDescripcion()));
        TableColumn<ParametrosDTO, String> colFecha = new TableColumn("Fecha registro");
        colFecha.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getFechaRegistro()));
        tableParametros.getColumns().addAll(colId, colNombre, colEstado, colCodigo, colDescripcion, colFecha);

        try {
            parametrosList = ParametrosService.allParametros();
            if (parametrosList != null && !parametrosList.isEmpty()) {
                tableParametros.setItems(FXCollections.observableArrayList(parametrosList));
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error de parametros", null, "La lista está nula o vacía");
            }
        } catch (Exception e) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de parametros", null, "Hubo un error al obtener los datos a cargar");
        }
    }

    private void editar() {
        if (parametros != null) {
            txtNombre.setText(parametros.getNombreParametro());
            txtValor.setText(parametros.getValor());
            txtDescripcion.setText(parametros.getDescripcion());
            if (parametros.isEstado()) {
                cmbEstado.setValue("Activo");
            } else {
                cmbEstado.setValue("Desactivo");
            }

        }
    }

    @Override
    public void initialize() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
