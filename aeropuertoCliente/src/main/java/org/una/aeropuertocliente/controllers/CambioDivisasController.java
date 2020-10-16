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
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author colo7
 */
public class CambioDivisasController  extends Controller implements Initializable {

    @FXML
    private JFXTextField txtIngresarMonto;
    @FXML
    private JFXComboBox<String> cbMoneda;
    @FXML
    private ImageView img1;
    @FXML
    private ImageView img2;
    @FXML
    private ImageView img3;
    @FXML
    private ImageView img4;
    @FXML
    private ImageView img5;
    @FXML
    private ImageView img6;
    @FXML
    private ImageView img7;
    @FXML
    private ImageView img8;
    @FXML
    private JFXButton btnSalir;
    @FXML
    private JFXButton btnExportarXML;
    @FXML
    private JFXButton btnExportarPDF;
    @FXML
    private Text lbl1;
    @FXML
    private Text lbl2;
    @FXML
    private Text lbl3;
    @FXML
    private Text lbl4;
    @FXML
    private Text lbl5;
    @FXML
    private Text lbl6;
    @FXML
    private Text lbl7;
    @FXML
    private Text lbl8;
    @FXML
    private Label lblselect;
    @FXML
    private ImageView imgSelect;
    @FXML
    private Label lblMontoCambio;
    @FXML
    private Label lblcompra;
    @FXML
    private Label lblVenta;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                cbMoneda.setItems(FXCollections.observableArrayList("Colon","Eurodolar","Dolar estadounidense","Yen","Dolar canadiense","Libra esterlina","Franco","Dolar australiano","Dolar neozelandes"));
                     
    }    

    @Override
    public void initialize() {
       
    }

    @FXML
    private void actionComboBox(ActionEvent event) {
        String itemSelect = cbMoneda.getSelectionModel().getSelectedItem();
        if(itemSelect!=null){
            System.out.println("org.una.aeropuertocliente.controllers.CambioDivisasController.actionComboBox()  "+ itemSelect);
        }
    }
    
}
