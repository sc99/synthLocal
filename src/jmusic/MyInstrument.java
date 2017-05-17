/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmusic;

import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.SimpleDoubleProperty;
import jm.audio.AOException;
import jm.audio.AudioObject;
import jm.audio.Instrument;
import jm.audio.synth.Compressor;
import jm.audio.synth.Envelope;
import jm.audio.synth.Filter;
import jm.audio.synth.Noise;
import jm.audio.synth.Oscillator;
import jm.audio.synth.Pluck;
import jm.audio.synth.StereoPan;
import jm.audio.synth.Volume;

/**
 *
 * @author BEAR
 */
public class MyInstrument extends Instrument{

    //Objetos que inician la cadena
    private Oscillator oscillator;
    private Pluck pluck;
    private Noise noise;
    
    //Filtro
    private Filter filter;
    
    //Puntos de subida y bajada
    private Envelope envelope;
    
    //Modificador de volumen
    private Compressor volume;
    
    //Modificador de paneo
    private StereoPan stereopan;
    
    //Atributos propios del instrumento
    //por el momento, no modificables
    private int sampleRate;
    private int channels;
    
    //lista de valores que debe recibir el constructor
    //para guiar la construccion del instrumento
    public static final int OSCILLATOR = 0;
        //tipo de onda para el oscilador
        public static final int OSCILLATOR_WAVE = 1;
    public static final int PLUCK = 2;
        //nivel de sustain del pluck
        public static final int PLUCK_FEEDBACK = 3;
    public static final int NOISE = 4;
        //tipo de ruido
        public static final int NOISE_TYPE = 5;
    public static final int FILTER = 6;
        public static final int FILTER_CUTOFF = 7;
        public static final int FILTER_TYPE = 8;
    public static final int ENVELOPE = 9;
        public static final int ENVELOPE_VALUE = 10;
    public static final int VOLUME = 11;
        public static final int VOLUME_VALUE = 12;
    
    //Map que asigna valores a cada uno de los objetos de audio
    private Map<Integer,Map<Integer,Object>> controller;
    
    public MyInstrument(int sampleRate, Map<Integer,Map<Integer,Object>> controller){
        this.sampleRate = 44100;
        this.channels = 2;
        this.controller = controller;
    }
    @Override
    public void createChain() throws AOException {
        AudioObject first;
        if(controller.get(NOISE) != null){
            Map<Integer,Object> parameters = controller.get(NOISE);
            noise = new Noise(this,(Integer)parameters.get(NOISE_TYPE),sampleRate,channels);
            first = noise;
        }else if(controller.get(PLUCK) != null){
            Map<Integer,Object> parameters = controller.get(PLUCK);
            pluck = new Pluck(this,sampleRate, channels,(Double)parameters.get(PLUCK_FEEDBACK));
            first = pluck;
        }else if(controller.get(OSCILLATOR)!= null){
            Map<Integer,Object> parameters = controller.get(OSCILLATOR);
            oscillator = new Oscillator(this,(Integer)parameters.get(OSCILLATOR_WAVE), sampleRate, channels);
            first = oscillator;
        }else{
            Map<Integer,Object> parameters = new HashMap();
            parameters.put(OSCILLATOR_WAVE, Oscillator.SINE_WAVE);
            controller.put(OSCILLATOR,parameters);
            oscillator = new Oscillator(this,(Integer)parameters.get(OSCILLATOR_WAVE), sampleRate, channels);
            first = oscillator;
        }
        AudioObject next = null;
        if(controller.get(FILTER) != null){
            Map<Integer,Object> parameters = controller.get(FILTER);
            filter = new Filter(first,((SimpleDoubleProperty)parameters.get(FILTER_CUTOFF)).get(),(Integer)parameters.get(FILTER_TYPE));
            next = filter;
        }
        if(controller.get(ENVELOPE) != null){
            Map<Integer,Object> parameters = controller.get(ENVELOPE);
            envelope = new Envelope(next == null ? first : next, (double[])parameters.get(ENVELOPE_VALUE));
            next = envelope;
        }
        if(controller.get(VOLUME) != null){
            Map<Integer,Object> parameters = controller.get(VOLUME);
            volume = new Compressor(next == null ? first : next, 1.0, 1.0, ((SimpleDoubleProperty)parameters.get(VOLUME_VALUE)).get());
            next = envelope;
        }
    }
    @Override
    public void setController(double[] values){
        if(controller.get(FILTER) != null){
            Map<Integer,Object> parameters = controller.get(ENVELOPE);
            filter.setCutOff(((SimpleDoubleProperty)parameters.get(FILTER_CUTOFF)).get());
        }
        if(controller.get(ENVELOPE) != null){
            Map<Integer,Object> parameters = controller.get(ENVELOPE);
            envelope.setBreakPoints((double[])parameters.get(ENVELOPE_VALUE));
        }
        if(controller.get(VOLUME) != null){
            Map<Integer,Object> parameters = controller.get(VOLUME);
            volume.setGain((Double)parameters.get(VOLUME_VALUE));
        }
    }

    public Map<Integer,Map<Integer,Object>> getControllerValues(){
        return controller;
    }
    public void setControllerValues(Map<Integer,Map<Integer,Object>> controller){
        this.controller = controller;
    }
}