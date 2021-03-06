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
import java.util.Arrays;
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
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
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
    public List<Node> modDesarrollo = new ArrayList<>();
    public List<String> modDesarrolloAxiliar = new ArrayList<>();
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

        notificar(1);
        cmbFiltro.setItems(FXCollections.observableArrayList("Id", "Estado", "Nombre", "Código"));
        cmbEstado.setItems(FXCollections.observableArrayList("Activo", "Inactivo"));
        asignarAccionComboboxFiltro();
        llenarListaNodos();
        desarrollo();
        if (Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_GESTOR") && Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_ADMIN")) {
            actionZonasClick();
        }
    }

    private void asignarAccionComboboxFiltro() {
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
        if (txtBusqueda.getText().isEmpty() || cmbFiltro.getValue() == null || cmbEstado.getValue() == null) {
            limpiarTableView();
            mensaje = "Campos vacíos en el apartado de búsqueda";
            notificar(0);
        }
        if (cmbFiltro.getValue() == "Id" && !txtBusqueda.getText().isEmpty()) {
            filtrarPorId();
        }
        if (cmbFiltro.getValue() == "Estado" && !cmbEstado.getValue().isEmpty()) {
            filtrarPorEstado();
        }
        if (cmbFiltro.getValue() == "Nombre" && !txtBusqueda.getText().isEmpty()) {
            filtrarPorNombre();
        }
        if (cmbFiltro.getValue() == "Código" && !txtBusqueda.getText().isEmpty()) {
            filtrarPorCodigo();
        }
    }

    private void filtrarPorCodigo() {
        zonasList = ZonasService.codigoZona(txtBusqueda.getText());
        if (zonasList != null) {
            limpiarTableView();
            llenarTableView();
            tableZonas.setItems(FXCollections.observableArrayList(zonasList));
        } else {
            mensaje = "No se encontró coincidencias";
            notificar(0);
        }
    }

    private void filtrarPorNombre() {
        zonasList = ZonasService.nombreZona(txtBusqueda.getText());
        if (zonasList != null) {
            limpiarTableView();
            llenarTableView();
            tableZonas.setItems(FXCollections.observableArrayList(zonasList));
        } else {
            mensaje = "No se encontró coincidencias";
            notificar(0);
        }
    }

    private void filtrarPorEstado() {
        if (cmbEstado.getValue().equals("Activo")) {
            zonasList = ZonasService.estadoZona(true);
            if (zonasList != null) {
                limpiarTableView();
                llenarTableView();
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
                llenarTableView();
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

    private void filtrarPorId() throws NumberFormatException {
        zonasFilt = ZonasService.idZona(Long.valueOf(txtBusqueda.getText()));
        if (zonasFilt != null) {
            limpiarTableView();
            llenarTableView();
            tableZonas.setItems(FXCollections.observableArrayList(zonasFilt));
        } else {
            mensaje = "No se encontró coincidencias";
            notificar(0);
        }
    }

    private void llenarTableView() {
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
        if (Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_GESTOR") || Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_ADMIN")) {
            agregarBtnTableView();
        }

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
        tableZonas.setPlaceholder(box);
    }

    private void alertar1() {
        ImageView imageView = new ImageView(new Image("org/una/aeropuertocliente/views/shared/info.png"));
        Text lab = new Text("Para mostrar datos en este apartado debe realizar el filtro correspondiente");
        lab.setFill(Color.web("#0076a3"));
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(imageView);
        box.getChildren().add(lab);
        tableZonas.setPlaceholder(box);
    }

    private void limpiarTableView() {
        tableZonas.getItems().clear();
        tableZonas.getColumns().clear();
    }

    private void agregarBtnTableView() {
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
                        btn.setId("btnSeleccionar");
                        btn.setText("Seleccionar");
                        modDesarrolloAxiliar.add("Seleccionar");
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

        tableZonas.getColumns().add(colBtn);

    }

    public void llenarListaNodos() {
        modDesarrollo.clear();
        modDesarrolloAxiliar.clear();
        modDesarrolloAxiliar.add(titulo.getText());
        modDesarrolloAxiliar.add(lblTable.getText());
        modDesarrolloAxiliar.add(txtBusqueda.getPromptText());
        modDesarrolloAxiliar.add(cmbFiltro.getPromptText());
        modDesarrolloAxiliar.add(btnFiltrar.getText());
        modDesarrolloAxiliar.add(cmbEstado.getPromptText());
        modDesarrollo.addAll(Arrays.asList(titulo, lblTable, txtBusqueda, cmbFiltro, btnFiltrar, cmbEstado));
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
            if (node instanceof Label) {
                if (node == lblTable) {
                    dato = tableZonas.getId();
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
