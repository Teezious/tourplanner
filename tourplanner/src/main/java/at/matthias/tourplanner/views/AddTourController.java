package at.matthias.tourplanner.views;

import at.matthias.tourplanner.BL.Tourhandler;
import at.matthias.tourplanner.viewmodels.AddTourViewmodel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
public class AddTourController implements Initializable {
    @FXML private TextField name;
    @FXML private TextField start;
    @FXML private TextField end;
    @FXML private TextArea description;
    private AddTourViewmodel atvm;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        atvm = new AddTourViewmodel();
    }

    public void createTour(ActionEvent c) {

        if (name.getText() != null && start.getText() != null && end.getText() != null) {
            atvm.add(name.getText(), start.getText(), end.getText(), description.getText());
            switchWindow(FormPaths.MAINWINDOWPATH);
        }
        // TO DO else error msg?
    }

    public void cancelTour(ActionEvent c) {
        switchWindow(FormPaths.MAINWINDOWPATH);
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
