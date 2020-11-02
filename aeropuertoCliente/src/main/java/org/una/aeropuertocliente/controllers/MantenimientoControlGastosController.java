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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.una.aeropuertocliente.dtos.AreasTrabajosDTO;
import org.una.aeropuertocliente.dtos.ControlesGastosDTO;
import org.una.aeropuertocliente.dtos.DetallesControlesGastosDTO;
import org.una.aeropuertocliente.entitiesServices.AreasTrabajosService;
import org.una.aeropuertocliente.entitiesServices.ControlGastosService;
import org.una.aeropuertocliente.entitiesServices.DetallesControlesGastosService;
import org.una.aeropuertocliente.utils.AppContext;
import org.una.aeropuertocliente.utils.Mensaje;

/**
 * FXML Controller class
 *
 * @author cfugu
 */
public class MantenimientoControlGastosController extends Controller implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private Label titulo;
    @FXML
    private Label lbltxtId;
    @FXML
    private Label lblTxtResponsable;
    @FXML
    private JFXTextField txtResponsable;
    @FXML
    private Label lblCmbEstado;
    @FXML
    private JFXComboBox<String> cmbEstado;
    @FXML
    private Label lblcmbEsatdo;
    @FXML
    private Label lblFechaRegistro;
    @FXML
    private JFXTextField txtFecha;
    @FXML
    private Label lbltxtMonto;
    @FXML
    private JFXTextField txtMonto;
    @FXML
    private Label lblTitulo2;
    @FXML
    private Label lbltxtObservacion;
    @FXML
    private Label lbltxtTipo;
    @FXML
    private JFXTextField txtTipoServico;
    @FXML
    private Label lbltxtDuracion;
    @FXML
    private JFXTextField txtDuracion;
    @FXML
    private Label lbltxtPeridiocidad;
    @FXML
    private JFXTextField txtPeridiocidad;
    @FXML
    private Label lblCancelar;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private Label lblGuardar;
    @FXML
    private JFXButton btnRegistrar;
    @FXML
    private JFXTextField txtObservacion;
    @FXML
    private JFXComboBox<String> cmbEstadoPago;
    @FXML
    private Label lblCmbArea;
    @FXML
    private JFXComboBox<String> cmbAreas;
    public List<AreasTrabajosDTO> areasList = new ArrayList<AreasTrabajosDTO>();
    public List<DetallesControlesGastosDTO> detallesList = new ArrayList<DetallesControlesGastosDTO>();
    AreasTrabajosDTO areaTrabajoDTO;
    ControlesGastosDTO controlesGastosDTO;
    DetallesControlesGastosDTO detallesGastosDTO;
    DetallesControlesGastosDTO detallesGastosDTO2;
    @FXML
    private JFXTextField txtEmpresa;
    @FXML
    private JFXTextField txtContrato;
    @FXML
    private Label lbltxtNombre;
    public List area = new ArrayList<String>();
    private List<Node> requeridos = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        areasList = AreasTrabajosService.allAreasTrabajos();
        for (AreasTrabajosDTO areasTrabajosDTO : areasList) {
            area.add(areasTrabajosDTO.getNombreAreaTrabajo());
        }

        cmbEstado.setItems(FXCollections.observableArrayList("Valido", "Anulado"));
        cmbEstadoPago.setItems(FXCollections.observableArrayList("Anulado", "Cancelado", "Pendiente"));
        cmbAreas.setItems(FXCollections.observableArrayList(area));
        areaTrabajoDTO = null;
        txtEmpresa.clear();
        txtResponsable.clear();
        txtContrato.clear();
        txtFecha.clear();
        txtFecha.setDisable(true);

        txtObservacion.clear();
        txtTipoServico.clear();
        txtDuracion.clear();
        txtPeridiocidad.clear();
        controlesGastosDTO = (ControlesGastosDTO) AppContext.getInstance().get("control");
        if (controlesGastosDTO != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            txtEmpresa.setText(controlesGastosDTO.getEmpresaContratante());
            txtResponsable.setText(controlesGastosDTO.getResponsable());
            txtContrato.setText(controlesGastosDTO.getNumeroContrato());
            txtFecha.setText(formatter.format(controlesGastosDTO.getFechaRegistro()));

            detallesGastosDTO = controlesGastosDTO.getDetalleControlGastoId();
            txtObservacion.setText(detallesGastosDTO.getObservacion());
            txtTipoServico.setText(detallesGastosDTO.getTipoServicio());
            txtDuracion.setText(detallesGastosDTO.getDuracion().toString());
            txtPeridiocidad.setText(detallesGastosDTO.getPeriodicidad().toString());
            cmbAreas.setValue(detallesGastosDTO.getAreaTrabajoId().toString());
            cmbEstado.setValue(detallesGastosDTO.getEstado());
            cmbEstadoPago.setValue(detallesGastosDTO.getEstadoPago());
        }
        indicarRequeridos();
    }

    @FXML
    private void onActionCancelar(ActionEvent event) {
        detallesGastosDTO = null;
        controlesGastosDTO = null;
        txtEmpresa.clear();
        txtResponsable.clear();
        txtContrato.clear();
        txtFecha.clear();
        txtFecha.setDisable(true);

        txtObservacion.clear();
        txtTipoServico.clear();
        txtDuracion.clear();
        txtPeridiocidad.clear();

        PrincipalController.cambiarVistaPrincipal("controlGastos/ControlGastos");
    }

    @FXML
    private void onActionRegistrar(ActionEvent event) {
        String validacion = validarRequeridos();
        if (controlesGastosDTO == null) {
            if (validacion == null) {

                controlesGastosDTO = new ControlesGastosDTO();
                controlesGastosDTO.setEmpresaContratante(txtEmpresa.getText());
                controlesGastosDTO.setResponsable(txtResponsable.getText());
                controlesGastosDTO.setNumeroContrato(txtContrato.getText());

                detallesGastosDTO = new DetallesControlesGastosDTO();
                detallesGastosDTO.setObservacion(txtObservacion.getText());
                detallesGastosDTO.setTipoServicio(txtTipoServico.getText());
                detallesGastosDTO.setDuracion(Long.parseLong(txtDuracion.getText()));
                detallesGastosDTO.setPeriodicidad(Long.parseLong(txtPeridiocidad.getText()));
                detallesGastosDTO.setEstado(cmbEstado.getValue());
                detallesGastosDTO.setEstadoPago(cmbEstadoPago.getValue());

                for (AreasTrabajosDTO areasTrabajosDTO : areasList) {
                    if (cmbAreas.getValue() == areasTrabajosDTO.getNombreAreaTrabajo()) {
                        detallesGastosDTO.setAreaTrabajoId(areasTrabajosDTO);
                    }
                }
                detallesGastosDTO2 = DetallesControlesGastosService.createDetalleControlGasto(detallesGastosDTO);
                if (detallesGastosDTO2 != null) {
                    controlesGastosDTO.setDetalleControlGastoId(detallesGastosDTO2);

                    if (ControlGastosService.createControlGasto(controlesGastosDTO) == 201) {
                        new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Guardar Controles de gastos", ((Stage) txtContrato.getScene().getWindow()), "Se guardó correctamente");
                        PrincipalController.cambiarVistaPrincipal("controlGastos/ControlGastos");
                    } else {
                        new Mensaje().showModal(Alert.AlertType.ERROR, "Error al guardar Area de trabajo", ((Stage) txtContrato.getScene().getWindow()), "No se guardó correctamente");
                    }
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error al guardar Detalle control de gastos", ((Stage) txtContrato.getScene().getWindow()), "No se guardó correctamente");
                }
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error al crear los datos", ((Stage) txtContrato.getScene().getWindow()), validacion);
            }

        } else {
            if (validacion == null) {

                controlesGastosDTO.setEmpresaContratante(txtEmpresa.getText());
                controlesGastosDTO.setResponsable(txtResponsable.getText());
                controlesGastosDTO.setNumeroContrato(txtContrato.getText());

                detallesGastosDTO.setObservacion(txtObservacion.getText());
                detallesGastosDTO.setTipoServicio(txtTipoServico.getText());
                detallesGastosDTO.setDuracion(Long.parseLong(txtDuracion.getText()));
                detallesGastosDTO.setPeriodicidad(Long.parseLong(txtPeridiocidad.getText()));
                detallesGastosDTO.setEstado(cmbEstado.getValue());
                detallesGastosDTO.setEstadoPago(cmbEstadoPago.getValue());

                if (DetallesControlesGastosService.updateDetalleControlGasto(detallesGastosDTO) == 200) {
                    controlesGastosDTO.setDetalleControlGastoId(detallesGastosDTO);
                    if (ControlGastosService.updateControlGasto(controlesGastosDTO) == 200) {
                        new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Guardar Controles de gastos", ((Stage) txtContrato.getScene().getWindow()), "Se guardó correctamente");
                        PrincipalController.cambiarVistaPrincipal("controlGastos/ControlGastos");
                    } else {
                        new Mensaje().showModal(Alert.AlertType.ERROR, "Error al guardar Area de trabajo", ((Stage) txtContrato.getScene().getWindow()), "No se guardó correctamente");
                    }
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error al guardar Detalle control de gastos", ((Stage) txtContrato.getScene().getWindow()), "No se guardó correctamente");
                }
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error al crear los datos", ((Stage) txtContrato.getScene().getWindow()), validacion);
            }
        }
    }

    public void indicarRequeridos() {
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txtContrato, txtDuracion, txtEmpresa, txtObservacion, txtPeridiocidad, txtResponsable,
                txtTipoServico, cmbAreas, cmbEstadoPago, cmbEstado));
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
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
