package at.matthias.tourplanner.BL;
import at.matthias.tourplanner.DL.Database;
import at.matthias.tourplanner.models.LogItem;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Loghandler {
    private static final String CREATELOG =
        "Insert into logs (fk_tour_id, date, time, distance, rating, avg_speed, breaks, degrees, weather) values(?,?,?,?,?,?,?,?,?)";
    private static final String REMOVELOG = "delete from logs where id = ?";
    private static final String EDITLOG =
        "update tours set date  = ?, time  = ?, distance  = ?, rating  = ?, avg_speed  = ?, breaks  = ?, degrees = ?, weather = ?,  where id = ?";
    private static final String GETLOGBYID =
        "select id, date, time, distance, rating, avg_speed, breaks, degrees, weather,  where fk_tour_id = ? from logs";
    private Database db;
    public Loghandler() {
        db = new Database();
    }

    public void add() {
        // TODO
    }

    public void remove(int id) {
        try (PreparedStatement ps = db.getConn().prepareStatement(REMOVELOG)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void edit() {
        // TODO
    }

    public List<LogItem> get(int id) {
        ArrayList<LogItem> logs = new ArrayList<>();
        LogItem log = null;
        try (PreparedStatement ps = db.getConn().prepareStatement(GETLOGBYID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                log = new LogItem();
                logs.add(log);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return logs;
    }
}
