import java.math.BigDecimal;
import java.math.RoundingMode;

public class Gps {

    private String lat;
    private String lon;
    private double ele;


    public Gps(String lat, String lon, double ele) {
        this.lat = lat;
        this.lon = lon;
        this.ele = ele;
    }

    public String getLon() {
        return lon;
    }

    public String getLat() {
        return lat;
    }

    public double getEle() {
        return ele;
    }
}
