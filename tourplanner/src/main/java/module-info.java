module tourplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    opens at.matthias.tourplanner to javafx.fxml;
    exports at.matthias.tourplanner;
    exports at.matthias.tourplanner.views;
    exports at.matthias.tourplanner.viewmodels;
    exports at.matthias.tourplanner.BL;
    exports at.matthias.tourplanner.models;
}
