package at.matthias.tourplanner.views;

import at.matthias.tourplanner.BL.Tourhandler;
import at.matthias.tourplanner.DL.XMLReader;
import at.matthias.tourplanner.models.TourItem;
import at.matthias.tourplanner.viewmodels.EditTourViewmodel;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;

public class EditTourController {

    @FXML private TextField name;
    @FXML private TextField start;
    @FXML private TextField end;
    @FXML private TextArea description;
    Tourhandler handler;
    private final EditTourViewmodel etvm;
    private static final Logger logger = Logger.getLogger(EditTourController.class);

    public EditTourController() {
        logger.info("initializing EditTourController");
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
            XMLReader reader = new XMLReader();
            switchScene(reader.getPath("mainwindow"));
        } else {
            logger.info("Some fields have been left out");
        }

        // TO DO else error msg?
    }

    public void cancel(ActionEvent c) {
        logger.info("cancelling Edit");
        XMLReader reader = new XMLReader();
        switchScene(reader.getPath("mainwindow"));
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
