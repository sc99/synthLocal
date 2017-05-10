/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package midi;

import javafx.beans.property.SimpleDoubleProperty;
import jm.JMC;
import jm.music.data.Note;

/**
 *
 * @author Daniel Castillo
 */
public class Message extends Note implements Comparable<Message>{
    private long time;
    private int status;
    public static final SimpleDoubleProperty pan = new SimpleDoubleProperty(0.5);
    public Message(int status, int pitch, int dynamic, long timestamp){
        super(pitch, JMC.WHOLE_NOTE, dynamic, pan.get());
        this.time = timestamp;
        this.status = status;
    }
    public long getTimestamp(){
        return this.time;
    }
    public int getStatus(){
        return status;
    }
    @Override
    public int compareTo(Message o) {
        if(time < o.getTimestamp()){
            return -1;
        }
        if(time > o.getTimestamp()){
            return 1;
        }
        return 0;
    }
}