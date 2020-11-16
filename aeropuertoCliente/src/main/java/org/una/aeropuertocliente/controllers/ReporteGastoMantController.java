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
import org.una.aeropuertocliente.dtos.AreasTrabajosDTO;
import org.una.aeropuertocliente.dtos.RegistrosAccionesDTO;
import org.una.aeropuertocliente.entitiesServices.AreasTrabajosService;
import org.una.aeropuertocliente.entitiesServices.RegistrosAccionesService;
import org.una.aeropuertocliente.entitiesServices.ReportesService;
import org.una.aeropuertocliente.sharedService.Token;
import org.una.aeropuertocliente.utils.AppContext;
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
    public List<Node> modDesarrollo = new ArrayList<>();
    public List<String> modDesarrolloAxiliar = new ArrayList<>();
    @FXML
    private Label titulo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        desabilidatarComponentesVista();
        validarFechaFin();
        validarFechaIni();
        lisArea = new ArrayList<>();
        lisArea = AreasTrabajosService.allAreasTrabajos();
        llenarComboBox();
        llenarListaNodos();
        desarrollo();
        if (Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_ADMIN")) {
            btnGenerar.setDisable(true);
        }
    }

    private void llenarComboBox() {
        cbAreaTrabajo.setItems(FXCollections.observableArrayList(lisArea));
        cbEstadoPago.setItems(FXCollections.observableArrayList("Anulado", "Cancelado", "Pendiente"));
        cbFiltro.setItems(FXCollections.observableArrayList("Rango de fechas", "Rango de fechas y area de trabajo", "Rango de fechas y estado de pago", "Rango de fechas y empresa"));
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

    private void desabilidatarComponentesVista() {
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
        desabilidatarComponentesVista();
    }

    @FXML
    private void actionGenerar(ActionEvent event) {
        if (cbFiltro.getValue().equals("Rango de fechas")) {
            crearReporteFechas();
        } else if (cbFiltro.getValue().equals("Rango de fechas y area de trabajo")) {
            crearReporteFechasAreaTrabajo();
        } else if (cbFiltro.getValue().equals("Rango de fechas y estado de pago")) {
            crearReporteFechasEstadoPago();
        } else if (cbFiltro.getValue().equals("Rango de fechas y empresa")) {
            crearReporteFechasEmpresa();
        }
    }

    private void crearReporteFechasEmpresa() {
        if (fechaFin.getValue() != null && fechaIni.getValue() != null && !txtEmpresa.getText().isEmpty()) {
            ReportesService.reporteGastosMantFechasEmpresa(fechaIni.getValue().toString(), fechaFin.getValue().toString(), txtEmpresa.getText());
        } else {
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Generar reporte", ((Stage) btnLimpiar.getScene().getWindow()), "Campos vacios, por favor completarlos.");
        }
    }

    private void crearReporteFechasEstadoPago() {
        if (fechaFin.getValue() != null && fechaIni.getValue() != null && cbEstadoPago.getValue() != null) {
            ReportesService.reporteGastosMantFechasEstadoPago(fechaIni.getValue().toString(), fechaFin.getValue().toString(), cbEstadoPago.getValue());
        } else {
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Generar reporte", ((Stage) btnLimpiar.getScene().getWindow()), "Campos vacios, por favor completarlos.");
        }
    }

    private void crearReporteFechasAreaTrabajo() {
        if (fechaFin.getValue() != null && fechaIni.getValue() != null && cbAreaTrabajo.getValue() != null) {
            ReportesService.reporteGastosMantFechasAreaTrabajo(fechaIni.getValue().toString(), fechaFin.getValue().toString(), cbAreaTrabajo.getValue().getId().toString());
        } else {
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Generar reporte", ((Stage) btnLimpiar.getScene().getWindow()), "Campos vacios, por favor completarlos.");
        }
    }

    private void crearReporteFechas() {
        if (fechaFin.getValue() != null && fechaIni.getValue() != null) {
            ReportesService.reporteGastosMantFechas(fechaIni.getValue().toString(), fechaFin.getValue().toString());
            RegistrosAccionesService.createRegistroAccion(new RegistrosAccionesDTO(Token.getInstance().getUsuario(), "Creo nuevo reporte de recorrido aviones", new Date()));
        } else {
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Generar reporte", ((Stage) btnLimpiar.getScene().getWindow()), "Campos vacios, por favor completarlos.");
        }
    }

    @Override
    public void initialize() {

    }

    public void llenarListaNodos() {
        modDesarrollo.clear();
        modDesarrolloAxiliar.clear();
        modDesarrolloAxiliar.add(titulo.getText());
        modDesarrolloAxiliar.add(btnLimpiar.getText());
        modDesarrolloAxiliar.add(btnGenerar.getText());
        modDesarrolloAxiliar.add(cbFiltro.getPromptText());
        modDesarrolloAxiliar.add(cbEstadoPago.getPromptText());
        modDesarrolloAxiliar.add(cbAreaTrabajo.getPromptText());
        modDesarrolloAxiliar.add(fechaFin.getPromptText());
        modDesarrolloAxiliar.add(fechaIni.getPromptText());
        modDesarrolloAxiliar.add(txtEmpresa.getPromptText());
        modDesarrollo.addAll(Arrays.asList(titulo, btnLimpiar, btnGenerar, cbFiltro, cbEstadoPago, cbAreaTrabajo, fechaFin, fechaIni, txtEmpresa));
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
