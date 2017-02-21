/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package midi;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;
import javax.sound.midi.Transmitter;

/**
 *
 * @author Bear
 */
public class cSequence implements Diccionary{
    private static boolean recording = false;
    private static boolean rewind = false;
    private static Sequencer sequencer;
    private static Sequence sequence;
    private static Track track;
    private static int[][] cc;
    private static long duration = 0;
    private static Walkman w;
    //according to Java Sound Demo
    //ticks = (time * resolution) / 500

    /**
     *
     * @param status
     * @param note
     * @param velocity
     * @throws InvalidMidiDataException
     * @throws MidiUnavailableException
     */
    public static boolean isRecording(){
        return recording;
    }
    public static void turnRecording(){
        if(recording){
            recording = false;
            if(controladores.Clock.getBegin() != -1){
                controladores.Clock.setMark();
                setDuration();
            }
        }else{
            recording = true;
            if(rewind){
                controladores.Clock.reset();
                clearTrack();
                rewind = false;
            }
            if(controladores.Clock.getBegin() == -1){
                controladores.Clock.begin();
            }else{
                controladores.Clock.setMark();
            }
        }
        System.out.println("recording = " + recording);
    }

    public static void record(int status, int note, int velocity) throws InvalidMidiDataException, MidiUnavailableException{
        if(sequence == null){
            sequence = new Sequence(Sequence.PPQ,20);
        }
        if(track == null){
            track = sequence.createTrack();
            try{
                loadControllers();
            }catch(Exception e){
                //do something...
            }
        }
        if(controladores.Clock.getBegin() != -1){
            ShortMessage msg = new ShortMessage();
            msg.setMessage(status,note,velocity);
            int tick = (int) (controladores.Clock.getTimeforTick() * sequence.getResolution())/500;
            MidiEvent event = new MidiEvent(msg,tick);
            track.add(event);
        }
    }
    public static void open(Synthesizer syn) throws MidiUnavailableException{
        sequencer = MidiSystem.getSequencer();
        Transmitter t = sequencer.getTransmitter();
        Receiver r = syn.getReceiver();
        t.setReceiver(r);
        sequencer.open();
    }
    public static void playRecord() throws MidiUnavailableException, InvalidMidiDataException{
        System.out.println("sequence = " + sequence == null ? false : true);
        System.out.println("sequencer = " + sequencer == null ? false : true);
        if(w == null || !w.isRunning()){
            w = new Walkman(sequence,sequencer,duration);
            w.start();
        }
    }
    public static void stop() throws MidiUnavailableException{
        if(w.isRunning()){
            w.kill();
        }
    }
    public static void setDuration(){
        duration = controladores.Clock.getEnd() != -1 ? controladores.Clock.getEnd() - controladores.Clock.getBegin() : controladores.Clock.getTimeforTick();
    }
    public static void clearTrack(){
        if(track != null && sequence != null){
            sequence.deleteTrack(track);
            track = null;
        }
    }
    public static void finish(){
        controladores.Clock.finish();
        if(recording){
            turnRecording();
        }
        rewind = true;
    }
    public static Sequence getSequence(){
        return sequence;
    }

    private static void loadControllers() throws InvalidMidiDataException{
        ShortMessage cc = new ShortMessage();
        cc.setMessage(CONTROL_CHANGE, CHORUS, controladores.cController.chorusValue);
        MidiEvent chorus = new MidiEvent(cc,0);
        track.add(chorus);
        cc.setMessage(CONTROL_CHANGE, REVERB, controladores.cController.reverbValue);
        MidiEvent reverb = new MidiEvent(cc,0);
        track.add(reverb);
        cc.setMessage(CONTROL_CHANGE, MODULATION, controladores.cController.pressureValue);
        MidiEvent tremolo = new MidiEvent(cc,0);
        track.add(tremolo);
    }
}
