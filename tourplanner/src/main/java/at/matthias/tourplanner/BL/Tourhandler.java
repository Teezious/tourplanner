package at.matthias.tourplanner.BL;

import at.matthias.tourplanner.DL.FileHandler;
import at.matthias.tourplanner.DL.TourHelper;
import at.matthias.tourplanner.DL.XMLReader;
import at.matthias.tourplanner.models.RouteItem;
import at.matthias.tourplanner.models.TourItem;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;

public class Tourhandler {
  private final TourHelper tourHelper = new TourHelper();
  private static final Logger logger = Logger.getLogger(Tourhandler.class);

  public void makeFavorite(int tourId) {
    tourHelper.makeFavorite(tourId);
  }

  public void add(String name, String start, String end, String description) {
    Maphandler maphandler = new Maphandler();
    String imgId = UUID.randomUUID().toString();
    XMLReader reader = new XMLReader();
    String absImgPath = reader.getPath("imageAbsolute") + imgId + ".jpg";
    Float distance = maphandler.requestHandler(start, end, absImgPath).getDistance();
    tourHelper.add(name, start, end, description, distance, imgId, false);
  }

  public void remove(TourItem toBeRemoved) {
    XMLReader reader = new XMLReader();
    String imgPath = reader.getPath("imageAbsolute") + toBeRemoved.getImage() + ".jpg";
    FileHandler.remove(imgPath);
    tourHelper.remove(toBeRemoved);
  }

  public void edit(int id, String name, String start, String end, String description) {
    TourItem te = get(id);
    if (!te.getStart().equals(start) || !te.getEnd().equals(end)) {
      XMLReader reader = new XMLReader();
      String imgPath = reader.getPath("imageAbsolute") + te.getImage() + ".jpg";
      FileHandler.remove(imgPath);
      Maphandler maphandler = new Maphandler();
      String imgId = UUID.randomUUID().toString();
      String newImgPath = reader.getPath("imageAbsolute") + imgId + ".jpg";
      Float distance = maphandler.requestHandler(start, end, newImgPath).getDistance();
      tourHelper.edit(id, name, start, end, description, distance, imgId);
    } else {
      tourHelper.edit(id, name, start, end, description, te.getDistance(), te.getImage());
    }
  }

  public List<TourItem> get() {
    ArrayList<TourItem> items = new ArrayList<>();
    ResultSet rs = tourHelper.get();
    if (rs != null) {
      try {
        while (rs.next()) {
          TourItem t = new TourItem(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
              rs.getString(5), rs.getFloat(6), rs.getString(7), rs.getBoolean(8));
          items.add(t);
        }

        return items;
      } catch (SQLException e) {
        logger.error("Error reading TourItems Resultset!" + e);
      }
    }

    return Collections.emptyList();
  }

  public TourItem get(int id) {
    TourItem tour = null;
    ResultSet rs = tourHelper.get(id);
    if (rs != null) {
      try {
        while (rs.next()) {
          tour = new TourItem(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
              rs.getString(5), rs.getFloat(6), rs.getString(7), rs.getBoolean(8));
        }
      } catch (SQLException e) {
        logger.error("Error reading TourItem Resultset!" + e);
      }
    }
    return tour;
  }

  public List<TourItem> search(String name) {
    List<TourItem> tours = get();
    if (name != null) {
      return tours.stream()
          .filter(x -> x.getName().toLowerCase().contains(name.toLowerCase()))
          .collect(Collectors.toList());
    }
    return Collections.emptyList();
  }
}
