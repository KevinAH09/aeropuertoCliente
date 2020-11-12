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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import org.una.aeropuertocliente.dtos.AvionesDTO;
import org.una.aeropuertocliente.entitiesServices.AvionesService;
import org.una.aeropuertocliente.sharedService.Token;
import org.una.aeropuertocliente.utils.AppContext;

/**
 * FXML Controller class
 *
 * @author Bosco
 */
public class AvionesController extends Controller implements Initializable {

    @FXML
    private Label labTitulo;
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
    private Label lblTable;

    AvionesDTO aviones;
    AvionesDTO avionesFil;
    public List<AvionesDTO> avionesList = new ArrayList<AvionesDTO>();
    public List<AvionesDTO> avionesList2 = new ArrayList<AvionesDTO>();
    public List<Node> modDesarrollo = new ArrayList<>();
    public List<String> modDesarrolloAxiliar = new ArrayList<>();
    @FXML
    private TableView<AvionesDTO> tableAvion;
    @FXML
    private JFXButton btnVolverVuelos;
    String mensaje;
    public AvionesDTO data = new AvionesDTO();
    @FXML
    private Label titulo;
    @FXML
    private JFXComboBox<String> cmbEstado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        actionAvionClick();
        llenarAviones();
        combFilter.setItems(FXCollections.observableArrayList("Id", "Matrícula", "Tipo de avión", "Estado", "Nombre aerolinea"));
        cmbEstado.setItems(FXCollections.observableArrayList("Activo", "Inactivo"));
        asignarAccionComboboxFiltro();
        llenarListaNodos();
        desarrollo();
    }

    private void asignarAccionComboboxFiltro() {
        combFilter.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (t1 == "Id") {
                    cmbEstado.setVisible(false);
                    txtFilter.setVisible(true);
                    txtFilter.setPromptText("Ingrese número correspondiente");
                }
                if (t1 == "Matrícula") {
                    cmbEstado.setVisible(false);
                    txtFilter.setVisible(true);
                    txtFilter.setPromptText("Ingrese matrícula del avión");
                }
                if (t1 == "Tipo de avión") {
                    cmbEstado.setVisible(false);
                    txtFilter.setVisible(true);
                    txtFilter.setPromptText("Ingrese el tipo de avión");
                }
                if (t1 == "Estado") {
                    cmbEstado.setVisible(true);
                    txtFilter.setVisible(false);
                }
                if (t1 == "Nombre aerolinea") {
                    cmbEstado.setVisible(false);
                    txtFilter.setVisible(true);
                    txtFilter.setPromptText("Ingrese nombre de la aerolinea");
                }
            }

        }
        );
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
                filtrarPorAerolinea();
            }
            
        }

    }

    private void filtrarPorAerolinea() {
        tableAvion.getItems().clear();
        avionesList = AvionesService.allAviones();
        for (int i = 0; i < avionesList.size(); i++) {
            if (avionesList.get(i).getAerolineaId().getNombreAerolinea().equals(txtFilter.getText())) {
                avionesList2 = AvionesService.aerolinea(avionesList.get(i).getAerolineaId().getId());
            }

        }
        if (!avionesList2.isEmpty()) {
            tableAvion.setItems(FXCollections.observableArrayList(avionesList2));
        } else {
            mensaje = "No se encontró coincidencias";
            notificar(0);
        }
    }

    private void filtrarPorTipoAvion() {
        tableAvion.getItems().clear();
        avionesList = AvionesService.TipoAvion(txtFilter.getText());
        if (avionesList != null) {
            tableAvion.setItems(FXCollections.observableArrayList(avionesList));
        } else {
            mensaje = "No se encontró coincidencias";
            notificar(0);
        }
    }

    private void filtrarPorMatricula() {
        tableAvion.getItems().clear();
        avionesList = AvionesService.matricula(txtFilter.getText());
        if (avionesList != null) {
            tableAvion.setItems(FXCollections.observableArrayList(avionesList));
        } else {
            mensaje = "No se encontró coincidencias";
            notificar(0);
        }
    }

    private void filtrarPorEstado() {
        if (cmbEstado.getValue().equals("Activo")) {
            tableAvion.getItems().clear();
            avionesList = AvionesService.estado(true);
            if (avionesList != null) {
                tableAvion.setItems(FXCollections.observableArrayList(avionesList));
            } else {
                mensaje = "No se encontró coincidencias";
                notificar(0);
            }
        }
        if (cmbEstado.getValue() == "Inactivo") {
            tableAvion.getItems().clear();
            avionesList = AvionesService.estado(false);
            if (avionesList != null) {
                tableAvion.setItems(FXCollections.observableArrayList(avionesList));
            } else {
                mensaje = "No se encontró coincidencias";
                notificar(0);
            }
        }
    }

    private void filtrarPorId() throws NumberFormatException {
        tableAvion.getItems().clear();
        avionesFil = AvionesService.idAvion(Long.valueOf(txtFilter.getText()));
        if (avionesFil != null) {
            tableAvion.setItems(FXCollections.observableArrayList(avionesFil));
        } else {
            mensaje = "No se encontró coincidencias";
            notificar(0);
        }
    }

    private void notificar(int num) {
        tableAvion.getItems().clear();
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
        Text lab = new Text("No hay vuelos en este avion, porfavor regitrar vuelos");
        lab.setFill(Color.web("#0076a3"));
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(imageView2);
        box.getChildren().add(lab);
        tableAvion.setPlaceholder(box);
    }

    private void alertar2() {
        ImageView imageView2 = new ImageView(new Image("org/una/aeropuertocliente/views/shared/warning.png"));
        Text lab = new Text(mensaje);
        lab.setFill(Color.web("#0076a3"));
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(imageView2);
        box.getChildren().add(lab);
        tableAvion.setPlaceholder(box);
    }

    private void alertar1() {
        ImageView imageView = new ImageView(new Image("org/una/aeropuertocliente/views/shared/info.png"));
        Text lab = new Text("Para mostrar datos en este apartado debe realizar el filtro correspondiente");
        lab.setFill(Color.web("#0076a3"));
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(imageView);
        box.getChildren().add(lab);
        tableAvion.setPlaceholder(box);
    }

    private void actionAvionClick() {
        tableAvion.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2 && tableAvion.selectionModelProperty().get().getSelectedItem() != null) {
                    AvionesDTO avion = (AvionesDTO) tableAvion.selectionModelProperty().get().getSelectedItem();
                    AppContext.getInstance().set("avionAVuelos", avion);
                    PrincipalController.cambiarVistaPrincipal("vuelos/Vuelos");
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
        tableAvion.getColumns().addAll(colId, colMatricula, colTipoAvion, colHoraVuelo, colEstado, colAerolinea);
        addButtonToTable();
        notificar(1);
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
                            AvionesDTO avion = (AvionesDTO) getTableView().getItems().get(getIndex());
                            AppContext.getInstance().set("avionAVuelos", avion);
                            PrincipalController.cambiarVistaPrincipal("vuelos/Vuelos");
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

        tableAvion.getColumns().add(colBtn);

    }

    @Override
    public void initialize() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void volver(ActionEvent event) {
        PrincipalController.cambiarVistaPrincipal("vuelos/Vuelos");
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
        modDesarrolloAxiliar.add(combFilter.getPromptText());
        modDesarrolloAxiliar.add(cmbEstado.getPromptText());
        modDesarrolloAxiliar.add(btnFiltrar.getText());
        modDesarrolloAxiliar.add(btnVolverVuelos.getText());
        modDesarrollo.addAll(Arrays.asList(titulo, lblTable, combFilter, cmbEstado, btnFiltrar, btnVolverVuelos));
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
                    dato = tableAvion.getId();
                    ((Label) node).setText(dato);
                } else {
                    dato = ((Label) node).getId();
                    ((Label) node).setText(dato);
                }
            }
        }
    }

}
