package at.matthias.tourplanner.views;

import at.matthias.tourplanner.BL.Tourhandler;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
public class AddTourController implements Initializable {
    private final String MAINWINDOWPATH = "/fxml/mainWindow.fxml";
    @FXML private TextField name;
    @FXML private TextField start;
    @FXML private TextField end;
    @FXML private TextArea description;

    public void createTour(ActionEvent c) {
        Tourhandler handler = new Tourhandler();
        if (name.getText() != null && start.getText() != null && end.getText() != null) {
            handler.add(name.getText(), start.getText(), end.getText(), description.getText());
            switchWindow(MAINWINDOWPATH);
        }
        // TO DO else error msg?
    }

    public void cancelTour(ActionEvent c) {
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
            name.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
