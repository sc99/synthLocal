/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alumno
 */
public class ConfigTecController extends synthCtrl implements Initializable {

    /**
     * Initializes the controller class.
     */ 
     private TextField [] tecls;
    private String [] contLbs={"c1","c1sos","d1","d1sos","e1","f1","f1sos","g1","g1sos","a1","a1sos","b1",
            "c2","c2sos","d2","d2sos","e2","f2","f2sos","g2","g2sos","a2","a2sos","b2",
            "c3","c3sos","d3","d3sos","e3","f3","f3sos","g3","g3sos","a3","a3sos","b3"};
    
    @FXML
    private Button guardar;
    @FXML
    private Button reestablecer;
    @FXML
    private AnchorPane cPane;
    @FXML
    private TextField txt1;
    @FXML
    private TextField txt2;
    @FXML
    private TextField txt3;
    @FXML
    private TextField txt4;
    @FXML
    private TextField txt5;
    @FXML
    private TextField txt6;
    @FXML
    private TextField txt7;
    @FXML
    private TextField txt8;
    @FXML
    private TextField txt9;
    @FXML
    private TextField txt10;
    @FXML
    private TextField txt11;
    @FXML
    private TextField txt12;
    
    @FXML
    private TextField txt13;
    @FXML
    private TextField txt14;
    @FXML
    private TextField txt15;
    @FXML
    private TextField txt16;
    @FXML
    private TextField txt17;
    @FXML
    private TextField txt18;
    @FXML
    private TextField txt19;
    @FXML
    private TextField txt20;
    @FXML
    private TextField txt21;
    @FXML
    private TextField txt22;
    @FXML
    private TextField txt23;
    @FXML
    private TextField txt24;
    @FXML
    private TextField txt25;
    @FXML
    private TextField txt26;
    @FXML
    private TextField txt27;
    @FXML
    private TextField txt28;
    @FXML
    private TextField txt29;
    @FXML
    private TextField txt30;
    @FXML
    private TextField txt31;
    @FXML
    private TextField txt32;
    @FXML
    private TextField txt33;
    @FXML
    private TextField txt34;
    @FXML
    private TextField txt35;
    @FXML
    private TextField txt36;
    
    @FXML
    private Label c1;
    @FXML
    private Label c1sos;
    @FXML
    private Label d1;
    @FXML
    private Label d1sos;
    @FXML
    private Label e1;
    @FXML
    private Label f1;
    @FXML
    private Label f1sos;
    @FXML
    private Label g1;
    @FXML
    private Label g1sos;
    @FXML
    private Label a1;
    @FXML
    private Label a1sos;
    @FXML
    private Label b1;
    
    @FXML
    private Label c2;
    @FXML
    private Label c2sos;
    @FXML
    private Label d2;
    @FXML
    private Label d2sos;
    @FXML
    private Label e2;
    @FXML
    private Label f2;
    @FXML
    private Label f2sos;
    @FXML
    private Label g2;
    @FXML
    private Label g2sos;
    @FXML
    private Label a2;
    @FXML
    private Label a2sos;
    @FXML
    private Label b2;
    @FXML
    private Label c3;
    @FXML
    private Label c3sos;
    @FXML
    private Label d3;
    @FXML
    private Label d3sos;
    @FXML
    private Label e3;
    @FXML
    private Label f3;
    @FXML
    private Label f3sos;
    @FXML
    private Label g3;
    @FXML
    private Label g3sos;
    @FXML
    private Label a3;
    @FXML
    private Label a3sos;
    @FXML
    private Label b3;
   
