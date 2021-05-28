package at.matthias.tourplanner.viewmodels;

import at.matthias.tourplanner.BL.Loghandler;
import at.matthias.tourplanner.models.LogItem;
import lombok.Getter;
import lombok.Setter;

public class AddLogViewmodel {
    @Getter @Setter private int id;
    public void add(LogItem log) {
        Loghandler lh = new Loghandler();
        lh.add(id, log);
    }
}
