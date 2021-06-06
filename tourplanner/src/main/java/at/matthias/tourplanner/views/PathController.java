package at.matthias.tourplanner.views;

import at.matthias.tourplanner.BL.Reporthandler;
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
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;

public class PathController {
  public enum Mode {
    IMPORT,
    EXPORT,
    SUMMARY,
    REPORT;
  }
  @FXML private TextField pathField;
  private static final Logger logger = Logger.getLogger(PathController.class);
  @Getter @Setter private TourItem tour;
  @Getter @Setter private Mode mode;

  public void cancel(ActionEvent e) {
    logger.info("received Cancel");
    XMLReader reader = new XMLReader();
    switchScene(reader.getPath("mainwindow"));
  }

  public void enter(ActionEvent e) {
    logger.info("received Enter");
    String path = pathField.getText();
    switch (mode) {
      case EXPORT:
        logger.info("Exporting...");
        FileHandler.exportTour(path, tour);
        break;
      case IMPORT:
        logger.info("Importing...");
        FileHandler.importTour(path);
        break;

      case SUMMARY:
        logger.info("Filing Summary...");
        Reporthandler.fileSummary(path, tour);
        break;
      case REPORT:
        logger.info("Filing Report...");
        Reporthandler.fileReport(path, tour);
        break;

      default:
        break;
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
