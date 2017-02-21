/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Saul
 */
public class cSerializa implements Serializable{
    
    ArrayList<String []> setRec=new ArrayList<String []>();
    private boolean error=false;
    ArrayList<String []> setConfiguraciones=new ArrayList<String []>();
    
    public boolean getError(){
        return this.error;
    }
    public ArrayList<String []> traeSet(String archivo){
        try{
            ObjectInputStream entrada=new ObjectInputStream(new FileInputStream(archivo));
            setRec=(ArrayList<String []>)entrada.readObject();
            entrada.close();
        }catch(Exception ex){
            ex.getMessage();
            error=true;
        }
        return setRec;
    }
    public ArrayList<String []> traeConfig(String archivo){
        try{
            ObjectInputStream entrada=new ObjectInputStream(new FileInputStream(archivo));
            setConfiguraciones=(ArrayList<String []>)entrada.readObject();
            entrada.close();
        }catch(Exception ex){
            ex.getMessage();
            error=true;
        }
        return setConfiguraciones;
    }
    public void guardarTeclado(ArrayList<String []> config,String archivo){
        try{
           
            FileOutputStream salida=new FileOutputStream(archivo);
            ObjectOutputStream objSalida=new ObjectOutputStream(salida);
            objSalida.writeObject(config);
            objSalida.close();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            error=true;
        }
    }
    public void guardarSet(ArrayList<String []> set,String archivo){
        try{
            FileOutputStream salida=new FileOutputStream(archivo);
            ObjectOutputStream objSalida=new ObjectOutputStream(salida);
            objSalida.writeObject(set);
            objSalida.close();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            error=true;
        }
    }
}
