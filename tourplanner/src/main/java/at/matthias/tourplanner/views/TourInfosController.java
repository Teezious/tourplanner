package at.matthias.tourplanner.views;

import at.matthias.tourplanner.models.LogItem;
import at.matthias.tourplanner.models.TourItem;
import at.matthias.tourplanner.viewmodels.ControllerViewModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

public class TourInfosController implements Initializable {
    @FXML private TableView<LogItem> logTable;
    private ControllerViewModel ctrlview;

    public void addLog(ActionEvent c) {
        TourItem item = ctrlview.getController().getCurrentTour();
        System.out.println(item.getName());
        // TODO
    }
    public void removeLog(ActionEvent c) {
        // TODO
    }
    public void editLog(ActionEvent c) {
        // TODO
    }

    public void updateTableView() {
        // TODO
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTableView();
        formatCells();
        ctrlview = new ControllerViewModel();
        ctrlview.setTourInfosController(this);
    }

    private void setupTableView() {
        // TODO
    }

    private void formatCells() {
        // tourList.setCellFactory((param -> new ListCell<TourItem>() {
        //     @Override
        //     protected void updateItem(TourItem tour, boolean empty) {
        //         super.updateItem(tour, empty);
        //         if (empty || tour == null) {
        //             setText(null);
        //             setGraphic(null);
        //         } else {
        //             setText(tour.getName());
        //         }
        //     }
        // }));
    }
}
