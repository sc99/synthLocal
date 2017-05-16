/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmusic;

import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jm.audio.synth.Noise;
import jm.audio.synth.Oscillator;

/**
 *
 * @author Bear
 */
public class DefaultMusic {
    public static ObservableList<Map<Integer,Map<Integer,Object>>> defaultComponentes(){
        Map<Integer,Object> oscillator = new HashMap();
        oscillator.put(MyInstrument.OSCILLATOR_WAVE, Oscillator.SINE_WAVE);
        Map<Integer,Object> noise = new HashMap();
        noise.put(MyInstrument.NOISE_TYPE, Noise.WHITE_NOISE);
        Map<Integer,Object> pluck = new HashMap();
        pluck.put(MyInstrument.PLUCK_FEEDBACK, 0.5);
        Map<Integer,Map<Integer,Object>> os = new HashMap();
        Map<Integer,Map<Integer,Object>> no = new HashMap();
        Map<Integer,Map<Integer,Object>> pl = new HashMap();
        os.put(MyInstrument.OSCILLATOR, oscillator);
        no.put(MyInstrument.NOISE,noise);
        pl.put(MyInstrument.PLUCK, pluck);
        return FXCollections.observableArrayList(os,no,pl);
    }
    public static ObservableList<Map<String,Integer>> defaultOscillatorTypes(){
        String[] keys = {"SINE WAVE","COSINE WAVE","SAWTOOTH WAVE","SAWDOWN WAVE","SABERSAW WAVE","PULSE WAVE"};
        Integer[] values = {0,1,2,3,4,5,6,7};
        ObservableList<Map<String,Integer>> src = FXCollections.observableArrayList();
        for(int i = 0; i < keys.length; i++){
            Map<String,Integer> r = new HashMap();
            r.put(keys[i],values[i]);
            src.add(r);
        }
        return src;
    }
    public static ObservableList<Map<String,Integer>> defaultNoiseTypes(){
        String[] keys = {"WHITE NOISE","STEP NOISE","SMOOTH NOISE","BROWN NOISE","FRACTAL NOISE","GAUSSIAN NOISE","WALK NOISE","GENDYN NOISE"};
        Integer[] values = {0,1,2,3,4,5,6,7};
        ObservableList<Map<String,Integer>> src = FXCollections.observableArrayList();
        for(int i = 0; i < keys.length; i++){
            Map<String,Integer> r = new HashMap();
            r.put(keys[i],values[i]);
            src.add(r);
        }
        return src;
    }
}
