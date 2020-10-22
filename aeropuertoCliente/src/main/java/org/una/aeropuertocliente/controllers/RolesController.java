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
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import static org.una.aeropuertocliente.controllers.PrincipalController.cambiarVistaPrincipal;
import org.una.aeropuertocliente.dtos.RolesDTO;
import org.una.aeropuertocliente.entitiesServices.RolesService;
import org.una.aeropuertocliente.sharedService.Token;
import org.una.aeropuertocliente.utils.AppContext;
import org.una.aeropuertocliente.utils.FlowController;

/**
 * FXML Controller class
 *
 * @author cfugu
 */
public class RolesController extends Controller implements Initializable {

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
    private TableView<RolesDTO> tableRoles;
    @FXML
    private Label lblRegistrar;
    @FXML
    private JFXButton btnRegistrar;
    RolesDTO rolesFilt;
    RolesDTO roles;
    String mensaje;

    public List<RolesDTO> rolesList = new ArrayList<RolesDTO>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (!Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_GESTOR")) {
            btnRegistrar.setVisible(false);
            btnRegistrar.setDisable(true);
        } else {
            btnRegistrar.setVisible(true);
            btnRegistrar.setDisable(false);
        }
        actionRolesClick();
        llenarRoles();
        cmbFiltro.setItems(FXCollections.observableArrayList("Id", "Estado", "Código"));
        mensaje = "Por favor debe ingresar un datos en el campo de búsqueda";
    }

    @FXML
    private void onActionFiltrar(ActionEvent event) {
        if (txtBusqueda.getText() == null || txtBusqueda.getText().isEmpty() || cmbFiltro.getValue().isEmpty()) {
            tableRoles.getItems().clear();
            mensaje = "Por favor debe ingresar un datos en el campo de búsqueda";
        }
        if (cmbFiltro.getValue().equals("Id") && !txtBusqueda.getText().isEmpty()) {
            tableRoles.getItems().clear();
            rolesFilt = RolesService.idRole(Long.valueOf(txtBusqueda.getText()));
            if (rolesFilt != null) {
                tableRoles.setItems(FXCollections.observableArrayList(rolesFilt));
            } else {
                mensaje = "No se encontró coincidencias";
                notificar(0);
            }
        }
        if (cmbFiltro.getValue().equals("Estado") && !txtBusqueda.getText().isEmpty()) {
            if (txtBusqueda.getText().equals("true")) {
                tableRoles.getItems().clear();
                rolesList = RolesService.estadoRoles(true);
                if (rolesList != null) {
                    tableRoles.setItems(FXCollections.observableArrayList(rolesList));
                } else {
                    mensaje = "No se encontró coincidencias";
                    notificar(0);
                }
            }
            if (txtBusqueda.getText().equals("false")) {
                tableRoles.getItems().clear();
                rolesList = RolesService.estadoRoles(false);
                if (rolesList != null) {
                    tableRoles.setItems(FXCollections.observableArrayList(rolesList));
                } else {
                    mensaje = "No se encontró coincidencias";
                    notificar(0);
                }
            }
            if (rolesList != null) {
                mensaje = "No se encontró coincidencias";
                notificar(0);
            }
        }
        if (cmbFiltro.getValue().equals("Código") && !txtBusqueda.getText().isEmpty()) {
            tableRoles.getItems().clear();
            rolesList = RolesService.codigoRoles(txtBusqueda.getText());
            if (rolesList != null) {
                tableRoles.setItems(FXCollections.observableArrayList(rolesList));
            } else {
                mensaje = "No se encontró coincidencias";
                notificar(0);
            }
        }
    }

    @FXML
    private void onActionRegistrar(ActionEvent event) {
        AppContext.getInstance().set("rol", null);
        cambiarVistaPrincipal("mantenimientoRoles/MantenimientoRoles");
    }

    private void llenarRoles() {
        TableColumn<RolesDTO, String> colId = new TableColumn("Id");
        colId.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getId().toString()));
        TableColumn<RolesDTO, String> colEstado = new TableColumn("Estado");
        colEstado.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().isEstado()));
        TableColumn<RolesDTO, String> colCodigo = new TableColumn("Código");
        colCodigo.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getCodigo()));
        TableColumn<RolesDTO, String> colDescripcion = new TableColumn("Descripcion");
        colDescripcion.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getDescripcion()));
        tableRoles.getColumns().addAll(colId, colEstado, colCodigo, colDescripcion);
        notificar(1);
    }

    private void actionRolesClick() {
        tableRoles.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_GESTOR")) {
                    if (mouseEvent.getClickCount() == 2 && tableRoles.selectionModelProperty().get().getSelectedItem() != null) {
                        RolesDTO rol = (RolesDTO) tableRoles.selectionModelProperty().get().getSelectedItem();
                        AppContext.getInstance().set("rol", rol);
                        System.out.println(rol.getDescripcion());
                        cambiarVistaPrincipal("mantenimientoRoles/MantenimientoRoles");
                    }
                }

            }
        });
    }

    private void notificar(int num) {
        tableRoles.getItems().clear();
        if (num == 1) {
            ImageView imageView = new ImageView(new Image("org/una/aeropuertocliente/views/shared/info.png"));
            Text lab = new Text("Para mostrar datos en este apartado debe realizar el filtro correspondiente");
            lab.setFill(Color.web("#0076a3"));
            VBox box = new VBox();
            box.setAlignment(Pos.CENTER);
            box.getChildren().add(imageView);
            box.getChildren().add(lab);
            tableRoles.setPlaceholder(box);
        } else {
            ImageView imageView2 = new ImageView(new Image("org/una/aeropuertocliente/views/shared/warning.png"));
            Text lab = new Text(mensaje);
            lab.setFill(Color.web("#0076a3"));
            VBox box = new VBox();
            box.setAlignment(Pos.CENTER);
            box.getChildren().add(imageView2);
            box.getChildren().add(lab);
            tableRoles.setPlaceholder(box);
        }
    }

    @Override
    public void initialize() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
