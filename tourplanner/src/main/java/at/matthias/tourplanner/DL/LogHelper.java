package at.matthias.tourplanner.DL;

import at.matthias.tourplanner.models.LogItem;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LogHelper extends Database {
    private static final String ADDLOG =
        "Insert into logs (fk_tour_id, date, time, distance, rating, avg_speed, breaks, degrees, weather, activity) values(?,?,?,?,?,?,?,?,?,?)";
    private static final String REMOVELOG = "delete from logs where id = ?";
    private static final String EDITLOG =
        "update logs set date  = ?, time  = ?, distance  = ?, rating  = ?, avg_speed  = ?, breaks  = ?, degrees = ?, weather = ?, activity = ? where id = ?";
    private static final String GETLOGBYID =
        "select date, time, distance, rating, avg_speed, breaks, degrees, weather, activity, id from logs where fk_tour_id = ?";

    public void add(int tourId, LogItem log) {
        try (PreparedStatement ps = getConn().prepareStatement(ADDLOG)) {
            ps.setInt(1, tourId);
            ps.setDate(2, java.sql.Date.valueOf(log.getDate()));
            ps.setInt(3, log.getTime());
            ps.setInt(4, log.getDistance());
            ps.setInt(5, log.getRating());
            ps.setFloat(6, log.getAvgSpd());
            ps.setInt(7, log.getBreaks());
            ps.setInt(8, log.getDegrees());
            ps.setString(9, log.getWeather());
            ps.setString(10, log.getActivity());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void remove(int id) {
        try (PreparedStatement ps = getConn().prepareStatement(REMOVELOG)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void edit(LogItem log) {
        try (PreparedStatement ps = getConn().prepareStatement(EDITLOG)) {
            ps.setDate(1, java.sql.Date.valueOf(log.getDate()));
            ps.setInt(2, log.getTime());
            ps.setInt(3, log.getDistance());
            ps.setInt(4, log.getRating());
            ps.setFloat(5, log.getAvgSpd());
            ps.setInt(6, log.getBreaks());
            ps.setInt(7, log.getDegrees());
            ps.setString(8, log.getWeather().toString());
            ps.setString(9, log.getActivity().toString());
            ps.setInt(10, log.getId());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet get(int id) {
        try (PreparedStatement ps = getConn().prepareStatement(GETLOGBYID)) {
            ps.setInt(1, id);
            return ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
