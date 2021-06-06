package at.matthias.tourplanner;
import static org.junit.Assert.*;

import at.matthias.tourplanner.BL.Loghandler;
import at.matthias.tourplanner.BL.Tourhandler;
import at.matthias.tourplanner.models.LogItem;
import at.matthias.tourplanner.models.TourItem;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoghandlerTest {
  public static int tourId1;
  public static int tourId2;
  public static int logId1;
  public static int logId2;
  @Test
  public void AAddLogTest1() {
    Tourhandler th = new Tourhandler();
    Loghandler lh = new Loghandler();
    th.add("ltest1", "Feldkirch", "Bregenz", "Vorarlberg");
    List<TourItem> tl = th.get()
                            .stream()
                            .filter(x -> x.getName().toLowerCase().contains("ltest1".toLowerCase()))
                            .collect(Collectors.toList());
    int id = 0;
    for (TourItem t : tl) {
      if (t.getName().equals("ltest1")) {
        id = t.getId();
        tourId1 = id;
        LocalDate ld = LocalDate.of(2020, Month.JANUARY, 8);
        LogItem l = new LogItem(ld, 60, 90, 7, 1, 27, "SUNNY", "Testing1");
        lh.add(t.getId(), l);
        List<LogItem> ll = lh.get(t.getId());
        for (LogItem li : ll) {
          assertEquals("Testing1", li.getActivity());
          logId1 = li.getId();
          break;
        }
        break;
      }
    }
  }
  @Test
  public void BAddLogTest2() {
    Tourhandler th = new Tourhandler();
    Loghandler lh = new Loghandler();
    th.add("ltest2", "Feldkirch", "Bregenz", "Vorarlberg");
    List<TourItem> tl = th.get()
                            .stream()
                            .filter(x -> x.getName().toLowerCase().contains("ltest2".toLowerCase()))
                            .collect(Collectors.toList());
    int id = 0;
    for (TourItem t : tl) {
      if (t.getName().equals("test2")) {
        id = t.getId();
        tourId2 = id;
        LocalDate ld = LocalDate.of(2020, Month.JANUARY, 8);
        LogItem l = new LogItem(ld, 60, 90, 7, 1, 27, "SUNNY", "Testing2");
        lh.add(t.getId(), l);
        List<LogItem> ll = lh.get(t.getId());
        for (LogItem li : ll) {
          assertEquals("Testing2", li.getActivity());
          logId2 = li.getId();
          break;
        }
      }
    }
  }

  @Test
  public void CEditTest() {
    Loghandler lh = new Loghandler();
    List<LogItem> ll = lh.get(tourId1);
    LogItem l = null;
    for (LogItem li : ll) {
      if (li.getActivity().equals("Testing1")) {
        l = li;
      }
      break;
    }
    if (l != null) {
      l.setActivity("Testing3");
      lh.edit(l);
    }
    ll = lh.get(tourId1);
    for (LogItem li : ll) {
      assertEquals("Testing3", li.getActivity());
      break;
    }
  }
  @Test
  public void DDeleteTest() {
    Loghandler lh = new Loghandler();
    Tourhandler th = new Tourhandler();
    lh.remove(logId1);
    lh.remove(logId2);

    List<LogItem> ll1 = lh.get(tourId1);
    List<LogItem> ll2 = lh.get(tourId2);
    assertTrue(ll1.isEmpty() && ll2.isEmpty());

    List<TourItem> tl = th.get()
                            .stream()
                            .filter(x -> x.getName().toLowerCase().contains("ltest".toLowerCase()))
                            .collect(Collectors.toList());
    for (TourItem t : tl) {
      th.remove(t);
    }
  }
}