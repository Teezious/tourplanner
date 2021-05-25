package at.matthias.tourplanner.BL;

import at.matthias.tourplanner.DL.Database;
import at.matthias.tourplanner.models.TourItem;
import at.matthias.tourplanner.views.Controller;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;

public class Tourhandler {
    private static final String CREATETOUR =
        "Insert into tours (name, startpoint, endpoint, description, distance, image) values(?,?,?,?,?,?)";
    private static final String REMOVETOUR = "delete from tours where id = ?";
    private static final String EDITTOUR =
        "update tours set name  = ?, startpoint  = ?, endpoint  = ?, description  = ?, distance  = ?, image  = ?  where id = ?";
    private static final String GETTOURS = "select id, name, startpoint, endpoint, description, distance, image from tours";
    private static final String GETTOURBYID =
        "select id, name, startpoint, endpoint, description, distance, image from tours where id = ?";
    private Database db;
    public Tourhandler() {
        db = new Database();
    }

    public void add(String name, String start, String end, String description) {
        String image = getImage();
        float distance = calculateDistance(start, end);

        try (PreparedStatement ps = db.getConn().prepareStatement(CREATETOUR)) {
            ps.setString(1, name);
            ps.setString(2, start);
            ps.setString(3, end);
            ps.setString(4, description);
            ps.setFloat(5, distance);
            ps.setString(6, image);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void remove(int id) {
        try (PreparedStatement ps = db.getConn().prepareStatement(REMOVETOUR)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void edit(int id, String name, String start, String end, String description) {
        System.out.println("we editin");
        String image = getImage();
        float distance = calculateDistance(start, end); // TODO ?
        try (PreparedStatement ps = db.getConn().prepareStatement(EDITTOUR)) {
            ps.setString(1, name);
            ps.setString(2, start);
            ps.setString(3, end);
            ps.setString(4, description);
            ps.setFloat(5, distance);
            ps.setString(6, image);
            ps.setInt(7, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<TourItem> get() {
        ArrayList<TourItem> items = new ArrayList<>();
        try (PreparedStatement ps = db.getConn().prepareStatement(GETTOURS)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                items.add(new TourItem(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                                       rs.getFloat(6), rs.getString(7)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }

    public TourItem get(int id) {
        TourItem tour = null;
        try (PreparedStatement ps = db.getConn().prepareStatement(GETTOURBYID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tour = new TourItem(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                                    rs.getFloat(6), rs.getString(7));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tour;
    }

    public List<TourItem> search(String name) {
        List<TourItem> tours = get();
        return tours.stream().filter(x -> x.getName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
    }

    private float calculateDistance(String start, String end) {
        // TODO
        return 0.5f;
    }

    private String getImage() {
        // TODO
        return "URL";
    }
}