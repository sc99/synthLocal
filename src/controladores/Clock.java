/*
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
public class Clock {
    private static long begin = -1;
    private static long end = -1;
    private static ArrayList mark = new ArrayList();
    public final static long BREATH = (long) Math.pow(10, 6) * 5; //a little breath for the synthesizer c:
    private final static long INICIALTIME = System.nanoTime();
    
    public static long getTime(){
        return System.nanoTime() - INICIALTIME;
    }
    public static void setMark(){
        mark.add(0,System.currentTimeMillis());
    }
    public static long getTimeforTick(){
        if(mark.isEmpty()){
            if(begin == -1){
                return 0;
            }else{
                return System.currentTimeMillis() - begin;
            }
        }else{
            //problemas
            if(mark.size() % 2 == 1){
                return System.currentTimeMillis() - (long)mark.get(mark.size()-1);
            }else{
                long acumulation = 0;
                for(int i = 0; i < mark.size(); i += 2){
                    acumulation += (long)mark.get(i) - (long)mark.get(i+1);
                }
                return System.currentTimeMillis() - begin - acumulation;
            }
        }
    }
    public static int getMarkSize(){
        return mark.size();
    }
    public static void reset(){
        mark.clear();
        begin = -1;
        end = -1;
    }
    public static void finish(){
        end = System.currentTimeMillis();
    }
    public static void begin(){
        begin = System.currentTimeMillis();
    }
    public static long getBegin(){
        return begin;
    }
    public static long getEnd(){
        return end;
    }
}
