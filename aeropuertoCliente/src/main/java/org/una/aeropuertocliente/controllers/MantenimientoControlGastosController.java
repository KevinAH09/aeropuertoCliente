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
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
            txtEmpresa.setText(controlesGastosDTO.getEmpresaContratante());
            txtResponsable.setText(controlesGastosDTO.getResponsable());
            txtContrato.setText(controlesGastosDTO.getNumeroContrato());
            txtFecha.setText(controlesGastosDTO.getFechaRegistro().toString());

            detallesGastosDTO = controlesGastosDTO.getDetalleControlGastoId();
            txtObservacion.setText(detallesGastosDTO.getObservacion());
            txtTipoServico.setText(detallesGastosDTO.getTipoServicio());
            txtDuracion.setText(detallesGastosDTO.getDuracion().toString());
            txtPeridiocidad.setText(detallesGastosDTO.getPeriodicidad().toString());
            cmbAreas.setValue(detallesGastosDTO.getAreaTrabajoId().toString());
            cmbEstado.setValue(detallesGastosDTO.getEstado());
            cmbEstadoPago.setValue(detallesGastosDTO.getEstadoPago());
        }

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
        if (controlesGastosDTO == null) {
            if (!txtEmpresa.getText().isEmpty() && !cmbEstado.getValue().isEmpty() && !txtContrato.getText().isEmpty() && !txtResponsable.getText().isEmpty() && !txtObservacion.getText().isEmpty() && !txtTipoServico.getText().isEmpty()
                    && !txtDuracion.getText().isEmpty() && !txtPeridiocidad.getText().isEmpty() && !cmbEstadoPago.getValue().isEmpty() && !cmbAreas.getValue().isEmpty()) {

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

                if (DetallesControlesGastosService.createDetalleControlGasto(detallesGastosDTO) == 201) {
                    controlesGastosDTO.setDetalleControlGastoId(detallesGastosDTO);
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
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error al crear los datos", ((Stage) txtContrato.getScene().getWindow()), "Rellene los campos necesarios");
            }

        } else {
            if (!txtEmpresa.getText().isEmpty() && !cmbEstado.getValue().isEmpty() && !txtContrato.getText().isEmpty() && !txtResponsable.getText().isEmpty() && !txtObservacion.getText().isEmpty() && !txtTipoServico.getText().isEmpty()
                    && !txtDuracion.getText().isEmpty() && !txtPeridiocidad.getText().isEmpty() && !cmbEstadoPago.getValue().isEmpty() && !cmbAreas.getValue().isEmpty()) {
//                controlesGastosDTO = new ControlesGastosDTO();
                controlesGastosDTO.setEmpresaContratante(txtEmpresa.getText());
                controlesGastosDTO.setResponsable(txtResponsable.getText());
                controlesGastosDTO.setNumeroContrato(txtContrato.getText());

//                detallesGastosDTO = new DetallesControlesGastosDTO();
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
                if (DetallesControlesGastosService.updateDetalleControlGasto(detallesGastosDTO) == 200) {
                    controlesGastosDTO.setDetalleControlGastoId(detallesGastosDTO);
                    if (ControlGastosService.updateControlGasto(controlesGastosDTO) == 200) {
                        new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Guardar Controles de gastos", ((Stage) txtContrato.getScene().getWindow()), "Se guardó correctamente");
                        PrincipalController.cambiarVistaPrincipal("controlGastos/ControlGastos");
                    } else {
                        new Mensaje().showModal(Alert.AlertType.ERROR, "Error al guardar Area de trabajo", ((Stage) txtContrato.getScene().getWindow()), "No se guardó correctamente");
                    }
                }else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error al guardar Detalle control de gastos", ((Stage) txtContrato.getScene().getWindow()), "No se guardó correctamente");
                }
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error al crear los datos", ((Stage) txtContrato.getScene().getWindow()), "Rellene los campos necesarios");
            }
        }
    }

    @Override
    public void initialize() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
