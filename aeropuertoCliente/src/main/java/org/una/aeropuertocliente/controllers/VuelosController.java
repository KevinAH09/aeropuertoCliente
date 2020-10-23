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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.una.aeropuertocliente.dtos.AvionesDTO;
import org.una.aeropuertocliente.dtos.VuelosDTO;
import org.una.aeropuertocliente.entitiesServices.AvionesService;
import org.una.aeropuertocliente.entitiesServices.VuelosService;
import org.una.aeropuertocliente.utils.AppContext;
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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Objetoaviones = null;
        Objetoaviones = (AvionesDTO) AppContext.getInstance().get("avionAVuelos");

        actionVueloClick();
        combFilter.setItems(FXCollections.observableArrayList("Id", "Destino", "Origen", "Estado", "Matricula del Avión"));
        txtTipoAvion.setText("");
        txtmatricula.setText("");
        if (Objetoaviones != null) {
            llenarVuelos();
            txtTipoAvion.setText(Objetoaviones.getTipoAvion());
            txtmatricula.setText(Objetoaviones.getMatricula());
            A2=VuelosService.vuelos(Objetoaviones.getId());
            if(A2.isEmpty())
            {
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
    }

    private void actionVueloClick() {
        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2 && tableView.selectionModelProperty().get().getSelectedItem() != null) {
                    VuelosDTO vuelo = (VuelosDTO) tableView.selectionModelProperty().get().getSelectedItem();
                    AppContext.getInstance().set("VueloAMantenimientoVuelo", vuelo);
                    if (Objetoaviones != null) {
                        AppContext.getInstance().set("AvionAMantenimientoVuelo", Objetoaviones);
                    }
                    PrincipalController.cambiarVistaPrincipal("mantenimientoVuelos/MantenimientoVuelos");
                }

            }
        });
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
        colEstado.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getFechaFinal()));
        TableColumn<VuelosDTO, String> colTipoBicatora = new TableColumn("Tipo Bitácora");
        colTipoBicatora.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getBitacoraVueloId().getTipoBitacora()));
        tableView.getColumns().addAll(colId, colOrigen, colDestino, colFechaInicio, colFechaFinal, colEstado, colTipoBicatora);
        notificar(1);
    }

    @FXML
    private void filtrar(ActionEvent event) {
        if (combFilter.getValue().isEmpty() || txtFilter.getText().isEmpty()) {
            notificar(0);
        } else {
            if (combFilter.getValue().equals("Id") && !txtFilter.getText().isEmpty()) {
                tableView.getItems().clear();
                vuelosFil = VuelosService.idVuelo(Long.valueOf(txtFilter.getText()));
                if (vuelosFil != null) {
                    tableView.setItems(FXCollections.observableArrayList(vuelosFil));
                } else {
                    notificar(0);
                }
            }
            if (combFilter.getValue().equals("Estado") && !txtFilter.getText().isEmpty()) {
                if (txtFilter.getText().equals("true")) {
                    tableView.getItems().clear();
                    vuelosList = VuelosService.estado(true);
                    if (vuelosList != null) {
                        tableView.setItems(FXCollections.observableArrayList(vuelosList));
                    } else {
                        notificar(0);
                    }
                }
                if (txtFilter.getText().equals("false")) {
                    tableView.getItems().clear();
                    vuelosList = VuelosService.estado(false);
                    if (vuelosList != null) {
                        tableView.setItems(FXCollections.observableArrayList(vuelosList));
                    } else {
                        notificar(0);
                    }
                }
            }
            if (combFilter.getValue().equals("Destino") && !txtFilter.getText().isEmpty()) {
                tableView.getItems().clear();
                vuelosList = VuelosService.Destino(txtFilter.getText());
                if (vuelosList != null) {
                    tableView.setItems(FXCollections.observableArrayList(vuelosList));
                } else {
                    notificar(0);
                }
            }
            if (combFilter.getValue().equals("Origen") && !txtFilter.getText().isEmpty()) {
                tableView.getItems().clear();
                vuelosList = VuelosService.Origen(txtFilter.getText());
                if (vuelosList != null) {
                    tableView.setItems(FXCollections.observableArrayList(vuelosList));
                } else {
                    notificar(0);
                }
            }
            if (combFilter.getValue().equals("Matricula del Avión") && !txtFilter.getText().isEmpty()) {
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
        }
    }

    @FXML
    private void registrarVuelos(ActionEvent event) {
        if (Objetoaviones != null) {
            AppContext.getInstance().set("AvionAMantenimientoVuelo", Objetoaviones);
            AppContext.getInstance().set("VueloAMantenimientoVuelo", null);
        }
        PrincipalController.cambiarVistaPrincipal("mantenimientoVuelos/MantenimientoVuelos");
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
            ImageView imageView = new ImageView(new Image("org/una/aeropuertocliente/views/shared/info.png"));
            Text lab = new Text("Para mostrar datos en este apartado debe realizar el filtro correspondiente");
            lab.setFill(Color.web("#0076a3"));
            VBox box = new VBox();
            box.setAlignment(Pos.CENTER);
            box.getChildren().add(imageView);
            box.getChildren().add(lab);
            tableView.setPlaceholder(box);
        }
        if (num == 0) {
            ImageView imageView2 = new ImageView(new Image("org/una/aeropuertocliente/views/shared/warning.png"));
            Text lab = new Text("No se encontró coincidencias");
            lab.setFill(Color.web("#0076a3"));
            VBox box = new VBox();
            box.setAlignment(Pos.CENTER);
            box.getChildren().add(imageView2);
            box.getChildren().add(lab);
            tableView.setPlaceholder(box);
        }
        if (num == 2) {
            ImageView imageView2 = new ImageView(new Image("org/una/aeropuertocliente/views/shared/warning.png"));
            Text lab = new Text("No hay vuelos en este avion, porfavor regitrar vuelos");
            lab.setFill(Color.web("#0076a3"));
            VBox box = new VBox();
            box.setAlignment(Pos.CENTER);
            box.getChildren().add(imageView2);
            box.getChildren().add(lab);
            tableView.setPlaceholder(box);

        }
    }

    @FXML
    private void filtrar1(ActionEvent event) {
    }
}
