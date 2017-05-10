/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package midi;

import controladores.cController;
import controladores.tecladoCtrl;
import static controladores.tecladoCtrl.relNoteMixer;
import static controladores.tecladoCtrl.statusMixers;
import java.util.ArrayList;
import java.util.Collections;
import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import jm.audio.RTMixer;
import jm.music.rt.RTLine;
import jmusic.MyInstrument;
import jmusic.MyTempLine;
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
    private MidiChannel[] channels;
    private int velocity;
    private int channel;
    private String target = "";
    private long ini;
    private Synthesizer syn;
    
    public cMidi()throws Exception{
        syn = MidiSystem.getSynthesizer();
        this.channels = syn.getChannels();
        this.velocity = 100;
        this.channel = 0;
    }
    
    public void close(){
        try{
            MidiSystem.getSynthesizer().close();
            MidiSystem.getSequencer().close();
        }catch(Exception e){
            e.printStackTrace();
        }   
    }
    
    public void controlChannel() throws Exception{
        for(int i = 0; i < channels.length; i++){
            if(cController.controler[1]){
                channels[i].controlChange(CHORUS, cController.chorusValue);
            }else{
                channels[i].controlChange(CHORUS, 0);
            }
            if(cController.controler[2]){
                channels[i].controlChange(REVERB, cController.reverbValue);
            }else{
                channels[i].controlChange(REVERB, 0);
            }
            if(cController.controler[3]){
                channels[i].controlChange(MODULATION, cController.pressureValue);
            }else{
                channels[i].controlChange(MODULATION, 0);
            }
        }
    }
    @Override
    public void run() {
        while(true){
            try{
                Thread.sleep(5);
                if(!cController.queue.isEmpty()){
                    if(System.currentTimeMillis() >= cController.queue.get(0).getTimestamp()){
                        Message note = cController.queue.get(0);
                        int suich = note.getStatus();
                        if(suich == 0){
                            //NOTE OFF
                            System.out.println("OFF: " + note.getPitch());
                            if(relNoteMixer.get(note.getPitch()) != null){
                                int mixer = relNoteMixer.get(note.getPitch());
                                if(statusMixers.get(mixer)!=null){
                                    statusMixers.get(mixer).stop();
                                    statusMixers.remove(mixer);
                                }
                                relNoteMixer.remove(note.getPitch());
                            }
                        }else{
                            //NOTE ON
                            System.out.println("ON: " + note.getDynamic());
                            int position = statusMixers.keySet().size();
                            if(relNoteMixer.get(note.getPitch()) == null){
                                jm.audio.Instrument[] in = new jm.audio.Instrument[9];
                                for(int i = 0; i < in.length; i++)
                                    in[i] = new MyInstrument(44100,tecladoCtrl.CONTROLLER);
                                RTLine line = new MyTempLine(in,note,position);
                                RTMixer mixer = new RTMixer(new RTLine[]{line});
                                statusMixers.put(position,mixer);
                                relNoteMixer.put(note.getPitch(),position);
                                mixer.begin();
                            }
    //                        if(controladores.Clock.getBegin() != -1){
    //                            cSequence.record(queue.get(0).getStatus(), queue.get(0).getData1(), queue.get(0).getData2());
    //                        }
                        }
                        cController.queue.remove(0);
                    }
                }
            }catch(Exception e){
                System.out.println("Error en el hilo!");
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Agrega notas a la cola de notas para despues ser tocadas
     * @param note : valor MIDI de la nota por agregar
     * @param suich : 1 = NOTE_ON ; 0 = NOTE_OFF
     */
    public void addToQueue(int note, int suich)throws Exception{
            controlChannel();
             int velocity= cController.soft?80:127;
            
            if(cController.controler[0]){
                //delay...
                if(cController.controler[4]){
                    //delay with shift...
                    int nummessages = cController.delayValues[1];
                    Message[] message = new Message[nummessages];
                    Message[] clone = new Message[nummessages];
                    long timestamp = System.currentTimeMillis();
                    for(int i = 0; i < message.length; i++){
                        long added = 0;
                        if(i != 0){
                            added = cController.delayValues[0];
                        }
                        timestamp += added;
                        if(suich != 0){
                            message[i] = new Message(suich, note, (velocity - (int)((velocity/nummessages)*i)),timestamp);
                            clone[i] = new Message(suich, note + cController.shiftValue, (velocity - (int)((velocity/nummessages)*i)),timestamp);
                        }else{
                            message[i] = new Message(suich, note, 0, timestamp);
                            clone[i] = new Message(suich, note + cController.shiftValue, 0 ,timestamp);
                        }
                    }
                    for(int i = 0; i < nummessages; i++){
                        cController.queue.add(message[i]);
                        cController.queue.add(clone[i]);
                        Collections.sort(cController.queue);
                    }
                }else{
                    //just delay...
                    int nummessages = cController.delayValues[1];
                    Message[] message = new Message[nummessages];
                    long timestamp = System.currentTimeMillis();
                    for(int i = 0; i < message.length; i++){
                        long added = 0;
                        if(i != 0){
                            added = cController.delayValues[0];
                        }
                        timestamp += added;
                        if(suich != 0){
                            message[i] = new Message(suich, note, (velocity - (int)((velocity/nummessages)*i)),timestamp);
                        }else{
                            message[i] = new Message(suich, note, 0,timestamp);
                        }
                    }
                    for(int i = 0; i < nummessages; i++){
                        cController.queue.add(message[i]);
                        Collections.sort(cController.queue);
                    }
                }
                
            }else{
                //single note...
                if(cController.controler[4]){
                    //single note with shift...
                    Message message;
                    Message clone;
                    if(suich == 1){
                        message = new Message(suich,note,velocity,System.currentTimeMillis());
                        clone = new Message(suich,note + cController.shiftValue,velocity,System.currentTimeMillis());
                    }else{
                        message = new Message(suich,note,velocity,System.currentTimeMillis());
                        clone = new Message(suich,note + cController.shiftValue,velocity,System.currentTimeMillis());
                    }
                    cController.queue.add(message);
                    cController.queue.add(clone);
                    Collections.sort(cController.queue);
                }else{
                    //just a single note...
                    Message message;
                    if(suich == 1){
                        message = new Message(suich,note,velocity,System.currentTimeMillis());
                    }else{
                        message = new Message(suich,note,velocity,System.currentTimeMillis());
                    }
                    cController.queue.add(message);
                    Collections.sort(cController.queue);
                }
            }
    }
}
