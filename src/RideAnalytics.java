import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RideAnalytics {

    public static void main(String[] args) throws Exception {

        // Initialize Activities list
        ArrayList<Activity> activities = new ArrayList();

        // Get all GPX files in folder
        File folder = new File("rides");
        File[] listOfFiles = folder.listFiles();

        String activityObjectName = "";

        // For each GPX file, create activity
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                activities.add(createActivity("rides/" + listOfFiles[i].getName()));
            }
        }

        // Print summary of all activities
        for (Activity a: activities) {
            a.printSummary();
        }
        // Get API result from map service
        String apiResult;
        ApiRequest navigation = new ApiRequest("get", args[0]);
        apiResult = navigation.apiGetRequest(activities.get(0).getStartPosition(), activities.get(0).getEndPosition(),activities.get(0).getType());
        Double distance = navigation.getDistance(apiResult);
        System.out.println("API Distance in km: " + Math.round(distance / 1000));
        Double time = navigation.getTime(apiResult);
        System.out.println("API Time in min: " + Math.round(time / 60000));



    }




    // Create activity objects including GPS coordinates and Data points ("tracking points")

    public static Activity createActivity(String inputPath) {

        try {

            File fXmlFile = new File(inputPath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);


            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            XPath xPath = XPathFactory.newInstance().newXPath();

            // Parse Activity Name
            String name = xPath.compile("*/trk/name").evaluate(doc);

            // Parse Activity Type
            String type = xPath.compile("*/trk/type").evaluate(doc);

            // Parse Activity Date & Time
            String tempDate = xPath.compile("*/metadata/time").evaluate(doc).substring(0,19);
            Date date = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(tempDate));

            // Generate Activity ID
            String id = type + "_" + tempDate + "_" + name.replaceAll(" ","_");

            Activity activity = new Activity(type, id, name, date);

            // Go over each tracking point in XML records; indicated 'trkpt'
            NodeList nList = doc.getElementsByTagName("trkpt");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    // Parse information from XML
                    Element eElement = (Element) nNode;
                    // GPS coordinates
                    Gps gps = new Gps(
                            eElement.getAttribute("lat"), // Latitude
                            eElement.getAttribute("lon"), // Longitude
                            Double.valueOf(eElement.getElementsByTagName("ele").item(0).getTextContent())); // Elevation
                    // Heart rate, Time
                    int heartRate = Integer.parseInt(eElement.getElementsByTagName("ns3:hr").item(0).getTextContent());
                    String tempTime = eElement.getElementsByTagName("time").item(0).getTextContent();
                    Date time = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(tempTime));

                    // Add data point to list in Activity object
                    activity.addDataPoint(new DataPoint(type, gps, time, heartRate));

                    if ( temp == 0)
                        activity.setStartPosition(gps);
                    if (temp == nList.getLength() - 1) {
                        activity.setEndTime(time);
                        activity.setEndPosition(gps);
                    }

                }
            }
            return activity;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Activity();

    }

}
