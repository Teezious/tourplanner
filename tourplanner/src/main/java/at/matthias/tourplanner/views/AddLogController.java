package at.matthias.tourplanner.views;
import at.matthias.tourplanner.models.LogItem;
import at.matthias.tourplanner.viewmodels.AddLogViewmodel;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import org.apache.log4j.Logger;

public class AddLogController implements Initializable {
    @FXML private DatePicker date;
    @FXML private Spinner<Integer> time;
    @FXML private Spinner<Integer> distance;
    @FXML private Spinner<Integer> rating;
    @FXML private Spinner<Integer> breaks;
    @FXML private Spinner<Integer> degrees;
    @FXML private ChoiceBox<String> weather;
    @FXML private ChoiceBox<String> activity;
    AddLogViewmodel alv;
    Logger logger = Logger.getLogger(AddLogController.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        alv = new AddLogViewmodel();
        logger.info("initializing AddLogController");
    }
    public void setTourId(int id) {
        logger.info("setting TourId");
        alv.setTourId(id);
    }

    public void cancelLog(ActionEvent c) {
        logger.info("Cancelling of Adding Log");
        switchWindow(FormPaths.MAINWINDOWPATH);
    }

    public void addLog(ActionEvent c) {
        LocalDate providedDate = date.getValue();
        Integer providedTime = time.getValue();
        Integer providedDistance = distance.getValue();
        Integer providedRating = rating.getValue();
        Integer providedBreaks = breaks.getValue();
        Integer providedDegrees = degrees.getValue();
        String providedWeather = weather.getValue();
        String providedAcitvity = activity.getValue();

        alv.add(new LogItem(providedDate, providedTime, providedDistance, providedRating, providedBreaks, providedDegrees,
                            providedWeather, providedAcitvity));

        switchWindow(FormPaths.MAINWINDOWPATH);
        // TO DO else error msg?
    }

    private void switchWindow(String path) {
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
