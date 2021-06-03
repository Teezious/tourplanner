package at.matthias.tourplanner.views;

import at.matthias.tourplanner.DL.XMLReader;
import at.matthias.tourplanner.models.LogItem;
import at.matthias.tourplanner.viewmodels.EditLogViewmodel;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import org.apache.log4j.Logger;

public class EditLogController {
    @FXML private DatePicker date;
    @FXML private Spinner<Integer> time;
    @FXML private Spinner<Integer> distance;
    @FXML private Spinner<Integer> rating;
    @FXML private Spinner<Integer> breaks;
    @FXML private Spinner<Integer> degrees;
    @FXML private ChoiceBox<String> weather;
    @FXML private ChoiceBox<String> activity;
    private final EditLogViewmodel elv;
    private static final Logger logger = Logger.getLogger(EditLogController.class);

    public EditLogController() {
        elv = new EditLogViewmodel();
        logger.info("initializing EditLogController");
    }

    public void save(ActionEvent c) {
        LogItem toEdit = elv.getToEdit();
        if (toEdit != null) {
            logger.info("saving edited Log");
            toEdit.setDate(date.getValue());
            toEdit.setTime(time.getValue());
            toEdit.setDistance(distance.getValue());
            toEdit.setRating(rating.getValue());
            toEdit.setBreaks(breaks.getValue());
            toEdit.setDegrees(degrees.getValue());
            toEdit.setWeather(weather.getValue());
            toEdit.setActivity(activity.getValue());
            toEdit.calculateAvgSpeed();
            elv.saveEdit(toEdit);
        } else {
            logger.error("Error saving edited Log! LogItem is null");
        }
        XMLReader reader = new XMLReader();
        switchWindow(reader.getPath("mainwindow"));

        // TO DO else error msg?
    }

    public void cancel(ActionEvent c) {
        logger.info("cancelling Log Edit");
        XMLReader reader = new XMLReader();
        switchWindow(reader.getPath("mainwindow"));
    }

    private void switchWindow(String path) {
        logger.info("Switching to MainWindow");
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(path));
            root = loader.load();
            date.getScene().setRoot(root);
        } catch (IOException e) {
            logger.info("Error Switching to MainWindow!" + e);
        }
    }

    public void setLog(LogItem log) {
        logger.info("Setting Log Data");
        elv.setToEdit(log);
        if (log != null) {
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
    }
}
