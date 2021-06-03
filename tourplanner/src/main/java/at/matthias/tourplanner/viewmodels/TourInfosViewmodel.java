package at.matthias.tourplanner.viewmodels;

import at.matthias.tourplanner.BL.Loghandler;
import at.matthias.tourplanner.models.LogItem;
import at.matthias.tourplanner.models.TourItem;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;

public class TourInfosViewmodel implements TourObserver {
    @Getter @Setter public LogItem currentLog;
    @Getter @Setter public TourItem currentTour;
    @Getter ObservableList<LogItem> logList;
    private final List<TourObserver> tourObservers;
    private final List<SearchObserver> observers;
    private static final Logger logger = Logger.getLogger(TourInfosViewmodel.class);

    public TourInfosViewmodel() {
        logger.info("initializing TourInfosViewModel");
        logList = FXCollections.observableArrayList();
        tourObservers = FXCollections.observableArrayList();
        observers = FXCollections.observableArrayList();
    }
    @Override
    public void updateCurrentTour(TourItem tour) {
        logger.info("updating current Tour");
        currentTour = tour;
        for (TourObserver o : tourObservers) {
            o.updateCurrentTour(tour);
        }
    }

    public void updateLogList() {
        logger.info("updating current LogList");
        Loghandler lh = new Loghandler();
        if (currentTour != null) {
            List<LogItem> currentLogList = lh.get(currentTour.getId());
            if (currentLogList != null) {
                if (currentLogList.isEmpty()) {
                    logList.clear();
                } else {
                    logList.clear();
                    logList.addAll(currentLogList);
                }
            }
        } else {
            logList.clear();
        }
    }

    public void removeLog() {
        logger.info("removing log");
        Loghandler lh = new Loghandler();
        if (currentLog != null) {
            lh.remove(currentLog.getId());
            currentLog = null;
        } else {
            logger.info("Removing Log failed! No Log selected");
        }
    }

    public void addObserver(TourObserver o) {
        logger.info("Adding TourObserver");
        tourObservers.add(o);
    }
    public void removeObserver(TourObserver o) {
        logger.info("Removing TourObserver");
        tourObservers.remove(o);
    }

    public void addSearchObserver(SearchObserver o) {
        logger.info("Adding SearchObserver");
        observers.add(o);
    }

    public void removeSerchObserver(SearchObserver o) {
        logger.info("Removing SearchObserver");
        observers.remove(o);
    }

    public void newSearch(String search) {
        logger.info("received new Search");
        for (SearchObserver o : observers) {
            o.newSearch(search);
        }
    }
}
