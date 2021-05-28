package at.matthias.tourplanner.BL;

import at.matthias.tourplanner.DL.APIcomm;
import at.matthias.tourplanner.DL.Database;
import at.matthias.tourplanner.DL.TourHelper;
import at.matthias.tourplanner.models.TourItem;
import at.matthias.tourplanner.views.MainController;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.*;

public class Tourhandler {
    private TourHelper tourHelper;

    public Tourhandler() {
        tourHelper = new TourHelper();
    }

    public void add(String name, String start, String end, String description) {
        tourHelper.add(name, start, end, description);
    }

    public void remove(TourItem toBeRemoved) {
        tourHelper.remove(toBeRemoved);
    }

    public void edit(int id, String name, String start, String end, String description) {
        tourHelper.edit(id, name, start, end, description);
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
