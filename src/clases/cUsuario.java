/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.util.ArrayList;

/**
 *
 * @author Alumno
 */
public class cUsuario {
    
    private ArrayList<String []> setConfig=new ArrayList<>();
    public  String [] teclado=new String[36];
    
    public cUsuario(String ubic){
        cSerializa teclRecuperado=new cSerializa();
        setConfig=teclRecuperado.traeConfig(ubic);
        
    }
    public cUsuario(){
        String []s={"q","2","w","3","e","r","5","t","6","y","7","u",
                    "i","9","o","0","p","z","s","x","d","c","f","v",
                    "b","h","n","j","m",",","l",".","ñ","-","{","}",};
        teclado=s;
        String [] relleno={"Synth","MARSOFT TEAM","FDCB\nSCR\nSUHC\nAILT\nLEMB"};
        setConfig.add(relleno);
        
    }
    public void guardarTeclado(){
        String [] xArr=new String [37];
        xArr[0]="Teclado";
        for(int i=0;i<teclado.length;i++)
        {
            xArr[i+1]=teclado[i];
        }
        addConfig(xArr);
    }
    public void cargarTeclado(){
        String [] xArr=new String [36];
        String [] tcls=null;
            for(int i=0;i<setConfig.size();i++){
                if(setConfig.get(i)[0].equals("Teclado")){
                    tcls=setConfig.get(i);
                    for(int j=1;j<tcls.length;j++){
                 
                        xArr[j-1]=tcls[j];
                    }
                    break;
                }
            }
        
        setTeclado(xArr);
    }
    public boolean addConfig(String [] nvConfig){
        boolean add=false;
        
            
            for(int i=0;i<setConfig.size();i++)
            {
                if(setConfig.get(i)[0].equals(nvConfig[0])){
                    setConfig.set(i,nvConfig);
                    add=true;
                    break;
                }
            }
        if(!add){
            setConfig.add(nvConfig);
            add=true;
           }

        
        return add;
        
    }
    public void setTeclado(String [] s){
        teclado=s;
    }
    public  String [] getTeclado(){
        return teclado;
    }
    public boolean guardarConfig(String archivo){
        guardarTeclado();
        cSerializa saver=new cSerializa();
        saver.guardarTeclado(setConfig, archivo);
        return saver.getError();
    }
    
}
