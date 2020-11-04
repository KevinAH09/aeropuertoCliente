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
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.una.aeropuertocliente.dtos.AerolineasDTO;
import org.una.aeropuertocliente.dtos.AvionesDTO;
import org.una.aeropuertocliente.dtos.AvionesZonasDTO;
import org.una.aeropuertocliente.dtos.VuelosDTO;
import org.una.aeropuertocliente.dtos.ZonasDTO;
import org.una.aeropuertocliente.entitiesServices.AvionesService;
import org.una.aeropuertocliente.entitiesServices.AvionesZonasService;
import org.una.aeropuertocliente.entitiesServices.VuelosService;
import org.una.aeropuertocliente.utils.AppContext;
import org.una.aeropuertocliente.utils.Mensaje;

/**
 * FXML Controller class
 *
 * @author Bosco
 */
public class MantenimientoAvionController extends Controller implements Initializable {

    @FXML
    private JFXTextField txtTipoAvion;
    @FXML
    private JFXTextField txtZona;
    @FXML
    private JFXButton btnZona;
    @FXML
    private JFXTextField txtFiltrar;
    @FXML
    private JFXButton btnFiltrar;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private TableView<VuelosDTO> tableview;
    @FXML
    private Label labTituloAviones;
    @FXML
    private Label labTable;
    @FXML
    private JFXTextField txtMatricula;
    @FXML
    private JFXComboBox<String> combEstado;
    @FXML
    private JFXComboBox<String> combFiltro;
    AvionesDTO avion;
    VuelosDTO vueloFil;
    AvionesZonasDTO avionZona;
    public List<VuelosDTO> vuelosList = new ArrayList<VuelosDTO>();
    @FXML
    private Label lblGuardar;
    @FXML
    private Label lblEditar;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private JFXButton btnGuardarEditar;
    @FXML
    private Label labBtnVolver;
    @FXML
    private JFXButton btnVolver;
    AerolineasDTO aerolinea;
    public AvionesZonasDTO avionZonaList;
    @FXML
    private Label labVuelos;
    String mensaje;
    public VuelosDTO data = new VuelosDTO();
    private List<Node> requeridos = new ArrayList<>();
    public List<Node> modDesarrollo = new ArrayList<>();
    public List<String> modDesarrolloAxiliar = new ArrayList<>();
    @FXML
    private Label titulo;
    @FXML
    private Label titulo2;
    @FXML
    private Label lblTable;
    @FXML
    private JFXComboBox<String> cmbEstado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        aerolinea = (AerolineasDTO) AppContext.getInstance().get("aerolinea");
        avion = (AvionesDTO) AppContext.getInstance().get("agregarAvion");
        combEstado.setItems(FXCollections.observableArrayList("Activo", "Inactivo"));
        cmbEstado.setItems(FXCollections.observableArrayList("Activo", "Inactivo"));
        combFiltro.setItems(FXCollections.observableArrayList("Id", "Destino", "Origen", "Estado"));
        combFiltro.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (t1 == "Id") {
                    cmbEstado.setVisible(false);
                    txtFiltrar.setVisible(true);
                    txtFiltrar.setPromptText("Ingrese número correspondiente");
                }
                if (t1 == "Destino") {
                    cmbEstado.setVisible(false);
                    txtFiltrar.setVisible(true);
                    txtFiltrar.setPromptText("Ingrese destino del vuelo");
                }
                if (t1 == "Origen") {
                    cmbEstado.setVisible(false);
                    txtFiltrar.setVisible(true);
                    txtFiltrar.setPromptText("Ingrese origen del vuelo");
                }
                if (t1 == "Estado") {
                    cmbEstado.setVisible(true);
                    txtFiltrar.setVisible(false);
                    txtFiltrar.setPromptText("Ingrese estado");
                }
            }

        }
        );
        if (avion != null) {
            llenarVuelos();
            if (avion.isEstado()) {
                combEstado.setValue("Activo");
            } else {
                combEstado.setValue("Inactivo");
            }
            avionZonaList = AvionesZonasService.zonaReciente(avion.getId());
            if (avionZonaList != null) {
                txtZona.setText(avionZonaList.getZona().getNombreZona());
            }

            txtTipoAvion.setDisable(true);
            txtMatricula.setDisable(true);
            txtZona.setDisable(true);
            combEstado.setDisable(true);
            txtTipoAvion.setText(avion.getTipoAvion());
            txtMatricula.setText(avion.getMatricula());
            btnCancelar.setVisible(false);
            btnEditar.setVisible(true);
            btnGuardarEditar.setDisable(true);
            btnZona.setDisable(true);
            btnZona.setDisable(true);
            if (AppContext.getInstance().get("zon") != null) {
                ZonasDTO zon;
                zon = (ZonasDTO) AppContext.getInstance().get("zon");
                txtZona.setText(zon.getNombreZona());
            }

        } else {
            txtTipoAvion.setText("");
            txtMatricula.setText("");
            combEstado.setValue("Activo");
            if (AppContext.getInstance().get("zon") != null) {
                ZonasDTO zon;
                zon = (ZonasDTO) AppContext.getInstance().get("zon");
                txtZona.setText(zon.getNombreZona());
            }

            txtTipoAvion.setDisable(false);
            txtMatricula.setDisable(false);
            txtZona.setDisable(true);
            combEstado.setDisable(true);
            notificar(2);

            txtFiltrar.setVisible(false);
            combFiltro.setVisible(false);
            btnFiltrar.setVisible(false);
            btnCancelar.setVisible(false);
            btnZona.setDisable(false);

            btnCancelar.setVisible(false);
            btnEditar.setDisable(true);
            btnGuardarEditar.setVisible(true);
        }
        indicarRequeridos();
        llenarListaNodos();
        desarrollo();
    }

    @FXML
    private void agregarZona(ActionEvent event) {
        PrincipalController.cambiarVistaPrincipal("zonas/Zonas");

    }

    @FXML
    private void btnFiltrar(ActionEvent event) {
        if (combFiltro.getValue() == null || txtFiltrar.getText() == null) {
            mensaje = "Por favor debe ingresar un datos en el campo de búsqueda";
            notificar(0);
        } else {
            if (combFiltro.getValue().equals("Id") && !txtFiltrar.getText().isEmpty()) {
                tableview.getItems().clear();
                vueloFil = VuelosService.idVuelo(Long.valueOf(txtFiltrar.getText()));
                if (vueloFil != null) {
                    tableview.setItems(FXCollections.observableArrayList(vueloFil));
                } else {
                    mensaje = "No se encontró coincidencias";
                    notificar(0);
                }
            }
            if (combFiltro.getValue().equals("Estado") && !txtFiltrar.getText().isEmpty()) {
                if (cmbEstado.getValue().equals("Activo")) {
                    tableview.getItems().clear();
                    vuelosList = VuelosService.estado(true);
                    if (vuelosList != null) {
                        tableview.setItems(FXCollections.observableArrayList(vuelosList));
                    } else {
                        mensaje = "No se encontró coincidencias";
                        notificar(0);
                    }
                }
                if (cmbEstado.getValue().equals("Inactivo")) {
                    tableview.getItems().clear();
                    vuelosList = VuelosService.estado(false);
                    if (vuelosList != null) {
                        tableview.setItems(FXCollections.observableArrayList(vuelosList));
                    } else {
                        mensaje = "No se encontró coincidencias";
                        notificar(0);
                    }
                }
                if (vuelosList == null) {
                    mensaje = "No se encontró coincidencias";
                    notificar(0);
                }
            }
            if (combFiltro.getValue().equals("Origen") && !txtFiltrar.getText().isEmpty()) {
                tableview.getItems().clear();
                vuelosList = VuelosService.Origen(txtFiltrar.getText());
                if (vuelosList != null) {
                    tableview.setItems(FXCollections.observableArrayList(vuelosList));
                } else {
                    mensaje = "No se encontró coincidencias";
                    notificar(0);
                }
            }
            if (combFiltro.getValue().equals("Destino") && !txtFiltrar.getText().isEmpty()) {
                tableview.getItems().clear();
                vuelosList = VuelosService.Destino(txtFiltrar.getText());
                if (vuelosList != null) {
                    tableview.setItems(FXCollections.observableArrayList(vuelosList));
                } else {
                    mensaje = "No se encontró coincidencias";
                    notificar(0);
                }
            }
        }
    }

    private void llenarVuelos() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        TableColumn<VuelosDTO, String> colId = new TableColumn("Id");
        colId.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getId().toString()));
        TableColumn<VuelosDTO, String> colOrigen = new TableColumn("Origen");
        colOrigen.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getOrigen()));
        TableColumn<VuelosDTO, String> colDestino = new TableColumn("Destino");
        colDestino.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getDestino()));
        TableColumn<VuelosDTO, String> colFechaInicio = new TableColumn("Fecha Inicio");
        colFechaInicio.setCellValueFactory((param) -> new SimpleObjectProperty(formatter.format(param.getValue().getFechaInicio())));
        TableColumn<VuelosDTO, String> colFechaFinal = new TableColumn("Fecha Final");
        colFechaFinal.setCellValueFactory((param) -> new SimpleObjectProperty(formatter.format(param.getValue().getFechaFinal())));
        TableColumn<VuelosDTO, String> colEstado = new TableColumn("Estado\nActivo Inactivo");
        colEstado.setCellValueFactory((param) -> {
            if (param.getValue().isEstado()) {
                return new SimpleStringProperty("Activo");
            }
            return new SimpleStringProperty("Inactivo");
        });
        tableview.getColumns().addAll(colId, colOrigen, colDestino, colFechaInicio, colFechaFinal, colEstado);
        notificar(1);
    }

    public void indicarRequeridos() {
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txtMatricula, txtTipoAvion, txtZona, combEstado));
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

    @Override
    public void initialize() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void notificar(int num) {
        tableview.getItems().clear();
        if (num == 1) {
            ImageView imageView = new ImageView(new Image("org/una/aeropuertocliente/views/shared/info.png"));
            Text lab = new Text("Para mostrar datos en este apartado debe realizar el filtro correspondiente");
            lab.setFill(Color.web("#0076a3"));
            VBox box = new VBox();
            box.setAlignment(Pos.CENTER);
            box.getChildren().add(imageView);
            box.getChildren().add(lab);
            tableview.setPlaceholder(box);
        }
        if (num == 0) {
            ImageView imageView2 = new ImageView(new Image("org/una/aeropuertocliente/views/shared/warning.png"));
            Text lab = new Text(mensaje);
            lab.setFill(Color.web("#0076a3"));
            VBox box = new VBox();
            box.setAlignment(Pos.CENTER);
            box.getChildren().add(imageView2);
            box.getChildren().add(lab);
            tableview.setPlaceholder(box);
        }
        if (num == 2) {
            ImageView imageView2 = new ImageView(new Image("org/una/aeropuertocliente/views/shared/warning.png"));
            Text lab = new Text("No hay vuelos asignados a este avión");
            lab.setFill(Color.web("#0076a3"));
            VBox box = new VBox();
            box.setAlignment(Pos.CENTER);
            box.getChildren().add(imageView2);
            box.getChildren().add(lab);
            tableview.setPlaceholder(box);

        }
    }

    @FXML
    private void onActionCancelar(ActionEvent event) {
    }

    @FXML
    private void onActionEditar(ActionEvent event) {

        btnZona.setDisable(false);
        txtTipoAvion.setDisable(false);
        txtMatricula.setDisable(false);
        combEstado.setDisable(false);
        btnEditar.setDisable(true);
        btnGuardarEditar.setDisable(false);
        btnVolver.setDisable(true);
    }

    @FXML
    private void onActionGuardarEditar(ActionEvent event) {
        String validacion = validarRequeridos();
        avionZona = new AvionesZonasDTO();
        if (avion == null) {
            if (validacion == null) {
                avion = new AvionesDTO();
                if (combEstado.getValue().equals("Activo")) {
                    avion.setEstado(true);
                } else {
                    avion.setEstado(false);
                }

                avion.setMatricula(txtMatricula.getText());
                avion.setTipoAvion(txtTipoAvion.getText());
                avion.setAerolineaId((AerolineasDTO) AppContext.getInstance().get("aerolinea"));
                if (AvionesService.createAvion(avion) == 201) {
                    AvionesDTO avionesDTO = new AvionesDTO();
                    avionesDTO = AvionesService.matriculaUnicaAvion(txtMatricula.getText());
                    avionZona.avion = avionesDTO;
                    avionZona.setZona((ZonasDTO) AppContext.getInstance().get("zon"));

                    if (AvionesZonasService.createAvionZona(avionZona) == 201) {
                        btnGuardarEditar.setDisable(true);
                        btnEditar.setDisable(false);

                        new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar Aviones", ((Stage) txtMatricula.getScene().getWindow()), "Se guardó correctamente");
                    } else {
                        new Mensaje().showModal(Alert.AlertType.ERROR, "Error al guardar la zona del avion", ((Stage) txtMatricula.getScene().getWindow()), "No se guardó correctamente");
                    }

                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error al guardar los Aviones", ((Stage) txtMatricula.getScene().getWindow()), "No se guardó correctamente");
                }
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error al crear los datos", ((Stage) txtMatricula.getScene().getWindow()), validacion);
            }

        } else {
            if (validacion == null) {
                if (combEstado.getValue().equals("Activo")) {
                    avion.setEstado(true);
                } else {
                    avion.setEstado(false);
                }
                avion.setMatricula(txtMatricula.getText());
                avion.setTipoAvion(txtTipoAvion.getText());

                if (AvionesService.updateAvion(avion) == 200) {
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Editar Avion", ((Stage) txtMatricula.getScene().getWindow()), "Se editó correctamente");
                    btnEditar.setDisable(false);
                    btnGuardarEditar.setDisable(true);
                    btnVolver.setDisable(false);
                    btnZona.setDisable(true);
                    txtTipoAvion.setDisable(true);
                    txtMatricula.setDisable(true);
                    combEstado.setDisable(true);
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error al editar el Avion", ((Stage) txtMatricula.getScene().getWindow()), "No se editó correctamente");
                }
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error al crear los datos", ((Stage) txtMatricula.getScene().getWindow()), validacion);
            }
        }
    }

    @FXML
    private void onActionVolver(ActionEvent event) {
        PrincipalController.cambiarVistaPrincipal("mantenimientoAerolineas/MantenimientoAerolineas");
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

    public void llenarListaNodos() {
        modDesarrollo.clear();
        modDesarrolloAxiliar.clear();
        modDesarrolloAxiliar.add(titulo.getText());
        modDesarrolloAxiliar.add(titulo2.getText());
        modDesarrolloAxiliar.add(lblTable.getText());
        modDesarrolloAxiliar.add(txtTipoAvion.getPromptText());
        modDesarrolloAxiliar.add(txtZona.getPromptText());
        modDesarrolloAxiliar.add(txtFiltrar.getPromptText());
        modDesarrolloAxiliar.add(txtMatricula.getPromptText());
        modDesarrolloAxiliar.add(combEstado.getPromptText());
        modDesarrolloAxiliar.add(combFiltro.getPromptText());
        modDesarrolloAxiliar.add(cmbEstado.getPromptText());
        modDesarrolloAxiliar.add(btnFiltrar.getText());
        modDesarrolloAxiliar.add(btnZona.getText());
        modDesarrolloAxiliar.add(btnCancelar.getText());
        modDesarrolloAxiliar.add(btnEditar.getText());
        modDesarrolloAxiliar.add(btnGuardarEditar.getText());
        modDesarrolloAxiliar.add(btnVolver.getText());
        modDesarrollo.addAll(Arrays.asList(titulo, titulo2, lblTable, txtTipoAvion, txtZona, txtFiltrar, txtMatricula, combEstado, combFiltro, cmbEstado,
                btnFiltrar, btnZona, btnCancelar, btnEditar, btnGuardarEditar, btnVolver));
    }

    public void desarrollo() {
        String dato = "";
        boolean validos1 = (Boolean) AppContext.getInstance().get("mod");
        if (validos1) {
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
                        dato = tableview.getId();
                        ((Label) node).setText(dato);
                    } else {
                        dato = ((Label) node).getId();
                        ((Label) node).setText(dato);
                    }
                }
            }
        } else {
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

}
