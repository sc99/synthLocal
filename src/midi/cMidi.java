/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package midi;

import controladores.cController;
import java.util.ArrayList;
import java.util.Collections;
import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import static midi.Diccionary.CHORUS;
import static midi.Diccionary.MODULATION;
import static midi.Diccionary.NOTE_OFF;
import static midi.Diccionary.NOTE_ON;
import static midi.Diccionary.REVERB;

/**
 *
 * @author Daniel Castillo
 */
public class cMidi extends Thread implements Diccionary{
    private boolean _error;
    private MidiChannel[] _channels;
    private Instrument[] _instruments;
    private int velocity;
    private int channel;
    private ArrayList<Message> queue;
    private Receiver receiver;
    private String target = "";
    private long ini;
    private Synthesizer syn;
    
    public cMidi(){
        try{
            syn = MidiSystem.getSynthesizer();
            if(!syn.isOpen()){
                syn.open();
                cSequence.open(syn);
            }
            this._instruments = syn.getDefaultSoundbank().getInstruments();
            syn.loadInstrument(this._instruments[0]);
            this._channels = syn.getChannels();
            this.velocity = 100;
            this.channel = 0;
            this.queue = cController.queue;
            this.receiver = syn.getReceiver();
        }catch(Exception e){
            _error = true;
            System.out.println("error iniciando!");
            System.out.println(e.getMessage());
        }
    }

    public cMidi(ArrayList<Message> queue, String target){
        try{
            this._instruments = MidiSystem.getSynthesizer().getDefaultSoundbank().getInstruments();
            MidiSystem.getSynthesizer().loadInstrument(this._instruments[0]);
            this._channels = MidiSystem.getSynthesizer().getChannels();
            this.velocity = 100;
            this.channel = 0;
            this.queue = cController.queue;
            this.receiver = MidiSystem.getReceiver();
            this.target = target;
            this.ini = System.currentTimeMillis();
            MidiSystem.getSynthesizer().open();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public boolean isError() {
        return _error;
    }
    
    public void close(){
        try{
            MidiSystem.getSynthesizer().close();
            MidiSystem.getSequencer().close();
        }catch(Exception e){
            _error = true;
            System.out.println(e.getMessage());
        }
        
    }
    
    public void play(int note){
        try{
            //_channels[channel].noteOn(C4, velocity);
            ShortMessage message = new ShortMessage();
            message.setMessage(NOTE_ON, note, velocity);
            Receiver receiver = MidiSystem.getSynthesizer().getReceiver();
            receiver.send(message,-1);
        }catch(Exception e){
            System.out.println("error!");
            System.out.println(e.getMessage());
            _error = true;
        }
    }
    public void controlChannel(){
        try{
//            for(int i = 0; i < _channels.length; i++){
                if(cController.controler[1]){
                    _channels[0].controlChange(CHORUS, cController.chorusValue);
                }else{
                    _channels[0].controlChange(CHORUS, 0);
                }
                if(cController.controler[2]){
                    _channels[0].controlChange(REVERB, cController.reverbValue);
                }else{
                    _channels[0].controlChange(REVERB, 0);
                }
                if(cController.controler[3]){
                    _channels[0].controlChange(MODULATION, cController.pressureValue);
                }else{
                    _channels[0].controlChange(MODULATION, 0);
                }
//            }
        }catch(Exception e){
            System.out.println("error!");
            System.out.println(e.getMessage());
            _error = true;
        }
    }

    public void stop(int note){
        try{
            ShortMessage message = new ShortMessage();
            message.setMessage(NOTE_ON, note, 0);
            Receiver receiver = MidiSystem.getReceiver();
            receiver.send(message,-1);
        }catch(Exception e){
            _error = true;
        }
    }

    @Override
    public void run() {
        while(true){
            try{
                Thread.sleep(5);
                if(!queue.isEmpty()){
                    if(controladores.Clock.getTime() >= queue.get(0).getTimestamp()){
                        receiver.send(queue.get(0),-1);
                        if(controladores.Clock.getBegin() != -1){
                            cSequence.record(queue.get(0).getStatus(), queue.get(0).getData1(), queue.get(0).getData2());
                        }
                        queue.remove(0);
                    }
                }
            }catch(Exception e){
                System.out.println("Error en el hilo!");
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void addToQueue(int note,int suich){
        try{
            controlChannel();
             int velocity= cController.soft?80:127;
            
            if(cController.controler[0]){
                //delay...
                if(cController.controler[4]){
                    //delay with shift...
                    int nummessages = cController.delayValues[1];
                    Message[] message = new Message[nummessages];
                    Message[] clone = new Message[nummessages];
                    long timestamp = controladores.Clock.getTime();
                    for(int i = 0; i < message.length; i++){
                        long added = 0;
                        if(i != 0){
                            added = cController.delayValues[0];
                        }
                        timestamp += added;
                        if(suich != 0){
                            message[i] = new Message(NOTE_ON, note, (velocity - (int)((velocity/nummessages)*i)),timestamp);
                            clone[i] = new Message(NOTE_ON, note + cController.shiftValue, (velocity - (int)((velocity/nummessages)*i)),timestamp);
                        }else{
                            message[i] = new Message(NOTE_OFF, note, 0, timestamp);
                            clone[i] = new Message(NOTE_OFF, note + cController.shiftValue, 0 ,timestamp);
                        }
                    }
                    for(int i = 0; i < nummessages; i++){
                        queue.add(message[i]);
                        queue.add(clone[i]);
                        Collections.sort(queue);
                    }
                }else{
                    //just delay...
                    int nummessages = cController.delayValues[1];
                    Message[] message = new Message[nummessages];
                    long timestamp = controladores.Clock.getTime();
                    for(int i = 0; i < message.length; i++){
                        long added = 0;
                        if(i != 0){
                            added = cController.delayValues[0];
                        }
                        timestamp += added;
                        if(suich != 0){
                            message[i] = new Message(NOTE_ON, note, (velocity - (int)((velocity/nummessages)*i)),timestamp);
                        }else{
                            message[i] = new Message(NOTE_OFF, note, 0,timestamp);
                        }
                    }
                    for(int i = 0; i < nummessages; i++){
                        queue.add(message[i]);

                        Collections.sort(queue);
                    }
                }
                
            }else{
                //single note...
                if(cController.controler[4]){
                    //single note with shift...
                    Message message;
                    Message clone;
                    if(suich == 1){
                        message = new Message(NOTE_ON,note,velocity,controladores.Clock.getTime() + controladores.Clock.BREATH);
                        clone = new Message(NOTE_ON,note + cController.shiftValue,velocity,controladores.Clock.getTime() + controladores.Clock.BREATH);
                    }else{
                        message = new Message(NOTE_OFF,note,velocity,controladores.Clock.getTime() + controladores.Clock.BREATH);
                        clone = new Message(NOTE_OFF,note + cController.shiftValue,velocity,controladores.Clock.getTime() + controladores.Clock.BREATH);
                    }
                    queue.add(message);
                    queue.add(clone);
                    Collections.sort(queue);
                }else{
                    //just a single note...
                    Message message;
                    if(suich == 1){
                        message = new Message(NOTE_ON,note,velocity,controladores.Clock.getTime() + controladores.Clock.BREATH);
                    }else{
                        message = new Message(NOTE_OFF,note,velocity,controladores.Clock.getTime() + controladores.Clock.BREATH);
                    }
                    queue.add(message);
                    Collections.sort(queue);
                }
            }
//            Collections.sort(queue);
        }catch(Exception e){
            _error = true;
            System.out.println(e.getMessage());
        }
    }
}
