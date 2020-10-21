/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.una.aeropuertocliente.dtos.AerolineasDTO;
import org.una.aeropuertocliente.dtos.AvionesDTO;
import org.una.aeropuertocliente.entitiesServices.AerolineasService;
import org.una.aeropuertocliente.entitiesServices.AvionesService;
import org.una.aeropuertocliente.utils.AppContext;
import org.una.aeropuertocliente.utils.FlowController;
import org.una.aeropuertocliente.utils.Mensaje;

/**
 * FXML Controller class
 *
 * @author cfugu
 */
public class MantenimientoAerolineasController extends Controller implements Initializable {

    @FXML
    private JFXTextField txtId;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextField txtResponsable;
    @FXML
    private JFXComboBox<String> cmbEstado;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private TableView<AvionesDTO> tableAviones;
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lbltxtId;
    @FXML
    private Label lbltxtNombre;
    @FXML
    private Label titulo;
    @FXML
    private Label lblTxtResponsable;
    @FXML
    private Label lblCmbEstado;
    @FXML
    private Label lblGuardar;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private Label lblCancelar;
    @FXML
    private Label lblEditar;
    @FXML
    private Label lblTable;
    @FXML
    private Label lblRegistrar;
    @FXML
    private JFXButton btnRegistrar;
    AerolineasDTO aerolinea;
    AvionesDTO aviones;
    AvionesDTO avionesFil;
    public List<AvionesDTO> avionesList = new ArrayList<AvionesDTO>();
    public List<AvionesDTO> avionesList2 = new ArrayList<AvionesDTO>();
    @FXML
    private Label labTxtFild;
    @FXML
    private JFXTextField txtFilter;
    @FXML
    private Label labComb;
    @FXML
    private JFXComboBox<String> combFilter;
    @FXML
    private Label labBtnFiltrar;
    @FXML
    private JFXButton btnFiltrar;
    @FXML
    private Label lblVolver;
    @FXML
    private JFXButton btnVolverAerolinea;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        aerolinea = (AerolineasDTO) AppContext.getInstance().get("aerolinea");
        combFilter.setItems(FXCollections.observableArrayList("Id", "Matrícula", "Tipo de avión", "Estado", "Aerolinea"));
        cmbEstado.setItems(FXCollections.observableArrayList("Activo", "Inactivo"));
        if (aerolinea != null) {
            actionAvionClick();
            llenarAviones();
            if (aerolinea.isEstado()) {
                cmbEstado.setValue("Activo");
            } else {
                cmbEstado.setValue("Inactivo");
            }
            txtNombre.setDisable(true);
            txtId.setDisable(true);
            txtResponsable.setDisable(true);
            cmbEstado.setDisable(true);
            txtNombre.setText(aerolinea.getNombreAerolinea());
            txtId.setText(aerolinea.getId().toString());
            txtResponsable.setText(aerolinea.getNombreResponsable());
            if (AvionesService.aerolinea(aerolinea.getId()).isEmpty()) {
                txtFilter.setVisible(false);
                combFilter.setVisible(false);
                btnFiltrar.setVisible(false);
                btnCancelar.setVisible(false);
                tableAviones.setVisible(false);
            }
            btnCancelar.setVisible(false);
            btnEditar.setVisible(true);
            btnGuardar.setDisable(true);

        } else {
            txtNombre.setText("");
            txtId.setText("");
            txtResponsable.setText("");
            cmbEstado.setValue("Activo");

            txtFilter.setVisible(false);
            combFilter.setVisible(false);
            btnFiltrar.setVisible(false);
            btnCancelar.setVisible(false);
            tableAviones.setVisible(false);

            txtNombre.setDisable(false);
            txtId.setDisable(true);
            txtResponsable.setDisable(false);
            cmbEstado.setDisable(true);

            btnCancelar.setVisible(false);
            btnEditar.setDisable(true);
            btnGuardar.setVisible(true);

            tableAviones.getItems().clear();
        }

    }

    private void actionAvionClick() {
        tableAviones.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2 && tableAviones.selectionModelProperty().get().getSelectedItem() != null) {
                     aviones = (AvionesDTO) tableAviones.selectionModelProperty().get().getSelectedItem();
                    AppContext.getInstance().set("agregarAvion", aviones);
                    PrincipalController.cambiarVistaPrincipal("mantenimientoAviones/MantenimientoAvion"); 
                }
            }
        });
    }
    
    private void llenarAviones() {
        TableColumn<AvionesDTO, String> colId = new TableColumn("Id");
        colId.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getId().toString()));
        TableColumn<AvionesDTO, String> colMatricula = new TableColumn("Matrícula");
        colMatricula.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getMatricula()));
        TableColumn<AvionesDTO, String> colTipoAvion = new TableColumn("Tipo de avión");
        colTipoAvion.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getTipoAvion()));
        TableColumn<AvionesDTO, String> colHoraVuelo = new TableColumn("Horas de vuelo");
        colHoraVuelo.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getHorasVuelo()));
        TableColumn<AvionesDTO, String> colEstado = new TableColumn("Estado");
        colEstado.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().isEstado()));
        TableColumn<AvionesDTO, String> colAerolinea = new TableColumn("Aerolinea");
        colAerolinea.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getAerolineaId().getNombreAerolinea()));
        tableAviones.getColumns().addAll(colId, colMatricula, colTipoAvion, colHoraVuelo, colEstado, colAerolinea);

        try {
            avionesList = AvionesService.aerolinea(aerolinea.getId());
            if (avionesList != null && !avionesList.isEmpty()) {
                tableAviones.setItems(FXCollections.observableArrayList(avionesList));
            } else {
                new Mensaje().showModal(Alert.AlertType.INFORMATION, "Error de mantenimiento de aerolinea", null, "La aerolinea no tiene aviones");
            }
        } catch (Exception e) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de mantenimiento de aerolinea", null, "Hubo un error al obtener los datos a cargar");
        }
    }

    @FXML
    private void onActionGuardar(ActionEvent event) {
        if (aerolinea == null) {
            if (!txtNombre.getText().isEmpty() && !cmbEstado.getValue().isEmpty() && !txtResponsable.getText().isEmpty()) {
                aerolinea = new AerolineasDTO();
                if (cmbEstado.getValue().equals("Activo")) {
                    aerolinea.setEstado(true);
                } else {
                    aerolinea.setEstado(false);
                }
                aerolinea.setNombreResponsable(txtResponsable.getText());
                aerolinea.setNombreAerolinea(txtNombre.getText());
                if (AerolineasService.createAerolinea(aerolinea) == 201) {
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar Aerolinea", ((Stage) txtNombre.getScene().getWindow()), "Se guardó correctamente");
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error al guardar la Aerolinea", ((Stage) txtNombre.getScene().getWindow()), "No se guardó correctamente");
                }
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error al crear Aerolinea", ((Stage) txtNombre.getScene().getWindow()), "Rellene los campos necesarios");
            }

        } else {
            if (!txtNombre.getText().isEmpty() && !cmbEstado.getValue().isEmpty() && !txtResponsable.getText().isEmpty()) {
                if (cmbEstado.getValue().equals("Activo")) {
                    aerolinea.setEstado(true);
                } else {
                    aerolinea.setEstado(false);
                }
                aerolinea.setNombreResponsable(txtResponsable.getText());
                aerolinea.setNombreAerolinea(txtNombre.getText());
                if (AerolineasService.updateAerolinea(aerolinea) == 200) {
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Editar Aerolinea", ((Stage) txtNombre.getScene().getWindow()), "Se editó correctamente");
                    btnEditar.setDisable(false);
                    btnGuardar.setDisable(true);
                    btnRegistrar.setDisable(false);
                    btnVolverAerolinea.setDisable(false);
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error al editar la Aerolinea", ((Stage) txtNombre.getScene().getWindow()), "No se editó correctamente");
                }
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error al editar la Aerolinea", ((Stage) txtNombre.getScene().getWindow()), "Rellene los campos necesarios");
            }
        }
    }

    @FXML
    private void onActionCancelar(ActionEvent event) {
    }

    @FXML
    private void onActionEditar(ActionEvent event) {
        txtNombre.setDisable(false);
        txtId.setDisable(true);
        txtResponsable.setDisable(false);
        cmbEstado.setDisable(false);
        btnEditar.setDisable(true);
        btnGuardar.setDisable(false);
        btnRegistrar.setDisable(true);
        btnVolverAerolinea.setDisable(true);

    }

    @FXML
    private void onActionRegistrar(ActionEvent event) {
        aviones=null;
        AppContext.getInstance().set("agregarAvion", aviones);
        PrincipalController.cambiarVistaPrincipal("mantenimientoAviones/MantenimientoAvion"); 
    }

    @Override
    public void initialize() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void filtrar(ActionEvent event) {
        if (txtFilter.getText() == null || txtFilter.getText().isEmpty()) {
            tableAviones.getItems().clear();
            avionesList = AvionesService.aerolinea(aerolinea.getId());
            tableAviones.setItems(FXCollections.observableArrayList(avionesList));
        }
        if (combFilter.getValue().equals("Id") && !txtFilter.getText().isEmpty()) {
            tableAviones.getItems().clear();
            avionesFil = AvionesService.idAvion(Long.valueOf(txtFilter.getText()));
            tableAviones.setItems(FXCollections.observableArrayList(avionesFil));
        }
        if (combFilter.getValue().equals("Estado") && !txtFilter.getText().isEmpty()) {
            if (txtFilter.getText().equals("true")) {
                tableAviones.getItems().clear();
                avionesList = AvionesService.estado(true);
                tableAviones.setItems(FXCollections.observableArrayList(avionesList));
            }
            if (txtFilter.getText().equals("false")) {
                tableAviones.getItems().clear();
                avionesList = AvionesService.estado(false);
                tableAviones.setItems(FXCollections.observableArrayList(avionesList));
            } else {
                tableAviones.getItems().clear();
                avionesList = AvionesService.aerolinea(aerolinea.getId());
                tableAviones.setItems(FXCollections.observableArrayList(avionesList));
            }
        }
        if (combFilter.getValue().equals("Matrícula") && !txtFilter.getText().isEmpty()) {
            tableAviones.getItems().clear();
            avionesList = AvionesService.matricula(txtFilter.getText());
            tableAviones.setItems(FXCollections.observableArrayList(avionesList));
        }
        if (combFilter.getValue().equals("Tipo de avión") && !txtFilter.getText().isEmpty()) {
            tableAviones.getItems().clear();
            avionesList = AvionesService.TipoAvion(txtFilter.getText());
            tableAviones.setItems(FXCollections.observableArrayList(avionesList));
        }
    }

    @FXML
    private void volver(ActionEvent event) {
        PrincipalController.cambiarVistaPrincipal("aerolineas/Aerolineas"); 
    }

}
