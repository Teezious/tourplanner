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
  private static final Logger logger = Logger.getLogger(TourOverviewViewmodel.class);

  public TourOverviewViewmodel() {
    tourList = FXCollections.observableArrayList();
    tourObservers = FXCollections.observableArrayList();
    searchObservers = FXCollections.observableArrayList();
    logger.info("initializing TourOverviewViewmodel");
  }
  // passes make favorite call
  public void makeFavorite() {
    logger.info("New favorite Tour");
    if (currentTour != null) {
      Tourhandler th = new Tourhandler();
      th.makeFavorite(currentTour.getId());
    } else {
      logger.info("Select Tour to make favorite");
    }
  }

  // filter list depending on new search
  public void filterList(String search) {
    Tourhandler th = new Tourhandler();
    List<TourItem> currenTourlist;
    if (search == null) {
      currenTourlist = th.get();
      logger.info("clearing search");

    } else {
      currenTourlist = th.search(search);
      logger.info("new Search");
    }
    if (currenTourlist != null) {
      if (currenTourlist.isEmpty()) {
        tourList.clear();
      } else {
        tourList.clear();
        tourList.addAll(currenTourlist);
      }
    }
  }

  // updates list with new tour data
  public void updateList() {
    logger.info("updating Tourlist");
    Tourhandler th = new Tourhandler();

    List<TourItem> currenTourlist = th.get();
    if (currenTourlist != null) {
      if (currenTourlist.isEmpty()) {
        tourList.clear();
      } else {
        tourList.clear();
        tourList.addAll(currenTourlist);
      }
    }
  }
  // new current tour has been selected
  public void selectTour(TourItem tour) {
    currentTour = tour;
    for (TourObserver ob : tourObservers) {
      ob.updateCurrentTour(tour); // notifiy observers
    }
  }
  // pass remove tour call
  public void removeTour() {
    Tourhandler th = new Tourhandler();
    if (currentTour != null) {
      logger.info("removing Tour");
      th.remove(currentTour);
    }
    selectTour(null);
    updateList();
  }
  // adds tour observer
  public void addObserver(TourObserver o) {
    logger.info("adding TourObserver");
    tourObservers.add(o);
  }
  // removes tour observer
  public void removeObserver(TourObserver o) {
    logger.info("removing TourObserver");
    tourObservers.remove(o);
  }
  // adds searc observer
  public void addSearchObserver(SearchObserver o) {
    logger.info("adding SearchObserver");
    searchObservers.add(o);
  }
  // removes searc observer
  public void removeSearchObserver(SearchObserver o) {
    logger.info("removing SearchObserver");
    searchObservers.remove(o);
  }

  public void newSearch(String search) {
    for (SearchObserver o : searchObservers) {
      o.newSearch(search); // notify observers
    }
  }
}
