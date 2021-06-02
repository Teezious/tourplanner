package at.matthias.tourplanner.BL;
import at.matthias.tourplanner.DL.APIcomm;
import at.matthias.tourplanner.DL.XMLReader;
import at.matthias.tourplanner.models.RouteItem;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import org.apache.log4j.Logger;

public class Maphandler {
    private final String MAPQUESTPATH = "/config/mapQuestAccess.xml";
    private String url;
    private String key;

    public Maphandler() {
        XMLReader reader = new XMLReader();

        this.key = reader.readXMLElement(getClass().getResource(MAPQUESTPATH).toString(), "key");
        this.url = reader.readXMLElement(getClass().getResource(MAPQUESTPATH).toString(), "url");
    }

    public RouteItem requestHandler(String start, String end, String imagePath) {
        APIcomm client = new APIcomm();
        URL request = createDirectionsURL(start, end);
        RouteItem route = client.directionRequest(request);

        request = getMapURL(route.getSessionId());
        byte[] byteimg = client.imageRequest(request);

        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(byteimg));
            var imgFile = new File(imagePath);
            ImageIO.write(image, "JPG", imgFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return route;
    }

    private URL createDirectionsURL(String start, String end) {
        URL request = null;
        try {
            request = new URL(this.url + "/directions/v2/route?key=" + this.key + "&from=" + start + "&to=" + end + "&unit=k");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return request;
    }

    private URL getMapURL(String sessionID) {
        URL mapurl = null;
        try {
            mapurl = new URL(this.url + "/staticmap/v5/map?key=" + this.key + "&session=" + sessionID + "&size=600,400");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return mapurl;
    }
}
