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
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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
    private TextField txtTipoAvion;
    @FXML
    private TextField txtZona;
    @FXML
    private Button btnZona;
    @FXML
    private TextField txtFiltrar;
    @FXML
    private Button btnFiltrar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TableView<VuelosDTO> tableview;
    @FXML
    private Label labTituloAviones;
    @FXML
    private Label labTxtFildTipoAvion;
    @FXML
    private Label labTxtFildMatricula;
    @FXML
    private Label labCombEstado;
    @FXML
    private Label labTxtFildZona;
    @FXML
    private Label labBtnAgregar;
    @FXML
    private Label labTxtFildVuelo;
    @FXML
    private Label labCombFiltro;
    @FXML
    private Label labBtnFiltro;
    @FXML
    private Label labTable;
    @FXML
    private Label labBtn;
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
    private Label lblCancelar;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        aerolinea = (AerolineasDTO) AppContext.getInstance().get("aerolinea");
        avion = (AvionesDTO) AppContext.getInstance().get("agregarAvion");
        combEstado.setItems(FXCollections.observableArrayList("Activo", "Inactivo"));
        combFiltro.setItems(FXCollections.observableArrayList("Id", "Destino", "Origen", "Estado"));
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
            if (AppContext.getInstance().get("zon") != null) {
                ZonasDTO zon;
                zon = (ZonasDTO) AppContext.getInstance().get("zon");
                txtZona.setText(zon.getNombreZona());
            }
            txtTipoAvion.setDisable(true);
            txtMatricula.setDisable(true);
            txtZona.setDisable(true);
            combEstado.setDisable(true);
            txtTipoAvion.setText(avion.getTipoAvion());
            txtMatricula.setText(avion.getMatricula());
            // txtZona.setText(aerolinea.getNombreResponsable());
