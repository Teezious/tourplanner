package at.matthias.tourplanner.BL;

import at.matthias.tourplanner.models.TourItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManagerImpl implements AppManager {
    @Override
    public List<TourItem> GetItems() {
        TourItem[] items = {new TourItem("Test1"), new TourItem("Test2"), new TourItem("Test3"), new TourItem("Test4"),
                            new TourItem("Test5")};
        return new ArrayList<>(Arrays.asList(items));
    }

    @Override
    public List<TourItem> Search(String name) {
        return new ArrayList<>();
    }
}
