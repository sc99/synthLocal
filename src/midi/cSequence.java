/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package midi;

import java.util.ArrayList;
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
    private static boolean recording = false;
    private static boolean playing = false;
    private static boolean recorded = false;
    private static boolean paused = false;
    private static ArrayList<Phrase> phrases;
    private static Clock clock;
    private static Score score;
    public static boolean isRecording(){return recording;}
    public static boolean isRecorded(){return recorded;}
    public static boolean isPaused(){return paused;}
    public static void startRecording(){
        recording = true;
        clock = new Clock();
        clock.beginRecord();
        score = new Score();
        score.setTempo(120);
        phrases = new ArrayList<>();
    }
    public static void addRecord(Note note){
        double tick = ((double)2/1000)*(double)clock.getCurrent();
        System.out.println(clock.getCurrent());
        System.out.println((double)2/1000);
        System.out.println(((double)2/1000)*(double)clock.getCurrent());
        Phrase p = new Phrase();
        p.add(note);
        p.setStartTime(tick);
        p.setInstrument(phrases.size()-1);
        phrases.add(p);
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
        Instrument[] ins = new Instrument[phrases.size()];
        for(int i = 0; i < ins.length; i++)ins[i] = new MyInstrument(44100, controladores.tecladoCtrl.CONTROLLER);
        score.addPart(new Part(phrases.toArray(new Phrase[phrases.size()])));
        View.print(score);
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
}
