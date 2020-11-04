/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.una.aeropuertocliente.apiForex.TiposMonedasServices;
import org.una.aeropuertocliente.utils.AppContext;
import org.una.aeropuertocliente.utils.Mensaje;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
    private List<String> listaMoneas = new ArrayList<>();
    public List<Node> modDesarrollo = new ArrayList<>();
    public List<String> modDesarrolloAxiliar = new ArrayList<>();
    private List<String> cambios = new ArrayList<>();
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

        } else {
//           
        }

        USDCostaRica = TiposMonedasServices.valorMonedaDolarVSColon().valorMoneda();

        USDEuros = TiposMonedasServices.valorMonedaDolarVSEuros().valorMoneda();

        USDYen = TiposMonedasServices.valorMonedaDolarVSYenJapones().valorMoneda();

        USDCanada = TiposMonedasServices.valorMonedaDolarVSCanadaDolar().valorMoneda();

        USDLibraEstaerlina = TiposMonedasServices.valorMonedaDolarVSLibraEsterlina().valorMoneda();

        USDFranco = TiposMonedasServices.valorMonedaDolarVSFrancoSuizo().valorMoneda();

        USDAustralia = TiposMonedasServices.valorMonedaDolarVSAustraliaDolar().valorMoneda();

        USDNuevaZelanda = TiposMonedasServices.valorMonedaDolarVSNuevaZelandaDolar().valorMoneda();

        listaMoneas = Arrays.asList("Colon", "Eurodolar", "Dolar estadounidense", "Yen", "Dolar canadiense", "Libra esterlina", "Franco", "Dolar australiano", "Dolar neozelandes");

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

    void generarXML() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            Document document = implementation.createDocument(null, "AeropuertoUNA", null);
            document.setXmlVersion("1.0");

            //Main Node
            Element raiz = document.getDocumentElement();
            Element itemMoneda = document.createElement(cbMoneda.getValue());
            Element keyMonto = document.createElement(txtIngresarMonto.getText());
            itemMoneda.appendChild(keyMonto);
            raiz.appendChild(itemMoneda);
            Element intemDetalle = document.createElement("Cambios");
            //Por cada key creamos un item que contendrá la key y el value
            llenarMontosReportes();
            for (int i = 0; i < cambios.size(); i++) {
                //Item Node

                //Key Node
                Element keyNode = document.createElement("Cambio");
                org.w3c.dom.Text nodeKeyValue = document.createTextNode(cambios.get(i));
                keyNode.appendChild(nodeKeyValue);
                //append keyNode and valueNode to itemNode
                intemDetalle.appendChild(keyNode);
                //append itemNode to raiz
                raiz.appendChild(intemDetalle); //pegamos el elemento a la raiz "Documento"
            }
            //Generate XML
            Source source = new DOMSource(document);
            DirectoryChooser filChoser = new DirectoryChooser();
            //Indicamos donde lo queremos almacenar
            Result result = new StreamResult(new java.io.File(filChoser.showDialog((Stage) lblselect.getScene().getWindow()).getAbsoluteFile().getPath() + "/KevinXML.xml")); //nombre del archivo
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(CambioDivisasController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(CambioDivisasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void generarPDF() {

        try {
            Date fec = new Date();

            PDDocument pdf = new PDDocument();//crea la instancia
            PDPage page = new PDPage();//crea la pagina
            pdf.addPage(page);//agregamos la pagina al pdf
            PDPageContentStream content = new PDPageContentStream(pdf, page);//aqui escribimos

            content.beginText();
            content.setFont(PDType1Font.COURIER, 11);
            content.newLineAtOffset(85, 770);
            content.showText("Arepuerto UNA");
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.COURIER, 11);
            content.newLineAtOffset(85, 750);
            content.showText("GENERADO:" + new SimpleDateFormat("dd-MM-yyyy").format(fec));
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.COURIER, 11);
            content.newLineAtOffset(85, 730);
            content.showText("REPORTE DE RESULTADOS DE CAMBIO DE DIVISAS");
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.COURIER, 11);
            content.newLineAtOffset(85, 690);
            content.showText("Moneda seleccionada: " + cbMoneda.getValue());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.COURIER, 11);
            content.newLineAtOffset(85, 670);
            content.showText("Monto a consultar: " + txtIngresarMonto.getText());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.COURIER, 11);
            content.newLineAtOffset(85, 650);
            content.showText("Resultados del cambio: ");
            content.endText();
            llenarMontosReportes();
            int vari = 620;
            for (int p = 0; p < cambios.size(); p++) {
                content.beginText();
                content.setFont(PDType1Font.COURIER, 11);
                content.newLineAtOffset(100, vari);
                content.showText(cambios.get(p));
                content.endText();
                vari = vari - 20;
            }
            content.close();
            DirectoryChooser filChoser = new DirectoryChooser();
