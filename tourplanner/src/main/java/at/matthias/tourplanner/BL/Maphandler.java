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
    private String url;
    private String key;

    private static final Logger logger = Logger.getLogger(Maphandler.class);

    public Maphandler() {
        XMLReader reader = new XMLReader();
        String path = reader.getFullPath("mapquest");
        this.key = reader.readXMLElement(path, "key");
        this.url = reader.readXMLElement(path, "url");
    }

    public RouteItem requestHandler(String start, String end, String imagePath) {
        logger.info("new Map- and directionRequest");
        APIcomm client = new APIcomm();
        var request = createDirectionsURL(start, end);      // TODO Split this
        RouteItem route = client.directionRequest(request); // TODO Split this
        logger.warn(imagePath + " in requesthandler");
        request = getMapURL(route.getSessionId());
        byte[] byteimg = client.imageRequest(request);
        if (byteimg != null) {
            try {

                BufferedImage image = ImageIO.read(new ByteArrayInputStream(byteimg));

                if (image != null) {
                    var imgFile = new File(imagePath);
                    ImageIO.write(image, "JPG", imgFile);
                } else {
                    logger.warn("Image is null");
                }
            }

            catch (IOException e) {
                logger.error("Error writing Image!" + e);
            }
        }

        return route;
    }

    private URL createDirectionsURL(String start, String end) {
        URL request = null;
        try {
            request = new URL(this.url + "/directions/v2/route?key=" + this.key + "&from=" + start + "&to=" + end + "&unit=k");
        } catch (MalformedURLException e) {
            logger.error("Error creating Directions URL!" + e);
        }
        return request;
    }

    private URL getMapURL(String sessionID) {
        URL mapurl = null;
        try {
            mapurl = new URL(this.url + "/staticmap/v5/map?key=" + this.key + "&session=" + sessionID + "&size=600,400");
        } catch (MalformedURLException e) {
            logger.error("Error creating Map URL!" + e);
        }
        return mapurl;
    }
}
