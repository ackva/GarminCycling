import java.util.Date;

class DataPoint {


    private final String type;
    private final Gps gps;
    private final Date time;
    private final int heartRate;
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
    DataPoint(String type, Gps gps, Date time, int heartRate) {
        this.type = type;
        this.gps = gps;
        this.time = time;
        this.heartRate = heartRate;
    }

    public String getDataPointInfo() {
        return"Type: " + this.type + " -- Date: " + this.time + " -- " + " -- heartRate: " + this.heartRate + " -- GPS: " + this.gps.getLat() + "," + this.gps.getLon();
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

    int getHeartRate() {
        return heartRate;
    }

    public int getCadence() {
        return cadence;
    }
}
