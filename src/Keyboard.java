import javax.sound.midi.*;
import java.io.File;
import java.util.Scanner;

/**
 * Created by Garrett on 9/2/2017.
 */
public class Keyboard {



    public static void main(String[] args) throws Exception {
        Player player = new Player();
        Dynamic.setDynamic(Dynamic.ff);
//        player.play(Note.C, Octave.O4);
        Scanner s = new Scanner(System.in);
        Runner runner = new Runner();
        Beat metronome = new Beat();
//        runner.loadFile(new File("src\\MIDI\\castles.mid"));
//        runner.runFile();
        player.play(Note.Eb, Octave.O5);
        Thread.sleep(100);
        player.release(Note.Eb + Octave.O5);
        player.play(Note.E, Octave.O5);
        Thread.sleep(600);
        player.play(Note.E, Octave.O5);
        Thread.sleep(300);
        player.play(Note.E, Octave.O5);
        Thread.sleep(150);
        player.play(Note.E, Octave.O5);

        while (true) {
            String command = s.next().toLowerCase();
            if (command.equals("stop")) System.exit(0);
            if (command.equals("instr")) {
                try {
                    player.setInstr((s.nextInt()));
                } catch (Exception e) {

                }
            }

            if (command.equals("beatsetup")) metronome.beatSetup(s.next());
            if (command.equals("startbeat")) metronome.startBeat(player);
            if (command.equals("stopbeat")) metronome.stopBeat(player);
            if (command.equals("setbpm")) metronome.setBpm(s.nextInt());
            if (command.equals("metronome"))
                while(true) {
                    player.playMetronome(metronome);

//                    if(s.hasNext()) break;
                }
            if (command.equals("off")) player.stop();
            if (command.equals("mute")) player.mute();
            if (command.equals("channel")) player.setActiveChannel(s.nextInt());
            try {
                player.play(Integer.parseInt(command));
            } catch (Exception e) {
            }

        }

    }

}
