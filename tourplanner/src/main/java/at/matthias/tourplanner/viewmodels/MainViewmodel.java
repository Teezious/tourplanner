package at.matthias.tourplanner.viewmodels;
import at.matthias.tourplanner.models.TourItem;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;

public class MainViewmodel implements TourObserver {
  private TourOverviewViewmodel tovm;
  private TourInfosViewmodel tivm;
  private final Logger logger = Logger.getLogger(MainViewmodel.class);
  @Getter @Setter private TourItem currentTour;

  public MainViewmodel(TourOverviewViewmodel tovm, TourInfosViewmodel tivm) {
    logger.info("initialising MainViewModel");
    this.tovm = tovm;
    this.tivm = tivm;
    this.tovm.addObserver(tivm); // initialize observers
    this.tovm.addObserver(this); // initialize observers
    this.tivm.addSearchObserver(tovm); // initialize observers
  }

  @Override // new curren tour has been received
  public void updateCurrentTour(TourItem tour) {
    logger.info("New Current Tour");
    currentTour = tour;
  }
}
