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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.una.aeropuertocliente.dtos.AvionesDTO;
import org.una.aeropuertocliente.dtos.VuelosDTO;
import org.una.aeropuertocliente.entitiesServices.VuelosService;
import org.una.aeropuertocliente.utils.AppContext;
import org.una.aeropuertocliente.sharedService.Token;
import org.una.aeropuertocliente.utils.Mensaje;

/**
 * FXML Controller class
 *
 * @author Bosco
 */
public class VuelosController extends Controller implements Initializable {

    @FXML
    private Label lablTitulo;
    @FXML
    private Label titulo;
    @FXML
    private Label lbltxtNombre;
    @FXML
    private Label labTxtFild1;
    @FXML
    private Label labComb1;
    @FXML
    private Label labBtnFiltrar1;
    @FXML
    private Label lblTable1;
    @FXML
    private Label lbltxtTipo;
    @FXML
    private JFXTextField txtmatricula;
    VuelosDTO vuelos;
    VuelosDTO vuelosFil;
    public List<VuelosDTO> vuelosList = new ArrayList<VuelosDTO>();
    public List<VuelosDTO> vuelosList2 = new ArrayList<VuelosDTO>();
    public List<Node> modDesarrollo = new ArrayList<>();
    public List<String> modDesarrolloAxiliar = new ArrayList<>();
    @FXML
    private Label lbltxtTipo1;
    @FXML
    private Label lbltxtNombre1;
    @FXML
    private JFXTextField txtTipoAvion;
    @FXML
    private JFXButton btnBuscarAvion;
    @FXML
    private JFXTextField txtFilter;
    @FXML
    private JFXComboBox<String> combFilter;
    @FXML
    private JFXButton btnFiltrar;
    @FXML
    private TableView<VuelosDTO> tableView;
    @FXML
    private JFXButton btnRegistrarVuelos;
    AvionesDTO Objetoaviones;
    public List<VuelosDTO> A2 = new ArrayList<VuelosDTO>();
    public VuelosDTO data = new VuelosDTO();
    @FXML
    private JFXComboBox<String> cmbEstado;
    @FXML
    private JFXDatePicker datePikerFechainicio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Objetoaviones = null;
        Objetoaviones = (AvionesDTO) AppContext.getInstance().get("avionAVuelos");

