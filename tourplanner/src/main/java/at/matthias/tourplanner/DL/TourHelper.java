package at.matthias.tourplanner.DL;

import at.matthias.tourplanner.BL.Maphandler;
import at.matthias.tourplanner.models.TourItem;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public class TourHelper extends Database {
    private static final String CREATETOUR =
        "Insert into tours (name, startpoint, endpoint, description, distance, image) values(?,?,?,?,?,?)";
    private static final String REMOVETOUR = "delete from tours where id = ?";
    private static final String EDITTOUR =
        "update tours set name  = ?, startpoint  = ?, endpoint  = ?, description  = ?, distance  = ?, image  = ?  where id = ?";
    private static final String GETTOURS = "select id, name, startpoint, endpoint, description, distance, image from tours";
    private static final String GETTOURBYID =
        "select id, name, startpoint, endpoint, description, distance, image from tours where id = ?";
    private static final String IMGPATH = "img/";
    private static final String IMGPATHABSOLUTE =
        "/home/matthias/Nextcloud/FHT/SS21/SWE2/tourplanner/tourplanner/src/main/resources/img"; // TODO find solution for this

    public void add(String name, String start, String end, String description) {
        Maphandler maphandler = new Maphandler();
        String imgId = UUID.randomUUID().toString();
        String absImgPath = IMGPATHABSOLUTE + "/" + imgId + ".jpg";
        try (PreparedStatement ps = getConn().prepareStatement(CREATETOUR)) {
            ps.setString(1, name);
            ps.setString(2, start);
            ps.setString(3, end);
            ps.setString(4, description);
            ps.setFloat(5, maphandler.requestHandler(start, end, absImgPath).getDistance());
            ps.setString(6, absImgPath);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void remove(TourItem toBeRemoved) {
        try {
            if (Files.exists(Paths.get(toBeRemoved.getImage()))) {
                Files.delete(Paths.get(toBeRemoved.getImage()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (PreparedStatement ps = getConn().prepareStatement(REMOVETOUR)) {
            ps.setInt(1, toBeRemoved.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void edit(int id, String name, String start, String end, String description) {
        Maphandler maphandler = new Maphandler();
        String imgId = UUID.randomUUID().toString();
        String absImgPath = IMGPATHABSOLUTE + "/" + imgId + ".jpg";

        try (PreparedStatement ps = getConn().prepareStatement(EDITTOUR)) {
            ps.setString(1, name);
            ps.setString(2, start);
            ps.setString(3, end);
            ps.setString(4, description);
            ps.setFloat(5, maphandler.requestHandler(start, end, absImgPath).getDistance());
            ps.setString(6, absImgPath);
            ps.setInt(7, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet get() {
        try (PreparedStatement ps = getConn().prepareStatement(GETTOURS)) {
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet get(int id) {
        try (PreparedStatement ps = getConn().prepareStatement(GETTOURBYID)) {
            ps.setInt(1, id);
            return ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
