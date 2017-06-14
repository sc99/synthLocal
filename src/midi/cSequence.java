/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package midi;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jm.audio.Instrument;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.View;
import jmusic.MyInstrument;
import utilities.Clock;

/**
 *
 * @author Bear
 */
public class cSequence implements Diccionary{
    private static boolean recording = false;
    private static boolean playing = false;
    private static boolean recorded = false;
    private static boolean paused = false;
    private static ArrayList<Part> parts;
    private static ArrayList<StoredMessage> messages;
    private static Clock clock;
    private static Score score;
    private static final int BPM = 120;
    public static boolean isRecording(){return recording;}
    public static boolean isRecorded(){return recorded;}
    public static boolean isPaused(){return paused;}
    public static void startRecording(){
        recording = true;
        clock = new Clock();
        clock.beginRecord();
        score = new Score();
        score.setTempo(BPM);
        parts = new ArrayList<>();
        messages = new ArrayList<>();
    }
    public static void addRecord(Note note, int status){
        double bps = (double)BPM/60;
        double bpms = (double)bps/1000;
        double ts = clock.getCurrent();
        double tick = bpms * ts;
        if(status == 1){
            StoredMessage sm = new StoredMessage(note,tick);
            messages.add(sm);
        }else{
            for(StoredMessage sm: messages){
                if(sm.getNotePitch() == note.getPitch()){
                    sm.setEnd(tick);
                    Part p = new Part();
                    Phrase ph = new Phrase();
                    ph.addNote(sm.getNote());
                    ph.setStartTime(sm.getBegin());
                    p.add(ph);
                    p.setInstrument(parts.size());
                    parts.add(p);
                    messages.remove(sm);
                    break;
                }
            }
        }
    }
    public static void pauseRecord(){
        clock.pauseRecord();
        paused = true;
    }
    public static void unPauseRecord(){
        clock.proceedRecord();
        paused = false;
    }
    public static void stopRecord(){
        clock.endRecord();
        recording = false;
        recorded = true;
        paused = false;
    }
    
    public static void startPlay(){
        playing = true;
        clock.beginPlay();
        Instrument[] ins = new Instrument[parts.size()];
        for(int i = 0; i < ins.length; i++)ins[i] = new MyInstrument(44100, controladores.tecladoCtrl.CONTROLLER);
        score.addPartList(parts.toArray(new Part[parts.size()]));
        Play.audio(score, ins);
    }
    public static void pausePlay(){
        Play.pauseAudio();
        clock.pausePlay();
        paused = true;
    }
    public static void unPausePlay(){
        Play.unPauseAudio();
        clock.proceedPlay();
        paused = false;
    }
    public static void stopPlay(){
        System.out.println("stoped");
        Play.stopAudio();
        clock.endPlay();
        paused = false;
    }
    private static class StoredMessage{
        private Note note;
        private double tickBegin;
        private double tickEnd;
        public StoredMessage(Note note, double tickBegin){
            this.note = note;
            this.tickBegin = tickBegin;
        }
        public Note getNote(){return note;}
        public int getNotePitch(){return note.getPitch();}
        public void setEnd(double end){
            tickEnd = end;
            this.note.setDuration(tickEnd - tickBegin);
            System.out.println("Tick: " + tickBegin + " - " + tickEnd);
        }
        public double getBegin(){return tickBegin;}
    }
}
