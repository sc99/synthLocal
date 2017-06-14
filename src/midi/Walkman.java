/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package midi;

import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

/**
 *
 * @author Alumno
 */
public class Walkman extends Thread{
    private Sequence sequence;
    private Sequencer sequencer;
    private long position;
    private long duration;
    private boolean stop;
    private boolean pause;
    private boolean active;
    public Walkman(Sequence sequence, Sequencer sequencer, long duration){
        this.sequence = sequence;
        this.sequencer = sequencer;
        this.duration = duration;
        active = true;
    }

    @Override
    public void run() {
        try{
            sequencer.setSequence(sequence);
            if(!sequencer.isOpen()){
                sequencer.open();
            }
            sequencer.start();
            while(active){
                if(pause){
                    sequencer.stop();
                }
                if(stop){
                    sequencer.stop();
                    active = false;
                }
            }
            sequencer.stop();
            sequencer.setMicrosecondPosition(0);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public boolean isRunning(){
        boolean a = false;
        try{
            a = sequencer.isRunning();
        }catch(Exception e){
            a = false;
        }
        return a;
    }
    public void kill(){
        this.stop = true;
    }
}
