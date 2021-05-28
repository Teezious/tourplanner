package at.matthias.tourplanner.views;
import at.matthias.tourplanner.BL.Loghandler;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        alv = new AddLogViewmodel();
    }
    public void setId(int id) {
        alv.setId(id);
    }

    public void cancelLog(ActionEvent c) {
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
