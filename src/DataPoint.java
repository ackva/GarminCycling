import java.util.Date;

public class DataPoint {


    private String type;
    private Gps gps;
    private Date time;
    private int heartRate;
    private int cadence;

    // Constructor with Cadence; must be type running!
    public DataPoint(String type, Gps gps, Date time, int heartRate, int cadence) {
        this.type = type;
        this.gps = gps;
        this.time = time;
        this.heartRate = heartRate;
        this.cadence = cadence;
    }

    // Constructor without cadence; must be type cycling!
    public DataPoint(String type, Gps gps, Date time, int heartRate) {
        this.type = type;
        this.gps = gps;
        this.time = time;
        this.heartRate = heartRate;
    }

    public String getDataPointInfo() {
        String info = "Type: " + this.type + " -- Date: " + this.time + " -- " + " -- heartRate: " + this.heartRate + " -- GPS: " + this.gps.getLat() + "," + this.gps.getLon();
        return info;
    }

    public String getType() {
        return type;
    }

    public Gps getGps() {
        return gps;
    }

    public Date getTime() {
        return time;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public int getCadence() {
        return cadence;
    }
}
