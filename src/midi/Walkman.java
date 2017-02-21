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
    private long duration;
    private boolean stop;
    public Walkman(Sequence sequence, Sequencer sequencer, long duration){
        this.sequence = sequence;
        this.sequencer = sequencer;
        this.duration = duration;
    }

    @Override
    public void run() {
        try{
            System.out.println("reproduciendo " + sequence.getTickLength());
            System.out.println("Duration = " + duration);
            sequencer.setSequence(sequence);
            if(!sequencer.isOpen()){
                sequencer.open();
            }
            sequencer.start();
            long end = System.currentTimeMillis() + duration;
            while(System.currentTimeMillis() <= end){
                if(checkForStop()){
                    sequencer.stop();
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
    public boolean checkForStop(){
        return this.stop;
    }
}
