/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import controladores.tecladoCtrl;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.TimerTask;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Saul
 */
public class Comunicacion  extends Thread{
    
    private static Mensajero msj;
    private Socket cliente;
    private final boolean principal;
    private final ServerSocket ordenador;
    
    public Comunicacion(Mensajero msjero){
        
        this.msj=msjero;
        principal=msj.isPrincipal();
        cliente=msj.getClienteMovil();
        ordenador=msj.getOrdenadorActual();
    }
    public static void setRes(String res){
        msj.setRespuesta(res);
    }
   public void forzarCierre()throws Exception{
        PrintStream salida=new PrintStream(cliente.getOutputStream());
        salida.flush();
        salida.println("EOC");
        msj.setEstatus("Desconectado");
       currentThread().interrupt();
       throw new Exception("Forzado");
   }
   
   public void cerrarConexion()throws Exception{
        PrintStream salida=new PrintStream(cliente.getOutputStream());
        salida.flush();
        salida.println("EOC");
       cliente.close();
      // salida.close();
       throw new Exception("Forzado");
   }
   
   public void escribir(String mensaje){
       System.out.println("ESCRIBIENDO>"+mensaje);
        try {
            PrintStream salida = new PrintStream(cliente.getOutputStream());
            salida.flush();
            salida.println(mensaje);
        } catch (IOException ex) {
           
            System.out.println("Error al enviar parámetro>"+ex.getMessage());
        } 
   }
   
   public void escuchar(){
       System.out.println("Cliente conectado!!!!!!>"+cliente.getLocalAddress().getHostAddress());
       try{
           while(!msj.isDisponible()){
               Scanner mensajeCliente=new Scanner(cliente.getInputStream());
               String mensaje=mensajeCliente.nextLine();
               if(mensaje.equalsIgnoreCase("EOC")){
                   cerrarConexion();
               }else
                   if(mensaje.startsWith("MSJ>")){//Mensaje de comprobación
                      //  System.out.println("Cliente Móvil:"+mensaje);
                         tecladoCtrl.getComprobador().setMensaje(mensaje);
                   }else{
                       if(mensaje.startsWith("MSJ:")){//Mensaje que contiene parámetros para procesar
                     //  System.out.println("COMMAND>"+mensaje);
                       procesarMensaje(mensaje);
                       } 
                   }
               msj.setEstatus("Nuevo");
               PrintStream salida=new PrintStream(cliente.getOutputStream());
               salida.flush();
               salida.println(msj.getRespuesta());
              // System.out.println("RESPONSE>"+msj.getRespuesta());
           }
       }catch(Exception e){
            msj.setDisponible(true);
            msj.setEstatus("Desconectado");
            System.out.println("Cliente desconectado>");
       }
   }
   
   private void procesarMensaje(String mensaje){
       try{
            int slider=Integer.parseInt(mensaje.substring(mensaje.indexOf(":")+1,mensaje.indexOf("|")));
            Double valor=Double.parseDouble(mensaje.substring(mensaje.indexOf("|")+1));
            tecladoCtrl.cambiarValores(slider,""+valor);
       }catch(Exception e){
           System.out.println("Error al procesar mensaje");
           e.printStackTrace();
       }
   }
    @Override
    public void run()
    {
        try {
            cliente=ordenador.accept();
            msj.setDisponible(false);
            escuchar();
            
        } catch (IOException ex) {
            System.out.println("Error al conectar cliente");
        }
        
    }
    
}
