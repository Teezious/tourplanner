package at.matthias.tourplanner.views;

import at.matthias.tourplanner.BL.Tourhandler;
import at.matthias.tourplanner.models.TourItem;
import at.matthias.tourplanner.viewmodels.EditTourViewmodel;
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

    @FXML private TextField name;
    @FXML private TextField start;
    @FXML private TextField end;
    @FXML private TextArea description;
    Tourhandler handler;
    EditTourViewmodel etvm;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        etvm = new EditTourViewmodel();
    }

    public void setTour(TourItem tour) {
        if (tour != null) {
            etvm.setId(tour.getId());
            this.name.setText(tour.getName());
            this.start.setText(tour.getStart());
            this.end.setText(tour.getEnd());
            this.description.setText(tour.getDescription());
        } else {
            etvm.setId(-1);
        }
    }

    public void save(ActionEvent c) {
        if (etvm.getId() != -1 && name.getText() != null && start.getText() != null && end.getText() != null) {
            etvm.saveEdit(name.getText(), start.getText(), end.getText(), description.getText());
            switchScene(FormPaths.MAINWINDOWPATH);
        }

        // TO DO else error msg?
    }

    public void cancel(ActionEvent c) {
        switchScene(FormPaths.MAINWINDOWPATH);
    }

    private void switchScene(String path) {
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
