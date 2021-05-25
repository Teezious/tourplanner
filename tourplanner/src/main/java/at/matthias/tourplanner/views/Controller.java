package at.matthias.tourplanner.views;

import at.matthias.tourplanner.BL.Manager;
import at.matthias.tourplanner.BL.TourFactory;
import at.matthias.tourplanner.BL.Tourhandler;
import at.matthias.tourplanner.Main;
import at.matthias.tourplanner.models.TourItem;
import at.matthias.tourplanner.viewmodels.ControllerViewModel;
import at.matthias.tourplanner.viewmodels.MainViewModel;
import at.matthias.tourplanner.views.EditTourController;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Controller implements Initializable {
    private final String ADDTOURPATH = "/fxml/addTour.fxml";
    private final String EDITTOURPATH = "/fxml/editTour.fxml";
    // create custom viewmodel
    static final MainViewModel viewModel = new MainViewModel();

    // add fx:id and use intelliJ to create field in controller
    @FXML private TextField searchBar;
    @FXML private ListView<TourItem> tourList;
    private ObservableList<TourItem> obsrvTourList;
    private TourItem currentTour;
    private Manager manager;
    private Tourhandler tourhandler;
    private ControllerViewModel ctrlview;

    protected TourItem getCurrentTour() {
        return this.currentTour;
    }

    public void searchAction(ActionEvent s) {
        updateListView(tourhandler.search(searchBar.textProperty().getValue()));
    }

    public void clearAction(ActionEvent c) {
        searchBar.textProperty().setValue("");
        updateListView(tourhandler.get());
    }

    public void addTour(ActionEvent a) {
        switchWindow(ADDTOURPATH);
    }

    public void removeTour(ActionEvent a) {
        tourhandler.remove(currentTour.getId());
        updateListView(tourhandler.get());
    }

    public void editTour(ActionEvent a) {
        if (currentTour != null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(EDITTOURPATH));
                Parent root = loader.load();
                EditTourController ed = loader.getController();
                ed.setData(currentTour); // TODO parse touritem directly?
                tourList.getScene().setRoot(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // TODO error window
        }
        updateListView(tourhandler.get());
    }

    public void updateListView(List<TourItem> items) {
        obsrvTourList.clear();
        obsrvTourList.addAll(items);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        manager = TourFactory.GetManager();
        ctrlview = new ControllerViewModel();
        tourhandler = new Tourhandler();
        setupListView();
        formatCells();
        setCurrentItem();
        ctrlview.setController(this);
    }

    private void setupListView() {
        obsrvTourList = FXCollections.observableArrayList();
        obsrvTourList.addAll(tourhandler.get());
        tourList.setItems(obsrvTourList);
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

    // TODO Never Reapeat functions...
    private void switchWindow(String path) {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(path));
            root = loader.load();
            tourList.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
