package at.matthias.tourplanner.views;

import at.matthias.tourplanner.DL.XMLReader;
import at.matthias.tourplanner.models.TourItem;
import at.matthias.tourplanner.viewmodels.SearchObserver;
import at.matthias.tourplanner.viewmodels.TourOverviewViewmodel;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import lombok.Getter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class TourOverviewController implements Initializable, SearchObserver {
  @Getter private final TourOverviewViewmodel tovm;
  @FXML private ListView<TourItem> tourList;
  private static final String DEFAULT_CONTROL_INNER_BACKGROUND = "derive(-fx-base,80%)";
  private static final String HIGHLIGHTED_CONTROL_INNER_BACKGROUND = "derive(palegreen, 50%)";
  private static final Logger logger = Logger.getLogger(TourOverviewController.class);

  public TourOverviewController() {
    tovm = new TourOverviewViewmodel();
    tovm.addSearchObserver(
        this); // TourOverviewController has to be notified when searchbar status changes
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    logger.info("initializing TourOverviewController");
    tovm.updateList();
    updateListView();
    formatCells();
  }
  // addTour event
  public void addTour(ActionEvent a) {
    logger.info("adding Tour");
    switchSceneTour(TourController.Mode.ADD);
  }
  // removeTour event
  public void removeTour(ActionEvent a) {
    logger.info("removing Tour");
    tovm.removeTour();
  }
  // editTour event
  public void editTour(ActionEvent a) {
    logger.info("editing Tour");
    switchSceneTour(TourController.Mode.EDIT);
  }
  // makeFavorite event
  public void makeFavorite(ActionEvent a) {
    tovm.makeFavorite();
    tovm.updateList();
    updateListView();
  }

  // new tour has been selected
  public void selectTour(MouseEvent m) {
    TourItem currentTour = tourList.getSelectionModel().getSelectedItem();
    if (currentTour == null) {
      return;
    }
    tovm.selectTour(currentTour);
  }

  public void updateListView() {
    ObservableList<TourItem> currentList = tovm.getTourList();
    if ((currentList != null) && (!currentList.isEmpty())) {
      tourList.getItems().removeAll(); // remove all current items

      // sort by favorite and then alpahbetically
      currentList.sort(new Comparator<TourItem>() {
        @Override
        public int compare(TourItem p, TourItem q) {
          String fn = p.getName();
          String qn = q.getName();
          boolean pd = p.getFavorite();
          boolean qd = q.getFavorite();
          if (pd && !qd)
            return -1;
          else if (!pd && qd)
            return 1;
          else
            return fn.compareToIgnoreCase(qn);
        }
      });

      tourList.setItems(currentList); // sets new items
    }
  }

  private void formatCells() {
    tourList.setCellFactory(new Callback<ListView<TourItem>, ListCell<TourItem>>() {
      @Override
      public ListCell<TourItem> call(ListView<TourItem> param) {
        ListCell<TourItem> cell = new ListCell<TourItem>() {
          @Override
          protected void updateItem(TourItem tour, boolean empty) {
            super.updateItem(tour, empty);
            if (empty || tour == null) {
              setText(null);
              setGraphic(null);
              setStyle("-fx-control-inner-background: " + DEFAULT_CONTROL_INNER_BACKGROUND
                  + ";"); // default style
            } else {
              setText(tour.getName());
              if (tour.favorite) {
                setStyle("-fx-control-inner-background: " + HIGHLIGHTED_CONTROL_INNER_BACKGROUND
                    + ";"); // style of favorite tour
              } else {
                setStyle("-fx-control-inner-background: " + DEFAULT_CONTROL_INNER_BACKGROUND
                    + ";"); // default style
              }
            }
          }
        };
        return cell;
      }
    });
  }
  // switch scene to tour form
  private void switchSceneTour(TourController.Mode m) {
    XMLReader reader = new XMLReader();
    String path = reader.getPath("tourFxml"); // get path to tourform
    logger.info("Switching Scene to Tour");
    Parent root;
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource(path));
      root = loader.load();
      tourList.getScene().setRoot(root);
      TourController c = loader.getController();
      c.setData(tovm.getCurrentTour(), m); // set data
    } catch (IOException e) {
      logger.error("Error Switching Scene to Tour!" + e);
    }
  }

  @Override // received new search -> updatelistview
  public void newSearch(String search) {
    tovm.filterList(search);
    updateListView();
  }
}
