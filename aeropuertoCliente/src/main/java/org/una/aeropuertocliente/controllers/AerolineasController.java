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
import javafx.scene.control.Button;
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
import org.una.aeropuertocliente.dtos.AerolineasDTO;
import org.una.aeropuertocliente.entitiesServices.AerolineasService;
import org.una.aeropuertocliente.sharedService.Token;
import org.una.aeropuertocliente.utils.AppContext;

/**
 * FXML Controller class
 *
 * @author Bosco
 */
public class AerolineasController extends Controller implements Initializable {

    @FXML
    private Button btnFiltrar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TableView<AerolineasDTO> tableview;
    @FXML
    private JFXTextField txtFilter;
    @FXML
    private JFXComboBox<String> combFilter;
    @FXML
    private Label labTitulo;
    @FXML
    private Label labTxtFild;
    @FXML
    private Label labComb;
    @FXML
    private Label labBtnFiltrar;
    @FXML
    private Label labBtnCancelar;
    @FXML
    private Label labbtnRegistrarAvion;
    @FXML
    private JFXButton btnRegistrarAvion;

    AerolineasService aerolineaService;

    AerolineasDTO aerolinea;
    AerolineasDTO aerolinea1;
    AerolineasDTO aerolineaFil;
    public List<AerolineasDTO> aerolineaList = new ArrayList<AerolineasDTO>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (!Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_GESTOR")) {
            btnRegistrarAvion.setVisible(false);
            btnRegistrarAvion.setDisable(true);
        } else {
            btnRegistrarAvion.setVisible(true);
            btnRegistrarAvion.setDisable(false);
        }
        aerolinea = null;
        actionAerolineaClick();
        llenarAerolineas();
        combFilter.setItems(FXCollections.observableArrayList("Id", "Nombre Responsable", "Nombre Aerolinea", "Estado"));

    }

    private void llenarAerolineas() {
        TableColumn<AerolineasDTO, String> colId = new TableColumn("Id");
        colId.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getId().toString()));
        TableColumn<AerolineasDTO, String> colNombreAerolinea = new TableColumn("Nombre Aerolinea");
        colNombreAerolinea.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getNombreAerolinea()));
        TableColumn<AerolineasDTO, String> colResponsable = new TableColumn("Nombre Responsable");
        colResponsable.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getNombreResponsable()));
        TableColumn<AerolineasDTO, String> colEstado = new TableColumn("Estado\nActivo(True)Inactivo(False)");
        colEstado.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().isEstado()));
        tableview.getColumns().addAll(colId, colNombreAerolinea, colResponsable, colEstado);
        notificar(1);
    }

    private void actionAerolineaClick() {
        aerolinea = null;

        tableview.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_GESTOR")) {
                    if (mouseEvent.getClickCount() == 2 && tableview.selectionModelProperty().get().getSelectedItem() != null) {
                        aerolinea = (AerolineasDTO) tableview.selectionModelProperty().get().getSelectedItem();
                        AppContext.getInstance().set("aerolinea", aerolinea);
                        PrincipalController.cambiarVistaPrincipal("mantenimientoAerolineas/MantenimientoAerolineas");
                    }

                }
            }
        });
    }

    @FXML
    private void filtrar(ActionEvent event) {
        if (combFilter.getValue().isEmpty() || txtFilter.getText().isEmpty()) {
            notificar(0);
        } else {

            if (combFilter.getValue().equals("Id") && !txtFilter.getText().isEmpty()) {
                tableview.getItems().clear();
                aerolineaFil = AerolineasService.idAerolinea(Long.valueOf(txtFilter.getText()));

                if (aerolineaFil != null) {
                    tableview.setItems(FXCollections.observableArrayList(aerolineaFil));
                } else {
                    notificar(0);
                }
            }
            if (combFilter.getValue().equals("Estado") && !txtFilter.getText().isEmpty()) {
                if (txtFilter.getText().equals("true")) {
                    tableview.getItems().clear();
                    aerolineaList = AerolineasService.estadoAerolinea(true);
                    if (aerolineaList != null) {
                        tableview.setItems(FXCollections.observableArrayList(aerolineaList));
                    } else {
                        notificar(0);
                    }
                }
                if (txtFilter.getText().equals("false")) {
                    tableview.getItems().clear();
                    aerolineaList = AerolineasService.estadoAerolinea(false);
                    if (aerolineaList != null) {
                        tableview.setItems(FXCollections.observableArrayList(aerolineaList));
                    } else {
                        notificar(0);
                    }
                }
                if (aerolineaList == null) {
                    notificar(0);
                }
            }
            if (combFilter.getValue().equals("Nombre Responsable") && !txtFilter.getText().isEmpty()) {
                tableview.getItems().clear();
                aerolineaList = AerolineasService.nombreResponsable(txtFilter.getText());
                tableview.setItems(FXCollections.observableArrayList(aerolineaList));
                if (aerolineaList != null) {
                    tableview.setItems(FXCollections.observableArrayList(aerolineaList));
                } else {
                    notificar(0);
                }
            }
            if (combFilter.getValue().equals("Nombre Aerolinea") && !txtFilter.getText().isEmpty()) {
                tableview.getItems().clear();
                aerolineaList = AerolineasService.nombreAerolinea(txtFilter.getText());
                if (aerolineaList != null) {
                    tableview.setItems(FXCollections.observableArrayList(aerolineaList));
                } else {
                    notificar(0);
                }
            }
        }
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
        } else {
            ImageView imageView2 = new ImageView(new Image("org/una/aeropuertocliente/views/shared/warning.png"));
            Text lab = new Text("No se encontró coincidencias");
            lab.setFill(Color.web("#0076a3"));
            VBox box = new VBox();
            box.setAlignment(Pos.CENTER);
            box.getChildren().add(imageView2);
            box.getChildren().add(lab);
            tableview.setPlaceholder(box);
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
    }

    @FXML
    private void registrarAvion(ActionEvent event) {
        if (Token.getInstance().getUsuario().getRolId().getCodigo().equals("ROLE_GESTOR")) {
            aerolinea = null;
            AppContext.getInstance().set("aerolinea", aerolinea);
            PrincipalController.cambiarVistaPrincipal("mantenimientoAerolineas/MantenimientoAerolineas");
        }
    }

    @Override
    public void initialize() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
