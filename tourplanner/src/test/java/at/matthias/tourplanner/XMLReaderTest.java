package at.matthias.tourplanner;
import static org.junit.Assert.*;

import at.matthias.tourplanner.DL.XMLReader;
import java.util.Map;
import org.junit.Test;
public class XMLReaderTest {
  @Test
  public void getPathTest() {
    XMLReader reader = new XMLReader();
    String path = reader.getPath("mapquest");
    assertEquals("/config/mapQuestAccess.xml", path);
  }
  @Test
  public void getFullPathTest() {
    XMLReader reader = new XMLReader();
    String pathShouldBe = this.getClass().getResource("/config/mapQuestAccess.xml").toString();
    if (pathShouldBe.contains("file:")) {
      String[] cut = pathShouldBe.split(":");
      pathShouldBe = cut[1];
    }
    String path = reader.getFullPath("mapquest");
    assertEquals(pathShouldBe, path);
  }
  @Test
  public void readDbConfigTest() {
    XMLReader reader = new XMLReader();
    Map<String, String> mp = reader.readDbConfig(reader.getFullPath("dbaccess"));
    String dburl = mp.get("dburl");
    String dbname = mp.get("dbname");
    String user = mp.get("user");
    String pw = mp.get("password");
    assertTrue(dburl.equals("jdbc:postgresql://localhost:5432/") && dbname.equals("tourplannerDB")
        && user.equals("tourplanner") && pw.equals("tourplanner"));
  }
}
