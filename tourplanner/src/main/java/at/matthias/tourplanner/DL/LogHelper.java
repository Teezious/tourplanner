package at.matthias.tourplanner.DL;

import at.matthias.tourplanner.models.LogItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;

public class LogHelper extends Database {
  private static final String ADDLOG =
      "Insert into logs (fk_tour_id, date, time, distance, rating, avg_speed, breaks, degrees, weather, activity) values(?,?,?,?,?,?,?,?,?,?)";
  private static final String REMOVELOG = "delete from logs where id = ?";
  private static final String EDITLOG =
      "update logs set date  = ?, time  = ?, distance  = ?, rating  = ?, avg_speed  = ?, breaks  = ?, degrees = ?, weather = ?, activity = ? where id = ?";
  private static final String GETLOGBYID =
      "select date, time, distance, rating, avg_speed, breaks, degrees, weather, activity, id from logs where fk_tour_id = ?";
  private static final Logger logger = Logger.getLogger(LogHelper.class);

  public void add(int tourId, LogItem log) {
    Connection conn = getConn();
    if (conn != null) {
      try (PreparedStatement ps = getConn().prepareStatement(ADDLOG)) {
        ps.setInt(1, tourId);
        ps.setDate(2, java.sql.Date.valueOf(log.getDate()));
        ps.setInt(3, log.getTime());
        ps.setInt(4, log.getDistance());
        ps.setInt(5, log.getRating());
        ps.setFloat(6, log.getSpeed());
        ps.setInt(7, log.getBreaks());
        ps.setInt(8, log.getDegrees());
        ps.setString(9, log.getWeather());
        ps.setString(10, log.getActivity());
        if (ps.executeUpdate() != -1) {
          logger.info("Successfully added Log");
        } else {
          logger.error("Error adding Log to Database!");
        }

      } catch (Exception e) {
        logger.error("Error adding Log!" + e);
      }
    } else {
      logger.error("Error adding Log! Connection is null");
    }
  }

  public void remove(int id) {
    Connection conn = getConn();
    if (conn != null) {
      try (PreparedStatement ps = getConn().prepareStatement(REMOVELOG)) {
        ps.setInt(1, id);
        if (ps.executeUpdate() != -1) {
          logger.info("Successfully removed Log");
        } else {
          logger.error("Error removing Log in Database!");
        }
      } catch (Exception e) {
        logger.error("Error removing Log!" + e);
      }
    } else {
      logger.error("Error removing Log! Connection is null");
    }
  }

  public void edit(LogItem log) {
    Connection conn = getConn();
    if (conn != null) {
      try (PreparedStatement ps = getConn().prepareStatement(EDITLOG)) {
        ps.setDate(1, java.sql.Date.valueOf(log.getDate()));
        ps.setInt(2, log.getTime());
        ps.setInt(3, log.getDistance());
        ps.setInt(4, log.getRating());
        ps.setFloat(5, log.getSpeed());
        ps.setInt(6, log.getBreaks());
        ps.setInt(7, log.getDegrees());
        ps.setString(8, log.getWeather());
        ps.setString(9, log.getActivity());
        ps.setInt(10, log.getId());
        if (ps.executeUpdate() != -1) {
          logger.info("Successfully edited Log");
        } else {
          logger.error("Error editing Log in Database!");
        }

      } catch (Exception e) {
        logger.error("Error editing Tour!" + e);
      }
    } else {
      logger.error("Error editing Tour! Connection is null");
    }
  }

  public ResultSet get(int tourId) {
    Connection conn = getConn();
    if (conn != null) {
      try (PreparedStatement ps = getConn().prepareStatement(GETLOGBYID)) {
        ps.setInt(1, tourId);
        logger.info("Getting Logs");
        return ps.executeQuery();

      } catch (Exception e) {
        logger.error("Error getting Logs!" + e);
      }
    } else {
      logger.error("Error getting Logs! Connection is null");
    }

    return null;
  }
}
