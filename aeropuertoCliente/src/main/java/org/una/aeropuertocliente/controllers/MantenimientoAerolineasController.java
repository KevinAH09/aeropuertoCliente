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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.una.aeropuertocliente.dtos.AerolineasDTO;
import org.una.aeropuertocliente.dtos.AvionesDTO;
import org.una.aeropuertocliente.dtos.RegistrosAccionesDTO;
import org.una.aeropuertocliente.entitiesServices.AerolineasService;
import org.una.aeropuertocliente.entitiesServices.AvionesService;
import org.una.aeropuertocliente.entitiesServices.RegistrosAccionesService;
import org.una.aeropuertocliente.sharedService.Token;
import org.una.aeropuertocliente.utils.AppContext;
import org.una.aeropuertocliente.utils.Mensaje;

/**
 * FXML Controller class
 *
 * @author cfugu
 */
public class MantenimientoAerolineasController extends Controller implements Initializable {

    @FXML
    private JFXTextField txtId;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextField txtResponsable;
    @FXML
    private JFXComboBox<String> cmbEstado;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private TableView<AvionesDTO> tableAviones;
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lbltxtId;
    @FXML
    private Label lbltxtNombre;
    @FXML
    private Label titulo;
    @FXML
    private Label lblTxtResponsable;
    @FXML
    private Label lblCmbEstado;
    @FXML
    private Label lblGuardar;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private Label lblEditar;
    @FXML
    private Label lblTable;
    @FXML
    private Label lblRegistrar;
    @FXML
    private JFXButton btnRegistrar;
    AerolineasDTO aerolinea;
    AvionesDTO aviones;
    AvionesDTO avionesFil;
    public List<AvionesDTO> avionesList = new ArrayList<AvionesDTO>();
    public List<AvionesDTO> avionesList2 = new ArrayList<AvionesDTO>();
    public List<Node> modDesarrollo = new ArrayList<>();
    public List<String> modDesarrolloAxiliar = new ArrayList<>();
    @FXML
    private Label labTxtFild;
    @FXML
    private JFXTextField txtFilter;
    @FXML
    private Label labComb;
    @FXML
    private JFXComboBox<String> combFilter;
    @FXML
    private Label labBtnFiltrar;
    @FXML
    private JFXButton btnFiltrar;
    @FXML
    private Label lblVolver;
    @FXML
    private JFXButton btnVolverAerolinea;
    @FXML
    private Label labTituloAviones;
    String mensaje;
    public AvionesDTO data = new AvionesDTO();
    private List<Node> requeridos = new ArrayList<>();
    @FXML
    private JFXComboBox<String> cmbEstado2;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        aerolinea = (AerolineasDTO) AppContext.getInstance().get("aerolinea");
        combFilter.setItems(FXCollections.observableArrayList("Id", "Matrícula", "Tipo de avión", "Estado", "Nombre aerolinea"));
        cmbEstado.setItems(FXCollections.observableArrayList("Activo", "Inactivo"));
        cmbEstado2.setItems(FXCollections.observableArrayList("Activo", "Inactivo"));
        asignarAccionComboboxFiltro();
        if (aerolinea != null) {
            actionAvionClick();
            llenarAviones();
            if (aerolinea.isEstado()) {
                cmbEstado.setValue("Activo");
            } else {
                cmbEstado.setValue("Inactivo");
            }
            txtNombre.setDisable(true);
            txtId.setDisable(true);
            txtResponsable.setDisable(true);
            cmbEstado.setDisable(true);
            txtNombre.setText(aerolinea.getNombreAerolinea());
            txtId.setText(aerolinea.getId().toString());
            txtResponsable.setText(aerolinea.getNombreResponsable());
            if (AvionesService.aerolinea(aerolinea.getId()).isEmpty()) {
                txtFilter.setVisible(false);
                combFilter.setVisible(false);
                btnFiltrar.setVisible(false);
                btnCancelar.setVisible(false);
                notificar(2);
            }
            btnCancelar.setVisible(false);
            btnEditar.setVisible(true);
            btnGuardar.setDisable(true);

        } else {
            txtNombre.setText("");
            txtId.setText("");
            txtResponsable.setText("");
            cmbEstado.setValue("Activo");

            txtFilter.setVisible(false);
            combFilter.setVisible(false);
            btnFiltrar.setVisible(false);
            btnCancelar.setVisible(false);

            notificar(2);
            txtNombre.setDisable(false);
            txtId.setDisable(true);
            txtResponsable.setDisable(false);
            cmbEstado.setDisable(true);
            btnRegistrar.setDisable(true);
            btnCancelar.setVisible(false);
            btnEditar.setDisable(true);
            btnGuardar.setVisible(true);

            tableAviones.getItems().clear();
        }
        indicarRequeridos();
        llenarListaNodos();
        desarrollo();
    

