/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package midi;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import jm.music.data.Score;

/**
 *
 * @author Bear
 */
public class cSequence implements Diccionary{
    private boolean recording = false;
    private boolean rewind = false;
    private int[][] cc;
    private long duration = 0;
    private Walkman w;
    
    private Score score;
    //according to Java Sound Demo
    //ticks = (time * resolution) / 500

    public boolean isRecording(){
        return recording;
    }
    public void turnRecording(){
//        if(recording){
//            recording = false;
//            if(controladores.Clock.getBegin() != -1){
//                controladores.Clock.setMark();
//                setDuration();
//            }
//        }else{
//            recording = true;
//            if(rewind){
//                controladores.Clock.reset();
//                clearTrack();
//                rewind = false;
//            }
//            if(controladores.Clock.getBegin() == -1){
//                controladores.Clock.begin();
//            }else{
//                controladores.Clock.setMark();
//            }
//        }
//        System.out.println("recording = " + recording);
    }

    public void record(Message note) throws Exception{
//        ShortMessage msg = new ShortMessage();
//        msg.setMessage(status,note,velocity);
//        int tick = (int) (controladores.Clock.getTimeforTick() * sequence.getResolution())/500;
//        MidiEvent event = new MidiEvent(msg,tick);
    }
    public void playRecord() throws MidiUnavailableException, InvalidMidiDataException{
//        if(w == null || !w.isRunning()){
//            w = new Walkman(sequence,sequencer,duration);
//            w.start();
//        }
    }
    public void stop() throws MidiUnavailableException{
//        if(w.isRunning()){
//            w.kill();
//        }
    }
    public void clearTrack(){
        score = new Score();
    }
    public void finish(){
//        controladores.Clock.end();
//        if(recording){
//            turnRecording();
//        }
//        rewind = true;
    }
}