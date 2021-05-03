package at.matthias.tourplanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // fxml created with SceneBuilder
        URL url = new File("tourplanner/src/main/java/at/matthias/tourplanner/views/mainWindow.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        System.out.println("fxml loaded");

        // bootstrap "window" named stage
        primaryStage.setTitle("Hello World");
        System.out.println("set title");

        // set scene into stage in defined size
        primaryStage.setScene(new Scene(root, 600, 500));
        System.out.println("set scene");

        // let's go
        primaryStage.show();
        System.out.println("show stage");
    }

    public static void main(String[] args) {
        launch(args);
    }
}