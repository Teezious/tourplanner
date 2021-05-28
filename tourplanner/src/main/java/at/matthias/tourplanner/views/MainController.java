package at.matthias.tourplanner.views;

import at.matthias.tourplanner.viewmodels.MainViewmodel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import lombok.Getter;

@Getter
public class MainController implements Initializable {
    @FXML private TourOverviewController tourOverviewController;
    @FXML private TourInfosController tourInfosController;
    private MainViewmodel mvm;

    public void initialize(URL url, ResourceBundle resourceBundle) {

        mvm = new MainViewmodel(tourOverviewController.getTovm(), tourInfosController.getTivm());
    }
}
