package at.matthias.tourplanner.BL;

import at.matthias.tourplanner.DL.LogHelper;
import at.matthias.tourplanner.models.LogItem;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;

public class Loghandler {
    private LogHelper logHelper;
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

    public ObservableList<LogItem> get(int id) {
        ArrayList<LogItem> logs = new ArrayList<>();
        ObservableList<LogItem> obsrvlog = FXCollections.observableArrayList();

        try {
            ResultSet rs = logHelper.get(id);
            while (rs.next()) {
                logs.add(new LogItem(rs.getDate(1).toLocalDate(), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getFloat(5),
                                     rs.getInt(6), rs.getInt(7), rs.getString(8), rs.getString(9), rs.getInt(10)));
            }
        } catch (SQLException s) {
            s.printStackTrace();
        }

        obsrvlog.addAll(logs);
        return obsrvlog;
    }
}
