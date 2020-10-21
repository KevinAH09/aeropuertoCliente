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
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.una.aeropuertocliente.dtos.AerolineasDTO;
import org.una.aeropuertocliente.dtos.AvionesDTO;
import org.una.aeropuertocliente.dtos.AvionesZonasDTO;
import org.una.aeropuertocliente.dtos.VuelosDTO;
import org.una.aeropuertocliente.utils.AppContext;
import org.una.aeropuertocliente.utils.Mensaje;

/**
 * FXML Controller class
 *
 * @author Bosco
 */
public class MantenimientoAvionController extends Controller implements Initializable {

    @FXML
    private TextField txtTipoAvion;
    @FXML
    private TextField txtZona;
    @FXML
    private Button btnZona;
    @FXML
    private TextField txtFiltrar;
    @FXML
    private Button btnFiltrar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TableView<VuelosDTO> tableview;
    @FXML
    private Label labTituloAviones;
    @FXML
    private Label labTxtFildTipoAvion;
    @FXML
    private Label labTxtFildMatricula;
    @FXML
    private Label labCombEstado;
    @FXML
    private Label labTxtFildZona;
    @FXML
    private Label labBtnAgregar;
    @FXML
    private Label labTxtFildVuelo;
    @FXML
    private Label labCombFiltro;
    @FXML
    private Label labBtnFiltro;
    @FXML
    private Label labBtnCancelar;
    @FXML
    private Label labTable;
    @FXML
    private Label labBtn;
    @FXML
    private JFXTextField txtMatricula;
    @FXML
    private JFXComboBox<String> combEstado;
    @FXML
    private JFXComboBox<?> combFiltro;
    @FXML
    private JFXButton btnGuardar;
    AvionesDTO avion;
    AvionesZonasDTO avionZona;
    public List<VuelosDTO> vuelosList = new ArrayList<VuelosDTO>();
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       avion =  (AvionesDTO) AppContext.getInstance().get("agregarAvion");
        combEstado.setItems(FXCollections.observableArrayList("Activo", "Inactivo"));
        System.out.println("---------------------------"+avion);
//        if (avion != null) {
//            llenarVuelos();
//            if (avion.isEstado()) {
//                combEstado.setValue("Activo");
//            } else {
//                combEstado.setValue("Inactivo");
//            }
//            txtTipoAvion.setDisable(true);
//            txtMatricula.setDisable(true);
//            txtZona.setDisable(true);
//            combEstado.setDisable(true);
//            txtTipoAvion.setText(avion.getTipoAvion());
//            txtMatricula.setText(avion.getMatricula());
//            txtZona.setText(aerolinea.getNombreResponsable());
//            if (AvionesService.aerolinea(aerolinea.getId()).isEmpty()) {
//                txtFilter.setVisible(false);
//                combFilter.setVisible(false);
//                btnFiltrar.setVisible(false);
//                btnCancelar.setVisible(false);
//                tableAviones.setVisible(false);
//            }
//            btnCancelar.setVisible(false);
//            btnEditar.setVisible(true);
//            btnGuardar.setDisable(true);
//
//        } else {
//            txtNombre.setText("");
//            txtId.setText("");
//            txtResponsable.setText("");
//            cmbEstado.setValue("Activo");
//
//            txtFilter.setVisible(false);
//            combFilter.setVisible(false);
//            btnFiltrar.setVisible(false);
//            btnCancelar.setVisible(false);
//            tableAviones.setVisible(false);
//
//            txtNombre.setDisable(false);
//            txtId.setDisable(true);
//            txtResponsable.setDisable(false);
//            cmbEstado.setDisable(true);
//
//            btnCancelar.setVisible(false);
//            btnEditar.setDisable(true);
//            btnGuardar.setVisible(true);
//
//            tableAviones.getItems().clear();
//        }
    }    

    @FXML
    private void agregarZona(ActionEvent event) {
    }

    @FXML
    private void filtrar(ActionEvent event) {
    }

    @FXML
    private void combFiltro(ActionEvent event) {
    }

    @FXML
    private void btnFiltrar(ActionEvent event) {
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
    }

    @FXML
    private void guardar(ActionEvent event) {
    }
    
    private void llenarVuelos() {
//        TableColumn<VuelosDTO, String> colId = new TableColumn("Id");
//        colId.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getId().toString()));
//        TableColumn<VuelosDTO, String> colMatricula = new TableColumn("Matrícula");
//        colMatricula.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getMatricula()));
//        TableColumn<VuelosDTO, String> colTipoAvion = new TableColumn("Tipo de avión");
//        colTipoAvion.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getTipoAvion()));
//        TableColumn<VuelosDTO, String> colHoraVuelo = new TableColumn("Horas de vuelo");
//        colHoraVuelo.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getHorasVuelo()));
//        TableColumn<VuelosDTO, String> colEstado = new TableColumn("Estado");
//        colEstado.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().isEstado()));
//        TableColumn<VuelosDTO, String> colAerolinea = new TableColumn("Aerolinea");
//        colAerolinea.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getAerolineaId().getNombreAerolinea()));
//        tableview.getColumns().addAll(colId, colMatricula, colTipoAvion, colHoraVuelo, colEstado, colAerolinea);
//
//        try {
//            avionesList = AvionesService.aerolinea(aerolinea.getId());
//            if (avionesList != null && !avionesList.isEmpty()) {
//                tableview.setItems(FXCollections.observableArrayList(avionesList));
//            } else {
//                new Mensaje().showModal(Alert.AlertType.INFORMATION, "Error de mantenimiento de aerolinea", null, "La aerolinea no tiene aviones");
//            }
//        } catch (Exception e) {
//            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de mantenimiento de aerolinea", null, "Hubo un error al obtener los datos a cargar");
//        }
    }

    @Override
    public void initialize() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
