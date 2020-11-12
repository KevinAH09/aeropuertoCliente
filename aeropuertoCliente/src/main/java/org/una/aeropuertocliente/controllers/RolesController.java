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
import static org.una.aeropuertocliente.controllers.PrincipalController.cambiarVistaPrincipal;
import org.una.aeropuertocliente.dtos.RolesDTO;
import org.una.aeropuertocliente.dtos.ZonasDTO;
import org.una.aeropuertocliente.entitiesServices.RolesService;
import org.una.aeropuertocliente.sharedService.Token;
import org.una.aeropuertocliente.utils.AppContext;
import org.una.aeropuertocliente.utils.FlowController;

/**
 * FXML Controller class
 *
 * @author cfugu
 */
public class RolesController extends Controller implements Initializable {

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
    private TableView<RolesDTO> tableRoles;
    @FXML
    private Label lblRegistrar;
    @FXML
    private JFXButton btnRegistrar;
    RolesDTO rolesFilt;
    RolesDTO roles;
    String mensaje;

    public List<RolesDTO> rolesList = new ArrayList<RolesDTO>();
    public List<Node> modDesarrollo = new ArrayList<>();
    public List<String> modDesarrolloAxiliar = new ArrayList<>();
    @FXML
    private Label lblEstado;
    @FXML
    private JFXComboBox<String> cmbEstado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validarRol();
        actionRolesClick();
        notificar(1);
        cmbFiltro.setItems(FXCollections.observableArrayList("Id", "Estado", "Código"));
        cmbEstado.setItems(FXCollections.observableArrayList("Activo", "Inactivo"));
        mensaje = "Por favor debe ingresar un datos en el campo de búsqueda";
        asignarAccionComboboxFiltro();
        llenarListaNodos();
        desarrollo();
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

