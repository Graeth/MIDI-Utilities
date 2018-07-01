import javax.sound.midi.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.Scanner;

/**
 * Created by Garrett on 9/2/2017.
 */
public class Keyboard extends JFrame {
    Player player;
    Runner runner;
    Beat metronome;

    JPanel mainPanel = new JPanel();
    JButton metButton = new JButton("Start/Stop Metronome"),
    commandLineButton = new JButton("Send Command");
    JTextField commandLine = new JTextField("", 30);
    JTextArea metChoiceLabel = new JTextArea("Format:\nBPM:\nAccel:\n",3,20);
    JLabel commandLineLabel = new JLabel("Command Line");
    JSlider bpmSlider, accelSlider;
//    String metChoices[];
    JComboBox metPicker;

    public int bpmSliderVal = 60, accelSliderVal = 0;

    public static void main(String[] args) throws Exception {
        new Keyboard();
    }
    public Keyboard() throws Exception {
        super("MIDI Utility");
        setSize(800,400);
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        //commandLine.addActionListener();
        metronome = new Beat();

//        metChoices = metronome.getTypeNames();
        metPicker = new JComboBox(Constants.workingFormats);
        metChoiceLabel.setEditable(false);
        loadAndDisplayRhythm();
        bpmSlider = new JSlider(JSlider.HORIZONTAL, 20, 180, bpmSliderVal);
        bpmSlider.setMajorTickSpacing(20);
        bpmSlider.setMinorTickSpacing(5);
        bpmSlider.setPaintTicks(true);
//        bpmSlider.setPaintLabels(true);

        accelSlider = new JSlider(JSlider.HORIZONTAL, 0, 20, accelSliderVal);
        accelSlider.setMajorTickSpacing(5);
        accelSlider.setMinorTickSpacing(1);
        accelSlider.setPaintTicks(true);
        metPicker.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                bpmSlider.setValue(((BeatFormat) metPicker.getSelectedItem()).getBpm());
                accelSlider.setValue(((BeatFormat) metPicker.getSelectedItem()).getAcceleration());
            }
        });
//        accelSlider.setPaintLabels(true);
        mainPanel.add(metButton);
        mainPanel.add(commandLineLabel);
        mainPanel.add(commandLine);
        mainPanel.add(commandLineButton);
        mainPanel.add(metPicker);
        mainPanel.add(metChoiceLabel);
        mainPanel.add(bpmSlider);
        mainPanel.add(accelSlider);
        add(mainPanel);
        player = new Player();
        Dynamic.setDynamic(Dynamic.ff);
        Scanner s = new Scanner(System.in);
        runner = new Runner();

//        runner.loadFile(new File("src\\MIDI\\castles.mid"));
//        runner.runFile();
//        player.play(Note.Eb, Octave.O5);
//        Thread.sleep(100);
//        player.release(Note.Eb + Octave.O5);
//        player.play(Note.E, Octave.O5);
//        Thread.sleep(600);
//        player.play(Note.E, Octave.O5);
//        Thread.sleep(300);
//        player.play(Note.E, Octave.O5);
//        Thread.sleep(150);
//        player.play(Note.E, Octave.O5);

        while (true) {
//            String command = s.next().toLowerCase();
//            String comman = commandLine.getText();
            String command = "";
            if(commandLineButton.getModel().isPressed())
            {
                command = commandLine.getText();
                commandLine.setText("");
            }
            displayRhythm();
            BeatFormat curForm = (BeatFormat)metPicker.getSelectedItem();

            bpmSliderVal = bpmSlider.getValue();
            accelSliderVal = accelSlider.getValue();
            curForm.setAcceleration(accelSliderVal);
            curForm.setBpm(bpmSliderVal);
//            metronome.setBpm(bpmSliderVal);
            metPicker.setSelectedItem(curForm);
//            if (command.equals("stop")) System.exit(0);
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
            if (command.equals("load")) loadAndDisplayRhythm();
            // if (command.equals("save")) metronome.saveRhythm(s.next());
            if (command.equals("metronome")) {

                while (!metButton.getModel().isPressed()) {
                    try {
                        player.playMetronome(metronome);
                    } catch(Exception e) {
                        metronome.loadRhythm((BeatFormat) metPicker.getSelectedItem());
                    }
//                    if(s.hasNext()) break;
                }
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
    public void loadAndDisplayRhythm() {
        BeatFormat f = (BeatFormat) metPicker.getSelectedItem();
        metronome.loadRhythm(f);
        metChoiceLabel.setText("Format:"+f.getFormat().toUpperCase()+"\nBPM:" +f.getBpm() + "\nAccel:"+f.getAcceleration());
    }
    public void displayRhythm() {
        BeatFormat f = (BeatFormat) metPicker.getSelectedItem();
        metChoiceLabel.setText("Format:"+f.getFormat().toUpperCase()+"\nBPM:" +f.getBpm() + "\nAccel:"+f.getAcceleration());
    }

}
