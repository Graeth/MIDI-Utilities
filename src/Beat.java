import java.util.ArrayList;

/**
 * Created by Garrett on 6/21/2018.
 */

// Used to return the current beat type for an external player (i.e., follows pattern 1xx2xx while return NORMAL SILENT SILENT NORMAL SILENT SILENT)
enum beatTypes {SILENT, NORMAL, ACCENT}


public class Beat {
    final boolean accent = true, noAccent = false, silent = true, audible = false;
    // The current beat type for an external player
    beatTypes currentBeat = beatTypes.NORMAL;

    String name = "untitled";

    public boolean triggered = false;
    /*
    * @var bpm - current beats per minute of metronome
    * @var timeSigUpper - upper number of time signature (this many beats in a measure)
    * @var timeSigLower - lower number of time signature (this number is a beat)
    * @var acceleration - the bpm acceleration per measure
    * @var iteration - the current measure
    * @var beatNote - the note that is played by the metronome
    * @var uniqueMeasures - amount of different measure types specified in configuration
    * */
    public int bpm = 0;
    public int timeSigUpper = 0;
    public int timeSigLower = 4;



    public int acceleration = 0;
    public int iteration = 0;
    public int beatNote = Octave.O4+Note.C;
    public int uniqueMeasures = 1;

    public int accentedBeatDynamic = 60, normalBeatDynamic = 30, silentBeatDynamic = 0;
    /*
    * @var beatDelay - total delay between the initiation of a beat (millis)
    * @var silenceAfterBeat - time where there is no noise between two notes representing beats (millis)
    * @var beatTime - time where a beat is making noise (millis)
     */
    public int beatDelay = 0, silenceAfterBeat = 0, beatTime = 0, instrument = Instruments.Woodblock;

    //What percentage of beatDelay is silenceAfterBeat
    public double silencePercentage = 0.3;

    // An array that marks if a certain beat is accented or not
    public ArrayList<Boolean> IsAccented;
    // An array that marks if a certain beat is muted or not
    public ArrayList<Boolean> muteBehavior;

    // Lists current time signature "Playing in 3/4 time with 420 beats per minute."
    public void listTimeSignature() {
        System.out.println("Playing in " + timeSigUpper+"/"+timeSigLower+" time with " + bpm + " beats per minute.");
        //System.out.println("These beats are accented: ")

    }

    // be dank make bank
    public Beat() {
        initialize();
    }
    //initializes required arrays, only needs to be called once at initialization of object
    public void initialize() {
        iteration = 0;
        IsAccented = new ArrayList<Boolean>();
        muteBehavior = new ArrayList<Boolean>();
    }

    public void accentBeat(int beat) {
        nextBeat(accent, audible, beat);
    }
    public void normalBeat(int beat) {
        nextBeat(noAccent, audible, beat);
    }
    public void silentBeat(int beat) {
        nextBeat(noAccent, silent, beat);
    }

    public void nextBeat(boolean accented, boolean muted, int beat) {
        IsAccented.add(beat, accented);
        muteBehavior.add(beat, muted);
    }


    /*
    * @param rhythm - the configuration string for the beat - in form abx,aba,abb where a is accented b is unaccented and x is silent
    * this sets IsAccented and muteBehavior accordingly, random mutes and increased rate of mutes is not manipulated here
    * calculates upper time signature by dividing total beats by unique measures and lists time signature
    * */
    public void beatSetup(String rhythm) {
        uniqueMeasures = 1;

        int k = 0;
        for(int i=0; i < rhythm.length(); i++) {
            // k is necessary or abb,axb will add the second accent at index 4 since , isn't part of the array
            k = (i+1)-uniqueMeasures;
            String temp = rhythm.substring(i,i+1);
            if(temp.equals(",")) uniqueMeasures++;
            if(temp.equals("a")) accentBeat(k);
            if(temp.equals("b")) normalBeat(k);
            if(temp.equals("x")) silentBeat(k);
        }
        timeSigUpper = (k+1)/uniqueMeasures;
        listTimeSignature();
        System.out.println(rhythm);
        // don't get that stank
    }
    //starts playing the metronome
    public void startBeat(Player metronome) {
        metronome.play(beatNote);
    }
    //stops playing the metronome
    public void stopBeat(Player metronome) {
        metronome.release(beatNote);
    }
    //sets bpm and redefines time slices
    public void setBpm(int bpm) {
        this.bpm = bpm;
        generateBeatConstants();
    }

    public void setTimeSigUpper(int timeSigUpper) {
        this.timeSigUpper = timeSigUpper;
    }


    /*
    does some quick maff to determine what beattime and beatdelay should be. Call this whenever bpm is changed.
    Necessary bc the percentage of silence should be the same regardless of bpm (or maybe follow a function)
    * */
    public void generateBeatConstants() {
        beatTime = (int)(60000/(double)bpm);
        beatDelay = (int) ((beatTime*(1-silencePercentage)));
        silenceAfterBeat = (int) ((beatTime*silencePercentage));
    }

    public beatTypes getCurrentBeat() throws Exception {
        System.out.println(name);
        if(triggered) {
            triggered = false;
            Thread.sleep(beatDelay);
            System.out.println("DELAY_SILENCE");
            return beatTypes.SILENT;
        } else {
            Thread.sleep(silenceAfterBeat);
            triggered = true;
        }
        if(iteration >= timeSigUpper*uniqueMeasures) {
            iteration = 0;
            setBpm(bpm+acceleration);
        }
            if (muteBehavior.get(iteration)) {
                iteration++;
                return beatTypes.SILENT;
            } else
            if (IsAccented.get(iteration)) {
                iteration++;
                return beatTypes.ACCENT;
            } else

            if (!IsAccented.get(iteration)) {
                iteration++;
                return beatTypes.NORMAL;
            }

        return beatTypes.ACCENT;
    }
    // Plays note with external player
    public void playBeat(Player p) throws Exception {
        //p.setInstr(Instruments.Steel_Drums);
        if(muteBehavior.get(iteration))
        {
            Dynamic.setDynamic(Dynamic.muted);
        }
        else if(IsAccented.get(iteration)) {
            Dynamic.setDynamic(Dynamic.accentedBeatDynamic);
        } else
        {
            Dynamic.setDynamic(Dynamic.beatDynamic);
        }
        //play note
        p.play(beatNote);
        Thread.sleep(beatDelay);
        p.release(beatNote);
        Thread.sleep(silenceAfterBeat);
        iteration++;
    }

    public void loadRhythm(BeatFormat bf) {
        setBpm(bf.getBpm());
        setAcceleration(bf.getAcceleration());
        this.name = bf.getName();
        beatSetup(bf.getFormat());
    }

    public String[] getTypeNames() {
        int formatSize = Constants.workingFormats.length;
        String[] names = new String[formatSize];
        for(int i = 0; i < formatSize; i++)
        {
            names[i] = Constants.workingFormats[i].getName();
        }
        return names;
    }
    public void setAcceleration(int acceleration) {
        System.out.println("Acceleration is " + acceleration + " beats per measure");
        this.acceleration = acceleration;
    }
}
