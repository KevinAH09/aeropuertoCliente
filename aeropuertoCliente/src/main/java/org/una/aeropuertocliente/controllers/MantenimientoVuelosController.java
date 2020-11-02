/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.una.aeropuertocliente.dtos.AvionesDTO;
import org.una.aeropuertocliente.dtos.BitacorasVuelosDTO;
import org.una.aeropuertocliente.dtos.VuelosDTO;
import org.una.aeropuertocliente.entitiesServices.BitacorasVuelosService;
import org.una.aeropuertocliente.entitiesServices.VuelosService;
import org.una.aeropuertocliente.utils.AppContext;
import org.una.aeropuertocliente.utils.Mensaje;

/**
 * FXML Controller class
 *
 * @author Bosco
 */
public class MantenimientoVuelosController extends Controller implements Initializable {

    @FXML
    private Label labTitulo;
    @FXML
    private Label labtxtOrigen;
    @FXML
    private JFXTextField txtOrigen;
    @FXML
    private Label labtimePikerInicio;
    @FXML
    private Label labtxtAvion;
    @FXML
    private JFXTextField txtAvion;
    @FXML
    private Label labtxtDestino;
    @FXML
    private JFXTextField txtDestino;
    @FXML
    private Label labtimePikerFinal;
    @FXML
    private Label labTorre;
    @FXML
    private Label labCombEstado;
    @FXML
    private Label labBitacora;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXDatePicker datePikerInicio;
    @FXML
    private JFXDatePicker datePikerFinal;
    @FXML
    private Label labTxtTipoBitacora;
    @FXML
    private JFXTextField txtTipoBita;
    @FXML
    private Label labZona;
    @FXML
    private JFXRadioButton radioZona;
    @FXML
    private Label labradioCargaPasajero;
    @FXML
    private JFXRadioButton radioSubioCargaPasajero;
    @FXML
    private Label labRadioCargoCombustible;
    @FXML
    private JFXRadioButton radioCargoCombustible;
    @FXML
    private Label labHoras;
    @FXML
    private JFXRadioButton radioHoras;
    @FXML
    private Label labBtnGuardar;
    @FXML
    private JFXComboBox<String> combEstado;
    @FXML
    private JFXRadioButton radioTorre;
    VuelosDTO vuelos;
    BitacorasVuelosDTO bitacora;
    BitacorasVuelosDTO bitacora2;
    AvionesDTO aviones;
    public List<VuelosDTO> vuelosList = new ArrayList<VuelosDTO>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Callback<DatePicker, DateCell> dayFinCellFactory = dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (datePikerInicio.getValue() != null) {
                    if (item.isBefore(datePikerInicio.getValue())) {
                        setStyle("-fx-background-color: #ffc0cb;");
                        Platform.runLater(() -> setDisable(true));
                    }
                }
            }
        };
        datePikerFinal.setDayCellFactory(dayFinCellFactory);
        Callback<DatePicker, DateCell> dayIniCellFactory = dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (datePikerFinal.getValue() != null) {
                    if (item.isAfter(datePikerFinal.getValue())) {
                        setStyle("-fx-background-color: #ffc0cb;");
                        Platform.runLater(() -> setDisable(true));
                    }
                }
            }
        };
        datePikerInicio.setDayCellFactory(dayIniCellFactory);
        vuelos = (VuelosDTO) AppContext.getInstance().get("VueloAMantenimientoVuelo");
        aviones = (AvionesDTO) AppContext.getInstance().get("AvionAMantenimientoVuelo");
        combEstado.setItems(FXCollections.observableArrayList("Activo", "Inactivo"));
        if (vuelos != null) {
            txtAvion.setText(aviones.getTipoAvion());
            txtAvion.setDisable(true);
            txtDestino.setText(vuelos.getDestino());
            txtOrigen.setText(vuelos.getOrigen());
            if (vuelos.isEstado() == true) {
                combEstado.setValue("Activo");
            } else {
                combEstado.setValue("Inactivo");
            }
            Date date1 = vuelos.getFechaFinal();
            LocalDate localdate1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            datePikerFinal.setValue(localdate1);

            Date date2 = vuelos.getFechaInicio();
            LocalDate localdate2 = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            datePikerInicio.setValue(localdate2);

            txtTipoBita.setText(vuelos.getBitacoraVueloId().getTipoBitacora());

            if (vuelos.getBitacoraVueloId().isCargaCombustible() == true) {
                radioCargoCombustible.setSelected(true);
            }
            if (vuelos.getBitacoraVueloId().isHorasVuelo() == true) {
                radioHoras.setSelected(true);
            }
            if (vuelos.getBitacoraVueloId().isCargaPasajero() == true) {
                radioSubioCargaPasajero.setSelected(true);
            }
            if (vuelos.getBitacoraVueloId().isAutorizacionTorreControl() == true) {
                radioTorre.setSelected(true);
            }
            if (vuelos.getBitacoraVueloId().isZonaDescarga() == true) {
                radioZona.setSelected(true);
            }
        } else {
            txtAvion.setText(aviones.getTipoAvion());
            txtAvion.setDisable(true);
            txtDestino.setText("");
            txtOrigen.setText("");
            txtTipoBita.setText("");
            combEstado.setValue("Activo");
            combEstado.setDisable(true);
            datePikerFinal.setValue(null);
            datePikerInicio.setValue(null);
            radioZona.setSelected(false);
            radioTorre.setSelected(false);
            radioSubioCargaPasajero.setSelected(false);
            radioHoras.setSelected(false);
            radioCargoCombustible.setSelected(false);
                    
        }
    }

    @FXML
    private void guardar(ActionEvent event) {
        if (vuelos == null) {
            if (!txtDestino.getText().isEmpty() && !combEstado.getValue().isEmpty() && !txtOrigen.getText().isEmpty() && !txtTipoBita.getText().isEmpty() && datePikerFinal.getValue() != null && datePikerInicio.getValue() != null) {
                vuelos = new VuelosDTO();
                bitacora = new BitacorasVuelosDTO();
                if (combEstado.getValue().equals("Activo")) {
                    vuelos.setEstado(true);
                } else {
                    vuelos.setEstado(false);
                }
                LocalDate localDateFinal = datePikerFinal.getValue();
                Instant instant1 = Instant.from(localDateFinal.atStartOfDay(ZoneId.systemDefault()));
                Date date1 = Date.from(instant1);
                LocalDate localDateInicio = datePikerInicio.getValue();
                Instant instant2 = Instant.from(localDateInicio.atStartOfDay(ZoneId.systemDefault()));
                Date date2 = Date.from(instant2);
                vuelos.setDestino(txtDestino.getText());
                vuelos.setOrigen(txtOrigen.getText());
                vuelos.setFechaFinal(date1);
                vuelos.setFechaInicio(date2);

                bitacora.setTipoBitacora(txtTipoBita.getText());
                if (radioCargoCombustible.isSelected() == true) {
                    bitacora.setCargaCombustible(true);
                } else {
                    bitacora.setCargaCombustible(false);
                }
                if (radioHoras.isSelected() == true) {
                    bitacora.setHorasVuelo(true);
                } else {
                    bitacora.setHorasVuelo(false);
                }
                if (radioSubioCargaPasajero.isSelected() == true) {
                    bitacora.setCargaPasajero(true);
                } else {
                    bitacora.setCargaPasajero(false);
                }
                if (radioTorre.isSelected() == true) {
                    bitacora.setAutorizacionTorreControl(true);
                } else {
                    bitacora.setAutorizacionTorreControl(false);
                }
                if (radioZona.isSelected() == true) {
                    bitacora.setZonaDescarga(true);
                } else {
                    bitacora.setZonaDescarga(false);
                }

                bitacora2 = BitacorasVuelosService.createBitacoraVueloExpecial(bitacora);
                if (bitacora2 != null) {
                    vuelos.setBitacoraVueloId(bitacora2);
                    vuelos.setAvionId(aviones);
                    if (VuelosService.createVuelo(vuelos) == 201) {
                        new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar Vuelo", ((Stage) txtAvion.getScene().getWindow()), "Se guardó correctamente");
                        PrincipalController.cambiarVistaPrincipal("vuelos/Vuelos");

                    } else {
                        new Mensaje().showModal(Alert.AlertType.ERROR, "Error al guardar el vuelo", ((Stage) txtAvion.getScene().getWindow()), "No se guardó correctamente");

                    }
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error al guardar la Bitácora", ((Stage) txtAvion.getScene().getWindow()), "No se guardó correctamente");
                }
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error al crear Vuelo", ((Stage) txtAvion.getScene().getWindow()), "Rellene los campos necesarios");
            }
        } else {
            if (!txtDestino.getText().isEmpty() && !combEstado.getValue().isEmpty() && !txtOrigen.getText().isEmpty() && !txtTipoBita.getText().isEmpty() && datePikerFinal.getValue() != null && datePikerInicio.getValue() != null) {
                bitacora = new BitacorasVuelosDTO();
                bitacora = vuelos.getBitacoraVueloId();
                if (combEstado.getValue().equals("Activo")) {
                    vuelos.setEstado(true);
                } else {
                    vuelos.setEstado(false);
                }

                LocalDate localDateFinal = datePikerFinal.getValue();
                Instant instant1 = Instant.from(localDateFinal.atStartOfDay(ZoneId.systemDefault()));
                Date date1 = Date.from(instant1);
                LocalDate localDateInicio = datePikerInicio.getValue();
                Instant instant2 = Instant.from(localDateInicio.atStartOfDay(ZoneId.systemDefault()));
                Date date2 = Date.from(instant2);

                vuelos.setDestino(txtDestino.getText());
                vuelos.setOrigen(txtOrigen.getText());
                vuelos.setFechaFinal(date1);
                vuelos.setFechaInicio(date2);
                vuelos.setAvionId(aviones);
                bitacora.setTipoBitacora(txtTipoBita.getText());

                if (radioCargoCombustible.isSelected()) {
                    bitacora.setCargaCombustible(true);
                } else {
                    bitacora.setCargaCombustible(false);
                }
                if (radioHoras.isSelected()) {
                    bitacora.setHorasVuelo(true);
                } else {
                    bitacora.setHorasVuelo(false);
                }
                if (radioSubioCargaPasajero.isSelected()) {
                    bitacora.setCargaPasajero(true);
                } else {
                    bitacora.setCargaPasajero(false);
                }
                if (radioTorre.isSelected()) {
                    bitacora.setAutorizacionTorreControl(true);
                } else {
                    bitacora.setAutorizacionTorreControl(false);
                }
                if (radioZona.isSelected()) {
                    bitacora.setZonaDescarga(true);
                } else {
                    bitacora.setZonaDescarga(false);
                }
                if (BitacorasVuelosService.updateBitacoraVuelo(bitacora) == 200) {
                    if (VuelosService.updateVuelo(vuelos) == 200) {
                        new Mensaje().showModal(Alert.AlertType.INFORMATION, "Editar Vuelo", ((Stage) txtAvion.getScene().getWindow()), "Se editó correctamente");
                        PrincipalController.cambiarVistaPrincipal("vuelos/Vuelos");
                    } else {
                        new Mensaje().showModal(Alert.AlertType.ERROR, "Error al editar el Vuelo", ((Stage) txtAvion.getScene().getWindow()), "No se editó correctamente");
                    }
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error al editar el bitácora", ((Stage) txtAvion.getScene().getWindow()), "No se editó correctamente");
                }

            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error al editar el Vuelo", ((Stage) txtAvion.getScene().getWindow()), "Rellene los campos necesarios");
            }
        }
    }

    @Override
    public void initialize() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
