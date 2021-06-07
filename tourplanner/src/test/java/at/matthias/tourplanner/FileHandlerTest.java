package at.matthias.tourplanner;
import static org.junit.Assert.assertTrue;

import at.matthias.tourplanner.BL.Tourhandler;
import at.matthias.tourplanner.DL.FileHandler;
import at.matthias.tourplanner.models.TourItem;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FileHandlerTest {
  @Test
  public void AExport() {
    String path = "/home/matthias/Desktop";
    String filePath = "/home/matthias/Desktop/ftest1.xml";
    TourItem t = new TourItem("ftest1", "Amsterdam", "Den Haag", "Niederlande");

    FileHandler.exportTour(path, t);
    assertTrue(Files.exists(Paths.get(filePath)));
  }

  @Test
  public void BImport() {
    String filePath = "/home/matthias/Desktop/ftest1.xml";
    FileHandler.importTour(filePath);
    Tourhandler th = new Tourhandler();

    List<TourItem> tl = th.get();
    for (TourItem t : tl) {
      if (t.getName().equals("ftest1")) {
        assertTrue(t.getDescription().equals("Niederlande"));
        th.remove(t);
        FileHandler.remove(filePath);
        break;
      }
    }
  }
}
