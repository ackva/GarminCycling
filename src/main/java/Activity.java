import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Activity {

    private String type;
    private String id;
    private String name;
    private Date date;
    private Gps startPosition;
    private Gps endPosition;
    private Date endTime;
    private double avgHeartRate;
    private double duration; // in minutes
    private ArrayList<DataPoint> dataPoints;

    Activity() {
    }

    Activity(String type, String id, String name, Date date) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.date = date;
        this.dataPoints = new ArrayList<DataPoint>();
        this.avgHeartRate = 0;
        this.duration = 0;

    }

    void addDataPoint(DataPoint dataPoint) {
        // Add data point
        dataPoints.add(dataPoint);
        // Update Average Heart Rate
        this.avgHeartRate = ( this.avgHeartRate * (this.dataPoints.size() - 1) + dataPoint.getHeartRate() ) / this.dataPoints.size();
        // Update Duration (in min)
        this.duration = TimeUnit.MILLISECONDS.toMinutes(Math.abs(dataPoint.getTime().getTime() - this.date.getTime()));
    }

    private String getActivityInfo() {
        return "Name: " +  this.name + "Type: " +  this.type +" -- Date: " + this.date + " -- " + " -- # of points: " + this.dataPoints.size();

    }

    private double getAvgHeartRate() {
        return avgHeartRate;
    }

    public String getType() {
        return type;
    }

    Gps getStartPosition() {
        return startPosition;
    }

    Gps getEndPosition() {
        return endPosition;
    }

    private Date getEndTime() {
        return endTime;
    }

    private double getDuration() {
        return this.duration;
    }

    void setStartPosition(Gps startPosition) {
        this.startPosition = startPosition;
    }

    void setEndPosition(Gps endPosition) {
        this.endPosition = endPosition;
    }

    void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    void printSummary() {
            System.out.println(" **Activity Summary ** ");
            System.out.println(getActivityInfo());
            System.out.println("Avg HR: " + this.getAvgHeartRate());
            System.out.println("Duration: " + this.getDuration());
            System.out.println("End Time: " + this.getEndTime());
            System.out.println("End Position: " + this.getEndPosition().getLat() + "," + this.getEndPosition().getLon());
        }



}
