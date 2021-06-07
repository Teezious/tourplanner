package at.matthias.tourplanner.viewmodels;

import at.matthias.tourplanner.BL.Tourhandler;
import lombok.Getter;
import lombok.Setter;

public class TourViewmodel {
  @Getter @Setter private int id;

  // pass edit tour call
  public void edit(String name, String start, String end, String description) {
    Tourhandler th = new Tourhandler();
    th.edit(id, name, start, end, description);
  }
  // pass add tour call
  public void add(String name, String start, String end, String description) {
    Tourhandler th = new Tourhandler();
    th.add(name, start, end, description);
  }
}
