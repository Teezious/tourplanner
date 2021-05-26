package at.matthias.tourplanner.views;

import at.matthias.tourplanner.BL.Loghandler;
import at.matthias.tourplanner.models.LogItem;
import at.matthias.tourplanner.models.TourItem;
import at.matthias.tourplanner.viewmodels.ControllerViewModel;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
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
    Loghandler logHandler;
    TourItem currentTour;
    LogItem currentLog;

    public void addLog(ActionEvent c) {
        switchWindow(ADDLOGPATH);
    }
    public void removeLog(ActionEvent c) {
        // TODO
    }
    public void editLog(ActionEvent c) {
        // TODO
    }

    public void updateTourItem() {
        currentTour = ControllerViewModel.getController().getCurrentTour();
        updateLogTableView();
        updateTourDetails();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentTour = null;
        currentLog = null;
        obsrvLogList = FXCollections.observableArrayList();
        initTableView();
        updateLogTableView();
        updateTourDetails();
        ControllerViewModel.setTourInfosController(this);
    }

    private void initTableView() {
        // TODO
    }

    private void updateLogTableView() {
        if (currentTour != null) {
            this.logTable.getColumns().clear();
            TableColumn<LogItem, Integer> logIdCol = new TableColumn<>("LogId");
            logIdCol.setCellValueFactory(new PropertyValueFactory<>("logId"));
            TableColumn<LogItem, LocalDate> dateCol = new TableColumn<>("date");
            dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
            TableColumn<LogItem, LocalTime> timeCol = new TableColumn<>("time");
            timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
            TableColumn<LogItem, Float> distanceCol = new TableColumn<>("distance");
            distanceCol.setCellValueFactory(new PropertyValueFactory<>("distance"));
            TableColumn<LogItem, Integer> ratingCol = new TableColumn<>("rating");
            ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
            TableColumn<LogItem, Float> avgSpeedCol = new TableColumn<>("average speed");
            avgSpeedCol.setCellValueFactory(new PropertyValueFactory<>("average speed"));
            TableColumn<LogItem, Integer> breaksCol = new TableColumn<>("breaks");
            breaksCol.setCellValueFactory(new PropertyValueFactory<>("breaks"));
            TableColumn<LogItem, Integer> degreesCol = new TableColumn<>("degrees");
            degreesCol.setCellValueFactory(new PropertyValueFactory<>("degrees"));
            TableColumn<LogItem, Integer> weatherCol = new TableColumn<>("weather"); // TODO STRINg
            weatherCol.setCellValueFactory(new PropertyValueFactory<>("weather"));
            TableColumn<LogItem, Integer> activityCol = new TableColumn<>("activity"); // TODO STRINg
            activityCol.setCellValueFactory(new PropertyValueFactory<>("activity"));
            obsrvLogList.addAll(logHandler.get(currentTour.getId()));
            if (obsrvLogList.isEmpty() == false) {
                logTable.setItems(obsrvLogList);
                logTable.getColumns().addAll(logIdCol, dateCol, timeCol, activityCol, distanceCol, ratingCol, avgSpeedCol,
                                             breaksCol, degreesCol, weatherCol, activityCol);
            } else {
            }

            this.logTable.getColumns().clear();
        }
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
            image.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
