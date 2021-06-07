package at.matthias.tourplanner.views;

import at.matthias.tourplanner.DL.XMLReader;
import at.matthias.tourplanner.models.TourItem;
import at.matthias.tourplanner.viewmodels.MainViewmodel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import org.apache.log4j.Logger;

public class MainController implements Initializable {
  @FXML private TourOverviewController tourOverviewController;
  @FXML private TourInfosController tourInfosController;
  @FXML private MainViewmodel mvm;
  @FXML private MenuBar menuBar;
  private Logger logger = Logger.getLogger(MainController.class);

  public void initialize(URL url, ResourceBundle resourceBundle) {
    logger.info("initializing MainController");
    mvm = new MainViewmodel(tourOverviewController.getTovm(), tourInfosController.getTivm());
  }

  // file Report action
  public void fileReport(ActionEvent a) {
    logger.info("Filing new Report....");
    switchScene(PathController.Mode.REPORT);
  }
  // file Summary action
  public void fileSummary(ActionEvent e) {
    logger.info("Filing new Report....");
    switchScene(PathController.Mode.SUMMARY);
  }

  @FXML // Import tour action
  public void importTour(ActionEvent a) {
    logger.info("Importing....");
    switchScene(PathController.Mode.IMPORT);
  }
  @FXML // expor Tour action
  public void exportTour(ActionEvent a) {
    logger.info("Exporting....");
    switchScene(PathController.Mode.EXPORT);
  }

  // Switch Scene to path
  public void switchScene(PathController.Mode m) {
    XMLReader reader = new XMLReader();
    Parent root;
    logger.info("Switching Scene to Pathfxml");
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(
          getClass().getResource(reader.getPath("importExport"))); // get fxml location
      root = loader.load();
      PathController c = loader.getController();
      c.setTour(mvm.getCurrentTour()); // set Tour
      c.setMode(m); // set Mode
      menuBar.getScene().setRoot(root);
    } catch (IOException e) {
      logger.error("Error Switching Scene to Pathfxml!" + e);
    }
  }
}
