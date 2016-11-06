/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;


import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.stage.Stage;

/**
 *
 * @author Saul
 */
public class synthCtrl implements Initializable {
    
    @FXML
    private  final int aPant=Toolkit.getDefaultToolkit().getScreenSize().height;
    @FXML
    private  final int lPant=Toolkit.getDefaultToolkit().getScreenSize().width;
    @FXML
    private Label label;
    @FXML
    private Button btnSesion;
    @FXML
    private Button btnInvitado;
    @FXML
    private TextField usr;
    @FXML 
    private TextField psw;
    @FXML
    private ImageView logo;

    @FXML
    public  int getaPant() {
        return aPant;
    }
    @FXML
    public  int getlPant() {
        return lPant;
    }
    
    public boolean verificaCta(String usr,String clv){
        boolean valida=false;
//        valida=verifCta();
        valida=true;
        return valida;
    }
    public void entraTeclado(Stage st) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/teclado.fxml"));       
        Scene scene = new Scene(root);       
        st.setScene(scene);
        st.setTitle("Synth! MD TECLADO");
        st.setMaximized(true);
        st.show();
        scene.getRoot().requestFocus();
    }
    private boolean validaCampos(){
        boolean valido=false;
        String us=usr.getText();
        String clav=psw.getText();
            if(!(us.equals("") && clav.equals("")))
            {
                System.out.println("Llenos");
                if(verificaCta(us,clav))
                {
                    System.out.println("Tru");
                    
                    
                }
                
            }
            else
                System.out.println("Vacio");
        return valido;
    }
    @FXML
    private void manejaBtn(ActionEvent e) throws IOException{
        if(e.getSource()==btnSesion){
            label.setText("Iniciando Sesion...");
            //validaCampos();
            Stage nSg=(Stage)btnSesion.getScene().getWindow();
            nSg.close();
            entraTeclado(nSg);
            
        }
        else
            if(e.getSource()==btnInvitado)
            {
                label.setText("Iniciando...");
            }
    }
    
    public void configComponentes(){
        Image l=new Image("imgs/Synth.png");
        logo.setImage(l);
        logo.setFitWidth(lPant/3);
        logo.setFitHeight(aPant/2);
        logo.setLayoutX(lPant/6);
        logo.setLayoutY(aPant/5);
        logo.maxWidth(lPant/3);
        logo.maxHeight(aPant/2);
        logo.minWidth(lPant/4);
        logo.minHeight(aPant/3);
        usr.setLayoutX(lPant/12*7);
        usr.setLayoutY(aPant/16*6);
        usr.setMinSize(lPant/4,aPant/32);
        usr.setMaxSize(lPant/3,aPant/16);
        psw.setMinSize(lPant/4,aPant/32);
        psw.setMaxSize(lPant/3,aPant/16);
        psw.setLayoutX(lPant/12*7);
        psw.setLayoutY(aPant/16*7);
        btnSesion.setLayoutX(lPant/96*59);
        btnSesion.setLayoutY(aPant/16*9);
        btnSesion.setMaxSize(lPant/8,aPant/16);
        btnSesion.setMinSize(lPant/16,aPant/24);
        btnInvitado.setMaxSize(lPant/8,aPant/16);
        btnInvitado.setMinSize(lPant/16,aPant/24);
        btnInvitado.setLayoutX(lPant/12*9);
        btnInvitado.setLayoutY(aPant/16*9);
        label.setLayoutX(lPant/32*15);
        label.setLayoutY(aPant/4*3);
        label.setMaxSize(lPant/3,aPant/6);
        label.setMinSize(lPant/4,aPant/8);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        configComponentes();
        // TODO
    }    
    
}
