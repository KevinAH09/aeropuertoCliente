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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
import org.una.aeropuertocliente.entitiesServices.ZonasService;
import org.una.aeropuertocliente.sharedService.Token;
import org.una.aeropuertocliente.utils.AppContext;
import org.una.aeropuertocliente.utils.FlowController;

/**
 * FXML Controller class
 *
 * @author cfugu
 */
public class ZonasController extends Controller implements Initializable {

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
    private Label lblTable;
    @FXML
    private TableView<ZonasDTO> tableZonas;
    @FXML
    private Label lblFiltrar;
    @FXML
    private JFXButton btnFiltrar;
    ZonasDTO zonas;
    ZonasDTO zonasFilt;
    public List<ZonasDTO> zonasList = new ArrayList<ZonasDTO>();
    ZonasService zonSer;
    String mensaje;
    public ZonasDTO data = new ZonasDTO();
    @FXML
    private Label lblEstadoZona;
    @FXML
    private JFXComboBox<String> cmbEstado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        actionZonasClick();
        notificar(1);
        cmbFiltro.setItems(FXCollections.observableArrayList("Id", "Estado", "Nombre", "Código"));
        cmbEstado.setItems(FXCollections.observableArrayList("Activo", "Inactivo"));
        cmbFiltro.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (t1 == "Id") {
                    cmbEstado.setVisible(false);
                    txtBusqueda.setVisible(true);
                    txtBusqueda.setPromptText("Ingrese el  número correspondiente");
                }
                if (t1 == "Nombre") {
                    cmbEstado.setVisible(false);
                    txtBusqueda.setVisible(true);
                    txtBusqueda.setPromptText("Ingrese el nombre de la zona");
                }
                if (t1 == "Código") {
                    cmbEstado.setVisible(false);
                    txtBusqueda.setVisible(true);
                    txtBusqueda.setPromptText("Ingrese el código");
                }
                if (t1 == "Estado") {
                    cmbEstado.setVisible(true);
                    txtBusqueda.setVisible(false);
                }
            }

        }
        );
    }

    @FXML
    private void onActionFiltrar(ActionEvent event) {
        if (txtBusqueda.getText() == null || txtBusqueda.getText().isEmpty() || cmbFiltro.getValue().isEmpty()) {
            limpiarTableView();
            mensaje = "Por favor debe ingresar un datos en el campo de búsqueda";
            notificar(0);
        }
        if (cmbFiltro.getValue().equals("Id") && !txtBusqueda.getText().isEmpty()) {
            zonasFilt = ZonasService.idZona(Long.valueOf(txtBusqueda.getText()));
            if (zonasFilt != null) {
                limpiarTableView();
                llenarZonas();
                tableZonas.setItems(FXCollections.observableArrayList(zonasFilt));
            } else {
                mensaje = "No se encontró coincidencias";
                notificar(0);
            }
        }
        if (cmbFiltro.getValue().equals("Estado") && cmbEstado.getValue() != null) {
            if (cmbEstado.getValue().equals("Activo")) {
                zonasList = ZonasService.estadoZona(true);
                if (zonasList != null) {
                    limpiarTableView();
                    llenarZonas();
                    tableZonas.setItems(FXCollections.observableArrayList(zonasList));
                } else {
                    mensaje = "No se encontró coincidencias";
                    notificar(0);
                }
            }
            if (cmbEstado.getValue().equals("Inactivo")) {
                zonasList = ZonasService.estadoZona(false);
                if (zonasList != null) {
                    limpiarTableView();
                    llenarZonas();
                    tableZonas.setItems(FXCollections.observableArrayList(zonasList));
                } else {
                    mensaje = "No se encontró coincidencias";
                    notificar(0);
                }
            }
            if (zonasList == null) {
                mensaje = "No se encontró coincidencias";
                notificar(0);
            }
        }
        if (cmbFiltro.getValue().equals("Nombre") && !txtBusqueda.getText().isEmpty()) {
            zonasList = ZonasService.nombreZona(txtBusqueda.getText());
            if (zonasList != null) {
                limpiarTableView();
                llenarZonas();
                tableZonas.setItems(FXCollections.observableArrayList(zonasList));
            } else {
                mensaje = "No se encontró coincidencias";
                notificar(0);
            }
        }
        if (cmbFiltro.getValue().equals("Código") && !txtBusqueda.getText().isEmpty()) {
            zonasList = ZonasService.codigoZona(txtBusqueda.getText());
            if (zonasList != null) {
                limpiarTableView();
                llenarZonas();
                tableZonas.setItems(FXCollections.observableArrayList(zonasList));
            } else {
                mensaje = "No se encontró coincidencias";
                notificar(0);
            }
        }
    }

    private void llenarZonas() {
        TableColumn<ZonasDTO, String> colId = new TableColumn("Id");
        colId.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getId().toString()));
        TableColumn<ZonasDTO, String> colNombre = new TableColumn("Nombre");
        colNombre.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getNombreZona()));
        TableColumn<ZonasDTO, String> colEstado = new TableColumn("Estado");
        colEstado.setCellValueFactory((param) -> {
            if (param.getValue().isEstado()) {
                return new SimpleStringProperty("Activo");
            }
            return new SimpleStringProperty("Inactivo");
        });
        TableColumn<ZonasDTO, String> colCodigo = new TableColumn("Código");
        colCodigo.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getCodigo()));
        TableColumn<ZonasDTO, String> colDescripcion = new TableColumn("Descripcion");
        colDescripcion.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getDescripcion()));
        tableZonas.getColumns().addAll(colId, colNombre, colEstado, colCodigo, colDescripcion);
        addButtonToTable();

    }

    private void actionZonasClick() {
        tableZonas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2 && tableZonas.selectionModelProperty().get().getSelectedItem() != null) {
                    ZonasDTO zona = (ZonasDTO) tableZonas.selectionModelProperty().get().getSelectedItem();
                    AppContext.getInstance().set("zon", zona);
                    PrincipalController.cambiarVistaPrincipal("mantenimientoAviones/MantenimientoAvion");
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
            tableZonas.setPlaceholder(box);
        } else {
            ImageView imageView2 = new ImageView(new Image("org/una/aeropuertocliente/views/shared/warning.png"));
            Text lab = new Text(mensaje);
            lab.setFill(Color.web("#0076a3"));
            VBox box = new VBox();
            box.setAlignment(Pos.CENTER);
            box.getChildren().add(imageView2);
            box.getChildren().add(lab);
            tableZonas.setPlaceholder(box);
        }
    }

    private void limpiarTableView() {
        tableZonas.getItems().clear();
        tableZonas.getColumns().clear();
    }

    private void addButtonToTable() {
        TableColumn<ZonasDTO, Void> colBtn = new TableColumn("Acción");

        Callback<TableColumn<ZonasDTO, Void>, TableCell<ZonasDTO, Void>> cellFactory = new Callback<TableColumn<ZonasDTO, Void>, TableCell<ZonasDTO, Void>>() {
            @Override
            public TableCell<ZonasDTO, Void> call(final TableColumn<ZonasDTO, Void> param) {
                final TableCell<ZonasDTO, Void> cell = new TableCell<ZonasDTO, Void>() {

                    private final JFXButton btn = new JFXButton("Seleccionar");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            data = getTableView().getItems().get(getIndex());
                            AppContext.getInstance().set("zon", data);
                            PrincipalController.cambiarVistaPrincipal("mantenimientoAviones/MantenimientoAvion");
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

        tableZonas.getColumns().add(colBtn);

    }

    @Override
    public void initialize() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
