package at.matthias.tourplanner.DL;

import at.matthias.tourplanner.models.TourItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;

public class TourHelper extends Database {
    private static final String CREATETOUR =
        "Insert into tours (name, startpoint, endpoint, description, distance, image) values(?,?,?,?,?,?)";
    private static final String REMOVETOUR = "delete from tours where id = ?";
    private static final String EDITTOUR =
        "update tours set name  = ?, startpoint  = ?, endpoint  = ?, description  = ?, distance  = ?, image  = ?  where id = ?";
    private static final String GETTOURS = "select id, name, startpoint, endpoint, description, distance, image from tours";
    private static final String GETTOURBYID =
        "select id, name, startpoint, endpoint, description, distance, image from tours where id = ?";

    private static final Logger logger = Logger.getLogger(TourHelper.class);

    public void add(String name, String start, String end, String description, Float distance, String absImgPath) {
        Connection conn = getConn();
        if (conn != null) {
            try (PreparedStatement ps = getConn().prepareStatement(CREATETOUR)) {
                ps.setString(1, name);
                ps.setString(2, start);
                ps.setString(3, end);
                ps.setString(4, description);
                ps.setFloat(5, distance);
                ps.setString(6, absImgPath);
                if (ps.executeUpdate() != -1) {
                    logger.info("Successfully added Tour");
                } else {
                    logger.error("Error adding Tour in Database!");
                }

            } catch (Exception e) {
                logger.error("Error adding Tour!" + e);
            }
        } else {
            logger.error("Error adding Tour! Connection is null");
        }
    }
    public void remove(TourItem toBeRemoved) {
        Connection conn = getConn();
        if (conn != null) {
            try (PreparedStatement ps = getConn().prepareStatement(REMOVETOUR)) {
                ps.setInt(1, toBeRemoved.getId());
                if (ps.executeUpdate() != -1) {
                    logger.info("Successfully removed Tour from Database");
                } else {
                    logger.error("Error removing Tour from Database!");
                }
            } catch (Exception e) {
                logger.error("Error removing TourItem from Database!" + e);
            }
        } else {
            logger.error("Error removing TourItem! Connection is null");
        }
    }

    public void edit(int id, String name, String start, String end, String description, Float distance, String absImgPath) {
        Connection conn = getConn();
        if (conn != null) {
            try (PreparedStatement ps = getConn().prepareStatement(EDITTOUR)) {
                ps.setString(1, name);
                ps.setString(2, start);
                ps.setString(3, end);
                ps.setString(4, description);
                ps.setFloat(5, distance);
                ps.setString(6, absImgPath);
                ps.setInt(7, id);
                if (ps.executeUpdate() != -1) {
                    logger.info("Successfully edited Tour");
                } else {
                    logger.error("Error editing Tour in Database!");
                }
            } catch (Exception e) {
                logger.error("Error editing Tour!" + e);
            }
        } else {
            logger.error("Error editing Tour! Connection is null");
        }
    }

    public ResultSet get() {
        Connection conn = getConn();
        if (conn != null) {
            try (PreparedStatement ps = conn.prepareStatement(GETTOURS)) {
                ResultSet rs = ps.executeQuery();
                if (rs != null) {
                    logger.info("Successfully got Tours from Database");
                    return rs;
                } else {
                    logger.error("Result is null");
                    return null;
                }
            } catch (Exception e) {
                logger.error("Error getting Tours!" + e);
            }
            return null;
        } else {
            logger.error("Connection is null");
            return null;
        }
    }

    public ResultSet get(int id) {
        Connection conn = getConn();
        if (conn != null) {

        } else {
            logger.error("Connection is null");
            return null;
        }
        try (PreparedStatement ps = getConn().prepareStatement(GETTOURBYID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                logger.info("Successfully got Tour from Database");
                return rs;
            } else {
                logger.error("Result is null");
                return null;
            }

        } catch (Exception e) {
            logger.error("Error getting Tour!" + e);
        }
        return null;
    }
}
