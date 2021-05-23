package at.matthias.tourplanner.views;

import at.matthias.tourplanner.BL.Tourhandler;
import at.matthias.tourplanner.models.TourItem;
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

public class EditTourController implements Initializable {
    private final String MAINWINDOWPATH = "/fxml/mainWindow.fxml";
    @FXML private TextField name;
    @FXML private TextField start;
    @FXML private TextField end;
    @FXML private TextArea description;
    Tourhandler handler;
    TourItem toEdit;

    public void save(ActionEvent c) {
        if (toEdit != null) {
            if (name.getText() != null && start.getText() != null && end.getText() != null) {
                handler.edit(this.toEdit.getId(), name.getText(), start.getText(), end.getText(), description.getText());
                switchWindow(MAINWINDOWPATH);
            }
        }

        // TO DO else error msg?
    }

    public void cancel(ActionEvent c) {
        switchWindow(MAINWINDOWPATH);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handler = new Tourhandler();
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

    public void setData(TourItem tour) {
        this.toEdit = tour;

        this.name.setText(tour.getName());
        this.start.setText(tour.getStart());
        this.end.setText(tour.getEnd());
        this.description.setText(tour.getDescription());
    }
}
