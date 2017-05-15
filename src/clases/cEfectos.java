/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import controladores.cController;
import controladores.tecladoCtrl;
import java.util.ArrayList;

/**
 *
 * @author Saul
 */
public class cEfectos {
    
    private ArrayList<String []> set;
    
    public cEfectos(){
        set=new ArrayList<String []>();
    }
    public cEfectos(String idSet){
        cSerializa rec=new cSerializa();
       set= rec.traeSet(idSet);
    }
    
    public void setEfecto(String efecto []){
        
        boolean repetido=false;
        for(int i=0;i<set.size();i++){
            String [] xArr=set.get(i);
            if(xArr[0].equals(efecto[0]))
            {
                set.set(i,efecto);
                repetido=true;
                break;
            }
        }
        if(!repetido)
            set.add(efecto);
    }
    public ArrayList<String []> getSetEfectos(){
        return this.set;
    }
    public void  setSetEfectos(String idSet){
       
        cSerializa rec=new cSerializa();
         ArrayList<String []> xSet=rec.traeSet(idSet);
         for(int i=0;i<xSet.size();i++){
            setEfecto(xSet.get(i));
         }
    }
    public String [] getEfecto(String idEfecto){
        String regreso [] = null;
        for(int i=0;i<set.size();i++){
            regreso=set.get(i);
           if(regreso[0].equals(idEfecto))
               break;
        }
        return regreso;
    }
    //Saca del ArrayList todos los Arrays de String con la configuracion
    //de cada efecto y asigna dicha configuración a los efectos correspondientes
    //en cController
    public boolean cargaEfectos(){
        System.out.println("Carga efectos");
        boolean exito=false;
        String sE="";
        boolean estado=true;
        tecladoCtrl.Delay.setDisable(true);
        tecladoCtrl.Chorus.setDisable(true);
        tecladoCtrl.Reverb.setDisable(true);
        tecladoCtrl.PitchShift.setDisable(true);
        tecladoCtrl.Tremolo.setDisable(true);
        for(int i=0;i<set.size();i++){
            System.out.println("Entrando en cargando efectos");
            sE=set.get(i)[0];
//            System.out.println(set.get(i)[1]);
//            System.out.println(sE);
//            System.out.println(sE.equals("Delay"));
            if(sE.equals("Delay")){
//                    System.out.println((int)Double.parseDouble(set.get(i)[1])*1000000000);
                    //System.out.println(set.get(i)[1]);
                    //System.out.println(cController.delayValues[0]);
                    estado=Double.parseDouble(set.get(i)[1])>0.0?false:true;
                    changeController(!estado,set.get(i)[0]);
                    tecladoCtrl.Delay.setDisable(estado);
                    setControllerValues("DD",(int)Double.parseDouble(set.get(i)[1])*1000000000);
                   // System.out.println("Se esta poniendo");
                    setControllerValues("DF",Integer.parseInt(set.get(i)[2]));
                    //System.out.println(cController.delayValues[0]);
                    exito=true;                  
                    
                    
            }else
                if(sE.equals("Chorus")){
                    setControllerValues("C",Integer.parseInt(set.get(i)[1]));
                    exito=true;
                    System.out.println("En chorus");
                    estado=Integer.parseInt(set.get(i)[1])>0?false:true;
                    changeController(!estado,set.get(i)[0]);
                    tecladoCtrl.Chorus.setDisable(estado);
                    System.out.println(estado+" Se cambio?");
                }else
                if(sE.equals("Reverb")){
                    setControllerValues("R",Integer.parseInt(set.get(i)[1]));
                    exito=true;
                    
                    estado=Integer.parseInt(set.get(i)[1])>0?false:true;
                    tecladoCtrl.Reverb.setDisable(estado);
                    changeController(!estado,set.get(i)[0]);
                }else
                if(sE.equals("Pressure")){
                    setControllerValues("P",Integer.parseInt(set.get(i)[1]));
                   exito=true;
                   
                   estado=Integer.parseInt(set.get(i)[1])>0?false:true;
                    tecladoCtrl.Tremolo.setDisable(estado);
                    changeController(!estado,set.get(i)[0]);
                }else
                if(sE.equals("Pitch Shift")){
                    setControllerValues("S",Integer.parseInt(set.get(i)[1]));
                    exito=true;
                    
                    estado=Integer.parseInt(set.get(i)[1])>0?false:true;
                    tecladoCtrl.PitchShift.setDisable(estado);
                    changeController(!estado,set.get(i)[0]);
                }else
                    exito=false;          
            if(!exito){
                System.out.println("Algo anduvo mal");
                break;
            }
        }
        
        return exito;
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
    public void changeController(boolean value,String currentPedal){
        
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
    public boolean guardaSet(String archivo){
        cSerializa guardado=new cSerializa();
        guardado.guardarSet(set,archivo);
        return guardado.getError();
    }
    
}
