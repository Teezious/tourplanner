package at.matthias.tourplanner.views;

import at.matthias.tourplanner.models.TourItem;
import at.matthias.tourplanner.viewmodels.MainViewmodel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.apache.log4j.Logger;

public class MainController implements Initializable {
    @FXML private TourOverviewController tourOverviewController;
    @FXML private TourInfosController tourInfosController;
    @FXML private MainViewmodel mvm;
    private Logger logger = Logger.getLogger(MainController.class);

    public void initialize(URL url, ResourceBundle resourceBundle) {
        logger.info("initializing MainController");
        mvm = new MainViewmodel(tourOverviewController.getTovm(), tourInfosController.getTivm());
    }

    public void fileReport(ActionEvent a) {
        logger.info("Filing new Report....");
        mvm.fileReport();
    }
}
