package at.matthias.tourplanner.views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
public class AddTourController implements Initializable {
    @FXML TextField tourName;
    @FXML TextField tourStart;
    @FXML TextField tourEnd;
    @FXML TextArea tourDescription;

    public void createTour(ActionEvent c) {
        //  AddTourHandler handler = new AddTourHandler();
        //         handler.addTour(tourName.getText(), tourStart.getText(), tourEnd.getText(), tourDescription.getText());
        Node node = (Node)c.getSource();
        Stage thisStage = (Stage)node.getScene().getWindow();
        thisStage.close();
    }

    public void cancelTour(ActionEvent c) {
        Node node = (Node)c.getSource();
        Stage thisStage = (Stage)node.getScene().getWindow();
        thisStage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }

    // // TODO Never Reapeat functions...
    // private void switchWindow(String path) {
    //     try {

    //         FXMLLoader loader = new FXMLLoader();
    //         loader.setLocation(getClass().getResource(path));
    //         Parent root = loader.load();
    //         Stage stage = new Stage();
    //         stage.initModality(Modality.APPLICATION_MODAL);
    //         stage.initStyle(StageStyle.UNDECORATED);
    //         stage.setTitle("Add Tour");
    //         stage.setScene(new Scene(root));
    //         stage.show(); // open new window;
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }
}
