/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.awt.Toolkit;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alumno
 */
public class tecladoCtrl implements Initializable {

    private final int aPant=Toolkit.getDefaultToolkit().getScreenSize().height;
    private final int lPant=Toolkit.getDefaultToolkit().getScreenSize().width;
    
    
    
    /**
     * Initializes the controller class.
     */
//    @FXML
//    private HBox teclado;
    @FXML
    AnchorPane aP;
    @FXML
     Rectangle c1;
    @FXML
    Rectangle c1sos;
    @FXML
     Rectangle d1;
    @FXML
     Rectangle d1sos;
    @FXML
     Rectangle e1;
    @FXML
     Rectangle f1;
    @FXML
    Rectangle f1sos;
    @FXML
     Rectangle g1;
    @FXML
    Rectangle g1sos;
    @FXML
     Rectangle a1;
    @FXML
    Rectangle a1sos;
    @FXML
     Rectangle b1;
    
    @FXML
     Rectangle c2;
    @FXML
    Rectangle c2sos;
    @FXML
     Rectangle d2;
    @FXML
    Rectangle d2sos;
    @FXML
     Rectangle e2;
    @FXML
     Rectangle f2;
    @FXML
    Rectangle f2sos;
    @FXML
     Rectangle g2;
    @FXML
    Rectangle g2sos;
    @FXML
     Rectangle a2;
    @FXML
    Rectangle a2sos;
    @FXML
     Rectangle b2;
    
    @FXML
     Rectangle c3;
    @FXML
    Rectangle c3sos;
    @FXML
     Rectangle d3;
    @FXML
    Rectangle d3sos;
    @FXML
     Rectangle e3;
    @FXML
     Rectangle f3;
    @FXML
    Rectangle f3sos;
    @FXML
     Rectangle g3;
    @FXML
    Rectangle g3sos;
    @FXML
     Rectangle a3;
    @FXML
    Rectangle a3sos;
    @FXML
     Rectangle b3;
    

    String [][] arrR=new String[36][2];
   
    
    @FXML
    private MenuItem agregarEf;
    @FXML 
    private MenuItem abrirEf;
    @FXML
    private MenuItem guardar;
    @FXML
    private MenuBar menu;   
    @FXML
    private Menu mEfectos;
    @FXML 
    private Menu mPost;
    @FXML
    private Menu mExtras;
    @FXML 
    private Label lab1;
    @FXML
    private MenuItem postea;
    @FXML
    private MenuItem inf;
    @FXML
    private MenuItem configTecl;
    

   
    @FXML
    public void manejaMenu(ActionEvent e){
        System.out.println("Metodo");
        System.out.println(e.getSource());
    }
    
    @FXML
    public void suenaNota(KeyEvent e){
        System.out.println("Tecla presionada");
        System.out.println(e.getCode());
        lab1.setText(getNotaTocada(e.getText()));
//        if(e.getCode().equals(KeyCode.A)){
//         Stage s= (Stage)aP.getScene().getWindow();
//         s.close();
//        }
    }
    public String getNotaTocada(String tecla){
        String nota="";
        for(int i=0;i<36;i++){
            if(arrR[i][1].equalsIgnoreCase(tecla))
                nota=arrR[i][0];
        }
        return nota;
    }
    //Asigna configuracion de teclas guardada a
    //cada rectangulo del teclado virtual
    public void asignarTeclas(Rectangle [] tecl){
        
        //Aquí llamamos a la clase de serialización y abrimos el archivo.
        String [] teclas={"q","2","w","3","e","r","5","t","6","y","7","u",
                          "i","9","o","0","p","z","s","x","d","c","f","v",
                          "g","b","h","n","j","m","k",",","l",".","ñ","-",};
        //for(int j=0;j<2;j++){
            for(int i=0;i<tecl.length;i++){
                arrR[i][0]=tecl[i].getId();
                arrR[i][1]=teclas[i];
            }
        //}
        
    }
    
    public void mostrarTeclado(Rectangle [] aR){
        
        aR[0].setWidth(lPant/24);
        aR[0].setHeight(aPant/3);
        aR[0].setLayoutX(lPant/16);
        aR[0].setY(aPant/2);
        aR[0].setFill(Color.WHITE);
        aR[0].setStroke(Color.BLACK);
        System.out.println(aR[0].getX());
        System.out.println(aR.length);
            for(int i=1;i<aR.length;i++){

                
                aR[i].setY(aPant/2);
                System.out.println(aR[i].getId());
                //negra 
                if(aR[i].getId().endsWith("s")){
                    aR[i].setWidth(lPant/48);
                    System.out.println(aR[i].getId()+"DENTRO IF");
                    aR[i].setHeight(aPant/6);
                    aR[i].setLayoutX(aR[i-1].getLayoutX()+aR[i-1].getWidth()/2);                
                    aR[i].setFill(Color.BLACK);
                    aR[i].setStroke(Color.WHITE); 
                      
                }
                else
                {
                    aR[i].setWidth(lPant/24);
                    aR[i].setHeight(aPant/3);
                    if(aR[i].getId().startsWith("f")||aR[i].getId().startsWith("c"))
                        aR[i].setLayoutX(aR[i-1].getLayoutX()+aR[i-1].getWidth());
                    else
                        aR[i].setLayoutX(aR[i-2].getLayoutX()+aR[i-2].getWidth());                 
                    aR[i].setFill(Color.WHITE);
                    aR[i].setStroke(Color.BLACK); 
                    
                }
            }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Rectangle [] aR={c1,c1sos,d1,d1sos,e1,f1,f1sos,g1,g1sos,a1,a1sos,b1,
            c2,c2sos,d2,d2sos,e2,f2,f2sos,g2,g2sos,a2,a2sos,b2,
            c3,c3sos,d3,d3sos,e3,f3,f3sos,g3,g3sos,a3,a3sos,b3};
        
        menu.setPrefSize(lPant,aPant/16);
        lab1.setLayoutX(lPant/2);
        lab1.setLayoutY(aPant/3);
        
        asignarTeclas(aR);
        //arrR=aR;       
        mostrarTeclado(aR);
        
       

    }    
    
}
