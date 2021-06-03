package at.matthias.tourplanner.BL;

import at.matthias.tourplanner.DL.LogHelper;
import at.matthias.tourplanner.models.LogItem;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;

public class Loghandler {
    private LogHelper logHelper;
    private static final Logger logger = Logger.getLogger(Loghandler.class);
    public Loghandler() {
        logHelper = new LogHelper();
    }

    public void add(int tourId, LogItem log) {
        logHelper.add(tourId, log);
    }

    public void remove(int id) {
        logHelper.remove(id);
    }

    public void edit(LogItem log) {
        logHelper.edit(log);
    }

    public List<LogItem> get(int id) {
        ArrayList<LogItem> logs = new ArrayList<>();
        ResultSet rs = logHelper.get(id);
        if (rs != null) {
            try {

                while (rs.next()) {
                    logs.add(new LogItem(rs.getDate(1).toLocalDate(), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getFloat(5),
                                         rs.getInt(6), rs.getInt(7), rs.getString(8), rs.getString(9), rs.getInt(10)));
                }
                return logs;
            } catch (SQLException e) {
                logger.error("Error reading Log Resultset!" + e);
            }
        }
        return Collections.emptyList();
    }
}
