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
import javafx.stage.Stage;
import javafx.util.Callback;
import org.una.aeropuertocliente.dtos.ParametrosDTO;
import org.una.aeropuertocliente.dtos.RegistrosAccionesDTO;
import org.una.aeropuertocliente.dtos.ZonasDTO;
import org.una.aeropuertocliente.entitiesServices.ParametrosService;
import org.una.aeropuertocliente.entitiesServices.RegistrosAccionesService;
import org.una.aeropuertocliente.sharedService.Token;
import org.una.aeropuertocliente.utils.AppContext;
import org.una.aeropuertocliente.utils.Mensaje;

/**
 * FXML Controller class
 *
 * @author cfugu
 */
public class ParametrosController extends Controller implements Initializable {

    @FXML
    private Label lblZonas;
    @FXML
    private Label titulo;
    @FXML
    private Label lbltxtNombre;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private Label lbltxtCodigo;
    @FXML
    private JFXTextField txtValor;
    @FXML
    private Label lblcmbEstado;
    @FXML
    private JFXComboBox<String> cmbEstado;
    @FXML
    private Label lbltxtDescripcion;
    @FXML
    private JFXTextField txtDescripcion;
    @FXML
    private Label lblCancelar;
    @FXML
    private JFXButton btnRegistrar;
    @FXML
    private Label lblGuardar;
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
    private TableView<ParametrosDTO> tableParametros;
    ParametrosDTO parametros;
    ParametrosDTO parametrosFilt;
    public List<ParametrosDTO> parametrosList = new ArrayList<ParametrosDTO>();
    private List<Node> requeridos = new ArrayList<>();
    String mensaje;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private Label lblcbEstado;
    @FXML
    private JFXComboBox<String> cmbEstado2;
    public List<Node> modDesarrollo = new ArrayList<>();
    public List<String> modDesarrolloAxiliar = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbEstado.setItems(FXCollections.observableArrayList("Activo", "Inactivo"));
        cmbEstado2.setItems(FXCollections.observableArrayList("Activo", "Inactivo"));
        cmbFiltro.setItems(FXCollections.observableArrayList("Id", "Estado", "Nombre"));
        actionParametrosClick();
        notificar(1);
        indicarRequeridos();

