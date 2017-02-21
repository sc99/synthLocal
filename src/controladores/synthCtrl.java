/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author MARSOFTTEAM
 */
public class synthCtrl implements Initializable {
    
    protected  final int aPant=Toolkit.getDefaultToolkit().getScreenSize().height;
    protected  final int lPant=Toolkit.getDefaultToolkit().getScreenSize().width;
    public static clases.cUsuario sesion=new clases.cUsuario();
    @FXML
    private Label label;
    @FXML
    private Button btnSesion;
    @FXML
    private Button btnInvitado;
    @FXML
    private TextField usr;
    @FXML 
    private PasswordField psw;
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
    protected void salir(Stage st,WindowEvent e){
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        st.getIcons().add(new Image("/imgs/Synth.png"));
        alert.setTitle("Confirmacion");
        alert.setHeaderText("Espera!");
        alert.setContentText("¿Realmente deseas salir?");
        Optional<ButtonType>result=alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            System.out.println("Adios");
            st.close();
            System.exit(0);
        }
        else{
            System.out.println("No salir");
                e.consume();
        }
    }
    protected void cerrarTeclado(Stage st,WindowEvent e){
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        st.getIcons().add(new Image("/imgs/Synth.png"));
        alert.setTitle("Confirmacion");
        alert.setHeaderText("Espera!");
        alert.setContentText("¿Realmente deseas salir?");
        Optional<ButtonType>result=alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            System.out.println("Adios");
            sesion.guardarConfig("C:\\Synth\\Synth.cfg");
            st.close();
            System.exit(0);
        }
        else{
            System.out.println("No salir");
                e.consume();
        }
    }
    
    protected void alerta(Stage st,WindowEvent e){
        Alert alerta=new Alert(AlertType.WARNING);
        st.getIcons().add(new Image("/imgs/Synth.png"));
        alerta.setTitle("¡CUIDADO!");
        alerta.setHeaderText("AVISO");
        alerta.setContentText("Recuerda, los efectos que agregues tendrás que guardarlos"
                +             " en un set si quieres usarlos después");
        Optional<ButtonType> result=alerta.showAndWait();
        if(result.get() == ButtonType.OK){
            st.close();
        }
       
    }
    public void entraTeclado(Stage st,String url) throws IOException{
        String ub=url;
        File cfg=new File(url+"\\Synth.cfg");        
        if(cfg.exists()){
           sesion=new clases.cUsuario(cfg.getAbsolutePath());
           sesion.cargarTeclado();
        }else
        {
            sesion.guardarTeclado();
        }
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/teclado.fxml"));       
        Scene scene = new Scene(root);   
        scene.getStylesheets().add(getClass().getResource("/css/pruebas.css").toExternalForm());
        st.getIcons().add(new Image("/imgs/Synth.png"));
        st.setMaximized(true);
        st.setScene(scene);
        st.setMaximized(false);
        st.setTitle("Synth! MD TECLADO");
        st.setMaximized(true);
        st.setOnCloseRequest(e->cerrarTeclado(st,e));
        st.show();
        scene.getRoot().requestFocus();
    }
    private boolean validaCampos(){
        boolean valido=false;
        String us=usr.getText();
        String clav=psw.getText();
            if(!(us.equals("") && clav.equals(""))){
                System.out.println("Llenos");
                if(verificaCta(us,clav)){
                    System.out.println("Tru");   
                }
            }else
                System.out.println("Vacio");
        return valido;
    }
    public void setTeclado(){
        
    }
     private void avisoCfg(Stage st,String url){
        Alert av=new Alert(AlertType.INFORMATION);
        av.setTitle("\t\t Aviso");
        av.setHeaderText("IMPORTANTE");
        av.setContentText("Se ha creado una nueva carpeta en"+url);
        Optional<ButtonType> result=av.showAndWait();
             
    }
     private void avisoError(Stage st){
        Alert err=new Alert(AlertType.ERROR);
        err.setTitle("\t\t Error ");
        err.setHeaderText("¡Vaya!");
        err.setContentText("Ha habido un problema al cargar los archivos del programa.\n"
                         + "Comprueba la ubicación del folder 'Synth'");
        
         Optional<ButtonType> result=err.showAndWait();
        
    }
    public void fallo(Stage st,String msj){
        Alert err=new Alert(AlertType.ERROR);
        err.setTitle("\t\t Error ");
        err.setHeaderText("¡Vaya!");
        err.setContentText(msj);
        
         Optional<ButtonType> result=err.showAndWait();
    }
    @FXML
    private void manejaBtn(ActionEvent e){
        String ub="";
        Stage nSg=(Stage)btnSesion.getScene().getWindow();
        if(e.getSource()==btnSesion){
            label.setText("Iniciando Sesion...");
            //validaCampos();
            String correo = usr.getText();
            String contra = psw.getText();
            
            String id = "";
            try{
                    clases.cCifrado enc=new clases.cCifrado();
                    correo=enc.Encriptar(correo);
                    contra=enc.Encriptar(contra);
                    id=getCuenta(correo, contra);
                    int validId = Integer.parseInt(id);
                    if(validId == 0){
                        label.setText("CORREO O CONTRASEÑA INCORRRECTOS");
                    }else{

                        File folder=new File("C:\\Synth");
                if(!folder.exists())
                {
                    avisoError(nSg);
                    if(folder.mkdir())
                    {
                        avisoCfg(nSg,folder.getAbsolutePath());
                        //String [] datosUs={'Sesion',usr,psw};
                        //sesion.addConfig(datosUs);
                        // regresa boolean sesion.guardarConfig(folder.getAbsolutePath()+"\\Synth.cfg");
                        
                        //entraTeclado(nSg,folder.getAbsolutePath());
                        entraTeclado(nSg,folder.getAbsolutePath());
                    }
                    else
                        avisoError(nSg);
                   
            }else
            {
                ub=folder.getAbsolutePath();
                entraTeclado(nSg,ub);
            }
                }
            }catch(Exception ex){
                //tiene un error...
                fallo(nSg,"\t No se ha podido iniciar tu sesión, \n"
                        + "\t intentalo más tarde o ingresa como invitado.");
                label.setText("ERROR DEL SERVIDOR: " + id);
            }
        }
        else
            if(e.getSource()==btnInvitado){
                label.setText("Iniciando...");
                    File folder=new File("C:\\Synth");
                try{
                    if(!folder.exists())
                    {
                    avisoError(nSg);
                    if(folder.mkdir())
                    {
                        avisoCfg(nSg,folder.getAbsolutePath());
                        //String [] datosUs={'Sesion',usr,psw};
                        //sesion.addConfig(datosUs);
                        // regresa boolean sesion.guardarConfig(folder.getAbsolutePath()+"\\Synth.cfg");
                        
                        //entraTeclado(nSg,folder.getAbsolutePath());
                        entraTeclado(nSg,folder.getAbsolutePath());
                    }
                    else
                        avisoError(nSg);
                   
                    }else
                    {
                        ub=folder.getAbsolutePath();
                        entraTeclado(nSg,ub);
                    }
                
            }catch(Exception ex){
                    avisoError(nSg);
        }
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

    private static String getCuenta(java.lang.String correo, java.lang.String contra) {

        ws.WsLocal_Service service = new ws.WsLocal_Service();
        ws.WsLocal port = service.getWsLocalPort();
            return port.getCuenta(correo,contra);
    }
}
