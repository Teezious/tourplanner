package at.matthias.tourplanner.views;

import at.matthias.tourplanner.BL.Loghandler;
import at.matthias.tourplanner.models.LogItem;
import at.matthias.tourplanner.viewmodels.EditLogViewmodel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;

public class EditLogController implements Initializable {
    private Loghandler loghandler;

    @FXML private DatePicker date;
    @FXML private Spinner<Integer> time;
    @FXML private Spinner<Integer> distance;
    @FXML private Spinner<Integer> rating;
    @FXML private Spinner<Integer> breaks;
    @FXML private Spinner<Integer> degrees;
    @FXML private ChoiceBox<String> weather;
    @FXML private ChoiceBox<String> activity;
    private EditLogViewmodel elv;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loghandler = new Loghandler();
        elv = new EditLogViewmodel();
    }

    public void save(ActionEvent c) {
        LogItem toEdit = elv.getToEdit();
        if (toEdit != null) {
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
        }
        switchWindow(FormPaths.MAINWINDOWPATH);

        // TO DO else error msg?
    }

    public void cancel(ActionEvent c) {
        switchWindow(FormPaths.MAINWINDOWPATH);
    }

    private void switchWindow(String path) {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(path));
            root = loader.load();
            date.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLog(LogItem log) {
        elv.setToEdit(log);
        this.date.setValue(log.getDate());
        this.time.getValueFactory().setValue(log.getTime());
        this.distance.getValueFactory().setValue(log.getDistance());
        this.rating.getValueFactory().setValue(log.getRating());
        this.breaks.getValueFactory().setValue(log.getBreaks());
        this.degrees.getValueFactory().setValue(log.getDegrees());
        this.weather.setValue(log.getWeather());
        this.activity.setValue(log.getActivity());
    }
}
