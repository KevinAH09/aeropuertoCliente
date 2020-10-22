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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import org.una.aeropuertocliente.dtos.AvionesDTO;
import org.una.aeropuertocliente.dtos.VuelosDTO;
import org.una.aeropuertocliente.entitiesServices.AvionesService;
import org.una.aeropuertocliente.utils.AppContext;
import org.una.aeropuertocliente.utils.Mensaje;

/**
 * FXML Controller class
 *
 * @author Bosco
 */
public class VuelosController extends Controller implements Initializable {

    @FXML
    private Label lablTitulo;
    @FXML
    private Label titulo;
    @FXML
    private JFXTextField txtId;
    @FXML
    private Label lbltxtNombre;
    private JFXTextField txtFilter;
    private JFXComboBox<String> combFilter;
    private TableView<AvionesDTO> tableAviones;
    @FXML
    private Label labTxtFild1;
    @FXML
    private JFXTextField txtFilter1;
    @FXML
    private Label labComb1;
    @FXML
    private JFXComboBox<?> combFilter1;
    @FXML
    private Label labBtnFiltrar1;
    @FXML
    private JFXButton btnFiltrar1;
    @FXML
    private Label labBtnCancelar1;
    @FXML
    private JFXButton btnCancelar1;
    @FXML
    private Label lblTable1;
    @FXML
    private TableView<?> tableAviones1;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private Label lbltxtTipo;
    @FXML
    private JFXTextField txtmatricula;
    AvionesDTO aviones;
    AvionesDTO avionesFil;
    public List<AvionesDTO> avionesList = new ArrayList<AvionesDTO>();
    public List<AvionesDTO> avionesList2 = new ArrayList<AvionesDTO>();
    @FXML
    private Label lbltxtTipo1;
    @FXML
    private Label lbltxtNombre1;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //actionAvionClick();
        //llenarAviones();
        //combFilter.setItems(FXCollections.observableArrayList("Id", "Matrícula","Tipo de avión","Estado","Aerolinea"));
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
        tableAviones.getColumns().addAll(colId,colMatricula, colTipoAvion, colHoraVuelo,colEstado,colAerolinea);

        try {
             avionesList = AvionesService.allAviones();
            if (avionesList != null && !avionesList.isEmpty()) {
                tableAviones.setItems(FXCollections.observableArrayList(avionesList));
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error de tramite", null, "La lista está nula o vacía");
            }
        } catch (Exception e) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de tramite", null, "Hubo un error al obtener los datos a cargar");
        }
    }
    
    private void filtrar(ActionEvent event) {
        if (txtFilter.getText() == null || txtFilter.getText().isEmpty()) {
            tableAviones.getItems().clear();
            avionesList = AvionesService.allAviones();
            tableAviones.setItems(FXCollections.observableArrayList(avionesList));
        }
        if (combFilter.getValue().equals("Id") && !txtFilter.getText().isEmpty()) {
            tableAviones.getItems().clear();
            avionesFil = AvionesService.idAvion(Long.valueOf(txtFilter.getText()));
            tableAviones.setItems(FXCollections.observableArrayList(avionesFil));
        }
        if (combFilter.getValue().equals("Estado")&& !txtFilter.getText().isEmpty()) {
            if (txtFilter.getText().equals("true")) {
                tableAviones.getItems().clear();
                avionesList = AvionesService.estado(true);
                tableAviones.setItems(FXCollections.observableArrayList(avionesList));
            }
            if (txtFilter.getText().equals("false")) {
                tableAviones.getItems().clear();
                avionesList = AvionesService.estado(false);
                tableAviones.setItems(FXCollections.observableArrayList(avionesList));
            }
        }
        if (combFilter.getValue().equals("Matrícula")&& !txtFilter.getText().isEmpty()) {
            tableAviones.getItems().clear();
            avionesList = AvionesService.matricula(txtFilter.getText());
            tableAviones.setItems(FXCollections.observableArrayList(avionesList));
        }
        if (combFilter.getValue().equals("Tipo de avión")&& !txtFilter.getText().isEmpty()) {
            tableAviones.getItems().clear();
            avionesList = AvionesService.TipoAvion(txtFilter.getText());
            tableAviones.setItems(FXCollections.observableArrayList(avionesList));
        }
        if (combFilter.getValue().equals("Aerolinea")&& !txtFilter.getText().isEmpty()) {
            
            tableAviones.getItems().clear();
            avionesList = AvionesService.allAviones();
            for (int i = 0; i < avionesList.size(); i++) {
                if (avionesList.get(i).getAerolineaId().getNombreAerolinea().equals(txtFilter.getText())) {
                    avionesList2 = AvionesService.aerolinea(avionesList.get(i).getAerolineaId().getId());
                    
                }

            }
            if (!avionesList2.isEmpty()) {
                tableAviones.setItems(FXCollections.observableArrayList(avionesList2));
            }
        }
        
    }


    @FXML
    private void filtrar1(ActionEvent event) {
    }

    @FXML
    private void cancelar1(ActionEvent event) {
    }

    @FXML
    private void registrarVuelos(ActionEvent event) {
    }

    @Override
    public void initialize() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private void actionAvionClick() {
        tableAviones.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2 && tableAviones.selectionModelProperty().get().getSelectedItem() != null) {
                    AvionesDTO avion = (AvionesDTO) tableAviones.selectionModelProperty().get().getSelectedItem();
                    //AppContext.getInstance().set("avion", zona);
                    txtId.setText(avion.getTipoAvion());
                    txtmatricula.setText(avion.getMatricula());
                    System.out.println(avion.getTipoAvion());
//                    ((Stage) btnFiltrar.getScene().getWindow()).close();
                }

            }
        });
    }

    @FXML
    private void buscarAvion(ActionEvent event) {
        PrincipalController.cambiarVistaPrincipal("aviones/Aviones");
    }
} 
