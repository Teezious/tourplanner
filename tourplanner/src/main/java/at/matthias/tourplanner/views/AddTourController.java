package at.matthias.tourplanner.views;

import at.matthias.tourplanner.DL.XMLReader;
import at.matthias.tourplanner.viewmodels.AddTourViewmodel;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;
public class AddTourController {
  @FXML private TextField name;
  @FXML private TextField start;
  @FXML private TextField end;
  @FXML private TextArea description;
  private final AddTourViewmodel atvm;
  private static final Logger logger = Logger.getLogger(AddTourController.class);

  public AddTourController() {
    logger.info("initializing AddTourController");
    atvm = new AddTourViewmodel();
  }

  public void createTour(ActionEvent c) {
    if (name.getText() != null && start.getText() != null && end.getText() != null) {
      atvm.add(name.getText(), start.getText(), end.getText(), description.getText());

      XMLReader reader = new XMLReader();
      switchScene(reader.getPath("mainwindow"));

    } else {
      logger.info("Some Fields seem to be empty! Adding Tour unsuccesful");
    }
    // TO DO else error msg?
  }

  public void cancelTour(ActionEvent c) {
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
      logger.error("Error switching to MainWindow" + e);
    }
  }
}
