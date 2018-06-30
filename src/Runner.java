import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import java.io.File;

/**
 * Created by Garrett on 9/2/2017.
 */
public class Runner {
    File f;
    Sequencer sequencer;
    public Runner() throws Exception {

            sequencer = MidiSystem.getSequencer();

    }
    public void loadFile(File r) throws Exception {
        this.f = r;

        sequencer.setSequence(MidiSystem.getSequence(f));
    }
    public void runFile() throws Exception {
        sequencer.open();
        sequencer.start();
        while(true) {
            if (sequencer.isRunning()) {
                try {
                    Thread.sleep(1000); // Check every second
                } catch (InterruptedException ignore) {
                    break;
                }
            } else {
                break;

            }
        }
        sequencer.stop();
        sequencer.close();

}

    /** Provides help message and exits the program */
    private static void helpAndExit() {
        System.out.println("Usage: java MidiPlayer midifile.mid");
        System.exit(1);
    }
}
