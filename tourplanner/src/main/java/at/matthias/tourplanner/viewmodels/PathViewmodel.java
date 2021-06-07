package at.matthias.tourplanner.viewmodels;

import at.matthias.tourplanner.BL.Reporthandler;
import at.matthias.tourplanner.DL.FileHandler;
import at.matthias.tourplanner.models.TourItem;

public class PathViewmodel {
  // passes call
  public void exportTour(String path, TourItem tour) {
    FileHandler.exportTour(path, tour);
  }
  // passes call
  public void importTour(String path) {
    FileHandler.importTour(path);
  }
  // passes call
  public void fileSummary(String path, TourItem tour) {
    Reporthandler.fileSummary(path, tour);
  }
  // passes call
  public void fileReport(String path, TourItem tour) {
    Reporthandler.fileReport(path, tour);
  }
}
