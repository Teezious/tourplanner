package at.matthias.tourplanner.viewmodels;

import at.matthias.tourplanner.BL.Loghandler;
import at.matthias.tourplanner.models.LogItem;
import at.matthias.tourplanner.models.TourItem;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

public class TourInfosViewmodel implements TourObserver {
    @Getter @Setter public LogItem currentLog;
    @Getter @Setter public TourItem currentTour;
    private final List<TourObserver> tourObservers;
    private final List<SearchObserver> observers;
    private ObservableList<LogItem> logList;

    public TourInfosViewmodel() {
        tourObservers = FXCollections.observableArrayList();
        observers = FXCollections.observableArrayList();
    }
    @Override
    public void updateCurrentTour(TourItem tour) {
        currentTour = tour;
        for (TourObserver o : tourObservers) {
            o.updateCurrentTour(tour);
        }
    }

    public ObservableList<LogItem> getLogList() {
        Loghandler lh = new Loghandler();
        return lh.get(currentTour.getId());
    }

    public void removeLog() {
        Loghandler lh = new Loghandler();
        if (currentLog != null) {
            lh.remove(currentLog.getId());
            currentLog = null;
        } else {
            System.out.println(" no log selected"); // TODO Show error
        }
    }

    public void addObserver(TourObserver o) {
        tourObservers.add(o);
    }
    public void removeObserver(TourObserver o) {
        tourObservers.remove(o);
    }

    public void addSearchObserver(SearchObserver o) {
        observers.add(o);
    }

    public void removeSerchObserver(SearchObserver o) {

        observers.remove(o);
    }

    public void newSearch(String search) {
        for (SearchObserver o : observers) {
            o.newSearch(search);
        }
    }
}
