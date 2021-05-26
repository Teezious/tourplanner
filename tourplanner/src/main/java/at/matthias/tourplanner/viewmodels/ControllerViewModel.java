package at.matthias.tourplanner.viewmodels;

import at.matthias.tourplanner.views.*;
import lombok.Getter;
import lombok.Setter;

public class ControllerViewModel {
    private static Controller controller;
    private static TourInfosController tourInfosController;

    // TODO if null
    private ControllerViewModel() {
    }

    public static void setController(Controller ctrl) {
        controller = ctrl;
    }
    public static Controller getController() {
        return controller;
    }
    public static TourInfosController getTourInfosController() {
        return tourInfosController;
    }
    public static void setTourInfosController(TourInfosController tourinfosctrl) {
        tourInfosController = tourinfosctrl;
    }
}
