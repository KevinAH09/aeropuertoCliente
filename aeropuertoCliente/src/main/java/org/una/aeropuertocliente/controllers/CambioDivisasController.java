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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.una.aeropuertocliente.apiForex.TiposMonedasServices;
import org.una.aeropuertocliente.utils.AppContext;
import org.una.aeropuertocliente.utils.Mensaje;

/**
 * FXML Controller class
 *
 * @author colo7
 */
public class CambioDivisasController extends Controller implements Initializable {

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
    private Text lblMontoCambio;
    @FXML
    private Label lblcompra;
    @FXML
    private Label lblVenta;
    List<String> listaMonedas = new ArrayList<>();

    double USDEuros;
    double USDCostaRica;
    double USDLibraEstaerlina;
    double USDAustralia;
    double USDNuevaZelanda;
    double USDCanada;
    double USDFranco;
    double USDYen;
    String itemSelect = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (!(boolean) AppContext.getInstance().get("cambiodivisas")) {
            btnSalir.setDisable(true);
            btnSalir.setVisible(false);
        } else {
            btnSalir.setDisable(false);
            btnSalir.setVisible(true);
        }

        USDCostaRica = TiposMonedasServices.valorMonedaDolarVSColon().valorMoneda();

        USDEuros = TiposMonedasServices.valorMonedaDolarVSEuros().valorMoneda();

        USDYen = TiposMonedasServices.valorMonedaDolarVSYenJapones().valorMoneda();

        USDCanada = TiposMonedasServices.valorMonedaDolarVSCanadaDolar().valorMoneda();

        USDLibraEstaerlina = TiposMonedasServices.valorMonedaDolarVSLibraEsterlina().valorMoneda();

        USDFranco = TiposMonedasServices.valorMonedaDolarVSFrancoSuizo().valorMoneda();

        USDAustralia = TiposMonedasServices.valorMonedaDolarVSAustraliaDolar().valorMoneda();

        USDNuevaZelanda = TiposMonedasServices.valorMonedaDolarVSNuevaZelandaDolar().valorMoneda();

        listaMonedas = Arrays.asList("Colon", "Eurodolar", "Dolar estadounidense", "Yen", "Dolar canadiense", "Libra esterlina", "Franco", "Dolar australiano", "Dolar neozelandes");

