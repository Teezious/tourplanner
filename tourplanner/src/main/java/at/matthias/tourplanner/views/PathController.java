package at.matthias.tourplanner.views;

import at.matthias.tourplanner.DL.FileHandler;
import at.matthias.tourplanner.DL.XMLReader;
import at.matthias.tourplanner.models.TourItem;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;

public class PathController implements Initializable {
  @FXML private TextField pathField;
  private static final Logger logger = Logger.getLogger(PathController.class);
  private TourItem t;
  private boolean export;

  public void export(TourItem tour) {
    t = tour;
    export = true;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    export = false;
  }

  public void cancel(ActionEvent e) {
    logger.info("received Cancel");
    XMLReader reader = new XMLReader();
    switchScene(reader.getPath("mainwindow"));
  }

  public void enter(ActionEvent e) {
    logger.info("received Enter");
    String path = pathField.getText();
    if (export) {
      logger.info("Exporting...");
      FileHandler.exportTour(path, t);
    } else {
      logger.info("Importing...");
      FileHandler.importTour(path);
    }
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
      pathField.getScene().setRoot(root);
    } catch (IOException e) {
      logger.error("Error switching to MainWindow" + e);
    }
  }
}
