package at.matthias.tourplanner.views;
import at.matthias.tourplanner.DL.XMLReader;
import at.matthias.tourplanner.models.LogItem;
import at.matthias.tourplanner.models.TourItem;
import at.matthias.tourplanner.viewmodels.LogViewmodel;
import java.io.IOException;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;

public class LogController {
  public enum Mode {
    ADD,
    EDIT;
  }
  @FXML private DatePicker date;
  @FXML private Spinner<Integer> time;
  @FXML private Spinner<Integer> distance;
  @FXML private Spinner<Integer> rating;
  @FXML private Spinner<Integer> breaks;
  @FXML private Spinner<Integer> degrees;
  @FXML private ChoiceBox<String> weather;
  @FXML private ChoiceBox<String> activity;
  private final LogViewmodel lv;
  private static final Logger logger = Logger.getLogger(LogController.class);
  private Mode mode;

  public LogController() {
    logger.info("initializing AddLogController");
    lv = new LogViewmodel();
  }

  public void cancel(ActionEvent c) {
    logger.info("Cancelling of Adding Log");
    switchWindow();
  }

  public void setData(int id, Mode m) {
    mode = m;
    switch (mode) {
      case ADD:
        lv.setTourId(id);
        break;
      case EDIT:
        logger.info("Setting Log Data");
        lv.updateToEdit(id);
        LogItem log = lv.getToEdit(); // get log Item
        if (log != null) {
          // if mode is edit data has to be set
          this.date.setValue(log.getDate());
          this.time.getValueFactory().setValue(log.getTime());
          this.distance.getValueFactory().setValue(log.getDistance());
          this.rating.getValueFactory().setValue(log.getRating());
          this.breaks.getValueFactory().setValue(log.getBreaks());
          this.degrees.getValueFactory().setValue(log.getDegrees());
          this.weather.setValue(log.getWeather());
          this.activity.setValue(log.getActivity());
        } else {
          logger.error("Error setting Log Data! LogItem is null");
        }
        break;
      default:
        break;
    }
  }

  public void save(ActionEvent c) {
    LocalDate providedDate = date.getValue();
    Integer providedTime = time.getValue();
    Integer providedDistance = distance.getValue();
    Integer providedRating = rating.getValue();
    Integer providedBreaks = breaks.getValue();
    Integer providedDegrees = degrees.getValue();
    String providedWeather = weather.getValue();
    String providedAcitvity = activity.getValue();
    switch (mode) {
      case ADD:
        lv.add(new LogItem(providedDate, providedTime, providedDistance, providedRating,
            providedBreaks, providedDegrees, providedWeather, providedAcitvity));
        break;
      case EDIT:
        // if mode is edit data has to be set
        LogItem toEdit = lv.getToEdit();
        logger.info("saving edited Log");
        toEdit.setDate(providedDate);
        toEdit.setTime(providedTime);
        toEdit.setDistance(providedDistance);
        toEdit.setRating(providedRating);
        toEdit.setBreaks(providedBreaks);
        toEdit.setDegrees(providedDegrees);
        toEdit.setWeather(providedWeather);
        toEdit.setActivity(providedAcitvity);
        toEdit.calculateSpeed();
        lv.edit(toEdit);
        break;
      default:
        break;
    }
    switchWindow();
  }
  // switch back to mainwindow
  private void switchWindow() {
    XMLReader reader = new XMLReader();
    String path = reader.getPath("mainwindow");
    logger.info("Switching Scene to MainWindow");
    Parent root;
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource(path));
      root = loader.load();
      date.getScene().setRoot(root);
    } catch (IOException e) {
      logger.error("Error switching to MainWindow" + e);
    }
  }
}
