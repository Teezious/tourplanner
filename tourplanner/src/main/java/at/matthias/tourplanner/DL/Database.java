package at.matthias.tourplanner.DL;

import at.matthias.tourplanner.models.TourItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Database implements DataAccess {
    public Database() {
        // TODO read from conf establish DB Conn
    }

    @Override
    public List<TourItem> GetItems() {
        return new ArrayList<>();
    }
}
