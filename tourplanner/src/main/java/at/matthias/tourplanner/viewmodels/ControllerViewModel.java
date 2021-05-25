package at.matthias.tourplanner.viewmodels;

import at.matthias.tourplanner.views.*;
import lombok.Getter;
import lombok.Setter;

public class ControllerViewModel {
    private static Controller controller;
    private static TourInfosController tourInfosController;

    public void setController(Controller ctrl) {
        controller = ctrl;
    }
    public Controller getController() {
        return controller;
    }
    public TourInfosController getTourInfosController() {
        return tourInfosController;
    }
    public void setTourInfosController(TourInfosController tourinfosctrl) {
        tourInfosController = tourinfosctrl;
    }
}
