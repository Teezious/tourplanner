package at.matthias.tourplanner.views;

import at.matthias.tourplanner.BL.Tourhandler;
import at.matthias.tourplanner.models.TourItem;
import at.matthias.tourplanner.viewmodels.EditTourViewmodel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.LogManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;

public class EditTourController implements Initializable {

    @FXML private TextField name;
    @FXML private TextField start;
    @FXML private TextField end;
    @FXML private TextArea description;
    Tourhandler handler;
    EditTourViewmodel etvm;
    Logger logger = Logger.getLogger(EditTourController.class);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        etvm = new EditTourViewmodel();
    }

    public void setTour(TourItem tour) {
        if (tour != null) {
            logger.info("setting Tour Data");
            etvm.setId(tour.getId());
            this.name.setText(tour.getName());
            this.start.setText(tour.getStart());
            this.end.setText(tour.getEnd());
            this.description.setText(tour.getDescription());
        } else {
            etvm.setId(-1);
            logger.warn("Tour received was null");
        }
    }

    public void save(ActionEvent c) {
        if (etvm.getId() != -1 && name.getText() != null && start.getText() != null && end.getText() != null) {
            logger.info("Saving Tour Edit");
            etvm.saveEdit(name.getText(), start.getText(), end.getText(), description.getText());
            switchScene(FormPaths.MAINWINDOWPATH);
        } else {
            logger.info("Some fields have been left out");
        }

        // TO DO else error msg?
    }

    public void cancel(ActionEvent c) {
        logger.info("cancelling Edit");
        switchScene(FormPaths.MAINWINDOWPATH);
    }

    private void switchScene(String path) {
        Parent root;
        logger.info("Switching Scene to MainWindow");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(path));
            root = loader.load();
            name.getScene().setRoot(root);
        } catch (IOException e) {
            logger.error("Error Switching Scene" + e);
        }
    }
}
