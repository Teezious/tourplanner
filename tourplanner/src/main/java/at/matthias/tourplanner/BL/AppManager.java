package at.matthias.tourplanner.BL;

import java.util.List;

import at.matthias.tourplanner.models.TourItem;

public interface AppManager {
    public List<TourItem> GetItems();
    public List<TourItem> Search(String name);

}
