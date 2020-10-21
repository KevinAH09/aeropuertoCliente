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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.una.aeropuertocliente.dtos.AerolineasDTO;
import org.una.aeropuertocliente.entitiesServices.AerolineasService;
import org.una.aeropuertocliente.utils.AppContext;
import org.una.aeropuertocliente.utils.FlowController;
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
    private JFXComboBox<String> combFilter;
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
    AerolineasDTO aerolinea1;
    AerolineasDTO aerolineaFil;
    public List<AerolineasDTO> aerolineaList = new ArrayList<AerolineasDTO>();
    @FXML
    private AnchorPane AnchorPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        aerolinea=null;
        actionAerolineaClick();
        llenarAerolineas();
        combFilter.setItems(FXCollections.observableArrayList("Id", "Nombre Responsable","Nombre Aerolinea","Estado"));

    }    

    private void llenarAerolineas() {
        TableColumn<AerolineasDTO, String> colId = new TableColumn("Id");
        colId.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getId().toString()));
        TableColumn<AerolineasDTO, String> colNombreAerolinea = new TableColumn("Nombre Aerolinea");
        colNombreAerolinea.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getNombreAerolinea()));
        TableColumn<AerolineasDTO, String> colResponsable = new TableColumn("Nombre Responsable");
        colResponsable.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getNombreResponsable()));
        TableColumn<AerolineasDTO, String> colEstado = new TableColumn("Estado\nActivo(True)Inactivo(False)");
        colEstado.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().isEstado()));
        tableview.getColumns().addAll(colId, colNombreAerolinea, colResponsable,colEstado);

        try {         
            aerolineaList = AerolineasService.allAerolineas();
            if (aerolineaList != null && !aerolineaList.isEmpty()) {
                tableview.setItems(FXCollections.observableArrayList(aerolineaList));
            } else {
                new Mensaje().showModal(Alert.AlertType.INFORMATION, "Información", null, "no cuenta con aerolinea");
            }
        } catch (Exception e) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de aerolinea", null, "Hubo un error al obtener los datos a cargar");
        }
    }
    
    private void actionAerolineaClick() {
        aerolinea=null;
        tableview.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2 && tableview.selectionModelProperty().get().getSelectedItem() != null) {
                    aerolinea = (AerolineasDTO) tableview.selectionModelProperty().get().getSelectedItem();
                    AppContext.getInstance().set("aerolinea", aerolinea);
                    PrincipalController.cambiarVistaPrincipal("mantenimientoAerolineas/MantenimientoAerolineas");                
                } 

            }
        });
    }
    
    @FXML
    private void filtrar(ActionEvent event) {
        
         if (txtFilter.getText() == null || txtFilter.getText().isEmpty()) {
            tableview.getItems().clear();
            aerolineaList = AerolineasService.allAerolineas();
            tableview.setItems(FXCollections.observableArrayList(aerolineaList));
        }
        if (combFilter.getValue().equals("Id") && !txtFilter.getText().isEmpty()) {
            tableview.getItems().clear();
            aerolineaFil = AerolineasService.idAerolinea(Long.valueOf(txtFilter.getText()));
            tableview.setItems(FXCollections.observableArrayList(aerolineaFil));
        }
        if (combFilter.getValue().equals("Estado")&& !txtFilter.getText().isEmpty()) {
            if (txtFilter.getText().equals("true")) {
                tableview.getItems().clear();
                aerolineaList = AerolineasService.estadoAerolinea(true);
                tableview.setItems(FXCollections.observableArrayList(aerolineaList));
            }
            if (txtFilter.getText().equals("false")) {
                tableview.getItems().clear();
                aerolineaList = AerolineasService.estadoAerolinea(false);
                tableview.setItems(FXCollections.observableArrayList(aerolineaList));
            }
        }
        if (combFilter.getValue().equals("Nombre Responsable")&& !txtFilter.getText().isEmpty()) {
            tableview.getItems().clear();
            aerolineaList = AerolineasService.nombreResponsable(txtFilter.getText());
            tableview.setItems(FXCollections.observableArrayList(aerolineaList));
        }
        if (combFilter.getValue().equals("Nombre Aerolinea")&& !txtFilter.getText().isEmpty()) {
            tableview.getItems().clear();
            aerolineaList = AerolineasService.nombreAerolinea(txtFilter.getText());
            tableview.setItems(FXCollections.observableArrayList(aerolineaList));
        }
        
    }

    @FXML
    private void cancelar(ActionEvent event) {
    }

    @FXML
    private void registrarAvion(ActionEvent event) {
        aerolinea=null;
        AppContext.getInstance().set("aerolinea", aerolinea);
        FlowController.getInstance().goView("mantenimientoAerolineas/MantenimientoAerolineas");
         
        //((Stage) btnRegistrarAvion.getScene().getWindow()).close();
       
    }

    @Override
    public void initialize() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
