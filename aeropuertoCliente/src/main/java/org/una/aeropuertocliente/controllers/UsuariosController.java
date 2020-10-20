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
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.una.aeropuertocliente.utils.AppContext;
import org.una.aeropuertocliente.utils.FlowController;

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
    public List<UsuariosDTO> usuariosList = new ArrayList<UsuariosDTO>();
    public List<UsuariosDTO> usuariosList2 = new ArrayList<UsuariosDTO>();
    public UsuariosDTO usuariosFilt = new UsuariosDTO();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        actionUsuariosClick();
        cmbFiltro.setItems(FXCollections.observableArrayList("Id", "Estado", "Nombre", "Cedula", "Rol"));
        llenarUsuarios();
    }

    @FXML
    private void onActionFiltrar(ActionEvent event) {
        if (txtBusqueda.getText() == null || txtBusqueda.getText().isEmpty()) {
            tableUsuarios.getItems().clear();
            usuariosList = UsuariosService.allUsuarios();
            tableUsuarios.setItems(FXCollections.observableArrayList(usuariosList));
        }
        if (cmbFiltro.getValue().equals("Id") && !txtBusqueda.getText().isEmpty()) {
            tableUsuarios.getItems().clear();
            usuariosFilt = UsuariosService.idUsuario(Long.valueOf(txtBusqueda.getText()));
            tableUsuarios.setItems(FXCollections.observableArrayList(usuariosFilt));
        }
        if (cmbFiltro.getValue().equals("Estado") && !txtBusqueda.getText().isEmpty()) {
            if (txtBusqueda.getText().equals("true")) {
                tableUsuarios.getItems().clear();
                usuariosList = UsuariosService.estadoUsuarios(true);
                tableUsuarios.setItems(FXCollections.observableArrayList(usuariosList));
            }
            if (txtBusqueda.getText().equals("false")) {
                tableUsuarios.getItems().clear();
                usuariosList = UsuariosService.estadoUsuarios(false);
                tableUsuarios.setItems(FXCollections.observableArrayList(usuariosList));
            }
        }
        if (cmbFiltro.getValue().equals("Nombre") && !txtBusqueda.getText().isEmpty()) {
            tableUsuarios.getItems().clear();
            usuariosList = UsuariosService.nombreUsuarios(txtBusqueda.getText());
            tableUsuarios.setItems(FXCollections.observableArrayList(usuariosList));
        }
        if (cmbFiltro.getValue().equals("Cedula") && !txtBusqueda.getText().isEmpty()) {
            tableUsuarios.getItems().clear();
            usuariosFilt = UsuariosService.cedulaUsuarios(txtBusqueda.getText());
            tableUsuarios.setItems(FXCollections.observableArrayList(usuariosFilt));
        }
        if (cmbFiltro.getValue().equals("Rol") && !txtBusqueda.getText().isEmpty()) {
            boolean band = true;
            tableUsuarios.getItems().clear();
            usuariosList = UsuariosService.allUsuarios();
            for (int i = 0; i < usuariosList.size() || band == true; i++) {
                if (usuariosList.get(i).getRolId().getDescripcion().equals(txtBusqueda.getText().toUpperCase())) {
                    System.out.println("Entró");
                    System.out.println(usuariosList.get(i).getRolId().getDescripcion());
                    usuariosList2 = UsuariosService.rolUsuarios(usuariosList.get(i).getRolId().getId());
                    System.out.println(usuariosList2.get(0).getNombreCompleto());
                    band = false;
                }

            }
            band = true;
            System.out.println(usuariosList2.size());
            if (!usuariosList2.isEmpty() || usuariosList2 != null) {
                tableUsuarios.setItems(FXCollections.observableArrayList(usuariosList2));
            }
        }
    }

    @FXML
    private void onActionRegistrar(ActionEvent event) {
        FlowController.getInstance().goView("mantenimientoUsuarios/MantenimientoUsuarios");
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
        tableUsuarios.getColumns().addAll(colId, colNombre, colEstado, colCedula, colCorreo, colFecha, colRol);

        try {
            usuariosList = UsuariosService.allUsuarios();
            System.out.println(usuariosList);
            if (usuariosList != null && !usuariosList.isEmpty()) {
                tableUsuarios.setItems(FXCollections.observableArrayList(usuariosList));
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error de Usuario", null, "La lista está nula o vacía");
            }
        } catch (Exception e) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de Usuario", null, "Hubo un error al obtener los datos a cargar");
        }
    }

    private void actionUsuariosClick() {
        tableUsuarios.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2 && tableUsuarios.selectionModelProperty().get().getSelectedItem() != null) {
                    UsuariosDTO usuario = (UsuariosDTO) tableUsuarios.selectionModelProperty().get().getSelectedItem();
                    AppContext.getInstance().set("usu", usuario);
                    System.out.println(usuario.getNombreCompleto());
//                    ((Stage) btnFiltrar.getScene().getWindow()).close();
                }

            }
        });
    }

    @Override
    public void initialize() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
