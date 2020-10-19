
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.utils;



import java.io.IOException;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.una.aeropuertocliente.App;
import org.una.aeropuertocliente.controllers.Controller;


/**
 *
 * @author esanchez
 */
public class FlowController {

    
    private static FlowController INSTANCE = null;
    private static Stage mainStage; 
    private static ResourceBundle idioma;
    private static HashMap<String, FXMLLoader> loaders = new HashMap<>();
    private Stage s;
  
    
    private FlowController() {
    }

    private static void createInstance() { 
        if (INSTANCE == null) {
            synchronized (FlowController.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FlowController();
                }
            }
        }
    }

    public static FlowController getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public void InitializeFlow(Stage stage, ResourceBundle idioma) {
        getInstance();
        this.mainStage = stage;
        this.idioma = idioma;
    }

    private FXMLLoader getLoader(String name) { 
        FXMLLoader loader = loaders.get(name);
        if (loader == null) {
            synchronized (FlowController.class) {
                if (loader == null) {
                    try {
                        loader = new FXMLLoader(App.class.getResource( "views/"+name + ".fxml"), this.idioma);
                        loader.load();
                    } catch (Exception ex) {
                        loader = null;
                        System.out.println("org.una.aeropuertocliente.utils.FlowController.goMain()"+ex);
                    }
                }
            }
        }
        return loader;
    }

    public void goMain() {
        try {
            this.mainStage.setScene(new Scene(FXMLLoader.load(App.class.getResource("views/base/Base.fxml"), this.idioma)));
            this.mainStage.setMinWidth(768);
            this.mainStage.show();
        } catch (Exception ex) {
            System.out.println("org.una.aeropuertocliente.utils.FlowController.goMain()"+ex);
        }
    }

    public void goView(String viewName) { 
        goView(viewName, "Center", null);
    }

    public void goView(String viewName, String accion) {
        goView(viewName, "Center", accion); 
    }
    public Parent retornaSatge(String viewName) throws IOException{
        Parent root = FXMLLoader.load(App.class.getResource("views/"+viewName+".fxml"));
        return root;
    }

    public void goView(String viewName, String location, String accion) {
        FXMLLoader loader = getLoader(viewName);
        Controller controller = loader.getController();
        controller.setAccion(accion);
        controller.initialize();
        Stage stage = controller.getStage();
        s = stage;
        if (stage == null) {
            stage = this.mainStage;
            controller.setStage(stage);
        }
        switch (location) {
            case "Center":
			((StackPane) ((BorderPane) stage.getScene().getRoot()).getCenter()).getChildren().clear();
			((StackPane) ((BorderPane) stage.getScene().getRoot()).getCenter()).getChildren().add(loader.getRoot()); 
                break;
            case "Top":
                break;
            case "Bottom":
                break;
            case "Right":
                break;
            case "Left":
                break;
            default:
                break;
        }
    }

    public void goViewInStage(String viewName, Stage stage) {
        FXMLLoader loader = getLoader(viewName);
        Controller controller = loader.getController();
        controller.setStage(stage);
        stage.getScene().setRoot(loader.getRoot());
    }

    public void goViewInWindow(String viewName) {
        FXMLLoader loader = getLoader(viewName);
        Controller controller = loader.getController();
        controller.initialize();
        Stage stage = new Stage();
        stage.setOnHidden((WindowEvent event) -> {
            controller.getStage().getScene().setRoot(new Pane());
            controller.setStage(null);
        });
        controller.setStage(stage);
        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }

    public void goViewInWindowModal(String viewName, Stage parentStage, Boolean resizable) { //muestra una ventana modal
        FXMLLoader loader = getLoader(viewName);
        Controller controller = loader.getController();
        controller.initialize();
        Stage stage = new Stage();
        stage.setResizable(resizable);
        stage.setOnHidden((WindowEvent event) -> {
            controller.getStage().getScene().setRoot(new Pane());
            controller.setStage(null);
        });
        controller.setStage(stage);
        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(parentStage);
        stage.centerOnScreen();
        stage.showAndWait();

    }
	
	

    public Controller getController(String viewName) {
        return getLoader(viewName).getController();
    }
    public  Stage getStage()
    {
        return FlowController.mainStage;
    }
	
    
    
    public void initialize() {
        this.loaders.clear();
    }

    public void salir() {
        this.mainStage.close();
    }
    public static void eliminar(String viewName){
            loaders.remove(viewName);
    }

}