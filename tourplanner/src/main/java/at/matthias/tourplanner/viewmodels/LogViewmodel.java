package at.matthias.tourplanner.viewmodels;

import at.matthias.tourplanner.BL.Loghandler;
import at.matthias.tourplanner.models.LogItem;
import lombok.Getter;
import lombok.Setter;

public class LogViewmodel {
  @Getter @Setter private int tourId;
  @Getter @Setter private int logId;
  @Getter @Setter private LogItem toEdit;

  public void add(LogItem log) {
    Loghandler lh = new Loghandler();
    lh.add(tourId, log);
  }

  public void edit(LogItem log) {
    Loghandler lh = new Loghandler();
    lh.edit(log);
  }

  public void updateToEdit(int id) {
    Loghandler lh = new Loghandler();
    logId = id;
    toEdit = lh.get(id);
  }
}
