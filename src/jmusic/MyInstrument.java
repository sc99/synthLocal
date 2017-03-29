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
    public MyInstrument(){
        
    }
    @Override
    public void createChain() throws AOException {
        
    }
    
}