//            if (VuelosService.(aerolinea.getId()).isEmpty()) {
//                txtFilter.setVisible(false);
//                combFilter.setVisible(false);
//                btnFiltrar.setVisible(false);
//                btnCancelar.setVisible(false);
//                tableAviones.setVisible(false);
//            }
            btnCancelar.setVisible(false);
            btnEditar.setVisible(true);
            btnGuardarEditar.setDisable(true);
            btnZona.setDisable(true);
            btnZona.setDisable(true);

        } else {
            txtTipoAvion.setText("");
            txtMatricula.setText("");
            combEstado.setValue("Activo");

//            avionZonaList = AvionesZonasService.zonaReciente(avion.getId());
//            if(avionZonaList!=null)
//            {
//                txtZona.setText(avionZonaList.getZona().getNombreZona());
//            }
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
//
            btnCancelar.setVisible(false);
            btnEditar.setDisable(true);
            btnGuardarEditar.setVisible(true);
        }
    }

    @FXML
    private void agregarZona(ActionEvent event) {
        PrincipalController.cambiarVistaPrincipal("zonas/Zonas");

    }

    @FXML
    private void btnFiltrar(ActionEvent event) {
        if (combFiltro.getValue() == null || txtFiltrar.getText() == null) {
            notificar(0);
        } else {

            if (combFiltro.getValue().equals("Id") && !txtFiltrar.getText().isEmpty()) {
                tableview.getItems().clear();
                vueloFil = VuelosService.idVuelo(Long.valueOf(txtFiltrar.getText()));
                if (vueloFil != null) {
                    tableview.setItems(FXCollections.observableArrayList(vueloFil));
                } else {
                    notificar(0);
                }
            }
            if (combFiltro.getValue().equals("Estado") && !txtFiltrar.getText().isEmpty()) {
                if (txtFiltrar.getText().toLowerCase().equals("true")) {
                    tableview.getItems().clear();
                    vuelosList = VuelosService.estado(true);
                    if (vuelosList != null) {
                        tableview.setItems(FXCollections.observableArrayList(vuelosList));
                    } else {
                        notificar(0);
                    }
                }
                if (txtFiltrar.getText().equals("false")) {
                    tableview.getItems().clear();
                    vuelosList = VuelosService.estado(false);
                    if (vuelosList != null) {
                        tableview.setItems(FXCollections.observableArrayList(vuelosList));
                    } else {
                        notificar(0);
                    }
                }
                if (vuelosList == null) {
                    notificar(0);
                }
            }
            if (combFiltro.getValue().equals("Origen") && !txtFiltrar.getText().isEmpty()) {
                tableview.getItems().clear();
                vuelosList = VuelosService.Origen(txtFiltrar.getText());
                if (vuelosList != null) {
                    tableview.setItems(FXCollections.observableArrayList(vuelosList));
                } else {
                    notificar(0);
                }
            }
            if (combFiltro.getValue().equals("Destino") && !txtFiltrar.getText().isEmpty()) {
                tableview.getItems().clear();
                vuelosList = VuelosService.Destino(txtFiltrar.getText());
                if (vuelosList != null) {
                    tableview.setItems(FXCollections.observableArrayList(vuelosList));
                } else {
                    notificar(0);
                }
            }
        }
    }

    @FXML
    private void guardar(ActionEvent event) {
    }

    private void llenarVuelos() {
        TableColumn<VuelosDTO, String> colId = new TableColumn("Id");
        colId.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getId().toString()));
        TableColumn<VuelosDTO, String> colOrigen = new TableColumn("Origen");
        colOrigen.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getOrigen()));
        TableColumn<VuelosDTO, String> colDestino = new TableColumn("Destino");
        colDestino.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getDestino()));
        TableColumn<VuelosDTO, String> colFechaInicio = new TableColumn("Fecha Inicio");
        colFechaInicio.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getFechaInicio()));
        TableColumn<VuelosDTO, String> colFechaFinal = new TableColumn("Fecha Final");
        colFechaFinal.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getFechaFinal()));
        TableColumn<VuelosDTO, String> colEstado = new TableColumn("Estado");
        colEstado.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().isEstado()));
        tableview.getColumns().addAll(colId, colOrigen, colDestino, colFechaInicio, colFechaFinal, colEstado);
        notificar(1);
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
            Text lab = new Text("No se encontró coincidencias");
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
        if (avion == null) {
            if (!txtMatricula.getText().isEmpty() && !combEstado.getValue().isEmpty() && !txtTipoAvion.getText().isEmpty() && !txtZona.getText().isEmpty()) {
                avion = new AvionesDTO();
                if (combEstado.getValue().equals("Activo")) {
                    avion.setEstado(true);
                } else {
                    avion.setEstado(false);
                }

                avion.setMatricula(txtMatricula.getText());
                avion.setTipoAvion(txtTipoAvion.getText());
                //avionZona.setAvion(avion);
                //avionZona.setZona((ZonasDTO) AppContext.getInstance().get("zon"));

                if (AvionesService.createAvion(avion) == 201) {
                    //if (AvionesZonasService.createAvionZona(avionZona) == 201) {
                        new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar Aviones", ((Stage) txtMatricula.getScene().getWindow()), "Se guardó correctamente");
                    //}

                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error al guardar los Aviones", ((Stage) txtMatricula.getScene().getWindow()), "No se guardó correctamente");
                }
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error al crear avión", ((Stage) txtMatricula.getScene().getWindow()), "Rellene los campos necesarios");
            }

        } else {
            if (!txtMatricula.getText().isEmpty() && !combEstado.getValue().isEmpty() && !txtTipoAvion.getText().isEmpty() && !txtZona.getText().isEmpty()) {
                if (combEstado.getValue().equals("Activo")) {
                    avion.setEstado(true);
                } else {
                    avion.setEstado(false);
                }
                avion.setMatricula(txtMatricula.getText());
                avion.setTipoAvion(txtTipoAvion.getText());
                //avionZona.setAvion(avion);
                //avionZona.setZona((ZonasDTO) AppContext.getInstance().get("zon"));

                if (AvionesService.updateAvion(avion) == 200) {
                    //if (AvionesZonasService.updateAvionZona(avionZona) == 200) {
                        new Mensaje().showModal(Alert.AlertType.INFORMATION, "Editar Avion", ((Stage) txtMatricula.getScene().getWindow()), "Se editó correctamente");
                        btnEditar.setDisable(false);
                        btnGuardarEditar.setDisable(true);
                        btnVolver.setDisable(false);
                   // }
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error al editar el Avion", ((Stage) txtMatricula.getScene().getWindow()), "No se editó correctamente");
                }
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error al editar el Avion", ((Stage) txtMatricula.getScene().getWindow()), "Rellene los campos necesarios");
            }
        }
    }

    @FXML
    private void onActionVolver(ActionEvent event) {
        PrincipalController.cambiarVistaPrincipal("mantenimientoAerolineas/MantenimientoAerolineas");
    }

}