        asignarAccionComboboxFiltro();
        llenarListaNodos();
        desarrollo();
    }

    private void asignarAccionComboboxFiltro() {
        cmbFiltro.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (t1 == "Id") {
                    cmbEstado2.setVisible(false);
                    txtBusqueda.setVisible(true);
                    txtBusqueda.setPromptText("Ingrese el  número correspondiente");
                }
                if (t1 == "Nombre") {
                    cmbEstado2.setVisible(false);
                    txtBusqueda.setVisible(true);
                    txtBusqueda.setPromptText("Ingrese el nombre de la zona");
                }
                if (t1 == "Estado") {
                    cmbEstado2.setVisible(true);
                    txtBusqueda.setVisible(false);

                }
            }

        }
        );
    }

    @FXML
    private void onActionCancelar(ActionEvent event) {
        txtNombre.clear();
        txtValor.clear();
        txtDescripcion.clear();
        parametros = null;
    }

    @FXML
    private void onActionGuardar(ActionEvent event) {
        String validacion = validarRequeridos();
        if (parametros == null) {
            guardarNuevoParametro(validacion);

        } else {
            guardarEdicionParametro(validacion);
        }
        tableParametros.getItems().clear();
        parametrosList = ParametrosService.allParametros();
        tableParametros.setItems(FXCollections.observableArrayList(parametrosList));
    }

    private void guardarEdicionParametro(String validacion) {
        if (validacion == null) {
            if (cmbEstado.getValue().equals("Activo")) {
                parametros.setEstado(true);
            } else {
                parametros.setEstado(false);
            }
            parametros.setDescripcion(txtDescripcion.getText());
            parametros.setNombreParametro(txtNombre.getText());
            parametros.setValor(txtValor.getText());
            if (ParametrosService.updateParametro(parametros) == 200) {
                RegistrosAccionesService.createRegistroAccion(new RegistrosAccionesDTO(Token.getInstance().getUsuario(), "Edito parametro " + parametros.getId(), new Date()));
                new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Guardar parametro", ((Stage) txtNombre.getScene().getWindow()), "Se guardó correctamente");
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error al guardar el parametro", ((Stage) txtNombre.getScene().getWindow()), "No se guardó correctamente");
            }
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error al crear el parametro", ((Stage) txtNombre.getScene().getWindow()), validacion);
        }
    }

    private void guardarNuevoParametro(String validacion) {
        if (validacion == null) {
            parametros = new ParametrosDTO();
            if (cmbEstado.getValue().equals("Activo")) {
                parametros.setEstado(true);
            } else {
                parametros.setEstado(false);
            }
            parametros.setDescripcion(txtDescripcion.getText());
            parametros.setNombreParametro(txtNombre.getText());
            parametros.setValor(txtValor.getText());
            if (ParametrosService.createParametro(parametros) == 201) {
                RegistrosAccionesService.createRegistroAccion(new RegistrosAccionesDTO(Token.getInstance().getUsuario(), "Creo nuevo parametro", new Date()));
                new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Guardar parametro", ((Stage) txtNombre.getScene().getWindow()), "Se guardó correctamente");
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error al guardar el parametro", ((Stage) txtNombre.getScene().getWindow()), "No se guardó correctamente");
            }
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error al crear el parametro", ((Stage) txtNombre.getScene().getWindow()), validacion);
        }
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
        tableParametros.setPlaceholder(box);
    }

    private void alertar1() {
        ImageView imageView = new ImageView(new Image("org/una/aeropuertocliente/views/shared/info.png"));
        Text lab = new Text("Para mostrar datos en este apartado debe realizar el filtro correspondiente");
        lab.setFill(Color.web("#0076a3"));
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(imageView);
        box.getChildren().add(lab);
        tableParametros.setPlaceholder(box);
    }

    @FXML
    private void onActionFiltrar(ActionEvent event) {
        if (txtBusqueda.getText().isEmpty() || cmbFiltro.getValue() == null || cmbEstado2.getValue() == null) {
            limpiarTableView();
            mensaje = "Por favor debe ingresar un datos en el campo de búsqueda";
            notificar(0);
        }

        if (cmbFiltro.getValue() == "Id" && !txtBusqueda.getText().isEmpty()) {
            filtrarPorId();
        }

        if (cmbFiltro.getValue() == "Estado" && cmbEstado2.getValue() != null) {
            filtrarPorEstado();
        }

        if (cmbFiltro.getValue() == "Nombre" && !txtBusqueda.getText().isEmpty()) {
            filtrarPorNombre();
        }
    }

    private void filtrarPorNombre() {
        limpiarTableView();
        parametrosList = new ArrayList<>();
        parametrosList.add(ParametrosService.nombreParametros(txtBusqueda.getText()));
        if (parametrosList != null && parametrosList.isEmpty()) {
            llenarTableView();
            tableParametros.setItems(FXCollections.observableArrayList(parametrosFilt));
        } else {
            mensaje = "No se encontró coincidencias";
            notificar(0);
        }
    }

    private void filtrarPorEstado() {
        if (cmbEstado2.getValue().equals("Activo")) {
            limpiarTableView();
            parametrosList = ParametrosService.estadoParametros(true);
            if (parametrosList != null) {
                llenarTableView();
                tableParametros.setItems(FXCollections.observableArrayList(parametrosList));
            } else {
                mensaje = "No se encontró coincidencias";
                notificar(0);
            }
        }
        if (cmbEstado2.getValue().equals("Inactivo")) {
            limpiarTableView();
            parametrosList = ParametrosService.estadoParametros(false);
            if (parametrosList != null) {
                llenarTableView();
                tableParametros.setItems(FXCollections.observableArrayList(parametrosList));
            } else {
                mensaje = "No se encontró coincidencias";
                notificar(0);
            }
        }
        if (parametrosList == null) {
            mensaje = "No se encontró coincidencias";
            notificar(0);
        }
    }

    private void filtrarPorId() throws NumberFormatException {
        limpiarTableView();
        parametrosFilt = ParametrosService.idParametro(Long.valueOf(txtBusqueda.getText()));
        if (parametrosFilt != null) {
            llenarTableView();
            tableParametros.setItems(FXCollections.observableArrayList(parametrosFilt));
        } else {
            mensaje = "No se encontró coincidencias";
            notificar(0);
        }
    }

    private void actionParametrosClick() {
        parametros = null;
        tableParametros.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2 && tableParametros.selectionModelProperty().get().getSelectedItem() != null) {
                    parametros = (ParametrosDTO) tableParametros.selectionModelProperty().get().getSelectedItem();
                    System.out.println(parametros.getNombreParametro());
                    editar();
                }

            }
        });
    }

    private void llenarTableView() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        TableColumn<ParametrosDTO, String> colId = new TableColumn("Id");
        colId.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getId().toString()));
        TableColumn<ParametrosDTO, String> colNombre = new TableColumn("Nombre");
        colNombre.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getNombreParametro()));
        TableColumn<ParametrosDTO, String> colEstado = new TableColumn("Estado");
        colEstado.setCellValueFactory((param) -> {
            if (param.getValue().isEstado()) {
                return new SimpleStringProperty("Activo");
            }
            return new SimpleStringProperty("Inactivo");
        });
        TableColumn<ParametrosDTO, String> colCodigo = new TableColumn("Valor Parametro");
        colCodigo.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getValor()));
        TableColumn<ParametrosDTO, String> colDescripcion = new TableColumn("Descripcion");
        colDescripcion.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getDescripcion()));
        TableColumn<ParametrosDTO, String> colFecha = new TableColumn("Fecha registro");
        colFecha.setCellValueFactory((param) -> new SimpleObjectProperty(formatter.format(param.getValue().getFechaRegistro())));
        tableParametros.getColumns().addAll(colId, colNombre, colEstado, colCodigo, colDescripcion, colFecha);
        agregarBtnTableView();
    }

    private void editar() {
        if (parametros != null) {
            txtNombre.setText(parametros.getNombreParametro());
            txtValor.setText(parametros.getValor());
            txtDescripcion.setText(parametros.getDescripcion());
            if (parametros.isEstado()) {
                cmbEstado.setValue("Activo");
            } else {
                cmbEstado.setValue("Inactivo");
            }

        }
    }

    private void agregarBtnTableView() {
        TableColumn<ParametrosDTO, Void> colBtn = new TableColumn("Acción");

        Callback<TableColumn<ParametrosDTO, Void>, TableCell<ParametrosDTO, Void>> cellFactory = new Callback<TableColumn<ParametrosDTO, Void>, TableCell<ParametrosDTO, Void>>() {
            @Override
            public TableCell<ParametrosDTO, Void> call(final TableColumn<ParametrosDTO, Void> param) {
                final TableCell<ParametrosDTO, Void> cell = new TableCell<ParametrosDTO, Void>() {

                    private final JFXButton btn = new JFXButton("Editar");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            parametros = getTableView().getItems().get(getIndex());
                            editar();
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

        tableParametros.getColumns().add(colBtn);

    }

    private void limpiarTableView() {
        tableParametros.getItems().clear();
        tableParametros.getColumns().clear();
    }

    public void indicarRequeridos() {
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txtDescripcion, txtNombre, cmbEstado, txtValor));
    }

    public String validarRequeridos() {
        Boolean validos = true;
        String invalidos = "";
        for (Node node : requeridos) {
            if (node instanceof JFXTextField && (((JFXTextField) node).getText() == null || ((JFXTextField) node).getText().isEmpty())) {
                if (validos) {
                    invalidos += ((JFXTextField) node).getPromptText();
                } else {
                    invalidos += "," + ((JFXTextField) node).getPromptText();
                }
                validos = false;
            } else if (node instanceof JFXComboBox && (((JFXComboBox) node).getValue() == null)) {
                if (validos) {
                    invalidos += ((JFXComboBox) node).getPromptText();
                } else {
                    invalidos += "," + ((JFXComboBox) node).getPromptText();
                }
                validos = false;
            }
        }
        if (validos) {
            return null;
        } else {
            return "Los siguientes campos son requeridos " + "[" + invalidos + "].";
        }
    }

    public void llenarListaNodos() {
        modDesarrollo.clear();
        modDesarrolloAxiliar.clear();
        modDesarrolloAxiliar.add(titulo.getText());
        modDesarrolloAxiliar.add(lblZonas.getText());
        modDesarrolloAxiliar.add(txtNombre.getPromptText());
        modDesarrolloAxiliar.add(txtValor.getPromptText());
        modDesarrolloAxiliar.add(cmbEstado.getPromptText());
        modDesarrolloAxiliar.add(txtDescripcion.getPromptText());
        modDesarrolloAxiliar.add(txtBusqueda.getPromptText());
        modDesarrolloAxiliar.add(cmbFiltro.getPromptText());
        modDesarrolloAxiliar.add(cmbEstado2.getPromptText());
        modDesarrolloAxiliar.add(btnFiltrar.getText());
        modDesarrolloAxiliar.add(btnCancelar.getText());
        modDesarrolloAxiliar.add(btnRegistrar.getText());
        modDesarrollo.addAll(Arrays.asList(titulo, txtNombre, cmbEstado, txtDescripcion, txtBusqueda,
                cmbFiltro, cmbEstado2, btnFiltrar, btnCancelar, btnRegistrar));
    }

    public void desarrollo() {
        boolean validos1 = (Boolean) AppContext.getInstance().get("mod");
        if (validos1) {
            validarBooleanoTrue();
        } else {
            validarBooleanoFalse();
        }
    }

    private void validarBooleanoFalse() {
        String dato = "";
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
        String dato = "";
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
                    dato = tableParametros.getId();
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
