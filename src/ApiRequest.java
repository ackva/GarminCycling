import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;
import java.net.*;
import com.google.gson.*;

    /**
     * Inspired by StackOverflow
     */

    public class ApiRequest {
        private String method;
        private String apiKey;

        public ApiRequest(String method, String apiKey) {
            this.method = method;
            this.apiKey = apiKey;
        }

        public String apiGetRequest(Gps start, Gps destination, String meanOfTransport) throws MalformedURLException {

            String vehicle = "";
            switch (meanOfTransport) {
                case "cycling":
                    vehicle = "bike";
                    break;
                case "running":
                    vehicle = "foot";
                    break;
                default:
                    vehicle = "foot";
                    break;
            }

            BigDecimal startLat = new BigDecimal(start.getLat());
            startLat = startLat.setScale(6, RoundingMode.HALF_UP);
            BigDecimal startLon = new BigDecimal(start.getLon());
            startLon = startLon.setScale(6, RoundingMode.HALF_UP);
            BigDecimal destLat = new BigDecimal(destination.getLat());
            destLat = destLat.setScale(6, RoundingMode.HALF_UP);
            BigDecimal destLon = new BigDecimal(destination.getLon());
            destLon = destLon.setScale(6, RoundingMode.HALF_UP);

            StringBuilder result = new StringBuilder();
            URL url = new URL("https://graphhopper.com/api/1/route?point=" + startLat + "," + startLon + "&point=" + destLat + "," + destLon + "&vehicle=" + vehicle + "&locale=en&key=" + this.apiKey);


            //String url = "https://www.openstreetmap.org/directions?engine=graphhopper_bicycle&route=" + startLat + "%2C" + startLon + "%3B" + destLat + "%2C" + destLon;
            try {
/*
                    URL url = new URL("https://graphhopper.com/api/1/route?point=" + startLat + "," + startLon + "&point=" + destLat + "," + destLon + "&vehicle=bike&locale=de&key=" + key;
                    InputStream is = url.openConnection().getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
*/
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod(this.method.toUpperCase());
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                rd.close();



                    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                        Desktop.getDesktop().browse(new URI("https://www.openstreetmap.org/directions?engine=graphhopper_bicycle&route=" + startLat + "%2C" + startLon + "%3B" + destLat + "%2C" + destLon));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            return result.toString();
            }

        public double getDistance(String jsonContent) {
            double distance = 0;

            // Use GSON to populate the attributes
            JsonParser jsonParser = new JsonParser();

            // Parse Job Details into attributes
            JsonObject navigationDetails = jsonParser.parse(jsonContent).getAsJsonObject();
            distance = navigationDetails.get("paths").getAsJsonArray().get(0).getAsJsonObject().get("distance").getAsDouble();

            return distance;
        }

        public double getTime(String jsonContent) {
            double time = 0;

            // Use GSON to populate the attributes
            JsonParser jsonParser = new JsonParser();

            // Parse Job Details into attributes
            JsonObject navigationDetails = jsonParser.parse(jsonContent).getAsJsonObject();
            time = navigationDetails.get("paths").getAsJsonArray().get(0).getAsJsonObject().get("time").getAsDouble();

            return time;
        }
    }