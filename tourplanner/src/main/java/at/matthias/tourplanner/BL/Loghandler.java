package at.matthias.tourplanner.BL;
import at.matthias.tourplanner.DL.Database;
import at.matthias.tourplanner.models.LogItem;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Loghandler {
    private static final String ADDLOG =
        "Insert into logs (fk_tour_id, date, time, distance, rating, avg_speed, breaks, degrees, weather, activity) values(?,?,?,?,?,?,?,?,?,?)";
    private static final String REMOVELOG = "delete from logs where id = ?";
    private static final String EDITLOG =
        "update logs set date  = ?, time  = ?, distance  = ?, rating  = ?, avg_speed  = ?, breaks  = ?, degrees = ?, weather = ?, activity = ? where id = ?";
    private static final String GETLOGBYID =
        "select date, time, distance, rating, avg_speed, breaks, degrees, weather, activity, id from logs where fk_tour_id = ?";
    private Database db;
    public Loghandler() {
        db = new Database();
    }

    public void add(int tourId, LogItem log) {
        System.out.println(tourId);
        try (PreparedStatement ps = db.getConn().prepareStatement(ADDLOG)) {
            ps.setInt(1, tourId);
            ps.setDate(2, java.sql.Date.valueOf(log.getDate()));
            ps.setInt(3, log.getTime());
            ps.setInt(4, log.getDistance());
            ps.setInt(5, log.getRating());
            ps.setFloat(6, log.getAvgSpd());
            ps.setInt(7, log.getBreaks());
            ps.setInt(8, log.getDegrees());
            ps.setString(9, log.getWeather().toString());
            ps.setString(10, log.getActivity().toString());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void remove(int id) {
        try (PreparedStatement ps = db.getConn().prepareStatement(REMOVELOG)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void edit(LogItem log) {
        try (PreparedStatement ps = db.getConn().prepareStatement(EDITLOG)) {
            ps.setDate(1, java.sql.Date.valueOf(log.getDate()));
            ps.setInt(2, log.getTime());
            ps.setInt(3, log.getDistance());
            ps.setInt(4, log.getRating());
            ps.setFloat(5, log.getAvgSpd());
            ps.setInt(6, log.getBreaks());
            ps.setInt(7, log.getDegrees());
            ps.setString(8, log.getWeather().toString());
            ps.setString(9, log.getActivity().toString());
            ps.setInt(10, log.getLogId());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<LogItem> get(int id) {
        ArrayList<LogItem> logs = new ArrayList<>();
        ObservableList<LogItem> obsrvlog = FXCollections.observableArrayList();
        try (PreparedStatement ps = db.getConn().prepareStatement(GETLOGBYID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LogItem log = new LogItem(rs.getDate(1).toLocalDate(), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getFloat(5),
                                          rs.getInt(6), rs.getInt(7), rs.getString(8), rs.getString(9), rs.getInt(10));
                logs.add(log);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        obsrvlog.addAll(logs);
        return obsrvlog;
    }
}
