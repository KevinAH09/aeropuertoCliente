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
import java.util.Arrays;
import java.util.Date;
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
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.una.aeropuertocliente.dtos.AerolineasDTO;
import org.una.aeropuertocliente.dtos.RegistrosAccionesDTO;
import org.una.aeropuertocliente.dtos.ZonasDTO;
import org.una.aeropuertocliente.entitiesServices.AerolineasService;
import org.una.aeropuertocliente.entitiesServices.RegistrosAccionesService;
import org.una.aeropuertocliente.entitiesServices.ReportesService;
import org.una.aeropuertocliente.entitiesServices.ZonasService;
import org.una.aeropuertocliente.sharedService.Token;
import org.una.aeropuertocliente.utils.AppContext;
import org.una.aeropuertocliente.utils.Mensaje;

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
    public List<Node> modDesarrollo = new ArrayList<>();
    public List<String> modDesarrolloAxiliar = new ArrayList<>();
    @FXML
    private Label titulo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        desabilitarComponentesVista();
        validarFechaFin();
        validarFechaIni();
        lisAerolineas = new ArrayList<>();
        lisZonas = new ArrayList<>();
        lisAerolineas = AerolineasService.allAerolineas();
        lisZonas = ZonasService.allZonas();
        llenarComboBox();
        llenarListaNodos();
        desarrollo();
        if (Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_ADMIN")) {
            btnGenerar.setDisable(true);
        }
    }

    private void llenarComboBox() {
        cbAerolineas.setItems(FXCollections.observableArrayList(lisAerolineas));
        cbZonas.setItems(FXCollections.observableArrayList(lisZonas));
        cbFiltro.setItems(FXCollections.observableArrayList("Rango de fechas", "Rango de fechas y aerolinea", "Rango de fechas y zona", "Rango de fechas, aerolinea y zona"));
    }

    private void desabilitarComponentesVista() {
        fechaFin.setVisible(false);
        fechaIni.setVisible(false);
        cbAerolineas.setVisible(false);
        cbZonas.setVisible(false);
        fechaFin.setValue(null);
        fechaIni.setValue(null);
        cbAerolineas.setValue(null);
        cbZonas.setValue(null);
        cbFiltro.setValue("Filtrar reporte por:");
    }

    private void validarFechaIni() {
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
    }

    private void validarFechaFin() {
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
    }

    @FXML
    private void actionFiltroSelect(ActionEvent event) {
        if (cbFiltro.getValue().equals("Rango de fechas")) {
            fechaFin.setVisible(true);
            fechaIni.setVisible(true);
            cbAerolineas.setVisible(false);
            cbZonas.setVisible(false);
        } else if (cbFiltro.getValue().equals("Rango de fechas y zona")) {
            fechaFin.setVisible(true);
            fechaIni.setVisible(true);
            cbAerolineas.setVisible(false);
            cbZonas.setVisible(true);
        } else if (cbFiltro.getValue().equals("Rango de fechas y aerolinea")) {
            fechaFin.setVisible(true);
            fechaIni.setVisible(true);
            cbAerolineas.setVisible(true);
            cbZonas.setVisible(false);
        } else if (cbFiltro.getValue().equals("Rango de fechas, aerolinea y zona")) {
            fechaFin.setVisible(true);
            fechaIni.setVisible(true);
            cbAerolineas.setVisible(true);
            cbZonas.setVisible(true);
        }
    }

    @FXML
    private void actionLimpiar(ActionEvent event) {
        desabilitarComponentesVista();
    }

    @FXML
    private void actionGenerar(ActionEvent event) {
        if (cbFiltro.getValue().equals("Rango de fechas")) {
            crearReporteFechas();
        } else if (cbFiltro.getValue().equals("Rango de fechas y zona")) {
            crearReporteFechasZona();
        } else if (cbFiltro.getValue().equals("Rango de fechas y aerolinea")) {
            crearReporteFechasAerolinea();
        } else if (cbFiltro.getValue().equals("Rango de fechas, aerolinea y zona")) {
            crearReporteFechasZonaAerolinea();
        }
    }

    private void crearReporteFechasZonaAerolinea() {
        if (fechaFin.getValue() != null && fechaIni.getValue() != null && cbAerolineas.getValue() != null && cbZonas.getValue() != null) {
            ReportesService.reporteAvionesFechasAerolineaZona(fechaIni.getValue().toString(), fechaFin.getValue().toString(), cbAerolineas.getValue().getId().toString(), cbZonas.getValue().getId().toString());
            RegistrosAccionesService.createRegistroAccion(new RegistrosAccionesDTO(Token.getInstance().getUsuario(), "Creo nuevo reporte de recorrido aviones", new Date()));
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Generar reporte", ((Stage) btnLimpiar.getScene().getWindow()), "Campos vacios, por favor completarlos.");
        }
    }

    private void crearReporteFechasAerolinea() {
        if (fechaFin.getValue() != null && fechaIni.getValue() != null && cbAerolineas.getValue() != null) {
            ReportesService.reporteAvionesFechasAerolinea(fechaIni.getValue().toString(), fechaFin.getValue().toString(), cbAerolineas.getValue().getId().toString());
            RegistrosAccionesService.createRegistroAccion(new RegistrosAccionesDTO(Token.getInstance().getUsuario(), "Creo nuevo reporte de recorrido aviones", new Date()));
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Generar reporte", ((Stage) btnLimpiar.getScene().getWindow()), "Campos vacios, por favor completarlos.");
        }
    }

    private void crearReporteFechasZona() {
        if (fechaFin.getValue() != null && fechaIni.getValue() != null && cbZonas.getValue() != null) {
            ReportesService.reporteAvionesFechasZona(fechaIni.getValue().toString(), fechaFin.getValue().toString(), cbZonas.getValue().getId().toString());
            RegistrosAccionesService.createRegistroAccion(new RegistrosAccionesDTO(Token.getInstance().getUsuario(), "Creo nuevo reporte de recorrido aviones", new Date()));
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Generar reporte", ((Stage) btnLimpiar.getScene().getWindow()), "Campos vacios, por favor completarlos.");
        }
    }

    private void crearReporteFechas() {
        if (fechaFin.getValue() != null && fechaIni.getValue() != null) {
            ReportesService.reporteAvionesFechas(fechaIni.getValue().toString(), fechaFin.getValue().toString());
            RegistrosAccionesService.createRegistroAccion(new RegistrosAccionesDTO(Token.getInstance().getUsuario(), "Creo nuevo reporte de recorrido aviones", new Date()));
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Generar reporte", ((Stage) btnLimpiar.getScene().getWindow()), "Campos vacios, por favor completarlos.");
        }
    }

    @Override
    public void initialize() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void llenarListaNodos() {
        modDesarrollo.clear();
        modDesarrolloAxiliar.clear();
        modDesarrolloAxiliar.add(titulo.getText());
        modDesarrolloAxiliar.add(btnGenerar.getText());
        modDesarrolloAxiliar.add(btnLimpiar.getText());
        modDesarrolloAxiliar.add(cbFiltro.getPromptText());
        modDesarrolloAxiliar.add(cbAerolineas.getPromptText());
        modDesarrolloAxiliar.add(cbZonas.getPromptText());
        modDesarrolloAxiliar.add(fechaIni.getPromptText());
        modDesarrolloAxiliar.add(fechaFin.getPromptText());
        modDesarrollo.addAll(Arrays.asList(titulo, btnGenerar, btnLimpiar, cbFiltro, cbAerolineas, cbZonas, fechaIni, fechaFin));
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
                dato = ((Label) node).getId();
                ((Label) node).setText(dato);
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