        cbMoneda.setItems(FXCollections.observableArrayList("Colon", "Eurodolar", "Dolar estadounidense", "Yen", "Dolar canadiense", "Libra esterlina", "Franco", "Dolar australiano", "Dolar neozelandes"));
        cbMoneda.setValue("Colon");
        itemSelect = "Colon";

    }

    @Override
    public void initialize() {

    }

    @FXML
    private void actionComboBox(ActionEvent event) {
        itemSelect = null;
        itemSelect = cbMoneda.getSelectionModel().getSelectedItem();
        if (itemSelect != null) {
            txtIngresarMonto.setText(null);
            lblMontoCambio.setText(null);
            lblselect.setText(null);
            imgSelect.setImage(null);
            llenarImages();
        }
    }

    private void llenarImages() {
        if (itemSelect.equals("Colon")) {
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
        if (itemSelect.equals("Eurodolar")) {
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
        if (itemSelect.equals("Dolar estadounidense")) {
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
        if (itemSelect.equals("Yen")) {
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
        if (itemSelect.equals("Dolar canadiense")) {
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
        if (itemSelect.equals("Libra esterlina")) {
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
        if (itemSelect.equals("Franco")) {
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
        if (itemSelect.equals("Dolar australiano")) {
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
        if (itemSelect.equals("Dolar neozelandes")) {
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

    @FXML
    private void keyPressMonto(KeyEvent event) {
    }

    @FXML
    private void actionImg1(MouseEvent event) {

        llenarDatos(lbl1.getText());
    }

    @FXML
    private void actionImg2(MouseEvent event) {

        llenarDatos(lbl2.getText());
    }

    @FXML
    private void actionImg3(MouseEvent event) {

        llenarDatos(lbl3.getText());
    }

    @FXML
    private void actionImg4(MouseEvent event) {
        llenarDatos(lbl4.getText());
    }

    @FXML
    private void actionImg5(MouseEvent event) {
        llenarDatos(lbl5.getText());
    }

    @FXML
    private void actionImg6(MouseEvent event) {
        llenarDatos(lbl6.getText());
    }

    @FXML
    private void actionImg7(MouseEvent event) {
        llenarDatos(lbl7.getText());
    }

    @FXML
    private void actionImg8(MouseEvent event) {
        llenarDatos(lbl8.getText());
    }

    private void llenarDatos(String monedaSelect) {
        if (!txtIngresarMonto.getText().isEmpty() && cbMoneda.getValue() != null) {
            llenarMonto(monedaSelect);
            llenarImgSelect(monedaSelect);
            lblselect.setText(monedaSelect);
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error al calcular monto", null, "Monto no digitado");
        }

    }

    private void llenarImgSelect(String selectImg) {
        if (selectImg.equals("Colon")) {
            imgSelect.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/costa-rica.png"));
        } else if (selectImg.equals("Eurodolar")) {
            imgSelect.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/union-europea.png"));
        } else if (selectImg.equals("Dolar estadounidense")) {
            imgSelect.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/estados-unidos-de-america.png"));
        } else if (selectImg.equals("Yen")) {
            imgSelect.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/japon.png"));
        } else if (selectImg.equals("Dolar canadiense")) {
            imgSelect.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/canada.png"));
        } else if (selectImg.equals("Libra esterlina")) {
            imgSelect.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/reino-unido.png"));
        } else if (selectImg.equals("Franco")) {
            imgSelect.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/suiza.png"));
        } else if (selectImg.equals("Dolar australiano")) {
            imgSelect.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/australia.png"));
        } else if (selectImg.equals("Dolar neozelandes")) {
            imgSelect.setImage(new Image("org/una/aeropuertocliente/views/cambioDivisas/nueva-zelandia.png"));

        }

    }

    private void llenarMonto(String selectImg) {

        double monto = Double.valueOf(txtIngresarMonto.getText());
        if (itemSelect.equals("Colon")) {
            if (selectImg.equals("Eurodolar")) {
                lblMontoCambio.setText(String.format("%.2f", (monto / USDCostaRica) * USDEuros));
            } else if (selectImg.equals("Dolar estadounidense")) {
                lblMontoCambio.setText(String.format("%.2f", (monto / USDCostaRica)));
            } else if (selectImg.equals("Yen")) {
                lblMontoCambio.setText(String.format("%.2f", (monto / USDCostaRica) * USDYen));
            } else if (selectImg.equals("Dolar canadiense")) {
                lblMontoCambio.setText(String.format("%.2f", (monto / USDCostaRica) * USDCanada));
            } else if (selectImg.equals("Libra esterlina")) {
                lblMontoCambio.setText(String.format("%.2f", (monto / USDCostaRica) * USDLibraEstaerlina));
            } else if (selectImg.equals("Franco")) {
                lblMontoCambio.setText(String.format("%.2f", (monto / USDCostaRica) * USDFranco));
            } else if (selectImg.equals("Dolar australiano")) {
                lblMontoCambio.setText(String.format("%.2f", (monto / USDCostaRica) * USDAustralia));
            } else if (selectImg.equals("Dolar neozelandes")) {
                lblMontoCambio.setText(String.format("%.2f", (monto / USDCostaRica) * USDNuevaZelanda));
            }

        } else if (itemSelect.equals("Eurodolar")) {
            if (selectImg.equals("Colon")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDEuros) * USDCostaRica));
            } else if (selectImg.equals("Dolar estadounidense")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDEuros)));
            } else if (selectImg.equals("Yen")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDEuros) * USDYen));
            } else if (selectImg.equals("Dolar canadiense")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDEuros) * USDCanada));
            } else if (selectImg.equals("Libra esterlina")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDEuros) * USDLibraEstaerlina));
            } else if (selectImg.equals("Franco")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDEuros) * USDFranco));
            } else if (selectImg.equals("Dolar australiano")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDEuros) * USDAustralia));
            } else if (selectImg.equals("Dolar neozelandes")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDEuros) * USDNuevaZelanda));
            }

        } else if (itemSelect.equals("Dolar estadounidense")) {
            if (selectImg.equals("Colon")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDCostaRica)));
            } else if (selectImg.equals("Eurodolar")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDEuros)));
            } else if (selectImg.equals("Yen")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDYen)));
            } else if (selectImg.equals("Dolar canadiense")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDCanada)));
            } else if (selectImg.equals("Libra esterlina")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDLibraEstaerlina)));
            } else if (selectImg.equals("Franco")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDFranco)));
            } else if (selectImg.equals("Dolar australiano")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDAustralia)));
            } else if (selectImg.equals("Dolar neozelandes")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDNuevaZelanda)));
            }
        } else if (itemSelect.equals("Yen")) {
            if (selectImg.equals("Colon")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDYen) * USDCostaRica));
            } else if (selectImg.equals("Dolar estadounidense")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDYen)));
            } else if (selectImg.equals("Eurodolar")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDYen) * USDEuros));
            } else if (selectImg.equals("Dolar canadiense")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDYen) * USDCanada));
            } else if (selectImg.equals("Libra esterlina")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDYen) * USDLibraEstaerlina));
            } else if (selectImg.equals("Franco")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDYen) * USDFranco));
            } else if (selectImg.equals("Dolar australiano")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDYen) * USDAustralia));
            } else if (selectImg.equals("Dolar neozelandes")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDYen) * USDNuevaZelanda));
            }

        } else if (itemSelect.equals("Dolar canadiense")) {
            if (selectImg.equals("Colon")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDCanada) * USDCostaRica));
            } else if (selectImg.equals("Dolar estadounidense")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDCanada)));
            } else if (selectImg.equals("Eurodolar")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDCanada) * USDEuros));
            } else if (selectImg.equals("Yen")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDCanada) * USDYen));
            } else if (selectImg.equals("Libra esterlina")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDCanada) * USDLibraEstaerlina));
            } else if (selectImg.equals("Franco")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDCanada) * USDFranco));
            } else if (selectImg.equals("Dolar australiano")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDCanada) * USDAustralia));
            } else if (selectImg.equals("Dolar neozelandes")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDCanada) * USDNuevaZelanda));
            }

        } else if (itemSelect.equals("Libra esterlina")) {
            if (selectImg.equals("Colon")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDLibraEstaerlina) * USDCostaRica));
            } else if (selectImg.equals("Dolar estadounidense")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDLibraEstaerlina)));
            } else if (selectImg.equals("Eurodolar")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDLibraEstaerlina) * USDEuros));
            } else if (selectImg.equals("Dolar canadiense")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDLibraEstaerlina) * USDCanada));
            } else if (selectImg.equals("Yen")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDLibraEstaerlina) * USDYen));
            } else if (selectImg.equals("Franco")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDLibraEstaerlina) * USDFranco));
            } else if (selectImg.equals("Dolar australiano")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDLibraEstaerlina) * USDAustralia));
            } else if (selectImg.equals("Dolar neozelandes")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDLibraEstaerlina) * USDNuevaZelanda));
            }

        } else if (itemSelect.equals("Franco")) {
            if (selectImg.equals("Colon")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDFranco) * USDCostaRica));
            } else if (selectImg.equals("Dolar estadounidense")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDFranco)));
            } else if (selectImg.equals("Eurodolar")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDFranco) * USDEuros));
            } else if (selectImg.equals("Yen")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDFranco) * USDYen));
            } else if (selectImg.equals("Libra esterlina")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDFranco) * USDLibraEstaerlina));
            } else if (selectImg.equals("Dolar canadiense")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDFranco) * USDCanada));
            } else if (selectImg.equals("Dolar australiano")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDFranco) * USDAustralia));
            } else if (selectImg.equals("Dolar neozelandes")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDFranco) * USDNuevaZelanda));
            }

        } else if (itemSelect.equals("Dolar australiano")) {
            if (selectImg.equals("Colon")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDAustralia) * USDCostaRica));
            } else if (selectImg.equals("Dolar estadounidense")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDAustralia)));
            } else if (selectImg.equals("Eurodolar")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDAustralia) * USDEuros));
            } else if (selectImg.equals("Dolar canadiense")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDAustralia) * USDCanada));
            } else if (selectImg.equals("Yen")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDAustralia) * USDYen));
            } else if (selectImg.equals("Franco")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDAustralia) * USDFranco));
            } else if (selectImg.equals("Libra esterlina")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDAustralia) * USDLibraEstaerlina));
            } else if (selectImg.equals("Dolar neozelandes")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDAustralia) * USDNuevaZelanda));
            }

        } else if (itemSelect.equals("Dolar neozelandes")) {
            if (selectImg.equals("Colon")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDNuevaZelanda) * USDCostaRica));
            } else if (selectImg.equals("Dolar estadounidense")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDNuevaZelanda)));
            } else if (selectImg.equals("Eurodolar")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDNuevaZelanda) * USDEuros));
            } else if (selectImg.equals("Yen")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDNuevaZelanda) * USDYen));
            } else if (selectImg.equals("Libra esterlina")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDNuevaZelanda) * USDLibraEstaerlina));
            } else if (selectImg.equals("Dolar canadiense")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDNuevaZelanda) * USDCanada));
            } else if (selectImg.equals("Dolar australiano")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDNuevaZelanda) * USDAustralia));
            } else if (selectImg.equals("Franco")) {

                lblMontoCambio.setText(String.format("%.2f", (monto / USDNuevaZelanda) * USDFranco));
            }

        }

    }

    @FXML
    private void actionSalir(ActionEvent event) {
        if (new Mensaje().showConfirmation("Cerrar cambio divisas", ((Stage) lblMontoCambio.getScene().getWindow()), "Desea salir de cambio de divisas")) {
            ((Stage) lblMontoCambio.getScene().getWindow()).close();
        }
    }

}
