package at.matthias.tourplanner.DL;

import at.matthias.tourplanner.models.RouteItem;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.apache.log4j.Logger;

public class APIcomm {

    public RouteItem directionRequest(URL url) {

        try {
            ObjectMapper om = new ObjectMapper();
            om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(url.toURI()).header("accept", "application/json").build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            RouteItem model = om.readValue(response.substring(9), RouteItem.class); // What TODO here
            return model;
        } catch (IOException | URISyntaxException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] imageRequest(URL url) {

        try {

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(url.toURI()).header("accept", "image.jpeg").build();

            byte[] response = client.send(request, HttpResponse.BodyHandlers.ofByteArray()).body();

            return response;

        } catch (InterruptedException | IOException | URISyntaxException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}
