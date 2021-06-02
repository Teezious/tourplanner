package at.matthias.tourplanner.views;

import at.matthias.tourplanner.models.LogItem;
import at.matthias.tourplanner.models.TourItem;
import at.matthias.tourplanner.viewmodels.TourInfosViewmodel;
import at.matthias.tourplanner.viewmodels.TourObserver;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lombok.Getter;
import org.apache.log4j.Logger;

public class TourInfosController implements TourObserver, Initializable {

    @FXML private ImageView image;
    @FXML private Label name;
    @FXML private Label startpoint;
    @FXML private Label endpoint;
    @FXML private Label length;
    @FXML private Label description;
    @FXML private TextField searchBar;
    @FXML private TableView<LogItem> logTable;
    @Getter private final TourInfosViewmodel tivm;
    private Logger logger = Logger.getLogger(TourInfosController.class);

    public TourInfosController() {
        tivm = new TourInfosViewmodel();
        tivm.addObserver(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logger.info("initializing TourInfosController");
        updateTourDetails(tivm.getCurrentTour());
        updateLogTable(tivm.getCurrentTour());
    }

    public void addLog(ActionEvent c) {
        switchSceneAddLog(FormPaths.ADDLOGPATH);
    }

    public void removeLog(ActionEvent c) {
        tivm.removeLog();
        updateLogTable(tivm.getCurrentTour());
    }

    public void editLog(ActionEvent c) {
        if (tivm.getCurrentLog() != null) {
            switchSceneEditLog(FormPaths.EDITLOGPATH);
        } else {
            logger.info("Editing Log failed! No log was selected");
        }
        updateLogTable(tivm.getCurrentTour());
    }

    public void searchAction(ActionEvent s) {
        logger.info("New Search Action");
        tivm.newSearch(searchBar.textProperty().getValue());
    }

    public void clearAction(ActionEvent c) {
        logger.info("Clearing Search");
        searchBar.textProperty().setValue("");
        tivm.newSearch(null);
    }

    public void updateLogTable(TourItem currentTour) {
        logger.info("Updating Log Table");
        logTable.getColumns().clear();
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
        TableColumn<LogItem, String> weatherCol = new TableColumn<>("Weather");
        weatherCol.setCellValueFactory(new PropertyValueFactory<>("weather"));
        TableColumn<LogItem, String> activityCol = new TableColumn<>("Activity");
        activityCol.setCellValueFactory(new PropertyValueFactory<>("activity"));
        if (currentTour != null) {
            logTable.setItems(tivm.getLogList());
            logTable.getColumns().addAll(logIdCol, dateCol, timeCol, distanceCol, ratingCol, avgSpeedCol, breaksCol, degreesCol,
                                         weatherCol, activityCol);
        } else {
            logger.info("Log Table, No Tour selected");
            logTable.getColumns().clear();
        }
    }

    public void selectLog(MouseEvent m) {
        LogItem curLog = logTable.getSelectionModel().getSelectedItem();
        if (curLog == null) {
            return;
        }
        tivm.setCurrentLog(curLog);
    }

    private void updateTourDetails(TourItem currentTour) {
        logger.info("updating Tour Details");
        if (currentTour == null) {
            logger.info("Tour Details, No Tour selected");
            this.image.setImage(null);
            this.name.setText("");
            this.startpoint.setText("");
            this.endpoint.setText("");
            this.length.setText("");
            this.description.setText("");
        } else {
            File imgFile = new File(currentTour.getImage());
            this.image.setImage(new Image(imgFile.toURI().toString()));
            this.name.setText(currentTour.getName());
            this.startpoint.setText(currentTour.getStart());
            this.endpoint.setText(currentTour.getEnd());
            this.length.setText(String.valueOf(currentTour.getDistance()));
            this.description.setText(currentTour.getDescription());
        }
    }
    private void switchSceneAddLog(String path) {
        logger.info("Switching Scene to AddLog");
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(path));
            root = loader.load();
            name.getScene().setRoot(root);
            AddLogController ac = loader.getController();
            ac.setTourId(tivm.getCurrentTour().getId());
        } catch (IOException e) {
            logger.error("Error Switching Scene to AddLog!" + e);
        }
    }
    private void switchSceneEditLog(String path) {
        Parent root;
        logger.info("Switching Scene to EditLog");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(path));
            root = loader.load();
            name.getScene().setRoot(root);
            EditLogController ec = loader.getController();
            ec.setLog(tivm.getCurrentLog());
        } catch (IOException e) {
            logger.error("Error Switching Scene to EditLog!" + e);
        }
    }
    @Override
    public void updateCurrentTour(TourItem tour) {
        tivm.setCurrentTour(tour);
        updateTourDetails(tour);
        updateLogTable(tour);
    }
}
