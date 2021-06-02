package at.matthias.tourplanner.viewmodels;

import at.matthias.tourplanner.BL.Tourhandler;
import lombok.Getter;
import lombok.Setter;

public class EditTourViewmodel {
    @Getter @Setter private int id;

    public void saveEdit(String name, String start, String end, String description) {
        Tourhandler th = new Tourhandler();
        th.edit(id, name, start, end, description);
    }
}
