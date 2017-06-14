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
import jm.music.data.CPhrase;
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
    private static Play play;
    private static boolean recording = false;
    private static boolean playing = false;
    private static boolean recorded = false;
    private static boolean paused = false;
    private static ArrayList<StoredMessage> messages;
    private static ArrayList<Instrument> ins;
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
        messages = new ArrayList<>();
        score = new Score();
        score.setTempo(80);
        ins = new ArrayList<>();
    }
    public static void addRecord(Note note, int status, Instrument inst){
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
                    Phrase ph = new Phrase(sm.getNote(),sm.getBegin());
                    Part pt = new Part(ph,"lol" + score.getPartArray().length, score.getPartArray().length);
                    score.addPart(pt);
                    ins.add(inst);
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
        View.print(score);
        play = new Play();
        play.audio(score, ins.toArray(new Instrument[ins.size()]));
    }
    public static void pausePlay(){
        play.pauseAudio();
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
        play.stopAudio();
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
            System.out.println("Stored: DYNAMIC=" + note.getDynamic());
        }
        public Note getNote(){return note;}
        public int getNotePitch(){return note.getPitch();}
        public void setEnd(double end){
            tickEnd = end;
            this.note.setDuration(tickEnd - tickBegin + 0.5);
            System.out.println("Tick: " + tickBegin + " - " + tickEnd);
        }
        public double getBegin(){return tickBegin;}
    }
}
