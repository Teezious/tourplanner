package at.matthias.tourplanner;

import static org.junit.Assert.assertTrue;

import at.matthias.tourplanner.BL.Reporthandler;
import at.matthias.tourplanner.BL.Tourhandler;
import at.matthias.tourplanner.DL.FileHandler;
import at.matthias.tourplanner.models.TourItem;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.Test;

public class ReportHandlerTest {
  @Test
  public void Summary() {
    String path = "/home/matthias/Desktop";
    String filePath = "/home/matthias/Desktop/rtest1_summary.pdf";
    Tourhandler th = new Tourhandler();
    th.add("rtest1", "Paris", "Marseille", "Frankreich");
    List<TourItem> tl = th.get();
    int id = 0;
    TourItem tour = null;
    for (TourItem t : tl) {
      if (t.getName().equals("rtest1")) {
        id = t.getId();
        tour = t;
      }
    }
    Reporthandler.fileSummary(path, th.get(id));
    assertTrue(Files.exists(Paths.get(filePath)));
    th.remove(tour);
    FileHandler.remove(filePath);
  }

  @Test
  public void Report() {
    String path = "/home/matthias/Desktop";
    String filePath = "/home/matthias/Desktop/rtest2_report.pdf";
    Tourhandler th = new Tourhandler();
    th.add("rtest2", "London", "Edinburgh", "England");
    List<TourItem> tl = th.get();
    TourItem tour = null;
    int id = 0;
    for (TourItem t : tl) {
      if (t.getName().equals("rtest2")) {
        id = t.getId();
        tour = t;
      }
    }
    Reporthandler.fileReport(path, th.get(id));
    assertTrue(Files.exists(Paths.get(filePath)));
    th.remove(tour);
    FileHandler.remove(filePath);
  }
}
