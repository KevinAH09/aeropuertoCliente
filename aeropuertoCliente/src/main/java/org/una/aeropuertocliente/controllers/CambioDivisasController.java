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
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.una.aeropuertocliente.apiForex.TiposMonedasServices;

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
    List<String> listaMonedas = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listaMonedas = Arrays.asList("Colon","Eurodolar","Dolar estadounidense","Yen","Dolar canadiense","Libra esterlina","Franco","Dolar australiano","Dolar neozelandes");
        cbMoneda.setItems(FXCollections.observableArrayList("Colon","Eurodolar","Dolar estadounidense","Yen","Dolar canadiense","Libra esterlina","Franco","Dolar australiano","Dolar neozelandes"));
                     
    }    

    @Override
    public void initialize() {
       
    }

    @FXML
    private void actionComboBox(ActionEvent event) {
        String itemSelect = cbMoneda.getSelectionModel().getSelectedItem();
        if(itemSelect!=null){
            llenarImages(itemSelect);
            lblMontoCambio.setText(String.valueOf(TiposMonedasServices.valorMonedaDolar().valorMoneda()));
        }
    }
    
    private void llenarImages(String itemSelect){
        if(itemSelect.equals("Colon")){
            img1.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/union-europea.png"));
            lbl1.setText("Eurodolar");
            img2.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/estados-unidos-de-america.png"));
            lbl2.setText("Dolar estadounidense");
            img3.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/japon.png"));
            lbl3.setText("Yen");
            img4.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/canada.png"));
            lbl4.setText("Dolar canadiense");
            img5.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/reino-unido.png"));
            lbl5.setText("Libra esterlina");
            img6.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/suiza.png"));
            lbl6.setText("Franco");
            img7.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/australia.png"));
            lbl7.setText("Dolar australiano");
            img8.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/nueva-zelandia.png"));
            lbl8.setText("Dolar neozelandes");
        }
        if(itemSelect.equals("Eurodolar")){
            img1.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/costa-rica.png"));
            lbl1.setText("Colon");
            img2.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/estados-unidos-de-america.png"));
            lbl2.setText("Dolar estadounidense");
            img3.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/japon.png"));
            lbl3.setText("Yen");
            img4.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/canada.png"));
            lbl4.setText("Dolar canadiense");
            img5.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/reino-unido.png"));
            lbl5.setText("Libra esterlina");
            img6.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/suiza.png"));
            lbl6.setText("Franco");
            img7.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/australia.png"));
            lbl7.setText("Dolar australiano");
            img8.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/nueva-zelandia.png"));
            lbl8.setText("Dolar neozelandes");
        }
        if(itemSelect.equals("Dolar estadounidense")){
            img1.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/costa-rica.png"));
            lbl1.setText("Colon");
            img2.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/union-europea.png"));
            lbl2.setText("Eurodolar");
            img3.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/japon.png"));
            lbl3.setText("Yen");
            img4.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/canada.png"));
            lbl4.setText("Dolar canadiense");
            img5.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/reino-unido.png"));
            lbl5.setText("Libra esterlina");
            img6.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/suiza.png"));
            lbl6.setText("Franco");
            img7.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/australia.png"));
            lbl7.setText("Dolar australiano");
            img8.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/nueva-zelandia.png"));
            lbl8.setText("Dolar neozelandes");
        }
        if(itemSelect.equals("Yen")){
            img1.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/costa-rica.png"));
            lbl1.setText("Colon");
            img2.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/union-europea.png"));
            lbl2.setText("Eurodolar");
            img3.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/estados-unidos-de-america.png"));
            lbl3.setText("Dolar estadounidense");
            img4.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/canada.png"));
            lbl4.setText("Dolar canadiense");
            img5.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/reino-unido.png"));
            lbl5.setText("Libra esterlina");
            img6.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/suiza.png"));
            lbl6.setText("Franco");
            img7.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/australia.png"));
            lbl7.setText("Dolar australiano");
            img8.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/nueva-zelandia.png"));
            lbl8.setText("Dolar neozelandes");
        }
        if(itemSelect.equals("Dolar canadiense")){
            img1.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/costa-rica.png"));
            lbl1.setText("Colon");
            img2.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/union-europea.png"));
            lbl2.setText("Eurodolar");
            img3.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/estados-unidos-de-america.png"));
            lbl3.setText("Dolar estadounidense");
            img4.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/japon.png"));
            lbl4.setText("Yen");
            img5.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/reino-unido.png"));
            lbl5.setText("Libra esterlina");
            img6.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/suiza.png"));
            lbl6.setText("Franco");
            img7.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/australia.png"));
            lbl7.setText("Dolar australiano");
            img8.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/nueva-zelandia.png"));
            lbl8.setText("Dolar neozelandes");
        }
        if(itemSelect.equals("Libra esterlina")){
            img1.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/costa-rica.png"));
            lbl1.setText("Colon");
            img2.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/union-europea.png"));
            lbl2.setText("Eurodolar");
            img3.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/estados-unidos-de-america.png"));
            lbl3.setText("Dolar estadounidense");
            img4.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/japon.png"));
            lbl4.setText("Yen");
            img5.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/canada.png"));
            lbl5.setText("Dolar canadiense");
            img6.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/suiza.png"));
            lbl6.setText("Franco");
            img7.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/australia.png"));
            lbl7.setText("Dolar australiano");
            img8.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/nueva-zelandia.png"));
            lbl8.setText("Dolar neozelandes");
        }
        if(itemSelect.equals("Franco")){
            img1.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/costa-rica.png"));
            lbl1.setText("Colon");
            img2.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/union-europea.png"));
            lbl2.setText("Eurodolar");
            img3.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/estados-unidos-de-america.png"));
            lbl3.setText("Dolar estadounidense");
            img4.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/japon.png"));
            lbl4.setText("Yen");
            img5.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/canada.png"));
            lbl5.setText("Dolar canadiense");
            img6.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/reino-unido.png"));
            lbl6.setText("Libra esterlina");
            img7.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/australia.png"));
            lbl7.setText("Dolar australiano");
            img8.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/nueva-zelandia.png"));
            lbl8.setText("Dolar neozelandes");
        }
        if(itemSelect.equals("Dolar australiano")){
            img1.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/costa-rica.png"));
            lbl1.setText("Colon");
            img2.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/union-europea.png"));
            lbl2.setText("Eurodolar");
            img3.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/estados-unidos-de-america.png"));
            lbl3.setText("Dolar estadounidense");
            img4.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/japon.png"));
            lbl4.setText("Yen");
            img5.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/canada.png"));
            lbl5.setText("Dolar canadiense");
            img6.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/reino-unido.png"));
            lbl6.setText("Libra esterlina");
             img7.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/suiza.png"));
            lbl7.setText("Franco");
            img8.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/nueva-zelandia.png"));
            lbl8.setText("Dolar neozelandes");
        }
        if(itemSelect.equals("Dolar neozelandes")){
            img1.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/costa-rica.png"));
            lbl1.setText("Colon");
            img2.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/union-europea.png"));
            lbl2.setText("Eurodolar");
            img3.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/estados-unidos-de-america.png"));
            lbl3.setText("Dolar estadounidense");
            img4.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/japon.png"));
            lbl4.setText("Yen");
            img5.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/canada.png"));
            lbl5.setText("Dolar canadiense");
            img6.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/reino-unido.png"));
            lbl6.setText("Libra esterlina");
             img7.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/suiza.png"));
            lbl7.setText("Franco");
            img8.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/australia.png"));
            lbl8.setText("Dolar australiano");
        }
       
    } 
}
