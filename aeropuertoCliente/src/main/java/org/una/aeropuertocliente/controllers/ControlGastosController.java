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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Date;
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
import org.una.aeropuertocliente.dtos.ControlesGastosDTO;
import org.una.aeropuertocliente.dtos.DetallesControlesGastosDTO;
import org.una.aeropuertocliente.dtos.ZonasDTO;
import org.una.aeropuertocliente.entitiesServices.ControlGastosService;
import org.una.aeropuertocliente.utils.AppContext;
import org.una.aeropuertocliente.utils.Mensaje;

/**
 * FXML Controller class
 *
 * @author cfugu
 */
public class ControlGastosController extends Controller implements Initializable {

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
    private TableView<ControlesGastosDTO> tableGastos;
    @FXML
    private Label lblRegistrar;
    @FXML
    private JFXButton btnRegistrar;
    String mensaje;
    ControlesGastosDTO gastos;
    ControlesGastosDTO gastosFilt;
    public List<ControlesGastosDTO> gastosList = new ArrayList<ControlesGastosDTO>();
    public List<DetallesControlesGastosDTO> detallesList = new ArrayList<DetallesControlesGastosDTO>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbFiltro.setItems(FXCollections.observableArrayList("Id", "Empresa", /*"Fecha",*/ "Contrato", "Estado", "Tipo"));
        actionControlClick();
        llenarGastos();
    }

    @FXML
    private void onActionFiltrar(ActionEvent event) throws ParseException {
        if (txtBusqueda.getText() == null || txtBusqueda.getText().isEmpty() || cmbFiltro.getValue().isEmpty()) {
            tableGastos.getItems().clear();
            mensaje = "Por favor debe ingresar un datos en el campo de búsqueda";
            notificar(0);
        }
        if (cmbFiltro.getValue().equals("Id") && !txtBusqueda.getText().isEmpty()) {
            tableGastos.getItems().clear();
            gastosFilt = ControlGastosService.idControlGasto(Long.valueOf(txtBusqueda.getText()));
            if (gastosFilt != null) {
                tableGastos.setItems(FXCollections.observableArrayList(gastosFilt));
            } else {
                mensaje = "No se encontró coincidencias";
                notificar(0);
            }
        }
        if (cmbFiltro.getValue().equals("Empresa") && !txtBusqueda.getText().isEmpty()) {
            tableGastos.getItems().clear();
            gastosList = ControlGastosService.empresaControlesGastos(txtBusqueda.getText());
            if (gastosList != null) {
                tableGastos.setItems(FXCollections.observableArrayList(gastosList));
            } else {
                mensaje = "No se encontró coincidencias";
                notificar(0);
            }
        }
        if (cmbFiltro.getValue().equals("Contrato") && !txtBusqueda.getText().isEmpty()) {
            tableGastos.getItems().clear();
            gastosList = ControlGastosService.contratoControlesGastos(txtBusqueda.getText());
            if (gastosList != null) {
                tableGastos.setItems(FXCollections.observableArrayList(gastosList));
            } else {
                mensaje = "No se encontró coincidencias";
                notificar(0);
            }
        }
        if (cmbFiltro.getValue().equals("Tipo") && !txtBusqueda.getText().isEmpty()) {
            tableGastos.getItems().clear();
            gastosList = ControlGastosService.tipoControlesGastos(txtBusqueda.getText());
            if (gastosList != null) {
                tableGastos.setItems(FXCollections.observableArrayList(gastosList));
            } else {
                mensaje = "No se encontró coincidencias";
                notificar(0);
            }
        }
        if (cmbFiltro.getValue().equals("Estado") && !txtBusqueda.getText().isEmpty()) {
            tableGastos.getItems().clear();
            gastosList = ControlGastosService.estadoControlesGastos(txtBusqueda.getText());
            if (gastosList != null) {
                tableGastos.setItems(FXCollections.observableArrayList(gastosList));
            } else {
                mensaje = "No se encontró coincidencias";
                notificar(0);
            }
        }

        if (cmbFiltro.getValue().equals("Fecha") && !txtBusqueda.getText().isEmpty()) {
            tableGastos.getItems().clear();
            String data = txtBusqueda.getText();
            String sDate1 = data.substring(0, 10);
            String sDate2 = data.substring(11, 21);
//            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(sDate1);
//            Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(sDate2);
//            System.out.println(date1);
//            System.out.println(date2);

            gastosList = ControlGastosService.fechaControlesGastos(sDate1, sDate2);
            if (gastosList != null) {
                tableGastos.setItems(FXCollections.observableArrayList(gastosList));
            } else {
                mensaje = "No se encontró coincidencias";
                notificar(0);
            }
        }
    }

    @FXML
    private void onActionRegistrar(ActionEvent event) {
        AppContext.getInstance().set("control", null);
        PrincipalController.cambiarVistaPrincipal("mantenimientoControlGastos/MantenimientoControlGastos");
    }

    private void llenarGastos() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
