package at.matthias.tourplanner;
import static org.junit.Assert.*;

import at.matthias.tourplanner.BL.Tourhandler;
import at.matthias.tourplanner.models.TourItem;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TourhandlerTest {
  private static final Logger logger = Logger.getLogger(LoghandlerTest.class);
  public static String imgPath1 = null;
  public static String imgPath2 = null;
  @Test
  public void AAddTourTest1() {
    Tourhandler th = new Tourhandler();
    th.add("test1", "Feldkirch", "Bregenz", "Vorarlberg");
    List<TourItem> tl = th.get();
    assertTrue(tl.stream().filter(o -> o.getName().equals("test1")).findFirst().isPresent());
  }

  @Test
  public void BAddTourTest2() {
    Tourhandler th = new Tourhandler();
    th.add("test2", "Barcelona", "Madrid", "Spanien");
    List<TourItem> tl = th.get();
    assertTrue(tl.stream().filter(o -> o.getName().equals("test2")).findFirst().isPresent());
  }

  @Test
  public void CImagesCreated1() {
    Tourhandler th = new Tourhandler();
    TourItem t1 = null;
    List<TourItem> tl = th.get();

    for (TourItem t : tl) {
      if (t.getName().equals("test1")) {
        t1 = t;
        break;
      }
    }
    if (t1 != null) {
      imgPath1 =
          "/home/matthias/Nextcloud/FHT/SS21/SWE2/tourplanner/tourplanner/src/main/resources/img/"
          + t1.getImage() + ".jpg";
      assertTrue(Files.exists(Paths.get(imgPath1)));
    } else {
      assertTrue(false);
    }
  }

  @Test
  public void DImagesCreated2() {
    Tourhandler th = new Tourhandler();
    TourItem t2 = null;
    List<TourItem> tl = th.get();
    for (TourItem t : tl) {
      if (t.getName().equals("test2")) {
        t2 = t;
        break;
      }
    }
    if (t2 != null) {
      imgPath2 =
          "/home/matthias/Nextcloud/FHT/SS21/SWE2/tourplanner/tourplanner/src/main/resources/img/"
          + t2.getImage() + ".jpg";
      assertTrue(Files.exists(Paths.get(imgPath2)));
    } else {
      assertTrue(false);
    }
  }

  @Test
  public void EEditTourTest() {
    Tourhandler th = new Tourhandler();
    List<TourItem> tl = th.get();
    int id = 0;
    for (TourItem t : tl) {
      if (t.getName().equals("test2")) {
        id = t.getId();
        break;
      }
    }
    th.edit(id, "test2", "Barcelona", "Valencia", "Spanien");
    assertEquals("Valencia", th.get(id).getEnd());
  }
  @Test
  public void FOldImageDeleted() {
    if (imgPath2 != null) {
      assertFalse(Files.exists(Paths.get(imgPath2)));
    } else {
      logger.warn("Foldimages: Test Failure wrong Path");
      assertTrue(false);
    }
  }

  @Test
  public void GNewImageExists() {
    Tourhandler th = new Tourhandler();
    List<TourItem> tl = th.get();
    for (TourItem t : tl) {
      if (t.getName().equals("test2")) {
        String NewImgPath2 =
            "/home/matthias/Nextcloud/FHT/SS21/SWE2/tourplanner/tourplanner/src/main/resources/img/"
            + t.getImage() + ".jpg";

        if (imgPath2 != null && !imgPath2.equals("")) {
          assertTrue(Files.exists(Paths.get(NewImgPath2)) && !NewImgPath2.equals(imgPath2));
        } else {
          logger.warn("imgPath2 Error: " + imgPath2);
          assertTrue(false);
        }
        imgPath2 = NewImgPath2;
      }
    }
  }

  @Test
  public void HGetToursTest() {
    Tourhandler th = new Tourhandler();
    List<TourItem> tl = th.get();
    assertTrue(!tl.isEmpty());
  }

  @Test
  public void ISearchTest1() {
    Tourhandler th = new Tourhandler();
    List<TourItem> t = th.search("test2");
    assertEquals(1, t.size());
  }
  @Test
  public void JSearchTest2() {
    Tourhandler th = new Tourhandler();
    List<TourItem> t = th.search("test");
    assertEquals(2, t.size());
  }
  @Test
  public void KMakeFavorite() {
    Tourhandler th = new Tourhandler();
    List<TourItem> t = th.search("test1");
    int id = 0;
    for (TourItem tit : t) {
      id = tit.getId();
    }
    th.makeFavorite(id);
    TourItem tf = th.get(id);
    assertTrue(tf.getFavorite());
  }

  @Test
  public void LRemoveToursTest() {
    Tourhandler th = new Tourhandler();
    List<TourItem> t = th.search("test");
    for (TourItem tit : t) {
      th.remove(tit);
    }
    t = th.search("test");
    assertTrue(t.isEmpty());
  }
  @Test
  public void MImagesRemovedTest() {
    if (imgPath2 != null && imgPath1 != null) {
      assertFalse(Files.exists(Paths.get(imgPath2)));
      assertFalse(Files.exists(Paths.get(imgPath1)));
    } else {
      logger.warn("Mimages: Test Failure wrong Path");
      logger.warn("imgPath2: " + imgPath2);
      logger.warn("imgPath1: " + imgPath1);
      assertTrue(false);
    }
  }
}
