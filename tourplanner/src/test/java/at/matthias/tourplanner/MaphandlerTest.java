package at.matthias.tourplanner;

import static org.junit.Assert.*;

import at.matthias.tourplanner.BL.Maphandler;
import org.junit.Test;

public class MaphandlerTest {
  Maphandler mp = new Maphandler();
  @Test
  public void directionURLTest() {
    String url = mp.createDirectionsURL("From", "To").toString();
    String toBe =
        "https://www.mapquestapi.com/directions/v2/route?key=qFXS91XhDmibs35MAOGUUG3XwqzxQMMb&from=From&to=To&unit=k";
    assertEquals(toBe, url);
  }

  @Test
  public void MapURLTest() {
    String url = mp.getMapURL("sessionID").toString();
    String toBe =
        "https://www.mapquestapi.com/staticmap/v5/map?key=qFXS91XhDmibs35MAOGUUG3XwqzxQMMb&session=sessionID&size=600,400";
    assertEquals(toBe, url);
  }
}
