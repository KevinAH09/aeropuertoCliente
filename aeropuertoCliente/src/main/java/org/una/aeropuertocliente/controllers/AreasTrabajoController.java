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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.una.aeropuertocliente.dtos.AreasTrabajosDTO;
import org.una.aeropuertocliente.dtos.ZonasDTO;
import org.una.aeropuertocliente.entitiesServices.AreasTrabajosService;
import org.una.aeropuertocliente.sharedService.Token;
import org.una.aeropuertocliente.utils.AppContext;
import org.una.aeropuertocliente.utils.FlowController;
import org.una.aeropuertocliente.utils.Mensaje;

/**
 * FXML Controller class
 *
 * @author cfugu
 */
public class AreasTrabajoController extends Controller implements Initializable {

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
    private Label lblRegistrar;
    @FXML
    private JFXButton btnRegistrar;
    @FXML
    private Label lblTitulo;
    @FXML
    private TableView<AreasTrabajosDTO> tableAreas;
    public AreasTrabajosService areaService;
    public List<AreasTrabajosDTO> areasList = new ArrayList<AreasTrabajosDTO>();
    public AreasTrabajosDTO areasFilt;
    String mensaje;

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
        actionZonasClick();
        notificar(1);
        cmbFiltro.setItems(FXCollections.observableArrayList("Id", "Nombre"));
        cmbFiltro.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (t1 == "Id") {
                    txtBusqueda.setPromptText("Ingrese el  número correspondiente");
                }
                if (t1 == "Nombre") {
                    txtBusqueda.setPromptText("Ingrese el nombre de la zona");
                }
            }

        }
        );
    }

    @FXML
    private void onActionFiltrar(ActionEvent event) {
        if (txtBusqueda.getText() == null || txtBusqueda.getText().isEmpty() || cmbFiltro.getValue() == null) {
            mensaje = "Por favor debe ingresar datos en el campo de búsqueda";
            notificar(0);
        }
        if (cmbFiltro.getValue().equals("Id") && !txtBusqueda.getText().isEmpty() && cmbFiltro.getValue() != null) {
            System.out.println("Entro Areas");
            limpiarTableView();
            areasFilt = areaService.idAreaTrabajo(Long.valueOf(txtBusqueda.getText()));
            if (areasFilt != null) {
                llenarAreas();
                tableAreas.setItems(FXCollections.observableArrayList(areasFilt));
            } else {
                mensaje = "No se encontró coincidencias";
                notificar(0);
            }
        }
        if (cmbFiltro.getValue().equals("Nombre") && !txtBusqueda.getText().isEmpty() && cmbFiltro.getValue() != null) {
            limpiarTableView();
            areasList = areaService.nombreAreasTrabajos(txtBusqueda.getText().toUpperCase());
            if (areasList != null) {
                llenarAreas();
                tableAreas.setItems(FXCollections.observableArrayList(areasList));
            } else {
                mensaje = "No se encontró coincidencias";
                notificar(0);
            }
        }
    }

    @FXML
    private void onActionRegistrar(ActionEvent event) {
        AppContext.getInstance().set("area", null);
        PrincipalController.cambiarVistaPrincipal("mantenimientoAreasTrabajo/MantenimientoAreasTrabajo");
    }

    private void llenarAreas() {
        TableColumn<AreasTrabajosDTO, String> colId = new TableColumn("Id");
        colId.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getId().toString()));
        TableColumn<AreasTrabajosDTO, String> colNombre = new TableColumn("Nombre");
        colNombre.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getNombreAreaTrabajo()));
        TableColumn<AreasTrabajosDTO, String> colDescrpcion = new TableColumn("Descripción");
        colDescrpcion.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getDescripcion()));
        TableColumn<AreasTrabajosDTO, String> colEstado = new TableColumn("Estado");
        colEstado.setCellValueFactory((param) -> {
            if (param.getValue().isEstado()) {
                return new SimpleStringProperty("Activo");
            }
            return new SimpleStringProperty("Inactivo");
        });
        tableAreas.getColumns().addAll(colId, colNombre, colDescrpcion, colEstado);
        addButtonToTable();

    }

    private void actionZonasClick() {
        tableAreas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_GESTOR")) {
                    if (mouseEvent.getClickCount() == 2 && tableAreas.selectionModelProperty().get().getSelectedItem() != null) {
                        AreasTrabajosDTO area = (AreasTrabajosDTO) tableAreas.selectionModelProperty().get().getSelectedItem();
                        AppContext.getInstance().set("area", area);
                        System.out.println(area.getNombreAreaTrabajo());
                        PrincipalController.cambiarVistaPrincipal("mantenimientoAreasTrabajo/MantenimientoAreasTrabajo");
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
            tableAreas.setPlaceholder(box);
        } else {
            ImageView imageView2 = new ImageView(new Image("org/una/aeropuertocliente/views/shared/warning.png"));
            Text lab = new Text(mensaje);
            lab.setFill(Color.web("#0076a3"));
            VBox box = new VBox();
            box.setAlignment(Pos.CENTER);
            box.getChildren().add(imageView2);
            box.getChildren().add(lab);
            tableAreas.setPlaceholder(box);
        }
    }

    private void limpiarTableView() {
        tableAreas.getItems().clear();
        tableAreas.getColumns().clear();
    }

    private void addButtonToTable() {
        TableColumn<AreasTrabajosDTO, Void> colBtn = new TableColumn("Acción");

        Callback<TableColumn<AreasTrabajosDTO, Void>, TableCell<AreasTrabajosDTO, Void>> cellFactory = new Callback<TableColumn<AreasTrabajosDTO, Void>, TableCell<AreasTrabajosDTO, Void>>() {
            @Override
            public TableCell<AreasTrabajosDTO, Void> call(final TableColumn<AreasTrabajosDTO, Void> param) {
                final TableCell<AreasTrabajosDTO, Void> cell = new TableCell<AreasTrabajosDTO, Void>() {

                    private final JFXButton btn = new JFXButton("Editar");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            AreasTrabajosDTO area = getTableView().getItems().get(getIndex());
                            AppContext.getInstance().set("area", area);
                            PrincipalController.cambiarVistaPrincipal("mantenimientoAreasTrabajo/MantenimientoAreasTrabajo");
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

        tableAreas.getColumns().add(colBtn);

    }

    @Override
    public void initialize() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
