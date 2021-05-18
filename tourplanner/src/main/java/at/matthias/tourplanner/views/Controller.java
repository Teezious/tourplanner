package at.matthias.tourplanner.views;

import at.matthias.tourplanner.BL.Manager;
import at.matthias.tourplanner.BL.TourFactory;
import at.matthias.tourplanner.Main;
import at.matthias.tourplanner.models.TourItem;
import at.matthias.tourplanner.viewmodels.MainViewModel;
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

    // create custom viewmodel
    static final MainViewModel viewModel = new MainViewModel();

    // add fx:id and use intelliJ to create field in controller
    @FXML public TextField searchBar;
    @FXML public ListView<TourItem> tourList;
    private ObservableList<TourItem> obsrvTourList;
    private TourItem currentTour;
    private Manager manager;

    public void searchAction(ActionEvent s) {
        obsrvTourList.clear();
        List<TourItem> items = manager.Search(searchBar.textProperty().getValue());
        obsrvTourList.addAll(items);
    }

    public void clearAction(ActionEvent c) {
        obsrvTourList.clear();
        searchBar.textProperty().setValue("");
        List<TourItem> items = manager.GetItems();
        obsrvTourList.addAll(items);
    }

    public void addTour(ActionEvent a) {
        switchWindow("/fxml/addTour.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        manager = TourFactory.GetManager();
        setupListView();
        formatCells();
        setCurrentItem();
    }

    private void setupListView() {
        obsrvTourList = FXCollections.observableArrayList();
        obsrvTourList.addAll(manager.GetItems());
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
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(path));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Add Tour");
            stage.setScene(new Scene(root));
            stage.show(); // open new window;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
