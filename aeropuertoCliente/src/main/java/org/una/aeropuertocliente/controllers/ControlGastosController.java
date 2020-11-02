/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.util.Callback;
import org.una.aeropuertocliente.dtos.ControlesGastosDTO;
import org.una.aeropuertocliente.dtos.DetallesControlesGastosDTO;
import org.una.aeropuertocliente.dtos.ZonasDTO;
import org.una.aeropuertocliente.entitiesServices.ControlGastosService;
import org.una.aeropuertocliente.utils.AppContext;
import org.una.aeropuertocliente.utils.Mensaje;

/**
 * FXML Controller class
 *
 * @author cfugu
 */
public class ControlGastosController extends Controller implements Initializable {

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
    private TableView<ControlesGastosDTO> tableGastos;
    @FXML
    private Label lblRegistrar;
    @FXML
    private JFXButton btnRegistrar;
    String mensaje;
    ControlesGastosDTO gastos;
    ControlesGastosDTO gastosFilt;
    public List<ControlesGastosDTO> gastosList = new ArrayList<ControlesGastosDTO>();
    public List<DetallesControlesGastosDTO> detallesList = new ArrayList<DetallesControlesGastosDTO>();
    @FXML
    private Label lblDesde;
    @FXML
    private JFXDatePicker fDesde;
    @FXML
    private Label lblHasta;
    @FXML
    private JFXDatePicker fHasta;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbFiltro.setItems(FXCollections.observableArrayList("Id", "Empresa", "Intervalo Fechas", "Contrato", "Estado", "Tipo"));
        actionControlClick();
        cmbFiltro.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (t1 == "Id") {
                    fDesde.setVisible(false);
                    fHasta.setVisible(false);
                    txtBusqueda.setVisible(true);
                    txtBusqueda.setPromptText("Ingrese el  número correspondiente");
                }
                if (t1 == "Empresa") {
                    fDesde.setVisible(false);
                    fHasta.setVisible(false);
                    txtBusqueda.setVisible(true);
                    txtBusqueda.setPromptText("Ingrese el nombre de la empresa");
                }
                if (t1 == "Contrato") {
                    fDesde.setVisible(false);
                    fHasta.setVisible(false);
                    txtBusqueda.setVisible(true);
                    txtBusqueda.setPromptText("Ingrese el número de contrato");
                }
                if (t1 == "Tipo") {
                    fDesde.setVisible(false);
                    fHasta.setVisible(false);
                    txtBusqueda.setVisible(true);
                    txtBusqueda.setPromptText("Ingrese el tipo de gasto");
                }
                if (t1 == "Estado") {
                    fDesde.setVisible(false);
                    fHasta.setVisible(false);
                    txtBusqueda.setVisible(true);
                    txtBusqueda.setPromptText("Ingrese el estado del gasto");
                }