// Aqui usamos la instancia formatter para darle el formato a la fecha. Es importante ver que el resultado es un string.
//        String fechaTexto = formatter.format(fecha);
        TableColumn<ControlesGastosDTO, String> colId = new TableColumn("Id");
        colId.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getId().toString()));
        TableColumn<ControlesGastosDTO, String> colEmpresa = new TableColumn("Empresa");
        colEmpresa.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getEmpresaContratante()));
        TableColumn<ControlesGastosDTO, String> colContrato = new TableColumn("Contrato");
        colContrato.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getNumeroContrato()));
        TableColumn<ControlesGastosDTO, String> colResponsable = new TableColumn("Responsable");
        colResponsable.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getResponsable()));
        TableColumn<ControlesGastosDTO, String> colFecha = new TableColumn("Fecha");
        colFecha.setCellValueFactory((param) -> new SimpleObjectProperty(formatter.format(param.getValue().getFechaRegistro())));
        TableColumn<ControlesGastosDTO, String> colTipo = new TableColumn("Tipo");
        colTipo.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getDetalleControlGastoId().getTipoServicio()));
        TableColumn<ControlesGastosDTO, String> colEstado = new TableColumn("Estado pago");
        colEstado.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getDetalleControlGastoId().getEstadoPago()));
        TableColumn<ControlesGastosDTO, String> colArea = new TableColumn("Area");
        colArea.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getDetalleControlGastoId().getAreaTrabajoId().getNombreAreaTrabajo()));
        tableGastos.getColumns().addAll(colId, colEmpresa, colContrato, colResponsable, colFecha, colTipo, colEstado, colArea);
//        notificar(1); 
        try {
            gastosList = ControlGastosService.allControlesGastos();
            if (gastosList != null && !gastosList.isEmpty()) {
                tableGastos.setItems(FXCollections.observableArrayList(gastosList));
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error de tramite", null, "La lista está nula o vacía");
            }
        } catch (Exception e) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de tramite", null, "Hubo un error al obtener los datos a cargar");
        }
    }

    private void actionControlClick() {
        tableGastos.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2 && tableGastos.selectionModelProperty().get().getSelectedItem() != null) {
                    ControlesGastosDTO control = (ControlesGastosDTO) tableGastos.selectionModelProperty().get().getSelectedItem();
                    AppContext.getInstance().set("control", control);
                    System.out.println(control.getNumeroContrato());
                    PrincipalController.cambiarVistaPrincipal("mantenimientoControlGastos/MantenimientoControlGastos");
//                    ((Stage) btnFiltrar.getScene().getWindow()).close();
                }

            }
        });
    }

    private void notificar(int num) {
        tableGastos.getItems().clear();
        if (num == 1) {
            ImageView imageView = new ImageView(new Image("org/una/aeropuertocliente/views/shared/info.png"));
            Text lab = new Text("Para mostrar datos en este apartado debe realizar el filtro correspondiente");
            lab.setFill(Color.web("#0076a3"));
            VBox box = new VBox();
            box.setAlignment(Pos.CENTER);
            box.getChildren().add(imageView);
            box.getChildren().add(lab);
            tableGastos.setPlaceholder(box);
        } else {
            ImageView imageView2 = new ImageView(new Image("org/una/aeropuertocliente/views/shared/warning.png"));
            Text lab = new Text(mensaje);
            lab.setFill(Color.web("#0076a3"));
            VBox box = new VBox();
            box.setAlignment(Pos.CENTER);
            box.getChildren().add(imageView2);
            box.getChildren().add(lab);
            tableGastos.setPlaceholder(box);
        }
    }

    @Override
    public void initialize() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
