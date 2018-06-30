/**
 * Created by Garrett on 9/2/2017.
 */
public class Dynamic {
    public static final int muted = 0, pp = 11, p = 14, mp = 18, mf = 25, f = 35, ff = 60, beatDynamic = 45, accentedBeatDynamic = 60;
    public static int curDynamic = 60;
    public static void setDynamic(int dynamic) {
        curDynamic = dynamic;
    }
    public static int getDynamic() {
        return curDynamic;
    }
}
