/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package midi;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.ShortMessage;

/**
 *
 * @author Daniel Castillo
 */
public class Message extends ShortMessage implements Comparable<Message>{
    private long time;
    private int vel;
    private long phaser;
    public Message(int status, int data1, int data2,long timestamp) throws InvalidMidiDataException {
        super(status,data1,data2);
        this.time = timestamp;
        this.vel = data2;
    }
    public long getTimestamp(){
        return this.time;
    }
    public int getVel(){
        return this.vel;
    }
    public void setPhaser(long phaser){
        this.phaser = phaser;
    }
    public long getPhaser(){
        return this.phaser;
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