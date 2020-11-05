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
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;
import org.una.aeropuertocliente.dtos.AreasTrabajosDTO;
import org.una.aeropuertocliente.entitiesServices.AreasTrabajosService;
import org.una.aeropuertocliente.entitiesServices.ReportesService;

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
    }

    @FXML
    private void actionLimpiar(ActionEvent event) {
    }

    @FXML
    private void actionGenerar(ActionEvent event) {
         if(cbFiltro.getValue().equals("Rango de fechas")){
            ReportesService.reporteGastosMantFechas(fechaIni.getValue().toString(), fechaFin.getValue().toString());
        }else if(cbFiltro.getValue().equals("Rango de fechas y area de trabajo")){
             ReportesService.reporteGastosMantFechasAreaTrabajo(fechaIni.getValue().toString(), fechaFin.getValue().toString(),cbAreaTrabajo.getValue().getId().toString());
        } else if(cbFiltro.getValue().equals("Rango de fechas y estado de pago")){
             ReportesService.reporteGastosMantFechasEstadoPago(fechaIni.getValue().toString(), fechaFin.getValue().toString(),cbEstadoPago.getValue());
        } else if(cbFiltro.getValue().equals("Rango de fechas y empresa")){
            ReportesService.reporteGastosMantFechasEmpresa(fechaIni.getValue().toString(), fechaFin.getValue().toString(),txtEmpresa.getText());
        }
    }

    @Override
    public void initialize() {

    }

}
