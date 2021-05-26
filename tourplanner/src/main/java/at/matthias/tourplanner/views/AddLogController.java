package at.matthias.tourplanner.views;
import at.matthias.tourplanner.BL.Loghandler;
import at.matthias.tourplanner.models.LogItem;
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
public class AddLogController implements Initializable {
    private final String MAINWINDOWPATH = "/fxml/mainWindow.fxml";
    @FXML private DatePicker date;
    @FXML private Spinner<Integer> time;
    @FXML private Spinner<Integer> distance;
    @FXML private Spinner<Integer> rating;
    @FXML private Spinner<Integer> breaks;
    @FXML private Spinner<Integer> degrees;
    @FXML private ChoiceBox<String> weather;
    @FXML private ChoiceBox<String> activity;

    public void addLog(ActionEvent c) {
        LocalDate providedDate = date.getValue();
        Integer providedTime = time.getValue();
        Integer providedDistance = distance.getValue();
        Integer providedRating = rating.getValue();
        Integer providedBreaks = breaks.getValue();
        Integer providedDegrees = degrees.getValue();
        String providedWeather = weather.getValue();
        String providedAcitvity = activity.getValue();

        Loghandler handler = new Loghandler();
        LogItem log = new LogItem(providedDate, providedTime, providedDistance, providedRating, providedBreaks, providedDegrees,
                                  providedWeather, providedAcitvity);
        handler.add(ControllerViewModel.getController().getCurrentTour().getId(), log);
        switchWindow(MAINWINDOWPATH);
        // TO DO else error msg?
    }

    public void cancelLog(ActionEvent c) {
        switchWindow(MAINWINDOWPATH);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
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
}
