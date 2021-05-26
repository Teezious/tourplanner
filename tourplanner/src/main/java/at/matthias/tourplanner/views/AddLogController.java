package at.matthias.tourplanner.views;
import at.matthias.tourplanner.BL.Loghandler;
import java.io.IOException;
import java.net.URL;
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
    @FXML private TextField time;
    @FXML private Spinner distance;
    @FXML private Spinner rating;
    @FXML private Spinner breaks;
    @FXML private Spinner degrees;
    @FXML private ChoiceBox weather;
    @FXML private ChoiceBox activity;

    public void createLog(ActionEvent c) {
        Loghandler handler = new Loghandler();
        if (true) {
            handler.add();
            switchWindow(MAINWINDOWPATH);
        }
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
