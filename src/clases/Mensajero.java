/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import controladores.tecladoCtrl;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Saul
 */
public class Mensajero {
    
    private String Estatus;
    private String respuesta;
    private String mensaje;
    private boolean disponible;
    private boolean terminado;
    private boolean principal;
    private int puertoEscucha;
    private Socket clienteMovil;
    private ServerSocket ordenadorActual;
    Comunicacion escucha;

    public String getEstatus() {
        return Estatus;
    }

    public void setEstatus(String Estatus) {
        this.Estatus = Estatus;
    }

    public int getPuertoEscucha() {
        return puertoEscucha;
    }

    public void setPuertoEscucha(int puertoEscucha) {
        this.puertoEscucha = puertoEscucha;
    }

    public Socket getClienteMovil() {
        return clienteMovil;
    }

    public void setClienteMovil(Socket clienteMovil) {
        this.clienteMovil = clienteMovil;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    public boolean isPrincipal(){
        return principal;
    }
    public void setPrincipal(boolean principal){
        this.principal=principal;
    }

    public ServerSocket getOrdenadorActual() {
        return ordenadorActual;
    }

    public void setOrdenadorActual(ServerSocket ordenadorActual) {
        this.ordenadorActual = ordenadorActual;
    }
    
    public String getMensaje()
    {
        return mensaje;
    }
    public String getRespuesta(){
        return respuesta;
    }
    public boolean isTerminado(){
        return terminado;
    }
    public void setMensaje(String mensaje){
        this.mensaje=mensaje;
    }
    public void setRespuesta(String respuesta){
        this.respuesta=respuesta;
    }
    public void setTerminado(boolean terminado){
        this.terminado=terminado;
    }
   
   
   
    public synchronized void iniciarComprobador(boolean abrirServidor,boolean esPrincipal,String Respuesta,String estatusServer)
    {
        try{
            if(abrirServidor)
                ordenadorActual=new ServerSocket(getPuertoEscucha(),1);
            setRespuesta(Respuesta);
            setPrincipal(esPrincipal);
            configurarServidor(estatusServer);
            System.out.println("Servicio COMUNICACIÓN MÓVIL :"+Respuesta);
            escucha=new Comunicacion(this);
            escucha.start();
            
        }catch(Exception e)
        {
            System.out.println("Error en Mensajero: "+e.getMessage());
            Estatus="La conexión falló";
            disponible=true;
            
        }
    }
    
    public void enviarParametro(String parametro){
        escucha.escribir(parametro);
    }
    public void configurarServidor(String estatus){
        Estatus=estatus;
        this.mensaje="";
        disponible=true;
        terminado=false;
    }
    public void liberarMensajero()
    {
        try{
            System.out.println("Terminando conexión con móvil");
            terminado=true; 
            //Comunicacion.setRes("EOC");
            escucha.cerrarConexion();
        }catch(Exception e)
        {
            if(!e.getMessage().equals("Forzado")){
                System.out.println("Error al liberar Mensajero: "+e.getMessage());
                Estatus="Se forzó el cierre de la conexión";   
            }else{
                System.out.println(e.getMessage());
                tecladoCtrl.initEscucha(true, true);
            }
        }
    }

   
}
