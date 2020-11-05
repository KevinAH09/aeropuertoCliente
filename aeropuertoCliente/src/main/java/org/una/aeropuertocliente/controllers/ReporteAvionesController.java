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
import org.una.aeropuertocliente.dtos.AerolineasDTO;
import org.una.aeropuertocliente.dtos.ZonasDTO;
import org.una.aeropuertocliente.entitiesServices.AerolineasService;
import org.una.aeropuertocliente.entitiesServices.ReportesService;
import org.una.aeropuertocliente.entitiesServices.ZonasService;

/**
 * FXML Controller class
 *
 * @author colo7
 */
public class ReporteAvionesController extends Controller implements Initializable {

    @FXML
    private JFXComboBox<String> cbFiltro;
    @FXML
    private JFXDatePicker fechaIni;
    @FXML
    private JFXDatePicker fechaFin;
    @FXML
    private JFXComboBox<AerolineasDTO> cbAerolineas;
    @FXML
    private JFXComboBox<ZonasDTO> cbZonas;
    @FXML
    private JFXButton btnLimpiar;
    @FXML
    private JFXButton btnGenerar;
    
    private List<AerolineasDTO> lisAerolineas = new ArrayList<>();
    private List<ZonasDTO> lisZonas = new ArrayList<>();
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
        lisAerolineas = new ArrayList<>();
        lisZonas = new ArrayList<>();
        lisAerolineas = AerolineasService.allAerolineas();
        lisZonas = ZonasService.allZonas();
        
        cbAerolineas.setItems(FXCollections.observableArrayList(lisAerolineas));
        cbZonas.setItems(FXCollections.observableArrayList(lisZonas));
        cbFiltro.setItems(FXCollections.observableArrayList("Rango de fechas","Rango de fechas y aerolinea","Rango de fechas y zona","Rango de fechas, aerolinea y zona"));
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
            ReportesService.reporteAvionesFechas(fechaIni.getValue().toString(), fechaFin.getValue().toString());
        }else if(cbFiltro.getValue().equals("Rango de fechas y zona")){
             ReportesService.reporteAvionesFechasZona(fechaIni.getValue().toString(), fechaFin.getValue().toString(),cbZonas.getValue().getId().toString());
        } else if(cbFiltro.getValue().equals("Rango de fechas y aerolinea")){
             ReportesService.reporteAvionesFechasAerolinea(fechaIni.getValue().toString(), fechaFin.getValue().toString(),cbAerolineas.getValue().getId().toString());
        } else if(cbFiltro.getValue().equals("Rango de fechas y aerolinea")){
            
        }
    }

    @Override
    public void initialize() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
