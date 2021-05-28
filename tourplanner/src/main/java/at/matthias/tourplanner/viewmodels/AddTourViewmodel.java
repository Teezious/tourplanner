package at.matthias.tourplanner.viewmodels;

import at.matthias.tourplanner.BL.Tourhandler;

public class AddTourViewmodel {
    public void add(String name, String start, String end, String description) {
        Tourhandler th = new Tourhandler();
        th.add(name, start, end, description);
    }
}
