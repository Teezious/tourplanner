package at.matthias.tourplanner.views;

import at.matthias.tourplanner.BL.Loghandler;
import at.matthias.tourplanner.models.LogItem;
import at.matthias.tourplanner.models.TourItem;
import at.matthias.tourplanner.viewmodels.ControllerViewModel;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

public class TourInfosController implements Initializable {
    private final String ADDLOGPATH = "/fxml/addLog.fxml";
    private final String EDITLOGPATH = "/fxml/editLog.fxml";
    @FXML private ImageView image;
    @FXML private TableView<LogItem> logTable;
    @FXML private Label name;
    @FXML private Label startpoint;
    @FXML private Label endpoint;
    @FXML private Label length;
    @FXML private Label description;
    private ObservableList<LogItem> obsrvLogList;
    Loghandler loghandler;
    TourItem currentTour;
    LogItem currentLog;

    public void addLog(ActionEvent c) {
        switchWindow(ADDLOGPATH);
    }
    public void removeLog(ActionEvent c) {
        if (currentLog != null) {
            loghandler.remove(currentLog.getLogId());
            currentLog = null;
        } else {
            System.out.println(" no log selected");
        }
        updateTourItem();
    }
    public void editLog(ActionEvent c) {
        if (currentLog != null) {
            switchWindow(EDITLOGPATH);
        } else {
            System.out.println(" no log selected");
        }
        updateTourItem();
    }

    public LogItem getCurrentLog() {
        return currentLog;
    }

    public void updateTourItem() {
        currentTour = ControllerViewModel.getController().getCurrentTour();
        updateTourDetails();
        updateLogTableView();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentTour = null;
        currentLog = null;
        loghandler = new Loghandler();
        obsrvLogList = FXCollections.observableArrayList();
        updateLogTableView();
        updateTourDetails();
        setCurrentItem();
        ControllerViewModel.setTourInfosController(this);
    }

    public void updateLogTableView() {
        if (currentTour != null) {
            this.logTable.getColumns().clear();
            TableColumn<LogItem, Integer> logIdCol = new TableColumn<>("LogId");
            logIdCol.setCellValueFactory(new PropertyValueFactory<>("logId"));
            logIdCol.setVisible(false);
            TableColumn<LogItem, LocalDate> dateCol = new TableColumn<>("Date");
            dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
            TableColumn<LogItem, LocalTime> timeCol = new TableColumn<>("Time");
            timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
            TableColumn<LogItem, Float> distanceCol = new TableColumn<>("Distance");
            distanceCol.setCellValueFactory(new PropertyValueFactory<>("distance"));
            TableColumn<LogItem, Integer> ratingCol = new TableColumn<>("Rating");
            ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
            TableColumn<LogItem, Float> avgSpeedCol = new TableColumn<>("Average speed");
            avgSpeedCol.setCellValueFactory(new PropertyValueFactory<>("avgSpd"));
            TableColumn<LogItem, Integer> breaksCol = new TableColumn<>("breaks");
            breaksCol.setCellValueFactory(new PropertyValueFactory<>("Breaks"));
            TableColumn<LogItem, Integer> degreesCol = new TableColumn<>("degrees");
            degreesCol.setCellValueFactory(new PropertyValueFactory<>("Degrees"));
            TableColumn<LogItem, String> weatherCol = new TableColumn<>("Weather"); // TODO STRINg
            weatherCol.setCellValueFactory(new PropertyValueFactory<>("weather"));
            TableColumn<LogItem, String> activityCol = new TableColumn<>("Activity"); // TODO STRINg
            activityCol.setCellValueFactory(new PropertyValueFactory<>("activity"));

            logTable.setItems(loghandler.get(currentTour.getId()));
            logTable.getColumns().addAll(logIdCol, dateCol, timeCol, distanceCol, ratingCol, avgSpeedCol, breaksCol, degreesCol,
                                         weatherCol, activityCol);
        }

        else {
            this.logTable.getColumns().clear();
        }
    }

    private void setCurrentItem() {
        logTable.getSelectionModel().selectedItemProperty().addListener(((observVal, oldVal, newVal) -> {
            if (newVal != null && oldVal != newVal) {
                currentLog = newVal;
            }
        }));
    }

    private void updateTourDetails() {
        if (currentTour == null) {
            this.image.setImage(null);
            this.name.setText("");
            this.startpoint.setText("");
            this.endpoint.setText("");
            this.length.setText("");
            this.description.setText("");
        } else {
            this.image.setImage(null); // TODO implement image stuff
            this.name.setText(currentTour.getName());
            this.startpoint.setText(currentTour.getStart());
            this.endpoint.setText(currentTour.getEnd());
            this.length.setText(String.valueOf(currentTour.getDistance()));
            this.description.setText(currentTour.getDescription());
        }
    }

    private void switchWindow(String path) {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(path));
            root = loader.load();
            logTable.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
