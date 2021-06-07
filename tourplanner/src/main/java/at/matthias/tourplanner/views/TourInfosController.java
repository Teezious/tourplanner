package at.matthias.tourplanner.views;

import at.matthias.tourplanner.DL.XMLReader;
import at.matthias.tourplanner.models.LogItem;
import at.matthias.tourplanner.models.TourItem;
import at.matthias.tourplanner.viewmodels.TourInfosViewmodel;
import at.matthias.tourplanner.viewmodels.TourObserver;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
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
  private static final Logger logger = Logger.getLogger(TourInfosController.class);

  public TourInfosController() {
    tivm = new TourInfosViewmodel();
    tivm.addObserver(this); // TourInfosController has to be notified when new tour is selected
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    logger.info("initializing TourInfosController");
    updateTourDetails(tivm.getCurrentTour());
    updateLogTable(tivm.getCurrentTour());
  }

  // add log event
  public void addLog(ActionEvent c) {
    TourItem t = tivm.getCurrentTour();
    if (t != null) {
      switchSceneLog(LogController.Mode.ADD, t.getId());
    } else {
      logger.info("Adding Log failed! No Tour was selected");
    }
  }

  // remove log event
  public void removeLog(ActionEvent c) {
    tivm.removeLog();
    updateLogTable(tivm.getCurrentTour());
  }

  // edit log event
  public void editLog(ActionEvent c) {
    LogItem l = tivm.getCurrentLog();
    if (l != null) {
      switchSceneLog(LogController.Mode.EDIT, l.getId());
    } else {
      logger.info("Editing Log failed! No log was selected");
    }
  }

  // new search event
  public void searchAction(ActionEvent s) {
    logger.info("New Search Action");
    tivm.newSearch(searchBar.textProperty().getValue());
  }

  // clears search event -> resets listview in TouroverviewController
  public void clearAction(ActionEvent c) {
    logger.info("Clearing Search");
    searchBar.textProperty().setValue("");
    tivm.newSearch(null);
  }

  // fills log table with data in log tab
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
    TableColumn<LogItem, Float> speedCol = new TableColumn<>("Speed");
    speedCol.setCellValueFactory(new PropertyValueFactory<>("speed"));
    TableColumn<LogItem, Integer> breaksCol = new TableColumn<>("breaks");
    breaksCol.setCellValueFactory(new PropertyValueFactory<>("Breaks"));
    TableColumn<LogItem, Integer> degreesCol = new TableColumn<>("degrees");
    degreesCol.setCellValueFactory(new PropertyValueFactory<>("Degrees"));
    TableColumn<LogItem, String> weatherCol = new TableColumn<>("Weather");
    weatherCol.setCellValueFactory(new PropertyValueFactory<>("weather"));
    TableColumn<LogItem, String> activityCol = new TableColumn<>("Activity");
    activityCol.setCellValueFactory(new PropertyValueFactory<>("activity"));
    if (currentTour != null) {
      tivm.updateLogList();
      ObservableList<LogItem> logList = tivm.getLogList();
      if (logList.isEmpty()) {
        logTable.getColumns().clear();
        logger.info("Log Table, empty");
      } else {
        logTable.setItems(logList);
        logTable.getColumns().addAll(logIdCol, dateCol, timeCol, distanceCol, ratingCol, speedCol,
            breaksCol, degreesCol, weatherCol, activityCol);
      }
    } else {
      logger.info("Log Table, No Tour selected");
      logTable.getColumns().clear();
    }
  }

  // new log has been selected
  public void selectLog(MouseEvent m) {
    LogItem curLog = logTable.getSelectionModel().getSelectedItem();
    if (curLog == null) {
      return;
    }
    tivm.setCurrentLog(curLog);
  }

  // updateds tourdetails in details tab
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
      String imgPath = new XMLReader().getFullPath("image") + currentTour.getImage() + ".jpg";
      if (Files.exists(Paths.get(imgPath))) {
        File imgFile = new File(imgPath);
        this.image.setImage(new Image(imgFile.toURI().toString()));
      } else {
        this.image.setImage(null);
      }
      this.name.setText(currentTour.getName());
      this.startpoint.setText(currentTour.getStart());
      this.endpoint.setText(currentTour.getEnd());
      this.length.setText(String.valueOf(currentTour.getDistance()));
      this.description.setText(currentTour.getDescription());
    }
  }
  // switch scene to log form
  private void switchSceneLog(LogController.Mode m, int id) {
    XMLReader reader = new XMLReader();
    String path = reader.getPath("logFxml");
    Parent root;
    logger.info("Switching Scene to Log");
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource(path));
      root = loader.load();
      name.getScene().setRoot(root);
      LogController lc = loader.getController();
      lc.setData(id, m);
    } catch (IOException e) {
      logger.error("Error Switching Scene to Log!" + e);
    }
  }
  @Override // new tour has been selected
  public void updateCurrentTour(TourItem tour) {
    tivm.setCurrentTour(tour);
    updateTourDetails(tour);
    updateLogTable(tour);
  }
}
