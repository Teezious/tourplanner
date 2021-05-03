module tourplanner {
    requires javafx.controls;
    requires javafx.fxml;
    opens at.matthias.tourplanner to javafx.fxml;
    exports at.matthias.tourplanner;
    exports at.matthias.tourplanner.views;
}
