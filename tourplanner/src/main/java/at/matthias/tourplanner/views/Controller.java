package at.matthias.tourplanner.views;

import at.matthias.tourplanner.BL.AppFac;
import at.matthias.tourplanner.BL.AppManager;
import at.matthias.tourplanner.models.TourItem;
import at.matthias.tourplanner.viewmodels.MainViewModel;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class Controller implements Initializable {

    // create custom viewmodel
    public MainViewModel viewModel = new MainViewModel();

    // add fx:id and use intelliJ to create field in controller
    @FXML public TextField searchBar;
    @FXML public ListView<TourItem> tourList;
    private ObservableList<TourItem> myTours;
    private TourItem currentTour;
    private AppManager manager;

    public void searchAction(ActionEvent s) {
        myTours.clear();
        List<TourItem> items = manager.Search(searchBar.textProperty().getValue());
        System.out.println(searchBar.textProperty().getValue());
        myTours.addAll(items);
    }

    public void clearAction(ActionEvent c) {
        myTours.clear();
        searchBar.textProperty().setValue("");
        List<TourItem> items = manager.GetItems();
        myTours.addAll(items);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        manager = AppFac.GetManager();
        setupListView();
        formatCells();
        setCurrentItem();
    }

    private void setupListView() {
        myTours = FXCollections.observableArrayList();
        myTours.addAll(manager.GetItems());
        tourList.setItems(myTours);
    }

    private void setCurrentItem() {
        tourList.getSelectionModel().selectedItemProperty().addListener(((observVal, oldVal, newVal) -> {
            if (newVal != null && oldVal != newVal) {
                currentTour = newVal;
            }
        }));
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
}
