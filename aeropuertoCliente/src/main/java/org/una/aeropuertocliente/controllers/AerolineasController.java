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
import javafx.scene.layout.AnchorPane;
import org.una.aeropuertocliente.dtos.AerolineasDTO;
import org.una.aeropuertocliente.entitiesServices.AerolineasService;
import org.una.aeropuertocliente.utils.Mensaje;

/**
 * FXML Controller class
 *
 * @author Bosco
 */
public class AerolineasController extends Controller implements Initializable {

    @FXML
    private Button btnFiltrar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TableView<AerolineasDTO> tableview;
    @FXML
    private JFXTextField txtFilter;
    @FXML
    private JFXComboBox<AerolineasDTO> combFilter;
    @FXML
    private Label labTitulo;
    @FXML
    private Label labTxtFild;
    @FXML
    private Label labComb;
    @FXML
    private Label labBtnFiltrar;
    @FXML
    private Label labBtnCancelar;
    @FXML
    private Label labbtnRegistrarAvion;
    @FXML
    private JFXButton btnRegistrarAvion;
    
    AerolineasService aerolineaService;

    AerolineasDTO aerolinea;
    public List<AerolineasDTO> aerolineaList = new ArrayList<AerolineasDTO>();
    @FXML
    private AnchorPane AnchorPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenarAerolineas();
    }    

    private void llenarAerolineas() {
        TableColumn<AerolineasDTO, String> colId = new TableColumn("Id");
        colId.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getId().toString()));
        TableColumn<AerolineasDTO, String> colNombreAerolinea = new TableColumn("Nombre Aerolinea");
        colNombreAerolinea.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getNombreAerolinea()));
        TableColumn<AerolineasDTO, String> colResponsable = new TableColumn("Nombre Responsable");
        colResponsable.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getNombreResponsable()));
        TableColumn<AerolineasDTO, String> colEstado = new TableColumn("Estado");
        colEstado.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().isEstado()));
        tableview.getColumns().addAll(colId, colNombreAerolinea, colResponsable,colEstado);

        try {         
            aerolineaList = AerolineasService.allAerolineas();
            if (aerolineaList != null && !aerolineaList.isEmpty()) {
                tableview.setItems(FXCollections.observableArrayList(aerolineaList));
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error de tramite", null, "La lista está nula o vacía");
            }
        } catch (Exception e) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de tramite", null, "Hubo un error al obtener los datos a cargar");
        }
    }
    
    @FXML
    private void filtrar(ActionEvent event) {
    }

    @FXML
    private void cancelar(ActionEvent event) {
    }

    @FXML
    private void registrarAvion(ActionEvent event) {
    }

    @Override
    public void initialize() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
