/**
 * Created by Garrett on 9/2/2017.
 */
public class Note {
    public static final int Bs = 0, C = 0, Cs = 1, Db =1, D = 2, Ds = 3,
    Eb = 3, E = 4, Fb = 4, Es = 5, F = 5, Fs = 6, Gb = 6, G = 7, Gs = 8,
    Ab = 8, A = 9, As = 10, Bb = 10, B = 11, Cb = 11;


    public int id = -1;
    public Note(int note, int octave) {
        if(IsValidNote(note,octave)) this.id = note + octave;
        else
            System.err.println("invalid note" + note + octave);
    }
    public boolean IsValidNote(int note, int octave) {
        return note+octave < 128 && note+octave >= 0;
    }
    public int returnID() {
        return id;
    }
    public static int get(int note, int octave) {
        if(note+octave < 128 && note+octave >= 0) return note + octave;
        else
            System.err.println("invalid note" + note + octave);
        return 0;
    }
}
