import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

/**
 * Created by Garrett on 9/2/2017.
 */
public class Player {
    Synthesizer syn;
    MidiChannel[] mc;
    Instrument[] instr;
    boolean muted = false;
    int ACTIVE_CHANNEL = 5;
    int last_active_beatType = 0;
    public Player() throws Exception {
        syn = MidiSystem.getSynthesizer();
        syn.open();
        mc = syn.getChannels();
        for(Instrument i : syn.getAvailableInstruments()) {
            System.out.println(i.toString());
        }
        instr = syn.getDefaultSoundbank().getInstruments();
        mc[ACTIVE_CHANNEL].programChange(instr[0].getPatch().getProgram());

    }
    public void setInstr(int instru) {
        mc[ACTIVE_CHANNEL].programChange(instr[instru].getPatch().getProgram());
        System.out.println("Playing instrument " + instru + " on channel " + ACTIVE_CHANNEL);

    }
    public void play(int note) {
        mc[ACTIVE_CHANNEL].noteOn(note, Dynamic.getDynamic());
        System.out.println("Playing " + (note) + " on channel " + ACTIVE_CHANNEL);

    }
    public void playMetronome(Beat metronome) throws Exception{
        setInstr(metronome.instrument);
        playMetronome(metronome, ACTIVE_CHANNEL);
    }
    public void playMetronome(Beat metronome, int met_channel) throws Exception {
        beatTypes dank = metronome.getCurrentBeat();
        System.out.println(last_active_beatType);
        System.out.println(dank);
        switch(dank) {
            case ACCENT:
//                if(last_active_beatType != 2)
                mc[met_channel].noteOn(metronome.beatNote, metronome.accentedBeatDynamic);
                last_active_beatType = 2;
                break;
            case NORMAL:
//                if(last_active_beatType != 1)
                mc[met_channel].noteOn(metronome.beatNote, metronome.normalBeatDynamic);
                last_active_beatType = 1;
                break;
            case SILENT:
//                if(last_active_beatType != 0)
                mc[met_channel].noteOff(metronome.beatNote);
                last_active_beatType = 0;
                break;
        }
    }
    public void release(int note) {
        mc[ACTIVE_CHANNEL].noteOff(note, Dynamic.getDynamic());
        System.out.println("Cutoff " + (note) + " on channel " + ACTIVE_CHANNEL);
    }
    public void stop() {
        for(MidiChannel MiC : mc) MiC.allNotesOff();
        System.out.println("All Notes Stopped ");
    }
    public void mute() {
        System.out.println("Sound Stopped on " + ACTIVE_CHANNEL);
            mc[ACTIVE_CHANNEL].allSoundOff();


    }
    public void setActiveChannel(int channel) {
        this.ACTIVE_CHANNEL = channel;
        System.out.println("Running on " + ACTIVE_CHANNEL);
    }

    public void play(int note, int octave) {
        mc[ACTIVE_CHANNEL].noteOn(Note.get(note, octave), Dynamic.getDynamic());
        System.out.println("Playing " + (note + octave) + " on channel " + ACTIVE_CHANNEL);
    }
}