    if (!Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_ADMIN")) {
        btnEditar.setVisible(true);
        btnEditar.setDisable(false);
        btnRegistrar.setDisable(false);
        btnRegistrar.setVisible(true);
    }else {
        btnEditar.setVisible(true);
        btnEditar.setDisable(true);
        btnRegistrar.setDisable(true);
        btnRegistrar.setVisible(true);
    }
}

    private void asignarAccionComboboxFiltro() {
        combFilter.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (t1 == "Id") {
                    cmbEstado2.setVisible(false);
                    txtFilter.setVisible(true);
                    txtFilter.setPromptText("Ingrese número correspondiente");
                }
                if (t1 == "Matrícula") {
                    cmbEstado2.setVisible(false);
                    txtFilter.setVisible(true);
                    txtFilter.setPromptText("Ingrese matrícula del avión");
                }
                if (t1 == "Tipo de avión") {
                    cmbEstado2.setVisible(false);
                    txtFilter.setVisible(true);
                    txtFilter.setPromptText("Ingrese el tipo de avión");
                }
                if (t1 == "Estado") {
                    cmbEstado2.setVisible(true);
                    txtFilter.setVisible(false);
                }
                if (t1 == "Nombre aerolinea") {
                    cmbEstado2.setVisible(false);
                    txtFilter.setVisible(true);
                    txtFilter.setPromptText("Ingrese nombre de la aerolinea");
                }
            }

        }
        );
    }

    private void actionAvionClick() {
        tableAviones.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2 && tableAviones.selectionModelProperty().get().getSelectedItem() != null) {
                    aviones = (AvionesDTO) tableAviones.selectionModelProperty().get().getSelectedItem();
                    AppContext.getInstance().set("agregarAvion", aviones);
                    PrincipalController.cambiarVistaPrincipal("mantenimientoAviones/MantenimientoAvion");
                }
            }
        });
    }

    private void llenarAviones() {
        TableColumn<AvionesDTO, String> colId = new TableColumn("Id");
        colId.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getId().toString()));
        TableColumn<AvionesDTO, String> colMatricula = new TableColumn("Matrícula");
        colMatricula.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getMatricula()));
        TableColumn<AvionesDTO, String> colTipoAvion = new TableColumn("Tipo de avión");
        colTipoAvion.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getTipoAvion()));
        TableColumn<AvionesDTO, String> colHoraVuelo = new TableColumn("Horas de vuelo");
        colHoraVuelo.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getHorasVuelo()));
        TableColumn<AvionesDTO, String> colEstado = new TableColumn("Estado\nActivo Inactivo");
        colEstado.setCellValueFactory((param) -> {
            if (param.getValue().isEstado()) {
                return new SimpleStringProperty("Activo");
            }
            return new SimpleStringProperty("Inactivo");
        });
        TableColumn<AvionesDTO, String> colAerolinea = new TableColumn("Nombre aerolinea");
        colAerolinea.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getAerolineaId().getNombreAerolinea()));
        tableAviones.getColumns().addAll(colId, colMatricula, colTipoAvion, colHoraVuelo, colEstado, colAerolinea);
        addButtonToTable();
        notificar(1);
    }

    @FXML
    private void onActionGuardar(ActionEvent event) {
        String validacion = validarRequeridos();
        if (aerolinea == null) {
            if (validacion == null) {

                aerolinea = new AerolineasDTO();
                if (cmbEstado.getValue().equals("Activo")) {
                    aerolinea.setEstado(true);
                } else {
                    aerolinea.setEstado(false);
                }
                aerolinea.setNombreResponsable(txtResponsable.getText());
                aerolinea.setNombreAerolinea(txtNombre.getText());
                if (AerolineasService.createAerolinea(aerolinea) == 201) {
                    RegistrosAccionesService.createRegistroAccion(new RegistrosAccionesDTO(Token.getInstance().getUsuario(), "Creo nueva aerolinea", new Date()));
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar Aerolinea", ((Stage) txtNombre.getScene().getWindow()), "Se guardó correctamente");
                    PrincipalController.cambiarVistaPrincipal("aerolineas/Aerolineas");
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error al guardar la Aerolinea", ((Stage) txtNombre.getScene().getWindow()), "No se guardó correctamente");
                }

            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error al crear los datos", ((Stage) txtNombre.getScene().getWindow()), validacion);
            }

        } else {
            if (validacion == null) {
                if (cmbEstado.getValue().equals("Activo")) {
                    aerolinea.setEstado(true);
                } else {
                    aerolinea.setEstado(false);
                }
                aerolinea.setNombreResponsable(txtResponsable.getText());
                aerolinea.setNombreAerolinea(txtNombre.getText());
                if (AerolineasService.updateAerolinea(aerolinea) == 200) {
                    RegistrosAccionesService.createRegistroAccion(new RegistrosAccionesDTO(Token.getInstance().getUsuario(), "Edito aerolinea " + aerolinea.getId(), new Date()));
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Editar Aerolinea", ((Stage) txtNombre.getScene().getWindow()), "Se editó correctamente");
                    btnEditar.setDisable(false);
                    btnGuardar.setDisable(true);
                    btnRegistrar.setDisable(false);
                    btnVolverAerolinea.setDisable(false);
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error al editar la Aerolinea", ((Stage) txtNombre.getScene().getWindow()), "No se editó correctamente");
                }
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error al crear los datos", ((Stage) txtNombre.getScene().getWindow()), validacion);
            }
        }
    }

    @FXML
    private void onActionCancelar(ActionEvent event) {
    }

    @FXML
    private void onActionEditar(ActionEvent event) {
        txtNombre.setDisable(false);
        txtId.setDisable(true);
        txtResponsable.setDisable(false);
        cmbEstado.setDisable(false);
        btnEditar.setDisable(true);
        btnGuardar.setDisable(false);
        btnRegistrar.setDisable(true);
        btnVolverAerolinea.setDisable(true);

    }

    @FXML
    private void onActionRegistrar(ActionEvent event) {
        aviones = null;
        AppContext.getInstance().set("agregarAvion", aviones);
        PrincipalController.cambiarVistaPrincipal("mantenimientoAviones/MantenimientoAvion");
    }

    @Override
    public void initialize() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void addButtonToTable() {
        TableColumn<AvionesDTO, Void> colBtn = new TableColumn("Acción");

        Callback<TableColumn<AvionesDTO, Void>, TableCell<AvionesDTO, Void>> cellFactory = new Callback<TableColumn<AvionesDTO, Void>, TableCell<AvionesDTO, Void>>() {
            @Override
            public TableCell<AvionesDTO, Void> call(final TableColumn<AvionesDTO, Void> param) {
                final TableCell<AvionesDTO, Void> cell = new TableCell<AvionesDTO, Void>() {

                    private final JFXButton btn = new JFXButton("Seleccionar");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            data = getTableView().getItems().get(getIndex());
                            AppContext.getInstance().set("agregarAvion", data);
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

        tableAviones.getColumns().add(colBtn);

    }

    @FXML
    private void filtrar(ActionEvent event) {
        if (combFilter.getValue() == null || txtFilter.getText().isEmpty()) {
            mensaje = "Por favor debe ingresar un datos en el campo de búsqueda";
            notificar(0);
        } else {

            if (combFilter.getValue() == "Id" && !txtFilter.getText().isEmpty()) {
                filtrarPorId();
            }
            if (combFilter.getValue() == "Estado" && !txtFilter.getText().isEmpty()) {
                filtrarPorEstado();
            }
            if (combFilter.getValue() == "Matrícula" && !txtFilter.getText().isEmpty()) {
                filtrarPorMatricula();
            }
            if (combFilter.getValue() == "Tipo de avión" && !txtFilter.getText().isEmpty()) {
                filtrarPorTipoAvion();
            }
            if (combFilter.getValue() == "Nombre aerolinea" && !txtFilter.getText().isEmpty()) {
                Aerolinea();
            }
        }
    }

    private void Aerolinea() {
        tableAviones.getItems().clear();
        avionesList = AvionesService.allAviones();
        for (int i = 0; i < avionesList.size(); i++) {
            if (avionesList.get(i).getAerolineaId().getNombreAerolinea().equals(txtFilter.getText())) {
                avionesList2 = AvionesService.aerolinea(avionesList.get(i).getAerolineaId().getId());
            }

        }
        if (!avionesList2.isEmpty()) {
            tableAviones.setItems(FXCollections.observableArrayList(avionesList2));
        } else {
            mensaje = "No se encontró coincidencias";
            notificar(0);
        }
    }

    private void filtrarPorTipoAvion() {
        tableAviones.getItems().clear();
        avionesList = AvionesService.TipoAvion(txtFilter.getText());
        if (avionesList != null) {
            tableAviones.setItems(FXCollections.observableArrayList(avionesList));
        } else {
            mensaje = "No se encontró coincidencias";
            notificar(0);
        }
    }

    private void filtrarPorMatricula() {
        tableAviones.getItems().clear();
        avionesList = AvionesService.matricula(txtFilter.getText());
        if (avionesList != null) {
            tableAviones.setItems(FXCollections.observableArrayList(avionesList));
        } else {
            mensaje = "No se encontró coincidencias";
            notificar(0);
        }
    }

    private void filtrarPorEstado() {
        if (cmbEstado2.getValue() == "Activo") {
            tableAviones.getItems().clear();
            avionesList = AvionesService.estado(true);
            if (avionesList != null) {
                tableAviones.setItems(FXCollections.observableArrayList(avionesList));
            } else {
                mensaje = "No se encontró coincidencias";
                notificar(0);
            }
        }
        if (cmbEstado2.getValue() == "Inactivo") {
            tableAviones.getItems().clear();
            avionesList = AvionesService.estado(false);
            if (avionesList != null) {
                tableAviones.setItems(FXCollections.observableArrayList(avionesList));
            } else {
                mensaje = "No se encontró coincidencias";
                notificar(0);
            }
        }
        if (avionesList == null) {
            mensaje = "No se encontró coincidencias";
            notificar(0);
        }
    }

    private void filtrarPorId() throws NumberFormatException {
        tableAviones.getItems().clear();
        avionesFil = AvionesService.idAvion(Long.valueOf(txtFilter.getText()));

        if (avionesFil != null) {
            tableAviones.setItems(FXCollections.observableArrayList(avionesFil));
        } else {
            mensaje = "No se encontró coincidencias";
            notificar(0);
        }
    }

    private void notificar(int num) {
        tableAviones.getItems().clear();
        if (num == 1) {
            alertar1();
        }
        if (num == 0) {
            alertar2();
        }
        if (num == 2) {
            alertar3();
        }
    }

    private void alertar3() {
        ImageView imageView2 = new ImageView(new Image("org/una/aeropuertocliente/views/shared/warning.png"));
        Text lab = new Text("No hay aviones en esta aerolinea, porfavor regitrar aviones");
        lab.setFill(Color.web("#0076a3"));
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(imageView2);
        box.getChildren().add(lab);
        tableAviones.setPlaceholder(box);
    }

    private void alertar2() {
        ImageView imageView2 = new ImageView(new Image("org/una/aeropuertocliente/views/shared/warning.png"));
        Text lab = new Text(mensaje);
        lab.setFill(Color.web("#0076a3"));
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(imageView2);
        box.getChildren().add(lab);
        tableAviones.setPlaceholder(box);
    }

    private void alertar1() {
        ImageView imageView = new ImageView(new Image("org/una/aeropuertocliente/views/shared/info.png"));
        Text lab = new Text("Para mostrar datos en este apartado debe realizar el filtro correspondiente");
        lab.setFill(Color.web("#0076a3"));
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(imageView);
        box.getChildren().add(lab);
        tableAviones.setPlaceholder(box);
    }

    public void indicarRequeridos() {
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txtId, txtNombre, txtResponsable, cmbEstado));
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

    @FXML
    private void volver(ActionEvent event) {
        PrincipalController.cambiarVistaPrincipal("aerolineas/Aerolineas");
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

    public void llenarListaNodos() {
        modDesarrollo.clear();
        modDesarrolloAxiliar.clear();
        modDesarrolloAxiliar.add(titulo.getText());
        modDesarrolloAxiliar.add(lblTable.getText());
        modDesarrolloAxiliar.add(txtId.getPromptText());
        modDesarrolloAxiliar.add(txtNombre.getPromptText());
        modDesarrolloAxiliar.add(txtResponsable.getPromptText());
        modDesarrolloAxiliar.add(cmbEstado.getPromptText());
        modDesarrolloAxiliar.add(txtFilter.getPromptText());
        modDesarrolloAxiliar.add(combFilter.getPromptText());
        modDesarrolloAxiliar.add(cmbEstado2.getPromptText());
        modDesarrolloAxiliar.add(btnCancelar.getText());
        modDesarrolloAxiliar.add(btnEditar.getText());
        modDesarrolloAxiliar.add(btnRegistrar.getText());
        modDesarrolloAxiliar.add(btnGuardar.getText());
        modDesarrolloAxiliar.add(btnVolverAerolinea.getText());
        modDesarrollo.addAll(Arrays.asList(titulo, lblTable, txtId, txtNombre, txtResponsable, cmbEstado, txtFilter, combFilter,
                cmbEstado2, btnCancelar, btnEditar, btnRegistrar, btnGuardar, btnVolverAerolinea));
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
                    dato = tableAviones.getId();
                    ((Label) node).setText(dato);
                } else {
                    dato = ((Label) node).getId();
                    ((Label) node).setText(dato);
                }
            }
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

}
