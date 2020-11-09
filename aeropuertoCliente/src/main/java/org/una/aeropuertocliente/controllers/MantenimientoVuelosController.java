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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.una.aeropuertocliente.dtos.AvionesDTO;
import org.una.aeropuertocliente.dtos.BitacorasVuelosDTO;
import org.una.aeropuertocliente.dtos.RegistrosAccionesDTO;
import org.una.aeropuertocliente.dtos.VuelosDTO;
import org.una.aeropuertocliente.entitiesServices.AvionesService;
import org.una.aeropuertocliente.entitiesServices.BitacorasVuelosService;
import org.una.aeropuertocliente.entitiesServices.RegistrosAccionesService;
import org.una.aeropuertocliente.entitiesServices.VuelosService;
import org.una.aeropuertocliente.sharedService.Token;
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
    @FXML
    private JFXComboBox<String> combBitacora;
    private List<Node> requeridos = new ArrayList<>();
    public List<Node> modDesarrollo = new ArrayList<>();
    public List<String> modDesarrolloAxiliar = new ArrayList<>();

    @FXML
    private Label titulo;
    @FXML
    private Label titulo2;

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
        combEstado.setItems(FXCollections.observableArrayList("Aprobado", "No aprobado"));
        combBitacora.setItems(FXCollections.observableArrayList("Despegue", "Aterrizaje"));
        combEstado.setDisable(true);
        if (vuelos != null) {
            combBitacora.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                    if (t1 == "Despegue") {
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
                        radioZona.setDisable(true);
                        radioCargoCombustible.setDisable(false);
                        radioHoras.setDisable(false);
                        radioSubioCargaPasajero.setDisable(false);
                        radioTorre.setDisable(false);
                        radioZona.setSelected(false);
                    }
                    if (t1 == "Aterrizaje") {
                        if (vuelos.getBitacoraVueloId().isZonaDescarga() == true) {
                            radioZona.setSelected(true);
                        }
                        radioZona.setDisable(false);
                        radioCargoCombustible.setDisable(true);
                        radioHoras.setDisable(true);
                        radioSubioCargaPasajero.setDisable(true);
                        radioTorre.setDisable(true);

                        radioSubioCargaPasajero.setSelected(false);
                        radioHoras.setSelected(false);
                        radioCargoCombustible.setSelected(false);
                        radioTorre.setSelected(false);
                    }
                }
            }
            );
            txtAvion.setText(aviones.getTipoAvion());
            txtAvion.setDisable(true);
            txtDestino.setText(vuelos.getDestino());
            txtOrigen.setText(vuelos.getOrigen());
            if (vuelos.isEstado() == true) {
                combEstado.setValue("Aprobado");
            } else {
                combEstado.setValue("No aprobado");
            }
            combEstado.setDisable(true);
            Date date1 = vuelos.getFechaFinal();
            LocalDate localdate1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            datePikerFinal.setValue(localdate1);

            Date date2 = vuelos.getFechaInicio();
            LocalDate localdate2 = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            datePikerInicio.setValue(localdate2);

            if (vuelos.getBitacoraVueloId().getTipoBitacora().equals("Despegue")) {
                combBitacora.setValue("Despegue");
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
                radioZona.setDisable(true);

            } else {
                combBitacora.setValue("Aterrizaje");
                if (vuelos.getBitacoraVueloId().isZonaDescarga() == true) {
                    radioZona.setSelected(true);
                }
                radioSubioCargaPasajero.setDisable(true);
                radioHoras.setDisable(true);
                radioCargoCombustible.setDisable(true);
                radioTorre.setDisable(true);
                radioZona.setDisable(false);
            }
        } else {
            combBitacora.setValue("Despegue");
            combBitacora.setDisable(true);
            radioZona.setDisable(true);
            radioSubioCargaPasajero.setDisable(false);
            radioHoras.setDisable(false);
            radioCargoCombustible.setDisable(false);
            txtAvion.setText(aviones.getTipoAvion());
            txtAvion.setDisable(true);
            txtDestino.setText("");
            txtOrigen.setText("");
            combEstado.setValue("No aprobado");
            combEstado.setDisable(true);
            datePikerFinal.setValue(null);
            datePikerInicio.setValue(null);
            radioZona.setSelected(false);
            radioTorre.setSelected(false);
            radioSubioCargaPasajero.setSelected(false);
            radioHoras.setSelected(false);
            radioCargoCombustible.setSelected(false);

        }
        indicarRequeridos();
        llenarListaNodos();
        desarrollo();
    }

    @FXML
    private void guardar(ActionEvent event) {
        String validacion = validarRequeridos();
        if (vuelos == null) {
            if (validacion == null) {
                vuelos = new VuelosDTO();
                bitacora = new BitacorasVuelosDTO();

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

                if ((bitacora.isCargaPasajero() == true || bitacora.isAutorizacionTorreControl() == true) && bitacora.isCargaCombustible() == true && bitacora.isHorasVuelo() == true) {
                    vuelos.setEstado(true);
                } else {
                    vuelos.setEstado(false);
                }

                bitacora.setTipoBitacora("Despegue");
                bitacora.setZonaDescarga(false);
                bitacora2 = BitacorasVuelosService.createBitacoraVueloExpecial(bitacora);
                if (bitacora2 != null) {
                    vuelos.setBitacoraVueloId(bitacora2);

                    String s = aviones.getHorasVuelo();
                    Date input = new Date();
                    input = vuelos.getFechaInicio();

                    Date input1 = new Date();
                    input1 = vuelos.getFechaFinal();

                    long diff = input1.getTime() - input.getTime();
                    long hora = Long.parseLong(s);
                    hora = hora + TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
                    aviones.setHorasVuelo(Long.toString(hora));
                    AvionesService.updateAvion(aviones);
                    vuelos.setAvionId(aviones);
                    if (VuelosService.createVuelo(vuelos) == 201) {
                        RegistrosAccionesService.createRegistroAccion(new RegistrosAccionesDTO(Token.getInstance().getUsuario(), "Creo nuevo vuelo", new Date()));
                        new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar Vuelo", ((Stage) txtAvion.getScene().getWindow()), "Se guardó correctamente");
                        PrincipalController.cambiarVistaPrincipal("vuelos/Vuelos");

                    } else {
                        new Mensaje().showModal(Alert.AlertType.ERROR, "Error al guardar el vuelo", ((Stage) txtAvion.getScene().getWindow()), "No se guardó correctamente");
                    }
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error al guardar la Bitácora", ((Stage) txtAvion.getScene().getWindow()), "No se guardó correctamente");
                }
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error al crear los datos", ((Stage) txtAvion.getScene().getWindow()), validacion);
            }
        } else {
            if (validacion == null) {
                bitacora = new BitacorasVuelosDTO();
                bitacora = vuelos.getBitacoraVueloId();

                LocalDate localDateFinal = datePikerFinal.getValue();
                Instant instant1 = Instant.from(localDateFinal.atStartOfDay(ZoneId.systemDefault()));
                Date date1 = Date.from(instant1);
                LocalDate localDateInicio = datePikerInicio.getValue();
                Instant instant2 = Instant.from(localDateInicio.atStartOfDay(ZoneId.systemDefault()));
                Date date2 = Date.from(instant2);

                long cont = 0;
                long resul = 0;
                String s = aviones.getHorasVuelo();
                long hora = Long.parseLong(s);

                Date input = new Date();
                input = vuelos.getFechaInicio();

                Date input1 = new Date();
                input1 = vuelos.getFechaFinal();

                cont = (input1.getTime() - input.getTime());

                cont = TimeUnit.HOURS.convert(cont, TimeUnit.MILLISECONDS);
                resul = hora - cont;

                vuelos.setDestino(txtDestino.getText());
                vuelos.setOrigen(txtOrigen.getText());
                vuelos.setFechaFinal(date1);
                vuelos.setFechaInicio(date2);

                cont = 0;
                cont = (vuelos.getFechaFinal().getTime() - vuelos.getFechaInicio().getTime());
                cont = TimeUnit.HOURS.convert(cont, TimeUnit.MILLISECONDS);
                resul = resul + cont;
                aviones.setHorasVuelo(Long.toString(resul));
                AvionesService.updateAvion(aviones);

                vuelos.setAvionId(aviones);

                if (combBitacora.getValue().equals("Despegue")) {
                    bitacora.setTipoBitacora("Despegue");
                } else {
                    bitacora.setTipoBitacora("Aterrizaje");
                }
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

                if ((bitacora.isCargaPasajero() == true || bitacora.isAutorizacionTorreControl() == true) && bitacora.isCargaCombustible() == true && bitacora.isHorasVuelo() == true) {
                    vuelos.setEstado(true);
                } else {
                    vuelos.setEstado(false);
                }

                if (BitacorasVuelosService.updateBitacoraVuelo(bitacora) == 200) {
                    if (VuelosService.updateVuelo(vuelos) == 200) {
                        RegistrosAccionesService.createRegistroAccion(new RegistrosAccionesDTO(Token.getInstance().getUsuario(), "Edito vuelo " + vuelos.getId(), new Date()));
                        new Mensaje().showModal(Alert.AlertType.INFORMATION, "Editar Vuelo", ((Stage) txtAvion.getScene().getWindow()), "Se editó correctamente");
                        PrincipalController.cambiarVistaPrincipal("vuelos/Vuelos");
                    } else {
                        new Mensaje().showModal(Alert.AlertType.ERROR, "Error al editar el Vuelo", ((Stage) txtAvion.getScene().getWindow()), "No se editó correctamente");
                    }
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error al editar el bitácora", ((Stage) txtAvion.getScene().getWindow()), "No se editó correctamente");
                }

            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error al crear los datos", ((Stage) txtAvion.getScene().getWindow()), validacion);
            }
        }
    }

    public void indicarRequeridos() {
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txtAvion, txtDestino, txtOrigen, datePikerFinal, datePikerInicio, combEstado));
    }

    public String validarRequeridos() {
        Boolean validos = true;
        String invalidos = "";
        for (Node node : requeridos) {
            if (node instanceof JFXTextField && (((JFXTextField) node).getText() == null || ((JFXTextField) node).getText().isEmpty())) {
                if (validos) {
                    invalidos += ((JFXTextField) node).getPromptText();
                } else {
                    invalidos += "," + ((JFXTextField) node).getPromptText();
                }
                validos = false;
            } else if (node instanceof JFXComboBox && (((JFXComboBox) node).getValue() == null)) {
                if (validos) {
                    invalidos += ((JFXComboBox) node).getPromptText();
                } else {
                    invalidos += "," + ((JFXComboBox) node).getPromptText();
                }
                validos = false;
            } else if (node instanceof JFXDatePicker && (((JFXDatePicker) node).getValue() == null)) {
                if (validos) {
                    invalidos += ((JFXDatePicker) node).getPromptText();
                } else {
                    invalidos += "," + ((JFXDatePicker) node).getPromptText();
                }
                validos = false;
            }
        }
        if (validos) {
            return null;
        } else {
            return "Los siguientes campos son requeridos " + "[" + invalidos + "].";
        }
    }

    @Override
    public void initialize() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void modoDesarrollo(KeyEvent event) {
        KeyCombination cntrlD = new KeyCodeCombination(KeyCode.D, KeyCodeCombination.CONTROL_DOWN);
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

    public void llenarListaNodos() {
        modDesarrollo.clear();
        modDesarrolloAxiliar.clear();
        modDesarrolloAxiliar.add(titulo.getText());
        modDesarrolloAxiliar.add(titulo2.getText());
        modDesarrolloAxiliar.add(txtOrigen.getPromptText());
        modDesarrolloAxiliar.add(txtAvion.getPromptText());
        modDesarrolloAxiliar.add(txtDestino.getPromptText());
        modDesarrolloAxiliar.add(datePikerInicio.getPromptText());
        modDesarrolloAxiliar.add(datePikerFinal.getPromptText());
        modDesarrolloAxiliar.add(combEstado.getPromptText());
        modDesarrolloAxiliar.add(combBitacora.getPromptText());
        modDesarrolloAxiliar.add(btnGuardar.getText());
        modDesarrolloAxiliar.add(radioZona.getText());
        modDesarrolloAxiliar.add(radioSubioCargaPasajero.getText());
        modDesarrolloAxiliar.add(radioCargoCombustible.getText());
        modDesarrolloAxiliar.add(radioHoras.getText());
        modDesarrolloAxiliar.add(radioTorre.getText());
        modDesarrollo.addAll(Arrays.asList(titulo, titulo2, txtOrigen, txtAvion, txtDestino, datePikerInicio, datePikerFinal, combEstado, combBitacora, btnGuardar,
                radioZona, radioSubioCargaPasajero, radioCargoCombustible, radioHoras, radioTorre));
    }

    public void desarrollo() {
        String dato = "";
        boolean validos1 = (Boolean) AppContext.getInstance().get("mod");
        if (validos1) {
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
                if (node instanceof Label) {
                    dato = ((Label) node).getId();
                    ((Label) node).setText(dato);

                }
                if (node instanceof JFXRadioButton) {
                    dato = ((JFXRadioButton) node).getId();
                    ((JFXRadioButton) node).setText(dato);

                }
            }
        } else {
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
                if (modDesarrollo.get(i) instanceof Label) {
                    dato = modDesarrolloAxiliar.get(i);
                    ((Label) modDesarrollo.get(i)).setText(dato);
                }
                if (modDesarrollo.get(i) instanceof JFXRadioButton) {
                    dato = modDesarrolloAxiliar.get(i);
                    ((JFXRadioButton) modDesarrollo.get(i)).setText(dato);
                }

            }
        }
    }

}