    @FXML
    public void manejaBtn(ActionEvent e){
        if(e.getSource()==guardar){
            System.out.println("Confirmando...");
            confirma((Stage)cPane.getScene().getWindow(),0);
        }else
            if(e.getSource()==reestablecer)
            {
               confirma((Stage)cPane.getScene().getWindow(),1);
            }
    }
    public void estadoModif(Stage st,String msj){
        Alert aviso=new Alert(Alert.AlertType.INFORMATION);
       aviso.setTitle("Aviso");
       aviso.setHeaderText("Mensaje de estatus");
       aviso.setContentText(msj);
      Optional<ButtonType> result=aviso.showAndWait();
    }
    public void confirma(Stage st,int tipoCambio){
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmacion");
        alert.setHeaderText("¡Espera!");
        alert.setContentText("¿Estás seguro de los cambios?");
        Optional<ButtonType>result=alert.showAndWait();

        if (result.get() == ButtonType.OK){
            System.out.println("Cambiado");            
            if(tipoCambio==0)
                validaNvsTecls(st);
            else
            {
                clases.cUsuario nTcl=new clases.cUsuario();
                synthCtrl.sesion.setTeclado(nTcl.getTeclado());
                tecladoCtrl.asignarNvsTcls();
                estadoModif(st,"Se restableció el teclado por default");
            }
        }
        else{
            System.out.println("No cambiado");
             
        }
    }
    public boolean validaTclsRep(String [] teclas){
        boolean repetida=false;
        String [] xArr=teclas;
        int numRep=0;
        for(int i=0;i<teclas.length;i++)
        {
           
            for(int j=0;j<teclas.length;j++)
            {
                if(teclas[i].equals(teclas[j]))
                    numRep++;
            }
            if(numRep!=1){
                repetida=true;
                break;
            }else
                numRep=0;
        }
        return repetida;
    }
    public void validaNvsTecls(Stage st){
        int cambio=0;
        String [] xTcl=synthCtrl.sesion.getTeclado();
        for(int i=0;i<xTcl.length;i++)
        {
            if(xTcl[i].equals(tecls[i].getText())){
                cambio++;
               
            }else
            {
               xTcl[i]=tecls[i].getText();
            }
        }
        if(cambio!=36)
        {
            if(!validaTclsRep(xTcl))
            {
                synthCtrl.sesion.setTeclado(xTcl);
                tecladoCtrl.asignarNvsTcls();
                estadoModif(st,"Cambios guardados");
            }else
            {
                estadoModif(st,"¡Teclas repetidas! \n"
                    + "Asígna una tecla diferente a cada nota.");
            }
        }
        else
        {
            estadoModif(st,"¡Mismas teclas! \n"
                    + "Introduce teclas distintas para realizar el cambio.");
        }
    }
    public void getLbls(){
        Label [] lbs={c1,c1sos,d1,d1sos,e1,f1,f1sos,g1,g1sos,a1,a1sos,b1,
            c2,c2sos,d2,d2sos,e2,f2,f2sos,g2,g2sos,a2,a2sos,b2,
            c3,c3sos,d3,d3sos,e3,f3,f3sos,g3,g3sos,a3,a3sos,b3};
        
        TextField [] txts={txt1,txt2,txt3,txt4,txt5,txt6,
                           txt7,txt8,txt9,txt10,txt11,txt12,
                           txt13,txt14,txt15,txt16,txt17,txt18,
                           txt19,txt20,txt21,txt22,txt23,txt24,
                           txt25,txt26,txt27,txt28,txt29,txt30,
                           txt31,txt32,txt33,txt34,txt35,txt36};
        String [] tclsActuales=synthCtrl.sesion.getTeclado();
       
        for(int i=0;i<lbs.length;i++)
        {
            lbs[i].setText(contLbs[i]);
            lbs[i].setId(contLbs[i]);
            lbs[i].setMaxSize(lPant/16, aPant/32);
            lbs[i].setMinSize(lPant/16,aPant/32);
            lbs[i].setLayoutX(i<12?lPant/24:i<24?lPant/24*4:lPant/24*8);
            lbs[i].setLayoutY(i%12==0?aPant/64:lbs[i-1].getLayoutY()+aPant/32+aPant/128);
            
            txts[i].setMaxSize(lPant/64,lPant/64);
            txts[i].setMinSize(lPant/64,lPant/64);
            txts[i].setLayoutX(i<12?lPant/12:i<24?lPant/12*3:lPant/12*5);
            txts[i].setLayoutY(lbs[i].getLayoutY());
            txts[i].setText(tclsActuales[i]);
        }
        tecls=txts;

    }
    public void modificable(boolean valor){
        guardar.setVisible(valor);
        reestablecer.setVisible(valor);
        for(int i=0;i<tecls.length;i++)
        {
            tecls[i].setDisable(!valor);
        }
        if(!valor)
        {
            cPane.setMaxSize(lPant/8*4, aPant/2);
            cPane.setMinSize(lPant/8*4, aPant/2);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        cPane.setMaxSize(lPant/8*5, aPant/2);
        cPane.setMinSize(lPant/8*5, aPant/2);
        
        getLbls();
        
        guardar.setMaxSize(lPant/12,aPant/32);
        guardar.setMinSize(lPant/12,aPant/32);
        guardar.setLayoutX(lPant/12*6);
        guardar.setLayoutY(aPant/16*2);
        
        reestablecer.setMaxSize(lPant/12,aPant/32);
        reestablecer.setMinSize(lPant/12,aPant/32);
        reestablecer.setLayoutX(lPant/12*6);
        reestablecer.setLayoutY(aPant/16*5);
        modificable(tecladoCtrl.getMostrar());
        
    }    
    
}
