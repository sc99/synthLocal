/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmusic;

import jm.audio.AOException;
import jm.audio.Instrument;
import jm.audio.synth.Comb;
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

    private Filter filter;
    private Oscillator oscillator;
    private Volume volume;
    private Envelope envelope;
    private StereoPan stereopan;
    private Pluck pluck;
    private Noise noise;
    private Comb comb;
    
    private int sampleRate;
    private int channels;
    public MyInstrument(int sampleRate){
        this.sampleRate = sampleRate;
        this.channels = 1;
    }
    @Override
    public void createChain() throws AOException {
        oscillator = new Oscillator(this, Oscillator.SINE_WAVE, this.sampleRate, this.channels);
        filter = new Filter(oscillator, 10000, Filter.LOW_PASS);
        envelope = new Envelope(filter, 
             new double[] {1.0, 0.2, 0.5, 0.8, 0.3, 1.0, 0.0});
        volume = new Volume(envelope);
        stereopan = new StereoPan(volume);
    }
    public void setController(double[] controlValues){
        filter.setCutOff(controlValues[0]);
    }
}
