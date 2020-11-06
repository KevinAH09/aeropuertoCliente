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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.una.aeropuertocliente.dtos.AreasTrabajosDTO;
import org.una.aeropuertocliente.entitiesServices.AreasTrabajosService;
import org.una.aeropuertocliente.entitiesServices.ReportesService;
import org.una.aeropuertocliente.utils.Mensaje;

/**
 * FXML Controller class
 *
 * @author colo7
 */
public class ReporteGastoMantController extends Controller implements Initializable {

    @FXML
    private JFXComboBox<String> cbFiltro;
    @FXML
    private JFXDatePicker fechaIni;
    @FXML
    private JFXDatePicker fechaFin;
    @FXML
    private JFXComboBox<String> cbEstadoPago;
    @FXML
    private JFXComboBox<AreasTrabajosDTO> cbAreaTrabajo;
    @FXML
    private JFXTextField txtEmpresa;
    @FXML
    private JFXButton btnLimpiar;
    @FXML
    private JFXButton btnGenerar;

    private List<AreasTrabajosDTO> lisArea = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fechaFin.setVisible(false);
        fechaIni.setVisible(false);
        cbAreaTrabajo.setVisible(false);
        txtEmpresa.setVisible(false);
        cbEstadoPago.setVisible(false);
        Callback<DatePicker, DateCell> dayFinCellFactory = dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (fechaIni.getValue() != null) {
                    if (item.isBefore(fechaIni.getValue())) {
                        setStyle("-fx-background-color: #ffc0cb;");
                        Platform.runLater(() -> setDisable(true));
                    }
                }
            }
        };
        fechaFin.setDayCellFactory(dayFinCellFactory);
        Callback<DatePicker, DateCell> dayIniCellFactory = dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (fechaFin.getValue() != null) {
                    if (item.isAfter(fechaFin.getValue())) {
                        setStyle("-fx-background-color: #ffc0cb;");
                        Platform.runLater(() -> setDisable(true));
                    }
                }
            }
        };
        fechaIni.setDayCellFactory(dayIniCellFactory);
        lisArea = new ArrayList<>();
        lisArea = AreasTrabajosService.allAreasTrabajos();

        cbAreaTrabajo.setItems(FXCollections.observableArrayList(lisArea));
        cbEstadoPago.setItems(FXCollections.observableArrayList("Anulado", "Cancelado", "Pendiente"));
        cbFiltro.setItems(FXCollections.observableArrayList("Rango de fechas", "Rango de fechas y area de trabajo", "Rango de fechas y estado de pago", "Rango de fechas y empresa"));
    }

    @FXML
    private void actionFiltroSelect(ActionEvent event) {
        if (cbFiltro.getValue().equals("Rango de fechas")) {
            fechaFin.setVisible(true);
            fechaIni.setVisible(true);
            cbAreaTrabajo.setVisible(false);
            txtEmpresa.setVisible(false);
            cbEstadoPago.setVisible(false);
        } else if (cbFiltro.getValue().equals("Rango de fechas y area de trabajo")) {
            fechaFin.setVisible(true);
            fechaIni.setVisible(true);
            cbAreaTrabajo.setVisible(true);
            txtEmpresa.setVisible(false);
            cbEstadoPago.setVisible(false);
        } else if (cbFiltro.getValue().equals("Rango de fechas y estado de pago")) {
            fechaFin.setVisible(true);
            fechaIni.setVisible(true);
            cbAreaTrabajo.setVisible(false);
            txtEmpresa.setVisible(false);
            cbEstadoPago.setVisible(true);
        } else if (cbFiltro.getValue().equals("Rango de fechas y empresa")) {
            fechaFin.setVisible(true);
            fechaIni.setVisible(true);
            cbAreaTrabajo.setVisible(false);
            txtEmpresa.setVisible(true);
            cbEstadoPago.setVisible(false);
        }
    }

    @FXML
    private void actionLimpiar(ActionEvent event) {
        fechaFin.setVisible(false);
        fechaIni.setVisible(false);
        cbAreaTrabajo.setVisible(false);
        txtEmpresa.setVisible(false);
        cbEstadoPago.setVisible(false);
        fechaFin.setValue(null);
        fechaIni.setValue(null);
        cbAreaTrabajo.setValue(null);
        cbEstadoPago.setValue(null);
        txtEmpresa.setText("");
        cbFiltro.setValue("Filtrar reporte por:");
    }

    @FXML
    private void actionGenerar(ActionEvent event) {
        if (cbFiltro.getValue().equals("Rango de fechas")) {
            if (fechaFin.getValue() != null && fechaIni.getValue() != null) {
                ReportesService.reporteGastosMantFechas(fechaIni.getValue().toString(), fechaFin.getValue().toString());
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Generar reporte", ((Stage) btnLimpiar.getScene().getWindow()), "Campos vacios, por favor completarlos.");
            }
        } else if (cbFiltro.getValue().equals("Rango de fechas y area de trabajo")) {
            if (fechaFin.getValue() != null && fechaIni.getValue() != null && cbAreaTrabajo.getValue() != null) {
                ReportesService.reporteGastosMantFechasAreaTrabajo(fechaIni.getValue().toString(), fechaFin.getValue().toString(), cbAreaTrabajo.getValue().getId().toString());
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Generar reporte", ((Stage) btnLimpiar.getScene().getWindow()), "Campos vacios, por favor completarlos.");
            }
        } else if (cbFiltro.getValue().equals("Rango de fechas y estado de pago")) {
            if (fechaFin.getValue() != null && fechaIni.getValue() != null && cbEstadoPago.getValue() != null) {
                ReportesService.reporteGastosMantFechasEstadoPago(fechaIni.getValue().toString(), fechaFin.getValue().toString(), cbEstadoPago.getValue());
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Generar reporte", ((Stage) btnLimpiar.getScene().getWindow()), "Campos vacios, por favor completarlos.");
            }
        } else if (cbFiltro.getValue().equals("Rango de fechas y empresa")) {
            if (fechaFin.getValue() != null && fechaIni.getValue() != null && !txtEmpresa.getText().isEmpty()) {
                ReportesService.reporteGastosMantFechasEmpresa(fechaIni.getValue().toString(), fechaFin.getValue().toString(), txtEmpresa.getText());
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Generar reporte", ((Stage) btnLimpiar.getScene().getWindow()), "Campos vacios, por favor completarlos.");
            }
        }
    }

    @Override
    public void initialize() {

    }

}
