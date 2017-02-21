/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * FXML Controller class
 *
 * @author Alumno
 */
public class WebInfController extends synthCtrl implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane wPane;
    @FXML
    private Label msj;
    @FXML
    private WebView pag;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        wPane.setMaxSize(lPant/32*13,aPant/2);
        wPane.setMinSize(lPant/32*13,aPant/2);
       WebEngine eng=pag.getEngine();
       eng.load("http://localhost:8080/Geometry%20Drawtech%202.0/");
    }    
    
}