//            System.out.println(filChoser.showDialog((Stage) lblselect.getScene().getWindow()).getPath());
//            System.out.println(filChoser.showDialog((Stage) lblselect.getScene().getWindow()).getCanonicalPath());
//            System.out.println(filChoser.showDialog((Stage) lblselect.getScene().getWindow()).getAbsoluteFile().getPath().replaceAll("\\\\", "/"));
            pdf.save(filChoser.showDialog((Stage) lblselect.getScene().getWindow()).getAbsoluteFile().getPath().replaceAll("\\\\", "/") + "/hola.xml");
            pdf.close();

        } catch (IOException ex) {
            Logger.getLogger(CambioDivisasController.class.getName()).log(Level.SEVERE, null, ex);
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
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error al calcular monto", (Stage) lblselect.getScene().getWindow(), "Monto no digitado");
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

    private void llenarMontosReportes() {

        double monto = Double.valueOf(txtIngresarMonto.getText());
        if (itemSelect.equals("Colon")) {
            cambios = new ArrayList<>();
            cambios.add("Eurodolar:" + String.format("%.2f", (monto / USDCostaRica) * USDEuros));
            cambios.add("Dolar estadounidense:" + String.format("%.2f", (monto / USDCostaRica)));
            cambios.add("Yen:" + String.format("%.2f", (monto / USDCostaRica) * USDYen));
            cambios.add("Dolarcanadiense:" + String.format("%.2f", (monto / USDCostaRica) * USDCanada));
            cambios.add("Libraesterlina:" + String.format("%.2f", (monto / USDCostaRica) * USDLibraEstaerlina));
            cambios.add("Franco:" + String.format("%.2f", (monto / USDCostaRica) * USDFranco));
            cambios.add("Dolaraustraliano:" + String.format("%.2f", (monto / USDCostaRica) * USDAustralia));
            cambios.add("Dolareozelandés:" + String.format("%.2f", (monto / USDCostaRica) * USDNuevaZelanda));

        } else if (itemSelect.equals("Eurodolar")) {
            cambios = new ArrayList<>();
            cambios.add("Colon:" + String.format("%.2f", (monto / USDEuros) * USDCostaRica));
            cambios.add("Dolar estadounidense:" + String.format("%.2f", (monto / USDEuros)));
            cambios.add("Yen:" + String.format("%.2f", (monto / USDEuros) * USDYen));
            cambios.add("Dolar canadiense:" + String.format("%.2f", (monto / USDEuros) * USDCanada));
            cambios.add("Libra esterlina:" + String.format("%.2f", (monto / USDEuros) * USDLibraEstaerlina));
            cambios.add("Franco:" + String.format("%.2f", (monto / USDEuros) * USDFranco));
            cambios.add("Dolar australiano:" + String.format("%.2f", (monto / USDEuros) * USDAustralia));
            cambios.add("Dolar neozelandés:" + String.format("%.2f", (monto / USDEuros) * USDNuevaZelanda));

        } else if (itemSelect.equals("Dolar estadounidense")) {
            cambios = new ArrayList<>();
            cambios.add("Colon:" + String.format("%.2f", (monto / USDCostaRica)));
            cambios.add("EuroDolar:" + String.format("%.2f", (monto / USDEuros)));
            cambios.add("Yen:" + String.format("%.2f", (monto / USDYen)));
            cambios.add("Dolar canadiense:" + String.format("%.2f", (monto / USDCanada)));
            cambios.add("Libra esterlina:" + String.format("%.2f", (monto / USDLibraEstaerlina)));
            cambios.add("Franco:" + String.format("%.2f", (monto / USDFranco)));
            cambios.add("Dolar australiano:" + String.format("%.2f", (monto / USDAustralia)));
            cambios.add("Dolar neozelandés:" + String.format("%.2f", (monto / USDNuevaZelanda)));

        } else if (itemSelect.equals("Yen")) {
            cambios = new ArrayList<>();
            cambios.add("Colon:" + String.format("%.2f", (monto / USDYen) * USDCostaRica));
            cambios.add("Dolar estadounidense:" + String.format("%.2f", (monto / USDYen)));
            cambios.add("Eurodolar:" + String.format("%.2f", (monto / USDYen) * USDEuros));
            cambios.add("Dolar canadiense:" + String.format("%.2f", (monto / USDYen) * USDCanada));
            cambios.add("Libra esterlina:" + String.format("%.2f", (monto / USDYen) * USDLibraEstaerlina));
            cambios.add("Franco:" + String.format("%.2f", (monto / USDYen) * USDFranco));
            cambios.add("Dolar australiano:" + String.format("%.2f", (monto / USDYen) * USDAustralia));
            cambios.add("Dolar neozelandés:" + String.format("%.2f", (monto / USDYen) * USDNuevaZelanda));

        } else if (itemSelect.equals("Dolar canadiense")) {

            cambios = new ArrayList<>();
            cambios.add("Colon:" + String.format("%.2f", (monto / USDCanada) * USDCostaRica));
            cambios.add("Dolar estadounidense:" + String.format("%.2f", (monto / USDCanada)));
            cambios.add("Eurodolar:" + String.format("%.2f", (monto / USDCanada) * USDEuros));
            cambios.add("Yen:" + String.format("%.2f", (monto / USDCanada) * USDYen));
            cambios.add("Libra esterlina:" + String.format("%.2f", (monto / USDCanada) * USDLibraEstaerlina));
            cambios.add("Franco:" + String.format("%.2f", (monto / USDCanada) * USDFranco));
            cambios.add("Dolar australiano:" + String.format("%.2f", (monto / USDCanada) * USDAustralia));
            cambios.add("Dolar neozelandés:" + String.format("%.2f", (monto / USDCanada) * USDNuevaZelanda));

        } else if (itemSelect.equals("Libra esterlina")) {
            cambios = new ArrayList<>();
            cambios.add("Colon:" + String.format("%.2f", (monto / USDLibraEstaerlina) * USDCostaRica));
            cambios.add("Dolar estadounidense:" + String.format("%.2f", (monto / USDLibraEstaerlina)));
            cambios.add("Eurodolar:" + String.format("%.2f", (monto / USDLibraEstaerlina) * USDEuros));
            cambios.add("Yen:" + String.format("%.2f", (monto / USDLibraEstaerlina) * USDYen));
            cambios.add("Dolar canadiense:" + String.format("%.2f", (monto / USDLibraEstaerlina) * USDCanada));
            cambios.add("Franco:" + String.format("%.2f", (monto / USDLibraEstaerlina) * USDFranco));
            cambios.add("Dolar australiano:" + String.format("%.2f", (monto / USDLibraEstaerlina) * USDAustralia));
            cambios.add("Dolar neozelandés:" + String.format("%.2f", (monto / USDLibraEstaerlina) * USDNuevaZelanda));

        } else if (itemSelect.equals("Franco")) {
            cambios = new ArrayList<>();
            cambios.add("Colon:" + String.format("%.2f", (monto / USDFranco) * USDCostaRica));
            cambios.add("Dolar estadounidense:" + String.format("%.2f", (monto / USDFranco)));
            cambios.add("Eurodolar:" + String.format("%.2f", (monto / USDFranco) * USDEuros));
            cambios.add("Yen:" + String.format("%.2f", (monto / USDFranco) * USDYen));
            cambios.add("Dolar canadiense:" + String.format("%.2f", (monto / USDFranco) * USDCanada));
            cambios.add("Libra esterlina:" + String.format("%.2f", (monto / USDFranco) * USDLibraEstaerlina));
            cambios.add("Dolar australiano:" + String.format("%.2f", (monto / USDFranco) * USDAustralia));
            cambios.add("Dolar neozelandés:" + String.format("%.2f", (monto / USDFranco) * USDNuevaZelanda));

        } else if (itemSelect.equals("Dolar australiano")) {
            cambios = new ArrayList<>();
            cambios.add("Colon:" + String.format("%.2f", (monto / USDAustralia) * USDCostaRica));
            cambios.add("Dolar estadounidense:" + String.format("%.2f", (monto / USDAustralia)));
            cambios.add("Eurodolar:" + String.format("%.2f", (monto / USDAustralia) * USDEuros));
            cambios.add("Yen:" + String.format("%.2f", (monto / USDAustralia) * USDYen));
            cambios.add("Dolar canadiense:" + String.format("%.2f", (monto / USDAustralia) * USDCanada));
            cambios.add("Libra esterlina:" + String.format("%.2f", (monto / USDAustralia) * USDLibraEstaerlina));
            cambios.add("Franco:" + String.format("%.2f", (monto / USDAustralia) * USDFranco));
            cambios.add("Dolar neozelandés:" + String.format("%.2f", (monto / USDAustralia) * USDNuevaZelanda));

        } else if (itemSelect.equals("Dolar neozelandes")) {
            cambios = new ArrayList<>();
            cambios.add("Colon:" + String.format("%.2f", (monto / USDNuevaZelanda) * USDCostaRica));
            cambios.add("Dolar estadounidense:" + String.format("%.2f", (monto / USDNuevaZelanda)));
            cambios.add("Eurodolar:" + String.format("%.2f", (monto / USDNuevaZelanda) * USDEuros));
            cambios.add("Yen:" + String.format("%.2f", (monto / USDNuevaZelanda) * USDYen));
            cambios.add("Dolar canadiense:" + String.format("%.2f", (monto / USDNuevaZelanda) * USDCanada));
            cambios.add("Libra esterlina:" + String.format("%.2f", (monto / USDNuevaZelanda) * USDLibraEstaerlina));
            cambios.add("Franco:" + String.format("%.2f", (monto / USDNuevaZelanda) * USDFranco));
            cambios.add("Dolar australiano:" + String.format("%.2f", (monto / USDNuevaZelanda) * USDAustralia));
        }

    }

    public void llenarListaNodos() {
        modDesarrollo.clear();
        modDesarrolloAxiliar.clear();
        modDesarrolloAxiliar.add(cbMoneda.getPromptText());
        modDesarrolloAxiliar.add(txtIngresarMonto.getPromptText());
        modDesarrolloAxiliar.add(btnExportarPDF.getText());
        modDesarrolloAxiliar.add(btnExportarXML.getText());
        modDesarrolloAxiliar.add(btnSalir.getText());
        modDesarrollo.addAll(Arrays.asList(cbMoneda, txtIngresarMonto, btnExportarPDF, btnExportarXML, btnSalir));
    }

    public void desarrollo() {
        String dato = "";
        boolean validos1 = (Boolean) AppContext.getInstance().get("mod");
        if (validos1) {
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
                    dato = ((Label) node).getId();
                    ((Label) node).setText(dato);

                }
            }
        } else {
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
    }

    @FXML
    private void actionExportXML(ActionEvent event) {
        generarXML();
    }

    @FXML
    private void actionExportPDF(ActionEvent event) {
        generarPDF();
    }

    @FXML
    private void modoDesarrollo(KeyEvent event) {
        KeyCombination cntrlD = new KeyCodeCombination(KeyCode.D, KeyCodeCombination.CONTROL_DOWN);
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
