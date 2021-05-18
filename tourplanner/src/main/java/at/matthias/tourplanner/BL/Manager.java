package at.matthias.tourplanner.BL;

import at.matthias.tourplanner.models.TourItem;
import java.util.List;

public interface Manager {
    public List<TourItem> GetItems();
    public List<TourItem> Search(String name);
}
