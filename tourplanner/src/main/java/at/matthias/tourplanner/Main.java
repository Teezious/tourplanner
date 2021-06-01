package at.matthias.tourplanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Main extends Application {
    private static final Logger LOGGER = LogManager.getLogger("File");

    @Override
    public void start(Stage primaryStage) throws Exception {
        LOGGER.info("Starting Application");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/mainWindow.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Tourplanner");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
