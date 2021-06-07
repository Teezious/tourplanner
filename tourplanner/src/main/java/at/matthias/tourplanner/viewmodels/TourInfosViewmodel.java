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

  @Override // updates current tour notifies observers
  public void updateCurrentTour(TourItem tour) {
    logger.info("updating current Tour");
    currentTour = tour;
    for (TourObserver o : tourObservers) {
      o.updateCurrentTour(tour);
    }
  }

  // update log list
  public void updateLogList() {
    logger.info("updating current LogList");
    Loghandler lh = new Loghandler();
    if (currentTour != null) {
      List<LogItem> currentLogList = lh.getLogByTour(currentTour.getId());
      if (currentLogList != null) {
        if (currentLogList.isEmpty()) {
          logger.info("loglist is empty");
          logList.clear();
        } else {
          logList.clear();
          logList.addAll(currentLogList);
        }
      }
    } else {
      logger.info("currentTour is null");
      logList.clear();
    }
  }
  // pass call to remove a certain log
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
  // adds tour observer
  public void addObserver(TourObserver o) {
    logger.info("Adding TourObserver");
    tourObservers.add(o);
  }
  // remvoves tour observer
  public void removeObserver(TourObserver o) {
    logger.info("Removing TourObserver");
    tourObservers.remove(o);
  }
  // adds search observer
  public void addSearchObserver(SearchObserver o) {
    logger.info("Adding SearchObserver");
    observers.add(o);
  }
  // remvoves search observer
  public void removeSerchObserver(SearchObserver o) {
    logger.info("Removing SearchObserver");
    observers.remove(o);
  }

  // new serach has been retrieved notify observers
  public void newSearch(String search) {
    logger.info("received new Search");
    for (SearchObserver o : observers) {
      o.newSearch(search);
    }
  }
}
