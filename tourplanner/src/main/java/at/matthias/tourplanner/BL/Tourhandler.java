package at.matthias.tourplanner.BL;

import at.matthias.tourplanner.DL.FileHandler;
import at.matthias.tourplanner.DL.TourHelper;
import at.matthias.tourplanner.models.TourItem;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.*;
import org.apache.log4j.Logger;

public class Tourhandler {
    private static final String IMGPATH = "img/";
    private static final String IMGPATHABSOLUTE =
        "/home/matthias/Nextcloud/FHT/SS21/SWE2/tourplanner/tourplanner/src/main/resources/img"; // TODO find solution for this
    private TourHelper tourHelper;

    public Tourhandler() {
        tourHelper = new TourHelper();
    }

    public void add(String name, String start, String end, String description) {
        Maphandler maphandler = new Maphandler();
        String imgId = UUID.randomUUID().toString();
        String absImgPath = IMGPATHABSOLUTE + "/" + imgId + ".jpg";
        Float distance = maphandler.requestHandler(start, end, absImgPath).getDistance();
        tourHelper.add(name, start, end, description, distance, absImgPath);
    }

    public void remove(TourItem toBeRemoved) {
        tourHelper.remove(toBeRemoved);
        FileHandler.remove(toBeRemoved.getImage());
    }

    public void edit(int id, String name, String start, String end, String description) {
        Maphandler maphandler = new Maphandler();
        String imgId = UUID.randomUUID().toString();
        String absImgPath = IMGPATHABSOLUTE + "/" + imgId + ".jpg";
        Float distance = maphandler.requestHandler(start, end, absImgPath).getDistance();
        tourHelper.edit(id, name, start, end, description, distance, absImgPath);
    }

    public List<TourItem> get() {
        ArrayList<TourItem> items = new ArrayList<>();
        ResultSet rs = tourHelper.get();
        try {
            while (rs.next()) {
                items.add(new TourItem(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                                       rs.getFloat(6), rs.getString(7)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    public TourItem get(int id) {
        TourItem tour = null;
        ResultSet rs = tourHelper.get(id);
        try {
            while (rs.next()) {
                tour = new TourItem(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                                    rs.getFloat(6), rs.getString(7));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tour;
    }

    public List<TourItem> search(String name) {
        ArrayList<TourItem> tours = new ArrayList<>();
        ResultSet rs = tourHelper.get();
        try {
            while (rs.next()) {
                tours.add(new TourItem(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                                       rs.getFloat(6), rs.getString(7)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (name != null) {
            return tours.stream()
                .filter(x -> x.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
        } else {
            return tours;
        }
    }
}
