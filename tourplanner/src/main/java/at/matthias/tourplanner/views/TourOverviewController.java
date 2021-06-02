package at.matthias.tourplanner.views;

import at.matthias.tourplanner.models.TourItem;
import at.matthias.tourplanner.viewmodels.SearchObserver;
import at.matthias.tourplanner.viewmodels.TourOverviewViewmodel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import lombok.Getter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class TourOverviewController implements Initializable, SearchObserver {
    @Getter private final TourOverviewViewmodel tovm;
    @FXML private ListView<TourItem> tourList;
    Logger logger = Logger.getLogger(TourOverviewController.class);

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
        switchSceneAdd(FormPaths.ADDTOURPATH);
    }

    public void removeTour(ActionEvent a) {
        logger.info("removing Tour");
        tovm.removeTour();
    }

    public void editTour(ActionEvent a) {
        TourItem currentTour = tovm.getCurrentTour();
        logger.info("editing Tour");
        if (currentTour != null) {
            switchSceneEdit(FormPaths.EDITTOURPATH);
        } else {
            logger.info("Editing Tour unsuccessful!No Tour selected");
        }
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
        tourList.setItems(tovm.getTourList());
    }

    private void formatCells() {
        tourList.setCellFactory((param -> new ListCell<TourItem>() {
            @Override
            protected void updateItem(TourItem tour, boolean empty) {
                super.updateItem(tour, empty);
                if (empty || tour == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(tour.getName());
                }
            }
        }));
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
