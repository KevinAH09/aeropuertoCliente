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
//import java.util.ArrayList;
//import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.una.aeropuertocliente.dtos.UsuariosDTO;
import org.una.aeropuertocliente.entitiesServices.UsuariosService;
import org.una.aeropuertocliente.utils.Mensaje;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

/**
 * FXML Controller class
 *
 * @author cfugu
 */
public class UsuariosController extends Controller implements Initializable {

    @FXML
    private Label lblZonas;
    @FXML
    private Label titulo;
    @FXML
    private Label lbltxtBusqueda;
    @FXML
    private JFXTextField txtBusqueda;
    @FXML
    private Label lblCmbFiltro;
    @FXML
    private JFXComboBox<String> cmbFiltro;
    @FXML
    private Label lblFiltrar;
    @FXML
    private JFXButton btnFiltrar;
    @FXML
    private Label lblTable;
    @FXML
    private TableView<UsuariosDTO> tableUsuarios;
    @FXML
    private Label lblRegistrar;
    @FXML
    private JFXButton btnRegistrar;
    public List <UsuariosDTO> usuariosList = new ArrayList<UsuariosDTO>();


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenarUsuarios();
    }

    @FXML
    private void onActionFiltrar(ActionEvent event) {
    }

    @FXML
    private void onActionRegistrar(ActionEvent event) {
    }

    private void llenarUsuarios() {
        TableColumn<UsuariosDTO, String> colId = new TableColumn("Id");
        colId.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getId().toString()));
        TableColumn<UsuariosDTO, String> colNombre = new TableColumn("Nombre");
        colNombre.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getNombreCompleto()));
        TableColumn<UsuariosDTO, String> colEstado = new TableColumn("Estado");
        colEstado.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().isEstado()));
        TableColumn<UsuariosDTO, String> colCedula = new TableColumn("Cédula");
        colCedula.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getCedula()));
        TableColumn<UsuariosDTO, String> colCorreo = new TableColumn("Correo");
        colCorreo.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getCorreo()));
        TableColumn<UsuariosDTO, String> colFecha = new TableColumn("Fecha Registro");
        colFecha.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getFechaRegistro()));
        TableColumn<UsuariosDTO, String> colRol = new TableColumn("Rol");
        colRol.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getRolId().getDescripcion()));
        tableUsuarios.getColumns().addAll(colId,colNombre, colEstado, colCedula, colCorreo,colFecha,colRol);

        try {
            usuariosList = UsuariosService.allUsuarios();
            System.out.println(usuariosList);
            if (usuariosList != null && !usuariosList.isEmpty()) {
                System.out.println("Entró");
                tableUsuarios.setItems(FXCollections.observableArrayList(usuariosList));
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error de Usuario", null, "La lista está nula o vacía");
            }
        } catch (Exception e) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de Usuario", null, "Hubo un error al obtener los datos a cargar");
        }
    }

    @Override
    public void initialize() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
