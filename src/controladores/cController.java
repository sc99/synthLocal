/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.util.ArrayList;

/**
 *
 * @author Bear
 */
public class cController {
    public static boolean[] controler = {false,false,false,false,true}; //activa los pedales
    public static int[] delayValues = {500000,5}; //primer valor = delay; segundo valor = feedback
    public static int chorusValue = 0;
    public static int reverbValue = 0;
    public static int pressureValue = 0;
    public static int shiftValue = 0;
    public static boolean soft=false;
    public static ArrayList<midi.Message> queue=new ArrayList<>();
    //el valor de cada tecla
    public static String[] keyboard = 
            {"q","2","w","3","e","r","5","t","6","y","7","u",
                          "i","9","o","0","p","z","s","x","d","c","f","v",
                          "g","b","h","n","j","m","k",",","l",".","ñ","-",};
}