    private void validarRol() {
        if (!Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_GESTOR") || !Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_ADMIN")) {
            btnRegistrar.setVisible(false);
            btnRegistrar.setDisable(true);
        } else {
            btnRegistrar.setVisible(true);
            btnRegistrar.setDisable(false);
        }
    }

    @FXML
    private void onActionFiltrar(ActionEvent event) {
        if (txtBusqueda.getText().isEmpty() || cmbFiltro.getValue() == null || cmbEstado.getValue() == null) {
            mensaje = "Por favor debe ingresar un datos en el campo de búsqueda";
            notificar(0);
        }
        if (cmbFiltro.getValue() == "Id" && !txtBusqueda.getText().isEmpty()) {
            filtrarPorId();
        }
        if (cmbFiltro.getValue() == "Estado" && !cmbEstado.getValue().isEmpty()) {
            filtrarPorEstado();
        }
        if (cmbFiltro.getValue() == "Código" && !txtBusqueda.getText().isEmpty()) {
            filtrarPorCodigo();
        }
    }

    private void filtrarPorCodigo() {
        limpiarTableView();
        rolesList = RolesService.codigoRoles(txtBusqueda.getText());
        if (rolesList != null) {
            llenarTableView();
            tableRoles.setItems(FXCollections.observableArrayList(rolesList));
        } else {
            mensaje = "No se encontró coincidencias";
            notificar(0);
        }
    }

    private void filtrarPorEstado() {
        if (cmbEstado.getValue().equals("Activo")) {
            limpiarTableView();
            rolesList = RolesService.estadoRoles(true);
            if (rolesList != null) {
                llenarTableView();
                tableRoles.setItems(FXCollections.observableArrayList(rolesList));
            } else {
                mensaje = "No se encontró coincidencias";
                notificar(0);
            }
        }
        if (cmbEstado.getValue().equals("Inactivo")) {
            limpiarTableView();
            rolesList = RolesService.estadoRoles(false);
            if (rolesList != null) {
                llenarTableView();
                tableRoles.setItems(FXCollections.observableArrayList(rolesList));
            } else {
                mensaje = "No se encontró coincidencias";
                notificar(0);
            }
        }
        if (rolesList == null) {
            mensaje = "No se encontró coincidencias";
            notificar(0);
        }
    }

    private void filtrarPorId() throws NumberFormatException {
        limpiarTableView();
        rolesFilt = RolesService.idRole(Long.valueOf(txtBusqueda.getText()));
        if (rolesFilt != null) {
            llenarTableView();
            tableRoles.setItems(FXCollections.observableArrayList(rolesFilt));
        } else {
            mensaje = "No se encontró coincidencias";
            notificar(0);
        }
    }

    @FXML
    private void onActionRegistrar(ActionEvent event) {
        AppContext.getInstance().set("rol", null);
        cambiarVistaPrincipal("mantenimientoRoles/MantenimientoRoles");
    }

    private void llenarTableView() {
        TableColumn<RolesDTO, String> colId = new TableColumn("Id");
        colId.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getId().toString()));
        TableColumn<RolesDTO, String> colEstado = new TableColumn("Estado");
        colEstado.setCellValueFactory((param) -> {
            if (param.getValue().isEstado()) {
                return new SimpleStringProperty("Activo");
            }
            return new SimpleStringProperty("Inactivo");
        });
        TableColumn<RolesDTO, String> colCodigo = new TableColumn("Código");
        colCodigo.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getCodigo()));
        TableColumn<RolesDTO, String> colDescripcion = new TableColumn("Descripcion");
        colDescripcion.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getDescripcion()));
        tableRoles.getColumns().addAll(colId, colEstado, colCodigo, colDescripcion);
        agregarBtnTableView();

    }

    private void actionRolesClick() {
        tableRoles.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_GESTOR")) {
                    if (mouseEvent.getClickCount() == 2 && tableRoles.selectionModelProperty().get().getSelectedItem() != null) {
                        RolesDTO rol = (RolesDTO) tableRoles.selectionModelProperty().get().getSelectedItem();
                        AppContext.getInstance().set("rol", rol);
                        System.out.println(rol.getDescripcion());
                        cambiarVistaPrincipal("mantenimientoRoles/MantenimientoRoles");
                    }
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
        tableRoles.setPlaceholder(box);
    }

    private void alertar1() {
        ImageView imageView = new ImageView(new Image("org/una/aeropuertocliente/views/shared/info.png"));
        Text lab = new Text("Para mostrar datos en este apartado debe realizar el filtro correspondiente");
        lab.setFill(Color.web("#0076a3"));
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(imageView);
        box.getChildren().add(lab);
        tableRoles.setPlaceholder(box);
    }

    private void limpiarTableView() {
        tableRoles.getItems().clear();
        tableRoles.getColumns().clear();
    }

    private void agregarBtnTableView() {
        TableColumn<RolesDTO, Void> colBtn = new TableColumn("Acción");

        Callback<TableColumn<RolesDTO, Void>, TableCell<RolesDTO, Void>> cellFactory = new Callback<TableColumn<RolesDTO, Void>, TableCell<RolesDTO, Void>>() {
            @Override
            public TableCell<RolesDTO, Void> call(final TableColumn<RolesDTO, Void> param) {
                final TableCell<RolesDTO, Void> cell = new TableCell<RolesDTO, Void>() {

                    private final JFXButton btn = new JFXButton("Editar");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            RolesDTO rol = getTableView().getItems().get(getIndex());
                            AppContext.getInstance().set("rol", rol);
                            System.out.println(rol.getDescripcion());
                            cambiarVistaPrincipal("mantenimientoRoles/MantenimientoRoles");
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

        tableRoles.getColumns().add(colBtn);
    }

    public void llenarListaNodos() {
        modDesarrollo.clear();
        modDesarrolloAxiliar.clear();
        modDesarrolloAxiliar.add(titulo.getText());
        modDesarrolloAxiliar.add(lblTable.getText());
        modDesarrolloAxiliar.add(txtBusqueda.getPromptText());
        modDesarrolloAxiliar.add(cmbFiltro.getPromptText());
        modDesarrolloAxiliar.add(cmbEstado.getPromptText());
        modDesarrolloAxiliar.add(btnFiltrar.getText());
        modDesarrolloAxiliar.add(btnRegistrar.getText());
        modDesarrollo.addAll(Arrays.asList(titulo, lblTable, txtBusqueda, cmbFiltro, cmbEstado, btnFiltrar, btnRegistrar));
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
                    dato = tableRoles.getId();
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
