package at.matthias.tourplanner;

import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/fxml/mainWindow.fxml"));
    Parent root = loader.load();
    primaryStage.setTitle("Tourplanner");
    primaryStage.setScene(new Scene(root, 600, 400));
    primaryStage.show();
  }

  public static void main(String[] args) {
    Date date = new Date();
    String LogDate = new SimpleDateFormat("dd.MM.yyyy-H:m").format(date);
    System.setProperty("logFilename", LogDate);
    launch(args);
  }
}
