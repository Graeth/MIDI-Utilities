/**
 * Created by Garrett on 7/1/2018.
 */
public class BeatFormat {
    public String format = "";

    public String name = "";

    public int getBpm() {
        return bpm;
    }

    public void setBpm(int bpm) {
        this.bpm = bpm;
    }

    public int bpm = 60;

    public int getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(int acceleration) {
        this.acceleration = acceleration;
    }

    public int acceleration = 0;

    public int getJerk() {
        return jerk;
    }

    public void setJerk(int jerk) {
        this.jerk = jerk;
    }

    public int jerk = 0;

    public BeatFormat(String form, String name) {
        this(form,name,60,0,0);

    }
    public BeatFormat(String form, String name, int bpm, int accel, int jerk) {
        this.format = form;
        this.name = name;
        this.bpm = bpm;
        this.acceleration = accel;
        this.jerk = jerk;
    }
    public String toString() {return name;}
    public String getName() {
        return name;
    }

    public void setFormat(String f) {
        format = f;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getFormat() {
        return format;
    }
}
