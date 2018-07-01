/**
 * Created by Garrett on 7/1/2018.
 */
public class Constants {

    public static BeatFormat   mazurka = new BeatFormat("bab","Mazurka", 60, 15,0),
                        waltz = new BeatFormat("abb", "Waltz"),
                        polonaise = new BeatFormat("", "Polonaise"),
                        minuet = new BeatFormat("", "Minuet"),
                        nocturne = new BeatFormat("", "Nocturne"),
                        userFormatted = new BeatFormat("abbb", "Untitled", 60, 0, 0);

    public static BeatFormat[] workingFormats = {userFormatted,mazurka, waltz};
}
