/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmusic;

import controladores.tecladoCtrl;
import static controladores.tecladoCtrl.statusMixers;
import jm.JMC;
import jm.audio.Instrument;
import jm.music.data.Note;
import jm.music.rt.RTLine;

/**
 *
 * @author Bear
 */
public class MyTempLine extends RTLine implements JMC{
    private Note note;
    private int position;
    
    public MyTempLine(Instrument[] in, Note note, int position){
        super(in);
        this.note = note;
        this.position = position;
    }
    @Override
    public Note getNextNote() {
        if(this.note.getPitch() == REST){
            this.pause();
            if(statusMixers.get(position) != null)statusMixers.get(this.position).stop();
            statusMixers.remove(this.position);
        }
        Note note = new Note(this.note.getPitch(),this.note.getRhythmValue(), this.note.getDynamic(), this.note.getPan());
        this.note = new Note(REST,REST);
        return note;
    }
    
}
