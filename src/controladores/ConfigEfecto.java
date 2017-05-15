/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alumno
 */
//Nota : hacer método apra jalar todos los efectos desde el teclado 
// y guardarlos todos de jalón
public class ConfigEfecto extends synthCtrl implements Initializable {

    
    
    @FXML
    AnchorPane ventanaConfig;
    @FXML
    AnchorPane ventEfect;
    @FXML
    VBox efectos;
    @FXML
    Button Delay;
    @FXML
    Button  Chorus;
    @FXML
    Button Reverb;
    @FXML
    Button PitchShift;
    @FXML
    Label labMsj;
    @FXML
    Label lab1;
    @FXML
    Label lab2;
    
    @FXML
    Slider sDelay;
    @FXML
    Slider sFeedback;
    @FXML
    Slider sChorus;
    @FXML
    Slider sReverb;
    @FXML
    Button Pressure;
    @FXML
    Slider sPressure;
    @FXML
    Slider sPitchShift;
//    @FXML
//    Button guardar;
    @FXML
    Button add;
    @FXML
    Button muestra;
    @FXML
    HBox paneBtns;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lab1.setVisible(false);
        lab2.setVisible(false);
        ventanaConfig.setMaxSize(lPant/2,aPant/2);
        ventanaConfig.setMinSize(lPant/2,aPant/2);
        
        ventEfect.setPrefSize(lPant/8*4,aPant/3);
//        ventEfect.setStyle("-fx-background-color:'blue'");
        ventEfect.setLayoutX(lPant/8);
        
        efectos.setPrefSize(lPant/8, aPant/2);
//        efectos.setStyle("-fx-background-color:'red'");
        
        
        Delay.setPrefSize(lPant/8, aPant/32);
        Chorus.setPrefSize(lPant/8,aPant/32);
        
        Reverb.setPrefSize(lPant/8,aPant/32);
        PitchShift.setPrefSize(lPant/8,aPant/32);
        Pressure.setPrefSize(lPant/8,aPant/32);
        
        efectos.setMargin(Delay,new Insets(30,20,10,10));
        efectos.setMargin(Chorus,new Insets(30,20,10,10));
       
        efectos.setMargin(Reverb,new Insets(30,20,10,10));
        efectos.setMargin(PitchShift,new Insets(30,20,10,10));
        efectos.setMargin(Pressure,new Insets(30,20,10,10));
        
        labMsj.setLayoutX(lPant/32*5);
        labMsj.setLayoutY(aPant/64);
        
        lab1.setLayoutX(lPant/128*21);
        lab1.setLayoutY(aPant/32*3);
        
        lab2.setLayoutX(lPant/128*20);
        lab2.setLayoutY(aPant/32*8);
        
        paneBtns.setPrefSize(lPant/8*4,aPant/6);
        paneBtns.setLayoutX(lPant/8);
        paneBtns.setLayoutY(aPant/3);
//        paneBtns.setStyle("-fx-background-color:'black'");
        
        add.setPrefSize(lPant/12, aPant/32);
//        guardar.setPrefSize(lPant/12,aPant/32);
        muestra.setPrefSize(lPant/12,aPant/32);
        
