/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clases.Mensajero;
import java.net.Inet4Address;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.TimerTask;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Saul
 */
public class ConectarMovil implements Initializable {

    @FXML
    private Label estatus;
    @FXML
    private Label IP;
    @FXML
    private Button hecho;
    
    private final Mensajero mensajero = tecladoCtrl.getMensajero();
    private final Mensajero comprobador=tecladoCtrl.getComprobador();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            IP.setText(Inet4Address.getLocalHost().getHostAddress());
            hecho.setText("Hecho");
            hecho.setOnAction(e->onClickHecho(e));
            TimerTask checarMensaje=new TimerTask(){
                @Override
                public void run() {
                    Platform.runLater(new Runnable(){
                        @Override
                        public void run() {
                          verMensaje();
                        }
                    });
                   
                }
            };
            Timer checador=new Timer(true);
           checador.scheduleAtFixedRate(checarMensaje,1000,1000);
            
        } catch (Exception ex) {
            IP.setText("Error al obtener IP");
            System.out.println("Error al conectar móvil "+ex.getMessage());
            mensajero.liberarMensajero();
        }
    }

    public void verMensaje()
    {
            if(comprobador.getMensaje()!=null){
                System.out.println("primario>"+comprobador.getEstatus());
                System.out.println("Mensaje>"+comprobador.getMensaje());
               if(!comprobador.getEstatus().equals("Desconectado")){
                   //System.out.println("El móvil dice>"+mensajero.getMensaje());
                   estatus.setText(comprobador.getEstatus());  
                   if(comprobador.isDisponible())
                     {
                         hecho.setText("Hecho");
                         hecho.setOnAction(e->onClickHecho(e));
                     }else
                     {
                         hecho.setText("Desconectar del Dispositivo Actual");
                         hecho.setOnAction(e->onClickLiberar(e));
                     }
               }
                
            }
    }
    public void onClickLiberar(ActionEvent e)
    {
        hecho.setText("Hecho");
        hecho.setOnAction(s->onClickHecho(s));
        comprobador.liberarMensajero();
 
    }
    public void onClickHecho(ActionEvent e)
    {
        //mensajero.liberarMensajero();
        Stage actualStage=(Stage)hecho.getScene().getWindow();
        actualStage.close();
    }
    
}
