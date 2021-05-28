package at.matthias.tourplanner.viewmodels;
import at.matthias.tourplanner.BL.Tourhandler;
import at.matthias.tourplanner.models.TourItem;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

public class TourOverviewViewmodel implements SearchObserver {
    @Getter @Setter public TourItem currentTour;
    @Getter ObservableList<TourItem> tourList;
    private final List<TourObserver> tourObservers;
    private final List<SearchObserver> searchObservers;

    public TourOverviewViewmodel() {
        tourList = FXCollections.observableArrayList();
        tourObservers = FXCollections.observableArrayList();
        searchObservers = FXCollections.observableArrayList();
    }

    public void filterList(String search) {

        Tourhandler th = new Tourhandler();
        if (search == null) {
            tourList.clear();
            tourList.addAll(th.get());
        } else {
            tourList.clear();
            tourList.addAll(th.search(search));
        }
    }

    public void updateList() {
        Tourhandler th = new Tourhandler();
        tourList.clear();
        tourList.addAll(th.get());
    }

    public void selectTour(TourItem tour) {
        currentTour = tour;
        for (TourObserver ob : tourObservers) {
            ob.updateCurrentTour(tour);
        }
    }
    public void removeTour() {
        Tourhandler th = new Tourhandler();
        if (currentTour != null) {
            th.remove(currentTour);
        }
        selectTour(null);
        updateList();
    }

    public void addObserver(TourObserver o) {
        tourObservers.add(o);
    }
    public void removeObserver(TourObserver o) {
        tourObservers.remove(o);
    }
    public void addSearchObserver(SearchObserver o) {
        searchObservers.add(o);
    }
    public void removeSearchObserver(SearchObserver o) {
        searchObservers.remove(o);
    }

    public void newSearch(String search) {
        for (SearchObserver o : searchObservers) {
            o.newSearch(search);
        }
    }
}
