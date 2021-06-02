package at.matthias.tourplanner.viewmodels;
import at.matthias.tourplanner.BL.Tourhandler;
import at.matthias.tourplanner.models.TourItem;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;

public class TourOverviewViewmodel implements SearchObserver {
    @Getter @Setter public TourItem currentTour;
    @Getter ObservableList<TourItem> tourList;
    private final List<TourObserver> tourObservers;
    private final List<SearchObserver> searchObservers;
    Logger logger = Logger.getLogger(TourOverviewViewmodel.class);

    public TourOverviewViewmodel() {
        tourList = FXCollections.observableArrayList();
        tourObservers = FXCollections.observableArrayList();
        searchObservers = FXCollections.observableArrayList();
        logger.info("initializing TourOverviewViewmodel");
    }

    public void filterList(String search) {

        Tourhandler th = new Tourhandler();
        if (search == null) {
            logger.info("clearing search");
            tourList.clear();
            tourList.addAll(th.get());
        } else {
            logger.info("new Search");
            tourList.clear();
            tourList.addAll(th.search(search));
        }
    }

    public void updateList() {
        logger.info("updating Tourlist");
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
            logger.info("removing Tour");
            th.remove(currentTour);
        }
        selectTour(null);
        updateList();
    }

    public void addObserver(TourObserver o) {
        logger.info("adding TourObserver");
        tourObservers.add(o);
    }
    public void removeObserver(TourObserver o) {
        logger.info("removing TourObserver");
        tourObservers.remove(o);
    }
    public void addSearchObserver(SearchObserver o) {
        logger.info("adding SearchObserver");
        searchObservers.add(o);
    }
    public void removeSearchObserver(SearchObserver o) {
        logger.info("removing SearchObserver");
        searchObservers.remove(o);
    }

    public void newSearch(String search) {
        for (SearchObserver o : searchObservers) {
            o.newSearch(search);
        }
    }
}
