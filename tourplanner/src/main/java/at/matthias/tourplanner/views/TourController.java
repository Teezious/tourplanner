package at.matthias.tourplanner.views;

import at.matthias.tourplanner.BL.Tourhandler;
import at.matthias.tourplanner.DL.XMLReader;
import at.matthias.tourplanner.models.TourItem;
import at.matthias.tourplanner.viewmodels.TourViewmodel;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;

public class TourController {
  public enum Mode {
    ADD,
    EDIT;
  }
  @FXML private TextField name;
  @FXML private TextField start;
  @FXML private TextField end;
  @FXML private TextArea description;
  private final TourViewmodel tvm;
  private Mode mode;
  private static final Logger logger = Logger.getLogger(TourController.class);

  public TourController() {
    logger.info("initializing TourController");
    tvm = new TourViewmodel();
  }

  public void setData(TourItem tour, Mode m) {
    mode = m;
    switch (mode) {
      case ADD:
        break;
      case EDIT:
        // if mode is edit data has to be set
        if (tour != null) {
          logger.info("setting Tour Data");
          tvm.setId(tour.getId());
          this.name.setText(tour.getName());
          this.start.setText(tour.getStart());
          this.end.setText(tour.getEnd());
          this.description.setText(tour.getDescription());
        } else {
          tvm.setId(-1);
          logger.warn("Tour received was null");
          switchScene();
        }
        break;
      default:
        break;
    }
  }

  public void save(ActionEvent c) {
    String providedName = name.getText();
    String providedStart = start.getText();
    String providedEnd = end.getText();
    String providedDescription = description.getText();

    if (providedName != null && providedStart != null && providedEnd != null
        && !providedStart.equals("") && !providedEnd.equals("")) {
      switch (mode) {
        case ADD:
          tvm.add(providedName, providedStart, providedEnd,
              providedDescription); // make call to add to TourViewmodel
          break;
        case EDIT:
          if (tvm.getId() != -1) {
            logger.info("Saving Tour Edit");
            tvm.edit(providedName, providedStart, providedEnd,
                providedDescription); // make call to edit to TourViewmodel
          }
          break;
        default:
          break;
      }

    } else {
      logger.info("Some fields have been left out");
    }

    switchScene();
  }

  public void cancel(ActionEvent c) {
    logger.info("cancelling Edit");
    switchScene();
  }

  // back to mainview
  private void switchScene() {
    XMLReader reader = new XMLReader();
    String path = reader.getPath("mainwindow");
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
