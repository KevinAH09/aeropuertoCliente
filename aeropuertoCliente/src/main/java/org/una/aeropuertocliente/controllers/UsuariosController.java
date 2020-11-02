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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.una.aeropuertocliente.dtos.ZonasDTO;
import org.una.aeropuertocliente.sharedService.Token;
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
    @FXML
    private Label lblEstado;
    @FXML
    private JFXComboBox<String> cmbEstado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbEstado.setItems(FXCollections.observableArrayList("Activo", "Inactivo"));
        cmbFiltro.setItems(FXCollections.observableArrayList("Id", "Estado", "Nombre", "Cedula", "Rol", "Area Trabajo"));
        notificar(1);
        cmbFiltro.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (t1 == "Estado") {
                    cmbEstado.setVisible(true);
                    txtBusqueda.setVisible(false);
                } else {
                    cmbEstado.setVisible(false);
                    txtBusqueda.setVisible(true);
                }
            }

        }
        );
        if (!Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_GESTOR")) {
            btnRegistrar.setVisible(false);
            btnRegistrar.setDisable(true);
        } else {
            actionUsuariosClick();
            addButtonToTable();
            btnRegistrar.setVisible(true);
            btnRegistrar.setDisable(false);
        }
    }

    @FXML
    private void onActionFiltrar(ActionEvent event) {
        if (txtBusqueda.getText() == null || txtBusqueda.getText().isEmpty() || cmbFiltro.getValue().isEmpty()) {
            limpiarTableView();
            notificar(0);
        }
        if (cmbFiltro.getValue().equals("Id") && !txtBusqueda.getText().isEmpty()) {
            limpiarTableView();
            usuariosFilt = UsuariosService.idUsuario(Long.valueOf(txtBusqueda.getText()));
            if (usuariosFilt != null) {
                llenarUsuarios();
                tableUsuarios.setItems(FXCollections.observableArrayList(usuariosFilt));
            } else {
                notificar(0);
            }
        }
        if (cmbFiltro.getValue().equals("Estado") && cmbEstado.getValue() != null) {
            if (cmbEstado.getValue().equals("Activo")) {
                limpiarTableView();
                usuariosList = UsuariosService.estadoUsuarios(true);
                if (usuariosList != null) {
                    llenarUsuarios();
                    tableUsuarios.setItems(FXCollections.observableArrayList(usuariosList));
                } else {
                    notificar(0);
                }
            }
            if (cmbEstado.getValue().equals("Inactivo")) {
                limpiarTableView();
                usuariosList = UsuariosService.estadoUsuarios(false);
                if (usuariosList != null) {
                    llenarUsuarios();
                    tableUsuarios.setItems(FXCollections.observableArrayList(usuariosList));
                } else {
                    notificar(0);
                }
            }
        }
        if (cmbFiltro.getValue().equals("Nombre") && !txtBusqueda.getText().isEmpty()) {
            limpiarTableView();
            usuariosList = UsuariosService.nombreUsuarios(txtBusqueda.getText());
            if (usuariosList != null) {
                llenarUsuarios();
                tableUsuarios.setItems(FXCollections.observableArrayList(usuariosList));
            } else {
                notificar(0);
            }
        }
        if (cmbFiltro.getValue().equals("Cedula") && !txtBusqueda.getText().isEmpty()) {
            limpiarTableView();
            usuariosFilt = UsuariosService.cedulaUsuarios(txtBusqueda.getText());
            if (usuariosFilt != null) {
                llenarUsuarios();
                tableUsuarios.setItems(FXCollections.observableArrayList(usuariosList));
            } else {
                notificar(0);
            }
        }
        if (cmbFiltro.getValue().equals("Area Trabajo") && !txtBusqueda.getText().isEmpty()) {
            limpiarTableView();
            usuariosList = UsuariosService.allUsuarios();
            for (int i = 0; i < usuariosList.size(); i++) {
                llenarUsuarios();
                if (usuariosList.get(i).getAreaTrabajoId().getNombreAreaTrabajo().equals(txtBusqueda.getText().toUpperCase())) {
                    usuariosList2 = UsuariosService.areaTrabajoUsuarios(usuariosList.get(i).getAreaTrabajoId().getId());
                    if (usuariosList2 != null) {
                        tableUsuarios.setItems(FXCollections.observableArrayList(usuariosList2));
                    } else {
                        notificar(0);
                    }
                }

            }
        }
        if (cmbFiltro.getValue().equals("Rol") && !txtBusqueda.getText().isEmpty()) {
            limpiarTableView();
            usuariosList = UsuariosService.allUsuarios();
            for (int i = 0; i < usuariosList.size(); i++) {
                llenarUsuarios();
                if (usuariosList.get(i).getRolId().getDescripcion().equals(txtBusqueda.getText().toUpperCase())) {
                    usuariosList2 = UsuariosService.rolUsuarios(usuariosList.get(i).getRolId().getId());
                    if (usuariosList2 != null) {
                        tableUsuarios.setItems(FXCollections.observableArrayList(usuariosList2));
                    } else {
                        notificar(0);
                    }
                }

            }

        }
    }

    @FXML
    private void onActionRegistrar(ActionEvent event) {
        AppContext.getInstance().set("usu", null);
        PrincipalController.cambiarVistaPrincipal("mantenimientoUsuarios/MantenimientoUsuarios");
    }

    private void llenarUsuarios() {
        TableColumn<UsuariosDTO, String> colId = new TableColumn("Id");
        colId.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getId().toString()));
        TableColumn<UsuariosDTO, String> colNombre = new TableColumn("Nombre");
        colNombre.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getNombreCompleto()));
        TableColumn<UsuariosDTO, String> colEstado = new TableColumn("Estado");
        colEstado.setCellValueFactory((param) -> {
            if (param.getValue().isEstado()) {
                return new SimpleStringProperty("Activo");
            }
            return new SimpleStringProperty("Inactivo");
        });
        TableColumn<UsuariosDTO, String> colCedula = new TableColumn("Cédula");
        colCedula.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getCedula()));
        TableColumn<UsuariosDTO, String> colCorreo = new TableColumn("Correo");
        colCorreo.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getCorreo()));
        TableColumn<UsuariosDTO, String> colFecha = new TableColumn("Fecha Registro");
        colFecha.setCellValueFactory((param) -> new SimpleObjectProperty(new SimpleDateFormat("dd-MM-yyyy").format(param.getValue().getFechaRegistro())));
        TableColumn<UsuariosDTO, String> colRol = new TableColumn("Rol");
        colRol.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getRolId().getDescripcion()));
        TableColumn<UsuariosDTO, String> colArea = new TableColumn("Area Trabajo");
        colArea.setCellValueFactory((param) -> {
            if (param.getValue().getAreaTrabajoId() != null) {
                return new SimpleStringProperty(param.getValue().getAreaTrabajoId().getNombreAreaTrabajo());
            }
            return new SimpleStringProperty("no tiene");
        });
        tableUsuarios.getColumns().addAll(colId, colNombre, colEstado, colCedula, colCorreo, colFecha, colRol, colArea);
    }

    private void actionUsuariosClick() {
        tableUsuarios.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_GESTOR")) {
                    if (mouseEvent.getClickCount() == 2 && tableUsuarios.selectionModelProperty().get().getSelectedItem() != null) {
                        UsuariosDTO usuario = (UsuariosDTO) tableUsuarios.selectionModelProperty().get().getSelectedItem();
                        AppContext.getInstance().set("usu", usuario);
                        PrincipalController.cambiarVistaPrincipal("mantenimientoUsuarios/MantenimientoUsuarios");
                    }
                }

            }
        });
    }

    private void notificar(int num) {
        limpiarTableView();
        if (num == 1) {
            ImageView imageView = new ImageView(new Image("org/una/aeropuertocliente/views/shared/info.png"));
            Text lab = new Text("Para mostrar datos en este apartado debe realizar el filtro correspondiente");
            lab.setFill(Color.web("#0076a3"));
            VBox box = new VBox();
            box.setAlignment(Pos.CENTER);
            box.getChildren().add(imageView);
            box.getChildren().add(lab);
            tableUsuarios.setPlaceholder(box);
        } else {
            ImageView imageView2 = new ImageView(new Image("org/una/aeropuertocliente/views/shared/warning.png"));
            Text lab = new Text("No se encontró coincidencias");
            lab.setFill(Color.web("#0076a3"));
            VBox box = new VBox();
            box.setAlignment(Pos.CENTER);
            box.getChildren().add(imageView2);
            box.getChildren().add(lab);
            tableUsuarios.setPlaceholder(box);
        }
    }

    private void limpiarTableView() {
        tableUsuarios.getItems().clear();
        tableUsuarios.getColumns().clear();
    }

    private void addButtonToTable() {
        TableColumn<UsuariosDTO, Void> colBtn = new TableColumn("Acción");

        Callback<TableColumn<UsuariosDTO, Void>, TableCell<UsuariosDTO, Void>> cellFactory = new Callback<TableColumn<UsuariosDTO, Void>, TableCell<UsuariosDTO, Void>>() {
            @Override
            public TableCell<UsuariosDTO, Void> call(final TableColumn<UsuariosDTO, Void> param) {
                final TableCell<UsuariosDTO, Void> cell = new TableCell<UsuariosDTO, Void>() {

                    private final JFXButton btn = new JFXButton("Editar");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            UsuariosDTO usuario = getTableView().getItems().get(getIndex());
                            AppContext.getInstance().set("usu", usuario);
                            PrincipalController.cambiarVistaPrincipal("mantenimientoUsuarios/MantenimientoUsuarios");
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        tableUsuarios.getColumns().add(colBtn);

    }

    @Override
    public void initialize() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
