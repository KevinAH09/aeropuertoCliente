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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.una.aeropuertocliente.dtos.AvionesDTO;
import org.una.aeropuertocliente.entitiesServices.AvionesService;
import org.una.aeropuertocliente.utils.AppContext;
import org.una.aeropuertocliente.utils.Mensaje;

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
    @FXML
    private TableView<AvionesDTO> tableAvion;
    @FXML
    private JFXButton btnVolverVuelos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        actionAvionClick();
        llenarAviones();
        combFilter.setItems(FXCollections.observableArrayList("Id", "Matrícula","Tipo de avión","Estado","Nombre aerolinea"));

    }

    @FXML
    private void filtrar(ActionEvent event) {
        if (combFilter.getValue().isEmpty() || txtFilter.getText().isEmpty()) {
            notificar(0);
        } else {
            if (combFilter.getValue().equals("Id") && !txtFilter.getText().isEmpty()) {
                tableAvion.getItems().clear();
                avionesFil = AvionesService.idAvion(Long.valueOf(txtFilter.getText()));
                if (avionesFil != null) {
                    tableAvion.setItems(FXCollections.observableArrayList(avionesFil));
                } else {
                    notificar(0);
                }
            }
            if (combFilter.getValue().equals("Estado") && !txtFilter.getText().isEmpty()) {
                if (txtFilter.getText().toLowerCase().equals("true")) {
                    tableAvion.getItems().clear();
                    avionesList = AvionesService.estado(true);
                    if (avionesList != null) {
                        tableAvion.setItems(FXCollections.observableArrayList(avionesList));
                    } else {
                        notificar(0);
                    }
                }
                if (txtFilter.getText().equals("false")) {
                    tableAvion.getItems().clear();
                    avionesList = AvionesService.estado(false);
                    if (avionesList != null) {
                        tableAvion.setItems(FXCollections.observableArrayList(avionesList));
                    } else {
                        notificar(0);
                    }
                }
            }
            if (combFilter.getValue().equals("Matrícula") && !txtFilter.getText().isEmpty()) {
                tableAvion.getItems().clear();
                avionesList = AvionesService.matricula(txtFilter.getText());
                if (avionesList != null) {
                    tableAvion.setItems(FXCollections.observableArrayList(avionesList));
                } else {
                    notificar(0);
                }
            }
            if (combFilter.getValue().equals("Tipo de avión") && !txtFilter.getText().isEmpty()) {
                tableAvion.getItems().clear();
                avionesList = AvionesService.TipoAvion(txtFilter.getText());
                if (avionesList != null) {
                    tableAvion.setItems(FXCollections.observableArrayList(avionesList));
                } else {
                    notificar(0);
                }
            }
            if (combFilter.getValue().equals("Nombre aerolinea") && !txtFilter.getText().isEmpty()) {

                tableAvion.getItems().clear();
                avionesList = AvionesService.allAviones();
                for (int i = 0; i < avionesList.size(); i++) {
                    if (avionesList.get(i).getAerolineaId().getNombreAerolinea().equals(txtFilter.getText())) {
                        avionesList2 = AvionesService.aerolinea(avionesList.get(i).getAerolineaId().getId());
                    }

                }
                if (!avionesList2.isEmpty()) {
                    tableAvion.setItems(FXCollections.observableArrayList(avionesList2));
                }else {
                    notificar(0);
                }
            }
        }

    }

    private void notificar(int num) {
        tableAvion.getItems().clear();
        if (num == 1) {
            ImageView imageView = new ImageView(new Image("org/una/aeropuertocliente/views/shared/info.png"));
            Text lab = new Text("Para mostrar datos en este apartado debe realizar el filtro correspondiente");
            lab.setFill(Color.web("#0076a3"));
            VBox box = new VBox();
            box.setAlignment(Pos.CENTER);
            box.getChildren().add(imageView);
            box.getChildren().add(lab);
            tableAvion.setPlaceholder(box);
        }
        if (num == 0) {
            ImageView imageView2 = new ImageView(new Image("org/una/aeropuertocliente/views/shared/warning.png"));
            Text lab = new Text("No se encontró coincidencias");
            lab.setFill(Color.web("#0076a3"));
            VBox box = new VBox();
            box.setAlignment(Pos.CENTER);
            box.getChildren().add(imageView2);
            box.getChildren().add(lab);
            tableAvion.setPlaceholder(box);
        }
        if (num == 2) {
            ImageView imageView2 = new ImageView(new Image("org/una/aeropuertocliente/views/shared/warning.png"));
            Text lab = new Text("No hay vuelos en este avion, porfavor regitrar vuelos");
            lab.setFill(Color.web("#0076a3"));
            VBox box = new VBox();
            box.setAlignment(Pos.CENTER);
            box.getChildren().add(imageView2);
            box.getChildren().add(lab);
            tableAvion.setPlaceholder(box);

        }
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
        TableColumn<AvionesDTO, String> colEstado = new TableColumn("Activo(true)Inactivo(False)");
        colEstado.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().isEstado()));
        TableColumn<AvionesDTO, String> colAerolinea = new TableColumn("Nombre aerolinea");
        colAerolinea.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getAerolineaId().getNombreAerolinea()));
        tableAvion.getColumns().addAll(colId, colMatricula, colTipoAvion, colHoraVuelo, colEstado, colAerolinea);
        notificar(1);
    }

    @Override
    public void initialize() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void volver(ActionEvent event) {
        PrincipalController.cambiarVistaPrincipal("vuelos/Vuelos");
    }

}