                if (t1 == "Intervalo Fechas") {
                    fDesde.setVisible(true);
                    fHasta.setVisible(true);
                    txtBusqueda.setVisible(false);
                }
            }

        }
        );
        notificar(1);
    }

    @FXML
    private void onActionFiltrar(ActionEvent event) {
        if (txtBusqueda.getText().isEmpty() || cmbFiltro.getValue().isEmpty()) {
            limpiarTableView();
            mensaje = "Por favor debe ingresar un datos en el campo de búsqueda";
            notificar(0);
        }
        if (cmbFiltro.getValue().equals("Id") && !txtBusqueda.getText().isEmpty()) {
            limpiarTableView();
            gastosFilt = ControlGastosService.idControlGasto(Long.valueOf(txtBusqueda.getText()));
            if (gastosFilt != null) {
                llenarGastos();
                tableGastos.setItems(FXCollections.observableArrayList(gastosFilt));
            } else {
                mensaje = "No se encontró coincidencias";
                notificar(0);
            }
        }
        if (cmbFiltro.getValue().equals("Empresa") && !txtBusqueda.getText().isEmpty()) {
            limpiarTableView();
            gastosList = ControlGastosService.empresaControlesGastos(txtBusqueda.getText());
            if (gastosList != null) {
                llenarGastos();
                tableGastos.setItems(FXCollections.observableArrayList(gastosList));
            } else {
                mensaje = "No se encontró coincidencias";
                notificar(0);
            }
        }
        if (cmbFiltro.getValue().equals("Contrato") && !txtBusqueda.getText().isEmpty()) {
            limpiarTableView();
            gastosList = ControlGastosService.contratoControlesGastos(txtBusqueda.getText());
            if (gastosList != null) {
                llenarGastos();
                tableGastos.setItems(FXCollections.observableArrayList(gastosList));
            } else {
                mensaje = "No se encontró coincidencias";
                notificar(0);
            }
        }
        if (cmbFiltro.getValue().equals("Tipo") && !txtBusqueda.getText().isEmpty()) {
            limpiarTableView();
            gastosList = ControlGastosService.tipoControlesGastos(txtBusqueda.getText());
            if (gastosList != null) {
                llenarGastos();
                tableGastos.setItems(FXCollections.observableArrayList(gastosList));
            } else {
                mensaje = "No se encontró coincidencias";
                notificar(0);
            }
        }
        if (cmbFiltro.getValue().equals("Estado") && !txtBusqueda.getText().isEmpty()) {
            limpiarTableView();
            gastosList = ControlGastosService.estadoControlesGastos(txtBusqueda.getText());
            if (gastosList != null) {
                llenarGastos();
                tableGastos.setItems(FXCollections.observableArrayList(gastosList));
            } else {
                mensaje = "No se encontró coincidencias";
                notificar(0);
            }
        }

        if (cmbFiltro.getValue().equals("Intervalo Fechas") && fDesde.getValue() != null && fHasta.getValue() != null) {
            limpiarTableView();
//            String data = txtBusqueda.getText();
//            String sDate1 = data.substring(0, 10);
//            String sDate2 = data.substring(11, 21);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date ini = new Date();
            Date fina = new Date();

            ini = Date.from(fDesde.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            fina = Date.from(fHasta.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            String sDate1 = formatter.format(ini);
            String sDate2 = formatter.format(fina);

            gastosList = ControlGastosService.fechaControlesGastos(sDate1, sDate2);
            if (gastosList != null) {
                llenarGastos();
                tableGastos.setItems(FXCollections.observableArrayList(gastosList));
            } else {
                mensaje = "No se encontró coincidencias";
                notificar(0);
            }
        }
    }

    @FXML
    private void onActionRegistrar(ActionEvent event) {
        AppContext.getInstance().set("control", null);
        PrincipalController.cambiarVistaPrincipal("mantenimientoControlGastos/MantenimientoControlGastos");
    }

    private void llenarGastos() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        TableColumn<ControlesGastosDTO, String> colId = new TableColumn("Id");
        colId.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getId().toString()));
        TableColumn<ControlesGastosDTO, String> colEmpresa = new TableColumn("Empresa");
        colEmpresa.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getEmpresaContratante()));
        TableColumn<ControlesGastosDTO, String> colContrato = new TableColumn("Contrato");
        colContrato.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getNumeroContrato()));
        TableColumn<ControlesGastosDTO, String> colResponsable = new TableColumn("Responsable");
        colResponsable.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getResponsable()));
        TableColumn<ControlesGastosDTO, String> colFecha = new TableColumn("Fecha");
        colFecha.setCellValueFactory((param) -> new SimpleObjectProperty(formatter.format(param.getValue().getFechaRegistro())));
        TableColumn<ControlesGastosDTO, String> colTipo = new TableColumn("Tipo");
        colTipo.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getDetalleControlGastoId().getTipoServicio()));
        TableColumn<ControlesGastosDTO, String> colEstado = new TableColumn("Estado pago");
        colEstado.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getDetalleControlGastoId().getEstadoPago()));
        TableColumn<ControlesGastosDTO, String> colArea = new TableColumn("Area");
        colArea.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getDetalleControlGastoId().getAreaTrabajoId().getNombreAreaTrabajo()));
        tableGastos.getColumns().addAll(colId, colEmpresa, colContrato, colResponsable, colFecha, colTipo, colEstado, colArea);
        addButtonToTable();
    }

    private void actionControlClick() {
        tableGastos.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2 && tableGastos.selectionModelProperty().get().getSelectedItem() != null) {
                    ControlesGastosDTO control = (ControlesGastosDTO) tableGastos.selectionModelProperty().get().getSelectedItem();
                    AppContext.getInstance().set("control", control);
                    System.out.println(control.getNumeroContrato());
                    PrincipalController.cambiarVistaPrincipal("mantenimientoControlGastos/MantenimientoControlGastos");
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
            tableGastos.setPlaceholder(box);
        } else {
            ImageView imageView2 = new ImageView(new Image("org/una/aeropuertocliente/views/shared/warning.png"));
            Text lab = new Text(mensaje);
            lab.setFill(Color.web("#0076a3"));
            VBox box = new VBox();
            box.setAlignment(Pos.CENTER);
            box.getChildren().add(imageView2);
            box.getChildren().add(lab);
            tableGastos.setPlaceholder(box);
        }
    }

    private void limpiarTableView() {
        tableGastos.getItems().clear();
        tableGastos.getColumns().clear();
    }

    private void addButtonToTable() {
        TableColumn<ControlesGastosDTO, Void> colBtn = new TableColumn("Acción");

        Callback<TableColumn<ControlesGastosDTO, Void>, TableCell<ControlesGastosDTO, Void>> cellFactory = new Callback<TableColumn<ControlesGastosDTO, Void>, TableCell<ControlesGastosDTO, Void>>() {
            @Override
            public TableCell<ControlesGastosDTO, Void> call(final TableColumn<ControlesGastosDTO, Void> param) {
                final TableCell<ControlesGastosDTO, Void> cell = new TableCell<ControlesGastosDTO, Void>() {

                    private final JFXButton btn = new JFXButton("Editar");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            ControlesGastosDTO control = getTableView().getItems().get(getIndex());
                            AppContext.getInstance().set("control", control);
                            System.out.println(control.getNumeroContrato());
                            PrincipalController.cambiarVistaPrincipal("mantenimientoControlGastos/MantenimientoControlGastos");
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

        tableGastos.getColumns().add(colBtn);

    }

    @Override
    public void initialize() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
