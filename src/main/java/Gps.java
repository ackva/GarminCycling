public class Gps {

    private final String lat;
    private final String lon;
    private final double ele;


    Gps(String lat, String lon, double ele) {
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
