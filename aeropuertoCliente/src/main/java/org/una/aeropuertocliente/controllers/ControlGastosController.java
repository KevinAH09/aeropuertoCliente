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
import java.util.Arrays;
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
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.una.aeropuertocliente.dtos.ControlesGastosDTO;
import org.una.aeropuertocliente.dtos.DetallesControlesGastosDTO;
import org.una.aeropuertocliente.dtos.ZonasDTO;
import org.una.aeropuertocliente.entitiesServices.ControlGastosService;
import org.una.aeropuertocliente.sharedService.Token;
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
    public List<Node> modDesarrollo = new ArrayList<>();
    public List<String> modDesarrolloAxiliar = new ArrayList<>();
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
        asignarAccionComboboxFiltro();
        notificar(1);
        validarRol();
        llenarListaNodos();
        desarrollo();
    }

    private void validarRol() {
        if (Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_GESTOR")||Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_ADMIN")) {
            actionControlClick();
            btnRegistrar.setVisible(true);
            btnRegistrar.setDisable(false);

        } else {
            btnRegistrar.setVisible(false);
            btnRegistrar.setDisable(true);
        }
    }

    private void asignarAccionComboboxFiltro() {
        cmbFiltro.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (t1 == "Id") {
                    ocultarDatePicker();
                    txtBusqueda.setPromptText("Ingrese el  número correspondiente");
                }
                if (t1 == "Empresa") {
                    ocultarDatePicker();
                    txtBusqueda.setPromptText("Ingrese el nombre de la empresa");
                }
                if (t1 == "Contrato") {
                    ocultarDatePicker();
                    txtBusqueda.setPromptText("Ingrese el número de contrato");
                }
                if (t1 == "Tipo") {
                    ocultarDatePicker();
                    txtBusqueda.setPromptText("Ingrese el tipo de gasto");
                }
                if (t1 == "Estado") {
                    ocultarDatePicker();
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
    }

    private void ocultarDatePicker() {
        fDesde.setVisible(false);
        fHasta.setVisible(false);
        txtBusqueda.setVisible(true);
    }

    @FXML
    private void onActionFiltrar(ActionEvent event) {
        if (txtBusqueda.getText().isEmpty() || cmbFiltro.getValue() == null) {
            limpiarTableView();
            mensaje = "Por favor debe ingresar un datos en el campo de búsqueda";
            notificar(0);
        }

        if (cmbFiltro.getValue() == "Id" && !txtBusqueda.getText().isEmpty()) {
            filtrarPorId();
        }

        if (cmbFiltro.getValue() == "Empresa" && !txtBusqueda.getText().isEmpty()) {
            filtrarPorNombreEmpresa();
        }

        if (cmbFiltro.getValue() == "Contrato" && !txtBusqueda.getText().isEmpty()) {
            filtrarPorContrato();
        }

        if (cmbFiltro.getValue() == "Tipo" && !txtBusqueda.getText().isEmpty()) {
            filtrarPorTipoGasto();
        }

        if (cmbFiltro.getValue() == "Estado" && !txtBusqueda.getText().isEmpty()) {
            filtrarPorEstado();
        }

        if (cmbFiltro.getValue() == "Intervalo Fechas" && fDesde.getValue() != null && fHasta.getValue() != null) {
            filtrarPorRangoFechas();
        }
    }

    private void filtrarPorRangoFechas() {
        limpiarTableView();
        Date ini = Date.from(fDesde.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fina = Date.from(fHasta.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        String sDate1 = new SimpleDateFormat("yyyy-MM-dd").format(ini);
        String sDate2 = new SimpleDateFormat("yyyy-MM-dd").format(fina);

        gastosList = ControlGastosService.fechaControlesGastos(sDate1, sDate2);
        if (gastosList != null) {
            llenarTableView();
            tableGastos.setItems(FXCollections.observableArrayList(gastosList));
        } else {
            mensaje = "No se encontró coincidencias";
            notificar(0);
        }
    }

    private void filtrarPorEstado() {
        limpiarTableView();
        gastosList = ControlGastosService.estadoControlesGastos(txtBusqueda.getText());
        if (gastosList != null) {
            llenarTableView();
            tableGastos.setItems(FXCollections.observableArrayList(gastosList));
        } else {
            mensaje = "No se encontró coincidencias";
            notificar(0);
        }
    }

    private void filtrarPorTipoGasto() {
        limpiarTableView();
        gastosList = ControlGastosService.tipoControlesGastos(txtBusqueda.getText());
        if (gastosList != null) {
            llenarTableView();
            tableGastos.setItems(FXCollections.observableArrayList(gastosList));
        } else {
            mensaje = "No se encontró coincidencias";
            notificar(0);
        }
    }

    private void filtrarPorContrato() {
        limpiarTableView();
        gastosList = ControlGastosService.contratoControlesGastos(txtBusqueda.getText());
        if (gastosList != null) {
            llenarTableView();
            tableGastos.setItems(FXCollections.observableArrayList(gastosList));
        } else {
            mensaje = "No se encontró coincidencias";
            notificar(0);
        }
    }

    private void filtrarPorNombreEmpresa() {
        limpiarTableView();
        gastosList = ControlGastosService.empresaControlesGastos(txtBusqueda.getText());
        if (gastosList != null) {
            llenarTableView();
            tableGastos.setItems(FXCollections.observableArrayList(gastosList));
        } else {
            mensaje = "No se encontró coincidencias";
            notificar(0);
        }
    }

    private void filtrarPorId() throws NumberFormatException {
        limpiarTableView();
        gastosFilt = ControlGastosService.idControlGasto(Long.valueOf(txtBusqueda.getText()));
        if (gastosFilt != null) {
            llenarTableView();
            tableGastos.setItems(FXCollections.observableArrayList(gastosFilt));
        } else {
            mensaje = "No se encontró coincidencias";
            notificar(0);
        }
    }

    @FXML
    private void onActionRegistrar(ActionEvent event) {
        AppContext.getInstance().set("control", null);
        PrincipalController.cambiarVistaPrincipal("mantenimientoControlGastos/MantenimientoControlGastos");
    }

    private void llenarTableView() {
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
        if (Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_GESTOR")) {
            agregarBtnTableView();
        }
    }

    private void actionControlClick() {
        tableGastos.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2 && tableGastos.selectionModelProperty().get().getSelectedItem() != null) {
                    ControlesGastosDTO control = (ControlesGastosDTO) tableGastos.selectionModelProperty().get().getSelectedItem();
                    AppContext.getInstance().set("control", control);
                    PrincipalController.cambiarVistaPrincipal("mantenimientoControlGastos/MantenimientoControlGastos");
                }

            }
        });
    }

    private void notificar(int num) {
        limpiarTableView();
        if (num == 1) {
            alertar1();
        } else {
            alertar2();
        }
    }

    private void alertar2() {
        ImageView imageView2 = new ImageView(new Image("org/una/aeropuertocliente/views/shared/warning.png"));
        Text lab = new Text(mensaje);
        lab.setFill(Color.web("#0076a3"));
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(imageView2);
        box.getChildren().add(lab);
        tableGastos.setPlaceholder(box);
    }

    private void alertar1() {
        ImageView imageView = new ImageView(new Image("org/una/aeropuertocliente/views/shared/info.png"));
        Text lab = new Text("Para mostrar datos en este apartado debe realizar el filtro correspondiente");
        lab.setFill(Color.web("#0076a3"));
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(imageView);
        box.getChildren().add(lab);
        tableGastos.setPlaceholder(box);
    }

    private void limpiarTableView() {
        tableGastos.getItems().clear();
        tableGastos.getColumns().clear();
    }

    private void agregarBtnTableView() {
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
                        btn.setId("btnEditar");
                        btn.setText("Editar");
                        modDesarrolloAxiliar.add("Editar");
                        modDesarrollo.add(btn);
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

    public void llenarListaNodos() {
        modDesarrollo.clear();
        modDesarrolloAxiliar.clear();
        modDesarrolloAxiliar.add(titulo.getText());
        modDesarrolloAxiliar.add(lblTable.getText());
        modDesarrolloAxiliar.add(cmbFiltro.getPromptText());
        modDesarrolloAxiliar.add(txtBusqueda.getPromptText());
        modDesarrolloAxiliar.add(btnFiltrar.getText());
        modDesarrolloAxiliar.add(btnRegistrar.getText());
        modDesarrolloAxiliar.add(fDesde.getPromptText());
        modDesarrolloAxiliar.add(fHasta.getPromptText());
        modDesarrollo.addAll(Arrays.asList(titulo, lblTable, cmbFiltro, txtBusqueda, btnFiltrar, btnRegistrar, fDesde, fHasta));
    }

    public void desarrollo() {
        String dato = "";
        boolean validos1 = (Boolean) AppContext.getInstance().get("mod");
        if (validos1) {
            validarBooleanoTrue();
        } else {
            validarBooleanoFalse();
        }
    }

    private void validarBooleanoFalse() {
        String dato;
        for (int i = 0; i < modDesarrollo.size(); i++) {
            if (modDesarrollo.get(i) instanceof JFXButton) {
                dato = modDesarrolloAxiliar.get(i);
                ((JFXButton) modDesarrollo.get(i)).setText(dato);
            }
            if (modDesarrollo.get(i) instanceof JFXTextField) {
                dato = modDesarrolloAxiliar.get(i);
                ((JFXTextField) modDesarrollo.get(i)).setPromptText(dato);
            }
            if (modDesarrollo.get(i) instanceof JFXComboBox) {
                dato = modDesarrolloAxiliar.get(i);
                ((JFXComboBox) modDesarrollo.get(i)).setPromptText(dato);
            }
            if (modDesarrollo.get(i) instanceof JFXDatePicker) {
                dato = modDesarrolloAxiliar.get(i);
                ((JFXDatePicker) modDesarrollo.get(i)).setPromptText(dato);
            }
            if (modDesarrollo.get(i) instanceof Label) {
                dato = modDesarrolloAxiliar.get(i);
                ((Label) modDesarrollo.get(i)).setText(dato);
            }
        }
    }

    private void validarBooleanoTrue() {
        String dato;
        for (Node node : modDesarrollo) {
            if (node instanceof JFXTextField) {
                dato = ((JFXTextField) node).getId();
                ((JFXTextField) node).setPromptText(dato);
            }
            if (node instanceof JFXButton) {
                dato = ((JFXButton) node).getId();
                ((JFXButton) node).setText(dato);
            }
            if (node instanceof JFXComboBox) {
                dato = ((JFXComboBox) node).getId();
                ((JFXComboBox) node).setPromptText(dato);
            }
            if (node instanceof JFXDatePicker) {
                dato = ((JFXDatePicker) node).getId();
                ((JFXDatePicker) node).setPromptText(dato);
            }
            if (node instanceof Label) {
                if (node == lblTable) {
                    dato = tableGastos.getId();
                    ((Label) node).setText(dato);
                } else {
                    dato = ((Label) node).getId();
                    ((Label) node).setText(dato);
                }
            }
        }
    }

    @Override
    public void initialize() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void modoDesarrollo(KeyEvent event) {
        KeyCombination cntrlD = new KeyCodeCombination(KeyCode.D, KeyCodeCombination.CONTROL_DOWN);
        if (Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_ADMIN")) {
            if (cntrlD.match(event)) {
                boolean validos1 = (Boolean) AppContext.getInstance().get("mod");
                if (validos1) {
                    AppContext.getInstance().set("mod", false);
                    desarrollo();
                } else {
                    AppContext.getInstance().set("mod", true);
                    desarrollo();
                }
            }
        }
    }

}
