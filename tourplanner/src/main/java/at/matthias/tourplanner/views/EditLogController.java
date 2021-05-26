package at.matthias.tourplanner.views;

import at.matthias.tourplanner.BL.Loghandler;
import at.matthias.tourplanner.models.Activity;
import at.matthias.tourplanner.models.LogItem;
import at.matthias.tourplanner.models.Weather;
import at.matthias.tourplanner.viewmodels.ControllerViewModel;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditLogController implements Initializable {
    private final String MAINWINDOWPATH = "/fxml/mainWindow.fxml";
    private Loghandler loghandler;
    private LogItem toEdit;
    @FXML private DatePicker date;
    @FXML private Spinner<Integer> time;
    @FXML private Spinner<Integer> distance;
    @FXML private Spinner<Integer> rating;
    @FXML private Spinner<Integer> breaks;
    @FXML private Spinner<Integer> degrees;
    @FXML private ChoiceBox<String> weather;
    @FXML private ChoiceBox<String> activity;

    public void save(ActionEvent c) {
        if (toEdit != null) {
            toEdit.setDate(date.getValue());
            toEdit.setTime(time.getValue());
            toEdit.setDistance(distance.getValue());
            toEdit.setRating(rating.getValue());
            toEdit.setBreaks(breaks.getValue());
            toEdit.setDegrees(degrees.getValue());
            toEdit.setWeather(Weather.valueOf(weather.getValue().toUpperCase()));
            toEdit.setActivity(Activity.valueOf(activity.getValue().toUpperCase()));
            toEdit.calculateAvgSpeed();
            loghandler.edit(toEdit);
            switchWindow(MAINWINDOWPATH);
        }

        // TO DO else error msg?
    }

    public void cancel(ActionEvent c) {
        switchWindow(MAINWINDOWPATH);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loghandler = new Loghandler();
        toEdit = ControllerViewModel.getTourInfosController().getCurrentLog();
        setData();
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

    public void setData() {
        String weatherStr = toEdit.getWeather().toString().toLowerCase();
        String activityStr = toEdit.getActivity().toString().toLowerCase();
        String w1 = weatherStr.substring(0, 1).toUpperCase();
        String a1 = activityStr.substring(0, 1).toUpperCase();
        weatherStr = w1 + weatherStr.substring(1);
        activityStr = a1 + activityStr.substring(1);

        this.date.setValue(toEdit.getDate());
        this.time.getValueFactory().setValue(toEdit.getTime());
        this.distance.getValueFactory().setValue(toEdit.getDistance());
        this.rating.getValueFactory().setValue(toEdit.getRating());
        this.breaks.getValueFactory().setValue(toEdit.getBreaks());
        this.degrees.getValueFactory().setValue(toEdit.getDegrees());
        this.weather.setValue(weatherStr);
        this.activity.setValue(activityStr);
    }
}