        paneBtns.setSpacing(lPant/16);
        paneBtns.setMargin(add,new Insets(35,0,0,lPant/16));
        paneBtns.setMargin(muestra,new Insets(35,0,0,0));
//        paneBtns.setMargin(guardar,new Insets(35,0,0,0));
        
    }   
    public void vacio(Stage st){
        
        Alert alerta=new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("¡Hey!");
        alerta.setHeaderText("Algo anda mal...");
        alerta.setContentText("Primero debes seleccionar una configuracion de efectos");
        Optional<ButtonType> result=alerta.showAndWait();
//        if(result.get() == ButtonType.OK){
//            st.close();
//        }
        
    }
    @FXML
    public void manejaBtn(ActionEvent e){
        if(labMsj.getText().equals("Configuración"))
        {
            vacio((Stage)labMsj.getScene().getWindow());
            System.out.println("Nel perro");
        }
        else
        {           
                if(e.getSource()==muestra){
                   ArrayList<String []> s=tecladoCtrl.set.getSetEfectos();
                   for(int i=0;i<s.size();i++){
                       System.out.println("Efecto"+i);
                       for(int j=0;j<s.get(i).length;j++){
                         System.out.println(s.get(i)[j]);
                       }
                   } 
                   
                }
                else{
                if(e.getSource()==add){
                    boolean estado=true;
                    
                  
                    String setEf []=labMsj.getText().equals("Delay")? new String[3]:new String[2];            
                    if(labMsj.getText().equals("Delay")){
                    setEf[0]=labMsj.getText();
                    setEf[1]=""+sDelay.getValue();
                    setEf[2]=""+(int)sFeedback.getValue();
                        estado=Double.parseDouble(setEf[1])>0.0?false:true;
                    tecladoCtrl.Delay.setDisable(estado);
                    }else
                    if(labMsj.getText().equals("Chorus")){
                    setEf[0]=labMsj.getText();
                    setEf[1]=""+(int)sChorus.getValue();
                    estado=Integer.parseInt(setEf[1])>0?false:true;
                    tecladoCtrl.Chorus.setDisable(estado);
                    }else
                    if(labMsj.getText().equals("Reverb")){
                    setEf[0]=labMsj.getText();
                    setEf[1]=""+(int)sReverb.getValue();
                    estado=Integer.parseInt(setEf[1])>0?false:true;
                    tecladoCtrl.Reverb.setDisable(estado);
                    }else
                    if(labMsj.getText().equals("Pitch Shift")){
                    setEf[0]=labMsj.getText();
                    setEf[1]=""+(int)sPitchShift.getValue();
                    estado=Integer.parseInt(setEf[1])>0?false:true;
                    tecladoCtrl.PitchShift.setDisable(estado);
                    }else
                    if(labMsj.getText().equals("Pressure")){
                    setEf[0]=labMsj.getText();
                    setEf[1]=""+(int)sPressure.getValue();
                    estado=Integer.parseInt(setEf[1])>0?false:true;
                    tecladoCtrl.Tremolo.setDisable(estado);
                    }
                      changeController(!estado);
                    tecladoCtrl.set.setEfecto(setEf);
                }
                }
            
        }
    }
    public void changeController(boolean value){
        String currentPedal = labMsj.getText();
        if(currentPedal.equals("Delay")){ 
            cController.controler[0] = value;
        }else
        if(currentPedal.equals("Chorus")){
            cController.controler[1] = value;
        }else
        if(currentPedal.equals("Reverb")){
            cController.controler[2] = value;
        }else
        if(currentPedal.equals("Pressure")){
            cController.controler[3] = value;
        }else
        if(currentPedal.equals("Pitch Shift")){
            cController.controler[4] = value;
        }
    }
    public void setControllerValues(String control, int value){
        if(control.equals("DD")){ 
            cController.delayValues[0] = value;
        }else
        if(control.equals("DF")){
            cController.delayValues[1] = value;
        }else
        if(control.equals("C")){
            cController.chorusValue = value;
        }else
        if(control.equals("R")){
            cController.reverbValue = value;
        }else
        if(control.equals("P")){
            cController.pressureValue = value;
        }else
        if(control.equals("S")){
            cController.shiftValue = value;
        }
    }
    public Slider getSlider(double min, double max, double value, int minorTickUnit, double majorTickCount, double blockIncrement){
        Slider slider = new Slider(min, max, value);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMinorTickCount(minorTickUnit);
        slider.setMajorTickUnit(2);
        slider.setBlockIncrement(1);
        slider.setSnapToTicks(true);
        slider.setOrientation(Orientation.HORIZONTAL);
//        slider.setLayoutX(20);
//        slider.setLayoutY(120);
        return slider;
    }
    
    @FXML
    public void apareceSliders(ActionEvent e){
        ventEfect.getChildren().clear();
        lab1.setVisible(true);
        lab2.setVisible(true);
                if(e.getSource()==Delay){
                    labMsj.setText("Delay");
                    lab1.setText("Retraso");
                    sDelay = getSlider(0,1,0,1,0.25,0.125);
                    sDelay.setSnapToTicks(false);
                    sDelay.valueProperty().addListener(new ChangeListener<Number>(){
                        @Override
                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                            System.out.println((int)(newValue.doubleValue()*1000000000));
                            setControllerValues("DD",(int)(newValue.doubleValue()*1000000000));
                        }
                    });
                    sDelay.setLayoutX(lPant/8);
                    sDelay.setLayoutY(aPant/32*4);
                    lab1.setLayoutX(lPant/128*21);

                    sFeedback = getSlider(1,10,0, 1, 2, 1);
                    sFeedback.valueProperty().addListener(new ChangeListener<Number>(){
                        @Override
                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                            setControllerValues("DF",newValue.intValue());
                            //System.out.println(newValue.intValue());
                        }
                    });                       
                    lab2.setText("Retroalimentación");

                    sFeedback.setLayoutX(lPant/8);
                    sFeedback.setLayoutY(aPant/32*9);
                    ventEfect.getChildren().addAll(labMsj,sDelay,sFeedback,lab1,lab2);
                }else
                if(e.getSource()==Chorus){
                    labMsj.setText("Chorus");
                    sChorus = getSlider(0,127,0,1,16,8);
                    sChorus.valueProperty().addListener(new ChangeListener<Number>(){
                        @Override
                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                            setControllerValues("C",newValue.intValue());
                        }
                    });
                    lab1.setText("Intensidad");
                    lab1.setLayoutX(lPant/128*21);
                    sChorus.setLayoutX(lPant/8);
                    sChorus.setLayoutY(aPant/32*6);
                    ventEfect.getChildren().addAll(labMsj,sChorus,lab1);
                }else
                if(e.getSource()==Reverb){
                    labMsj.setText("Reverb");
                    sReverb = getSlider(0,127,0,1,16,8);
                    sReverb.valueProperty().addListener(new ChangeListener<Number>(){
                        @Override
                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                            setControllerValues("R",newValue.intValue());
                        }
                    });
                    lab1.setText("Reverbación");
                    lab1.setLayoutX(lPant/128*21);
                    sReverb.setLayoutX(lPant/8);
                    sReverb.setLayoutY(aPant/32*6);
                    ventEfect.getChildren().addAll(labMsj,sReverb,lab1);
                }else
                if(e.getSource()==Pressure){
                    labMsj.setText("Pressure");
                    sPressure = getSlider(0,127,0,1,16,8);
                    sPressure.valueProperty().addListener(new ChangeListener<Number>(){
                        @Override
                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                            setControllerValues("P",newValue.intValue());
                        }
                    });
                    lab1.setText("Tiempo de interrupción");
                    lab1.setLayoutX(lPant/128*18);
                    sPressure.setLayoutX(lPant/8);
                    sPressure.setLayoutY(aPant/32*6);
                    ventEfect.getChildren().addAll(labMsj,sPressure,lab1);
                }else
                if(e.getSource()==PitchShift){
                    labMsj.setText("Pitch Shift");
                    sPitchShift = getSlider(0,5,0,1,1,1);
                    sPitchShift.valueProperty().addListener(new ChangeListener<Number>(){
                        @Override
                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                            setControllerValues("S",newValue.intValue());
                        }
                    });
                    lab1.setText("Saturación");
                    lab1.setLayoutX(lPant/128*21);
                    sPitchShift.setLayoutX(lPant/8);
                    sPitchShift.setLayoutY(aPant/32*6);
                    ventEfect.getChildren().addAll(labMsj,sPitchShift,lab1);
                }
                //independiente al switch)){ boton reproduccion de prueba
//                 add = new Button("Agregar");
//                add.setOnAction(new EventHandler<ActionEvent>(){
//                    @Override
//                    public void handle(ActionEvent event) {
//                        changeController(true);
//                    }
//                });
            }
    
    
}