        actionVueloClick();
        combFilter.setItems(FXCollections.observableArrayList("Id", "Destino", "Origen", "Estado", "Fecha inicio"));
        cmbEstado.setItems(FXCollections.observableArrayList("Activo", "Inactivo"));
        txtTipoAvion.setText("");
        txtmatricula.setText("");
        if (Objetoaviones != null) {
            llenarVuelos();
            txtTipoAvion.setText(Objetoaviones.getTipoAvion());
            txtmatricula.setText(Objetoaviones.getMatricula());
            A2 = VuelosService.vuelos(Objetoaviones.getId());
            if (A2.isEmpty()) {
                notificar(2);
                txtFilter.setDisable(true);
                combFilter.setDisable(true);
                btnFiltrar.setDisable(true);
            }
            txtTipoAvion.setDisable(true);
            txtmatricula.setDisable(true);
        } else {
            notificar(2);
            txtTipoAvion.setText("");
            txtmatricula.setText("");
            txtTipoAvion.setDisable(true);
            txtmatricula.setDisable(true);
            tableView.setDisable(true);
        }
        if (!Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_ADMIN")) {
            btnRegistrarVuelos.setVisible(true);
            btnRegistrarVuelos.setDisable(false);
        } else {
            btnRegistrarVuelos.setVisible(true);
            btnRegistrarVuelos.setDisable(true);
        }
        asignarAccionComboboxFiltro();
        llenarListaNodos();
        desarrollo();
    }

    private void asignarAccionComboboxFiltro() {
        combFilter.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (t1 == "Id") {
                    datePikerFechainicio.setVisible(false);
                    cmbEstado.setVisible(false);
                    txtFilter.setVisible(true);
                    txtFilter.setPromptText("Ingrese número correspondiente");
                }
                if (t1 == "Destino") {
                    datePikerFechainicio.setVisible(false);
                    cmbEstado.setVisible(false);
                    txtFilter.setVisible(true);
                    txtFilter.setPromptText("Ingrese nombre del destino");
                }
                if (t1 == "Nombre Aerolinea") {
                    datePikerFechainicio.setVisible(false);
                    cmbEstado.setVisible(false);
                    txtFilter.setVisible(true);
                    txtFilter.setPromptText("Ingrese nombre del Origen");
                }
                if (t1 == "Estado") {
                    cmbEstado.setVisible(true);
                    datePikerFechainicio.setVisible(false);
                    txtFilter.setVisible(false);
                }

                if (t1 == "Fecha inicio") {
                    cmbEstado.setVisible(false);
                    txtFilter.setVisible(false);
                    datePikerFechainicio.setVisible(true);
                }
            }

        }
        );
    }

    private void actionVueloClick() {
        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_GESTOR") || Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_ADMIN")) {
                    if (mouseEvent.getClickCount() == 2 && tableView.selectionModelProperty().get().getSelectedItem() != null) {
                        VuelosDTO vuelo = (VuelosDTO) tableView.selectionModelProperty().get().getSelectedItem();
                        AppContext.getInstance().set("VueloAMantenimientoVuelo", vuelo);
                        if (Objetoaviones != null) {
                            AppContext.getInstance().set("AvionAMantenimientoVuelo", Objetoaviones);
                        }
                        PrincipalController.cambiarVistaPrincipal("mantenimientoVuelos/MantenimientoVuelos");
                    }
                }
            }
        }
        );
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
        TableColumn<VuelosDTO, String> colTipoBicatora = new TableColumn("Tipo Bitácora");
        colTipoBicatora.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getBitacoraVueloId().getTipoBitacora()));
        tableView.getColumns().addAll(colId, colOrigen, colDestino, colFechaInicio, colFechaFinal, colEstado, colTipoBicatora);
        addButtonToTable();
        notificar(1);
    }

    private void addButtonToTable() {
        TableColumn<VuelosDTO, Void> colBtn = new TableColumn("Acción");
        Callback<TableColumn<VuelosDTO, Void>, TableCell<VuelosDTO, Void>> cellFactory = new Callback<TableColumn<VuelosDTO, Void>, TableCell<VuelosDTO, Void>>() {
            @Override
            public TableCell<VuelosDTO, Void> call(final TableColumn<VuelosDTO, Void> param) {
                final TableCell<VuelosDTO, Void> cell = new TableCell<VuelosDTO, Void>() {

                    private final JFXButton btn = new JFXButton("Seleccionar");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            VuelosDTO vuelo = (VuelosDTO) getTableView().getItems().get(getIndex());
                            AppContext.getInstance().set("VueloAMantenimientoVuelo", vuelo);
                            AppContext.getInstance().set("AvionAMantenimientoVuelo", Objetoaviones);
                            PrincipalController.cambiarVistaPrincipal("mantenimientoVuelos/MantenimientoVuelos");
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

        tableView.getColumns().add(colBtn);
    }

    @FXML
    private void filtrar(ActionEvent event) {
        
        
        tableView.getItems().clear();
        tableView.getColumns().clear();
        llenarVuelos();
        if (combFilter.getValue() != null) {
            if (combFilter.getValue().equals("Id") && !txtFilter.getText().isEmpty()) {
                filtrarPorId();
            } else if (combFilter.getValue() == "Estado" && cmbEstado.getValue() != null) {
                filtrarPorEstado();
            } else if (combFilter.getValue().equals("Origen") && !txtFilter.getText().isEmpty()) {
                filtrarPorOrigen();
            } else if (combFilter.getValue().equals("Destino") && !txtFilter.getText().isEmpty()) {
                filtrarPorDestino();
            } else if (combFilter.getValue().equals("Fecha inicio") && datePikerFechainicio.getValue()!=null) {
                filtrarPorFechaInicio();
            } else if (combFilter.getValue().equals("Matricula del Avión") && !txtFilter.getText().isEmpty()) {
                filtrarPorMatriculaAvion();
            } else {
                tableView.getItems().clear();
                tableView.getColumns().clear();
                notificar(3);
            }

        } else {
            tableView.getItems().clear();
            tableView.getColumns().clear();
            notificar(0);
        }
    }

    private void filtrarPorMatriculaAvion() {
        tableView.getItems().clear();
        vuelosList = VuelosService.allVuelos();
        for (int i = 0; i < vuelosList.size(); i++) {
            if (vuelosList.get(i).getAvionId().getMatricula().equals(txtFilter.getText())) {
                vuelosList2 = VuelosService.vuelos(vuelosList.get(i).getAvionId().getId());
            }

        }
        if (!vuelosList2.isEmpty()) {
            tableView.setItems(FXCollections.observableArrayList(vuelosList2));
        } else {
            notificar(0);
        }
    }

    private void filtrarPorOrigen() {
        tableView.getItems().clear();
        vuelosList = VuelosService.Origen(txtFilter.getText());
        if (vuelosList != null) {
            tableView.setItems(FXCollections.observableArrayList(vuelosList));
        } else {
            notificar(0);
        }
    }

    private void filtrarPorDestino() {
        tableView.getItems().clear();
        vuelosList = VuelosService.Destino(txtFilter.getText());
        if (vuelosList != null) {
            tableView.setItems(FXCollections.observableArrayList(vuelosList));
        } else {
            notificar(0);
        }
    }

    private void filtrarPorFechaInicio() {
        tableView.getItems().clear();
        LocalDate localDate = datePikerFechainicio.getValue();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        String sDate1 = new SimpleDateFormat("yyyy-MM-dd").format(date);
        vuelosList = VuelosService.FechaInicio(sDate1);
        System.out.println("org.una.aeropuertocliente.controllers.VuelosController.filtrarPorFechaInicio()"+vuelosList);
        if (vuelosList != null) {
            tableView.setItems(FXCollections.observableArrayList(vuelosList));
        } else {
            notificar(0);
        }
    }

    private void filtrarPorEstado() {
        if (cmbEstado.getValue() == "Activo") {
            tableView.getItems().clear();
            vuelosList = VuelosService.estado(true);
            if (vuelosList != null) {
                tableView.setItems(FXCollections.observableArrayList(vuelosList));
            } else {
                notificar(0);
            }
        }
        if (cmbEstado.getValue() == "Inactivo") {
            tableView.getItems().clear();
            vuelosList = VuelosService.estado(false);
            if (vuelosList != null) {
                tableView.setItems(FXCollections.observableArrayList(vuelosList));
            } else {
                notificar(0);
            }
        }
    }

    private void filtrarPorId() throws NumberFormatException {
        tableView.getItems().clear();
        vuelosFil = VuelosService.idVuelo(Long.valueOf(txtFilter.getText()));
        if (vuelosFil != null) {
            tableView.setItems(FXCollections.observableArrayList(vuelosFil));
        } else {
            notificar(0);
        }
    }

    @FXML
    private void registrarVuelos(ActionEvent event) {
        if (!txtTipoAvion.getText().equals("")) {
            if (Objetoaviones != null) {
                AppContext.getInstance().set("AvionAMantenimientoVuelo", Objetoaviones);
                AppContext.getInstance().set("VueloAMantenimientoVuelo", null);
            }
            PrincipalController.cambiarVistaPrincipal("mantenimientoVuelos/MantenimientoVuelos");
        } else {
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Registrar vuelo", ((Stage) txtTipoAvion.getScene().getWindow()), "Por favor busque un avión antes de registrar un vuelo");
        }
    }

    @Override
    public void initialize() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void buscarAvion(ActionEvent event) {
        PrincipalController.cambiarVistaPrincipal("aviones/Aviones");
    }

    private void notificar(int num) {
        tableView.getItems().clear();
        if (num == 1) {
            alertar1();
        }
        if (num == 0) {
            alertar2();
        }
        if (num == 2) {
            alertar3();
        }
        if(num == 3){
            alertar4();
        }
    }

    private void alertar3() {
        ImageView imageView2 = new ImageView(new Image("org/una/aeropuertocliente/views/shared/warning.png"));
        Text lab = new Text("No hay vuelos en este avion");
        lab.setFill(Color.web("#0076a3"));
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(imageView2);
        box.getChildren().add(lab);
        tableView.setPlaceholder(box);
    }
    
    private void alertar4() {
        ImageView imageView2 = new ImageView(new Image("org/una/aeropuertocliente/views/shared/warning.png"));
        Text lab = new Text("Campos vacíos en el apartado de búsqueda");
        lab.setFill(Color.web("#0076a3"));
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(imageView2);
        box.getChildren().add(lab);
        tableView.setPlaceholder(box);
    }

    private void alertar2() {
        ImageView imageView2 = new ImageView(new Image("org/una/aeropuertocliente/views/shared/warning.png"));
        Text lab = new Text("No se encontró coincidencias");
        lab.setFill(Color.web("#0076a3"));
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(imageView2);
        box.getChildren().add(lab);
        tableView.setPlaceholder(box);
    }

    private void alertar1() {
        ImageView imageView = new ImageView(new Image("org/una/aeropuertocliente/views/shared/info.png"));
        Text lab = new Text("Para mostrar datos en este apartado debe realizar el filtro correspondiente");
        lab.setFill(Color.web("#0076a3"));
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(imageView);
        box.getChildren().add(lab);
        tableView.setPlaceholder(box);
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
        modDesarrolloAxiliar.add(lblTable1.getText());
        modDesarrolloAxiliar.add(txtmatricula.getPromptText());
        modDesarrolloAxiliar.add(txtTipoAvion.getPromptText());
        modDesarrolloAxiliar.add(txtFilter.getPromptText());
        modDesarrolloAxiliar.add(combFilter.getPromptText());
        modDesarrolloAxiliar.add(cmbEstado.getPromptText());
        modDesarrolloAxiliar.add(btnBuscarAvion.getText());
        modDesarrolloAxiliar.add(btnFiltrar.getText());
        modDesarrolloAxiliar.add(btnRegistrarVuelos.getText());
        modDesarrollo.addAll(Arrays.asList(titulo, lblTable1, txtmatricula, txtTipoAvion, txtFilter, combFilter, cmbEstado, btnBuscarAvion,
                btnFiltrar, btnRegistrarVuelos));
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
                if (node == lblTable1) {
                    dato = tableView.getId();
                    ((Label) node).setText(dato);
                } else {
                    dato = ((Label) node).getId();
                    ((Label) node).setText(dato);
                }
            }
        }
    }
}
