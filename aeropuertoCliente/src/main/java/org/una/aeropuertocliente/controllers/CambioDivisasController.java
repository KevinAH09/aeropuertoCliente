/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.una.aeropuertocliente.apiForex.TiposMonedasServices;
import org.una.aeropuertocliente.dtos.RegistrosAccionesDTO;
import org.una.aeropuertocliente.entitiesServices.RegistrosAccionesService;
import org.una.aeropuertocliente.sharedService.Token;
import org.una.aeropuertocliente.utils.AppContext;
import org.una.aeropuertocliente.utils.Mensaje;
import org.w3c.dom.DOMException;
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
    String nombreArchivo = "";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validaciontxtIngresarSoloNum();
        obtenerDatosDivisas();
        llenarOpcionesComponentesVista();
        llenarListaNodos();
        desarrollo();

    }

    private void validaciontxtIngresarSoloNum() {
        txtIngresarMonto.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println(txtIngresarMonto.getText());
                if (!newValue.matches("\\d*")) {
                    txtIngresarMonto.setText(newValue.replaceAll("[^\\d]", ""));
                }

            }
        });
    }

    private void llenarOpcionesComponentesVista() {
        listaMoneas = Arrays.asList("Colon", "Eurodolar", "Dolar estadounidense", "Yen", "Dolar canadiense", "Libra esterlina", "Franco", "Dolar australiano", "Dolar neozelandes");
        cbMoneda.setItems(FXCollections.observableArrayList("Colon", "Eurodolar", "Dolar estadounidense", "Yen", "Dolar canadiense", "Libra esterlina", "Franco", "Dolar australiano", "Dolar neozelandes"));
        cbMoneda.setValue("Colon");
        itemSelect = "Colon";
    }

    private void obtenerDatosDivisas() {
        USDCostaRica = TiposMonedasServices.valorMonedaDolarVSColon().valorMoneda();

        USDEuros = TiposMonedasServices.valorMonedaDolarVSEuros().valorMoneda();

        USDYen = TiposMonedasServices.valorMonedaDolarVSYenJapones().valorMoneda();

        USDCanada = TiposMonedasServices.valorMonedaDolarVSCanadaDolar().valorMoneda();

        USDLibraEstaerlina = TiposMonedasServices.valorMonedaDolarVSLibraEsterlina().valorMoneda();

        USDFranco = TiposMonedasServices.valorMonedaDolarVSFrancoSuizo().valorMoneda();

        USDAustralia = TiposMonedasServices.valorMonedaDolarVSAustraliaDolar().valorMoneda();

        USDNuevaZelanda = TiposMonedasServices.valorMonedaDolarVSNuevaZelandaDolar().valorMoneda();
    }

    @Override
    public void initialize() {

    }

    @FXML
    private void actionComboBox(ActionEvent event) {
        itemSelect = null;
        itemSelect = cbMoneda.getSelectionModel().getSelectedItem();
        if (itemSelect != null) {
            txtIngresarMonto.setText("");
            lblMontoCambio.setText(null);
            lblselect.setText(null);
            imgSelect.setImage(null);
            llenarImages();
        }
    }

    void generarXML() {
        try {
            Document document = creaXML();
            guardaXML(document);
            if (Token.getInstance() != null) {
                RegistrosAccionesService.createRegistroAccion(new RegistrosAccionesDTO(Token.getInstance().getUsuario(), "Genero reporte XML de divisas", new Date()));
            }
            new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Generar XML", ((Stage) btnExportarPDF.getScene().getWindow()), "Archivo XML generado correctamente");
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(CambioDivisasController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(CambioDivisasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void guardaXML(Document document) throws TransformerException, TransformerFactoryConfigurationError {
        Source source = new DOMSource(document);
        DirectoryChooser filChoser = new DirectoryChooser();
        File file = filChoser.showDialog(lblselect.getScene().getWindow());
        if (file != null) {
            Result result = new StreamResult(file.getAbsoluteFile().getPath().replaceAll("\\\\", "/") + "/" + nombreArchivo + ".xml");
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Generar XML", ((Stage) btnExportarPDF.getScene().getWindow()), "Carpeta no seleccionada.");
        }
    }

    private Document creaXML() throws ParserConfigurationException, DOMException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation implementation = builder.getDOMImplementation();
        Document document = implementation.createDocument(null, "AeropuertoUNA", null);
        document.setXmlVersion("1.0");
        Element raiz = document.getDocumentElement();
        Element itemMoneda = document.createElement("Moneda");
        org.w3c.dom.Text nodeKeyMoneda = document.createTextNode(cbMoneda.getValue());
        itemMoneda.appendChild(nodeKeyMoneda);
        Element keyMonto = document.createElement("Monto");
        org.w3c.dom.Text nodeKeyMonto = document.createTextNode(txtIngresarMonto.getText());
        keyMonto.appendChild(nodeKeyMonto);
        raiz.appendChild(itemMoneda);
        raiz.appendChild(keyMonto);
        Element intemDetalle = document.createElement("Cambios");
        llenarMontosReportes();
        for (int i = 0; i < cambios.size(); i++) {
            Element keyNode = document.createElement("Cambio");
            org.w3c.dom.Text nodeKeyValue = document.createTextNode(cambios.get(i));
            keyNode.appendChild(nodeKeyValue);
            intemDetalle.appendChild(keyNode);
            raiz.appendChild(intemDetalle);
        }
        return document;
    }

    private void alertaIngresoNombre() {
        nombreArchivo = "";
        Dialog<Pair<String, String>> dialog = crearDialog();
        ButtonType guardarButtonType = crearButtonType(dialog);
        GridPane grid = crearGripPane();
        JFXTextField nombreArcivo = crearTextFlied();
        grid.add(nombreArcivo, 1, 0);
        crearNodeDialog(dialog, guardarButtonType, nombreArcivo);
        dialog.getDialogPane().setContent(grid);
        crearFuncionalidadDialog(nombreArcivo, dialog, guardarButtonType);
    }

    private void crearFuncionalidadDialog(JFXTextField nombreArcivo, Dialog<Pair<String, String>> dialog, ButtonType guardarButtonType) {
        Platform.runLater(() -> nombreArcivo.requestFocus());
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == guardarButtonType) {
                return new Pair<>(nombreArcivo.getText(), "");
            }
            return null;
        });
        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(usernamePassword -> {
            System.out.println(usernamePassword.getKey());
            nombreArchivo = usernamePassword.getKey();
        });
    }

    private void crearNodeDialog(Dialog<Pair<String, String>> dialog, ButtonType guardarButtonType, JFXTextField nombreArcivo) {
        Node guardarButton = dialog.getDialogPane().lookupButton(guardarButtonType);
        guardarButton.setDisable(true);
        nombreArcivo.textProperty().addListener((observable, oldValue, newValue) -> {
            guardarButton.setDisable(newValue.trim().isEmpty());
        });
    }

    private JFXTextField crearTextFlied() {
        JFXTextField nombreArcivo = new JFXTextField();
        nombreArcivo.setPromptText("Nombre");
        nombreArcivo.setLabelFloat(true);
        nombreArcivo.setPrefWidth(150);
        return nombreArcivo;
    }

    private GridPane crearGripPane() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        return grid;
    }

    private ButtonType crearButtonType(Dialog<Pair<String, String>> dialog) {
        ButtonType guardarButtonType = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(guardarButtonType, ButtonType.CANCEL);
        return guardarButtonType;
    }

    private Dialog<Pair<String, String>> crearDialog() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Nombre archivo");
        dialog.setHeaderText("Ingrese el nombre con el cual desea guardar el archivo");
        return dialog;
    }

    void generarPDF() {

        try {
            PDDocument pdf = generarContenidoPDF();
            guardarPDF(pdf);
            if (Token.getInstance() != null) {
                RegistrosAccionesService.createRegistroAccion(new RegistrosAccionesDTO(Token.getInstance().getUsuario(), "Genero reporte PDF de divisas", new Date()));
                new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Generar PDF", ((Stage) btnExportarPDF.getScene().getWindow()), "Archivo PDF generado correctamente");
            }
        } catch (IOException ex) {
            Logger.getLogger(CambioDivisasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void guardarPDF(PDDocument pdf) throws IOException {
        DirectoryChooser filChoser = new DirectoryChooser();
        File file = filChoser.showDialog(lblselect.getScene().getWindow());
        if (file != null) {
            pdf.save(file.getAbsoluteFile().getPath().replaceAll("\\\\", "/") + "/" + nombreArchivo + ".pdf");
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Generar PDF", ((Stage) btnExportarPDF.getScene().getWindow()), "Carpeta no seleccionada.");
        }
        pdf.close();
    }

    private PDDocument generarContenidoPDF() throws IOException {
        Date fec = new Date();
        PDDocument pdf = new PDDocument();
        PDPage page = new PDPage();
        pdf.addPage(page);
        PDPageContentStream content = new PDPageContentStream(pdf, page);
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
        return pdf;
    }

    private void llenarImages() {
        if (itemSelect.equals("Colon")) {
            llenerImagenesOpcionColon();
        }
        if (itemSelect.equals("Eurodolar")) {
            llenerImagenesOpcionEurodolar();
        }
        if (itemSelect.equals("Dolar estadounidense")) {
            llenerImagenesOpcionDolarEstadosunidense();
        }
        if (itemSelect.equals("Yen")) {
            llenerImagenesOpcionYen();
        }
        if (itemSelect.equals("Dolar canadiense")) {
            llenerImagenesOpcionDolarCanadiense();
        }
        if (itemSelect.equals("Libra esterlina")) {
            llenerImagenesOpcionLibraEsterlina();
        }
        if (itemSelect.equals("Franco")) {
            llenerImagenesOpcionFranco();
        }
        if (itemSelect.equals("Dolar australiano")) {
            llenerImagenesOpcionDolarAustraliano();
        }
        if (itemSelect.equals("Dolar neozelandes")) {
            llenerImagenesOpcionDolarNeozelandes();
        }
    }

    private void llenerImagenesOpcionDolarNeozelandes() {
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

    private void llenerImagenesOpcionDolarAustraliano() {
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

    private void llenerImagenesOpcionFranco() {
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

    private void llenerImagenesOpcionLibraEsterlina() {
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

    private void llenerImagenesOpcionDolarCanadiense() {
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

    private void llenerImagenesOpcionYen() {
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

    private void llenerImagenesOpcionDolarEstadosunidense() {
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

    private void llenerImagenesOpcionEurodolar() {
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

    private void llenerImagenesOpcionColon() {
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
            llenerLabelMontoCambioOpcionColon(selectImg, monto);

        } else if (itemSelect.equals("Eurodolar")) {
            llenerLabelMontoCambioOpcionEurodolar(selectImg, monto);

        } else if (itemSelect.equals("Dolar estadounidense")) {
            llenerLabelMontoCambioOpcionDolarEstadounidense(selectImg, monto);
        } else if (itemSelect.equals("Yen")) {
            llenerLabelMontoCambioOpcionYen(selectImg, monto);

        } else if (itemSelect.equals("Dolar canadiense")) {
            llenerLabelMontoCambioOpcionDolarCanadiense(selectImg, monto);

        } else if (itemSelect.equals("Libra esterlina")) {
            llenerLabelMontoCambioOpcionLibraEsterlina(selectImg, monto);

        } else if (itemSelect.equals("Franco")) {
            llenerLabelMontoCambioOpcionFranco(selectImg, monto);

        } else if (itemSelect.equals("Dolar australiano")) {
            llenerLabelMontoCambioOpcionDolarAustraliano(selectImg, monto);

        } else if (itemSelect.equals("Dolar neozelandes")) {
            llenerLabelMontoCambioOpcionDolarNeozelandes(selectImg, monto);

        }

    }

    private void llenerLabelMontoCambioOpcionDolarNeozelandes(String selectImg, double monto) {
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

    private void llenerLabelMontoCambioOpcionDolarAustraliano(String selectImg, double monto) {
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
    }

    private void llenerLabelMontoCambioOpcionFranco(String selectImg, double monto) {
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
    }

    private void llenerLabelMontoCambioOpcionLibraEsterlina(String selectImg, double monto) {
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
    }

    private void llenerLabelMontoCambioOpcionDolarCanadiense(String selectImg, double monto) {
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
    }

    private void llenerLabelMontoCambioOpcionYen(String selectImg, double monto) {
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
    }

    private void llenerLabelMontoCambioOpcionDolarEstadounidense(String selectImg, double monto) {
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
    }

    private void llenerLabelMontoCambioOpcionEurodolar(String selectImg, double monto) {
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
    }

    private void llenerLabelMontoCambioOpcionColon(String selectImg, double monto) {
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
    }

    private void llenarMontosReportes() {

        double monto = Double.valueOf(txtIngresarMonto.getText());
        if (itemSelect.equals("Colon")) {
            llenarListaReportesOpcionColon(monto);

        } else if (itemSelect.equals("Eurodolar")) {
            llenarListaReportesOpcionEurodolar(monto);

        } else if (itemSelect.equals("Dolar estadounidense")) {
            llenarListaReportesOpcionDolarEstadounidense(monto);

        } else if (itemSelect.equals("Yen")) {
            llenarListaReportesOpcionYen(monto);

        } else if (itemSelect.equals("Dolar canadiense")) {
            llenarListaReportesOpcionDolarCanadiense(monto);

        } else if (itemSelect.equals("Libra esterlina")) {
            llenarListaReportesOpcionLibraEsterlina(monto);

        } else if (itemSelect.equals("Franco")) {
            llenarListaReportesOpcionFranco(monto);

        } else if (itemSelect.equals("Dolar australiano")) {
            llenarListaReportesOpcionDolarAustraliano(monto);

        } else if (itemSelect.equals("Dolar neozelandes")) {
            llenarListaReportesOpcionDolarNeozelandes(monto);
        }

    }

    private void llenarListaReportesOpcionDolarNeozelandes(double monto) {
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

    private void llenarListaReportesOpcionDolarAustraliano(double monto) {
        cambios = new ArrayList<>();
        cambios.add("Colon:" + String.format("%.2f", (monto / USDAustralia) * USDCostaRica));
        cambios.add("Dolar estadounidense:" + String.format("%.2f", (monto / USDAustralia)));
        cambios.add("Eurodolar:" + String.format("%.2f", (monto / USDAustralia) * USDEuros));
        cambios.add("Yen:" + String.format("%.2f", (monto / USDAustralia) * USDYen));
        cambios.add("Dolar canadiense:" + String.format("%.2f", (monto / USDAustralia) * USDCanada));
        cambios.add("Libra esterlina:" + String.format("%.2f", (monto / USDAustralia) * USDLibraEstaerlina));
        cambios.add("Franco:" + String.format("%.2f", (monto / USDAustralia) * USDFranco));
        cambios.add("Dolar neozelandés:" + String.format("%.2f", (monto / USDAustralia) * USDNuevaZelanda));
    }

    private void llenarListaReportesOpcionFranco(double monto) {
        cambios = new ArrayList<>();
        cambios.add("Colon:" + String.format("%.2f", (monto / USDFranco) * USDCostaRica));
        cambios.add("Dolar estadounidense:" + String.format("%.2f", (monto / USDFranco)));
        cambios.add("Eurodolar:" + String.format("%.2f", (monto / USDFranco) * USDEuros));
        cambios.add("Yen:" + String.format("%.2f", (monto / USDFranco) * USDYen));
        cambios.add("Dolar canadiense:" + String.format("%.2f", (monto / USDFranco) * USDCanada));
        cambios.add("Libra esterlina:" + String.format("%.2f", (monto / USDFranco) * USDLibraEstaerlina));
        cambios.add("Dolar australiano:" + String.format("%.2f", (monto / USDFranco) * USDAustralia));
        cambios.add("Dolar neozelandés:" + String.format("%.2f", (monto / USDFranco) * USDNuevaZelanda));
    }

    private void llenarListaReportesOpcionLibraEsterlina(double monto) {
        cambios = new ArrayList<>();
        cambios.add("Colon:" + String.format("%.2f", (monto / USDLibraEstaerlina) * USDCostaRica));
        cambios.add("Dolar estadounidense:" + String.format("%.2f", (monto / USDLibraEstaerlina)));
        cambios.add("Eurodolar:" + String.format("%.2f", (monto / USDLibraEstaerlina) * USDEuros));
        cambios.add("Yen:" + String.format("%.2f", (monto / USDLibraEstaerlina) * USDYen));
        cambios.add("Dolar canadiense:" + String.format("%.2f", (monto / USDLibraEstaerlina) * USDCanada));
        cambios.add("Franco:" + String.format("%.2f", (monto / USDLibraEstaerlina) * USDFranco));
        cambios.add("Dolar australiano:" + String.format("%.2f", (monto / USDLibraEstaerlina) * USDAustralia));
        cambios.add("Dolar neozelandés:" + String.format("%.2f", (monto / USDLibraEstaerlina) * USDNuevaZelanda));
    }

    private void llenarListaReportesOpcionDolarCanadiense(double monto) {
        cambios = new ArrayList<>();
        cambios.add("Colon:" + String.format("%.2f", (monto / USDCanada) * USDCostaRica));
        cambios.add("Dolar estadounidense:" + String.format("%.2f", (monto / USDCanada)));
        cambios.add("Eurodolar:" + String.format("%.2f", (monto / USDCanada) * USDEuros));
        cambios.add("Yen:" + String.format("%.2f", (monto / USDCanada) * USDYen));
        cambios.add("Libra esterlina:" + String.format("%.2f", (monto / USDCanada) * USDLibraEstaerlina));
        cambios.add("Franco:" + String.format("%.2f", (monto / USDCanada) * USDFranco));
        cambios.add("Dolar australiano:" + String.format("%.2f", (monto / USDCanada) * USDAustralia));
        cambios.add("Dolar neozelandés:" + String.format("%.2f", (monto / USDCanada) * USDNuevaZelanda));
    }

    private void llenarListaReportesOpcionYen(double monto) {
        cambios = new ArrayList<>();
        cambios.add("Colon:" + String.format("%.2f", (monto / USDYen) * USDCostaRica));
        cambios.add("Dolar estadounidense:" + String.format("%.2f", (monto / USDYen)));
        cambios.add("Eurodolar:" + String.format("%.2f", (monto / USDYen) * USDEuros));
        cambios.add("Dolar canadiense:" + String.format("%.2f", (monto / USDYen) * USDCanada));
        cambios.add("Libra esterlina:" + String.format("%.2f", (monto / USDYen) * USDLibraEstaerlina));
        cambios.add("Franco:" + String.format("%.2f", (monto / USDYen) * USDFranco));
        cambios.add("Dolar australiano:" + String.format("%.2f", (monto / USDYen) * USDAustralia));
        cambios.add("Dolar neozelandés:" + String.format("%.2f", (monto / USDYen) * USDNuevaZelanda));
    }

    private void llenarListaReportesOpcionDolarEstadounidense(double monto) {
        cambios = new ArrayList<>();
        cambios.add("Colon:" + String.format("%.2f", (monto / USDCostaRica)));
        cambios.add("EuroDolar:" + String.format("%.2f", (monto / USDEuros)));
        cambios.add("Yen:" + String.format("%.2f", (monto / USDYen)));
        cambios.add("Dolar canadiense:" + String.format("%.2f", (monto / USDCanada)));
        cambios.add("Libra esterlina:" + String.format("%.2f", (monto / USDLibraEstaerlina)));
        cambios.add("Franco:" + String.format("%.2f", (monto / USDFranco)));
        cambios.add("Dolar australiano:" + String.format("%.2f", (monto / USDAustralia)));
        cambios.add("Dolar neozelandés:" + String.format("%.2f", (monto / USDNuevaZelanda)));
    }

    private void llenarListaReportesOpcionEurodolar(double monto) {
        cambios = new ArrayList<>();
        cambios.add("Colon:" + String.format("%.2f", (monto / USDEuros) * USDCostaRica));
        cambios.add("Dolar estadounidense:" + String.format("%.2f", (monto / USDEuros)));
        cambios.add("Yen:" + String.format("%.2f", (monto / USDEuros) * USDYen));
        cambios.add("Dolar canadiense:" + String.format("%.2f", (monto / USDEuros) * USDCanada));
        cambios.add("Libra esterlina:" + String.format("%.2f", (monto / USDEuros) * USDLibraEstaerlina));
        cambios.add("Franco:" + String.format("%.2f", (monto / USDEuros) * USDFranco));
        cambios.add("Dolar australiano:" + String.format("%.2f", (monto / USDEuros) * USDAustralia));
        cambios.add("Dolar neozelandés:" + String.format("%.2f", (monto / USDEuros) * USDNuevaZelanda));
    }

    private void llenarListaReportesOpcionColon(double monto) {
        cambios = new ArrayList<>();
        cambios.add("Eurodolar:" + String.format("%.2f", (monto / USDCostaRica) * USDEuros));
        cambios.add("Dolar estadounidense:" + String.format("%.2f", (monto / USDCostaRica)));
        cambios.add("Yen:" + String.format("%.2f", (monto / USDCostaRica) * USDYen));
        cambios.add("Dolarcanadiense:" + String.format("%.2f", (monto / USDCostaRica) * USDCanada));
        cambios.add("Libraesterlina:" + String.format("%.2f", (monto / USDCostaRica) * USDLibraEstaerlina));
        cambios.add("Franco:" + String.format("%.2f", (monto / USDCostaRica) * USDFranco));
        cambios.add("Dolaraustraliano:" + String.format("%.2f", (monto / USDCostaRica) * USDAustralia));
        cambios.add("Dolareozelandés:" + String.format("%.2f", (monto / USDCostaRica) * USDNuevaZelanda));
    }

    public void llenarListaNodos() {
        modDesarrollo.clear();
        modDesarrolloAxiliar.clear();
        modDesarrolloAxiliar.add(cbMoneda.getPromptText());
        modDesarrolloAxiliar.add(txtIngresarMonto.getPromptText());
        modDesarrolloAxiliar.add(btnExportarPDF.getText());
        modDesarrolloAxiliar.add(btnExportarXML.getText());
        modDesarrollo.addAll(Arrays.asList(cbMoneda, txtIngresarMonto, btnExportarPDF, btnExportarXML));
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

        if (!txtIngresarMonto.getText().isEmpty()) {
            alertaIngresoNombre();
            if (!nombreArchivo.isEmpty()) {
                generarXML();
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Generar PDF", ((Stage) btnExportarPDF.getScene().getWindow()), "Nombre no digitado.");
            }
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Generar XML", ((Stage) btnExportarPDF.getScene().getWindow()), "Monto no digitado.");
        }
    }

    @FXML
    private void actionExportPDF(ActionEvent event) {

        if (!txtIngresarMonto.getText().isEmpty()) {
            alertaIngresoNombre();
            if (!nombreArchivo.isEmpty()) {
                generarPDF();
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Generar PDF", ((Stage) btnExportarPDF.getScene().getWindow()), "Nombre no digitado.");
            }
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Generar PDF", ((Stage) btnExportarPDF.getScene().getWindow()), "Monto no digitado.");
        }
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
