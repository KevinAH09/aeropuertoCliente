/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.aeropuertocliente.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import org.una.aeropuertocliente.utils.AppContext;

/**
 * FXML Controller class
 *
 * @author colo7
 */
public class BaseController implements Initializable {

    @FXML
    private BorderPane base;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void moveTamano(MouseEvent event) {
        AppContext.getInstance().set("whit", base.getWidth());
        AppContext.getInstance().set("heig", base.getHeight());
    }

}
