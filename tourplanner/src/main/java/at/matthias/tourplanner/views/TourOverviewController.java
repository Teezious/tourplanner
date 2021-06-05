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
        tovm.addSearchObserver(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logger.info("initializing TourOverviewController");
        tovm.updateList();
        updateListView();
        formatCells();
    }
    public void addTour(ActionEvent a) {
        logger.info("adding Tour");
        XMLReader reader = new XMLReader();
        switchSceneAdd(reader.getPath("addtour"));
    }

    public void removeTour(ActionEvent a) {
        logger.info("removing Tour");
        tovm.removeTour();
    }

    public void editTour(ActionEvent a) {
        TourItem currentTour = tovm.getCurrentTour();
        logger.info("editing Tour");
        if (currentTour != null) {
            XMLReader reader = new XMLReader();
            switchSceneEdit(reader.getPath("edittour"));

        } else {
            logger.info("Editing Tour unsuccessful!No Tour selected");
        }
        tovm.updateList();
        updateListView();
    }

    public void makeFavorite(ActionEvent a) {
        tovm.makeFavorite();
        tovm.updateList();
        updateListView();
    }

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
            tourList.getItems().removeAll();
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

            tourList.setItems(currentList);
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
                            setStyle("-fx-control-inner-background: " + DEFAULT_CONTROL_INNER_BACKGROUND + ";");
                        } else {
                            setText(tour.getName());
                            if (tour.favorite) {
                                setStyle("-fx-control-inner-background: " + HIGHLIGHTED_CONTROL_INNER_BACKGROUND + ";");
                            } else {
                                setStyle("-fx-control-inner-background: " + DEFAULT_CONTROL_INNER_BACKGROUND + ";");
                            }
                        }
                    }
                };
                return cell;
            }
        });
    }

    private void switchSceneAdd(String path) {
        logger.info("Switching Scene to AddTour");
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(path));
            root = loader.load();
            tourList.getScene().setRoot(root);
        } catch (IOException e) {
            logger.error("Error Switching Scene to AddTour!" + e);
        }
    }

    private void switchSceneEdit(String path) {
        logger.info("Switching Scene to EditTour");
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(path));
            root = loader.load();
            tourList.getScene().setRoot(root);
            EditTourController c = loader.getController();
            c.setTour(tovm.getCurrentTour());
        } catch (IOException e) {
            logger.error("Error Switching Scene to EditTour!" + e);
        }
    }

    @Override
    public void newSearch(String search) {
        tovm.filterList(search);
        updateListView();
    }
}
